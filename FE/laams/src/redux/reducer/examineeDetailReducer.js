/* 리덕스에서 관리 할 상태 정의 */
const init = {
  examineeNo:null,
  examineeName:null
};

const examineeDetailReducer = (state = init, action)=>{
  switch (action.type) {
    case "examineeNo":
      return { ...state, examineeNo: action.payload};
    case "examineeName":
      return { ...state, examineeName: action.payload};
    default:
      return state;
  }
}
export default examineeDetailReducer;