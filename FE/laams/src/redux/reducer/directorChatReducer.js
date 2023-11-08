/* 리덕스에서 관리 할 상태 정의 */
const init = {
  chat:null,
};

const DirectorChatReducer = (state = init, action)=>{
  switch (action.type) {
    case "chat":
      return { ...state, chat: action.payload};
    default:
      return state;
  }
}
export default DirectorChatReducer;