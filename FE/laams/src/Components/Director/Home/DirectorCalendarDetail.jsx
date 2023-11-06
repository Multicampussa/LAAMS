import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import { setModalShow } from '../../../redux/actions/modalAction';

const DirectorCalendarDetail = () => {
  const examList = useSelector(state => state.DirectorCalendarExamList.examList);
  const [isClicked, setIsClicked] = useState({1:true, 2:false});
  const navigate = useNavigate();
  const dispatch = useDispatch();
  
  const examTimeFormat = useCallback((date)=>{
    const examDate = new Date(date);
    const hours = examDate.getHours() <10? '0'+examDate.getHours():examDate.getHours();
    const min = examDate.getMinutes() <10? '0'+examDate.getMinutes():examDate.getMinutes(); 
    return hours + ':' + min
  },[])

  //TODO : tab 선택에 따른 일정 및 요청 내역 반환
  const showExamList = useMemo(()=>{
    if(!examList){
        return <li>시험 일정이 없습니다</li>
    }
    if(isClicked[1]){
        return examList.map((exam,index)=>{
            return  <li className='director-calendar-detail-box-task-items'>
                        <div className='director-calendar-detail-box-task-items-title'
                            onClick={()=>{
                                navigate(`/director/exam/${exam.examNo}`);
                                dispatch(setModalShow(false))}}>{exam.examType}-{exam.examLanguage}</div>
                        <div>{examTimeFormat(exam.examDate)}</div>
                        <div>{examTimeFormat(exam.examDate)}</div>
                    </li>
        })
    }
    if(isClicked[2]){
        
    }
  },[examList,isClicked, examTimeFormat, navigate, dispatch])

  return (
    <section className='director-calendar-detail'> 
        <div className='director-calendar-detail-tab'>
            <div className={`director-calendar-detail-tab-${isClicked[1]}`}
                onClick={()=>setIsClicked({1:true, 2:false})}>감독 일정</div>
            <div className={`director-calendar-detail-tab-${isClicked[2]}`}
                onClick={()=>setIsClicked({1: false, 2:true})}>감독 요청 내역</div>
        </div>
        <div className='director-calendar-detail-box'>
            <ul>
                <li className={`director-calendar-detail-box-${isClicked[1]}-task`}>
                    <div>시험</div>
                    <div>시작시간</div>
                    <div>종료시간</div>
                </li>
                <li className={`director-calendar-detail-box-${isClicked[2]}-request`}>
                    <div>시험</div>
                    <div>시작시간</div>
                    <div>종료시간</div>
                    <div>승인여부</div>
                    <div>승인요청</div>
                </li>
            </ul>
            <ul>
                {
                    showExamList
                }
            </ul>
        </div>
    </section>
  )
}

export default DirectorCalendarDetail