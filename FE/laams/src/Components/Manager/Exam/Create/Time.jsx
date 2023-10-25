import React, { useCallback } from 'react'

const Time = ({setType,data}) => {
  const handlePrev = useCallback(()=>{setType("exam")},[setType]);

  const handleCreate = useCallback(()=>{
    console.log(data);
  },[data]);

  return (
    <>
      <div className='modal-title'>시험 정보</div>
      <div className='modal-main'>
        
      </div>
      <div className='flex-row-sb-center'>
        <button className='modal-btn-half' onClick={handlePrev}>이전</button>
        <button className='modal-btn-half' onClick={handleCreate}>생성</button>
      </div>
    </>
  )
}

export default Time