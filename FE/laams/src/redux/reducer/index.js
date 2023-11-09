import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer";
import ManagerExamDetail from "./managerExamDetailReducer";
import ExamineeDetail from "./examineeDetailReducer";
import ManagerCalendarDetail from "./managerCalendarDetailReducer";
import ManagerCompensation from "./managerCompensationReducer";
import DirectorChat from "./directorChatReducer";
import CalendarExamList from "./calendarExamList";
const rootReducer = combineReducers({
  User,
  Modal,
  ManagerExamDetail,
  ExamineeDetail,
  ManagerCalendarDetail,
  ManagerCompensation,
  CalendarExamList,
  DirectorChat
});
export default rootReducer;