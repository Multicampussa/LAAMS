/* 리덕스에서 관리 할 상태 정의 */
const init = {
  examNo:null,
};

const userReducer = (state = init, action)=>{
  switch (action.type) {
    case "examNo":
      return { ...state, examNo: action.payload};
    default:
      return state;
  }
}
export default userReducer;