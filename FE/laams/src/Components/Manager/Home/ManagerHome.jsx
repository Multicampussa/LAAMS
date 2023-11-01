import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom';
import Chart from '../Chart/Chart';
import useCenter from '../../../Hook/useCenter';

const ManagerHome = () => {
  const [page,setPage]=useState(1);
  const [title,setTitle] = useState("시험일정");
  const [chartTitle,setChartTitle] = useState("센터 별 시험 횟수");
  const [selectOpen,setSelectOpen] = useState(false);
  const [region,setRegion] = useState("서울");
  const [regionSelectOpen,setRegionSelectOpen] = useState(false);
  const centerData = useCenter();

  const handleChartRegionItem = useCallback((region)=>{
    setRegion(region);
    setRegionSelectOpen(false);
  },[]);

  const handleChartItem = useCallback((title)=>{
    setChartTitle(title);
    setSelectOpen(false);
  },[]);
  const regionItem = useMemo(()=>{
    if(!centerData) return [];
    return Object.keys(centerData).reverse().map((e,idx)=><li className='manager-home-chart-item' onClick={()=>handleChartRegionItem(e)} key={idx}>{e}</li>);
  })
  const chartItem = useMemo(()=>{
    const chartList = [
      "센터 별 시험 횟수",
      "센터 별 보상 건 수",
      "센터 별 응시자 수",
      "일년간 에러리포트 수 변화",
      "일년간 보상요청 수 변화"
    ]
    return chartList.map((e,idx)=>
    <li className='manager-home-chart-item' key={idx} onClick={()=>handleChartItem(e)}>{e}</li>);
  },[handleChartItem]);

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
        <button onClick={sliderPrev}>{"<"}</button>
        <div>{title}</div>
        <button onClick={sliderNext}>{">"}</button>
      </div>
      <ul className={`manager-home-slider-${page}`}>
        <li className='manager-home-box'>
          <div className='manager-home-box-title'>오늘 일정</div>
          <Link to="/manager/exam" className='manager-home-link'>더보기</Link>
          <ul>
            <li>
              구미 센터점 09:00 - 10:00
            </li>
            <li>
              구미 센터점 09:00 - 10:00
            </li>
            <li>
              구미 센터점 09:00 - 10:00
            </li>
            <li>
              구미 센터점 09:00 - 10:00
            </li>
          </ul>
        </li>
        <li className='manager-home-box'>
          <div className='manager-home-box-title'>미처리 보상</div>
          <Link to="/manager/reward" className='manager-home-link'>더보기</Link>
          <ul>
            <li>보상대상자 이름 / 수험번호 / 감독관이름 / 사유 타입</li>
            <li>보상대상자 이름 / 수험번호 / 감독관이름 / 사유 타입</li>
            <li>보상대상자 이름 / 수험번호 / 감독관이름 / 사유 타입</li>
            <li>보상대상자 이름 / 수험번호 / 감독관이름 / 사유 타입</li>
          </ul>
        </li>
        <li className='manager-home-box'>
          <div className='manager-home-box-title'>에러 리포트</div>
          <Link to="/manager/error-report" className='manager-home-link'>더보기</Link>
          <ul>
            <li>제목 / 감독관 이름 / 보고시간</li>
            <li>제목 / 감독관 이름 / 보고시간</li>
            <li>제목 / 감독관 이름 / 보고시간</li>
            <li>제목 / 감독관 이름 / 보고시간</li>
          </ul>
        </li>
      </ul>
    </section>
  )
}

export default ManagerHome