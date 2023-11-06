import React, { useEffect, useMemo, useState } from 'react'
import useApi from '../../../Hook/useApi';
import { Link } from 'react-router-dom';

const List = () => {
  const [errorReport,setErrorReport] = useState([]);
  const api = useApi();

  useEffect(()=>{
    api.get("manager/errorreport")
      .then(({data})=>setErrorReport(data))
      .catch((err)=>console.log(err.response));
  },[api]);

  const errorReportItems = useMemo(()=>{
    return errorReport.map((e,idx)=>{
      const errorTime = new Date(e.errorTime);
      return <Link key={idx} to={`/manager/error-report/${e.errorReportNo}`}>
      <li className='manager-errorreport-list-item'>
        <div>{e.errorReportNo}</div>
        <div>{e.title}</div>
        <div>{e.errorType}</div>
        <div>{e.directorName}</div>
        <div>{`${errorTime.getFullYear()}.${errorTime.getMonth()+1}.${errorTime.getDate()}`}</div>
      </li></Link>
    });
  },[errorReport]);

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>에러리포트</h3>
      </div>
      <div className='list-box'>
        <ul>
          <li className='manager-errorreport-list-title'>
            <div>No</div>
            <div>제목</div>
            <div>에러 유형</div>
            <div>감독관</div>
            <div>발생시간</div>
          </li>
          {
            errorReportItems
          }
        </ul>
        <ul className='flex-row-center gap-1'>
          <li><button>처음</button></li>
          <li><button>이전</button></li>
          <li><button>1</button></li>
          <li><button>다음</button></li>
          <li><button>끝</button></li>
        </ul>
      </div>
    </section>
  )
}

export default List