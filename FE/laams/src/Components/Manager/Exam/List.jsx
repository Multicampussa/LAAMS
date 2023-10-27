import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { useDispatch } from 'react-redux';
import { setModalShow, setModalType } from '../../../redux/actions/modalAction';
import useApi from './../../../Hook/useApi';
import { setExamNo } from '../../../redux/actions/managerExamDetailAction';

const List = () => {
  const dispatch = useDispatch();
  const api = useApi();
  const [date,setDate] = useState(new Date());
  const [location,setLocation] = useState("서울");
  const [center,setCenter] = useState("SSAPIc서울SDA센터");
  const [data,setData] = useState([]);

  const getDateFormat = useCallback(()=>{
    let month = date.getMonth()+1;
    return `${date.getFullYear()}-${month < 10 ? "0"+month:month}-${date.getDate()}`;
  },[date])

  const getExamList = useCallback(async(date)=>{
    await api.get(`manager/exam/daily?year=${date.getFullYear()}&month=${date.getMonth()+1}&day=${date.getDate()}`)
    .then(({data})=>setData(data)).catch(err=>console.log(err.response));
  },[api]);

  const handleExamItem = useCallback((no)=>{
    dispatch(setExamNo(no));
    dispatch(setModalType("exam-detail"));
    dispatch(setModalShow(true));
  },[dispatch]);
  
  //TODO : 시험목록데이터를 불러와 Div를 반환
  const examItems = useMemo(()=>{
    return data.map((e,idx)=>{
      const curDate = new Date(e.examDate);
      return <li onClick={()=>handleExamItem(e.no)} key={idx} className='manager-exam-list-item'>
        <div>{e.no}</div>
        <div>{e.examType} {e.examLanguage}</div>
        <div>{e.centerName}</div>
        <div>{curDate.getHours()}:{curDate.getMinutes()}</div>
      </li>
    });
  },[data,handleExamItem])

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
              <select onChange={(e)=>setLocation(e.target.value)}>
                <option>서울</option>
              </select>
            </li>
            <li>
              <select onChange={e=>setCenter(e.target.value)}>
                <option>SSAPIc서울SDA센터</option>
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