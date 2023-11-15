import React, { useCallback, useEffect, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { useSelector } from 'react-redux';
import { useMemo } from 'react';

const Detail = () => {
  const api = useApi();
  const examNo = useSelector(state=>state.ManagerExamDetail.examNo);
  const [data,setData] = useState();

  const getDateFormat = useCallback((date)=>{
    date = new Date(date);
    let month = date.getMonth()+1;
    return `${date.getFullYear()}-${month < 10 ? "0"+month:month}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
  },[])

  // //TODO : 시험번호로 상세정보요청
  useEffect(()=>{
    const getData = async (no)=>{
      try{
        const {data} = await api.get(`manager/exam/${no}`);
        setData(data.data);
      }catch(err){
        console.log(err.response);
      }
    }
    if(examNo){
      getData(examNo);
    }
  },[examNo,api]);

  //TODO : 감독관 목록 반환
  const directorItems = useMemo(()=>{
    if(!data) return [];
    return data.directors.map((e,idx)=><li className='manager-exam-detail-director-item' key={idx}>
      <div>{e.directorName}</div>
      <div>{e.directorAttendance ? "출근" : "결근"}</div>
      <div>{e.directorPhoneNum}</div>
    </li>)
  },[data])
  return (
    <div className='manager-exam-detail'>
      <div className='manager-exam-detail-info'>
        {
          data ? <>
          <div>시험 명</div>
          <div>{data.examType+" "+data.examLanguage}</div>
          <div>센터 명</div>
          <div>{data.centerName}</div>
          <div>시험시간</div>
          <div>{getDateFormat(data.examDate)}</div>
          <div>응시자 수</div>
          <div>{data.examineeNum}</div>
          <div>결시</div>
          <div>{data.examineeNum-data.attendanceNum}</div>
          <div>보상 대상</div>
          <div>{data.compensationNum}</div>
          </>:null
        }
      </div>
      <div className='manager-exam-detail-director'>
        <div className='manager-exam-detail-director-title'>
          <div>감독관 명</div>
          <div>출근</div>
          <div>연락처</div>
        </div>
        <ul className='manager-exam-detail-director-box'>
          {
            directorItems
          }
        </ul>
      </div>
    </div>
  )
}

export default Detail