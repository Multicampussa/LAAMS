import React, { useCallback, useMemo, useState } from 'react'
import useApi from '../../../../Hook/useApi'
import { useSelector } from 'react-redux';
import loadingGif from '../../../../assets/images/Spinner-1s-200px.gif';

const Select = ({setMode, images, imageFiles}) => {
  const [selectedImage,setSelectedImage] = useState([])
  const api = useApi();
  const examineeNo = useSelector(state=>state.ExamineeDetail.examineeNo);
  const examineeName = useSelector(state => state.ExamineeDetail.examineeName);
  const [response, setResponse] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // TODO : 사진 비교 API
  const submitImages = useCallback(async()=>{
    const formData = new FormData();
    formData.append("existingPhoto",imageFiles[selectedImage[0]]);
    formData.append("newPhoto ",imageFiles[selectedImage[1]]);
    formData.append("examineeName",examineeName);
    formData.append("examineeNo",examineeNo );
    setIsLoading(true); // 요청 시작
    try {
      const {data} = await api({
        url:"director/comparison",
        method:"post",
        data:formData,
        headers:{
          "Content-Type":"multipart/form-data"
        }
      });
      setResponse(data.data); 
    } catch (err) {
      console.log(err);
    }
    setIsLoading(false); 
  },[api,imageFiles,examineeName,examineeNo,selectedImage]);

  // TODO : 사진 선택 로직
  const handleSelect = useCallback((index)=>{
    if(selectedImage.length>=2){
      if(selectedImage && selectedImage.includes(index)){
        const idx = selectedImage.indexOf(index);
        const newSelects = [...selectedImage]
        newSelects.splice(idx,1);
        setSelectedImage(newSelects)
      }else{
        alert('사진을 두장만 선택해주세요')
        return
      }
    }
    if(selectedImage && selectedImage.includes(index)){
      const idx = selectedImage.indexOf(index);
      const newSelects = [...selectedImage]
      newSelects.splice(idx,1);
      setSelectedImage(newSelects)

    }else{
      const newSelects = [...selectedImage]
      newSelects.push(index)
      setSelectedImage(newSelects)
    }
  },[selectedImage])

  //TODO : 사진 리스트
  const articles = useMemo(()=>{
    return images.map((e,index)=>{
      return  <div className={`exam-docs-select-box-${selectedImage.includes(index)? 'true':'false'}`}
                  onClick={()=>{
                    handleSelect(index)
                  }}>
                <img className='exam-docs-select-image' 
                  src={e} 
                  alt="이미지" />
              </div>
    })
  },[images, handleSelect,selectedImage])

  return (
    <div className='exam-docs-select'>
      <div className='exam-docs-container'>
        <div className='exam-docs-container-title'>
          사진 비교
        </div>
        <div className='exam-docs-select-info'>
          사진 두 장을 선택해주세요
        </div>
        { isLoading? <div className='exam-docs-loading'>
          <img src={loadingGif} alt="로딩" /></div> : 
          <div className='exam-docs-select-container'>
              {
                articles
              }
          </div>}

        <div className={`exam-docs-select-response-${response? 'true':'false'}`}>
          {response? response:null}
        </div>
        <div className='exam-docs-btn-container'>
          <button className='exam-docs-compare-btn' onClick={()=>{setMode('slider-submit'); setResponse(null)}}>돌아가기</button>
          <button className='exam-docs-compare-btn'
            onClick={()=>{
              setResponse(null);
            if(selectedImage.length<=1){
              alert('사진 2장을 선택해주세요')
              return
            }
            submitImages();
          }}
          >사진 비교</button>
        </div>
      </div>
    </div>
  )
}

export default Select