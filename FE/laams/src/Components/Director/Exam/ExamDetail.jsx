import React, { useCallback, useEffect, useMemo, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { useParams } from 'react-router-dom';
import { setModalShow, setModalType } from './../../../redux/actions/modalAction';
import { useDispatch, useSelector } from 'react-redux';
import { setExamineeNo } from '../../../redux/actions/examineeDetailAction';
import { setExamNo } from '../../../redux/actions/managerExamDetailAction';
import { useGeoLocation } from './../../../Hook/useGeolocation';

const ExamDetail = () => {
  const params = useParams();
  const dispatch = useDispatch();
  const [examData, setExamData] = useState({});
  const [examineesData, setExamineesData] = useState([]);
  const api = useApi();
  const geolocation = useGeoLocation();
  const userId = useSelector(state=>state.User.memberId);
  const [directorAttendance, setDirectorAttendance] = useState(false);
  
  // TODO : 감독관 정보 조회
  const getDirectorInfo = useCallback(async()=>{
    if(!userId || !api){
      return
    }
    const response = await api.get(`member/info/${userId}`);
    return response.data.data.memberNo;
  },[api,userId])

  
// TODO : 시험 상세 정보 조회
  const getExam = useCallback(async()=>{
    await api.get(`director/exams/${params['no']}`)
    .then(({data})=>{
        setExamData({
            centerName : data.data.centerName,
            ceterRegion : data.data.ceterRegion,
            examDate : data.data.examDate,
            examLanguage : data.data.examLanguage,
            examType : data.data.examType,
            runningTime : data.data.runningTime
        })
    })
    .catch((err)=>{
        console.log(err)
    })
  },[api,params])

  // TODO : 현재 위치 정보 얻어옴
  const getLocation = useCallback(async()=>{
    try{
      const location = await geolocation();
      return location
    }catch(err){
      alert(err.message);
    }
  },[geolocation])

  //TODO : 감독관 센터 도착 인증 요청
  const directorAttend = useCallback(async()=>{
    const location = await getLocation();
    const directorUserNo = await getDirectorInfo();
    await api.post(`director/exams/${params['no']}/${directorUserNo}/attendance`,
      {latitude: location['latitude'], longitude:location['longitude']})
      // {latitude: 37.5884628, longitude:127.062636})
    .then(({data})=>{
      setDirectorAttendance(true);
      console.log(data)
    })
    .catch((err)=>{
      alert('센터 인근에서 인증해주세요')
    })
  },[api, getDirectorInfo,params,getLocation])

  //TODO : 서류 제출 변경
  const updateDocs = useCallback((index, e)=>{
    if(examineesData[index].attendanceTime){
    if(e.target.value==="제출"){
      api.put(`director/exams/${params['no']}/examinees/${examineesData[index].examineeNo}/document`,
      {
        "document": "서류_제출_완료"
      })
      .then(({data})=>{
        const newExamineeData = [...examineesData];
        newExamineeData[index].document = data.data.compensationType
        setExamineesData(newExamineeData);
      })
      .catch((err)=>{
        console.log(err)
      })
    }else{
      api.put(`director/exams/${params['no']}/examinees/${examineesData[index].examineeNo}/document`,
      {
        "document": "서류_미제출"
      })
      .then(({data})=>{
        const newExamineeData = [...examineesData];
        newExamineeData[index].document = data.data.compensationType
        setExamineesData(newExamineeData);
      })
      .catch((err)=>{
        console.log(err)
      })
    }
  }else{
    alert('출석하지 않은 응시자 입니다.')
  }

  },[api,examineesData,params])

  // TODO : 보상 모달 띄우기
  const handleCompensationModal = useCallback((examineeNo, examNo)=>{
    dispatch(setExamineeNo(examineeNo));
    dispatch(setExamNo(examNo));
    dispatch(setModalType("exam-compensation"));
    dispatch(setModalShow(true));
  },[dispatch])

  //TODO : 응시자 상세 모달 띄우기
  const handleExamineeModal = useCallback((examineeNo, examNo)=>{
    dispatch(setExamineeNo(examineeNo));
    dispatch(setExamNo(examNo));
    dispatch(setModalType("examinee-detail"));
    dispatch(setModalShow(true));
  },[dispatch])

  //TODO : 응시자 추가 서류 모달 띄우기
  const handleDocsModal = useCallback((examineeNo)=>{
    dispatch(setExamineeNo(examineeNo));
    dispatch(setModalShow(true));
    dispatch(setModalType("exam-docs"));
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
    const hours = attendanceTime.getHours() < 10 ? '0' + attendanceTime.getHours() : attendanceTime.getHours();
    const minutes = attendanceTime.getMinutes() < 10 ? '0' + attendanceTime.getMinutes() : attendanceTime.getMinutes();
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
    let cnt = 0;
    for (let examinee of examineesData) {
      if(examinee.attendanceTime){
        const time = new Date(examinee.attendanceTime);
        const examTime = new Date(examData['examDate']);
        if(time<examTime){
          cnt = cnt + 1;
        }
      }
    }
    return cnt;
  },[examineesData, examData])

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

  // TODO : 응시자 출석 정보 변경
  const changeAttendance = useCallback((index)=>{
    if(examineesData[index].attendance){
      alert('이미 출석한 응시자입니다');
      return
    }
    api.put(`director/exams/${params['no']}/examinees/${examineesData[index].examineeNo}/attendance`)
    .then((({data})=>{
      const newExamineeData = [...examineesData];
      newExamineeData[index].attendanceTime = data.data.attendanceTime;
      setExamineesData(newExamineeData);
    }))
    .catch((err)=>{
      console.log(err);
    })
  },[api,params,examineesData])

  //TODO : 응시자 지각 여부 화면에 띄워줌
  const lateAttendance = useCallback((examinee)=>{
      if(examinee.attendanceTime){
        const attendanceTime = new Date(examinee.attendanceTime);
        const examTime = new Date(examData['examDate']);
        if (attendanceTime>examTime){
            return '지각'
        }else{
          return ''
        }
      }else{
        return ''
      }
  },[examData])

  // TODO : 응시자 리스트 조회
  const examineeInfo = useMemo(()=>{
    return examineesData.map((examinee, index) => {
      return (
        <li className='director-examinees-list-items' key={index}>
          <div 
            className='director-examinees-list-items-code' 
            onClick={()=>handleExamineeModal(examinee.examineeNo,params['no'])}>
              {examinee.examineeCode}
          </div>
          <div 
            className='director-examinees-list-items-name' 
            onClick={()=>handleExamineeModal(examinee.examineeNo,params['no'])}>
              {examinee.examineeName}
          </div>
          <button 
            className={`director-examinees-list-items-btn-${examineesData[index].attendance? 'hidden':'show'}`}
            onClick={()=>{
              changeAttendance(index);
            }}>
            출석
          </button>
          <div className='director-examinees-list-time'>{attendanceFormat(examinee.attendanceTime)} {lateAttendance(examinee)}</div>
          <select 
            className='director-examinees-list-items-select' 
            defaultValue={docFormat(examinee.document)} 
            onChange={e=>updateDocs(index,e)}>
            <option value="대기" hidden>대기</option>
            <option value="미제출" >미제출</option>
            <option value="제출">제출</option>
          </select>
          <button 
          className='director-examinees-list-items-btn'
          onClick={()=>handleCompensationModal(examinee.examineeNo, params['no'])}>
            보상신청
          </button>
          <button  
            className='director-examinees-list-items-btn'
            onClick={()=>handleDocsModal()}>
              추가 서류
          </button>
        </li> 
      )
    })},[examineesData,docFormat,handleCompensationModal,
      handleExamineeModal,params, attendanceFormat, changeAttendance, 
      lateAttendance ,handleDocsModal, updateDocs])


  return (      
    <section className='exam-detail'>
        <aside className='exam-detail-aside'>
          <div className='exam-detail-aside-box'>
            <div className='exam-detail-aside-title'>시험정보</div>
            <div className='exam-detail-aside-title-box'>
              <p>{examData['centerName']}</p>
              <p>{getTime(examData['examDate'],examData['runningTime'])[0]} ~ {getTime(examData['examDate'],examData['runningTime'])[1]} ({examData['runningTime']}분)</p>
               {directorAttendance? <p className={`exam-detail-aside-title-box-${directorAttendance}`}>감독관 센터 도착 완료</p>
               :<p className={`exam-detail-aside-title-box-${directorAttendance}`}>센터 도착 인증을 해주세요</p>}          
            </div>
            <button 
            className='exam-detail-aside-btn'
            onClick={()=>{
              directorAttend()
            }}
            >감독관 센터 도착 인증</button>
            <div className='exam-detail-aside-title'>응시자 현황</div>
            <div className='exam-detail-aside-title-box'>
              <p>응시자 수: {examineesData.length}명</p>
              <p>출결 현황: {attendanceCnt()}명/{examineesData.length}명</p>
              <p>서류 현황: {docCnt()}명/{examineesData.length}명</p>
            </div>
            {/* api 연결 필요 */}
            <button className='exam-detail-aside-btn'>문의하기</button>
          </div>
        </aside>
        <div className='exam-detail-examinees-show'>응시자 목록</div>
        <article className='exam-detail-examinees'>
            <div className='exam-detail-examinees-box'>
              <ul className='director-examinees-list-title'>
                <li>수험번호</li>
                <li>응시자 이름</li>
                <li>출결 </li>
                <li>출결 시간</li>
                <li>서류</li>
                <li>보상 신청</li>
                <li>추가 서류</li>
              </ul>
              <ul className='exam-detail-examinees-list'>
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