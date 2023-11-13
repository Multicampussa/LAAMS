import React from 'react'
import styled from "styled-components";
import Header from '../../Components/Header/Header';
import Slider from '../../Components/Slider/Slider';
import Nav from '../../Components/Nav/Nav';
import Schedule from '../../Components/Schedule/Schedule';
import News from '../../Components/News/News';
import Notice from '../../Components/Notice/Notice';
import Footer from '../../Components/Footer/Footer';
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
      <Banner/>
      <Guide src='guide_pic.gif' alt=''/>
      <Footer/>
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
  width: 100%;
  height: 235px;
  margin-top: 10px;
  display: flex;
`

const Banner = styled.div`
  width: 100%;
  height: 172px;
  margin-top: 10px;
  background-color: skyblue;
`

const Guide = styled.img`
  margin-top: 10px;
  width: 100%;
`

export default Home