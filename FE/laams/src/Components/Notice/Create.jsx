import React, { useCallback, useRef, useState } from 'react'
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import useApi from './../../Hook/useApi';
import { useNavigate } from 'react-router-dom';

const Create = () => {
  const editorRef = useRef();
  const api = useApi();
  const [title,setTitle] = useState();
  const navigate = useNavigate();
  const [file, setFile] = useState(null);

  const onUploadImage = async (blob, callback) => {
    const file = new File([blob], "image.jpg"); 
    setFile(file); 
    callback(URL.createObjectURL(file), 'alt text'); 
    return false;
  };

  const handleCreate = useCallback(() => {
    const formData = new FormData();
    if(file) {
      formData.append('file', file); // 파일 추가
    }
    formData.append('dto', new Blob([JSON.stringify({ // JSON 데이터 추가
      "content": editorRef.current.getInstance().getHTML(),
      "title": title
    })], { type: 'application/json' }));

    api.post("notice/create", formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    }).then(({data})=>{
      navigate("/notice");
    })
  },[api,title,navigate,file]);

  return (
    <div className='notice-create'>
      <input onChange={(e)=>setTitle(e.target.value)} className='notice-create-title' placeholder='제목' />
      <div className="notice-create-wrap">
        <Editor
          ref={editorRef}
          initialValue="hello react editor world!"
          previewStyle="vertical"
          height="100%"
          initialEditType="wysiwyg"
          useCommandShortcut={false}
          plugins={[colorSyntax]}
          language="ko-KR"
          hooks={{
            addImageBlobHook: onUploadImage
          }}
        />
      </div>
      <button onClick={handleCreate} className='notice-create-btn'>작성하기</button>
    </div>
  )
}

export default Create
