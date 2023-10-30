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
  const handleCreate = useCallback(() => {
    api.post("app/notice/create",{
      "content": editorRef.current.getInstance().getHTML(),
      "memberId": localStorage.getItem("id"),
      "title": title
    }).then(({data})=>{
      navigate("/notice");
    })
  },[api,title,navigate]);

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
        />
      </div>
      <button onClick={handleCreate} className='notice-create-btn'>작성하기</button>
    </div>
  )
}

export default Create