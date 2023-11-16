import React, { useCallback, useState } from 'react'
import { styled } from 'styled-components'

const ExamResult = ({setIsShowModal,setData,data,setMain}) => {
  const handleBtnSubmit = useCallback(()=>{
    alert("시험이 신청되었습니다!");
    setIsShowModal(false);
  },[]);
  const [pay,setPay] = useState();
  return (
    <Wrap>
      <TitleBox>
        <Title>결제</Title>
        <BtnClose onClick={()=>setIsShowModal(false)}>X</BtnClose>
      </TitleBox>
      <MainContainer>
        <p>결제 내역</p>
        <ul>
          <li>
            <p>결제 금액</p>
            <p>84,000원</p>
          </li>
          <li>
            <p>할인 금액</p>
            <p>0원</p>
          </li>
          <li>
            <p>실 결제금액</p>
            <p><em>84,000</em>(VAT포함)</p>
          </li>
          <li>
            <p>결제 수단 선택</p>
            <PayBox>
              <div>
                <Pay1></Pay1>
                <label onClick={()=>setPay("신용카드")}><Radio $active={pay==="신용카드"} type="radio"/>신용카드</label>
              </div>
              <div>
                <Pay2>
                </Pay2>
                <label onClick={()=>setPay("계좌이체")}><Radio $active={pay==="계좌이체"} type="radio"/>계좌이체</label>
              </div>
              <div>
                <Pay3>
                </Pay3>
                <label onClick={()=>setPay("토스페이")}><Radio $active={pay==="토스페이"} type="radio"/>토스페이</label>
              </div>
              <div>
                <Pay4>
                </Pay4>
                <label onClick={()=>setPay("스마일페이")}><Radio $active={pay==="스마일페이"} type="radio"/>스마일페이</label>
              </div>
              <div>
                <Pay5></Pay5>
                <label onClick={()=>setPay("페이코")}><Radio $active={pay==="페이코"} type="radio"/>페이코</label>
              </div>
            </PayBox>
          </li>
        </ul>
      </MainContainer>
      <Hr/>
      <Btn onClick={handleBtnSubmit}>완료</Btn>
    </Wrap>
  )
}
const Wrap = styled.div`
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
  &>:first-child{
    height: 60px;
    line-height: 60px;
    background-color: #c5d9f1;
    font-weight: 900;
    font-size: 20px;
    padding: 0 20px;
  }
  & li{
    display: flex;
    height: 60px;
    line-height: 60px;
    padding: 0 20px;
    font-size: 16px;
    box-sizing: border-box;
    border-bottom: 1px solid #dddddd;
  }
  & li>:first-child{
    width: 150px;
  }
  & li:last-child{
    height: auto;
    padding-bottom: 20px;
  }
`
const Hr = styled.hr`
  background-color: #296ebc;
  width: 100%;
  height: 1px;
  border: 0;
  margin: 0;
`
const PayBox = styled.div`
  flex: 1;
  padding-top: 20px;
  display: flex;
  justify-content: space-between;
  & label{
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    padding: 10px;
    cursor: pointer;
  }
`
const Radio = styled.input`
  margin: 0;
  margin-right: 10px;
  width: 16px;
  height: 16px;
  border-radius: 16px;
  box-sizing: border-box;
  border: 1px solid #d8d8d8;
  background-color: ${props=>props.$active?"#3fa0ee":"#FFFFFF"};
`
const Pay1 = styled.div`
  width: 130px;
  height: 130px;
  background: url("pays.png");
  background-repeat: no-repeat;
  line-height:0;
  box-sizing: border-box;
`
const Pay2 = styled.div`
  width: 130px;
  height: 130px;
  background: url("pays.png") 0 0 no-repeat;
  background-position: 0 -130px;
`
const Pay3 = styled.div`
  width: 130px;
  height: 130px;
  background: url("pays.png") 0 0 no-repeat;
  background-position: 0 -260px;
`
const Pay4 = styled.div`
  width: 130px;
  height: 130px;
  background: url("pays.png") 0 0 no-repeat;
  background-position: 0 -390px;
`
const Pay5 = styled.div`
  width: 130px;
  height: 130px;
  background: url("pays.png") 0 0 no-repeat;
  background-position: 0 -520px;
`
export default ExamResult