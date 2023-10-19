/* 리덕스에서 관리 할 상태 정의 */
const init = {
  accessTokenExpireTime:null
};

const userReducer = (state = init, action)=>{
  switch (action.type) {
    case "accessTokenExpireTime":
      return { ...state, accessTokenExpireTime: action.payload};
    default:
      return state;
  }
}
export default userReducer;