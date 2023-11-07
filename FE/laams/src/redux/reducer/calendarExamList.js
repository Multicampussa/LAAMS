const init = {
    examList : []
  };
  
const calendarExamListReducer = (state = init, action)=>{
switch (action.type) {
    case "calendarExamList":
        return { ...state, examList: action.payload};
    default:
        return state;
}
}
export default calendarExamListReducer;