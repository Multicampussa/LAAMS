import React from 'react'
import styled from "styled-components";
import Header from '../../Components/Header/Header';
import Slider from '../../Components/Slider/Slider';
import Nav from '../../Components/Nav/Nav';
import Schedule from '../../Components/Schedule/Schedule';
import News from '../../Components/News/News';
import Notice from '../../Components/Notice/Notice';
const Home = () => {
  return (
    <Wrap>
      <Header/>
      <Slider/>
      <Nav/>
      <Area1>
        <Schedule/>
      </Area1>
      <Area2>
        <News/>
        <Notice/>
      </Area2>
      <Banner>
        <img src='banner1.jpg' alt='배너1' />
        <img src='banner-main.png' alt='배너2' />
        <img src='new-link1.jpg' alt='배너3' />
        <img src='new-link2.jpg' alt='배너2' />
      </Banner>
      <GuideBox>
        <GuideTitle src='guide_tit.gif' />
        <Guide src='guide_pic.gif' alt='가이드'/>
      </GuideBox>
    </Wrap>
  )
}

const Wrap = styled.div`
  width: 100%;
  min-height: 100%;
`

const Area1 = styled.div`
  width: 100%;
  margin: 30px auto 0;
`
const Area2 = styled.div`
  margin: 0 auto;
  width: 980px;
  height: 235px;
  margin-top: 10px;
  display: flex;
`

const Banner = styled.div`
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 1fr;
  width: 980px;
  height: 172px;
  margin-top: 10px;
`

const GuideBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f5f5f9;
  padding: 28px 0 24px;
  gap: 8px;
  margin-top: 20px;
`

const Guide = styled.img`
  margin-top: 10px;
  width: 980px;
`
const GuideTitle = styled.img`
  
`

export default Home