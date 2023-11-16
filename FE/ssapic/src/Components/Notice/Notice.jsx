import React from 'react'
import { styled } from 'styled-components'

const Notice = () => {
  return (
    <Wrap>
      <TapBox>
        <Tap></Tap>
        <More></More>
      </TapBox>
      <ListBox>
        <ListItemNew><FlexRow><p>[공지] 개인정보처리방침 변경 사전 안내 (2023.11.20일 시행)</p><img src='ico_new.gif' alt='new' /></FlexRow></ListItemNew>
        <ListItemNew><FlexRow><p>[Waiver]시험성적이 아쉽다면? 지금바로 싸픽신청하세요!</p><img src='ico_new.gif' alt='new' /></FlexRow></ListItemNew>
        <ListItem><p>SSAPIc 채점기준 및 학습방법 안내</p></ListItem>
        <ListItem><p>평가장 내 마스크 착용 안내</p></ListItem>
        <ListItem><p>SSAPIc 취소정책 및 규정변경 고지</p></ListItem>
        <ListItem><p>동대문구, 미취업청년 자격취득 활동비 지원</p></ListItem>
      </ListBox>
    </Wrap>
  )
}

const Wrap = styled.div`
  width: 50%;
  height: 100%;
  box-sizing: border-box;
  background-image: url("list_notice_pic_01.gif");
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
  width: 104px;
  height: 20px;
  background-position: 0 -24px;
  background-image: url("list_notice_tab_01.gif");
  background-repeat: no-repeat;
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
export default Notice