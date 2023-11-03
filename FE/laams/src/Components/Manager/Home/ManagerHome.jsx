import React, { useCallback, useEffect, useRef, useState } from 'react'
import Calendar from './Calendar'
import useApi from '../../../Hook/useApi';

const ManagerHome = () => {
  const calendarData = useRef();
  const api = useApi();
  const [curDate,setCurDate] = useState();

  const getCalendarData = useCallback(async (date)=>{
    if(!date || !api) return;
    const {data} = await api.get(`manager/dashboard/exam?year=${date.getFullYear()}&month=${date.getMonth()+1}`)
    if(data){
      calendarData.current=data.data;
      return true;
    }
  },[api]);

  const setDateWithSync = useCallback(async(date)=>{
    const res = await getCalendarData(date);
    if(res){
      setCurDate(date);
    }
  },[getCalendarData])

  //TODO : 초기화
  useEffect(()=>{
    setDateWithSync(new Date());
  },[api,setDateWithSync])

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
    setDateWithSync(new Date(year,month,1));
  },[curDate,setDateWithSync]);

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
    setDateWithSync(new Date(year,month,1));
  },[curDate,setDateWithSync])

  return (
    <section className='manager-home'>
      <Calendar handleNext={handleNext} handlePrev={handlePrev} curDate={curDate} calendarData={calendarData}></Calendar>
    </section>
  )
}

export default ManagerHome