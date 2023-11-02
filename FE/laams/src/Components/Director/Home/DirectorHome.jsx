import React, { useEffect } from 'react'
import useCreateRoom from '../../../Hook/useCreateRoom'
import { useSelector } from 'react-redux';
const DirectorHome = () => {
  const createRoom = useCreateRoom();
  const accessToken = useSelector(state=>state.User.accessToken);
  useEffect(()=>{
    if(!accessToken) return;
    createRoom();
  },[createRoom,accessToken])
  return (
    <div>
      DirectorHome
    </div>
  )
}

export default DirectorHome