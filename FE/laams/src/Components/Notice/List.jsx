import React, { useCallback, useEffect, useMemo, useState } from 'react'
import useApi from './../../Hook/useApi';
import { useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';

const List = () => {
  const api = useApi();
  const [noticeData,setNoticeData] = useState([]);
  const [page,curPage] = useState(1);
  const [count,setCount] = useState(10);
  const [total,setTotal] = useState();
  const authority = useSelector(state=>state.User.authority);
  const navigate = useNavigate();
  //TODO : 페이지 총 개수 저장
  useEffect(()=>{
    api.get("app/notice/count")
      .then(({data})=>setTotal(data.data.count))
      .catch(err=>console.log(err.response));
  },[api]);

  //TODO : 공지사항 목록 호출 및 저장
  useEffect(()=>{
    api.get(`app/notice/list?count=${count}&page=${page}`)
      .then(({data})=>{
        setNoticeData(data.data);
      })
      .catch(err=>console.log(err.response));
  },[api,count,page]);

  //TODO : 시간형식으로 변환
  const getTimeFormat = useCallback((date)=>{
    return `${date.getMonth()+1}.${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
  },[]);

  //TODO : 공지사항 세부페이지 이동
  const handleNoticeItem = useCallback((noticeNo)=>{
    navigate(`/notice/detail/${noticeNo}`);
  },[navigate]);

  //TODO : 공지사항 Data를 Div로 변환 및 반환
  const noticeItems = useMemo(()=>{
    return noticeData.map((e,idx)=><li onClick={()=>handleNoticeItem(e.noticeNo)} key={idx} className='notice-list-item'>
      <div>{e.noticeNo}</div>
      <div>{e.title}</div>
      <div>{getTimeFormat(new Date(e.createdAt))}</div>
    </li>)
  },[noticeData,getTimeFormat,handleNoticeItem]);

  return (
    <section className='list'>
      <div className='list-title'>
        <h3>공지사항</h3>
      </div>
      <div className='list-box notice-list-box'>
        {
          authority === "ROLE_MANAGER" ? <Link to="/notice/create" className='notice-list-create'>생성</Link> : null
        }
        <ul>
          <li className='notice-list-item'>
            <div>No</div>
            <div>제목</div>
            <div>작성 시간</div>
          </li>
          {
            noticeItems
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