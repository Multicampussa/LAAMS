import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import {  useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useApi from '../../../Hook/useApi';

const Alarm = () => {
  const directorRoom = useRef();
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const accessToken = useSelector(state=>state.User.accessToken);
  const [alarmMessage,setAlarmMessage] = useState([]);
  const [showAlarm,setShowAlarm] = useState(false);
  const authority = useSelector(state=>state.User.authority);
  const api = useApi();
  const directorChat = useSelector(state=>state.DirectorChat.chat);
  const alarmItems = useMemo(()=>{
    let temp = [];
    switch(authority){
      case "ROLE_DIRECTOR":
        temp = alarmMessage.filter(e=>!e.read && e.type!=="ENTER" && e.sender === "운영자");
        break;
      case "ROLE_MANAGER":
        temp = alarmMessage.filter(e=>!e.read && e.type!=="ENTER"  && e.sender !== "운영자");
        break;
      default:
        break;
    }
    if(temp.length === 0){
      return <li className='header-bell-item' >알람이 없습니다</li>
    }else{
      return temp.map((e,idx)=><li className='header-bell-item' key={idx}>{e.sender}:{e.message}</li>);
    }
  },[authority,alarmMessage])

  const handleAlarmBtn = useCallback(()=>{
    setShowAlarm(e=>!e);
  },[]);

  const managerConnect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/alarm`, function(message) {
        const recv = JSON.parse(message.body);
        recv.read = false;
        // alarmMessage.push(recv);
        setAlarmMessage(e=>[...e,recv]);
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


  //TODO : 감독관 채팅입력시 소켓으로 전달
  useEffect(()=>{
    if(!directorChat || directorChat==="" || !directorRoom.current || directorRoom.current[0] === null) return;
    ws.current.send("/app/chat/message", {'Authorization': `Bearer ${accessToken}`}, JSON.stringify({type:'TALK', roomId:directorRoom.current[0].roomId, sender:directorRoom.current[0].roomName, roomName:directorRoom.current[0].roomName, message:directorChat}));
  },[directorChat,accessToken]);


  //TODO : 감독관 소켓 연결
  const directorConnect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, async function(frame) {
      const {data} = await api.get("chat/rooms");
      if(data[0]===null){
        const res = await api.post("chat/room");
        if(res){
          data[0]=res.data.data;
          directorRoom.current = data;
          ws.current.subscribe(`/topic/chat/room/${res.data.data.roomId}`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
          ws.current.subscribe(`/topic/chat/room/notice-all`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
          ws.current.subscribe(`/topic/chat/room/notice-${data[2]?.roomName}`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
          ws.current.subscribe(`/topic/chat/room/notice-${data[3]?.roomName}`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
        }
      }else{
        directorRoom.current = data;
        ws.current.subscribe(`/topic/chat/room/${data[0]?.roomId}`, function(message) {
          const recv = JSON.parse(message.body);
          console.log(recv);
        });
        ws.current.subscribe(`/topic/chat/room/notice-all`, function(message) {
          const recv = JSON.parse(message.body);
          console.log(recv);
        });
        ws.current.subscribe(`/topic/chat/room/notice-${data[2]?.roomName}`, function(message) {
          const recv = JSON.parse(message.body);
          console.log(recv);
        });
        ws.current.subscribe(`/topic/chat/room/notice-${data[3]?.roomName}`, function(message) {
          const recv = JSON.parse(message.body);
          console.log(recv);
        });
      }
    }, function(error) {
      if(reconnect.current++ <= 5) {
        const timer = setTimeout(function() {
          socket.current = new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`);
          ws.current = Stomp.over(socket.current);
          directorConnect();
        },100);
        window.addEventListener("beforeunload",()=>{
          clearTimeout(timer);
        });
      }
    });
  },[accessToken,api]);

  useEffect(()=>{
    switch(authority){
      case "ROLE_DIRECTOR":
        directorConnect();
        break;
      case "ROLE_MANAGER":
        managerConnect();
        break;
      default:
        break;
    }
  },[managerConnect,directorConnect,authority]);

  useEffect(()=>{
    return ()=>{
      ws.current.disconnect();
    }
  },[authority])

  const handleCleanBtn = useCallback(()=>{
    setAlarmMessage([]);
  },[]);

  return (
    <>
      <button onClick={handleAlarmBtn} className='header-bell'>
        <div className='hidden-text'>BELL</div>
      </button>
      <ul className={`header-bell-box${showAlarm?"open":"close"}`}>
        {alarmItems}
        <button onClick={handleCleanBtn} className='header-bell-btn'>비우기</button>
      </ul>
    </>
  )
}

export default Alarm