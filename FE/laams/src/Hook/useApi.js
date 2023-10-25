import {useSelector,useDispatch} from "react-redux";
import {setAccessToken,setAccessTokenExpireTime} from "../redux/actions/userAction.js";
import axios from "axios";
import {useNavigate} from "react-router-dom";
const useApi = () => {
  const user = useSelector(state=>state.User);
  const api = axios.create({baseURL:`${process.env.REACT_APP_SPRING_URL}`,headers: { "Content-type": "application/json" }});
  const dispatch = useDispatch();
  const navigate = useNavigate();

  api.interceptors.request.use(
    async (config)=>{
      if (!user.accessToken) {
        config.headers.authorization = null;
      }else{
        if (new Date(user.accessTokenExpireTime) > new Date()) {
          config.headers.authorization = `Bearer ${user.accessToken}`;
        }else{
          const refreshTokenExpireTime = new Date(localStorage.getItem("refreshTokenExpireTime"));
          if(refreshTokenExpireTime && refreshTokenExpireTime > new Date()){
            await axios.post(`${process.env.REACT_APP_SPRING_URL}/member/refresh`,{},
              {
                headers: {Authorization: "Bearer "+localStorage.getItem("refreshToken"),},
              }
            )
            .then(({data})=>{
              dispatch(setAccessTokenExpireTime(new Date(data.accessTokenExpireTime).toString()));
              dispatch(setAccessToken(data.accessToken));
              config.headers["Authorization"] = "Bearer "+data.accessToken;
            }).catch((err)=>{
              console.log(err);
            })
          }
        }
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );

  api.interceptors.response.use(
    async (response)=>{
      switch (response.data.code) {  
        case "l001":
          alert(response.data.message);
          localStorage.clear();
          navigate("/");
          return Promise.reject(401);
        case "l002":
          const refreshTokenExpireTime = localStorage.getItem("refreshTokenExpireTime");
          if(!refreshTokenExpireTime || refreshTokenExpireTime < new Date()){
            localStorage.clear();
            navigate("/");
            window.alert("토큰이 만료되어 자동으로 로그아웃 되었습니다");
          }else{
            await axios.post(`${process.env.REACT_APP_SPRING_URL}/member/refresh`,{},
              {
                headers: {Authorization: "Bearer "+localStorage.getItem("refreshToken"),},
              }
            )
            .then(({data})=>{
              dispatch(setAccessTokenExpireTime(new Date(data.accessTokenExpireTime).toString()));
              dispatch(setAccessToken(data.accessToken));
              response.config.headers["Authorization"] = "Bearer "+data.accessToken;
            }).catch((err)=>{
              console.log(err);
            })
          }
          break;
        default:
          break;
      }
      return response;
    },
    async (error) => {
      return Promise.reject(error);
    }
  );

  return api;
};

export default useApi;