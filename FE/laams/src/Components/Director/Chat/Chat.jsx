import React,{useMemo,useRef,useCallback,useState,useEffect} from 'react'
import { useSelector } from 'react-redux';
import Stomp from 'stomp-websocket';
import SockJS from 'sockjs-client';
import useApi from './../../../Hook/useApi';

const Chat = ({data}) => {
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const chatBox = useRef();
  const accessToken = useSelector(state=>state.User.accessToken);
  const [message,setMessage] = useState("");
  const [messageList,setMessageList] = useState([]);
  const api = useApi();

  //TODO : 소켓 연결
  const connect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/${data.roomId}`, function(message) {
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
  },[accessToken,data]);
  
  const disconnect = useCallback(()=>{
    ws.current.disconnect();
  },[]);
  
  //TODO : 메시지 전송
  const handleSend = useCallback((e)=>{
    ws.current.send("/app/chat/message",{'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'TALK', roomId:data.roomId, sender:data.roomName, roomName: data.roomName,message}));
    setMessage("");
    chatBox.current.scrollTop = 0;
  },[message,accessToken,data]);

  //TODO : 지난 메시지 내역 조회
  const getMessage = useCallback(()=>{
    api.get(`room/${data.roomId}`)
    .then((res)=>{
      setMessageList(res.data);
    });
  },[api,data])

  useEffect(()=>{
    getMessage();
    connect();
    return ()=>disconnect();
  },[getMessage,connect,disconnect]);

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
    <div className='manager-chat-director'>
      <ul ref={chatBox} className='manager-chat-box'>
        {
          messageItems
        }
      </ul>
      <div className='manager-chat-send'><input onKeyDown={(e)=>{if(e.key==="Enter"){handleSend(e);}}} value={message} onChange={e=>setMessage(e.target.value)} /><button className='btn-s' onClick={handleSend}>입력</button></div>
    </div>
  )
}

export default Chat