<h1 align="center">Welcome to Icontact 👦🏻👧🏻</h1>
<p>
  <a href="document url" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
  <a href="license url" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

![image](https://user-images.githubusercontent.com/77473524/129682578-79a3e9c9-6494-48c0-b639-60dc1c936108.png)
> 유치원 선생님과 학부모 간에 서로 배려하면서 불편함을 최소화하여 소통할 수 있는 SNS 서비스

### ![icon](https://user-images.githubusercontent.com/77473524/129672190-aa207a5f-3d1f-48c6-b3c9-521fff5d4143.png) [Homepage](https://i5a304.p.ssafy.io)

## Table of Contents
  - [Project description](#Project-description)
  - [Tech Stack](#Tech-Stack)
  - [Feature description](#Feature-description)
  - [Built With](#Built-With)
  - [Author](#Author)
  - [License](#License)


## Project description
**프로젝트 기간 : 21.07.05 ~ 21.08.20**

  Icontact는 유치원 선생님과 학부모간 메신저 소통시 발생하는 개인 정보 노출 및 사생활 보호 문제점을 최소화하여 해결하기 위한 SNS 서비스이다.
  유치원에서 공지하는 정보들을 쉽게 학부모들에게 전달하기 쉽게 기능들이 구성되었으며 학부모들은 내장된 채팅 서비스를 통해 선생님과 개인적으로 소통할 수 있다. 또한 아이의 등하원 시에 버스의 현재 위치 정보 제공 기능을 통해 아이의 안전을 보호할 수 있다.

## Tech Stack
### Frontend
<img style="display: inline;" alt="HTML5" src ="https://img.shields.io/badge/html5-#E34F26.svg?&style=for-the-badge&logo=HTML5&logoColor=white"/><img style="display: inline;" alt="CSS3" src ="https://img.shields.io/badge/CSS3-#1572B6.svg?&style=for-the-badge&logo=css3&logoColor=white"/><img style="display: inline;" alt="javascript" src ="https://img.shields.io/badge/JavaScript-#F7DF1E.svg?&style=for-the-badge&logo=javascript&logoColor=white"/><img style="display: inline;" alt="npm" src ="https://img.shields.io/badge/npm-#CB3837.svg?&style=for-the-badge&logo=npm&logoColor=white"/><img style="display: inline;" alt="vue.js" src ="https://img.shields.io/badge/vue-#4FC08D.svg?&style=for-the-badge&logo=vue.js&logoColor=white"/><img style="display: inline;" alt="vuetify" src ="https://img.shields.io/badge/vuetify-#1867C0.svg?&style=for-the-badge&logo=vuetify&logoColor=white"/><img style="display: inline;" alt="bootstrap" src ="https://img.shields.io/badge/bootstrap-#7952B3.svg?&style=for-the-badge&logo=bootstrap&logoColor=white"/>
- HTML5, CSS3, Javascript
- npm
- vue.js
- vuetify, vuex
- sendbird
- geolocation
- 등등.. 
### Backend
<img style="display: inline;" alt="springboot" src ="https://img.shields.io/badge/spring boot-#6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/><img style="display: inline;" alt="gradle" src ="https://img.shields.io/badge/gradle-#02303A.svg?&style=for-the-badge&logo=gradle&logoColor=white"/><img style="display: inline;" alt="java" src ="https://img.shields.io/badge/java-#007396.svg?&style=for-the-badge&logo=java&logoColor=white"/><img style="display: inline;" alt="jwt" src ="https://img.shields.io/badge/jwt-#000000.svg?&style=for-the-badge&logo=JSON Web Tokens&logoColor=white"/><img style="display: inline;" alt="websocket" src ="https://img.shields.io/badge/websocket-#010101.svg?&style=for-the-badge&logo=socket.io&logoColor=white"/><img style="display: inline;" alt="twilio" src ="https://img.shields.io/badge/twilio-#F22F46.svg?&style=for-the-badge&logo=twilio&logoColor=white"/><img style="display: inline;" alt="mysql" src ="https://img.shields.io/badge/mysql-#4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white"/>

- WAS spring boot
- Gradle
- Java 8
- JWT
- websocket
- Twilio
- MySQL, mybatis, JPA
### Deploy
<img style="display: inline;" alt="aws" src ="https://img.shields.io/badge/aws-#232F3E.svg?&style=for-the-badge&logo=amazon aws&logoColor=white"/><img style="display: inline;" alt="s3" src ="https://img.shields.io/badge/s3-#569A31.svg?&style=for-the-badge&logo=amazon s3&logoColor=white"/><img style="display: inline;" alt="nginx" src ="https://img.shields.io/badge/nginx-#009639.svg?&style=for-the-badge&logo=nginx&logoColor=white"/><img style="display: inline;" alt="letsencrypt" src ="https://img.shields.io/badge/letsencrypt-#003A70.svg?&style=for-the-badge&logo=letsencrypt&logoColor=white"/>
- aws EC2
- aws S3
- Nginx
- Let's Encrypt
## Feature description

### 1. 알림장

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129673159-dcabec72-0e45-4a42-8ffe-1a19eed4f3a5.png" alt="img1">
</div>


    - 선생님 : 알림사항 작성 후 학부모에게 전송
    - 학부모 : 전달받은 알림장 검색 및 조회

### 2. 채팅

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129673209-c6c4977b-a9da-436d-bf23-cd295ad32a27.png" alt="img2">
<img src="https://user-images.githubusercontent.com/19733033/129673217-4bf01184-b6b1-41ff-b375-1bcc652893ab.png" alt="img2" width="49%">
<img src="https://user-images.githubusercontent.com/19733033/129673219-d06f0a2c-6d51-4a9b-9f6e-1972b83177f5.png" alt="img2" width="49%">
</div>


    - 선생님 : 학부모와 메신저를 통한 1:1 소통
    - 학부모 : 같은 반 선생님 및 학부모와 소통

### 3. 앨범

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129673822-150824cf-0720-4719-a493-adaebaefd34b.png" alt="img3">
<img src="https://user-images.githubusercontent.com/19733033/129673834-7bfe6cf7-c3f1-4967-b0b0-84245d0e5cdb.png" alt="img3">
</div>


    - 선생님 : 유치원 활동 사진들 업로드 및 관리
    - 학부모 : 앨범의 사진들 조회 및 앨범 다운로드

### 4. 출석

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129673922-b2d61c2f-46b7-4356-86da-1a8baf8899df.png" alt="img4">
<img src="https://user-images.githubusercontent.com/19733033/129673929-82be4d39-99a1-4789-9b34-b552ccd962aa.png" alt="img4">
</div>


    - 선생님 : 아이들의 일자별 출석현황 관리
    - 학부모 : 아이의 한달단위 날짜별 출석현황 조회

### 5. 공지사항

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129674071-022cc503-fd76-4115-9b67-ab7eb600291d.png" alt="img5">
<img src="https://user-images.githubusercontent.com/19733033/129674076-8917ab9e-d4d7-4426-a285-1932afbdb44c.png" alt="img5">
<img src="https://user-images.githubusercontent.com/19733033/129674077-5ff61698-3177-4877-b60d-54e88163e0e8.png" alt="img5">
</div>

    - 선생님 : 공지사항 작성 후 학부모에게 전송
    - 학부모 : 전달받은 공지사항 조회

### 6. 요청사항

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129674282-9b4b7aea-47b3-46dc-a641-8b997fadfc8f.png" alt="img6" width="49%">
<img src="https://user-images.githubusercontent.com/19733033/129674284-42c39f6e-f787-4f29-8d40-fd22ae1a856d.png" alt="img6" width="49%">
</div>

    - 선생님 : 전달받은 요청사항 조회
    - 학부모 : 투약, 귀가 등 요청사항 작성 후 전달

### 7. GPS

![bus](https://user-images.githubusercontent.com/19733033/129677526-64e12c29-9de6-44a3-bfa6-eaeae661667b.gif)

<div style="text-align:center">
<img src="https://user-images.githubusercontent.com/19733033/129677890-617c02d6-8751-424b-84b6-8d46d3009542.png" alt="img7" width="49%">
<img src="https://user-images.githubusercontent.com/19733033/129677895-c3eab68a-2f47-4ab2-b200-adf8c399e7c9.png" alt="img7" width="49%">
<img src="https://user-images.githubusercontent.com/19733033/129677898-26ed502c-0f46-4b90-a0ae-904640cb3550.png" alt="img7" width="49%">
<img src="https://user-images.githubusercontent.com/19733033/129677900-9658a66a-5ee4-4d26-bcef-c8378b17f305.png" alt="img7" width="49%">
</div>

    - 선생님 : 등하원 버스에 동승한 선생님의 현재 위치 실시간 전송
    - 학부모 : 전달받은 위치 조회 및 경로 표시

## Built With
### Frontend
 - **김현욱** - 로그인, 회원가입, 앨범, 알림장, 디자인
 - **류현선** - 출석, 요청사항, 마이페이지
### Backend
 - **이가빈** - 로그인, 공지사항, 요청사항, 앨범, 디자인
 - **이현건** - 회원가입, 핸드폰 인증, 알림장, 출석부, 앨범, GPS
 - **정원석** - 로그인, 채팅, 앨범, GPS
 - **조의상** - 마이페이지, 채팅, 공지사항

## Author

👤 **김현욱**

- **Team Leader**

* Github: [@hyeonuk27](https://github.com/hyeonuk27)

👤 **류현선**

* Github: [@hs-ryu](https://github.com/hs-ryu)

👤 **이가빈**

- Website: [@arielgv829](https://velog.io/@arielgv829)

* Github: [@Ariel-G-Lee](https://github.com/Ariel-G-Lee)

👤 **이현건**

* Github: [@HyeonGeon Lee](https://github.com/natonato)

👤 **정원석**

* Github: [@JWeonseok](https://github.com/JWeonseok)

👤 **조의상**

* Github: [@JoEuiSang](https://github.com/JoEuiSang)


## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

This project is [MIT](license url) licensed.

***
