import React, { useEffect, useState } from 'react'
import useLogin from '../../Hook/useLogin'

const Home = () => {
  const [loginData, setLoginData] = useState({});
  const [isChecked, setChecked] = useState(false); 
  const [isLogin, login] = useLogin();


  //UseCallback으로 변경
  const loginButtonClick = () => {
    console.log(loginData["id"], loginData["passwords"])
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
    localStorage.setItem("isChecked",!isChecked);
    setChecked(!isChecked);
  }

  useEffect(()=>{
    if(isLogin){
      if(isChecked){
        localStorage.setItem("id", loginData["id"])
      }
    }
  },[isLogin,isChecked,loginData])
 //지금 isChecked 정보까지 로컬스토리지에 저장할 필요 x
  useEffect(()=>{
    let idFlag = JSON.parse(localStorage.getItem("isChecked"));
    if (idFlag !== null){
      setChecked(idFlag);
    }
    if(idFlag === false){
      localStorage.setItem("id","");
    }
    let idData = localStorage.getItem("id");
    if (idData !== null){
      setLoginData("id", idData)
    }
  },[])


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
                loginData["id"] = e.target.value
              }}
            />
            <input 
              className='login-input'
              placeholder='비밀번호'
              onChange={e=>{
                loginData["password"] = e.target.value
              }}
            />
          </div>          
          <div className='login-checkbox'>
              <input 
                type="checkbox" 
                name="saveId" 
                value="save-on"
                id='login-checkbox-box'
                onClick={handleSaveId}
                checked={isChecked}/> 
              <label className='login-checkbox-label'
                for="login-checkbox-box">아이디 저장</label>
                {/* 라벨에 input을 감싸고 텍스트를 div로 감싸기 */}
          </div>
          <button 
            className='login-btn' 
            onClick={loginButtonClick}>
            로그인
          </button>
          <div className='login-text'>
            {/* 클래스 변수명 변경 */}
            <div className="left">
              <div>회원가입</div>
            </div>
            <div className="right">
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
