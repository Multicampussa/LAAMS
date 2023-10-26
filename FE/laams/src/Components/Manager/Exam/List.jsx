import React, { useCallback } from 'react'
import { useDispatch } from 'react-redux';
import { setModalShow, setModalType } from '../../../redux/actions/modalAction';

const List = () => {
  const dispatch = useDispatch();
  const handleExamCreate = useCallback(()=>{
    dispatch(setModalType("exam-create"));
    dispatch(setModalShow(true));
  },[dispatch]);
  return (
    <section className='list'>
      <div className='list-title'>
        <div className='manager-exam-list-title'>
          <h1>시험일정</h1>
          <ul className='manager-exam-list-search'>
            <li><input type='date'/></li>
            <li>
              <select>
                <option>서울</option>
              </select>
            </li>
            <li>
              <select>
                <option>4어쩌구점</option>
              </select>
            </li>
          </ul>
        </div>
        <button className='manager-exam-list-create' onClick={handleExamCreate}>생성</button>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-exam-list-item'>
            <div>No</div>
            <div>시험 명</div>
            <div>센터 명</div>
            <div>시험 시간</div>
          </li>
          <li className='manager-exam-list-item'>
            <div>1</div>
            <div>SSAPIc</div>
            <div>구미점</div>
            <div>9:00-10:00</div>
          </li>
          <li className='manager-exam-list-item'>
            <div>2</div>
            <div>SSAPIc</div>
            <div>서울점</div>
            <div>10:00-11:00</div>
          </li>
        </ul>
        <ul className='flex-row-center gap-1'>
          <li><button>처음</button></li>
          <li><button>이전</button></li>
          <li><button>1</button></li>
          <li><button>다음</button></li>
          <li><button>끝</button></li>
        </ul>
      </div>
    </section>
  )
}

export default List