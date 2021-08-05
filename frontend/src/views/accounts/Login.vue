<template>
  
  <div style="background-color: #A8B1CF">

    <!--Content before waves-->
    <div class="inner-header flex flex-column">
    
    
      <!-- 로고 -->
      <div>
        <br>
        <br>
        <img src="@/assets/flaticon/toys.png" class="logo" alt="">
        <path fill="#FFFFFF" stroke="#000000" stroke-width="10" stroke-miterlimit="10" d="M57,283" />
        <g><path fill="#fff"
        d="M250.4,0.8C112.7,0.8,1,112.4,1,250.2c0,137.7,111.7,249.4,249.4,249.4c137.7,0,249.4-111.7,249.4-249.4
        C499.8,112.4,388.1,0.8,250.4,0.8z M383.8,326.3c-62,0-101.4-14.1-117.6-46.3c-17.1-34.1-2.3-75.4,13.2-104.1
        c-22.4,3-38.4,9.2-47.8,18.3c-11.2,10.9-13.6,26.7-16.3,45c-3.1,20.8-6.6,44.4-25.3,62.4c-19.8,19.1-51.6,26.9-100.2,24.6l1.8-39.7    c35.9,1.6,59.7-2.9,70.8-13.6c8.9-8.6,11.1-22.9,13.5-39.6c6.3-42,14.8-99.4,141.4-99.4h41L333,166c-12.6,16-45.4,68.2-31.2,96.2  c9.2,18.3,41.5,25.6,91.2,24.2l1.1,39.8C390.5,326.2,387.1,326.3,383.8,326.3z" />
        </g>
        <h1>ICONTACT</h1>
        <br>
      </div>
      
      

      <!-- 로그인 폼 -->
      <div style="width: 200px;">
        <div class="d-flex justify-content-start">
          <vs-input
            color="#58679A"
            v-model="userId"
            placeholder="ID"
            style="text-align:left;"
          >
            <template #message-danger v-if="error.userid" style="margin: 0;">
              {{error.userid}}
            </template>
          </vs-input>
        </div>

        <vs-input
          type="password"
          color="#58679A"
          v-model="password"
          placeholder="Password"
          style="text-align:left;"
          @keypress.enter="login"
        >
        <template #message-danger v-if="error.password" style="margin: 0;">
          {{error.password}}
        </template>
        </vs-input>
        <br>         
        <v-btn 
        block 
        rounded 
        style="background-color: #58679A; color: white;"
        @click="login"
        :disabled="!userId || !password || error.password || error.userid"
        >
          로그인
        </v-btn>    
      </div>
      
      <hr>
      <div>
        <button @click="$router.push({ name: 'Signup' })">회원가입</button> |
        <button @click="$router.push({ name: 'ChangePasswordAuth' })">비밀번호 찾기</button>
      </div>
    </div>

    <!--Waves Container-->
    <div>
      <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
      viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
        <defs>
          <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
        </defs>
        <g class="parallax">
          <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7)" />
          <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
          <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
          <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
        </g>
      </svg>
    </div>
    <!--Waves end-->



    <!--Content starts-->
    <div class="content flex">
      <p>Team of ICONTACT | 2021 | Web for Kindergarden</p>
    </div>
    <!--Content ends-->

  </div>
  
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  <!-- 
  <div class="main_container">
    <div class="main_left_btn">
    </div>

    <div class="main_right_btn registration-form">
    
      <div style="margin:1.5rem;">
        <div class="form-group">
            <input type="text"
            class="form-control item"
            id="userId" 
            v-model="userId"
            placeholder="ID">
            <div v-if="error.userid" style="color:red;">{{error.userid}}</div>
        </div>
        <div class="form-group">
            <input type="password"
            class="form-control item"
            id="password" 
            v-model="password" 
            placeholder="비밀번호를 입력하세요"
            @keypress.enter="login">
            <div v-if="error.password" style="color:red;">{{error.password}}</div>
        </div>

        <button 
        @click="login" 
        :disabled="!userId || !password || error.password || error.userid"
        class="form-control item"
        style="background-color:rgb(250, 215, 73);"
        >로그인</button>

        <div class="btn" style="display:block;" @click="$router.push({ name: 'ChangePasswordAuth' })">비밀번호 찾기</div>
        <div class="btn" style="display:block;" @click="$router.push({ name: 'Signup' })">회원가입</div>
      </div>

    </div>
  </div> -->
  
</template>

<script>
import PV from "password-validator";
import authApi from '@/api/auth.js';

export default {
  name: 'Login',
  data: function () {
    return {
      userId: '',
      password: '',
      passwordSchema: new PV(),
      error: {
        userid: false,
        password: false,
      },
      value1: '',
      value2: ''      
    }
  },
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },

  watch: {
    userId: function() {
      this.checkForm();
    },
    password: function() {
      this.checkForm();
    },

  },
  methods: {
    checkForm() {
      // Ryuhyunsun === ryuhyunsun
      if (this.userId != this.userId.toLowerCase() || this.userId.length < 8){
        this.error.userid = "아이디 형식이 아닙니다.";
      }else {
        this.error.userid = false
      }
      if (!this.passwordSchema.validate(this.password) || this.password.length < 8){
        this.error.password = "비밀번호 형식이 아닙니다.";
      }else {
        this.error.password = false
      }
    },
    // 로그인
    async login() {
      const credentials = {userId: this.userId, password: this.password}
      // console.log(credentials)
      
      try {
        const result = await authApi.login(credentials);
        console.log('ok')
        if (result.accept === 2) {
          await this.$store.dispatch('setUser', {
            userId: result.userId,
            userName: result.userName,
            type: result.type,
            kidId: result.kidId,
            kidName: result.kidName,
            accept: result.accept,
            userTel: result.userTel,
            stateCode: result.stateCode,
            profileImg: result.profileImg,
            kinderCode: result.kinderCode,
            kinderName: result.kinderName,
            classCode: result.classCode,
            className: result.className,
          });

          this.$router.push('/');
        } else {
          alert('승인되지 않은 계정입니다. 관리자에게 문의하세요')
        }
      }catch (err){
        if(err.response){
          alert("아이디와 비밀번호를 확인하세요.");
        }else{
          alert('잘못된 접근입니다.');
        }
      }
    },
  },
}
</script>

<style scoped>

body{
  background-color: #dee9ff;
}

.registration-form{
padding: 50px 0;
}

.registration-form form{
  background-color: #fff;
  max-width: 600px;
  margin: auto;
  padding: 50px 70px;
  border-top-left-radius: 30px;
  border-top-right-radius: 30px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.075);
}

.registration-form .form-icon{
text-align: center;
  background-color: #FFF176;
  border-radius: 50%;
  font-size: 40px;
  color: white;
  width: 100px;
  height: 100px;
  margin: auto;
  margin-bottom: 50px;
  line-height: 100px;
}

.registration-form .item{
border-radius: 20px;
  margin-bottom: 25px;
  padding: 10px 20px;
}

.registration-form .create-account{
  border-radius: 30px;
  padding: 10px 20px;
  font-size: 18px;
  font-weight: bold;
  background-color: #FFF176;
  border: none;
  color: white;
  margin-top: 20px;
}

@media (max-width: 576px) {
  .registration-form form{
      padding: 50px 20px;
  }

  .registration-form .form-icon{
      width: 70px;
      height: 70px;
      font-size: 30px;
      line-height: 70px;
  }
}
.main_container {
width:100%;
height:100%;
}
.main_left_btn {
width:60%;
height:100%;
background-color:rgb(250, 215, 73);
float:left;
}
.main_right_btn {
width:40%;
height:100%;
float:right;
}







body {
  margin:0;
  width: 60vw;
}

h1 {
  font-family:'Noto Sans KR', sans-serif;
  font-weight: 900;
  letter-spacing: 2px;
  font-size:48px;
  color: #58679A;
  text-shadow:2px 2px 2px rgba(255, 255, 255, 0.7);
}
p {
  font-family:'Noto Sans KR', sans-serif;
  font-weight: 100;
  letter-spacing: 1px;
  font-size:14px;
  color: #58679A;
  

}

.logo {
  width:60px;
  fill:white;
  padding-right:10px;
  display:inline-block;
  vertical-align: middle;
}

.inner-header {
  height:85%;
  width:100%;
  margin: 0;
  padding: 0;
}

.flex { /*Flexbox for containers*/
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.waves {
  position:relative;
  width: 100%;
  height:10vh;
  margin-bottom:0; /*Fix for safari gap*/
  min-height:100px;
  max-height:150px;
}

.content {
  position:relative;
  height:20vh;
  text-align:center;
  background-color: white;
}

/* Animation */

.parallax > use {
  animation: move-forever 25s cubic-bezier(.55,.5,.45,.5)     infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
  animation-duration: 7s;
}
.parallax > use:nth-child(2) {
  animation-delay: -3s;
  animation-duration: 10s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 13s;
}
.parallax > use:nth-child(4) {
  animation-delay: -5s;
  animation-duration: 20s;
}
@keyframes move-forever {
  0% {
   transform: translate3d(-90px,0,0);
  }
  100% { 
    transform: translate3d(85px,0,0);
  }
}
/*Shrinking for mobile*/
@media (max-width: 768px) {
  .waves {
    height:40px;
    min-height:40px;
  }
  .content {
    height:30vh;
  }
  h1 {
    font-size:24px;
  }
}

</style>