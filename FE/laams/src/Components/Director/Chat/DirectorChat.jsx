import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useApi from '../../../Hook/useApi';

const DirectorChat = () => {
  const [roomId,setRoomId]=useState();
  const [roomName,setRoomName] = useState();
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`, null, {withCredentials: true}));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const [message,setMessage] = useState("");
  const [messageList,setMessageList] = useState([]);
  const accessToken = useSelector(state=>state.User.accessToken);
  const api = useApi();

  // const findRoom =useCallback(()=> {
  //   axios.get(`${process.env.REACT_APP_SPRING_URL}/room/${roomId}`).then(response => { console.log(response.data) });
  // },[roomId]);
  const sendMessage = useCallback( ()=> {
    ws.current.send("/app/chat/message", {'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'TALK', roomId:roomId, sender:roomName, message}));
    setMessage("");
  },[roomName,roomId,message,accessToken]);
  const recvMessage = useCallback( (recv)=> {
    // this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    // axios.get(`${process.env.REACT_APP_SPRING_URL}/room/`+roomId).then(response => console.log("TEST",response));
    setMessageList((e)=>[...e,`${recv.sender} : ${recv.message}`]);
    console.log("보내져!!!",roomId)
  },[roomId])

  const connect = useCallback(() =>{
    // pub/sub event
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/${roomId}`, function(message) {
        const recv = JSON.parse(message.body);
        recvMessage(recv);
      });
      ws.current.send("/app/chat/message", {'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'ENTER', roomId:roomId, sender:roomName}));
    }, function(error) {
      console.log(error);
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`, null, {withCredentials: true});
          ws.current = Stomp.over(socket.current);
          connect();
        },10*1000);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken,recvMessage,roomId,roomName])
  
  const getRoomData = useCallback(()=>{
    api.post("chat/room")
      .then(({data})=>{
        setRoomId(data.data.roomId);
        setRoomName(data.data.roomName);
      })
      .catch(err=>{console.log(err);});
  },[api])

  useEffect(()=>{
    if(!roomId || !roomName) {
      getRoomData();
    }else{
      connect();
    }
  },[roomName,roomId,connect,getRoomData]);

  const messageItems = useMemo(()=>{
    return messageList.map((e,idx)=><li key={idx}>{e}</li>)
  },[messageList]);
  return (
    <div>
      <button onClick={connect}>연결</button>
      <label>채팅 <input value={message} onKeyDown={e=>{
        if(e.keyCode === 13){sendMessage();}
      }} onChange={e=>{setMessage(e.target.value);}}/></label><button onClick={sendMessage}>입력</button>
      <ul>
        {
          messageItems
        }
      </ul>
    </div>
  )
}

export default DirectorChat
