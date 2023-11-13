import React, { useEffect, useMemo, useRef, useState } from 'react'
import { styled } from 'styled-components'

const Slider = () => {
  const timer = useRef(null);
  const [isSlideStop,setIsSlideStop] = useState(false);
  const [slideIdx,setSlideIdx] = useState(0);
  const images = useRef([
    "EventBannerServlet_maxtrixfile1_202212201427501_.jpg",
    "EventBannerServlet_maxtrixfile1_202212201450581_.jpg",
    "EventBannerServlet_maxtrixfile1_202303211537571_.jpg",
    "EventBannerServlet_maxtrixfile1_202306291328121_.jpg",
    "EventBannerServlet_maxtrixfile1_202311011019551_.jpg",
    "EventBannerServlet_maxtrixfile1_202311081018551_.jpg",
  ]);

  const slideItems = useMemo(()=>{
    return images.current.map((e,idx)=><ImageItem $src={e} key={idx}>
    </ImageItem>)
  },[])

  const slideBtns = useMemo(()=>{
    return images.current.map((e,idx)=><SlideBtn key={idx} $isShow={idx===slideIdx} onClick={()=>setSlideIdx(idx)}></SlideBtn>)
  },[slideIdx]);

  useEffect(()=>{
    if(!isSlideStop){
      timer.current = setInterval(()=>{
        setSlideIdx(e=>(e+1)%images.current.length);
      },[2*1000]);
    }else{
      clearInterval(timer.current);
    }
  },[isSlideStop])

  return (
    <Wrap>
      <SliderBox $idx={`-${slideIdx}00%`}>
        {slideItems}
      </SliderBox>
      <SlideBtnBox>
        {slideBtns}
        {
          isSlideStop ?
          <SlideStartBtn onClick={()=>setIsSlideStop(false)}><div className='hidden_txt'>슬라이드 정지해제 버튼</div></SlideStartBtn> 
          :<SlideStopBtn  onClick={()=>setIsSlideStop(true)}><div className='hidden_txt'>슬라이드 정지 버튼</div></SlideStopBtn>
        }
      </SlideBtnBox>
    </Wrap>
  )
}

const Wrap = styled.div`
  width: 100%;
  height: 353px;
  position: relative;
  overflow: hidden;
`
const SliderBox = styled.ul`
  flex-grow: 1;
  height: 100%;
  display: flex;
  transform: translateX(${props=>props.$idx});
`

const ImageItem = styled.li`
  width: 100%;
  height: 100%;
  flex-shrink: 0;
  background-image: url(${props=>props.$src});
  background-repeat: no-repeat;
  background-position: center;
`

const SlideBtnBox = styled.ul`
  position: absolute;
  left: calc(50% - 490px);
  bottom: 30px;
  display: flex;
  gap: 10px;
`

const SlideBtn = styled.button`
  width: ${props=>props.$isShow ? "40px" : "16px"};
  height: 16px;
  background-color:${props=>props.$isShow ? "#5AA3C2" : "#FFFFFF"};
  box-sizing: border-box;
  box-shadow: 0px 4px 16px rgba(0, 0, 0, 0.5);
  border-radius: 16px;
`

const SlideStopBtn = styled.button`
  display: flex;
  gap: 3px;
  width: 9px;
  height: 16px;
  &::before,&::after{
    display: block;
    width: 3px;
    height: 16px;
    content: "";
    background-color: #5AA3C2;
  }
`

const SlideStartBtn = styled.button`
  border-bottom: 8px solid transparent;
  border-top: 8px solid transparent;
  border-left: 12px solid #5AA3C2;
  border-right: 8px solid transparent;
`

export default Slider