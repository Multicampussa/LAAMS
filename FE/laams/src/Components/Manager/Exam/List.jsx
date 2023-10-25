import React from 'react'

const List = () => {
  return (
    <div className='manager-exam-list'>
      <div className='manager-exam-list-title'>시험일정</div>
      <div className='manager-exam-list-search'>
        <input type='date'/>
        <select>
          <option>서울</option>
        </select>
        <select>
          <option>4어쩌구점</option>
        </select>
      </div>
      <ul className='manager-exam-list-container'>
      <button className='manager-exam-list-btn-create'>생성</button>
        <li className='manager-exam-list-box'>
          <div className='manager-exam-list-item1'>No</div>
          <div className='manager-exam-list-item2'>시험 명</div>
          <div className='manager-exam-list-item3'>센터 명</div>
          <div className='manager-exam-list-item4'>시험 시간</div>
        </li>
        <li className='manager-exam-list-box'>
          <div className='manager-exam-list-item1'>No</div>
          <div className='manager-exam-list-item2'>시험 명</div>
          <div className='manager-exam-list-item3'>센터 명</div>
          <div className='manager-exam-list-item4'>시험 시간</div>
        </li>
        <li className='manager-exam-list-box'>
          <div className='manager-exam-list-item1'>No</div>
          <div className='manager-exam-list-item2'>시험 명</div>
          <div className='manager-exam-list-item3'>센터 명</div>
          <div className='manager-exam-list-item4'>시험 시간</div>
        </li>
      </ul>
      <ul className='manager-exam-list-navigation'>
        <li>{"<<"}</li>
        <li>{"<"}</li>
        <li>1</li>
        <li>{">"}</li>
        <li>{">>"}</li>
      </ul>
    </div>
  )
}

export default List