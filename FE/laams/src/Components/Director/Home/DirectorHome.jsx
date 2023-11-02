import React, { useCallback, useEffect, useState } from 'react'
import Calendar from '../../Common/Calendar/Calendar'
import useApi from '../../../Hook/useApi';
import { useSelector } from 'react-redux';
const DirectorHome = () => {

  const [examList,setExamList] = useState();
  const api = useApi();
  const [curDate,setCurDate] = useState(new Date());
  const userId = useSelector(state=>state.User.memberId);
  const getDirectorInfo = useCallback(async()=>{
    const response = await api.get(`member/info/${userId}`);
    return response.data.data.memberNo;
  },[api,userId])

  const getExamList = useCallback(async(date)=>{
    const directorUserNo = await getDirectorInfo();
    await api.get(`director/${directorUserNo}/exams?year=${date.getFullYear()}&month=${date.getMonth()+1}`)
    .then(({data})=>{
      const res = {};
      data.data.forEach(e=>{
        
        const date = new Date(e.examDate);
        if(res[date.getDate()]){
          res[date.getDate()].push({
            centerName : e.centerName,
            centerRegion : e.centerRegion,
            examDate : new Date(e.examDate),
            examType : e.examType,
            examLanguage : e.examLanguage
          });
        }else{
          res[date.getDate()]=[{
            centerName : e.centerName,
            centerRegion : e.centerRegion,
            examDate : new Date(e.examDate),
            examType : e.examType,
            examLanguage : e.examLanguage
          }];
        }
      });

      setExamList(res);
    }).catch(err=>console.log(err.response));
  },[api,getDirectorInfo]);

  useEffect(()=>{
    if(!curDate) return;
    getExamList(curDate);
  },[getExamList,curDate]);

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
    setCurDate(new Date(year,month,1));
  },[curDate]);

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
    setCurDate(new Date(year,month,1));
  },[curDate])

  return (
    <section className='director-home'>
      <div className='director-home-container'>
        <article className='director-home-task'>
          <div className='director-home-task-title'>시험 일정</div>
          <div className='director-home-task-calendar'>
          <Calendar handleNext={handleNext} handlePrev={handlePrev} curDate={curDate} examList={examList}></Calendar>
          </div>
        </article>
        <article className='director-home-todo'>
          <div className='director-home-todo-title'>오늘 일정</div>
          <div className='director-home-todo-box'>
            <button className='director-home-todo-box-btn'>감독관 센터 도착 인증</button>
            <div className='director-home--todo-box-items'></div>
          </div>
        </article>
        <article className='director-home-notice'>
          <div className='director-home-notice-flex'>
            <div className='director-home-notice-flex-title'>공지사항</div>
            <div className='director-home-notice-flex-additional'>+ 더보기</div>
          </div>
          <div className='director-home-notice-box'>
            <div className='director-home-notice-box-items'></div> 
          </div>
        </article>
      </div>
    </section>
  )
}

export default DirectorHome