import React, { useCallback, useEffect, useState } from 'react';
import { Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { useParams } from 'react-router-dom';
import useApi from '../../../Hook/useApi';

const Detail = () => {
  const {errorreportNo} = useParams();
  const api = useApi();
  const [data,setData] = useState();
  useEffect(()=>{
    if(!errorreportNo) return;
    api.get(`manager/errorreport/${errorreportNo}`)
      .then(({data})=>{
        setData(data);
      })
      .catch(err=>console.log(err));
  },[api,errorreportNo]);

  const getTimeFormat = useCallback(()=>{
    const date = new Date(data.errorTime);

    return `${date.getFullYear()}.${date.getMonth()+1}.${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
  },[data])
  return (
    <div className='notice-detail'>
      <div className='flex-row-center-center gap-1'>
        <div className='notice-detail-title'>
          <div>
            <div>{data? `[${data.errorType}] ${data.title}` : ""}</div>
          </div>
          <div>{data ? `${data.directorName} ${getTimeFormat()}` : ""}</div>
        </div>
        <button className='btn-s'>승인</button>
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