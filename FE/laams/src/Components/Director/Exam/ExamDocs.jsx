import React from 'react'

const ExamDocs = () => {
  return (
    <section className='exam-docs'>
        <div className='exam-docs-container'>
            <div className='exam-docs-container-title'>
                추가 서류 제출
            </div>
            <div className='exam-docs-box'>
                <div className='exam-docs-box-image'>

                </div>
                <div className='exam-docs-box-file'>
                    <input className="exam-docs-box-file-input" type="file" />
                </div>
            </div>
            <button className='exam-docs-btn'>파일 저장</button>
        </div>
    </section>
  )
}

export default ExamDocs