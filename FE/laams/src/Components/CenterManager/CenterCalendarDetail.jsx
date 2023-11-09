import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import useApi from './../../Hook/useApi';
import { setExamList, setMonthExamList } from '../../redux/actions/calendarExamListAction';

const CenterCalendarDetail = () => {
  const examList = useSelector(state=>state.CalendarExamList.examList)
  const dispatch = useDispatch();
  const [isChecked,setIsChecked] = useState([])
  const [isShow, setIsShow] = useState(false);
  const isModalOpen = useSelector(state=>state.Modal.show)
  const [requestList, setRequestList] = useState([]);
  const api = useApi();
  const examDate = useSelector(state=>state.CalendarExamList.examDate)
  const monthExamList = useSelector(state=>state.CalendarExamList.monthExamList)

  //TODO : 특정 시험 요청 목록 조회 로직
  const getRequestList = useCallback((index)=>{
    api.get(`centermanager/exam/${examList[index].examNo}/director/assignment`)
    .then(({data})=>{
      setRequestList(data.data)
    })
    .catch((err)=>{
      console.log(err)
    })
  },[api,examList])

  // TODO : 시험 일정 선택 핸들러 함수
  const handleIsChecked = useCallback((index)=>{
    const newIsChecked = [...isChecked]
    newIsChecked[index] = !newIsChecked[index];
    newIsChecked.forEach((e,i)=>{
      if(i !== index){
      newIsChecked[i] = false;
      }
    })
    if(newIsChecked[index]){
      setIsShow(true)
    }else{
      setIsShow(false)
    }

    setIsChecked(newIsChecked);
  },[isChecked])


  // TODO : 요청 승인 API
  const requestApprove = useCallback((directorNo, examNo, index)=>{
    api.put('centermanager/confirm', {examNo: examNo, directorNo: directorNo})
    .then((res)=>{
      const newReqList = [...requestList]
      newReqList.splice(index,1)
      setRequestList(newReqList)
      // examList에서 examNo와 일치하는 시험을 찾아서 해당 시험의 인덱스를 얻는다
      const examIndex = examList.findIndex(exam => exam.examNo === examNo)
      if (examIndex !== -1) {
        // examIndex를 이용하여 해당 시험의 confirmDirectorCnt 값을 업데이트한다
        const newExamList = [...examList]
        newExamList[examIndex].confirmDirectorCnt += 1
        dispatch(setExamList(newExamList));
      }
      const newMonthExamList = {...monthExamList}
      newMonthExamList[examDate['day']][examIndex].confirmDirectorCnt += 1
      dispatch(setMonthExamList(newMonthExamList))

    })
    .catch((err)=>{
      console.log(err)
    })
  },[api,requestList,examList,dispatch,monthExamList,examDate])


  // TODO : 요청 거절 API
  const requestDeny = useCallback((directorNo, examNo, index)=>{
    api.put('centermanager/deny', {examNo: examNo, directorNo: directorNo})
    .then((res)=>{
      const newReqList = [...requestList]
      newReqList.splice(index,1)
      setRequestList(newReqList)
    })
    .catch((err)=>{
      console.log(err)
    })
  },[api, requestList])

  // TODO : 센터의 해당 날짜 시험 일정 반환
  const showExamList = useMemo(()=>{
    if(!examList){
        return <li className=''>시험 일정이 없습니다</li>
    }
        return examList.map((exam,index)=>{
            const isFull = exam.maxDirector <= exam.confirmDirectorCnt;
            const title = isFull ? '더이상 감독 배치를 할 수 없습니다' : '';
            return <li className={`modal-item-${isChecked[index]? 'active':'deactive'}-${isFull? 'full':'notFull'}`}
            title={title}
            onClick={()=>{handleIsChecked(index); getRequestList(index);}}>{exam.examType}</li>
        })
    },[examList,isChecked, handleIsChecked, getRequestList])

  // TODO : 감독 요청 목록
  const showRequestList = useMemo(()=>{
    if(requestList.length===0){
      return <li className='center-calendar-detail-box-request-items-none'>해당 시험에 감독 요청이 없습니다</li>
    }
    return requestList.map((request,index)=>{
      return <li className='center-calendar-detail-box-request-items' key={index}>
              <div>{request.directorName} 감독관</div>
              <div>{request.isConfirm}</div>
              <div className='center-calendar-detail-box-request-items-btn'>
                <button className='center-calendar-detail-box-request-items-btn-deny'
                onClick={()=>requestDeny(request.directorNo, request.examNo, index)}>거절</button>
                <button className='center-calendar-detail-box-request-items-btn-approve'
                onClick={()=>{requestApprove(request.directorNo, request.examNo, index)}}>승인</button>
              </div>
            </li>
    })
  },[requestList, requestApprove, requestDeny]) 

  useEffect(() => {
    if (!isModalOpen) {
      setIsChecked([]);
      setIsShow(false);
      setRequestList([]);
    }

  }, [isModalOpen,examList]);
  return (
    <section className='center-calendar-detail'>
      <div className='center-calendar-detail-tab'>
      <div className='center-calendar-detail-tab-date'>{examDate['month']}월 {examDate['day']}일</div>
        <div className='center-calendar-detail-tab-exam'>시험 일정</div>
        <div className='center-calendar-detail-tab-request'>감독 요청</div>
      </div>
      <div className='center-calendar-detail-box'>
        <ul className='center-calendar-detail-box-exam'>
          {
            showExamList
          }
        </ul>
        <ul className={`center-calendar-detail-box-request-${isShow}`}>
          {
            showRequestList
          }
        </ul>
      </div>
    </section>
  )
}

export default CenterCalendarDetail