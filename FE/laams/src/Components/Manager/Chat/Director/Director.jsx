import React from 'react'
import { useEffect } from 'react'
import useApi from './../../../../Hook/useApi';

const Director = () => {
  const api = useApi();
  useEffect(()=>{
    api.get("chat/rooms")
      .then(({data})=>{
        console.log(data);
      })
  },[])
  return (
    <div>Director</div>
  )
}

export default Director