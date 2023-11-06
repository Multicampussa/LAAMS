/* 리덕스에서 관리 할 상태 정의 */
const init = {
  examineeNo : null
};

const ManagerCompensationReducer = (state = init, action)=>{
  switch (action.type) {
    case "examineeNo":
      return { ...state, examineeNo:action.payload};
    default:
      return state;
  }
}
export default ManagerCompensationReducer;