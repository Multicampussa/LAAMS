import React, { useCallback, useEffect, useRef, useState } from 'react'
import { useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';

const Alram = () => {
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const [alramMessage,] = useState([]);
  const accessToken = useSelector(state=>state.User.accessToken);

  const disconnect = useCallback(()=>{
    ws.current.unsubscribe(`/topic/chat/room/alarm`);
  },[]);

  const connect = useCallback(() =>{
    // pub/sub event
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/alarm`, function(message) {
        const recv = JSON.parse(message.body);
        alramMessage.push(recv);
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
    <button onClick={()=>{console.log(alramMessage)}} className='header-bell'>
      <div className='hidden-text'>BELL</div>
    </button>
  )
}

export default Alram