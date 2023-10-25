import React, { useCallback, useState } from 'react'
import { useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom'

const Header = () => {
  const [menu,setMenu]=useState("close");
  const toggleMenu = useCallback(()=>{
    setMenu(menu==="open"?"close":"open")
  },[menu]);
  const user = useSelector(state=>state.User);
  const navigate = useNavigate();
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
        <Link to="/" className='header-menu-box-item'>공지사항</Link>
        <Link to="/" className='header-menu-box-item'>전체 시험 일정</Link>
        <Link to="/" className='header-menu-box-item'>보상</Link>
        <Link to="/" className='header-menu-box-item'>에러리포트</Link>
        <Link to="/" className='header-menu-box-item'>로그아웃</Link>
      </div>
    </>
  )
}

export default Header