import React, { useCallback, useState } from 'react'
import { useEffect } from 'react';
import useApi from '../../../Hook/useApi';
import Notice from './Notice/Notice';
import Chat from './Chat';

const DirectorChat = () => {
  const [menu,setMenu] = useState(null);
  const [roomData,setRoomData] = useState([]);
  const api = useApi();
  const [main,setMain] = useState(null);

  useEffect(()=>{
    api.get("chat/rooms")
      .then(({data})=>{
        setRoomData(data);
        setMenu("notice-all");
      }).catch(err=>console.log(err));
  },[api]);

  useEffect(()=>{
    switch(menu){
      default:
        setMain(null);
        break;
      case "director":
        setMain(<Chat data={roomData[0]}/>);
        break;
      case "notice-all":
      case "noticeByRegion":
      case "noticeByCenter":
        setMain(<Notice menu={menu} data={roomData}/>);
        break;
    }
  },[menu,roomData])

  return (
    <section className='director-chat'>
      <article className='director-chat-aside'>
        <div onClick={()=>setMenu("notice-all")} className={`director-chat-aside-menu${menu === "notice-all" ? "open":"close"}`}>전체 공지</div>
        <div onClick={()=>setMenu("noticeByRegion")} className={`director-chat-aside-menu${menu === "noticeByRegion" ? "open":"close"}`}>지역 공지</div>
        <div onClick={()=>setMenu("noticeByCenter")} className={`director-chat-aside-menu${menu === "noticeByCenter" ? "open":"close"}`}>센터 공지</div>
        {/* <div onClick={()=>setMenu("noticeForNow")} className={`director-chat-aside-menu${menu === "noticeForNow" ? "open":"close"}`}>실시간 공지</div> */}
        <div onClick={()=>{setMenu("director")}} className={`director-chat-aside-menu${menu === "director" ? "open":"close"}`}>실시간 문의</div>
      </article>
      <article className='director-chat-wrap'>
        {main}
      </article>
    </section>
  )
}

export default DirectorChat
