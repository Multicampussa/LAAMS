import React, { useEffect, useState } from 'react'
import useApi from './../../../Hook/useApi';

const ManagerChat = () => {
  const api = useApi();
  const [menu,setMenu] = useState("All");
  const [noticeAll,setNoticeAll] = useState();
  const [noticeRegion,setNoticeRegion] = useState();
  const [noticeCenter,setNoticeCenter]  = useState();
  const [noticeNow,setNoticeNow] = useState();
  useEffect(()=>{
    api.post("chat/room/notice")
      .then(({data})=>{
        setNoticeAll(data["notice-all"]);
        setNoticeRegion(data["noticeByRegion"]);
        setNoticeCenter(data["noticeByCenter"]);
        setNoticeNow(data["noticeForNow"]);
      })
      .catch(err=>console.log(err));
  },[api]);

  useEffect(()=>{
    console.log(menu);
  },[menu]);

  return (
    <section className='director-chat'>
      <article className='director-chat-aside'>
        <div onClick={()=>setMenu("All")} className={`director-chat-aside-menu${menu === "All" ? "open":"close"}`}>전체 공지</div>
        <div onClick={()=>setMenu("Region")} className={`director-chat-aside-menu${menu === "Region" ? "open":"close"}`}>지역 공지</div>
        <div onClick={()=>setMenu("Center")} className={`director-chat-aside-menu${menu === "Center" ? "open":"close"}`}>센터 공지</div>
        <div onClick={()=>setMenu("Now")} className={`director-chat-aside-menu${menu === "Now" ? "open":"close"}`}>실시간 감독관 공지</div>
      </article>
      <article className='director-chat-wrap'></article>
    </section>
  )
}

export default ManagerChat
