import React from 'react'
import { styled } from 'styled-components'

const ExamCurrent = ({data}) => {
  const weekList = ["일","월","화","수","목","금","토"];
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
      {
        data.date?<div>
          <p>{`${data.date.getFullYear()}.${data.date.getMonth()+1}.${data.date.getDate()}(${weekList[data.date.getDay()]}) ${data.date.getHours() < 10 ? "0"+data.date.getHours() : data.date.getHours()}:${data.date.getMinutes() < 10 ? "0"+data.date.getMinutes() : data.date.getMinutes()} ~ ${data.endDate.getHours() < 10 ? "0"+data.endDate.getHours() : data.endDate.getHours()}:${data.endDate.getMinutes() < 10 ? "0"+data.endDate.getMinutes() : data.endDate.getMinutes()}`} </p>
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