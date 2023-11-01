import React, { useCallback, useState } from 'react'
import { useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom'
import { setUserClear } from '../../redux/actions/userAction';

const Header = () => {
  const dispatch = useDispatch();
  const [menu,setMenu]=useState("close");
  const toggleMenu = useCallback(()=>{
    setMenu(menu==="open"?"close":"open")
  },[menu]);
  const user = useSelector(state=>state.User);
  const navigate = useNavigate();
  const logout = useCallback(()=>{
    localStorage.clear();
    dispatch(setUserClear(null));
    navigate("/",{replace:true});
  },[dispatch]);
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
    let key = 0;
    switch(user.authority){
      case "ROLE_DIRECTOR":
        res = [
        <Link key={key++} onClick={handleMenuItem} to="/notice" className='header-menu-box-item'>공지사항</Link>,
        <Link key={key++} onClick={handleMenuItem} to="/" className='header-menu-box-item'>전체 시험 일정</Link>,
        <Link key={key++} onClick={handleMenuItem} to="/" className='header-menu-box-item'>보상</Link>,
        <Link key={key++} onClick={handleMenuItem} to="/" className='header-menu-box-item'>에러리포트</Link>,
        <Link key={key++} onClick={handleMenuItem} to="/update/user" className='header-menu-box-item'>회원 정보</Link>,
        <Link key={key++} onClick={handleMenuItem} to='/director/exam/1' className='header-menu-box-item'>시험 상세</Link>,
        <button key={key++} onClick={logout} className='header-menu-box-item'>로그아웃</button>
        ];
        break;
      case "ROLE_MANAGER":
        res = [
          <Link key={key++} onClick={handleMenuItem} to="/notice" className='header-menu-box-item'>공지사항</Link>,
          <Link key={key++} onClick={handleMenuItem} to="/manager/chart" className='header-menu-box-item'>차트</Link>,
          <Link key={key++} onClick={handleMenuItem} to="/manager/reward" className='header-menu-box-item'>보상</Link>,
          <Link key={key++} onClick={handleMenuItem} to="/manager/error-report" className='header-menu-box-item'>에러리포트</Link>,
          <button key={key++} onClick={logout} className='header-menu-box-item'>로그아웃</button>
        ];
        break;
      default:
        res = [
          <Link key={key++}  onClick={handleMenuItem} to="/notice" className='header-menu-box-item'>공지사항</Link>,
          <button key={key++} onClick={logout} className='header-menu-box-item'>로그아웃</button>
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