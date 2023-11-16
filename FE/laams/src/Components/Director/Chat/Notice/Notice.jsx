import React,  { useCallback,useState, useEffect,useRef,useMemo}  from 'react'
import { useSelector } from 'react-redux';

const Notice = ({menu,data}) => {
  const chatBox = useRef();
  const [messageList,setMessageList] = useState([]);
  const regionChat = useSelector(state=>state.DirectorChat.region);
  const centerChat = useSelector(state=>state.DirectorChat.center);
  const allChat = useSelector(state=>state.DirectorChat.all);

  const setAllChat = useCallback(()=>{
    setMessageList(allChat);
  },[allChat]);

  const setRegionChat = useCallback(()=>{
    setMessageList(regionChat);
  },[regionChat]);

  const setCenterChat = useCallback(()=>{
    setMessageList(centerChat);
  },[centerChat]);

  useEffect(()=>{
    switch(menu){
      case "notice-all":
        setAllChat();
        break;
      case "noticeByRegion":
        setRegionChat();
        break;
      case "noticeByCenter":
        setCenterChat();
        break;
      default:
        break;
    }
  },[menu,setAllChat,setRegionChat,setCenterChat]);

  //TODO : 메시지 Div 생성
  const messageItems = useMemo(()=>{
    return messageList.map((e,idx)=><li key={idx} className='manager-chat-noticeitem'>
      <div className='manager-chat-noticeitem-sender'>{e.sender}</div>
      <div className='manager-chat-noticeitem-message'>{e.message}</div>
    </li>);
  },[messageList]);

  useEffect(()=>{
    chatBox.current.scrollTop = chatBox.current.scrollHeight;
  },[messageItems])

  return (
    <article className='manager-chat-noticeall'>
      <ul ref={chatBox} className='manager-chat-box'>
        {
          messageItems
        }
      </ul>
    </article>
  )
}

export default Notice