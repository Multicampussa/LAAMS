import React, { useCallback, useRef, useState } from 'react'
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import useApi from '../../../Hook/useApi';
import { useNavigate } from 'react-router-dom';

const CreateErrorReport = () => {
    const api = useApi();
    const editorRef = useRef();
    const [errorData, setErrorData] = useState({});
    const navigate = useNavigate();

    const submitErrorReport = useCallback(()=>{
        if(!errorData['errorTime']){
            alert('에러 발생 시간을 입력해주세요')
            return
        }
        if(!errorData['title']){
            alert('제목을 입력해주세요')
            return
        }
        if(!errorData['type']){
            alert('에러 유형을 선택해주세요')
            return
        }
        if(!editorRef.current.getInstance().getHTML()){
            alert('내용을 입력해주세요')
            return
        }
        api.post(`director/errorReport`,{
            content: editorRef.current.getInstance().getHTML(),
            errorTime: errorData['errorTime'],
            title: errorData['title'],
            type: errorData['type']
        })
        .then(({data})=>{
            console.log(data.data)
            alert('에러리포트 작성이 완료되었습니다')
            navigate('/director')
            setErrorData({})
        })
  },[api,errorData,navigate])
    return (
        <section className='create-errorreport'>
            <div className='create-errorreport-text'>에러 리포트</div>
            <div className='create-errorreport-box'>
                <div className='create-errorreport-box-selects'>
                <select className='create-errorreport-select' onChange={
                e=>{
                    setErrorData(prevState=>({
                        ...prevState,
                        type : e.target.value
                    }))
                }
                }>
                    <option selected disabled>--에러 유형--</option>
                    <option value="네트워크">네트워크</option>
                    <option value="장비문제">장비 문제</option>
                    <option value="기타">기타</option>
                </select>
                <input className='create-errorreport-time' type="datetime-local" 
                onChange={
                e=>{
                    setErrorData(prevState=>({
                        ...prevState,
                        errorTime : e.target.value
                    }))
                }
                }/>
                </div>
            <input type="text" 
                className='create-errorreport-title' 
                placeholder='제목' 
                onChange={
                    e=>{
                        setErrorData(prevState=>({
                            ...prevState,
                            title : e.target.value
                        }))
                    }
                }/>
            </div>
            <div className='create-errorreport-wrap'>
                <Editor
                ref={editorRef}
                placeholder="내용을 입력해주세요"
                previewStyle="vertical"
                height="100%"
                initialEditType="wysiwyg"
                useCommandShortcut={false}
                plugins={[colorSyntax]}
                language="ko-KR"
                />
            </div>
            <button className='create-errorreport-btn'
            onClick={()=>submitErrorReport()}
             >작성</button>
        </section>
    )
}

export default CreateErrorReport