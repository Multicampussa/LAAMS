# LAAMS
![Logo](https://github.com/Going9/LAAMS/assets/105552606/646666cf-7094-494b-8e1f-3b7d3b580b73)
# 🔥 프로젝트 개요

프로젝트 기간: 2023.10.09 ~ 2023.11.17 <br> 

Language Assessment Attendance Management System <br>
### 어학 및 자격 평가 출결 관리 시스템 LAAMS 

<br><br>

# 🙂 개발 멤버 소개

<table>
    <tr>
      <td align="center">
        <h5><a href="https://github.com/SeungHyunH">한승현</a></h5>
      </td>
      <td align="center">
        <h5><a href="https://github.com/yewon830">정예원</a></h5>
      </td>
      <td align="center">
        <h5><a href="https://github.com/seong-un">장성운</a></h5>
      </td>
      <td align="center">
        <h5><a href="https://github.com/Going9">공익규</a></h5>
      </td>
      <td align="center">
        <h5><a href="https://github.com/hjhj-kk">김현지</a></h5>
      </td>
      <td align="center">
        <h5><a href="https://github.com/pdanbi00">박단비</a></h5>
      </td>
    </tr>
    <tr>
        <td height="140px" align="center">
            <img src="https://avatars.githubusercontent.com/SeungHyunH" width="140px" /> <br>Team Leader, FE</td>
        <td height="140px" align="center">
            <img src="https://avatars.githubusercontent.com/yewon830" width="140px" /> <br>FE</td>
        <td height="140px" align="center">
            <img src="https://avatars.githubusercontent.com/seong-un" width="140px" /> <br>BE</td>
        <td height="140px" align="center">
            <img src="https://avatars.githubusercontent.com/Going9" width="140px" /> <br>BE</td>
        <td height="140px" align="center">
            <img src="https://avatars.githubusercontent.com/hjkasd" width="140px" /> <br>BE</td>
        <td height="140px" align="center">
            <img src="https://avatars.githubusercontent.com/pdanbi00" width="140px" /> <br>BE</td>        
    </tr>
    <tr>
      <td align="center" style="padding: 0px">
        React 기초 설정<br>Login<br>운영자 페이지, 응시자 홈<br>Face Detection<br>채팅 구현
      </td>
      <td align="center">
        인프라 구축<br>감독관 및 센터담당자 페이지, 응시자 시험 페이지
      </td>
      <td align="center">
        jwt를 이용한 Login<br>채팅 구현<br>멤버 기능 관리
      </td>
      <td align="center">
        운영자 기능 관리<br>센터 담당자 기능 관리<br>얼굴 인식 및 일치율 비교 기능
      </td>
      <td align="center">
        감독관 기능 관리<br>센터 담당자 기능 관리
      </td>
      <td align="center">
        공지사항 관리<br>대시보드 기능 관리
      </td>    
    </tr>
</table>

<br>
<br>
<br>

# 📜 LAAMS 개요

어학 평가 출결 관리 감독 시스템.<br>
종이문서로 응시자의 출결을 관리하던 기존의 시스템을 개선하기 위해 만들어진 서비스 입니다.<br>
Face-Detection을 이용해 기존의 번거롭고 관리가 힘들었던 출결관리를 자동으로 처리하여 어학평가 업무의 효율을 향상시킵니다.

<br>
<br>

# 📦 서비스 구조도

![Web App Reference Architecture (10)](https://github.com/Going9/LAAMS/assets/105552606/0df33900-cd8b-4b4a-a513-2eef2dc3482a)

<br>
<br>

# 🌊 erd

![LAAMS_ERD](https://github.com/Going9/LAAMS/assets/105552606/716bf142-b567-4236-9081-f6fed1c6203b)

<br>
<br>

# 🛠️ 주요 기능

### 운영자 
+ 대시보드로 시험 전반 관리

![MN 차트](https://github.com/Going9/LAAMS/assets/105552606/5ce86975-bf96-41a3-a9fe-3da1231f1851)

<br>
<br>

### 감독관
+ 출결관리 및 보상 요청

![DR상세](https://github.com/Going9/LAAMS/assets/105552606/4619be23-b89f-485f-93ba-4b49020d3056)

<br>
<br>

+ 부정행위(대리출석) 의심대상자 캡쳐
![부정행위 의심대상자](https://github.com/Going9/LAAMS/assets/105552606/5d2e88ae-a358-4f01-92b0-b9f08cc07f53)



<br>
<br>

+ 부정행위 의심대상자의 민증사진 등과 현장 캡쳐본 사진 비교 및 일치율 검토
![사진비교 전후](https://github.com/Going9/LAAMS/assets/105552606/9ed53e72-2423-461f-bc0e-ee38bfc34eaa)


<br>
<br>

### 센터 담당자 
+ 감독관 요청 처리
![CM_캘린더](https://github.com/Going9/LAAMS/assets/105552606/045a74a2-7fd6-41f0-b033-925a52dda0ea)

<br>
<br>

### 응시자
+ 시험 신청
![scrnli_2023- 11- 21- 오전 10-37-26](https://github.com/Going9/LAAMS/assets/105552606/4288e864-5baf-443a-a1fc-c6ccedc53d8d)

<br>
<br>

+ face-detection으로 출석
![응시자 얼굴 인식 출결](https://github.com/Going9/LAAMS/assets/105552606/4cfc01d2-ba2b-4788-8887-397ecb062ef4)

<br>
<br>

+ 왼쪽이나 오른쪽으로 고개 돌렸을 시 부정행위 판단
![응시자 왼쪽 부정행위](https://github.com/Going9/LAAMS/assets/105552606/8c258e51-1a63-4969-857b-f41f7b17c4e7)
![응시자 오른쪽 부정행위](https://github.com/Going9/LAAMS/assets/105552606/68b8880b-a6be-40f1-8029-637fec601eb4)

<br>
<br>


<br>
<br>

# 🔧 주요 기술

### 🖱️Stack

+ Spring boot
+ JPA
+ Spring Data JPA
+ QueryDSL
+ Swagger
+ Spring Security
+ JWT
+ Jasypt
+ OpenCV
+ STOMP
+ WebSocket
+ AWS S3
+ Postman

### 🖱️Frontend

+ React
+ SCSS
+ Styled-Components
+ Redux
+ stomp-websocket
+ sockjs-client
+ HTML Geolocation API
+ HTML Media Capture API

### 🖱️Infra

+ Docker
+ AWS EC2
+ Nginx
+ Jenkins

### 🖱️DB

+ MariaDB
+ MongoDB
+ Redis

### 🖱️협업툴

+ Git (Sourcetree, Git Bash, GitLab)
+ Notion
+ Jira
+ Figma
+ MatterMost
+ Canva
+ Webex
+ Swagger

<br>
<br>
