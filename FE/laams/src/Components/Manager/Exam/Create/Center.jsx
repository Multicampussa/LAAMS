import React, { useMemo, useState,useCallback, useEffect } from 'react'
import useCenter from '../../../../Hook/useCenter';

const Center = ({setType,data}) => {
  const centerData = useCenter();
  const [centerRegionData,setCenterRegionData] = useState();
  const [regionIdx,setRegionIdx] = useState(-1);
  const [centerIdx,setCenterIdx] = useState(-1);

  //TODO : 센터지역 데이터 저장
  useEffect(()=>{
    if(!centerData) return;
    setCenterRegionData(Object.keys(centerData).reverse());
  },[centerData]);

  //TODO : 초기화 작업, 기존의 데이터가 있으면 동기화
  useEffect(()=>{
    if(data.centerRegion){
      setRegionIdx(data.centerRegion.idx);
    }else{
      setRegionIdx(-1);
    }

    if(data.centerName){
      setCenterIdx(data.centerName.idx);
    }else{
      setCenterIdx(-1);
    }
  },[data]);

  //선택한 지역을 저장
  const handleregionItem = useCallback((idx)=>{
    if(idx < 0) return;
    setRegionIdx(idx);
    data.centerRegion={name:centerRegionData[idx],idx};
  },[centerRegionData,data]);

  //TODO : 선택한 센터를 저장
  const handleCenterItem = useCallback((idx) => {
    if(idx < 0) return;
    setCenterIdx(idx);
    data.centerName={name:centerData[centerRegionData[regionIdx]][idx].centerName,idx};
  },[centerRegionData,centerData,regionIdx,data]);

  //TODO : 지역목록을 반환
  const regions = useMemo(()=>{
    if(!centerRegionData) return [];
    const temp = [];
    centerRegionData.forEach((e,idx)=>temp.push(<div onClick={()=>handleregionItem(idx)} className={idx!==regionIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e}</div>));
    return temp;
  },[centerRegionData,regionIdx,handleregionItem]);

  //TODO : 센터DivList를 반환
  const centers = useMemo(()=>{
    const temp = [];
    if(!centerData||!centerRegionData) return temp;
    if(!centerData || regionIdx===-1) return temp;
    centerData[centerRegionData[regionIdx]].forEach((e,idx)=>temp.push(<div onClick={()=>{handleCenterItem(idx)}} className={idx!==centerIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e.centerName}</div>));
    return temp;
  },[centerRegionData,centerData,regionIdx,centerIdx,handleCenterItem]);

  //TODO : 다음 페이지로 이동
  const handleNext = useCallback(()=>{
    if(!data.centerRegion){
      alert("지역을 선택해 주세요!");
      return;
    }

    if(!data.centerName){
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
          {regions}
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