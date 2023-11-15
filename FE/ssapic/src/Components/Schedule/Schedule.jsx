import React, { useState } from 'react'
import { styled } from 'styled-components'

const Schedule = ({setIsShowModal}) => {
  const [mode,setMode] = useState("SSAPIc L&R")
  return (
    <Wrap>
      <Header>
        <LinkBox>
          <Link $url={"btn_calculator.png"} />
          <Link $url={"btn_sche_all.gif"}/>
        </LinkBox>
      </Header>
      <Container>
        <TabBox>
          <Tab onClick={()=>setMode("SSAPIc L&R")} $isActive = {mode === "SSAPIc L&R"}>SSAPIc L&R</Tab>
          <Tab onClick={()=>setMode("SSAPIc Listening")} $isActive = {mode === "SSAPIc Listening"}>SSAPIc Listening</Tab>
          <Tab onClick={()=>setMode("SSAPIc Writing")} $isActive = {mode === "SSAPIc Writing"}>SSAPIc Writing</Tab>
        </TabBox>
        <TitleBox>
          <Title>마감일</Title>
          <Title>접수기간</Title>
          <Title>시험일</Title>
          <Title>성적발표일</Title>
          <Title>접수</Title>
        </TitleBox>
        <ExamBox>
          <Exam>
            <ExamItem1>마감일</ExamItem1>
            <ExamItem2>{`2023.10.14(토) ~ 2023.11.13(월)`}</ExamItem2>
            <ExamItem3>{`2023.11.15(수)`}</ExamItem3>
            <ExamItem4>{`2023.11.20(월)`}</ExamItem4>
            <ExamItem5><ExamApplyBtn onClick={()=>setIsShowModal(true)}><div className='hidden_txt'>신청하기</div></ExamApplyBtn></ExamItem5>
          </Exam>
          <Exam>
            <ExamItem1>D-1</ExamItem1>
            <ExamItem2>{`2023.10.14(토) ~ 2023.11.13(월)`}</ExamItem2>
            <ExamItem3>{`2023.11.15(수)`}</ExamItem3>
            <ExamItem4>{`2023.11.20(월)`}</ExamItem4>
            <ExamItem5><ExamApplyBtn onClick={()=>setIsShowModal(true)}><div className='hidden_txt'>신청하기</div></ExamApplyBtn></ExamItem5>
          </Exam>
          <Exam>
            <ExamItem1>D-2</ExamItem1>
            <ExamItem2>{`2023.10.14(토) ~ 2023.11.13(월)`}</ExamItem2>
            <ExamItem3>{`2023.11.15(수)`}</ExamItem3>
            <ExamItem4>{`2023.11.20(월)`}</ExamItem4>
            <ExamItem5><ExamApplyBtn onClick={()=>setIsShowModal(true)}><div className='hidden_txt'>신청하기</div></ExamApplyBtn></ExamItem5>
          </Exam>
          <Exam>
            <ExamItem1>D-3</ExamItem1>
            <ExamItem2>{`2023.10.14(토) ~ 2023.11.13(월)`}</ExamItem2>
            <ExamItem3>{`2023.11.15(수)`}</ExamItem3>
            <ExamItem4>{`2023.11.20(월)`}</ExamItem4>
            <ExamItem5><ExamApplyBtn onClick={()=>setIsShowModal(true)}><div className='hidden_txt'>신청하기</div></ExamApplyBtn></ExamItem5>
          </Exam>
          <Exam>
            <ExamItem1>D-4</ExamItem1>
            <ExamItem2>{`2023.10.14(토) ~ 2023.11.13(월)`}</ExamItem2>
            <ExamItem3>{`2023.11.15(수)`}</ExamItem3>
            <ExamItem4>{`2023.11.20(월)`}</ExamItem4>
            <ExamItem5><ExamApplyBtn onClick={()=>setIsShowModal(true)}><div className='hidden_txt'>신청하기</div></ExamApplyBtn></ExamItem5>
          </Exam>
        </ExamBox>
        <BtnScheMore/>
      </Container>
    </Wrap>
  )
}

const Wrap = styled.section`
  margin: 0 auto;
  width: 980px;
  height: 386px;
  box-sizing: border-box;
  border: 4px solid #3185d0;
  border-top: 0;
  position: relative;
`

const Header = styled.header`
  position: relative;
  width: 100%;
  height: 72px;
  background: #3185d0;
  background-image: url("sche_tit.gif");
  background-repeat: no-repeat;
  background-position: 7px 0px;
`

const LinkBox = styled.ul`
  position: absolute;
  right: 20px;
  top: 0;
  display: flex;
  align-items: center;
  height: 100%;
  gap: 5px;
`
const Link = styled.li`
  width : 132px;
  height: 30px;
  background-image: url(${props=>props.$url});
  cursor: pointer;
`
const Container = styled.article`
  box-sizing: border-box;
  padding: 10px;
  width: 100%;
  height: 314px;
`

const TabBox = styled.ul`
  display: flex;
  height: 35px;
`
const Tab = styled.li`
  width: 150px;
  height: 100%;
  line-height: 35px;
  box-sizing: border-box;
  border: 1px solid ${props=>props.$isActive?"#4ca3f5":"#d5d7e1"};
  text-align: center;
  font-weight: 900;
  font-size: 16px;
  color: ${props=>props.$isActive?"#ffffff":"#000000"};
  background-color: ${props=>props.$isActive?"#4ca3f5":"transparent"};
  cursor: pointer;
`
const TitleBox = styled.ul`
  width: 100%;
  height: 35px;
  display: grid;
  grid-template-columns: 1fr 3fr 2fr 3fr 2fr;
  background-color: #dbecfb;
`

const Title = styled.li`
  font-size: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #4ca3f5;
  &::before,&::after{
    display: block;
    content: "";
    width: 1px;
    height: 20px;
    background-color: #c0c0c0;
  }
  &:first-child:before{
    background-color :transparent;
  }
  &:last-child::after{
    background-color :transparent;
  }
`
const ExamBox = styled.ul`
  width: 100%;
  height: 193px;
  overflow-y: auto;
`
const Exam = styled.li`
  display: grid;
  grid-template-columns: 1fr 3fr 2fr 3fr 2fr;
  text-align: center;
  width: 100%;
  height: 48px;
  line-height: 48px;
  box-sizing: border-box;
  border-bottom: 1px solid #d8dbe1;
  border-top: 1px solid #d8dbe1;
`

const ExamItem1 = styled.div`
  background-image: url("ico_dday.gif");
  background-repeat: no-repeat;
  background-position: center;
  font-weight: 900;
  font-size: 14px;
`
const ExamItem2 = styled.div`
  font-size: 14px;
`
const ExamItem3 = styled.div`
  font-size: 14px;
  color: #296ebc;
  font-weight: 900;
`
const ExamItem4 = styled.div`
  font-size: 14px;
  font-weight: 900;
`
const ExamItem5 = styled.div`
  width: 100%;
  height: 100%;
  line-height: 40px;
`
const ExamApplyBtn = styled.button`
  background-image: url("btn_apply.gif");
  background-repeat: no-repeat;
  background-position: center;
  cursor: pointer;
  width: 84px;
  height: 24px;
`

const BtnScheMore = styled.button`
  position: absolute;
  background-image: url("btn_sche_more.gif");
  bottom: 10px;
  right: 10px;
  width: 88px;
  height: 24px;
`
export default Schedule