import { useState, useCallback } from 'react';
import {setAccessToken,setAccessTokenExpireTime,setAuthority, setMemberId} from "../redux/actions/userAction.js";
import axios from 'axios';
import { useDispatch } from 'react-redux';

const useLogin = ()=> {
  const [isLogin,setIsLogin] = useState(false);
  const dispatch = useDispatch();

  const login = useCallback((id,password,authority,isChecked)=>{
    axios.post(`${process.env.REACT_APP_SPRING_URL}/api/v1/member/login`,{id,pw :password, authority: authority})
      .then( ({data})=>{
        if(isChecked){
          localStorage.setItem("id", id)
        }else{
          localStorage.setItem("id", "")
        }
        dispatch(setAccessToken(data.accessToken));
        dispatch(setAuthority(data.authority));
        dispatch(setAccessTokenExpireTime(new Date(data.accessTokenExpireTime).toString()));
        dispatch(setMemberId(id));
        localStorage.setItem("refreshTokenExpireTime",new Date(data.refreshTokenExpireTime).toString());
        localStorage.setItem("refreshToken",data.refreshToken);
        setIsLogin(true);
        if(data.authority === "ROLE_DIRECTOR"){
          axios.post(`${process.env.REACT_APP_SPRING_URL}/api/v1/chat/room`,{},
            {
              headers: {Authorization: "Bearer "+data.accessToken,},
            }
          );
        }
      }).catch(err=>alert(err.response.data.message));
  },[dispatch])

  return [isLogin,login];
}
export default useLogin;
