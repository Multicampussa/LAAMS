import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer";
import ManagerExamDetail from "./managerExamDetailReducer";
import ExamineeDetail from "./examineeDetailReducer";
import ManagerCalendarDetail from "./managerCalendarDetailReducer";
import ManagerCompensation from "./managerCompensationReducer";
const rootReducer = combineReducers({
  User,
  Modal,
  ManagerExamDetail,
  ExamineeDetail,
  ManagerCalendarDetail,
  ManagerCompensation
});
export default rootReducer;