import React, { useCallback, useEffect, useMemo, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { useDispatch } from 'react-redux';
import { setModalShow, setModalType } from './../../../redux/actions/modalAction';
import { setManagerCompensationNo } from './../../../redux/actions/managerCompensationAction';

const List = () => {
  const api = useApi();
  const dispatch = useDispatch();
  const [compensationData,setcompensationData] = useState([]);
  useEffect(()=>{
    if(!api) return;
    api.get("manager/examinees/compensation")
      .then(({data})=>{setcompensationData(data)})
      .catch(err=>console.log(err.response));
  },[api]);

  const handleItem = useCallback((examineeCode)=>{
    dispatch(setModalShow(true));
    dispatch(setModalType("manager-compensation"));
    dispatch(setManagerCompensationNo(examineeCode));
  },[dispatch])

  const compensationItems = useMemo(()=>{
    if(!compensationData) return;
    return compensationData.map((e,idx)=><li onClick={()=>handleItem(e.examineeCode)} key={idx} className='manager-compensation-list-item'>
    <div>{e.examNo}</div>
    <div>{e.examineeCode}</div>
    <div>{e.examineeName}</div>
    <div>{e.compensationType}</div>
  </li>);
  },[compensationData,handleItem])

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>보상</h3>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-compensation-list-item'>
            <div>시험 번호</div>
            <div>수험 번호</div>
            <div>이름</div>
            <div>보상 유형</div>
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