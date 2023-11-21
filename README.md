# LAAMS
![Logo.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/cfd5c3ed-1aae-4335-8bbe-0496d4b8c40c/Logo.png)
# 🔥 프로젝트 개요

프로젝트 기간: 2023.10.09 ~ 2023.11.17 <br> 

Language Assessment Attendance Management System <br>
### 어학 및 자격 평가 출결 관리 시스템 LAAMS 

<br><br>

# 🙂 개발 멤버 소개

<table>
    <tr>
      <td align="center">
        <h5>한승현</h5>
      </td>
      <td align="center">
        <h5>정예원</h5>
      </td>
      <td align="center">
        <h5>장성운</h5>
      </td>
      <td align="center">
        <h5>공익규</h5>
      </td>
      <td align="center">
        <h5>김현지</h5>
      </td>
      <td align="center">
        <h5>박단비</h5>
      </td>
    </tr>
    <tr>
        <td height="140px" align="center"> <a href="https://github.com/SeungHyunH">
            <img src="https://avatars.githubusercontent.com/SeungHyunH" width="140px" /> <br>Team Leader, FE</a></td>
        <td height="140px" align="center"> <a href="https://github.com/yewon830">
            <img src="https://avatars.githubusercontent.com/yewon830" width="140px" /> <br>FE</a></td>
        <td height="140px" align="center"> <a href="https://github.com/seong-un">
            <img src="https://avatars.githubusercontent.com/seong-un" width="140px" /> <br>BE</a></td>
        <td height="140px" align="center"> <a href="https://github.com/Going9">
            <img src="https://avatars.githubusercontent.com/Going9" width="140px" /> <br>BE</a></td>
        <td height="140px" align="center"> <a href="https://github.com/hjkasd">
            <img src="https://avatars.githubusercontent.com/hjkasd" width="140px" /> <br>BE</a></td>
        <td height="140px" align="center"> <a href="https://github.com/pdanbi00">
            <img src="https://avatars.githubusercontent.com/pdanbi00" width="140px" /> <br>BE</a></td>        
    </tr>
    <tr>
      <td align="center" style="padding: 0px">
        React 기초 설정<br>Login<br>운영자 및 감독관 페이지<br>Face Detection<br>채팅 구현
      </td>
      <td align="center">
        인프라 구축<br>모든 페이지 반응형 적용
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

![Web App Reference Architecture (10).png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/af62b45b-e017-4807-a903-e83eb1edd07f/Web_App_Reference_Architecture_(10).png)

<br>
<br>

# 🌊 erd

![LAAMS_ERD.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/5b69e323-e73d-412a-abf6-8cfc3aef3564/LAAMS_ERD.png)

<br>
<br>

# 🛠️ 주요 기능

### 운영자 
+ 시험 생성
+ 대시보드
+ ![MN 차트.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/5db42ada-0e65-4574-a8cd-228872a81451/MN_%EC%B0%A8%ED%8A%B8.png)

### 감독관
+ 출결관리 및 보상 요청
+ 에러리포트
+ 부정행위 감독
+ ![DR상세.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/cc89dfec-0505-4ffc-92b2-f69a7a74f883/DR%EC%83%81%EC%84%B8.png)

### 센터 담당자 
+ 감독관 요청 처리
+ ![CM_캘린더.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/5c8bdffa-bcca-47a5-9ae3-b54fa7c2662c/CM_%EC%BA%98%EB%A6%B0%EB%8D%94.png)

### 응시자
+ 시험 신청 및 face-detection으로 출석
+ ![scrnli_2023- 11- 21- 오전 10-37-26.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/ff2c16f8-90c0-45c4-a8bb-51a77b465f91/6f144faf-c62f-4ff0-81a7-408c9d05657a/scrnli_2023-_11-_21-_%EC%98%A4%EC%A0%84_10-37-26.png)


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
