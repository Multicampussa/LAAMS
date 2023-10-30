import React from 'react'

const ExamCompensation = () => {
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
                <select >
                    <option value="">지각</option>
                    <option value="">서류 미비</option>
                    <option value="">기타</option>
                </select>
                <div className='compensation-container-box-title'>
                    제목
                </div>
                <input className='compensation-container-box-input-s' />
                <div className='compensation-container-box-title'>
                    내용
                </div>
                <textarea className='compensation-container-box-input-l' />          
            </div>
            <button className='compensation-btn'>
                    작성
            </button>
        </div>        

    </section>
  )
}

export default ExamCompensation