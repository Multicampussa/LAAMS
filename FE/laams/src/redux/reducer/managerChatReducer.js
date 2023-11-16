/* 리덕스에서 관리 할 상태 정의 */
const init = {

};

const ManagerChat = (state = init, action)=>{
  switch (action.type) {
    case "":
      return { ...state, ...action.payload};
    default:
      return state;
  }
}
export default ManagerChat;