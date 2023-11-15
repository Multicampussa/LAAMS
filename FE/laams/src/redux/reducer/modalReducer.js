/* 리덕스에서 관리 할 상태 정의 */

const init = {
  type:"",
  show: false
};

const userReducer = (state = init, action)=>{
  switch (action.type) {
    case "type":
      return { ...state, type: action.payload};
    case "show":
      return { ...state, show: action.payload};
    default:
      return state;
  }
}
export default userReducer;