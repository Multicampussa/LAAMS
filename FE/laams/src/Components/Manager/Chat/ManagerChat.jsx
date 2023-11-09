import React, { useEffect, useState } from 'react'
import useApi from './../../../Hook/useApi';
import NoticeAll from './Notice/NoticeAll';
import NoticeRegion from './Notice/NoticeRegion';
import NoticeCenter from './Notice/NoticeCenter';
import NoticeNow from './Now/NoticeNow';
import Director from './Director/Director';

const ManagerChat = () => {
  const api = useApi();
  const [menu,setMenu] = useState("notice-all");
  const [noticeData,setNoticData] = useState();
  const [component,setComponent] = useState(null);

  useEffect(()=>{
    api.post("chat/room/notice")
      .then(({data})=>{
        setNoticData(data);
      })
      .catch(err=>console.log(err));
  },[api]);

  useEffect(()=>{
    if(!noticeData) return;
    switch(menu){
      case"notice-all":
        setComponent(<NoticeAll data={noticeData[menu]}/>);
        break;
      case"noticeByRegion":
        const regionData = {};
        noticeData[menu].forEach(e=>regionData[e.roomName]=e.roomId);
        setComponent(<NoticeRegion  data={regionData}/>);
        break;
      case"noticeByCenter":
        const centerData = {};
        noticeData[menu].forEach(e=>centerData[e.roomName]=e.roomId);
        setComponent(<NoticeCenter  data={centerData}/>);
        break;
      case"noticeForNow":
        setComponent(<NoticeNow  data={noticeData[menu]}/>);
        break;
      case"director":
        setComponent(<Director  data={noticeData[menu]}/>);
        break;
      default:
        break;
    }
  },[menu,noticeData]);

  return (
    <section className='director-chat'>
      <article className='director-chat-aside'>
        <div onClick={()=>setMenu("notice-all")} className={`director-chat-aside-menu${menu === "notice-all" ? "open":"close"}`}>전체 공지</div>
        <div onClick={()=>setMenu("noticeByRegion")} className={`director-chat-aside-menu${menu === "noticeByRegion" ? "open":"close"}`}>지역 공지</div>
        <div onClick={()=>setMenu("noticeByCenter")} className={`director-chat-aside-menu${menu === "noticeByCenter" ? "open":"close"}`}>센터 공지</div>
        <div onClick={()=>setMenu("noticeForNow")} className={`director-chat-aside-menu${menu === "noticeForNow" ? "open":"close"}`}>실시간 공지</div>
        <div onClick={()=>setMenu("director")} className={`director-chat-aside-menu${menu === "director" ? "open":"close"}`}>감독관</div>
      </article>
      <article className='director-chat-wrap'>{component}</article>
    </section>
  )
}

export default ManagerChat
