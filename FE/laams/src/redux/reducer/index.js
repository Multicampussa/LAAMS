import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer";
import ManagerExamDetail from "./managerExamDetailReducer";
import ExamineeDetail from "./examineeDetailReducer";
import ManagerCalendarDetail from "./managerCalendarDetailReducer";
import DirectorCalendarExamList from "./directorCalendarExamList";
import ManagerCompensation from "./managerCompensationReducer";
import ManagerChat from "./managerChatReducer";
const rootReducer = combineReducers({
  User,
  Modal,
  ManagerExamDetail,
  ExamineeDetail,
  ManagerCalendarDetail,
  ManagerCompensation,
  ManagerChat
});
export default rootReducer;