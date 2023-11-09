import React, {  useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useApi from '../../../../Hook/useApi';

const NoticeNow = ({data}) => {
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const chatBox = useRef();
  const accessToken = useSelector(state=>state.User.accessToken);
  const [message,setMessage] = useState("");
  const [messageList,setMessageList] = useState([]);
  const api = useApi();

  //지난 채팅 내역 동기화
  useEffect(()=>{
    api.get(`room/${data[0]?.roomId}`)
      .then(({data})=>{
        setMessageList(data);
      })
  },[api,data])


  //TODO : 소켓 연결
  const connect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, async function(frame) {
      ws.current.subscribe(`/topic/chat/room/notice-now`, function(message) {
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
  },[accessToken]);

  const disconnect = useCallback(()=>{
    ws.current.disconnect();
  },[]);

  useEffect(()=>{
    connect();
    return ()=>{
      disconnect();
    }
  },[connect,disconnect])

  //TODO : 메시지 전송
  const handleSend = useCallback((e)=>{
    ws.current.send("/app/chat/message/notice/now",{'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'TALK', roomId:data[0]?.roomId, sender:null, roomName:data[0]?.roomName ,message}));
    setMessage("");
    chatBox.current.scrollTop = 0;
  },[message,data,accessToken]);

  //TODO : 메시지 Div 생성
  const messageItems = useMemo(()=>{
    return messageList.reverse().map((e,idx)=><li key={idx} className='manager-chat-noticeitem'>
      <div className='manager-chat-noticeitem-sender'>{e.sender}</div>
      <div className='manager-chat-noticeitem-message'>{e.message}</div>
    </li>);
  },[messageList]);
  return (
    <article className='manager-chat-noticenow'>
      <ul ref={chatBox} className='manager-chat-box'>
        {
          messageItems
        }
      </ul>
      <div className='manager-chat-send'><input onKeyDown={(e)=>{if(e.key==="Enter"){handleSend(e);}}} value={message} onChange={e=>setMessage(e.target.value)} /><button className='btn-s' onClick={handleSend}>입력</button></div>
    </article>
  )
}

export default NoticeNow