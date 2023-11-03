import React, { useCallback, useEffect, useMemo } from 'react'
import { useSelector } from 'react-redux'

const CalendarDetail = () => {
  const calendarData = useSelector(state=>state.ManagerCalendarDetail);

  useEffect(()=>{console.log(calendarData)},[calendarData]);

  const calendarDetailIems = useMemo(()=>{
    const res = [];
    let key = 0;
    calendarData.dailyDashboardErrorReports.forEach(e=>res.push(<li className='manager-calendar-detail-errorreport' key={key++}>
      <div className='manager-calendar-detail-item-title'>에러리포트 : {e.title}</div>
      <div className='manager-calendar-detail-item-name'>{e.directorName}</div>
    </li>));
    calendarData.unprocessedCompensations.forEach(e=>res.push(<li className='manager-calendar-detail-compensation' key={key++}>
      <div className='manager-calendar-detail-item-title'>보상 미처리 : {e.compensationType}</div>
      <div className='manager-calendar-detail-item-name'>{e.examineeName}</div>
    </li>))

    return res;
  },[calendarData]);

  const getDayFormat = useCallback((date)=>{
    switch(date){
      default:
      case 0:
        return "일요일";
      case 1:
        return "월요일";
      case 2:
        return "화요일";
      case 3:
        return "수요일";
      case 4:
        return "목요일";
      case 5:
        return "금요일";
      case 6:
        return "토요일";
    }
  },[])
  return (
    <article className='manager-calendar-detail'>
      <div className='manager-calendar-detail-title'>{`${calendarData.date.getFullYear()}년${calendarData.date.getMonth()+1}월${calendarData.date.getDate()}일${getDayFormat(calendarData.date.getDay())}`}</div>
      <ul className='manager-calendar-detail-box'>
        {
          calendarDetailIems
        }
      </ul>
    </article>
  )
}

export default CalendarDetail