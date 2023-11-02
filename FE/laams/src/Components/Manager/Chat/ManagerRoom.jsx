import React, { useEffect, useMemo, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { Link } from 'react-router-dom';

const ManagerRoom = () => {
  const api = useApi();
  const [roomData,setRoomData] = useState([]);
  useEffect(()=>{
    api.get("chat/rooms")
    .then(({data})=>{
      setRoomData(data);
    })
    .catch(err=>{console.log(err);});
  },[api]);

  const roomItems = useMemo(()=>{
    return roomData.map((e,idx)=><Link key={idx} to="/manager/chat" state={e}><li>{e.roomName}</li></Link>)
  },[roomData])

  return (
    <div className='flex-col'>
      <h1>채팅방</h1>
      <ul>
        {
          roomItems
        }
      </ul>
    </div>
  )
}

export default ManagerRoom