import React from 'react'
import { styled } from 'styled-components'

const News = () => {
  return (
    <Wrap>
      <TapBox>
        <Tap></Tap>
        <More></More>
      </TapBox>
      <ListBox>
        <ListItemNew><FlexRow><p>[아웃라우드]아웃라우드 런칭 기념 11월 한정 프로모션</p><img src='ico_new.gif' alt='new' /></FlexRow></ListItemNew>
        <ListItemNew><FlexRow><p>[모집] 삼성 청년 SW 아카데미 11기 (10/23~11/6)</p><img src='ico_new.gif' alt='new' /></FlexRow></ListItemNew>
        <ListItem><p>[특강] 싸픽노잼 x SSAPIc 주관사ㅣ아웃라우드 런칭 기념 특...</p></ListItem>
        <ListItem><p>[공지] CJ ONE 포인트 제휴 안내</p></ListItem>
        <ListItem><p>전국 시도별 지자체 SSAPIc 응시료 지원사업 현황</p></ListItem>
        <ListItem><p>[싸픽꿀팁] 챗 GPT로 싸픽 독학하기!</p></ListItem>
      </ListBox>
    </Wrap>
  )
}

const Wrap = styled.div`
  width: 50%;
  height: 100%;
  box-sizing: border-box;
  background-image: url("list_notice_pic_02.gif");
  background-repeat: no-repeat;
  background-position: 29px 69px;
  display: flex;
  flex-direction: column;
`
const TapBox = styled.div`
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  box-sizing: border-box;
  padding: 0 29px;
`

const Tap = styled.div`
  width: 99px;
  height: 20px;
  background-position: 0 -24px;
  background-image: url("list_notice_tab_02.gif");
`

const More = styled.div`
  width : 17px;
  height: 13px;
  background-image: url("ico_more.gif");
  cursor: pointer;
`

const ListBox = styled.ul`
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding-left: 226px;
  padding-right: 30px;
  list-style-type: disc;
  list-style-image: url("ico_dot.png");
  white-space: nowrap;
`

const ListItem = styled.li`
  padding-top: 9px;
  box-sizing: border-box;
  width: 100%;
  height: 25px;
  font-size: 12px;
  cursor: pointer;
  &:hover{
    font-weight: 900;
    color: #000000;
  }
  &>p{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`

const ListItemNew = styled.li`
  padding-top: 9px;
  box-sizing: border-box;
  width: 100%;
  height: 25px;
  font-size: 12px;
  color: #FF0000;
  cursor: pointer;
  &:hover{
    font-weight: 900;
    color: #000000;
  }
  & p{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`

const FlexRow = styled.div`
  display: flex;
`
export default News