import React, { useCallback, useMemo } from 'react'
import { useDispatch } from 'react-redux';
import { setModalShow, setModalType } from '../../../redux/actions/modalAction';
import { setManagerCalendarDetail } from '../../../redux/actions/managerCalendarDetailAction';

/*
calendarData : 확인해야할 목록 (한달) (Object)
  key : day
  value : 해당 날의 시험목록 (List)
curDate : 현재 날짜
handleNext : 다음 달 호출 함수
handlePrev : 이전 달 호출 함수
*/
const Calendar = ({calendarData,curDate,handleNext,handlePrev}) => {
  const dispatch = useDispatch();
  //TODO : 해당 달의 첫날 반환
  const getFirstDate = useCallback((date)=>{
    return new Date(date.getFullYear(),(date.getMonth()),1);
  },[]);

  //TODO : 해당 달의 마지막날 반환
  const getLastDate = useCallback((date)=>{
    return new Date(date.getFullYear(),(date.getMonth()+1),0);
  },[]);

  //TODO : 해당 달의 주차 수 반환
  const getWeek =  useCallback(() => {
    let date = curDate;
    if(!date) date = new Date();
    const cur = getLastDate(date);
    const currentDate = cur.getDate();
    const firstDay = getFirstDate(cur).getDay();
    return Math.ceil((currentDate + firstDay) / 7);
  },[curDate,getLastDate,getFirstDate]);
  
  //TODO : 상세보기 모달 호출
  const handleCalendarItem = useCallback((idx,date)=>{
    if(!calendarData){ return;}
    dispatch(setManagerCalendarDetail({...calendarData.current[idx-1],date}));
    dispatch(setModalType("manager-calendar-detail"));
    dispatch(setModalShow(true));
  },[calendarData,dispatch])

  //TODO : 달력 Div를 반환
  const calendarItems = useMemo(()=>{
    let date = curDate;
    if(!date) date=new Date();
    const temp = [];
    let firstDate = getFirstDate(date);
    let lastDate = getLastDate(date);
    let key =  0;
    for(let i = 0,iMax=firstDate.getDay(); i < iMax; i++){
      temp.push(<div className='calendar-day' key={key++}></div>);
    }
    
    for(let i = 1,iEnd=lastDate.getDate(); i <= iEnd; i++){
      const week = (firstDate.getDay()+i)%7;
      
      temp.push(<div onClick={()=>handleCalendarItem(i,new Date(curDate.getFullYear(),curDate.getMonth(),i))} className='calendar-day' key={key++}>
        <div className={week===0||week===1?"calendar-week-title":'calendar-day-title'}>{i}</div>
        <div className='calendar-day-icons'>
          {
            calendarData.current && calendarData.current[i-1].dailyDashboardErrorReports.length ? <div className='calendar-day-errorreport'><div className='hidden-text'>에러리포트</div></div> : null
          }
          {
            calendarData.current && calendarData.current[i-1].unprocessedAssignments.length ? <div className='calendar-day-assignment'><div className='hidden-text'>감독관 미할당</div></div> : null
          }
          {
            calendarData.current && calendarData.current[i-1].unprocessedCompensations.length ? <div className='calendar-day-compensation'><div className='hidden-text'>보상</div></div> : null
          }
        </div>
      </div>);
    }
    for(let i = lastDate.getDay(); i < 6; i++){
      temp.push(<div className='calendar-day' key={key++}></div>);
    }
    return temp;
  },[getLastDate,getFirstDate,curDate,calendarData,handleCalendarItem]);

  return (
    <article className='calendar'>
      <div className='calendar-title'>
        <button onClick={handlePrev} className='calendar-prev'><div className='hidden-text'>이전 달</div></button>
        <div>{curDate ? curDate.getFullYear() : (new Date()).getFullYear()}년 {curDate ? curDate.getMonth()+1 : (new Date()).getMonth()+1}월</div>
        <button onClick={handleNext} className='calendar-next'><div className='hidden-text'>다음 달</div></button>
      </div>
      <ul className='calendar-date'>
        <li className='calendar-date-item'>일</li>
        <li className='calendar-date-item'>월</li>
        <li className='calendar-date-item'>화</li>
        <li className='calendar-date-item'>수</li>
        <li className='calendar-date-item'>목</li>
        <li className='calendar-date-item'>금</li>
        <li className='calendar-date-item'>토</li>
      </ul>
      <div className={`calendar-content-${getWeek()}`}>
        {
          calendarItems
        }
      </div>
    </article>
  )
}

export default Calendar