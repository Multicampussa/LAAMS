import React, { useMemo, useState,useCallback } from 'react'

const Center = ({setType,data}) => {
  const [cityIdx,setCityIdx] = useState(-1);
  const [centerIdx,setCenterIdx] = useState(-1);
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

  const handleCenterItem = useCallback((idx) => {
    setCenterIdx(idx);
    data["city"]=centerData[cityIdx].city;
    data["center"]=centerData[cityIdx].center[centerIdx];
  },[centerData,cityIdx,centerIdx,data]);
  


  const citys = useMemo(()=>{
    const temp = [];
    centerData.forEach((e,idx)=>temp.push(<div onClick={()=>setCityIdx(idx)} className={idx!==cityIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e.city}</div>));
    return temp;
  },[centerData,cityIdx]);

  const centers = useMemo(()=>{
    const temp = [];
    if(cityIdx===-1) return temp;
    centerData[cityIdx].center.forEach((e,idx)=>temp.push(<div onClick={()=>{handleCenterItem(idx)}} className={idx!==centerIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e}</div>));
    return temp;
  },[centerData,cityIdx,centerIdx,handleCenterItem]);

  const handleNext = useCallback(()=>{
    setType("exam");
  },[setType]);

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
      <div className='flex-row-sb-center'>
        <button className='modal-btn-full' onClick={handleNext}>다음</button>
      </div>
    </>
  )
}

export default Center