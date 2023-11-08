import React, { useCallback, useEffect, useState } from 'react'
import useLogin from '../../Hook/useLogin'
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

const Home = () => {
  const [loginData,setLoginData] = useState({id:localStorage.getItem("id")? localStorage.getItem("id") : "", password:"", authority:"ROLE_DIRECTOR"});
  const [isChecked, setChecked] = useState(localStorage.getItem("id")? true : false); 
  const [, login] = useLogin();
  const user = useSelector(state=>state.User);
  const navigate = useNavigate();
  const isLogin = useSelector(state=>state.User.accessToken);

  // TODO: id, password 확인 및 로그인
  const loginButtonClick = useCallback(() => {
    if(loginData["id"] === ""){
      alert("아이디를 입력해 주세요");
      return
    }
    if(loginData["password"] === ""){
      alert("비밀번호를 입력해 주세요");
      return
    }
    login(loginData["id"], loginData["password"],loginData["authority"],isChecked);
  },[loginData,login,isChecked]);

  // TODO : 로그인 후처리 
  useEffect(()=>{
    if(isLogin){
      if(user.authority){
        switch(user.authority){
          default : break
          case "ROLE_DIRECTOR":
            navigate("/director");
          break;
          case "ROLE_MANAGER":
            navigate("/manager");
          break;
          case "ROLE_CENTER_MANAGER":
            navigate('/centermanager');
          break
        }
      }else{
        alert("권한이 설정되지 않았습니다!");
      }
    }
  },[isLogin, navigate ,user])

  

  return (
    <section className='home'>
      <div className='login-container'>
        <div className='login-logo'></div>
          <article className='login-box'>
          <div className='login-title'>Login</div>
          <ul className='login-tab'> 
           
            <li 
              className={`login-tab-items-${loginData['authority'] === 'ROLE_DIRECTOR'? 'active':'deactive'}`} 
              onClick={e=>setLoginData({...loginData, authority:'ROLE_DIRECTOR'})}
              >감독관</li>
            <li 
              className={`login-tab-items-${loginData['authority'] === 'ROLE_MANAGER'? 'active':'deactive'}`}
              onClick={e=>setLoginData({...loginData, authority:'ROLE_MANAGER'})}
              >운영자</li>
            <li 
              className={`login-tab-items-${loginData['authority'] === 'ROLE_CENTER_MANAGER'? 'active':'deactive'}`}
              onClick={e=>setLoginData({...loginData, authority:'ROLE_CENTER_MANAGER'})}
              >센터담당자</li>
          </ul>
          <div className='login-form'>
            <div className='login-input-box'>
              <input 
                className='login-input'
                placeholder='ID'
                defaultValue={loginData['id']}
                onChange={e=>{
                  loginData['id'] = e.target.value
                }}
              />
              <form onSubmit={e=>{
                e.preventDefault();
                loginButtonClick();
              }}>
                <input 
                  className='login-input'
                  placeholder='비밀번호'
                  type='password'
                  autoComplete='new-password'
                  onChange={e=>{
                    loginData['password'] = e.target.value
                  }}
                />
              </form>

            </div>          
            <div className='login-checkbox'>
              <label>
                <input
                  type="checkbox" 
                  defaultChecked={isChecked}
                  onChange={() =>{
                    setChecked(!isChecked);}}
                  className='login-checkbox-input'
                />
                <span className='login-checkbox-label'>
                  아이디 저장
                </span>
              </label>
            </div>
            <button 
              className='login-btn' 
              onClick={loginButtonClick}>
              로그인
            </button>
          </div>
          <div className='login-text'>
            <div>
            <div 
              className='login-text-left'
              onClick={()=>{
                navigate('/join');
              }}
              >회원가입</div>
            </div>
            <div className="login-text-right">
              <div>아이디 찾기</div>
              <div>비밀번호 찾기</div>
            </div>
          </div>
        </article>
      </div>      
    </section>
  )
}

export default Home
