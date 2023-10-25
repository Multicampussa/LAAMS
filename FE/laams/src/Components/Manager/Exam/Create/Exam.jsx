import React, { useCallback, useEffect, useMemo, useState } from 'react'

const Exam = ({setType,data}) => {
  const [testIdx,setTestIdx] = useState(-1);
  const [languageIdx,setLanguageIdx] = useState(-1);
  
  const [tests,] = useState([
    "SSAFIC Writing",
    "SSAFIC Listening"
  ]);
  const [languages,] = useState([
    "한국어",
    "영어",
    "일본어",
    "중국어",
    "스페인어"
  ]);

  //TODO : 초기화 작업, 기존의 데이터가 있으면 동기화
  useEffect(()=>{
    if(data.test){
      setTestIdx(data.test.idx);
    }else{
      setTestIdx(-1);
    }

    if(data.language){
      setLanguageIdx(data.language.idx);
    }else{
      setLanguageIdx(-1);
    }
  },[data]);


  //TODO : 다음페이지로 이동
  const handleNext = useCallback(()=>{
    if(!data.test){
      alert("시험을 선택해 주세요!");
      return;
    }

    if(!data.language){
      alert("언어를 선택해 주세요!");
      return;
    }
    
    setType("time");
  },[setType]);

  //TODO : 이전페이지로 이동
  const handlePrev = useCallback(()=>{
    setType("center");
  },[setType]);

  //선택한 시험을 저장
  const handleTestItem = useCallback((idx)=>{
    if(idx < 0) return;
    setTestIdx(idx);
    data.test={name:tests[idx],idx};
  },[tests,data]);

  //TODO : 시험DivList 반환
  const testItem = useMemo(()=>{
    return tests.map((e,idx)=><div onClick={()=>handleTestItem(idx)} className={idx!==testIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e}</div>)
  },[tests,testIdx,handleTestItem]);

  //TODO : 선택한 언어를 저장
  const handleLanguageItem = useCallback((idx)=>{
    if(idx < 0) return;
    setLanguageIdx(idx);
    data.language={name:languages[idx],idx};
  },[data,languages]);

  //TODO : 언어DivList 반환
  const languageItem = useMemo(()=>{
    return languages.map((e,idx)=><div onClick={()=>handleLanguageItem(idx)} className={idx!==languageIdx? "modal-item-deactive" : "modal-item-active"} key={idx}>{e}</div>)
  },[languages,languageIdx,handleLanguageItem]);

  return (
    <>
      <div className='modal-title'>시험 선택</div>
      <div className='modal-main flex-row'>
        <div className='flex-scrolly'>
          {testItem}
        </div>
        <div className='flex-scrolly'>
          {languageItem}
        </div>
      </div>
      <div className='flex-row-sb-center'>
        <button className='modal-btn-half' onClick={handlePrev}>이전</button>
        <button className='modal-btn-half' onClick={handleNext}>다음</button>
      </div>
    </>
  )
}

export default Exam