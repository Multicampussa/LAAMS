import React, { useCallback, useEffect, useState } from 'react'
import { styled } from 'styled-components'
import ExamType from './ExamType';

const Modal = ({setIsShowModal}) => {
  const [data,setData] = useState({});
  const [main,setMain] = useState(null);

  const initMain = useCallback(()=>{
    setMain(<ExamType setIsShowModal={setIsShowModal} setData={setData} data={data} setMain={setMain} />);
  },[data,setIsShowModal])

  useEffect(()=>{
    initMain();
  },[initMain])
  return (
    <Wrap>
      <ModalMain>
        {
          main
        }
      </ModalMain>
    </Wrap>
  )
}

const Wrap = styled.div`
  position: fixed;
  top: 0;
  right: 0;
  width: 100vw;
  height: 100vh;
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0,0,0,0.6);
`
const ModalMain = styled.article`
  width: 1024px;
  height: 90%;
  background-color: #FFFFFF;
`
export default Modal