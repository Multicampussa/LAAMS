import React, { useCallback, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {setModalShow} from "../../../redux/actions/modalAction.js";
import ExamCreate from "../../Manager/Exam/Create/ExamCreate.jsx"
const Modal = () => {
  const show = useSelector(state=>state.Modal.show);
  const type = useSelector(state=>state.Modal.type);
  const [component,setComponent] = useState();
  const dispatch = useDispatch();

  const closeModal = useCallback(()=>{
    dispatch(setModalShow(false));
  },[dispatch]);

  useEffect(()=>{
    switch(type){
      case "exam-create":
        setComponent(<ExamCreate/>);
        break;
      default:
        break;
    }
  },[type]);
  return (
    <div className='modal'>
      <div className={`modal-bg-${show}`}>
        <div className='modal-content'>
          <button className='modal-btn-close' onClick={closeModal}><div className='hidden-text'>Close</div></button>
          {
            component
          }
        </div>
      </div>
    </div>
  )
}

export default Modal