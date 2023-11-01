import React, { useCallback, useState } from 'react'
import { useMemo } from 'react';
import { useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom'

const Header = () => {
  const [menu,setMenu]=useState("close");
  const toggleMenu = useCallback(()=>{
    setMenu(menu==="open"?"close":"open")
  },[menu]);
  const user = useSelector(state=>state.User);
  const navigate = useNavigate();
  const handleMenuItem = useCallback(()=>{
    setMenu("close");
  },[])
  const handleHome = useCallback(()=>{
    switch(user.authority){
      case "ROLE_DIRECTOR":
        navigate("/director");
      break;
      case "ROLE_MANAGER":
        navigate("/manager");
      break;
      default:
        navigate("/",{replace:true});
        break;
    }
  },[navigate,user]);

  const menuItems = useMemo(()=>{
    let res = [];
    switch(user.authority){
      case "ROLE_DIRECTOR":
        res = [
        <Link onClick={handleMenuItem} to="/notice" className='header-menu-box-item'>공지사항</Link>,
        <Link onClick={handleMenuItem} to="/" className='header-menu-box-item'>전체 시험 일정</Link>,
        <Link onClick={handleMenuItem} to="/" className='header-menu-box-item'>보상</Link>,
        <Link onClick={handleMenuItem} to="/" className='header-menu-box-item'>에러리포트</Link>,
        <Link onClick={handleMenuItem} to="/update/user" className='header-menu-box-item'>회원 정보</Link>,
        <Link onClick={handleMenuItem} to="/test" className='header-menu-box-item'>로그아웃</Link>,
        <Link onClick={handleMenuItem} to='/director/exam/1' className='header-menu-box-item'>시험 상세</Link>];
        break;
      case "ROLE_MANAGER":
        res = [
          <Link onClick={handleMenuItem} to="/notice" className='header-menu-box-item'>공지사항</Link>,
          <Link onClick={handleMenuItem} to="/manager/chart" className='header-menu-box-item'>차트</Link>,
          <Link onClick={handleMenuItem} to="/manager/reward" className='header-menu-box-item'>보상</Link>,
          <Link onClick={handleMenuItem} to="/manager/error-report" className='header-menu-box-item'>에러리포트</Link>,
          <Link onClick={handleMenuItem} to="/test" className='header-menu-box-item'>로그아웃</Link>
        ];
        break;
      default:
        res = [
          <Link onClick={handleMenuItem} to="/notice" className='header-menu-box-item'>공지사항</Link>,
          <Link onClick={handleMenuItem} to="/test" className='header-menu-box-item'>로그아웃</Link>
        ]
        break;
    }
    return res;
  },[user])

  return (
    <>
      <header className='header'>
        <button onClick={handleHome} className='header-home'><div className='hidden-text'>Home</div></button>
        <div className='header-btn'>
          <button onClick={toggleMenu} className={`header-menu-${menu}`}>
            <span></span>
            <div className='hidden-text'>MENU</div>
          </button>
          <button className='header-bell'>
            <div className='hidden-text'>BELL</div>
          </button>
        </div>
      </header>
      <div className={`header-menu-box-${menu}`}>
        {
          menuItems
        }
      </div>
    </>
  )
}

export default Header