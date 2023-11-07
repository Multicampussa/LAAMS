import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useApi from '../../../Hook/useApi';

const Alram = () => {
  const api = useApi();
  const [roomId,setRoomId]=useState();
  const [roomName,setRoomName] = useState();
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const alramMessage = useRef([]);
  const accessToken = useSelector(state=>state.User.accessToken);
  const [showAlram,setShowAlram] = useState(false);
  const authority = useSelector(state=>state.User.authority);
  const alramItems = useMemo(()=>{
    switch(authority){
      case "ROLE_DIRECTOR":
        return alramMessage.current.filter(e=>e.type!=="ENTER" && e.sender === "운영자").map((e,idx)=><li className='header-bell-item' key={idx}>{e.sender}:{e.message}</li>)
      case "ROLE_MANAGER":
        return alramMessage.current.filter(e=>e.type!=="ENTER"  && e.sender !== "운영자").map((e,idx)=><li className='header-bell-item' key={idx}>{e.sender}:{e.message}</li>)
      default:
        break;
    }
    if(showAlram){}
  },[showAlram,authority])

  const handleAlramBtn = useCallback(()=>{
    setShowAlram(e=>!e);
  },[]);

  const disconnect = useCallback(()=>{
    switch(authority){
      case "ROLE_DIRECTOR":
        ws.current.unsubscribe(`/topic/chat/room/alarm`);
        ws.current.unsubscribe(`/topic/chat/room/${roomId}`);
        break;
      case "ROLE_MANAGER":
        ws.current.unsubscribe(`/topic/chat/room/alarm`);
        break;
      default:
        break;
    }
  },[authority,roomId]);

  const managerConnect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, async function(frame) {
      ws.current.subscribe(`/topic/chat/room/alarm`, function(message) {
        const recv = JSON.parse(message.body);
        alramMessage.current.push(recv);
      });
      
    }, function(error) {
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`);
          ws.current = Stomp.over(socket.current);
          managerConnect();
        },100);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken]);

  const directorConnect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/alarm`, function(message) {
        const recv = JSON.parse(message.body);
        alramMessage.current.push(recv);
      });
      ws.current.subscribe(`/topic/chat/room/${roomId}`, function(message) {
        const recv = JSON.parse(message.body);
        console.log(recv);
      });
      ws.current.send("/app/chat/message", {'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'ENTER', roomId:roomId, sender:roomName}));
    }, function(error) {
      console.log(error);
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`, null, {withCredentials: true});
          ws.current = Stomp.over(socket.current);
          directorConnect();
        },100);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken,roomId,roomName]);

  const getRoomData = useCallback(()=>{
    api.post("chat/room")
      .then(({data})=>{
        setRoomId(data.data.roomId);
        setRoomName(data.data.roomName);
      })
      .catch(err=>{console.log(err);});
  },[api])

  useEffect(()=>{
    if(!roomId){return;}
    directorConnect();
  },[roomId,directorConnect])

  useEffect(()=>{
    switch(authority){
      case "ROLE_DIRECTOR":
        getRoomData();
        break;
      case "ROLE_MANAGER":
        managerConnect();
        break;
      default:
        break;
    }
  },[getRoomData,managerConnect,directorConnect,authority]);

  useEffect(()=>{
    window.addEventListener("beforeunload",disconnect);
  },[disconnect])
  
  return (
    <>
      <button onClick={handleAlramBtn} className='header-bell'>
        <div className='hidden-text'>BELL</div>
      </button>
      <ul className={`header-bell-box${showAlram?"open":"close"}`}>
        {alramItems}
      </ul>
    </>
  )
}

export default Alram