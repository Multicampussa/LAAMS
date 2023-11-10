import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import useApi from '../../../Hook/useApi';
import { useState } from 'react';
import { useCallback } from 'react';
import { setModalShow, setModalType } from '../../../redux/actions/modalAction';
import { setManagerCompensationIsResetList } from '../../../redux/actions/managerCompensationAction';

const ExamCompensation = () => {
  const examineeNo = useSelector(state=>state.ManagerCompensation.examineeNo);
  const examNo = useSelector(state=>state.ManagerCompensation.examNo);
  const api = useApi();
  const [data,setData] = useState();
  const dispatch = useDispatch();
  useEffect(()=>{
    if(!examineeNo || !api) {return;}
    api.get(`manager/examinees/compensation/${examineeNo}`)
      .then(({data})=>setData(data));
  },[examineeNo,api]);
  const getDateFormat = useCallback(()=>{
    if(!data)return;
    const date = new Date(data.examDate);
    return `${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
  },[data])
  const handleConfirm = useCallback(()=>{
    if(!examNo||!examineeNo||!api) return;
    api.put("manager/compensation/confirm",{
      "examNo": examNo,
      "examineeNo": examineeNo
    }).then(({data})=>{
      alert(data.data);
      dispatch(setManagerCompensationIsResetList());
      dispatch(setModalType(null));
      dispatch(setModalShow(false));
    }).catch(err=>console.log(err));
  },[examineeNo,examNo,api,dispatch])
  const handleDeny = useCallback(()=>{
    if(!examNo||!examineeNo||!api) return;
    api.put("manager/compensation/deny",{
      "examNo": examNo,
      "examineeNo": examineeNo
    }).then(({data})=>{
      alert(data.data);
      dispatch(setManagerCompensationIsResetList());
      dispatch(setModalType(null));
      dispatch(setModalShow(false));
    }).catch(err=>console.log(err));
  },[examineeNo,examNo,api])
  return (
    <section className='compensation'>
        <div className='compensation-container'>
            <div className='compensation-container-title'>보상</div>    
            <div className='compensation-container-box'>
              <div className='flex-row flex-wrap'>
                <div className='compensation-container-box-row'>
                  <div className='compensation-container-box-title'>보상 사유</div>
                  <div className='compensation-container-box-item'>{data?data.compensationReason:""}</div>
                </div>
                <div className='compensation-container-box-row'>
                  <div className='compensation-container-box-title'>시험</div>
                  <div className='compensation-container-box-item'>{data?data.examType:""}</div>
                </div>
                <div className='compensation-container-box-row'>
                  <div className='compensation-container-box-title'>보상 대상</div>
                  <div className='compensation-container-box-item'>{data?data.examineeName:""}</div>
                </div>
                <div className='compensation-container-box-row'>
                  <div className='compensation-container-box-title'>수험 번호</div>
                  <div className='compensation-container-box-item'>{examineeNo}</div>
                </div>
                <div className='compensation-container-box-row'>
                  <div className='compensation-container-box-title'>장소</div>
                  <div className='compensation-container-box-item'>{data?`[${data.centerRegion}]${data.centerName}`:""}</div>
                </div>
                <div className='compensation-container-box-row'>
                  <div className='compensation-container-box-title'>일시</div>
                  <div className='compensation-container-box-item'>{data?getDateFormat():""}</div>
                </div>

              </div>

              <div className='compensation-container-box-title'>내용</div>
              <textarea readOnly className='compensation-container-box-textarea' value={data&&data.compensationReason?data.compensationReason:""}/>
            </div>
            <div className='flex-row gap-1'>
              <button onClick={handleDeny} className='compensation-btn-cancle'>거절</button>
              <button onClick={handleConfirm} className='compensation-btn'>승인</button>
            </div>
        </div>        

    </section>
  )
}

export default ExamCompensation