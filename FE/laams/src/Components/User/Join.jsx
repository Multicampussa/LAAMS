import React, { useCallback, useRef, useState } from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Join = () => {

  const [joinData,] = useState({});
  const [checkPwText, setCheckPwText] = useState();
  const [pwValidText, setPwValidText] = useState('특수기호 및 영어 포함 8~15자');
  const [emailValidText, setEmailValidText] = useState('인증번호가 올바르지 않습니다')
  const pwTextRef = useRef();
  const emailTextRef = useRef();
  const navigate = useNavigate();
  const [isEmailChecked, setIsEmailChecked] = useState(false);
  const [isPwChecked, setIsPwChecked] = useState(false);

  // TODO : 이메일 전송 로직
  const sendEmail = useCallback(()=>{
    axios({
      method: 'post',
      url: `${process.env.REACT_APP_SPRING_URL}/member/sendemail`,
      headers:{
        "content-type": "application/json"
      },
      data:{
        email : joinData['email']
      }
    })
    .then(()=>{
      setEmailValidText('인증번호가 이메일로 전송되었습니다')
      emailTextRef.current.style.color = "blue";
    })
    .catch((err)=>{
      console.log(err)
      setEmailValidText('이메일을 확인해주세요')
      emailTextRef.current.style.color = "red";
    })
  },[joinData])

  // TODO : 전송된 인증코드 확인 로직
  const checkEmailCode = useCallback(()=>{
    axios({
      method: 'post',
      url: `${process.env.REACT_APP_SPRING_URL}/member/sendemail/verify`,
      headers:{
        "content-type": "application/json"
      },
      data:{
        code: joinData['emailCode'],
        email: joinData['email']
      }
    })
    .then(()=>{
      setEmailValidText('올바른 인증번호입니다.');
      setIsEmailChecked(true)
      emailTextRef.current.style.color = "blue";
    })
    .catch((err)=>{
      console.log(err)
      setEmailValidText('인증번호가 올바르지 않습니다');
      setIsEmailChecked(false);
      emailTextRef.current.style.color = "red";
    })
  },[joinData])

  // TODO : 회원가입 로직
  const memberJoin = useCallback(()=>{
    for (let key in joinData) {
      if (joinData[key] === '') {
        alert(`정보를 모두 입력해주세요`);
        return;
      }
    }
    if(isEmailChecked && isPwChecked && checkPwText){
      axios({
        method:'post',
        url:`${process.env.REACT_APP_SPRING_URL}/member/signup`,
        headers:{
          "content-type": "application/json"
        },
        data:{
          "centerManagerCode": joinData['centerManagerCode'],
          "email": joinData['email'],
          "id": joinData['id'],
          "name": joinData['name'],
          "phone": joinData['phone'],
          "pw": joinData['password']
        }
      })
      .then((res)=>{
        alert('회원가입에 성공하셨습니다');
        navigate('/');
      })
      .catch((err)=>{
        console.log(err);
      })
    }else{
      if(!isPwChecked || !checkPwText){
        alert('비밀번호 확인을 해주세요');
        console.log(checkPwText)
        return
      }
      if(!isEmailChecked){
        alert('이메일 인증을 해주세요');
        return
      }
    }
    
  },[joinData,navigate,isEmailChecked,isPwChecked,checkPwText])


  // TODO: 비밀번호 정규식 확인 로직
  const checkPwReg = useCallback((e)=>{
    const pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    if(joinData['password'] && pwReg.test(joinData['password'])){
      if ( joinData['password'] === e.target.value){
        setPwValidText('사용가능한 비밀번호입니다.');
        pwTextRef.current.style.color = "blue";
      }else{
        setIsPwChecked(false);
        setPwValidText("비밀번호가 일치하지 않습니다");
        pwTextRef.current.style.color = "red";
      }
    }else{
      setIsPwChecked(false);
      setPwValidText("특수기호 및 영어 포함 8~15자");
      pwTextRef.current.style.color = "red";
    }

  },[joinData])
  // TODO : 비밀번호 확인 일치 로직
  const checkPassword = useCallback(e=>{
    const pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    setCheckPwText(e.target.value);
    checkPwReg(e);
    if(joinData['password'] && pwReg.test(joinData['password'])){
      if ( joinData['password'] === e.target.value){
        setIsPwChecked(true);
        setPwValidText('비밀번호가 일치합니다');
        pwTextRef.current.style.color = "blue";
      }
    }
    
  },[checkPwReg,joinData])


  return (
    <section className='join'>
        <div className='join-container'>
            <img 
              src="logo.svg" 
              className='join-logo' 
              alt="로고" 
            />
            <article className='join-box'>
              <label className='join-input'>
                <div className='join-input-title'>이름</div>
                <input 
                  placeholder='이름' 
                  className='join-input-box'
                  onChange={e=>{
                    joinData["name"] = e.target.value; 
                  }}/>
              </label>
              <label className='join-input'>
                <div className='join-input-title'>아이디</div>
                <input 
                  placeholder='아이디' 
                  className='join-input-box'
                  onChange={e=>{
                    joinData["id"] = e.target.value;
                  }}  
                />
              </label>
              <label className='join-input'>
                <div 
                  className='join-input-title'>비밀번호</div>
                <input 
                  placeholder='비밀번호' 
                  type='password' 
                  autoComplete='new-password' 
                  className='join-input-box'
                  onChange={e=>{
                    joinData['password'] = e.target.value;
                    checkPwReg(e);
                  }}
                />
                <input 
                  placeholder='비밀번호 확인' 
                  type='password' 
                  autoComplete='new-password' 
                  className='join-input-box'
                  onChange={e=>{checkPassword(e)}}/>
                <p ref={pwTextRef} className='join-input-tag'>{pwValidText}</p>
              </label>

              <label className='join-input'>
                <div className='join-input-title'>이메일</div>
                <div className='join-input-email'>
                  <input 
                    placeholder='이메일' 
                    className='join-input-box-email'
                    onChange={e=>{
                      joinData['email'] = e.target.value;
                    }}
                  />
                  <button 
                    className='join-input-box-button' 
                    onClick={sendEmail}>전송</button>
                </div>
                <div className='join-input-email'>
                  <input 
                    placeholder='인증번호' 
                    className='join-input-box-email'
                    onChange={e=>{
                      joinData['emailCode'] = e.target.value;
                    }} />
                  <button 
                    className='join-input-box-button'
                    onClick={checkEmailCode}>인증</button>
                </div>
                <p ref={emailTextRef} className='join-input-tag'>{emailValidText}</p>
              </label>
              <label className='join-input'>
                <div className='join-input-title'>전화번호</div>
                <input 
                  placeholder='전화번호' 
                  className='join-input-box'
                  onChange={e=>{
                    joinData["phone"] = e.target.value;
                  }}/>
                  <p className='join-input-phone'>01012345678 형식으로 입력해주세요</p>
              </label>
              <label className='join-input'>
                <div className='join-input-title'>센터 담당자 코드</div>
                <input 
                  placeholder='센터 담당자 코드' 
                  className='join-input-box'
                  onChange={e=>{
                    joinData['centerManagerCode'] = e.target.value;
                  }}/>
              </label>
            </article>
            <button className='join-btn' onClick={memberJoin}> 회원가입
              </button>
        </div>

    </section>
  )
}

export default Join