import React, { useCallback } from 'react'

const Time = ({setType,data}) => {
  const handlePrev = useCallback(()=>{setType("exam")},[setType]);

  const handleCreate = useCallback(()=>{
    console.log(data);
  },[data]);

  return (
    <>
      <div className='modal-title'>시험 정보</div>
      <div className='exam-create modal-main'>
        <div className='exam-create-title'>센터</div>
        <div>
          <span className='exam-create-subtitle'>지역</span>
          <span className='exam-create-name'>{data.city.name}</span>
        </div>
        <div>
          <span className='exam-create-subtitle'>센터명</span>
          <span className='exam-create-name'>{data.center.name}</span>
        </div>
        <div className='exam-create-title'>시험</div>
        <div>
          <span className='exam-create-subtitle'>시험명</span>
          <span className='exam-create-name'>{data.test.name}</span>
        </div>
        <div>
          <span className='exam-create-subtitle'>언어</span>
          <span className='exam-create-name'>{data.language.name}</span>
        </div>
        <div className='exam-create-title'>시간</div>
        <div>
          <label className='exam-create-subtitle'>시작 시간<input defaultValue={data.startTime} onChange={(e)=>data.startTime=e.target.value} type='time'/></label>
        </div>
        <div>
          <label className='exam-create-subtitle'>시험 시간<input defaultValue={data.testTime} onChange={(e)=>data.testTime=e.target.value} type='number'/></label>
        </div>
      </div>
      <div className='flex-row-sb-center'>
        <button className='modal-btn-half' onClick={handlePrev}>이전</button>
        <button className='modal-btn-half' onClick={handleCreate}>생성</button>
      </div>
    </>
  )
}

export default Time