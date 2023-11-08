import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import useApi from '../../../Hook/useApi';
import { setDirectorRoom } from '../../../redux/actions/directorChatAction';

const Alram = () => {
  const dispatch = useDispatch();
  const socket = useRef(new SockJS(`${process.env.REACT_APP_SPRING_URL}/ws/chat`));
  const ws = useRef(new Stomp.over(socket.current));
  const reconnect = useRef(0);
  const alramMessage = useRef([]);
  const accessToken = useSelector(state=>state.User.accessToken);
  const [showAlram,setShowAlram] = useState(false);
  const authority = useSelector(state=>state.User.authority);
  const api = useApi();
  const alramItems = useMemo(()=>{
    let temp = [];
    switch(authority){
      case "ROLE_DIRECTOR":
        temp = alramMessage.current.filter(e=>!e.read && e.type!=="ENTER" && e.sender === "운영자");
        break;
      case "ROLE_MANAGER":
        temp = alramMessage.current.filter(e=>!e.read && e.type!=="ENTER"  && e.sender !== "운영자");
        break;
      default:
        break;
    }
    if(showAlram){}
    if(temp.length === 0){
      return <li className='header-bell-item' >알람이 없습니다</li>
    }else{
      return temp.map((e,idx)=><li className='header-bell-item' key={idx}>{e.sender}:{e.message}</li>);
    }
  },[showAlram,authority])

  const handleAlramBtn = useCallback(()=>{
    setShowAlram(e=>!e);
  },[]);

  const managerDisconnect = useCallback(()=>{
    ws.current.unsubscribe(`/topic/chat/room/alarm`);
  },[]);

  const directorDisconnect = useCallback(()=>{
    ws.current.unsubscribe(`/topic/chat/room/alarm`);
  },[]);

  const managerConnect = useCallback(()=>{
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, function(frame) {
      ws.current.subscribe(`/topic/chat/room/alarm`, function(message) {
        const recv = JSON.parse(message.body);
        recv.read = false;
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
    ws.current.connect({'Authorization': `Bearer ${accessToken}`}, async function(frame) {
      const {data} = await api.get("chat/rooms");
      if(data[0]===null){
        const res = await api.post("chat/room");
        if(res){
          data[0]=res.data.data;
          dispatch(setDirectorRoom(data));
          ws.current.subscribe(`/topic/chat/room/${res.data.data.roomId}`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
        }
      }else{
        dispatch(setDirectorRoom(data));
        for(let i = 0; i < data.length; i++){
          ws.current.subscribe(`/topic/chat/room/${data[i].roomId}`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
          ws.current.send(`/topic/chat/room/${data[i].roomId}`, function(message) {
            const recv = JSON.parse(message.body);
            console.log(recv);
          });
        }
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
  },[accessToken,api,dispatch]);

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
    switch(authority){
      case "ROLE_DIRECTOR":
        window.addEventListener("beforeunload",directorDisconnect);
        break;
      case "ROLE_MANAGER":
        window.addEventListener("beforeunload",managerDisconnect);
        break;
      default:
        break;
    }
  },[managerDisconnect,directorDisconnect,authority])

  return (
    <>
      <button onClick={handleAlramBtn} className='header-bell'>
        <div className='hidden-text'>BELL</div>
      </button>
      <ul className={`header-bell-box${showAlram?"open":"close"}`}>
        {alramItems}
        <button className='header-bell-btn'>비우기</button>
      </ul>
    </>
  )
}

export default Alram