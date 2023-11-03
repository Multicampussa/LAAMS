/* 리덕스에서 관리 할 상태 정의 */
const init = {
  date:new Date(),
  dailyDashboardErrorReports:[],
  day:0,
  unprocessedAssignments:[],
  unprocessedCompensations:[]
};

const managerCalendarDetailReducer = (state = init, action)=>{
  switch (action.type) {
    case "managerCalendarDetail":
      return { ...state, ...action.payload};
    default:
      return state;
  }
}
export default managerCalendarDetailReducer;