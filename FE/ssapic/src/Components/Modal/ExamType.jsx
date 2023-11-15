import React, { useCallback, useEffect, useState } from 'react'
import { styled } from 'styled-components';
import ExamLanguage from './ExamLanguage';

const ExamType = ({setIsShowModal,setData,data,setMain}) => {
  const [examType,setExamType] = useState("SSAPIc L&R");
  const handleSelect = useCallback((mode)=>{
    setMain(<ExamLanguage setIsShowModal={setIsShowModal} setData={setData} data={data} setMain={setMain} mode={mode} />);
  },[setIsShowModal,setData,data,setMain]);
  useEffect(()=>{data.examType=examType;},[examType])
  return (
    <Wrap>
      <TitleBox>
        <Title>SSAPIc 시험신청</Title>
        <BtnClose onClick={()=>setIsShowModal(false)}>X</BtnClose>
      </TitleBox>
      <QuickArea>
        <QuickAreaTitle>시험 종류 선택</QuickAreaTitle>
        <QuickAreaConent>
          <li>응시하고자 하는 시험의 종류를 선택하시기 바랍니다.</li>
        </QuickAreaConent>
      </QuickArea>
      <QuickArea2>
        <div>
          <CheckboxLabel onClick={()=>setExamType("SSAPIc L&R")}>
            <CheckboxBg $checked={examType==="SSAPIc L&R"}/>
            <p>SSAPIc L&R</p>
          </CheckboxLabel>
          <CheckboxLabel onClick={()=>setExamType("SSAPIc Listening")}>
            <CheckboxBg $checked={examType==="SSAPIc Listening"}/>
            SSAPIc Listening
          </CheckboxLabel>
          <CheckboxLabel onClick={()=>setExamType("SSAPIc Writing")}>
            <CheckboxBg $checked={examType==="SSAPIc Writing"}/>
            SSAPIc Writing
          </CheckboxLabel>
        </div>
        <div>
          <Btn onClick={()=>handleSelect("center")}>센터 먼저 선택</Btn>
          <Btn onClick={()=>handleSelect("date")}>날짜 먼저 선택</Btn>
        </div>
      </QuickArea2>
      <StepBox>
        <p>시험 신청 절차</p>
        <img src='step_bg.png' alt='시험 신청 절차' width="100%" />
      </StepBox>
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

const QuickArea = styled.div`
  width: auto;
  box-sizing: border-box;
  height: 316px;
  padding: 100px 0 50px 250px;
  margin-left: 200px;
  background: #fff url("quick_bg.png") -176px 40px no-repeat;
  -webkit-background-size: 575px;
  background-size: 575px;
`
const QuickAreaTitle = styled.h1`
  margin-bottom: 25px;
  font-size: 30px;
  font-weight: 900;
`
const QuickAreaConent = styled.ul`
  & li{
    margin-bottom: 8px;
    padding-left: 13px;
    background: url("dot_gray4.png") 0 10px no-repeat;
    font-size: 18px;
    line-height: 26px;
  }
`
const QuickArea2 = styled.div`
  width: auto;
  margin: 0 40px 30px 40px;
  box-sizing: border-box;
  height: 140px;
  padding: 20px 0;
  background: none;
  background-color: #f2f2f2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 10px;
  & div{
    display: flex;
    gap: 10px;
  }
`

const CheckboxLabel = styled.div`
  padding-right: 10px;
  font-size: 21px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`

const CheckboxBg = styled.div`
  width: 20px;
  height: 20px;
  background-image: url("checkbox.png");
  -webkit-background-size: 20px;
  background-size: 20px;
  background-repeat: no-repeat;
  background-position: ${props=>props.$checked ? "0 -20px" : "0 1px"};
`

const Btn = styled.button`
  background-color: #2768b2;
  color: #FFFFFF;
  width: 195px;
  height: 50px;
  font-size: 20px;
  line-height: 50px;
  -webkit-border-radius: 25px;
  -moz-border-radius: 25px;
  border-radius: 25px;
`

const StepBox = styled.div`
  width: auto;
  margin: 0 40px 30px 40px;
  font-size: 21px;
  font-weight: 900;
  display: flex;
  flex-direction: column;
  gap: 10px;
`
export default ExamType