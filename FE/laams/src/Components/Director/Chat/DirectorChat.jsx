import React, { useCallback, useState } from 'react'
import { useDispatch } from 'react-redux';
import { setDirectorChat } from './../../../redux/actions/directorChatAction';

const DirectorChat = () => {
  const [menu,setMenu] = useState("All");
  const [chat,setChat] = useState("");
  const dispatch = useDispatch();
  const handleChat = useCallback((e)=>{
    setChat(e.target.value);
  },[]);
  const handleSend = useCallback(()=>{
    dispatch(setDirectorChat(chat));
  },[chat,dispatch]);
  return (
    <section className='director-chat'>
      <input value={chat} onChange={handleChat}/><button onClick={handleSend}>입력</button>
    </section>
  )
}

export default DirectorChat
