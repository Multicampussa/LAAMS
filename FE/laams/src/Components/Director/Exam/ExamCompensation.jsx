import React, { useCallback, useState } from 'react'
import useApi from '../../../Hook/useApi'
import { useSelector } from 'react-redux';

const ExamCompensation = () => {
  const api = useApi();
  const examineeNo = useSelector(state=>state.ExamineeDetail.examineeNo);
  const examNo = useSelector(state=>state.ManagerExamDetail.examNo);
  const [compensationData,setCompensationData] = useState({});
  const sendCompensation = useCallback(async()=>{
    await api.post(`director/exams/${examNo}/examinees/${examineeNo}/applyCompensation`, {
        "compensationReason": compensationData['content'],
        "compensationType": compensationData['type']?  compensationData['type']: '지각'
    })
    .then(()=>{
        alert('보상 신청이 완료되었습니다')
        setCompensationData({});
        
        console.log(compensationData)
    })
    .catch((err)=>{console.log(err)
        console.log(compensationData)})
  },[examNo,examineeNo,compensationData,api])

  const handleType = useCallback((e)=>{
    setCompensationData(prevState => ({
      ...prevState,
      type: e.target.value
    }));
  },[])
  
  const handleText = useCallback(e=>{
    setCompensationData(prevState => ({
      ...prevState,
      content: e.target.value
    }));
  },[])

  return (
    <section className='compensation'>
        <div className='compensation-container'>
            <div className='compensation-container-title'>
            보상 신청
            </div>    
            <div className='compensation-container-box'>
                <div className='compensation-container-box-title'>
                    보상 사유
                </div>
                <select className='compensation-container-box-select'
                value={compensationData['type'] || '지각'}
                    onChange={e=>handleType(e)}>
                    <option value="지각">지각</option>
                    <option value="서류 미비">서류 미비</option>
                    <option value="기타">기타</option>
                </select>
                <div className='compensation-container-box-title'>
                    내용
                </div>
                <textarea className='compensation-container-box-input-l' 
                onChange={e=>handleText(e)} value={compensationData['content'] || ''}
                />          
            </div>
            <button className='compensation-btn'
            onClick={()=>sendCompensation()}>
                작성
            </button>
        </div>        

    </section>
  )
}

export default ExamCompensation