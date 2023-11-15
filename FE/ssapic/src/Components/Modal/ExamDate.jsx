import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { styled } from 'styled-components';
import ExamCurrent from './ExamCurrent';

const ExamDate = ({setIsShowModal,setData,data,setMain}) => {

  const handleBtnNext = useCallback(()=>{
  },[]);

  return (
    <Wrap>
      <TitleBox>
        <Title>SSAPIc 시험신청</Title>
        <BtnClose onClick={()=>setIsShowModal(false)}>X</BtnClose>
      </TitleBox>
      <MainContainer>
        <CenterSelectBtn>
          <LayoutBox/>
          <p>센터선택</p>
        </CenterSelectBtn>
        <DateSelectBtn>
          <LayoutBox/>
          <p>날짜선택</p>
        </DateSelectBtn>
        <MainArea>
          <Header>날짜선택</Header>
          <RegionArea></RegionArea>
          <CenterArea></CenterArea>
        </MainArea>
        <ExamCurrent data={data} />
      </MainContainer>
      <Hr/>
      <Btn onClick={handleBtnNext}>다음</Btn>
    </Wrap>
  )
}
const Wrap = styled.section`
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  width: 100%;
  height: 100%;
  & div{
    flex-shrink: 0;
  }
`
const TitleBox = styled.div`
  width: 100%;
  height: 70px;
  background-color: #296ebc;
  position: relative;
`

const Title = styled.h1`
  height: 70px;
  line-height: 70px;
  font-size: 26px;
  color: #FFF;
  text-align: center;
`
const BtnClose = styled.button`
  position: absolute;
  color: #FFFFFF;
  width: 70px;
  height: 70px;
  top: 0;
  right: 0;
  font-size: 50px;
`
const Btn = styled.button`
  font-size: 21px;
  display: block;
  width: 90%;
  margin: 10px 5% 10px 5%;
  background-color: #3fa0ee;
  text-align: center;
  font-size: 16px;
  font-weight: 900;
  height: 40px;
  color: #ffffff;
  cursor: pointer;
`

const MainContainer = styled.div`
  width: 100%;
  flex: 1;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
`

const Hr = styled.hr`
  background-color: #296ebc;
  width: 100%;
  height: 1px;
  border: 0;
  margin: 0;
`

const CenterSelectBtn = styled.button`
  width: 25%;
  height: 50%;
  background-image: url("ap_place2.png");
  background-position: center;
  background-repeat: no-repeat;
  -webkit-background-size: 69px;
  background-size: 48px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 20px;
`

const DateSelectBtn = styled.button`
  width: 25%;
  height: 50%;
  background-image: url("ap_calendar.png");
  background-position: center;
  background-repeat: no-repeat;
  -webkit-background-size: 69px;
  background-size: 69px;
  background-color: #e7e7e7;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 20px;
`

const LayoutBox = styled.div`
  width: 100%;
  height: 120px;
`

const MainArea = styled.div`
  position: relative;
  width: 50%;
  height: 100%;
  border: 1px solid #dddddd;
`
const CenterArea = styled.ul`
  position: absolute;
  top: 60px;
  left: 50%;
  width: 50%;
  height: calc(100% - 60px);
  overflow-y: auto;
  box-sizing: border-box;
  &::-webkit-scrollbar {
    width: 8px;  /* 스크롤바의 너비 */
  }
  &::-webkit-scrollbar-thumb {
    background: #3fa0ee; /* 스크롤바의 색상 */
    border-radius: 10px;
  }
`

const RegionArea = styled.ul`
  position: absolute;
  top: 60px;
  width: 50%;
  height: calc(100% - 60px);
  overflow-y: auto;
  box-sizing: border-box;
  &::-webkit-scrollbar {
    width: 8px;  /* 스크롤바의 너비 */
  }
  &::-webkit-scrollbar-thumb {
    background: #3fa0ee; /* 스크롤바의 색상 */
    
    border-radius: 10px;
  }
`
const ListItem1 = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  font-weight: 900;
  display: flex;
  align-items: center;
  cursor: pointer;
`

const ListItem2 = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  font-weight: 900;
  display: flex;
  align-items: center;
  background-color: #e7e7e7;
  cursor: pointer;
`

const ListItem3 = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  font-weight: 900;
  display: flex;
  align-items: center;
  background-color: #d9d9d9;
  cursor: pointer;
`

const ListItemActive = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #FFF;
  font-weight: 900;
  background: #313d48 url("icon_check_pad.png") no-repeat right center;
  -webkit-background-size: 41px;
  background-size: 41px;
`

const Header = styled.header`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 60px;
  box-sizing: border-box;
  padding-left: 10px;
  font-size: 20px;
  line-height: 60px;
  border-bottom: 1px solid #296ebc;
`

export default ExamDate