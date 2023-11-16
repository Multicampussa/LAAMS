import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { styled } from 'styled-components';
import ExamCurrent from './ExamCurrent';
import ExamDate from './ExamDate';
import ExamResult from './ExamResult';

const ExamSelect = ({setIsShowModal,setData,data,setMain}) => {
  const [center,setCenter] = useState();
  const [region,setRegion] = useState();
  const [totalData,setTotalData] = useState();
  
  useEffect(()=>{
    const centerData = [
      {
          "centerNo": 1,
          "centerName": "제주관광대 평생교육원 802호",
          "centerRegion": "제주"
      },
      {
          "centerNo": 2,
          "centerName": "중앙컴퓨터학원",
          "centerRegion": "경남"
      },
      {
          "centerNo": 3,
          "centerName": "랭듀어학원",
          "centerRegion": "경북"
      },
      {
          "centerNo": 4,
          "centerName": "엘키즈외국어학원(두호동)",
          "centerRegion": "경북"
      },
      {
          "centerNo": 5,
          "centerName": "SSAPIc광양센터",
          "centerRegion": "전남"
      },
      {
          "centerNo": 6,
          "centerName": "SSAPIc순천센터",
          "centerRegion": "전남"
      },
      {
          "centerNo": 7,
          "centerName": "SSAPIc전주센터(효자동)",
          "centerRegion": "전북"
      },
      {
          "centerNo": 8,
          "centerName": "전주상공회의소(6층 평가장)",
          "centerRegion": "전북"
      },
      {
          "centerNo": 9,
          "centerName": "리더스교육평가원(천안)",
          "centerRegion": "충남"
      },
      {
          "centerNo": 10,
          "centerName": "리더스교육평가원(청주)",
          "centerRegion": "충북"
      },
      {
          "centerNo": 11,
          "centerName": "민병철어학원 (청주분원)",
          "centerRegion": "충북"
      },
      {
          "centerNo": 12,
          "centerName": "연세대 연세플라자 308호",
          "centerRegion": "강원"
      },
      {
          "centerNo": 13,
          "centerName": "강원대(춘천) 경영대1호관 2층1201호",
          "centerRegion": "강원"
      },
      {
          "centerNo": 14,
          "centerName": "SSAPIc분당센터",
          "centerRegion": "경기"
      },
      {
          "centerNo": 15,
          "centerName": "SSAPIc수원SDA센터",
          "centerRegion": "경기"
      },
      {
          "centerNo": 16,
          "centerName": "SSAPIc영통센터(명성빌딩)",
          "centerRegion": "경기"
      },
      {
          "centerNo": 17,
          "centerName": "경희대 국제캠퍼스 SSAPIc평가센터(멀티미디어교육관203호)",
          "centerRegion": "경기"
      },
      {
          "centerNo": 18,
          "centerName": "SSAPIc병점센터",
          "centerRegion": "경기"
      },
      {
          "centerNo": 19,
          "centerName": "SSAPIc전자화성센터(반월동)",
          "centerRegion": "경기"
      },
      {
          "centerNo": 20,
          "centerName": "일산공인시험센터",
          "centerRegion": "경기"
      },
      {
          "centerNo": 21,
          "centerName": "대흥 리더스교육평가원(대전)",
          "centerRegion": "대전"
      },
      {
          "centerNo": 22,
          "centerName": "둔산SDA",
          "centerRegion": "대전"
      },
      {
          "centerNo": 23,
          "centerName": "SSAPIc광주센터(동천동)",
          "centerRegion": "광주"
      },
      {
          "centerNo": 24,
          "centerName": "SSAPIc인천센터(인천CBT센터)",
          "centerRegion": "인천"
      },
      {
          "centerNo": 25,
          "centerName": "SSAPIc부산센터",
          "centerRegion": "부산"
      },
      {
          "centerNo": 26,
          "centerName": "부산외대 트리니티홀 D동 101호",
          "centerRegion": "부산"
      },
      {
          "centerNo": 27,
          "centerName": "서면 파고다어학원 409호",
          "centerRegion": "부산"
      },
      {
          "centerNo": 28,
          "centerName": "서면SDA",
          "centerRegion": "부산"
      },
      {
          "centerNo": 29,
          "centerName": "4세대아카데미(구로)",
          "centerRegion": "서울"
      },
      {
          "centerNo": 30,
          "centerName": "SSAPIc서울SDA센터",
          "centerRegion": "서울"
      },
      {
          "centerNo": 31,
          "centerName": "SSAPIc시청센터",
          "centerRegion": "서울"
      },
      {
          "centerNo": 32,
          "centerName": "TIEC(Gangnam)구 국제사이버대학교",
          "centerRegion": "서울"
      },
      {
          "centerNo": 33,
          "centerName": "강남 쏭즈스튜디오",
          "centerRegion": "서울"
      },
      {
          "centerNo": 34,
          "centerName": "강남 파고다어학원",
          "centerRegion": "서울"
      },
      {
          "centerNo": 35,
          "centerName": "강남시험센터 메이플넥스 역삼",
          "centerRegion": "서울"
      },
      {
          "centerNo": 36,
          "centerName": "강서CBT센터",
          "centerRegion": "서울"
      },
      {
          "centerNo": 37,
          "centerName": "관악CBT센터",
          "centerRegion": "서울"
      },
      {
          "centerNo": 38,
          "centerName": "대학로공인시험센터(한국패션디자인학교 2층)",
          "centerRegion": "서울"
      },
      {
          "centerNo": 39,
          "centerName": "서울동부자격검정센터(태릉입구역)",
          "centerRegion": "서울"
      },
      {
          "centerNo": 40,
          "centerName": "신촌 파고다어학원 본관",
          "centerRegion": "서울"
      },
      {
          "centerNo": 41,
          "centerName": "영등포군장대학",
          "centerRegion": "서울"
      },
      {
          "centerNo": 42,
          "centerName": "한국디지털융합직업전문학교",
          "centerRegion": "서울"
      },
      {
          "centerNo": 43,
          "centerName": "SSAPIc구미센터",
          "centerRegion": "구미"
      }
    ];
    const total = {};
    centerData.forEach((e)=>{
      if(!total[e.centerRegion]){
        total[e.centerRegion] = [e.centerName];
      }else{
        total[e.centerRegion].push(e.centerName);
      }
    });
    setTotalData(total);
  },[])
  useEffect(()=>{
    if(!totalData) return;
    setRegion( Object.keys(totalData).sort((a,b)=>-(totalData[a].length-totalData[b].length))[0]);
  },[totalData]);
  const regionItem = useMemo(()=>{
    if(!totalData) return [];
    return Object.keys(totalData).sort((a,b)=>-(totalData[a].length-totalData[b].length)).map((e,idx)=>{
      if(region === e){
        return <ListItemActive key={idx}>
          {e}
        </ListItemActive>
      }else{
        if(idx%2===0){
          return <ListItem1 onClick={()=>setRegion(e)} key={idx}>
            {e}
          </ListItem1>
        }else{
          return <ListItem2 onClick={()=>setRegion(e)} key={idx}>
            {e}
          </ListItem2>
        }
      }
    })
  },[totalData,region])
  const centerItem = useMemo(()=>{
    if(!totalData || !region) return [];
    return totalData[region].map((e,idx)=>{
      if(center === e){
        return <ListItemActive key={idx}>
          {e}
        </ListItemActive>
      }else{
        if(idx%2===0){
          return <ListItem2 onClick={()=>setCenter(e)} key={idx}>
            {e}
          </ListItem2>
        }else{
          return <ListItem3 onClick={()=>setCenter(e)} key={idx}>
            {e}
          </ListItem3>
        }
      }
    })
  },[center,region,totalData]);

  const handleBtnNext = useCallback(()=>{
    data.center = center;
    data.region = region;
    if(data.date){
      setMain(<ExamResult setMain={setMain} setIsShowModal={setIsShowModal} setData={setData} data={data} />)
    }else{
      setMain(<ExamDate setIsShowModal={setIsShowModal} setData={setData} data={data} />)
    }
  },[center,region,setMain,data,setData,setIsShowModal]);

  return (
    <Wrap>
      <TitleBox>
        <Title>SSAPIc 시험신청</Title>
        <BtnClose onClick={()=>setIsShowModal(false)}>X</BtnClose>
      </TitleBox>
      <MainContainer>
        <CenterSelectBtn>
          <LayoutBox/>
          <p>센터선택</p>
        </CenterSelectBtn>
        <DateSelectBtn>
          <LayoutBox/>
          <p>날짜선택</p>
        </DateSelectBtn>
        <MainArea>
          <Header>센터선택</Header>
          <RegionArea>{regionItem}</RegionArea>
          <CenterArea>{centerItem}</CenterArea>
        </MainArea>
        <ExamCurrent data={data} />
      </MainContainer>
      <Hr/>
      <Btn onClick={handleBtnNext}>다음</Btn>
    </Wrap>
  )
}
const Wrap = styled.section`
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  width: 100%;
  height: 100%;
  & div{
    flex-shrink: 0;
  }
`
const TitleBox = styled.div`
  width: 100%;
  height: 70px;
  background-color: #296ebc;
  position: relative;
`

const Title = styled.h1`
  height: 70px;
  line-height: 70px;
  font-size: 26px;
  color: #FFF;
  text-align: center;
`
const BtnClose = styled.button`
  position: absolute;
  color: #FFFFFF;
  width: 70px;
  height: 70px;
  top: 0;
  right: 0;
  font-size: 50px;
`
const Btn = styled.button`
  font-size: 21px;
  display: block;
  width: 90%;
  margin: 10px 5% 10px 5%;
  background-color: #3fa0ee;
  text-align: center;
  font-size: 16px;
  font-weight: 900;
  height: 40px;
  color: #ffffff;
  cursor: pointer;
`

const MainContainer = styled.div`
  width: 100%;
  flex: 1;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
`

const Hr = styled.hr`
  background-color: #296ebc;
  width: 100%;
  height: 1px;
  border: 0;
  margin: 0;
`

const CenterSelectBtn = styled.button`
  width: 25%;
  height: 50%;
  background-image: url("ap_place2.png");
  background-position: center;
  background-repeat: no-repeat;
  -webkit-background-size: 69px;
  background-size: 48px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 20px;
`

const DateSelectBtn = styled.button`
  width: 25%;
  height: 50%;
  background-image: url("ap_calendar.png");
  background-position: center;
  background-repeat: no-repeat;
  -webkit-background-size: 69px;
  background-size: 69px;
  background-color: #e7e7e7;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 20px;
`

const LayoutBox = styled.div`
  width: 100%;
  height: 120px;
`

const MainArea = styled.div`
  position: relative;
  width: 50%;
  height: 100%;
  border: 1px solid #dddddd;
`
const CenterArea = styled.ul`
  position: absolute;
  top: 60px;
  left: 50%;
  width: 50%;
  height: calc(100% - 60px);
  overflow-y: auto;
  box-sizing: border-box;
  &::-webkit-scrollbar {
    width: 8px;  /* 스크롤바의 너비 */
  }
  &::-webkit-scrollbar-thumb {
    background: #3fa0ee; /* 스크롤바의 색상 */
    border-radius: 10px;
  }
`

const RegionArea = styled.ul`
  position: absolute;
  top: 60px;
  width: 50%;
  height: calc(100% - 60px);
  overflow-y: auto;
  box-sizing: border-box;
  &::-webkit-scrollbar {
    width: 8px;  /* 스크롤바의 너비 */
  }
  &::-webkit-scrollbar-thumb {
    background: #3fa0ee; /* 스크롤바의 색상 */
    
    border-radius: 10px;
  }
`
const ListItem1 = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  font-weight: 900;
  display: flex;
  align-items: center;
  cursor: pointer;
`

const ListItem2 = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  font-weight: 900;
  display: flex;
  align-items: center;
  background-color: #e7e7e7;
  cursor: pointer;
`

const ListItem3 = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  font-weight: 900;
  display: flex;
  align-items: center;
  background-color: #d9d9d9;
  cursor: pointer;
`

const ListItemActive = styled.li`
  width: 100%;
  height: 60px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #FFF;
  font-weight: 900;
  background: #313d48 url("icon_check_pad.png") no-repeat right center;
  -webkit-background-size: 41px;
  background-size: 41px;
`

const Header = styled.header`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 60px;
  box-sizing: border-box;
  padding-left: 10px;
  font-size: 20px;
  line-height: 60px;
  border-bottom: 1px solid #296ebc;
`


export default ExamSelect