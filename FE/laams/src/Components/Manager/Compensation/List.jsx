import React, { useCallback, useEffect, useMemo, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { useDispatch, useSelector } from 'react-redux';
import { setModalShow, setModalType } from './../../../redux/actions/modalAction';
import { setManagerCompensationExamNo, setManagerCompensationIsResetList, setManagerCompensationNo } from './../../../redux/actions/managerCompensationAction';

const List = () => {
  const api = useApi();
  const dispatch = useDispatch();
  const [compensationData,setcompensationData] = useState([]);
  const isResetList = useSelector(state=>state.ManagerCompensation.isResetList);
  const [status,setStatus] = useState("보상_대기");

  useEffect(()=>{
    if(!api) return;
    if(isResetList){}
    api.get(`manager/examinees/compensationStatus?pageNumber=0&pageSize=10&status=${status}`)
      .then(({data})=>{
        console.log(data);
        setcompensationData(data.content);
      })
      .catch(err=>console.log(err.response));

  },[api,isResetList,status,dispatch]);

  const handleItem = useCallback((payload)=>{
    const {examineeCode,examNo} = payload;
    dispatch(setModalShow(true));
    dispatch(setModalType("manager-compensation"));
    dispatch(setManagerCompensationNo(examineeCode));
    dispatch(setManagerCompensationExamNo(examNo));
  },[dispatch])

  const compensationItems = useMemo(()=>{
    if(!compensationData) return;
    return compensationData.map((e,idx)=>{
      const examDate = new Date(e.examDate);
      return <li onClick={()=>handleItem({examineeCode:e.examineeCode,examNo:e.examNo})} key={idx} className='manager-compensation-list-item'>
        <div>{e.examNo}</div>
        <div>{e.examineeCode}</div>
        <div>{e.examineeName}</div>
        <div>{e.compensationType}</div>
        <div>{`${examDate.getFullYear()}.${examDate.getMonth()+1}.${examDate.getDate()}`}</div>
      </li>
    });
  },[compensationData,handleItem])

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>보상</h3>
      </div>
      <div className='list-box manager-compensation-list'>
        <select className='manager-compensation-list-select' onChange={e=>setStatus(e.target.value)}>
          <option value="보상_대기">보상 대기</option>
          <option value="보상_승인">보상 승인</option>
          <option value="보상_거절">보상 거절</option>
        </select>
        <ul>
          <li className='manager-compensation-list-item'>
            <div>시험 번호</div>
            <div>수험 번호</div>
            <div>이름</div>
            <div>보상 유형</div>
            <div>요청 날짜</div>
          </li>
          {
            compensationItems
          }
        </ul>
      </div>
    </section>
  )
}

export default List