import React, { useEffect, useMemo, useState } from 'react'
import useApi from './../../../Hook/useApi';

const List = () => {
  const api = useApi();
  const [rewardData,setRewardData] = useState([]);
  useEffect(()=>{
    if(!api) return;
    api.get("manager/examinees/compensation")
      .then(({data})=>{setRewardData(data)})
      .catch(err=>console.log(err.response));
  },[api]);

  const rewardItems = useMemo(()=>{
    if(!rewardData) return;
    return rewardData.map((e,idx)=><li key={idx} className='manager-reward-list-item'>
    <div>{e.examNo}</div>
    <div>{e.examineeCode}</div>
    <div>{e.examineeName}</div>
    <div>{e.compensationType}</div>
  </li>);
  },[rewardData])

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>보상</h3>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-reward-list-item'>
            <div>시험 번호</div>
            <div>수험 번호</div>
            <div>이름</div>
            <div>보상 유형</div>
          </li>
          {
            rewardItems
          }
        </ul>
      </div>
    </section>
  )
}

export default List