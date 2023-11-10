import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import { setModalShow } from '../../../redux/actions/modalAction';
import useApi from './../../../Hook/useApi';

const DirectorCalendarDetail = () => {
  const examList = useSelector(state => state.CalendarExamList.examList);
  const [isClicked, setIsClicked] = useState({1:true, 2:false});
  const [isChecked, setIsChecked] = useState(false)
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const api = useApi();
  const examDate = useSelector(state=>state.CalendarExamList.examDate)
  const [requestList, setRequestList] = useState([]);
  const isModalOpen = useSelector(state=>state.Modal.show)
  
  // TODO : 날짜 형식 조정
  const examTimeFormat = useCallback((date)=>{
    const examDate = new Date(date);
    const hours = examDate.getHours() <10? '0'+examDate.getHours():examDate.getHours();
    const min = examDate.getMinutes() <10? '0'+examDate.getMinutes():examDate.getMinutes(); 
    return hours + ':' + min
  },[])

  // TODO : 감독 배정 요청 보내는 API
  const sendRequest = useCallback((examNo,index)=>{
    api.post('director/exams/request', {'examNo':examNo})
    .then(({data})=>{
        const newRequestList = [...requestList];
        newRequestList[index].currentConfirm = '대기';
        setRequestList(newRequestList);
    })
    .catch((err)=>{
        console.log(err)
    })
  },[api,requestList])

  //TODO : 감독 요청 가져오기
  const getRequestList = useCallback(async(date)=>{
    await api.get(`director/exams/unapproved?year=${date['year']}&month=${date['month']}&day=${date['day']}`)
    .then(({data})=>{
        setRequestList(data.data)
    })
    .catch((err)=>{
        console.log(err)
    })
  },[api])

  useEffect(()=>{
    getRequestList(examDate)
    if(!isModalOpen){
        setIsChecked(false)
        setIsClicked({1:true, 2:false})
    }
  },[getRequestList, examDate,isModalOpen,examList])

  //TODO : tab 선택에 따른 일정 및 요청 내역 반환
  const showExamList = useMemo(()=>{
    if(isClicked[1]){
        if(examList.length===0){
            return <div className='director-calendar-detail-box-task-none' >감독을 맡은 시험이 없습니다</div>
        }
        return examList.map((exam,index)=>{
            return  <li className='director-calendar-detail-box-task-items' key={index}>
                        <div className='director-calendar-detail-box-task-items-title'
                            onClick={()=>{
                                navigate(`/director/exam/${exam.examNo}`);
                                dispatch(setModalShow(false))}}>{exam.examType}-{exam.examLanguage}</div>
                        <div>{examTimeFormat(exam.examDate)}</div>
                        <div>{examTimeFormat(exam.endExamDate)}</div>
                    </li>
        })
    }
    if(isClicked[2]){
        // 배치 가능한 시험만 조회
        if(requestList.length===0){
            return <div className='director-calendar-detail-box-task-none' >요청 목록 · 요청 가능한 시험 목록이 없습니다</div>
        }
        if (isChecked){
            return requestList.map((req,index)=>{
                if(req.currentConfirm==='미배치'){
                    return  <li li className='director-calendar-detail-box-request-items' key={index}>
                                <div>{req.examType}-{req.examLanguage}</div>
                                <div>{examTimeFormat(req.examDate)}</div>
                                <div>{examTimeFormat(req.endExamDate)}</div>
                                <div>{req.currentConfirm}</div>
                                <button onClick={()=>sendRequest(req.examNo,index)}className={`director-calendar-detail-box-request-items-btn-${req.currentConfirm==='미배치'? true:false}`}>감독요청</button>
                            </li>
                }else{
                    return null;
                }
            })
        }
        // 배치 가능 + 거절/대기 조회
        return  requestList.map((req, index)=>{
            return  <li li className='director-calendar-detail-box-request-items' key={index}>
                        <div>{req.examType}-{req.examLanguage}</div>
                        <div>{examTimeFormat(req.examDate)}</div>
                        <div>{examTimeFormat(req.endExamDate)}</div>
                        <div>{req.currentConfirm}</div>
                        <button onClick={()=>sendRequest(req.examNo,index)} className={`director-calendar-detail-box-request-items-btn-${req.currentConfirm==='미배치'? true:false}`}>감독요청</button>
                    </li>
        })
    }
  },[examList,isClicked, examTimeFormat, 
    navigate, dispatch,requestList,isChecked, sendRequest])

  return (
    <section className='director-calendar-detail'>
        <div className='director-calendar-detail-date'>{examDate['month']}월 {examDate['day']}일        <label className={`director-calendar-detail-box-${isClicked[2]}-label`}>
                <input type="checkbox" className={`director-calendar-detail-box-input`}
                onClick={()=>setIsChecked(!isChecked)}/>
                배치 가능 시험만 조회</label></div>
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
                    <div>감독요청</div>
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