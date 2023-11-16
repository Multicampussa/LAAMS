import React, { useCallback, useEffect, useState } from 'react';
import { Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { useParams } from 'react-router-dom';
import useApi from '../../Hook/useApi';

const Detail = () => {
  const {noticeNo} = useParams();
  const api = useApi();
  const [data,setData] = useState();
  useEffect(()=>{
    if(!noticeNo) return;
    api.get(`notice/detail/${noticeNo}`)
    .then(({data})=>{
      const content = data.data.content;

      // <img> 태그의 src 속성을 찾아서 변경
      const updatedContent = content.replace(/<img src="blob:[^"]*"/g, `<img src="${data.data.attachFile}"`);

      setData({
        ...data.data,
        content: updatedContent, // 변경된 content를 저장
      });
    })
      .catch(err=>console.log(err.response));
  },[api,noticeNo]);

  const getTimeFormat = useCallback(()=>{
    if(!data) return "";
    const date = new Date(data.createdAt);

    return `${date.getMonth()+1}.${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
  },[data])
  return (
    <div className='notice-detail'>
      <div className='notice-detail-title'>
        <div>{data? `[공지사항] ${data.title}` : ""}</div>
        <div>{getTimeFormat()}</div>
      </div>
      <div className="notice-detail-wrap">
        {
          data ? <Viewer initialValue={data.content} /> : null
        }
      </div>
    </div>
  )
}

export default Detail