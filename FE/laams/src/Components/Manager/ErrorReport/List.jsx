import React from 'react'

const List = () => {

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>에러리포트</h3>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-errorreport-list-item'>
            <div>No</div>
            <div>제목</div>
            <div>에러 유형</div>
            <div>감독관</div>
            <div>발생시간</div>
          </li>
          <li className='manager-errorreport-list-item'>
            <div>1</div>
            <div>헤드셋 고장 발생한 에러보고합니다</div>
            <div>장비오류</div>
            <div>김철수</div>
            <div>9:00-10:00</div>
          </li>
          <li className='manager-errorreport-list-item'>
            <div>2</div>
            <div>헤드셋 고장 발생한 에러보고합니다</div>
            <div>장비오류</div>
            <div>김철수</div>
            <div>9:00-10:00</div>
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