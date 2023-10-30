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
    api.get(`app/notice/detail/${noticeNo}`)
      .then(({data})=>{setData(data.data)})
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