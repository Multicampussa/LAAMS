import { useState, useCallback } from 'react';
import {setAccessTokenExpireTime} from "../redux/actions/userAction.js";
import axios from 'axios';
import { useDispatch } from 'react-redux';

const useLogin = ()=> {
  const [isLogin,setIsLogin] = useState(false);
  const dispatch = useDispatch();

  const login = useCallback((id,password)=>{
    axios.post("http://localhost:8080/member/login",{id,pw :password})
      .then(({data})=>{
        axios.defaults.headers.common['Authorization'] = `Bearer ${data.accessToken}`;
        dispatch(setAccessTokenExpireTime(new Date(data.accessTokenExpireTime).toString()));
        localStorage.setItem("refreshTokenExpireTime",new Date(data.refreshTokenExpireTime).toString());
        localStorage.setItem("refreshToken",data.refreshToken);
        console.log(data);
        setIsLogin(true);
      }).catch(err=>console.log(err.response));
  },[dispatch])

  return [isLogin,login];
}
export default useLogin;
