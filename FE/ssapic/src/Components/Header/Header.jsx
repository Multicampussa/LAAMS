import React from 'react'
import { styled } from 'styled-components'
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <Wrap>
      <Link to="/">
        <Logo src="/logo.svg" alt='홈'/>
      </Link>
      <Links>
        <div>회원가입</div>
        <div>&nbsp;|&nbsp;</div>
        <div>로그아웃</div>
        <div>&nbsp;|&nbsp;</div>
        <div>FAQ</div>
        <div>&nbsp;|&nbsp;</div>
        <div>1:1메일문의</div>
        <div>&nbsp;|&nbsp;</div>
        <div>사이트맵</div>
      </Links>
      <Gnb>
        <div>시험접수</div>
        <div>&nbsp;|&nbsp;</div>
        <div>성적확인</div>
        <div>&nbsp;|&nbsp;</div>
        <div>시험소개</div>
        <div>&nbsp;|&nbsp;</div>
        <div>수험자가이드</div>
        <div>&nbsp;|&nbsp;</div>
        <div>시험대비</div>
      </Gnb>
    </Wrap>
  )
}

const Wrap = styled.header`
  margin: 0 auto;
  width: 980px;
  height: 96px;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
`

const Logo = styled.img`
  width: 170px;
`

const Links = styled.div`
  height: 56px;
  width: 810px;
  flex-shrink: 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 12px;
  font-weight: 900;
  &>div:first-child{
    color: #8CB4DB;
  }
`

const Gnb = styled.nav`
  height: 40px;
  width: 810px;
  flex-shrink: 0;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  font-size: 15px;
  font-weight: 900;
`

export default Header