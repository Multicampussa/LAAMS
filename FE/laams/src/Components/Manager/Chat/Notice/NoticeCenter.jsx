import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useApi from '../../../../Hook/useApi';
import useCenter from './../../../../Hook/useCenter';

const NoticeCenter = ({data}) => {
  const isConnect = useRef(false);
  const centerData = useCenter();
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const chatBox = useRef();
  const accessToken = useSelector(state=>state.User.accessToken);
  const [message,setMessage] = useState("");
  const [messageList,setMessageList] = useState([]);
  const api = useApi();
  const [center,setCenter] = useState();
  const [region,setRegion] = useState(null);

  //TODO : 소켓 연결
  const connect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      isConnect.current=true;
      ws.current.subscribe(`/topic/chat/room/notice-${JSON.parse(center).centerName}`, function(message) {
        const recv = JSON.parse(message.body);
        recv.read = false;
        setMessageList(e=>[...e,recv]);
      });
    }, function(error) {
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`);
          ws.current = Stomp.over(socket.current);
          connect();
        },100);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken,center]);

  const disconnect = useCallback(()=>{
    ws.current.disconnect();
    isConnect.current=false;
  },[]);

  useEffect(()=>{
    if(!centerData) return;
    setRegion(Object.keys(centerData).reverse()[0]);
  },[centerData]);

  useEffect(()=>{
    if(!centerData||!region) return;
    setCenter(JSON.stringify(centerData[region][0]));
  },[centerData,region])

  const getMessage = useCallback((centerName)=>{
    api.get(`room/${data[centerName]}`)
    .then(({data})=>{
      setMessageList(data);
    });
  },[api,data])

  useEffect(()=>{
    if(!center)return;
    if(isConnect.current){
      disconnect();
    }
    getMessage(JSON.parse(center).centerName);
    connect();
  },[center,disconnect,connect,getMessage]);

  
  //TODO : 메시지 전송
  const handleSend = useCallback((e)=>{
    const centerObj = JSON.parse(center);
    ws.current.send("/app/chat/message/notice/center",{'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'TALK', roomId:data[centerObj.centerName], sender:null, roomName: centerObj.centerName,message}));
    setMessage("");
    chatBox.current.scrollTop = 0;
  },[message,data,accessToken,center]);
  
  //TODO : 지역Option 생성
  const regionItem = useMemo(()=>{
    if(!centerData)return[];
    return Object.keys(centerData).reverse().map((e,idx)=><option key={idx}>
      {e}
    </option>)
  },[centerData]);

    //TODO : 센터Option 생성
    const centerItem = useMemo(()=>{
      if(!centerData||!region)return[];
      return centerData[region].map((e,idx)=><option value={JSON.stringify(e)} key={idx}>
        {e.centerName}
      </option>)
    },[centerData,region]);

  //TODO : 메시지 li 생성
  const messageItems = useMemo(()=>{
    return messageList.map((e,idx)=><li key={idx} className='manager-chat-noticeitem'>
      <div className='manager-chat-noticeitem-sender'>{e.sender}</div>
      <div className='manager-chat-noticeitem-message'>{e.message}</div>
    </li>);
  },[messageList]);

  useEffect(()=>{
    chatBox.current.scrollTop = chatBox.current.scrollHeight;
  },[messageItems])

  return (
    <article className='manager-chat-noticecenter'>
      <div className='flex-row'>
        <select onChange={e=>setRegion(e.target.value)}>
          {regionItem}
        </select>
        <select onChange={e=>setCenter(e.target.value)}>
          {centerItem}
        </select>
      </div>
      <ul ref={chatBox} className='manager-chat-box'>
        {
          messageItems
        }
      </ul>
      <div className='manager-chat-send'><input onKeyDown={(e)=>{if(e.key==="Enter"){handleSend(e);}}} value={message} onChange={e=>setMessage(e.target.value)} /><button className='btn-s' onClick={handleSend}>입력</button></div>
    </article>
  )
}

export default NoticeCenter