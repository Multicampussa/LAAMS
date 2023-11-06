const init = {
    examList : []
  };
  
const directorCalendarExamListReducer = (state = init, action)=>{
switch (action.type) {
    case "directorCalendarExamList":
        return { ...state, examList: action.payload};
    default:
        return state;
}
}
export default directorCalendarExamListReducer;