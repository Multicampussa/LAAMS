import React, { useCallback, useEffect, useMemo, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { useParams } from 'react-router-dom';
import { setModalShow, setModalType } from './../../../redux/actions/modalAction';
import { useDispatch } from 'react-redux';
import { setExamineeNo } from '../../../redux/actions/examineeDetailAction';
import { setExamNo } from '../../../redux/actions/managerExamDetailAction';

// TODO : 시험 상세 정보 조회
const ExamDetail = () => {
  const params = useParams();
  const dispatch = useDispatch();
  const [examData, setExamData] = useState({});
  const [examineesData, setExamineesData] = useState([]);
  const api = useApi();
  const getExam = useCallback(async()=>{
    await api.get(`director/exams/${params['no']}`)
    .then(({data})=>{
        setExamData({
            centerName: data.data.centerName,
            ceterRegion : data.data.ceterRegion,
            examDate : data.data.examDate,
            examLanguage: data.data.examLanguage,
            examType: data.data.examType,
            runningTime : data.data.runningTime
        })
    })
    .catch((err)=>{
        console.log(err)
    })
  },[api,params])

  // TODO : 보상 모달 띄우기
  const handleCompensationModal = useCallback(()=>{
    dispatch(setModalType("exam-compensation"));
    dispatch(setModalShow(true));
  },[dispatch])

  //TODO : 응시자 상세 모달 띄우기
  const handleExamineeModal = useCallback((examineeNo, examNo)=>{
    dispatch(setExamineeNo(examineeNo));
    dispatch(setExamNo(examNo));
    console.log('응시자 정보',examineeNo,examNo)
    dispatch(setModalType("examinee-detail"));
    dispatch(setModalShow(true));
  },[dispatch])

  // TODO : 시작시간을 활용한 종료시간(시작+러닝타임) 계산 로직 및 형식 정리
  const getTime = useCallback((time, runningTime)=>{
    const testStartTime = new Date(time); // testStartTimeStr는 서버에서 받은 시험 시작 시간
    const runningTimeMs = runningTime * 60 * 1000; // runningTime은 서버에서 받은 러닝타임 (분 단위)
    const testEndTime = new Date(testStartTime.getTime() + runningTimeMs);
    const formatTime = (date) => {
      let hours = date.getHours();
      let minutes = date.getMinutes();

      // 한 자리수 시간 또는 분을 두 자리수 문자열로 변환
      hours = hours < 10 ? '0' + hours : hours;
      minutes = minutes < 10 ? '0' + minutes : minutes;

      return hours + ':' + minutes;
  };
  return [formatTime(testStartTime), formatTime(testEndTime)];
  },[])

  
  //TODO : 출석 시간 형식 조정
  const attendanceFormat = useCallback((time)=>{
    if(time){
    const attendanceTime = new Date(time);
    const hours = attendanceTime.getHours();
    const minutes = attendanceTime.getMinutes();
    return hours + ':' + minutes;}
    else{
      return ''
    }
  },[])


  //TODO : 시험의 응시자 목록 불러오기
  const getExaminees = useCallback(async()=>{
    await api.get(`director/exams/${params['no']}/examinees`)
    .then(({data})=>{
      setExamineesData(data.data)
    })
    .catch((err)=>{
      console.log(err);
    })
  },[api,params])

  useEffect(()=>{
    getExaminees()
    getExam()
  },[getExaminees,getExam])

  // TODO : 출석 인원수 체크
  const attendanceCnt = useCallback(()=>{
    let cnt = 0
    for (let examinee of examineesData){
      if (examinee.attendance){
        cnt = cnt + 1
      }
    }
    return cnt;
  },[examineesData])

  // TODO : 문서 제출 완료 갯수
  const docCnt = useCallback(()=>{
    let cnt = 0
    for (let examinee of examineesData){
      if(examinee.document === '서류_제출_완료'){
        cnt = cnt + 1
      }
    }
    return cnt;
  },[examineesData])


  // TODO : 서류 제출 형식 변경
  const docFormat = useCallback((doc)=>{
      if(doc === "서류_제출_대기"){
        return "대기"
      }else if(doc === "서류_제출_완료"){
        return "제출"
      }else{
        return "미제출"
      }
    },[])

  // TODO : 응시자 리스트 조회
  const examineeInfo = useMemo(()=>{
    return examineesData.map((examinee, index) => {
      return (
        <li className='director-examinees-list-items' key={index}>
          <div 
            className='director-examinees-list-items-title' 
            onClick={()=>handleExamineeModal(examinee.examineeNo,params['no'])}>
              {examinee.examineeCode}
          </div>
          <div 
            className='director-examinees-list-items-title' 
            onClick={()=>handleExamineeModal(examinee.examineeNo,params['no'])}>
              {examinee.examineeName}
          </div>
          <div>
            <select value={examinee.attendance? '출석' : '결석'}>
              <option>지각</option>
              <option>결석</option> 
              <option>출석</option>
            </select>
          </div>
          <div>{attendanceFormat(examinee.attendanceTime)}</div>
          <div>{docFormat(examinee.document)}</div>
          
          <button 
            className='director-examinees-list-btn'
            onClick={()=>handleCompensationModal()}>보상 신청</button>
          <button className='director-examinees-list-btn'>추가 서류</button>
        </li> 
      )
    })},[examineesData,docFormat,handleCompensationModal,handleExamineeModal,params, attendanceFormat])


  return (      
    <section className='exam-detail'>
        <aside className='exam-detail-aside'>
          <div className='exam-detail-aside-box'>
            <div className='exam-detail-aside-title'>시험정보</div>
            <div className='exam-detail-aside-title-box'>
              <p>{examData['centerName']}</p>
              <p>{getTime(examData['examDate'],examData['runningTime'])[0]} ~ {getTime(examData['examDate'],examData['runningTime'])[1]} ({examData['runningTime']}분)</p>
              {/* FIXME : api 연결 필요 */}
              <p>감독관 센터 도착 완료</p>
            </div>
            <button className='btn-m'>감독관 센터 도착 인증</button>
            <div className='exam-detail-aside-title'>응시자 현황</div>
            <div className='exam-detail-aside-title-box'>
              <p>응시자 수: {examineesData.length}명</p>
              <p>출결 현황: {attendanceCnt()}명/{examineesData.length}명</p>
              <p>서류 현황: {docCnt()}명/{examineesData.length}명</p>
            </div>
            {/* api 연결 필요 */}
            <button className='btn-m'>문의하기</button>
          </div>
        </aside>
        <article className='exam-detail-examinees'>
            <div className='exam-detail-examinees-box'>
              <ul className='exam-detail-examinees-list'>
                <li className='director-examinees-list-items'>
                  <div>수험번호</div>
                  <div>응시자 이름</div>
                  <div>출결 </div>
                  <div>출결 시간</div>
                  <div>서류</div>
                  <div>보상 신청</div>
                  <div>추가 서류</div>
                </li>
                {
                  examineeInfo
                }
              </ul>
            </div>
        </article>
    </section>
  )

}

export default ExamDetail