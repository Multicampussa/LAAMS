import React from 'react'
import { styled } from 'styled-components'

const ExamCurrent = ({data}) => {
  return (
    <Wrap>
      <div>
        <p>{data.examType}</p>
        <p>{`[${data.lng}]`}</p>
      </div>
      {
        data.center&&data.region?<div>
          <p>{`[${data.region}]`}</p>
          <p>{data.center}</p>
        </div>:null
      }
    </Wrap>
  )
}
const Wrap = styled.div`
  width: 25%;
  height: 100%;
  background-color: #214d80;
  box-sizing: border-box;
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  font-size: 15px;
  line-height: 23px;
  color: #c1dbf9;
  &>:first-child{
    font-weight: 900;
    font-size: 16px;
    color: #FFF;
  }
`

export default ExamCurrent