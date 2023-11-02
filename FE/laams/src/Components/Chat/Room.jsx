import React, { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom';
import useApi from '../../Hook/useApi';

const Room = () => {
  const [roomList,setRoomList] = useState();
  const api = useApi();
  useEffect(()=>{
    api.get("chat/rooms",{})
      .then(({data})=>{
        setRoomList(data);
      })
      .catch(err=>console.log(err.response))
  },[api]);
  const roomItems = useMemo(()=>{
    if(!roomList) return [];
    return roomList.map((e,idx)=><Link key={idx} to={`/room/${e.roomId}`}><li className='room-item'>{e.roomName}</li></Link>)  },[roomList])
  return (
    <div className='flex-col'>
     <h1>채팅방</h1>
     <ul className='flex-col'>
      {
        roomItems
      }
     </ul>
    </div>
  )
}

export default Room