const init = {
    examList : [],
    examDate : []
  };
  
const calendarExamListReducer = (state = init, action)=>{
switch (action.type) {
    case "calendarExamList":
        return { ...state, examList: action.payload};
    case "calendarExamDate":
        return { ...state, examDate: action.payload};
    default:
        return state;
}
}
export default calendarExamListReducer;