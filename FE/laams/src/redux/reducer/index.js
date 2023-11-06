import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer";
import ManagerExamDetail from "./managerExamDetailReducer";
import ExamineeDetail from "./examineeDetailReducer";
import ManagerCalendarDetail from "./managerCalendarDetailReducer";
import DirectorCalendarExamList from "./directorCalendarExamList";
const rootReducer = combineReducers({
  User,
  Modal,
  ManagerExamDetail,
  ExamineeDetail,
  ManagerCalendarDetail,
  DirectorCalendarExamList
});
export default rootReducer;