import React, { useCallback, useMemo } from 'react'
import { useSelector,useDispatch } from 'react-redux'
import { Link } from 'react-router-dom';
import { setModalShow, setModalType } from './../../../redux/actions/modalAction';
import { setExamNo } from './../../../redux/actions/managerExamDetailAction';
import { setManagerCompensationNo } from '../../../redux/actions/managerCompensationAction';
const CalendarDetail = () => {
  const calendarData = useSelector(state=>state.ManagerCalendarDetail);
  const dispatch = useDispatch();
  const handleClose = useCallback(()=>{
    dispatch(setModalShow(false));
  },[dispatch]);

  const handleAssignment=useCallback((examNo)=>{
    dispatch(setExamNo(examNo));
    dispatch(setModalType("exam-detail"));
  },[dispatch])

  const handleCompensation=useCallback((compensationNo)=>{
    dispatch(setManagerCompensationNo(compensationNo));
    dispatch(setModalType("manager-compensation"));
  },[dispatch])

  const calendarDetailIems = useMemo(()=>{
    const res = [];
    let key = 0;
    calendarData.unassignedExams.forEach(e=>res.push(
      <li onClick={()=>handleAssignment(e.examNo)} key={key++} className='manager-calendar-detail-assignment' >
        <div className='manager-calendar-detail-item-title'>감독관 미배정 : {e.examType}</div>
        <div className='manager-calendar-detail-item-name'>{`${e.centerName} ${e.centerManagerName}`}</div>
      </li>
    ));
    calendarData.dailyDashboardErrorReports.forEach(e=>res.push(
      <Link onClick={handleClose} key={key++} to={`/manager/error-report/${e.errorReportNo}`}>
        <li className='manager-calendar-detail-errorreport' >
          <div className='manager-calendar-detail-item-title'>에러리포트 : {e.title}</div>
          <div className='manager-calendar-detail-item-name'>{e.directorName}</div>
        </li>
      </Link>
    ));
    calendarData.unprocessedCompensations.forEach(e=>res.push(
      <li onClick={()=>handleCompensation(e.examineeNo)} key={key++} className='manager-calendar-detail-compensation' >
        <div className='manager-calendar-detail-item-title'>보상 미처리 : {e.compensationType}</div>
        <div className='manager-calendar-detail-item-name'>{e.examineeName}</div>
      </li>
    ))

    return res;
  },[calendarData,handleCompensation,handleClose,handleAssignment]);

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