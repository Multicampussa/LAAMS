import React, { useCallback, useState } from 'react'
import Calendar from './Common/Calendar/Calendar'
import useExamList from '../Hook/useExamList'
const Test = () => {
  const examList = useExamList();
  const [curDate,setCurDate] = useState(new Date());

  //TODO : 이전 달 호출
  //FIXME : 데이터 갱신
  const handlePrev = useCallback(()=>{
    let year = curDate.getFullYear();
    let month = curDate.getMonth();
    if(month === 0){
      month = 11;
      year--;
    }else{
      month--;
    }
    setCurDate(new Date(year,month,1));
  },[curDate]);

  //TODO : 다음달 호출
  //FIXME : 데이터 갱신
  const handleNext = useCallback(()=>{
    let year = curDate.getFullYear();
    let month = curDate.getMonth();
    if(month === 11){
      month = 0;
      year++;
    }else{
      month++;
    }
    setCurDate(new Date(year,month,1));
  },[curDate])

  return (
    <div style={{width:"100%",padding:"5% 25%"}}>
      <Calendar handleNext={handleNext} handlePrev={handlePrev} curDate={curDate} examList={examList}></Calendar>
    </div>
  )
}

export default Test