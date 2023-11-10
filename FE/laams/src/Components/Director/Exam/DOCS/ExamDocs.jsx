import React, { useCallback, useMemo, useRef, useState } from 'react'

const ExamDocs = () => {
  const slideIdx = useRef(1);
  const slideMaxIdx = useRef(1);
  const docsBox = useRef();
  const [images,setImages] = useState([]);
  const [imageFiles,setImageFiles] = useState([]);
  const [mode,setMode] = useState("slider-submit");
  const [addMode,setAddMode] = useState("close");
  const handleSlidePrev = useCallback(()=>{
    if(slideIdx.current <= 1) return;
    slideIdx.current-=1;
    docsBox.current.style.transform = `translateX(-${slideIdx.current-1}00%)`;
  },[]);
  const handleSlideNext = useCallback(()=>{
    if(slideIdx.current >= slideMaxIdx.current) return;
    slideIdx.current+=1;
    docsBox.current.style.transform = `translateX(-${slideIdx.current-1}00%)`;
  },[]);
  const handleAddImage = useCallback((e)=>{
    const reader = new FileReader();
    setImageFiles(el=>[...el,e.target.files[0]]);
    reader.readAsDataURL(e.target.files[0]);
    reader.onloadend = () => {
      setImages(el=>[...el,reader.result]);
      slideMaxIdx.current+=1;
      setAddMode("close");
   	};
  },[]);

  const articles= useMemo(()=>{
    return images.map(e=>{
      return <article className='exam-docs-article'>
        <img className='exam-docs-article-image' src={e} alt='' />
      </article>
    })
  },[images])

  const handleSubmit = useCallback(()=>{
    console.log(imageFiles);
  },[imageFiles])

  return (
    <section className='exam-docs'>
      <div className={`exam-docs-${mode}`}>
        <div className='exam-docs-container'>
          <div className='exam-docs-container-title'>
            추가 서류 제출
          </div>
          <button onClick={handleSlidePrev} className='exam-docs-prev'><div className='hidden-text'>이전</div></button>
          <button onClick={handleSlideNext} className='exam-docs-next'><div className='hidden-text'>다음</div></button>
          <article className={`exam-docs-add-box${addMode}`}>
            <button className='exam-docs-add-close' onClick={()=>setAddMode("close")}><div className='hidden-text'>닫기</div></button>
            <label htmlFor='exam-docs-add-image' className='exam-docs-add-btn'>파일선택</label>
            <input accept='image/*' onChange={(e)=>handleAddImage(e)} id='exam-docs-add-image' type='file'/>
            <button onClick={()=>{setMode("slider-capture");setAddMode("close");}} className='exam-docs-add-btn'>캡처 </button>
          </article>
          <div className='exam-docs-box'>
            <div className='exam-docs-slider' ref={docsBox}>
              {
                articles
              }
              <button onClick={()=>setAddMode("open")} className='exam-docs-article'><div className='hidden-text'>사진추가</div></button>
            </div>
          </div>
          <button onClick={handleSubmit} className='exam-docs-btn'>추가서류 제출</button>
        </div>
        <div className='exam-docs-capture'>
          <div className='exam-docs-container-title'>
            추가 서류 제출
          </div>
          <div className='exam-docs-box'>
            
          </div>
          <input/>
          <button onClick={()=>setMode("slider-submit")} className='exam-docs-btn'>사진 캡처</button>
        </div>
      </div>
    </section>
  )
}

export default ExamDocs