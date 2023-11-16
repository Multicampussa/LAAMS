import React ,{useCallback,useEffect,useRef} from 'react'

const Capture = ({mode,setImages,setMode,setImageFiles,slideMaxIdx}) => {
  const videoRef = useRef();
  const titleRef = useRef("");

  const getWebcam = useCallback((callback) => {
    try {
      const constraints = {
        'video': true,
        'audio': false,
        'facingMode': { exact: "environment" },
      }
      navigator.mediaDevices.getUserMedia(constraints)
        .then(callback);
    } catch (err) {
      console.log(err);
      return undefined;
    }
  },[]);

  useEffect(() => {
    if(mode === "slider-capture")
    getWebcam((stream => {
      videoRef.current.srcObject = stream;
    }));
  }, [getWebcam,mode]);

  const handleCapture = useCallback(()=>{
    if(!titleRef.current.value || titleRef.current.value===""){
      alert("추가 서류 제목을 입력해 주세요");
      return;
    }
    const canvas = document.createElement("canvas");
    const context = canvas.getContext('2d');
    canvas.width = videoRef.current.videoWidth;
    canvas.height = videoRef.current.videoHeight;
    context.translate(-videoRef.current.innerWidth, 0); // 좌우 반전
    context.drawImage(videoRef.current, 0, 0, canvas.width, canvas.height);
    const title = `${titleRef.current.value}.jpg`;
    canvas.toBlob((blob) => { //캔버스의 이미지를 파일 객체로 만드는 과정
      let file = new File([blob], title, { type: "image/jpeg" })
      const uploadFile = [file] //이미지 객체
      setImageFiles(el=>[...el,uploadFile[0]]);
    }, 'image/jpeg');

    setImages(el=>[...el,canvas.toDataURL()]);
    slideMaxIdx.current+=1;

    const s = videoRef.current.srcObject;
    s.getTracks().forEach((track) => {
      track.stop();
    });
    titleRef.current.value="";
    setMode("slider-submit");
  },[setImageFiles,setImages,setMode,slideMaxIdx])

  return (
    <div className='exam-docs-capture'>
      <input placeholder='추가 서류 제목을 입력해 주세요' ref={titleRef} className='exam-docs-capture-name' />
      <video autoPlay ref={videoRef} className='exam-docs-capture-video' />
      <button onClick={handleCapture} className='exam-docs-btn'>사진 캡처</button>
    </div>
  )
}

export default Capture