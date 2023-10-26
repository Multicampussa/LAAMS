import { useState, useCallback } from 'react';
import {setAccessToken,setAccessTokenExpireTime,setAuthority} from "../redux/actions/userAction.js";
import axios from 'axios';
import { useDispatch } from 'react-redux';

const useLogin = ()=> {
  const [isLogin,setIsLogin] = useState(false);
  const dispatch = useDispatch();

  const login = useCallback((id,password,isChecked)=>{
    axios.post(`${process.env.REACT_APP_SPRING_URL}/member/login`,{id,pw :password})
      .then(({data})=>{
        if(isChecked){
          localStorage.setItem("id", id)
        }else{
          localStorage.setItem("id", "")
        }
        dispatch(setAccessToken(data.accessToken));
        dispatch(setAuthority(data.authority));
        dispatch(setAccessTokenExpireTime(new Date(data.accessTokenExpireTime).toString()));
        localStorage.setItem("refreshTokenExpireTime",new Date(data.refreshTokenExpireTime).toString());
        localStorage.setItem("refreshToken",data.refreshToken);
        console.log(data);
        setIsLogin(true);
      }).catch(err=>alert(err.response.data.message));
  },[dispatch])

  return [isLogin,login];
}
export default useLogin;
