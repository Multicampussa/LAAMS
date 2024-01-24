import React, { useCallback, useEffect, useRef, useState } from 'react'
import { styled } from 'styled-components'
import * as tf from '@tensorflow/tfjs-core';
import '@tensorflow/tfjs-backend-webgl';
import * as blazeface from '@tensorflow-models/blazeface';
import axios from "axios";

const ExamCamera = ({ accessToken}) => {
  const videoRef = useRef();
  const canvasRef = useRef();
  const timer = useRef();
  const model = useRef();
  const [time,setTime] = useState(5);
  const isLogin = useRef(false);

  //TODO : 모델을 초기화
  useEffect(() => {
    const initFD = async () => {
      await tf.setBackend('webgl');
      model.current = await blazeface.load();
      if(model.current){
        const temp = setInterval(()=>{setTime(e=>e-1)},[1000])
        timer.current = temp;
      }
    }
    initFD();
  }, [])

  const estimateCanvas = useCallback( async (canvasRef) => {
    if(!model.current) return;
    const predictions = await model.current.estimateFaces(canvasRef, false);
    return predictions;
  },[])

  const getWebcam = useCallback((callback) => {
    try {
      const constraints = {
        'video': true,
        'audio': false
      }
      navigator.mediaDevices.getUserMedia(constraints)
        .then(callback);
    } catch (err) {
      console.log(err);
      return undefined;
    }
  },[])
  
  
  useEffect(() => {
    getWebcam((stream => {
      videoRef.current.srcObject = stream;
    }));
  }, [getWebcam]);

  const drawToCanvas = useCallback(async () => {
    try {
      const ctx = canvasRef.current.getContext('2d');
      canvasRef.current.width = videoRef.current.videoWidth;
      canvasRef.current.height = videoRef.current.videoHeight;

      if (ctx && ctx !== null) {
        if (videoRef.current) {
          ctx.translate(canvasRef.current.width, 0);
          ctx.scale(-1, 1);
          ctx.drawImage(videoRef.current, 0, 0, canvasRef.current.width, canvasRef.current.height);
          ctx.setTransform(1, 0, 0, 1, 0, 0);
        }

        const preds = await estimateCanvas(canvasRef.current);
        if(!preds) {
          return;
        }
        if(!isLogin.current&&preds.length===0){
          alert("화면 밖으로 나갔습니다");
          setTime(5);
          return;
        }
        for (let i = 0; i < preds.length; i++) {
          let p = preds[i];
          ctx.strokeStyle = "#FF0000";
          ctx.lineWidth = 5;
          ctx.strokeRect(p.topLeft[0], p.topLeft[1], p.bottomRight[0] - p.topLeft[0], p.bottomRight[1] - p.topLeft[1]);
          if(p.landmarks.length !== 0){
            //0 : 왼쪽눈, 1 : 오른쪽 눈, 2:코, 5: 입, 4:왼쪽 귀, 5:오른쪽 귀

            const leftLine = Math.sqrt((p.landmarks[0][0]-p.landmarks[2][0])*(p.landmarks[0][0]-p.landmarks[2][0])+(p.landmarks[0][1]-p.landmarks[2][1])*(p.landmarks[0][1]-p.landmarks[2][1]));
            const rightLine = Math.sqrt((p.landmarks[1][0]-p.landmarks[2][0])*(p.landmarks[1][0]-p.landmarks[2][0])+(p.landmarks[1][1]-p.landmarks[2][1])*(p.landmarks[1][1]-p.landmarks[2][1]));

            const ratio = leftLine/rightLine;
            if(isLogin.current){
              if(ratio < 0.7){
                setTime(-1);
                alert(`왼쪽으로 고개를 돌렸습니다! : ${ratio}`);
              }else if(ratio > 1.7){
                setTime(-1);
                alert(`오른쪽으로 고개를 돌렸습니다! : ${ratio}`);
              }
            }
          }
        }
      }
    } catch (err) {
      console.log(err);
    }
  },[estimateCanvas])

  useEffect(()=>{
    const temp = setInterval(() => {drawToCanvas();}, [200]);
    return ()=>clearInterval(temp);
  },[drawToCanvas])

  useEffect(()=>{
    if(time===-2){
      clearInterval(timer.current);
      timer.current=null;
      // FIXME : 출결 API 쏘기
      axios({
        method: 'post',
        url: `${process.env.REACT_APP_SPRING_URL}/api/v1/examinee/attendance`,
        headers: {Authorization: "Bearer "+ accessToken,},
      })
      .then(()=>{
        isLogin.current=true;
        alert('출석 완료했습니다')
      })
      .catch((err)=>console.log(err))
    }
  },[time,accessToken])

  return (
    <Wrap $isHidden={time===0}>
      {
        time >= 0 ? <Count>{time}초 뒤에 자동 출석됩니다<br/> 화면에서 나갈시 카운트가 초기화됩니다</Count> : null
      }
      <Modal>
        <Video ref={videoRef} autoPlay/>
        <CanvasVideo ref={canvasRef}/>
      </Modal>
    </Wrap>
  )
}
const Count = styled.div`
  position: fixed;
  z-index: 999;
  font-size: 16px;
  font-weight: 900;
  top: 5%;
  width: 500px;
  background-color: #FFF;
  border: 1px solid #000;
  box-sizing: border-box;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
`
const Wrap = styled.div`
  position: fixed;
  width: 100vw;
  height: 100vh;
  z-index: 999;
  background-color: rgba(0,0,0,0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  visibility: ${props=>props.$isHidden?"hidden":"none"};
`
const Modal = styled.div`
  position: relative;
  width: 1024px;
  height: 90%;
  background-color: #FFF;
`

const Video = styled.video`
  position: absolute;
  width: 100%;
  height: 100%;
  display: none;
`

const CanvasVideo = styled.canvas`
  position: absolute;
  width: 100%;
  height: 100%;
`
export default ExamCamera