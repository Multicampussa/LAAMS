import React, { useCallback, useEffect, useMemo, useState } from 'react'
import Calendar from './Calendar'
import useApi from '../../../Hook/useApi';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
const DirectorHome = () => {

  const [examList,setExamList] = useState();
  const api = useApi();
  const navigate = useNavigate();
  const [curDate,setCurDate] = useState(new Date());
  const [todayExamList, setTodayExamList] = useState([]);
  const [noticeListData, setNoticeListData] = useState([]);
  const userId = useSelector(state=>state.User.memberId);
  const getDirectorInfo = useCallback(async()=>{
    if(!userId || !api){
      return
    }
    const response = await api.get(`member/info/${userId}`);
    return response.data.data.memberNo;
  },[api,userId])

  // TODO :오늘자의 시험(5개) 조회
  const getTodayExamList = useCallback(async(curDate)=>{
    const directorUserNo = await getDirectorInfo();
    await api.get(`director/${directorUserNo}/exams?year=${curDate.getFullYear()}&month=${curDate.getMonth()+1}&day=${curDate.getDate()}`)
    .then(({data})=>{
      setTodayExamList(data.data.slice(0,5))
    }).catch((err)=>{console.log(err)})
    
  },[setTodayExamList,api,getDirectorInfo])

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
            examLanguage : e.examLanguage,
            examNo: e.examNo
          });
        }else{
          res[date.getDate()]=[{
            centerName : e.centerName,
            centerRegion : e.centerRegion,
            examDate : new Date(e.examDate),
            examType : e.examType,
            examLanguage : e.examLanguage,
            examNo: e.examNo
          }];
        }
      });

      setExamList(res);

    }).catch(err=>console.log(err.response));
  },[api,getDirectorInfo]);

  //
  const showTodayExamList = useMemo(()=>{
    if(todayExamList.length===0){
      return <li className='director-home-todo-box-items-text'>오늘의 일정이 없습니다</li>
    }else{
      return todayExamList.map((e,index)=>{

        return <div className='director-home-todo-box-items'>
                <div className='director-home-todo-box-items-text'
                onClick={()=>navigate(`/director/exam/${e.examNo}`)}>[{e.examType}]{e.centerName}</div>
                <div className='director-home-todo-box-items-text-region'>({e.centerRegion})</div>
              </div>
      })
    }
  },[todayExamList,navigate])

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
    api.get('app/notice/list?count=6&page=1')
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
        const month = createTime.getMonth()<10? '0'+createTime.getMonth():createTime.getMonth();
        const day = createTime.getDay() <10? '0' + createTime.getDay():createTime.getDay();
        const hours = createTime.getHours() <10? '0'+createTime.getHours():createTime.getHours();
        const min = createTime.getMinutes() <10? '0'+createTime.getMinutes():createTime.getMinutes(); 
        
        return(
          <div className='director-home-notice-box-items'
          onClick={()=>{
            navigate(`/notice/detail/${notice.noticeNo}`)
          }}>
            <div className='director-home-notice-box-items-title'>{notice.title}</div>
            <div className='director-home-notice-box-items-time'>{year}-{month}-{day} {hours}:{min}</div>
          </div>
        )
    })
  }
  },[noticeListData,navigate])
  
  useEffect(()=>{
    if(!curDate) return;
    getExamList(curDate);
    getTodayExamList(curDate);
    getNotice();
  },[getNotice,getExamList,curDate,getTodayExamList]);

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
              {
                showTodayExamList
              }
          </div>
        </article>
        <article className='director-home-notice'>
          <div className='director-home-notice-flex'>
            <div className='director-home-notice-flex-title'>공지사항</div>
            <div className='director-home-notice-flex-additional'
            onClick={()=>{
              navigate('/notice')
            }}
            >+ 더보기</div>
          </div>
          <div className='director-home-notice-box'>
            {
              showNotice
            }  
          </div>
        </article>
        <button className='director-home-btn-hidden'>감독관 센터 도착 인증</button>
      </div>
    </section>
  )
}

export default DirectorHome