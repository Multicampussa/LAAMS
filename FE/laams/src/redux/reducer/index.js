import { combineReducers } from "redux";
import User from "./userReducer";

const rootReducer = combineReducers({
  User,
});
export default rootReducer;