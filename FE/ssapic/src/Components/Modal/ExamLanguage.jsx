import React, { useCallback, useState } from 'react'
import { styled } from 'styled-components'
import ExamSelect from './ExamSelect';

const ExamLanguage = ({setIsShowModal,setData,data,setMain,mode}) => {
  const [lng,setLng] = useState("영어");
  const handleBtnNext = useCallback(()=>{
    setMain(<ExamSelect mode={mode} setIsShowModal={setIsShowModal} setData={setData} data={data} />)
  },[setIsShowModal,setData,data,setMain,mode])
  return (
    <Wrap>
      <TitleBox>
        <Title>SSAPIc 시험신청</Title>
        <BtnClose onClick={()=>setIsShowModal(false)}>X</BtnClose>
      </TitleBox>
      <Notice>
        <li>응시 어종 선택 시 ‘성적을 취득하고자 하는 시험 언어’를 선택하시기 바랍니다.</li>
        <li>응시 어종 선택 후, 시험센터와 날짜 선택 단계가 진행됩니다.</li>
        <li>패키지 신청의 경우, 현재 시험의 선택을 마치면 다음 평가 접수가 이어서 진행됩니다.</li>
      </Notice>
      <SelectBox>
        <SelectTitle>응시 어종 선택</SelectTitle>
        <LanguageBox>
          <LanguageItem>
            <img height="128px" src='img_english.png' alt='영어' />
            <label onClick={()=>setLng("영어")}><LngRadioBtn  $selected={lng==="영어" ? "ok":"no"} type="radio" name="lng"/>영어</label>
          </LanguageItem>
          <LanguageItem>
            <img height="128px" src='img_chinese.png' alt='중국어' />
            <label onClick={()=>setLng("중국어")}><LngRadioBtn  $selected={lng==="중국어" ? "ok":"no"} type="radio" name="lng"/>중국어</label>
          </LanguageItem>
          <LanguageItem>
            <img height="128px" src='img_japan.png' alt='일본어' />
            <label onClick={()=>setLng("일본어")}><LngRadioBtn  $selected={lng==="일본어" ? "ok":"no"} type="radio" name="lng"/>일본어</label>
          </LanguageItem>
          <LanguageItem>
            <img height="128px" src='img_spanish.png' alt='스페인어' />
            <label onClick={()=>setLng("스페인어")}><LngRadioBtn  $selected={lng==="스페인어" ? "ok":"no"} type="radio" name="lng"/>스페인어</label>
          </LanguageItem>
          <LanguageItem>
            <img height="128px" src='img_russian.png' alt='러시아어' />
            <label onClick={()=>setLng("러시아어")}><LngRadioBtn  $selected={lng==="러시아어" ? "ok":"no"} type="radio" name="lng"/>러시아어</label>
          </LanguageItem>
          <LanguageItem>
            <img height="128px" src='img_korean.png' alt='한국어' />
            <label onClick={()=>setLng("한국어")}><LngRadioBtn  $selected={lng==="한국어" ? "ok":"no"} type="radio" name="lng"/>한국어</label>
          </LanguageItem>
          <LanguageItem>
            <img height="128px" src='img_vietnam.png' alt='베트남어' />
            <label onClick={()=>setLng("베트남어")}><LngRadioBtn  $selected={lng==="베트남어" ? "ok":"no"} type="radio" name="lng"/>베트남어</label>
          </LanguageItem>
        </LanguageBox>
      </SelectBox>
      <Btn onClick={handleBtnNext}>다음</Btn>
    </Wrap>
  )
}
const Btn = styled.button`
  font-size: 21px;
  display: block;
  width: 90%;
  margin-left: 5%;
  border: 0 none;
  background-color: #3fa0ee;
  text-align: center;
  font-size: 16px;
  font-weight: 900;
  height: 40px;
  color: #ffffff;
  cursor: pointer;
`

const LanguageItem = styled.li`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  & label{
    display: flex;
    align-items: center;
    cursor: pointer;
  }
`

const LngRadioBtn = styled.input`
  width: 16px;
  height: 16px;
  border-radius: 16px;
  background-color: ${props=>props.$selected === "ok" ? "#3fa0ee":"#FFFFFF"};
  border: 1px solid #d8d8d8;
  cursor: pointer;`

const LanguageBox = styled.ul`
  display: flex;
  height: 295px;
  width: 100%;
  justify-content: space-around;
`

const SelectTitle = styled.h1`
  font-size: 30px;
  font-weight:900;
  margin-bottom: 20px;
`

const SelectBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`

const Notice = styled.ul`
  margin-bottom: 40px;
  padding: 35px 40px;
  background-color: #f7f7f7;
  font-weight: 900;
  font-size: 20px;
  list-style-image: url("dot_gray4.png");
  &>li:first-child{
    color: #FF0000;
  }
`

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
export default ExamLanguage