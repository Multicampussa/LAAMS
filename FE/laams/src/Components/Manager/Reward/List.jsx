import React from 'react'

const List = () => {

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>보상</h3>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-reward-list-item'>
            <div>No</div>
            <div>이름</div>
            <div>수험번호</div>
            <div>감독관</div>
            <div>보상 유형</div>
          </li>
          <li className='manager-reward-list-item'>
            <div>1</div>
            <div>김철수</div>
            <div>1</div>
            <div>김감독</div>
            <div>지각</div>
          </li>
          <li className='manager-reward-list-item'>
            <div>2</div>
            <div>박경림</div>
            <div>2</div>
            <div>김감독</div>
            <div>서류미비</div>
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