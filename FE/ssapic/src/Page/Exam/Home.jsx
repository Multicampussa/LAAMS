import React from 'react'
import { useNavigate } from 'react-router-dom';
import styled from "styled-components";

const Home = () => {
  const navigate = useNavigate();
  return (
    <Wrap>
      <LoginImg></LoginImg>
      <LoginContainer>
        <LoginTitle>SSAPIc</LoginTitle>
        <LoginBox>
          <CodeLabel>
            <CodeLabelText>수험번호</CodeLabelText>
            <CodeInput placeholder='수험번호를 입력해주세요'></CodeInput>
          </CodeLabel>
          <BirthLabel>
            <BirthLabelText>비밀번호</BirthLabelText> 
            <BirthInput placeholder='생년월일을 입력해주세요'></BirthInput>
          </BirthLabel>
          <LoginBtn onClick={()=>navigate("/exam/wait")}>시험 입장</LoginBtn>
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