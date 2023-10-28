import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer";
import ManagerExamDetail from "./managerExamDetailReducer";
const rootReducer = combineReducers({
  User,
  Modal,
  ManagerExamDetail,
});
export default rootReducer;