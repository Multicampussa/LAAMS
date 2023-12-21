const init = {
    examList : [],
    examDate : [],
    monthExamList : []
  };
  
const calendarExamListReducer = (state = init, action)=>{
switch (action.type) {
    case "calendarExamList":
        return { ...state, examList: action.payload};
    case "calendarExamDate":
        return { ...state, examDate: action.payload};
    case "monthExamList":
        return { ...state, monthExamList: action.payload};
    default:
        return state;
}
}
export default calendarExamListReducer;