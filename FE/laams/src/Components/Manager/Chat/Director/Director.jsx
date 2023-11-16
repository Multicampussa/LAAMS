import React from 'react'
import { useEffect } from 'react'
import useApi from './../../../../Hook/useApi';
import { useCallback } from 'react';
import { useState } from 'react';
import { useMemo } from 'react';
import DirectorChat from './DirectorChat';
import useCenter from '../../../../Hook/useCenter';

const Director = ({reset}) => {
  const api = useApi();
  const [room,setRoom] = useState([]);
  const [isNow,] = useState(false);
  const [directorId,] = useState("");
  const [main,setMain]=useState(null);
  const centerData = useCenter();
  const [center,setCenter] = useState();
  const [region,setRegion] = useState("전국");

  useEffect(()=>{
    if(reset){}
    setMain(null);
  },[reset])

  const searchRoom = useCallback(()=>{
    let url = `chat/rooms?isNow=${isNow}`;
    if(center){
      url += `&centerName=${center}`;
    }
    if(directorId&&directorId!==""){
    url += `&directorId=${directorId}`;
    }
    api.get(url)
      .then(({data})=>{
        setRoom(data.filter(e=>e.roomType==="private"));
      })
  },[api,isNow,directorId,center]);

  useEffect(()=>{searchRoom();},[searchRoom]);

  const handleRoomItem = useCallback((payload)=>{
    setMain(<DirectorChat room={payload}/>);
  },[]);

  const roomItems = useMemo(()=>{
    return room.map((e,idx)=><li className='manager-chat-room' onClick={()=>handleRoomItem(e)} key={idx}>{e.roomName}</li>)
  },[room,handleRoomItem])

  useEffect(()=>{
    if(!centerData||!region||region==="전국") {
      setCenter(null);
      return;
    }
    setCenter(centerData[region][0].centerName);
  },[centerData,region])

  //TODO : 지역Option 생성
  const regionItem = useMemo(()=>{
    if(!centerData)return[];
    return Object.keys(centerData).reverse().map((e,idx)=><option key={idx}>
      {e}
    </option>)
  },[centerData]);

  //TODO : 센터Option 생성
  const centerItem = useMemo(()=>{
    if(!centerData||!region)return[];
    if(region==="전국"){
      return <option>전국</option>
    }
    return centerData[region].map((e,idx)=><option key={idx}>
      {e.centerName}
    </option>)
  },[centerData,region]);

  return (
    <>
      {
        main?main:
        <div className='manager-chat-room-wrap'>
          <div className='flex-row'>
            <select onChange={e=>setRegion(e.target.value)}>
              <option>전국</option>
              {regionItem}
            </select>
            <select onChange={e=>setCenter(e.target.value)}>
              {centerItem}
            </select>
          </div>
          <ul className='manager-chat-room-box'>
            {roomItems}
          </ul>
        </div>
      }
    </>
  )
}

export default Director