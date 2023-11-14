import React from 'react'
import { useLocation } from 'react-router-dom';
import styled from "styled-components";

const ExamWaiting = () => {
    const location = useLocation();
    return (
        <Wrap>
            <Container>
                <Title>SSAPIc</Title>
                <InfoText>테스트 페이지</InfoText>
                <TestBox>
                    <UserImg></UserImg>
                    <BtnBox>
                        <RecordingBtn>녹음 시작</RecordingBtn>
                        <PlayBtn>재생</PlayBtn>
                    </BtnBox>
                    <TestText>1. '녹음시작' 버튼을 눌러 녹음을 해주세요 <br /> <br />2. '재생' 버튼을 눌러 녹음된 음성을 확인해주세요
                    </TestText>
                    <NextBtn>다음</NextBtn>
                </TestBox>
            </Container>
        </Wrap>
  )
}
const Wrap = styled.div`
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
`
const Container = styled.div`
    width: 80%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
`
const Title = styled.div`
    width: 100%;
    height: 6rem;
    background-color: #2172bb;
    color: white;
    text-align: center;
    font-size: 5rem;
    line-height: 5.5rem;
`
const InfoText = styled.div`
    width: 100%;
    height: 6rem;
    border-bottom: 0.1rem solid lightgray;
    font-size: 3rem;
    line-height: 7rem;
`
const TestBox = styled.div`
    width: 100%;
    height: 70%;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    gap:2%;
    
`
const BtnBox = styled.div`
    display: flex;
    justify-content: space-between;
`
const UserImg = styled.div`
    width: 40%;
    height: 70%;
    background-image: url("/woman.jpg");
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
`
const RecordingBtn = styled.button`
    width: 49%;
    height: 6rem;
    background-color: #fad551;
    font-size: 3rem;
    border-radius: 1rem;
    text-align: center;
    font-weight: 600;
    white-space: nowrap;
`
const TestText = styled.div`
    width: 58%;
    font-size: 2rem;
    background-color: #f2f2f2;
    padding: 2rem 4rem;
    box-sizing: border-box;
`
const PlayBtn = styled.button`
    width: 49%;
    height: 6rem;
    align-self: flex-end;
    background-color: #fad551;
    white-space: nowrap;
    font-size: 3rem;
    text-align: center;
    font-weight: 600;
    border-radius: 1rem;
`
const NextBtn = styled.button`
    background-color: #2172bb;
    color: white;
    width: 30rem;
    height: 6rem;
    font-weight: 700;
    font-size: 3rem;
    border-radius: 1rem;
    align-self: flex-end;
`

export default ExamWaiting