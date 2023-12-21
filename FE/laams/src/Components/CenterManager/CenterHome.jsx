import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import useApi from '../../Hook/useApi';
import Calendar from './Calendar';

const CenterHome = () => {
  const [examList,setExamList] = useState();
  const api = useApi();
  const navigate = useNavigate();
  const [curDate,setCurDate] = useState(new Date());
  const [todayExamList, setTodayExamList] = useState([]);
  const [noticeListData, setNoticeListData] = useState([]);


  // TODO :오늘자의 시험(5개) 조회
  const getTodayExamList = useCallback(async(curDate)=>{
    
    await api.get(`centermanager/exams?year=${curDate.getFullYear()}&month=${curDate.getMonth()+1}&day=${curDate.getDate()}`)
    .then(({data})=>{
      setTodayExamList(data.data.slice(0,5))
    }).catch((err)=>{console.log(err)})
    
  },[setTodayExamList,api])

  const getExamList = useCallback(async(date)=>{
    await api.get(`centermanager/exams?year=${date.getFullYear()}&month=${date.getMonth()+1}`)
    .then(({data})=>{
      const res = {};
      data.data.forEach(e=>{
        const date = new Date(e.examDate);
        if(res[date.getDate()]){
          res[date.getDate()].push({
            examDate : new Date(e.examDate),
            examType : e.examType,
            examLanguage : e.examLanguage,
            examNo: e.examNo,
            maxDirector: e.maxDirector,
            confirmDirectorCnt: e.confirmDirectorCnt
          });
        }else{
          res[date.getDate()]=[{
            examDate : new Date(e.examDate),
            examType : e.examType,
            examLanguage : e.examLanguage,
            examNo: e.examNo,
            maxDirector: e.maxDirector,
            confirmDirectorCnt: e.confirmDirectorCnt
          }];
        }
      });

      setExamList(res);

    }).catch(err=>console.log(err.response));
  },[api]);
  

  //
  const showTodayExamList = useMemo(()=>{
    if(todayExamList.length===0){
      
      return <div>
              <div className='center-home-todo-box-items-text-none'>오늘의 일정이 없습니다</div>
            </div>

    }else{
      return todayExamList.map((e,index)=>{

        return <div className='center-home-todo-box-items' key={index}>
                <div className='center-home-todo-box-items-text'
                >[{e.examType}]{e.centerName}</div>
              </div>
      })
    }
  },[todayExamList])

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
  const getNotice = useCallback(()=>{
    api.get('/notice/list?count=6&page=1')
    .then(({data})=>{
      setNoticeListData(data.data)
    })
    .catch((err)=>{
      console.log(err)
    })
  },[api])
  const showNotice = useMemo(()=>{
    if(!noticeListData){
      return <li>공지사항이 없습니다</li>
    }else{
      return noticeListData.map((notice, index) => {
        const createTime = new Date(notice.createdAt);
        const year = createTime.getFullYear();
        const month = createTime.getMonth()+1 <10? '0'+ (createTime.getMonth()+1):createTime.getMonth()+1;
        const day = createTime.getDate() <10? '0' + createTime.getDate():createTime.getDate();
        const hours = createTime.getHours() <10? '0'+createTime.getHours():createTime.getHours();
        const min = createTime.getMinutes() <10? '0'+createTime.getMinutes():createTime.getMinutes(); 
        
        return(
          <div className='center-home-notice-box-items'
          onClick={()=>{
            navigate(`/notice/detail/${notice.noticeNo}`)
          }} key={index}>
            <div className='center-home-notice-box-items-title'>{notice.title}</div>
            <div className='center-home-notice-box-items-time'>{year}-{month}-{day} {hours}:{min}</div>
          </div>
        )
    })
  }
  },[noticeListData,navigate])

  useEffect(()=>{
    const today =  new Date();
    if(!curDate) return;
    getExamList(curDate);
    getTodayExamList(today);
    getNotice();
  },[getNotice,getExamList,curDate,getTodayExamList]);
  return (
    <section className='center-home'>
      <div className='center-home-container'>
        <article className='center-home-task'>
          <div className='center-home-task-title'>시험 일정</div>
          <div className='center-home-task-calendar'>
          <Calendar handleNext={handleNext} handlePrev={handlePrev} curDate={curDate} examList={examList}></Calendar>
          </div>
        </article>
        <article className='center-home-todo'>
          <div className='center-home-todo-title'>오늘 일정</div>
          <div className='center-home-todo-box'>
              {
                showTodayExamList
              }
          </div>
        </article>
        <article className='center-home-notice'>
          <div className='center-home-notice-flex'>
            <div className='center-home-notice-flex-title'>공지사항</div>
            <div className='center-home-notice-flex-additional'
            onClick={()=>{
              navigate('/notice')
            }}
            >+ 더보기</div>
          </div>
          <div className='center-home-notice-box'>
            {
              showNotice
            }  
          </div>
        </article>
      </div>
    </section>
  )
}

export default CenterHome