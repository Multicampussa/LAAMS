/* 리덕스에서 관리 할 상태 정의 */
const init = {
  examineeNo : null,
  examNo : null,
  isResetList:true,
};

const ManagerCompensationReducer = (state = init, action)=>{
  switch (action.type) {
    case "examineeNo":
      return { ...state, examineeNo:action.payload};
    case "examNo":
      return { ...state, examNo:action.payload};
    case "isResetList":
      return { ...state, isResetList:!state.isResetList};
    default:
      return state;
  }
}
export default ManagerCompensationReducer;