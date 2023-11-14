import React, { useCallback, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import styled from "styled-components";
import axios from "axios";

const Home = () => {
  const navigate = useNavigate(); 
  const [userData, setUserData] = useState({});
  const userLogin = useCallback(()=>{
    axios({
      method: 'post',
      url: `${process.env.REACT_APP_SPRING_URL}/api/v1/member/login/examinee`,
      data:{
        birth: userData['birth'],
        examineeCode: userData['examineeCode']
      }
    })
    .then(({data})=>{
      // 더미 데이터 없어서 테스트 해봐야 함
      const newUserData = [...userData];
      newUserData['accessToken'] = data.accessToken
      setUserData(newUserData)
      navigate('/exam/wait', {state: {...userData}})
    })
    .catch((err)=>{
      console.log(err)
      alert("로그인 정보를 확인해주세요")
    })
  },[userData,navigate])
  return (
    <Wrap>
      <LoginImg></LoginImg>
      <LoginContainer>
        <LoginTitle>SSAPIc</LoginTitle>
        <LoginBox>
          <CodeLabel>
            <CodeLabelText>수험번호</CodeLabelText>
            <CodeInput placeholder='수험번호 8자를 입력해주세요' 
              onChange={(e)=>{
              userData['examineeCode'] = e.target.value;
              }}>
            </CodeInput>
          </CodeLabel>
          <BirthLabel>
            <BirthLabelText>비밀번호</BirthLabelText> 
            <BirthInput placeholder='생년월일 8자를 입력해주세요'
              onChange={(e)=>{
                userData['birth'] = e.target.value;
                }}
            ></BirthInput>
          </BirthLabel>
          <LoginBtn 
          onClick={()=>{
            userLogin();
          }}>시험 입장</LoginBtn>
        </LoginBox>
      </LoginContainer>
    </Wrap>

  )
}
const Wrap = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`
const LoginTitle = styled.div`
  font-size: 5rem;
  font-weight: 700;
  text-align: center;
  color:  #2172bb;
`
const LoginContainer = styled.div`
  width: 50%;
  height: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`
const LoginBox = styled.div`
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
`
const CodeLabel = styled.label`
  display: flex;
  flex-direction: column;

`
const CodeLabelText = styled.div`
  font-size: 2rem;
  font-weight: 600;
  color: #373737;
  padding-bottom: 1rem;
`

const BirthLabel = styled.label`
  display: flex;
  flex-direction: column;
  
`
const BirthLabelText = styled.div`
  font-size: 2rem;
  font-weight: 600;
  color: #373737;
  padding-bottom: 1rem;
`

const CodeInput = styled.input`
  border: 0.1rem solid lightgray;
  border-radius: 1rem;
  height: 5rem;
  padding-left: 1rem;
  font-size: 1.5rem;
`
const BirthInput = styled.input`
  border: 0.1rem solid lightgray;
  border-radius: 1rem;
  height: 5rem;
  padding-left: 1rem;
  font-size: 1.5rem;
`

const LoginBtn = styled.button`
  width: 100%;
  height: 5rem;
  font-size: 2rem;
  font-weight: 600;
  background-color: #2172bb;
  color: white;
  border-radius: 1rem;
`

const LoginImg = styled.div`
  width: 50%;
  height: 100%;
  background-image: url("/examHome.svg");
  background-repeat: no-repeat;
  background-size: cover;
  background-color: #cfdaf5;
`

export default Home