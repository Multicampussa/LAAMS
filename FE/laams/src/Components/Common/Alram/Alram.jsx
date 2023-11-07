import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';

const Alram = () => {
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
      case "ROLE_MNANAGER":
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
    ws.current.unsubscribe(`/topic/chat/room/alarm`);
  },[]);

  const connect = useCallback(() =>{
    // pub/sub event
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/alarm`, function(message) {
        const recv = JSON.parse(message.body);
        alramMessage.current.push(recv);
      });
    }, function(error) {
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`);
          ws.current = Stomp.over(socket.current);
          connect();
        },10*1000);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken,alramMessage])

  useEffect(()=>{  
    connect();
    return ()=>{
      disconnect();
    }
  },[connect,disconnect]);

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