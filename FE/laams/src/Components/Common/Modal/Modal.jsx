import React, { useCallback, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {setModalShow} from "../../../redux/actions/modalAction.js";
import ExamCreate from "../../Manager/Exam/Create/ExamCreate.jsx"
import ExamDetail from "../../Manager/Exam/Detail.jsx";
import ExamCompensation from '../../Director/Exam/ExamCompensation.jsx';
import ExamineeDetail from '../../Director/Exam/ExamineeDetail.jsx';
import ExamDocs from '../../Director/Exam/ExamDocs.jsx';
import CalendarDetail from '../../Manager/Home/CalendarDetail.jsx';
import ManagerCompensation from "../../Manager/Compensation/Compensation.jsx";
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
      case "manager-calendar-detail":
        setComponent(<CalendarDetail/>);
        break;
      case "manager-compensation":
        setComponent(<ManagerCompensation/>)
        break;
      case "exam-create":
        setComponent(<ExamCreate/>);
        break;
      case "exam-detail":
        setComponent(<ExamDetail/>);
        break;
      case "exam-compensation":
        setComponent(<ExamCompensation/>);
        break
      case "examinee-detail":
        setComponent(<ExamineeDetail/>);
        break
      case "exam-docs":
        setComponent(<ExamDocs/>);
        break
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