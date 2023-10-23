import React, { useCallback, useState } from 'react'
import { Link } from 'react-router-dom'

const Header = () => {
  const [menu,setMenu]=useState("close");
  const toggleMenu = useCallback(()=>{
    setMenu(menu==="open"?"close":"open")
  },[menu]);
  return (
    <>
      <header className='header'>
        <Link className='header-home' to="/"><div className='hidden-text'>LAAMS</div></Link>
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