import React,  { useCallback,useState, useEffect,useRef,useMemo}  from 'react'
import useApi from '../../../../Hook/useApi';
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useCenter from './../../../../Hook/useCenter';

const Notice = ({menu,data}) => {
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const chatBox = useRef();
  const accessToken = useSelector(state=>state.User.accessToken);
  const [messageList,setMessageList] = useState([]);
  const api = useApi();
  const isConnect = useRef(false);

  //TODO : 소켓 연결
  const connect = useCallback((menu,room)=>{
    isConnect.current=true;
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, async function(frame) {
      switch(menu){
        case "notice-all":
          ws.current.subscribe(`/topic/chat/room/notice-all`, function(message) {
            const recv = JSON.parse(message.body);
            recv.read = false;
            setMessageList(e=>[...e,recv]);
          });
          break;
        case "noticeByRegion":
          ws.current.subscribe(`/topic/chat/room/notice-${room[2]?.roomName}`, function(message) {
            const recv = JSON.parse(message.body);
            setMessageList(e=>[...e,recv]);
          });
          break;
        case "noticeByCenter":
          ws.current.subscribe(`/topic/chat/room/notice-${room[2]?.roomName}`, function(message) {
            const recv = JSON.parse(message.body);
            setMessageList(e=>[...e,recv]);
          });
          break;
        default:
          break;
      }
    }, function(error) {
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`);
          ws.current = Stomp.over(socket.current);
          connect(menu,room);
        },100);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken]);

  const disconnect = useCallback(()=>{
    isConnect.current=false;
    ws.current.disconnect();
  },[]);

  useEffect(()=>{
    if(!isConnect.current){
      switch(menu){
        case "notice-all":
          api.get(`room/${data[1].roomId}`)
          .then(res=>{
            setMessageList(res.data);
            connect(menu,data[1]);
          });
          break;
        case "noticeByRegion":
          api.get(`room/${data[2].roomId}`)
          .then(res=>{
            setMessageList(res.data);
            connect(menu,data[2]);
          });
          break;
        case "noticeByCenter":
          api.get(`room/${data[3].roomId}`)
          .then(res=>{
            setMessageList(res.data);
            connect(menu,data[3]);
          });
          break;
        default:
          break;
      }
    }else{
      disconnect();
    }
    return ()=>disconnect();
  },[api,data,disconnect,connect,menu]);

  //TODO : 메시지 Div 생성
  const messageItems = useMemo(()=>{
    return [...messageList].reverse().map((e,idx)=><li key={idx} className='manager-chat-noticeitem'>
      <div className='manager-chat-noticeitem-sender'>{e.sender}</div>
      <div className='manager-chat-noticeitem-message'>{e.message}</div>
    </li>);
  },[messageList]);

  return (
    <article className='manager-chat-noticeall'>
      <ul ref={chatBox} className='manager-chat-box'>
        {
          messageItems
        }
      </ul>
    </article>
  )
}

export default Notice