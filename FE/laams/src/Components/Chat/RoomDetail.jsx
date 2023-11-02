import axios from 'axios';
import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom'
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';

const RoomDetail = () => {
  const {roomId} = useParams();
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const [message,setMessage] = useState("");
  const [messageList,setMessageList] = useState([]);
  const accessToken = useSelector(state=>state.User.accessToken);
  const memberId = useSelector(state=>state.User.memberId);
  const findRoom =useCallback(()=> {
    axios.get(`${process.env.REACT_APP_SPRING_URL}/room/${roomId}`).then(response => { console.log(response.data) });
  },[roomId]);
  const sendMessage = useCallback( ()=> {
    ws.current.send("/app/chat/message", {'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'TALK', roomId:roomId, sender:memberId, message}));
    setMessage("");
  },[roomId,message,accessToken,memberId]);
  const recvMessage = useCallback( (recv)=> {
    // this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    // axios.get(`${process.env.REACT_APP_SPRING_URL}/room/`+roomId).then(response => console.log("TEST",response));
    setMessageList((e)=>[...e,`${recv.sender} : ${recv.message}`]);
  },[])

  
  useEffect(()=>{
    if(!roomId) return ;
    const connect = () =>{
      // pub/sub event
      ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
          ws.current.subscribe(`/topic/chat/room/${roomId}`, function(message) {
            const recv = JSON.parse(message.body);
            recvMessage(recv);
          });
          ws.current.send("/app/chat/message", {'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'ENTER', roomId:roomId, sender:memberId}));
      }, function(error) {
          if(reconnect.current++ <= 5) {
              setTimeout(function() {
                  console.log("connection reconnect");
                  socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`);
                  ws.current = Stomp.over(socket.current);
                  connect();
              },10*1000);
          }
      });
    }
    connect();
  },[roomId,recvMessage,accessToken,memberId]);
  const messageItems = useMemo(()=>{
    return messageList.map((e,idx)=><li key={idx}>{e}</li>)
  },[messageList]);
  return (
    <div>
      <label>채팅 <input onKeyDown={e=>{
        if(e.keyCode === 13){sendMessage()}
      }} onChange={e=>setMessage(e.target.value)}/></label><button onClick={sendMessage}>입력</button>
      <ul>
        {
          messageItems
        }
      </ul>
    </div>
  )
}

export default RoomDetail