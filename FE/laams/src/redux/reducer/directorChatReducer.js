/* 리덕스에서 관리 할 상태 정의 */
const init = {
  chat:null,
  region:[],
  all:[],
  center:[],
  private:[],
};

const DirectorChatReducer = (state = init, action)=>{
  switch (action.type) {
    case "chat":
      return { ...state, chat: action.payload};
    case "all":
      return { ...state, all: [...state.all,action.payload]};
    case "region":
      return { ...state, region: [...state.region,action.payload]};
    case "center":
      return { ...state, center: [...state.center,action.payload]};
    case "priavte":
      return { ...state, private: [...state.private,action.payload]};
    case "all-list":
      return { ...state, all: [...state.all,...action.payload]};
    case "region-list":
      return { ...state, region: [...state.region,...action.payload]};
    case "center-list":
      return { ...state, center: [...state.center,...action.payload]};
    case "priavte-list":
      return { ...state, private: [...state.private,...action.payload]};
  default:
      return state;
  }
}
export default DirectorChatReducer;