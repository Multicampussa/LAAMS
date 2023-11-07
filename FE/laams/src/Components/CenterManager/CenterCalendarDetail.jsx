import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useSelector } from 'react-redux'

const CenterCalendarDetail = () => {
  const examList = useSelector(state=>state.CalendarExamList.examList)
  const [isChecked,setIsChecked] = useState([])
  const [examNo, setExamNo] = useState();
  const [isShow, setIsShow] = useState(false);
  const isModalOpen = useSelector(state=>state.Modal.show)
  // FIXME : 이후 api 연결 필요
  const [requestList, setRequestList] = useState(["감독관 김철수","감독관 한철수","감독관 최철수","감독관 김응애","감독관 김화수","감독관 히히수","감독관 이철수","감독관 김감독","감독관 김응수"
  ]);

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
    setExamNo(examList[index].examNo);
  },[isChecked, examList])

  useEffect(() => {
    if (!isModalOpen) {
      setIsChecked([]);
    }
  }, [isModalOpen]);

  // TODO : 센터의 해당 날짜 시험 일정 반환
  const showExamList = useMemo(()=>{
    if(!examList){
        return <li className=''>시험 일정이 없습니다</li>
    }
        return examList.map((exam,index)=>{
            return <li className={`modal-item-${isChecked[index]? 'active':'deactive'}`}
            onClick={()=>{handleIsChecked(index);}}>{exam.examType}</li>
        })
    },[examList,isChecked, handleIsChecked])
  
    // TODO : 날짜 형식 수정
  const dateFormat = useCallback((date)=>{
    if(!date){
      return
    }
    const examDate = date.toString();
    const month = examDate.substr(4,3) 
    const day = examDate.substr(8,2) 
    return month + '. '+ day;
  },[])

  // TODO : 감독 요청 조회
  const showRequestList = useMemo(()=>{
    if(!requestList){
      return <li>해당 시험에 감독 요청이 없습니다</li>
    }
    return requestList.map((request,index)=>{
      return <li className='center-calendar-detail-box-request-items'>{request}</li>
    })
  },[requestList]) 


  return (
    <section className='center-calendar-detail'>
      <div className='center-calendar-detail-tab'>
      <div className='center-calendar-detail-tab-date'>{examList? dateFormat(examList[0].examDate):null}</div>
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
          {
            showRequestList
          }
        </ul>
      </div>
    </section>
  )
}

export default CenterCalendarDetail