/* 리덕스에서 관리 할 상태 정의 */
// 이후 원상태로 돌려놔야함 빈값, false,
const init = {
  type:"exam-docs",
  show:true  
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