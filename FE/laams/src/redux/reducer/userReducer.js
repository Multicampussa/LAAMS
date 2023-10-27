/* 리덕스에서 관리 할 상태 정의 */
const init = {
  accessToken:null,
  accessTokenExpireTime:null,
  authority:null,
  memberId:null
};

const userReducer = (state = init, action)=>{
  switch (action.type) {
    case "accessToken":
      return { ...state, accessToken: action.payload};
    case "accessTokenExpireTime":
      return { ...state, accessTokenExpireTime: action.payload};
    case "authority":
      return { ...state, authority: action.payload};
    case "memberId":
      return { ...state, memberId: action.payload};
    default:
      return state;
  }
}
export default userReducer;