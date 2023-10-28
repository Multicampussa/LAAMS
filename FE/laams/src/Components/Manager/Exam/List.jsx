import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useDispatch } from 'react-redux';
import { setModalShow, setModalType } from '../../../redux/actions/modalAction';
import useApi from './../../../Hook/useApi';
import { setExamNo } from '../../../redux/actions/managerExamDetailAction';
import useCenter from './../../../Hook/useCenter';

const List = () => {
  const dispatch = useDispatch();
  const api = useApi();
  const [date,setDate] = useState(new Date());
  const centerData = useCenter();
  const [locationData,setLocationData] = useState();
  const [location,setLocation] = useState();
  const [center,setCenter] = useState();
  const [data,setData] = useState([]);

  //TODO : 지역목록 저장
  useEffect(()=>{
    if(!centerData) return;
    setLocationData(Object.keys(centerData).reverse());
  },[centerData]);

  //TODO : 지역목록이 갱신되었을 때 센터 default value 세팅
  useEffect(()=>{
    if(!location || !centerData || centerData[location].length === 0) return;
    setCenter(centerData[location][0].centerName);
  },[location,centerData]);

  //TODO : 센터 Option List 호출
  const centerItems = useMemo(()=>{
    if(!centerData || !location) return [];
    if(!centerData[location]) return [];
    return centerData[location].map((e,idx)=><option key={idx}>{e.centerName}</option>);
  },[location,centerData]);

  //TODO : 지역 Option List 호출
  const locationItems = useMemo(()=>{
    if(!locationData || locationData.length === 0) return [];
    setLocation(locationData[0]);
    return locationData.map((e,idx)=><option key={idx}>{e}</option>)
  },[locationData])

  //TODO : Date를 YYYY-MM-dd형식으로 변환
  const getDateFormat = useCallback(()=>{
    let month = date.getMonth()+1;
    return `${date.getFullYear()}-${month < 10 ? "0"+month:month}-${date.getDate()}`;
  },[date])

  //TODO : 시험일정을 불러옴
  const getExamList = useCallback(async(date)=>{
    await api.get(`manager/exam/daily?year=${date.getFullYear()}&month=${date.getMonth()+1}&day=${date.getDate()}`)
    .then(({data})=>setData(data)).catch(err=>console.log(err.response));
  },[api]);


  //TODO : 시험상세정보 모달 호출
  const handleExamItem = useCallback((no)=>{
    dispatch(setExamNo(no));
    dispatch(setModalType("exam-detail"));
    dispatch(setModalShow(true));
  },[dispatch]);
  
  //TODO : 시험목록데이터를 불러와 Div를 반환
  const examItems = useMemo(()=>{
    const res = [];
    data.forEach((e,idx)=>{
      if(e.centerName === center){
        const curDate = new Date(e.examDate);
        res.push(<li onClick={()=>handleExamItem(e.no)} key={idx} className='manager-exam-list-item'>
          <div>{e.no}</div>
          <div>{e.examType} {e.examLanguage}</div>
          <div>{e.centerName}</div>
          <div>{curDate.getHours()}:{curDate.getMinutes()}</div>
        </li>);
      }
    });
    return res;
  },[data,center,handleExamItem])

  //TODO : 날짜가 변경되면 목록을 가져옴
  useEffect(()=>{
    getExamList(date);
  },[date,getExamList])

  const handleExamCreate = useCallback(()=>{
    dispatch(setModalType("exam-create"));
    dispatch(setModalShow(true));
  },[dispatch]);

  return (
    <section className='list'>
      <div className='list-title'>
        <div className='manager-exam-list-title'>
          <h3>시험일정</h3>
          <ul className='manager-exam-list-search'>
            <li><input type='date' value={getDateFormat()} onChange={e=>setDate(new Date(e.target.value))} /></li>
            <li>
              <select value={location} onChange={e=>setLocation(e.target.value)}>
                {locationItems}
              </select>
            </li>
            <li>
              <select value={center} onChange={e=>setCenter(e.target.value)}>
                {centerItems}
              </select>
            </li>
          </ul>
        </div>
        <button className='manager-exam-list-create' onClick={handleExamCreate}>생성</button>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-exam-list-item'>
            <div>No</div>
            <div>시험 명</div>
            <div>센터 명</div>
            <div>시험 시간</div>
          </li>
          {
            examItems
          }
        </ul>
        <ul className='flex-row-center gap-1'>
          <li><button>처음</button></li>
          <li><button>이전</button></li>
          <li><button>1</button></li>
          <li><button>다음</button></li>
          <li><button>끝</button></li>
        </ul>
      </div>
    </section>
  )
}

export default List