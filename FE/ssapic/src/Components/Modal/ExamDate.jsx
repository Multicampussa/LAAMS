import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { styled } from 'styled-components';
import ExamCurrent from './ExamCurrent';
import ExamResult from './ExamResult';
import ExamSelect from './ExamSelect';

const ExamDate = ({setIsShowModal,setData,data,setMain}) => {
  const [time,setTime] = useState();
  const [date,setDate] = useState();
  const handleBtnNext = useCallback(()=>{
    date.setHours(time.getHours());
    date.setMinutes(time.getMinutes());
    data.date = date;
    const endDate = new Date();
    endDate.setMinutes(date.getMinutes()+80);
    data.endDate = endDate;
    if(data.center){
      setMain(<ExamResult setMain={setMain} setIsShowModal={setIsShowModal} setData={setData} data={data} />)
    }else{
      setMain(<ExamSelect setMain={setMain} setIsShowModal={setIsShowModal} setData={setData} data={data} />)
    }
  },[time,date,data,setData,setIsShowModal,setMain]);


  useEffect(()=>{
    const temp = new Date();
    temp.setDate(temp.getDate()+2);
    temp.setHours(10);
    temp.setMinutes(0);
    setTime(temp);
    setDate(temp);
  },[]);

  const dateItems = useMemo(()=>{
    if(!date)return[];
    const weekList = ["일","월","화","수","목","금","토"];
    let curYear = date.getFullYear();
    let curMonth = date.getMonth();
    let curDay = date.getDate();
    let curWeek = weekList[date.getDay()];
    const temp = [];
    const curDate = new Date();
    curDate.setDate(curDate.getDate()+2);
    for(let i = 0; i < 30; i++){
      let year = curDate.getFullYear();
      let month = curDate.getMonth();
      let day = curDate.getDate();
      let week = weekList[curDate.getDay()];
      if(curYear===year && curMonth===month && curDay === day && curWeek === week){
        temp.push(<ListItemActive key={i}>
          {`${year}.${month+1}.${day}(${week})`}
        </ListItemActive>)
      }
      else if(i%2===0){
        temp.push(<ListItem1 onClick={()=>setTime(new Date(year,month,day))} key={i}>
          {`${year}.${month+1}.${day}(${week})`}
        </ListItem1>)
      }else{
        temp.push(<ListItem2 onClick={()=>setTime(new Date(year,month,day))} key={i}>
          {`${year}.${month+1}.${day}(${week})`}
        </ListItem2>)
      }
      curDate.setDate(curDate.getDate()+1);
    }
    return temp;
  },[date]);

  const timeItems = useMemo(()=>{
    if(!time)return [];
    const temp = [];
    const curHour = time.getHours();
    const curMinutes = time.getMinutes();
    let start = new Date();
    start.setHours(10);
    start.setMinutes(0);
    let end = new Date();
    end.setHours(10);
    end.setMinutes(70);
    for(let i = 0; i < 7; i++){ 
      const sh = start.getHours();
      const sm = start.getMinutes();
      if(sh===curHour&&sm===curMinutes){
        temp.push(<ListItemActive key={i}>
          {`${start.getHours() < 10 ? "0"+start.getHours():start.getHours()}:${start.getMinutes()<10 ? "0"+start.getMinutes() :start.getMinutes()}`}
          ~
          {`${end.getHours() < 10 ? "0"+end.getHours():end.getHours()}:${end.getMinutes()<10 ? "0"+end.getMinutes() :end.getMinutes()}`}
        </ListItemActive>)
      }
      else if(i%2===0){
        temp.push(<ListItem2 onClick={()=>{
          const cur = new Date();
          cur.setHours(sh);
          cur.setMinutes(sm);
          setTime(cur);
        }} key={i}>
          {`${start.getHours() < 10 ? "0"+start.getHours():start.getHours()}:${start.getMinutes()<10 ? "0"+start.getMinutes() :start.getMinutes()}`}
          ~
          {`${end.getHours() < 10 ? "0"+end.getHours():end.getHours()}:${end.getMinutes()<10 ? "0"+end.getMinutes() :end.getMinutes()}`}
        </ListItem2>)
      }else{
        temp.push(<ListItem3 onClick={()=>{
          const cur = new Date();
          cur.setHours(sh);
          cur.setMinutes(sm);
          setTime(cur);
        }} key={i}>
          {`${start.getHours() < 10 ? "0"+start.getHours():start.getHours()}:${start.getMinutes()<10 ? "0"+start.getMinutes() :start.getMinutes()}`}
          ~
          {`${end.getHours() < 10 ? "0"+end.getHours():end.getHours()}:${end.getMinutes()<10 ? "0"+end.getMinutes() :end.getMinutes()}`}
        </ListItem3>)
      }
      start.setMinutes(start.getMinutes()+80);
      end.setMinutes(end.getMinutes()+80);
    }
    return temp;
  },[time]);

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
          <RegionArea>{dateItems}</RegionArea>
          <CenterArea>{timeItems}</CenterArea>
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
  font-size: 18px;
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
  font-size: 18px;
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
  font-size: 18px;
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
  font-size: 18px;
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