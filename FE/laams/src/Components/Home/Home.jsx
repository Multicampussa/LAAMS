import React, { useEffect, useState } from 'react'
import useLogin from '../../Hook/useLogin'

const Home = () => {
  const [loginData,] = useState({id:"", password:""});
  const [isChecked, setChecked] = useState(localStorage.getItem("id")? true : false); 
  const [isLogin, login] = useLogin();


  //UseCallback으로 변경
  const loginButtonClick = () => {
    if(loginData["id"] === ""){
      alert("아이디를 입력해 주세요");
      return
    }
    if(loginData["password"]){
      alert("비밀번호를 입력해 주세요");
      return
    }
    login(loginData["id"], loginData["password"]);
  };

  const handleSaveId = () =>{
    setChecked(!isChecked);
  }

  useEffect(()=>{
    if(isLogin){
      if(isChecked){
        localStorage.setItem("id", loginData["id"])
      }
    }
  },[isLogin,isChecked,loginData])


  return (
    <section className='home'>
      <div className='login-container'>
        <img src="logo.svg" alt="logo"
        className='login-logo'
        />
        <article className='login-box'>
          <div className='login-title'>Login</div>
          <div className='login-input-box'>
            <input 
              className='login-input'
              placeholder='ID'
              onChange={e=>{
                console.log(loginData)
                loginData['id'] = e.target.value
              }}
            />
            <input 
              className='login-input'
              placeholder='비밀번호'
              onChange={e=>{
                loginData['password'] = e.target.value
              }}
            />
          </div>          
          <div className='login-checkbox'>
            <label>
              <input
                type="checkbox" 
                defaultChecked={isChecked}
                onChange={handleSaveId}
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
          <div className='login-text'>
            <div>
              <div>회원가입</div>
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
