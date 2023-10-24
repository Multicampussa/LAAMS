import React, { useCallback, useEffect, useState } from 'react'

const ManagerHome = () => {
  const [page,setPage]=useState(1);
  const [title,setTitle] = useState("시험일정");
  const sliderPrev = useCallback(()=>{
    if(page === 1) return;
    setPage(page-1);
  },[page]);
  const sliderNext = useCallback(()=>{
    if(page === 4) return;
    setPage(page+1);
  },[page]);

  useEffect(()=>{
    switch(page){
      case 1:
        setTitle("시험일정");
      break;
      case 2:
        setTitle("보상");
      break;
      case 3:
        setTitle("에러리포트");
        break;
      case 4:
        setTitle("현황");
        break;
      default:
        setTitle("시험일정");
        break;
    }
    
  },[page]);
  return (
    <section className='manager-home'>
      <div className='manager-home-title'>
        <button onClick={sliderPrev}>이전</button>
        <div>{title}</div>
        <button onClick={sliderNext}>다음</button>
      </div>
      <ul className={`manager-home-slider-${page}`}>
        <li className='manager-home-schedule'>
          
        </li>
        <li className='manager-home-reward'>

        </li>
        <li className='manager-home-report'>

        </li>
        <li className='manager-home-chart'>

        </li>
      </ul>
    </section>
  )
}

export default ManagerHome