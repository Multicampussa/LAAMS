import { combineReducers } from "redux";
import User from "./userReducer";
import Modal from "./modalReducer"
const rootReducer = combineReducers({
  User,
  Modal,
});
export default rootReducer;