import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Header from './Header';
import Modal from './Modal/Modal';

const PrivateRoute = ({role, children }) => {
  const user = useSelector(state=>state.User);
  const navigate = useNavigate();
  
  //TODO : 권한을 확인
  useEffect(()=>{
    if (!user.accessToken || !user.accessTokenExpireTime) {
      // alert("로그인해주세요!");
      // navigate("/",{replace:true})
    }else{
      if(!user.authority) {
        alert("토큰이 만료되었거나 권한이 설정되지 않았습니다!");
        navigate("/",{replace:true});
      }
      let isAllow = false;
      switch(role){
        case "director":
          if(user.authority==="ROLE_DIRECTOR"){
            isAllow=true;
          }
          break;
        case "manager":
          if(user.authority==="ROLE_MANAGER"){
            isAllow=true;
          }
          break;
        default:
          break;
      }
      if(!isAllow){
        alert("권한이 없습니다!");
        navigate("/",{replace:true});
      }
    }

  },[user,navigate,role])

  return (
    <>
      <Header></Header>
      <main>{children}</main>
      <Modal></Modal>
    </>
  );
};

export default PrivateRoute;