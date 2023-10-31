import React, { useCallback, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import useApi from '../../../Hook/useApi';

const ExamineeDetail = () => {
  const examineeNo = useSelector(state=>state.ExamineeDetail.examineeNo);
  const examNo = useSelector(state=>state.ManagerExamDetail.examNo);
  const api = useApi();
  const [examineeData,setExamineeData] = useState({});
 //TODO : 응시자 상세 정보 조회
 const getExamineeDetail = useCallback(async()=>{
  await api.get(`director/exams/${examNo}/examinees/${examineeNo}`)
  .then(({data})=>{
    setExamineeData({
      email : data.data.email,
      gender : data.data.gender,
      name : data.data.name,
      phone : data.data.phoneNum,
    })
  })
  .catch(err=>console.log(err))
 },[examNo,examineeNo,api])
 
 useEffect(()=>{
  getExamineeDetail()
 },[getExamineeDetail])
 
 //TODO : 성별 형식 한글화
 const genderFormat = ((gender)=>{
  if(gender==='male'){
    return '남성'
  }else{
    return '여성'
  }
 })

  return (
    <section className='examinee-detail'>
        <div className='examinee-detail-container'>
            <div className='examinee-detail-container-title'>
                응시자 상세정보
            </div>
            <div className='examinee-detail-box'>
                <aside className='examinee-detail-box-aside'>
                  <div className='examinee-detail-box-aside-text'>
                    이름
                  </div>
                  <div className='examinee-detail-box-aside-text'>
                    이메일
                  </div>
                  <div className='examinee-detail-box-aside-text'>
                    전화번호
                  </div>
                  <div className='examinee-detail-box-aside-text'>
                    성별
                  </div>
                </aside>
                <article className='examinee-detail-box-content'>
                  <div className='examinee-detail-box-content-text'>
                    {examineeData['name']}
                  </div>
                  <div className='examinee-detail-box-content-text'>
                    {examineeData['email']}
                  </div>
                  <div className='examinee-detail-box-content-text'>
                    {examineeData['phone']}
                  </div>
                  <div className='examinee-detail-box-content-text'>
                    {genderFormat(examineeData['gender'])}
                  </div>
                </article>
                
        </div>
        </div>
    </section>
  )
}

export default ExamineeDetail