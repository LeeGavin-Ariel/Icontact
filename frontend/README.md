# Frontend
- vscode

## Prerequisites
프로젝트의 `src/api/drf.js`에서 자신의 서버 url을 설정한다.
```javascript=
const URL = 'http://[your_server_url]/'
const WS = 'ws://[your_server_url]/'
```
`src/utils/awss3.js`에서 자신의 S3 설정값을 적용한다.

```javascript=
var albumBucketName = '[your_bucket_name]';
var bucketRegion = '[your_bucket_region]';
var IdentityPoolId = '[your_IdentityPoolId]';
```
`src/services/SendBird.js`에서 자신의 sendbird 설정값을 적용한다.

```javascript=
const sendBird = new SendBird({
  appId: '[your_sendbird_appkey]'
})
...
let url = '[your_sendbird_url]';
let headers = {
     'Api-Token':'[your_sendbird_api-token]',
}
```
gps 기능의 정상적인 실행을 위해서는 적합한 https를 적용해야 한다.

## Install

```
npm install
```
## Usage
```
npm run serve
```
## Project Structure

```
frontend
│  .gitignore
│  babel.config.js
│  package-lock.json
│  package.json
│  README.md
│  vue.config.js
│  yarn.lock
│  
├─public
│      favicon.ico
│      index.html
│      
└─src
    │  App.vue
    │  main.js
    │  
    ├─api						요청 API
    │      album.js
    │      attendance.js
    │      ...
    │      
    ├─assets					사용 asset
    │  │  1.jpeg
    │  │  children.png
    │  │  ...
    │  │  
    │  ├─flaticon
    │  │      alarm.png
    │  │      arrow.png
    │  │      ...
    │  │      
    │  └─scss
    │      │  index.scss
    │      │  
    │      └─partials
    │          ├─base
    │          │      _base.scss
    │          │      _buttons.scss
    │          └─     ...
    │                  
    ├─components					
    │  ├─Album						앨범컴포넌트
    │  │      AlbumDetail.vue
    │  │      AlbumList.vue
    │  │      
    │  ├─Attendance					출석컴포넌트
    │  │      ParentAttendance.vue
    │  │      TeacherAttendance.vue
    │  │      
    │  ├─chat						채팅컴포넌트
    │  │      Channel.vue
    │  │      ChannelList.vue
    │  │      ...
    │  │      
    │  ├─common						공통컴포넌트
    │  │      Navbar.vue
    │  │      Sidebar.vue
    │  │      
    │  ├─Notebook					알림장컴포넌트
    │  │      NotebookDetail.vue
    │  │      NotebookList.vue
    │  │      
    │  ├─notice						
    │  │  │  NoticeMain.vue
    │  │  │  
    │  │  ├─menuPage				식단컴포넌트
    │  │  │      MenuCreate.vue
    │  │  │      MenuDetail.vue
    │  │  │      ...
    │  │  │      
    │  │  ├─noticePage				공지컴포넌트
    │  │  │      NoticeCreate.vue
    │  │  │      NoticeDetail.vue
    │  │  │      ...
    │  │  │      
    │  │  └─schedulePage			일정컴포넌트
    │  │          ScheduleCreate.vue
    │  │          ScheduleDetail.vue
    │  │          ...
    │  │          
    │  └─Request					요청컴포넌트
    │          RequestDetail.vue
    │          RequestList.vue
    │          
    ├─plugins						플러그인
    │      vuetify.js
    │      
    ├─router						라우터
    │      index.js
    │      
    ├─services
    │      SendBird.js
    │      
    ├─store							Vuex
    │      index.js
    │      
    ├─utils							util API
    │      awss3.js
    │      jwt.js
    │      request.js
    │      
    └─views							
        ├─accounts					계정관리 페이지
        │      ChangePassword.vue
        │      ChangePasswordAuth.vue
        │      ...
        │      
        ├─errorpage					에러 페이지
        │      NotFound.vue
        │      ServerError.vue
        │      
        ├─funcs						기능 페이지
        │      Album.vue
        │      Attendance.vue
        │      ...
        │      
        ├─GPS						GPS 페이지
        │      Geolocation.vue
        │      
        ├─mainpage					메인 페이지
        │      MainPage.vue
        │      
        └─mypage					회원정보 페이지
                MyPage.vue
                MyPageMaster.vue

```

