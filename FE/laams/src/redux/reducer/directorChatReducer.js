/* 리덕스에서 관리 할 상태 정의 */
const init = {
  room:null,
};

const DirectorChatReducer = (state = init, action)=>{
  switch (action.type) {
    case "director-room":
      return { ...state, room: action.payload};
    default:
      return state;
  }
}
export default DirectorChatReducer;