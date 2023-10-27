import React, { useState, useRef, useCallback, useEffect } from 'react'
import axios from 'axios';
import useApi from '../../Hook/useApi';
import { useSelector } from 'react-redux';

const UpdateUser = () => {
  const [userData,] = useState({});
  const [originData,setOriginData] = useState({});
  const [checkPwText, setCheckPwText] = useState();
  const [pwValidText, setPwValidText] = useState('특수기호 및 영어 포함 8~15자');
  const [emailValidText, setEmailValidText] = useState('인증번호가 올바르지 않습니다')
  const [verifyCode, setVerifyCode] = useState({});
  const pwTextRef = useRef();
  const emailTextRef = useRef();
  const [isEmailChecked, setIsEmailChecked] = useState(false);
  const [isPwChecked, setIsPwChecked] = useState(false);
  const originId = useSelector(state=>state.User.memberId);
  const api = useApi();
  

  // TODO : 비밀번호 변경
  const updateUserPw = useCallback(()=>{
    if (!isPwChecked){
        alert('비밀번호 확인을 해주세요')
    }else{
        api.put('member/info/updatepassword', {
            "id": originId, 
            "oldPassword": userData['originPassword'], 
            "newPassword": userData['password']
          })
        .then(()=>{
            alert('비밀번호 변경이 완료되었습니다')
        })
        .catch((err)=>{
            alert(err.response.data.message)
        })    
    }
},[originId,api,userData,isPwChecked])

// TODO: 회원 정보 변경

const updateUserInfo = useCallback(()=>{
    if((isEmailChecked || (!userData['email'] && !userData['emailCode'])) && 
    (isPwChecked || (!userData['password'] && !checkPwText))){
      console.log(userData)
      console.log(isEmailChecked)
        api.put(`member/info/update`,{
        "email": userData['email']? userData['email']:originData['email'],
        "id": originId,
        "name": userData['name']? userData['name']:originData['name'],
        "phone": userData['phone']? userData['phone']:originData['phone`']
    })
    .then((res)=>{
        console.log(res)
        if(res.data.code === 200){
          alert('회원정보 수정이 완료되었습니다')
        }
        else{
          console.log(res);
        }
    })
    .catch((err)=>{
        console.log(err)
    })
    }else{
        if((!isPwChecked && (checkPwText || userData['password']))){
            alert('비밀번호 확인을 해주세요');
            console.log(checkPwText)
            return
          }
          if(!isEmailChecked || !userData['emailCode']){
            alert('이메일 인증을 해주세요');
            return
          }
    }
},[userData,api,isPwChecked,checkPwText,isEmailChecked,originId,originData])

  
  // TODO : 유저 정보 조회

  const getUserInfo = useCallback(async()=>{
    await api.get(`member/info/${originId}`)
    .then(({data})=>{
      setOriginData({
        name: data.data['name'],
        email: data.data['email'],
        phone: data.data['phone']
      });
    })
    .catch((err)=>{console.log(err)})
  },[originId,api])

  useEffect(()=>{
    getUserInfo();
  },[getUserInfo])
  // TODO : 이메일 전송 로직
  //FIXME : 중복코드 수정 및 useAPI활용필요
  const sendEmail = useCallback(()=>{
    axios({
      method: 'post',
      url: `${process.env.REACT_APP_SPRING_URL}/member/sendemail`,
      headers:{
        "content-type": "application/json"
      },
      data:{
        email : userData['email']
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
  },[userData])

  // TODO : 전송된 인증코드 확인 로직
  const checkEmailCode = useCallback(()=>{
    axios({
      method: 'post',
      url: `${process.env.REACT_APP_SPRING_URL}/member/sendemail/verify`,
      headers:{
        "content-type": "application/json"
      },
      data:{
        code: userData['emailCode'],
        email: userData['email']
      }
    })
    .then(()=>{
      setEmailValidText('올바른 인증번호입니다.');
      setIsEmailChecked(true)
      setVerifyCode({
        email : userData['email'],
        emailCode : userData['emailCode']
      });
      emailTextRef.current.style.color = "blue";
    })
    .catch((err)=>{
      console.log(err)
      setEmailValidText('인증번호가 올바르지 않습니다');
      setIsEmailChecked(false);
      emailTextRef.current.style.color = "red";
    })
  },[userData])

  // TODO: 비밀번호 정규식 확인 로직
  const checkPwReg = useCallback((e)=>{
    const pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    if(userData['password'] && pwReg.test(userData['password'])){
      if ( userData['password'] === e.target.value){
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

  },[userData])
  // TODO : 비밀번호 확인 일치 로직
  const checkPassword = useCallback(e=>{
    const pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    setCheckPwText(e.target.value);
    checkPwReg(e);
    if(userData['password'] && pwReg.test(userData['password'])){
      if ( userData['password'] === e.target.value){
        setIsPwChecked(true);
        setPwValidText('비밀번호가 일치합니다');
        pwTextRef.current.style.color = "blue";
      }
    }
    
  },[checkPwReg,userData])
  
  return (
    <section className='user'>
        <div className='user-container'>
            <img src="../../logo.svg" alt="로고" className='user-logo'/>
            <article className='user-box'>
                <div className='user-box-title'>회원정보</div>
              <label className='user-input'>
                <div className='user-input-title'>이름</div>
                <input 
                  defaultValue={originData['name']} 
                  className='user-input-box'
                  onChange={e=>{
                    userData["name"] = e.target.value; 
                  }}/>
              </label>
              <label className='user-input'>
                <div className='user-input-title'>아이디</div>
                <div 
                  className='user-id-box' 
                >{originId}</div>
              </label>
              <label className='user-input'>
                <div 
                  className='user-input-title'>비밀번호</div>
                  <input 
                  placeholder='기존 비밀번호' 
                  type='password' 
                  autoComplete='new-password' 
                  className='user-input-box'
                  onChange={e=>{
                    userData['originPassword'] = e.target.value;
                    checkPwReg(e);
                  }}
                />
                <input 
                  placeholder='비밀번호' 
                  type='password' 
                  autoComplete='new-password' 
                  className='user-input-box'
                  onChange={e=>{
                    userData['password'] = e.target.value;
                    checkPwReg(e);
                  }}
                />
                <input 
                  placeholder='비밀번호 확인' 
                  type='password' 
                  autoComplete='new-password' 
                  className='user-input-box'
                  onChange={e=>{checkPassword(e)}}
                  onKeyUp={e=>{
                    if(e.keyCode===13){
                        updateUserPw();
                    }
                }}
                  />
                <p ref={pwTextRef} className='user-input-tag'>{pwValidText}</p>
                <button onClick={updateUserPw} 
                className='btn-m'>비밀번호 변경</button>
              </label>


              <label className='user-input'>
                <div className='user-input-title'>이메일</div>
                <div className='user-input-email'>
                  <input 
                    defaultValue={originData['email']}
                    className='user-input-box-email'
                    onChange={e=>{
                      userData['email'] = e.target.value;
                      if(userData['email']===verifyCode['email'] 
                      && userData['emailCode'] === verifyCode['emailCode']){
                        setIsEmailChecked(true)
                        setEmailValidText('올바른 인증번호입니다.');
                        emailTextRef.current.style.color = "blue";
                      }else{
                        setEmailValidText('인증 확인을 해주세요');
                        setIsEmailChecked(false);
                        emailTextRef.current.style.color = "red";
                      }

                    }}
                  />
                  <button 
                    className='user-input-box-button' 
                    onClick={sendEmail}>전송</button>
                </div>
                <div className='user-input-email'>
                  <input 
                    placeholder='인증번호' 
                    className='user-input-box-email'
                    onChange={e=>{
                      userData['emailCode'] = e.target.value;
                      if(userData['email']===verifyCode['email'] 
                      && userData['emailCode'] === verifyCode['emailCode']){
                        setIsEmailChecked(true)
                        setEmailValidText('올바른 인증번호입니다.');
                        emailTextRef.current.style.color = "blue";
                      }else{
                        setEmailValidText('인증확인을 해주세요');
                        setIsEmailChecked(false);
                        emailTextRef.current.style.color = "red";
                      }
                    }} />
                  <button 
                    className='user-input-box-button'
                    onClick={checkEmailCode}>인증</button>
                </div>
                <p ref={emailTextRef} className='user-input-tag'>{emailValidText}</p>
              </label>
              <label className='user-input'>
                <div className='user-input-title'>전화번호</div>
                <input 
                  defaultValue={originData['phone']}
                  className='user-input-box'
                  onChange={e=>{
                    userData["phone"] = e.target.value;
                  }}/>
                  <p className='user-input-phone'>01012345678 형식으로 입력해주세요</p>
              </label>
            </article>
            <button className='user-btn' onClick={updateUserInfo}> 회원정보 수정
              </button>
        </div>

    </section>
  )
}

export default UpdateUser