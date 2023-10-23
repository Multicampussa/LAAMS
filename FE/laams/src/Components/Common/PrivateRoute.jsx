import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Header from './Header';
import Modal from './Modal/Modal';

const PrivateRoute = ({ children }) => {
  const user = useSelector(state=>state.User);
  const navigate = useNavigate();
  useEffect(()=>{
    if (!user.accessToken || !user.accessTokenExpireTime) {
      alert("로그인해주세요!");
      navigate("/",{replace:true})
    }
  },[user,navigate])

  return (
    <>
      <Header></Header>
      <main>{children}</main>
      <Modal></Modal>
    </>
  );
};

export default PrivateRoute;