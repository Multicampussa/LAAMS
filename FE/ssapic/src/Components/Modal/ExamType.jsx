import React from 'react'
import { styled } from 'styled-components';

const ExamType = ({setIsShowModal,setData,data,setMain}) => {
  return (
    <Wrap>
      <TitleBox>
        <Title>시험신청</Title>
        <BtnClose onClick={()=>setIsShowModal(false)}>X</BtnClose>
      </TitleBox>
      <QuickArea>
        <QuickAreaTitle>시험 종류 선택</QuickAreaTitle>
        <QuickAreaConent>
          <li>응시하고자 하는 시험의 종류를 선택하시기 바랍니다.</li>
          <li>2개 이상의 시험 선택 시 패키지 할인으로 접수됩니다.<br/>( 하단 ‘패키지 할인 접수 안내’ 참고)</li>
        </QuickAreaConent>
      </QuickArea>
      <QuickArea2>
        <CheckboxLabel>
          <Checkbox type="checkbox"/>
          SSAPIc L&R
        </CheckboxLabel>
        <CheckboxLabel>
          <Checkbox type="checkbox"/>
          SSAPIc Listening
        </CheckboxLabel>
        <CheckboxLabel>
          <Checkbox type="checkbox"/>
          SSAPIc Writing
        </CheckboxLabel>
      </QuickArea2>
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
`

const CheckboxLabel = styled.label`
  background-image: url("checkbox.png");
  -webkit-background-size: 20px;
  background-size: 20px;
  background-repeat: no-repeat;
  /* background-position: 0 -19px; */
  background-position: 0 11px;
  padding: 0 10px 0 30px;
  font-size: 21px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
`

const Checkbox = styled.input`
  display: none;
`
export default ExamType