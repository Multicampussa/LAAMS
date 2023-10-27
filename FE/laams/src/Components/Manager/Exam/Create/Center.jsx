import React, { useMemo, useState,useCallback, useEffect } from 'react'

const Center = ({setType,data}) => {
  const [cityIdx,setCityIdx] = useState(-1);
  const [centerIdx,setCenterIdx] = useState(-1);

  //TODO : 초기화 작업, 기존의 데이터가 있으면 동기화
  useEffect(()=>{
    if(data.city){
      setCityIdx(data.city.idx);
    }else{
      setCityIdx(-1);
    }

    if(data.center){
      setCenterIdx(data.center.idx);
    }else{
      setCenterIdx(-1);
    }
  },[data]);

  const [centerData,] = useState([
    {city:"서울특별시",center:[]},
    {city:"부산광역시",center:["a","b","a","b","a","b","a","b","a","b","a","b"]},
    {city:"대구광역시",center:["c","d","c","d","c","d","c","d","c","d","c","d","c","d","c","d"]},
    {city:"인천광역시",center:[]},
    {city:"광주광역시",center:[]},
    {city:"대전광역시",center:[]},
    {city:"울산광역시",center:[]},
    {city:"세종특별자치시",center:[]},
    {city:"경기도",center:[]},
    {city:"강원특별자치도",center:[]},
    {city:"충청북도",center:[]},
    {city:"충청남도",center:[]},
    {city:"전라북도",center:[]},
    {city:"전라남도",center:[]},
    {city:"경상북도",center:[]},
    {city:"경상남도",center:[]},
    {city:"제주특별자치도",center:[]},
  ]);

  //선택한 지역을 저장
  const handleCityItem = useCallback((idx)=>{
    if(idx < 0) return;
    setCityIdx(idx);
    data.city={name:centerData[idx].city,idx};
  },[centerData,data]);

  //TODO : 선택한 센터를 저장
  const handleCenterItem = useCallback((idx) => {
    if(idx < 0) return;
    setCenterIdx(idx);
    data.center={name:centerData[cityIdx].center[idx],idx};
  },[centerData,cityIdx,data]);

  //TODO : 지역목록을 반환
  const citys = useMemo(()=>{
    const temp = [];
    centerData.forEach((e,idx)=>temp.push(<div onClick={()=>handleCityItem(idx)} className={idx!==cityIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e.city}</div>));
    return temp;
  },[centerData,cityIdx,handleCityItem]);

  //TODO : 센터DivList를 반환
  const centers = useMemo(()=>{
    const temp = [];
    if(cityIdx===-1) return temp;
    centerData[cityIdx].center.forEach((e,idx)=>temp.push(<div onClick={()=>{handleCenterItem(idx)}} className={idx!==centerIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e}</div>));
    return temp;
  },[centerData,cityIdx,centerIdx,handleCenterItem]);

  //TODO : 다음 페이지로 이동
  const handleNext = useCallback(()=>{
    if(!data.city){
      alert("지역을 선택해 주세요!");
      return;
    }

    if(!data.center){
      alert("센터를 선택해 주세요!");
      return;
    }
    
    setType("exam");
  },[setType,data]);

  return (
    <>
      <div className='modal-title'>센터 선택</div>
      <div className='modal-main flex-row'>
        <div className='flex-scrolly'>
          {citys}
        </div>
        <div className='flex-scrolly'>
          {centers}
        </div>
      </div>
      <button className='modal-btn-full' onClick={handleNext}>다음</button>
    </>
  )
}

export default Center