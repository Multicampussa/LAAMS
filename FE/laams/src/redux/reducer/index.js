import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer";
import ManagerExamDetail from "./managerExamDetailReducer";
import ExamineeDetail from "./examineeDetailReducer";
import ManagerCalendarDetail from "./managerCalendarDetailReducer";
const rootReducer = combineReducers({
  User,
  Modal,
  ManagerExamDetail,
  ExamineeDetail,
  ManagerCalendarDetail
});
export default rootReducer;