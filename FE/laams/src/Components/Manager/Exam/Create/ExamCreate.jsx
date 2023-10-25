import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import Center  from "./Center.jsx";
import Exam from "./Exam.jsx";
import Time from "./Time.jsx";
const ExamCreate = () => {
  const [type,setType] = useState("center");
  const [data,setData] = useState({});
  const [main,setMain] = useState();
  const close = useSelector(state=>state.Modal.show);

  //TODO : 모달이 닫혔을 때 data 초기화
  useEffect(()=>{
    if(!close){
      setType("센터");
      setData({});
    }
  },[close]);

  //Todo : Type에 따라 MainComponent 선택
  useEffect(()=>{
    switch(type){
      default:
      case "center":
        setMain(<Center setType={setType} data={data}/>);
        break;
      case "exam":
        setMain(<Exam setType={setType} data={data} />);
        break;
      case "time":
        setMain(<Time setType={setType} data={data} />);
        break;
    }
  },[type,data]);

  return (
    <section className='modal-component'>
      {
        main
      }
    </section>
  )
}

export default ExamCreate