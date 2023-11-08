import React, { useEffect, useState } from 'react'
import useApi from './../../../Hook/useApi';

const DirectorChat = () => {
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

  return (
    <section className='director-chat'>
      
    </section>
  )
}

export default DirectorChat
