<template>
  
  <div id="bg">

    <!--Content before waves-->
    <div class="inner-header flex flex-column">
    
    
      <!-- 로고 -->
      <div>
        <br>
        <br>
        <img src="@/assets/icontact/light_log.png" style="width:20rem; margin:1.5rem;" alt="logo-image">
        <br>
      </div>
      
      

      <!-- 로그인 폼 -->
      <div style="width: 300px;">
        <input
          class="formInput"
          type="text"
          color="#58679A"
          v-model="userId"
          placeholder="아이디"
          style="text-align:left; margin:3px 0;"
        >
        <div v-if="error.userid" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.userid}}</div>

        <input
          class="formInput"
          type="password"
          color="#58679A"
          v-model="password"
          placeholder="비밀번호"
          style="text-align:left; margin:3px 0;"
          @keypress.enter="login"
        >
        <div v-if="error.password" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.password}}</div>



        
        <div style="width:304px; margin-top:3px;">
          <v-btn 
          block 
          rounded 
          style="background-color: #58679A; color: white;"
          @click="login"
          :disabled="!userId || !password || error.password !== false || error.userid !== false"
          >
            로그인
          </v-btn>    
        </div>
      </div>
      
      <hr>
      <div>
        <button class="etcBtn" @click="$router.push({ name: 'Signup' })">회원가입</button> |
        <button class="etcBtn" @click="$router.push({ name: 'ChangePasswordAuth' })">비밀번호 찾기</button>
      </div>
    </div>


    <!--파도-->
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


    <!--하단 내용-->
    <div class="content flex">
      <p class="m-0">ICONTACT | 2021 | Web for Kindergarden</p>
    </div>

  </div>
  

  
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
        this.error.userid = "아이디를 확인해주세요";
      } else {
        this.error.userid = false;
      }
      if (!this.passwordSchema.validate(this.password) || this.password.length < 8){
        this.error.password = "비밀번호를 확인해주세요";
      } else {
        this.error.password = false;
      }
    },
    // 로그인
    async login() {
      const credentials = {userId: this.userId, password: this.password}
      // console.log(credentials)
      
      try {
        const result = await authApi.login(credentials);
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
          this.$fire({
            html: `<a href="javascript:void(0);"></a>
            <p style="font-size: 30px; font-family: 'NanumSquareRound';">승인되지 않은 계정입니다.</p>
            <p style="font-size: 30px; font-family: 'NanumSquareRound';">관리자에게 문의하세요.</p>`,
            type: 'error',
            focusConfirm: false
          })
        }
      }catch (err){
        console.log(err)
        this.$fire({
          html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';">아이디와 비밀번호를 확인하세요.</p>`,
          type: 'error',
          focusConfirm: false
        })
      }
    },
  },
}
</script>

<style scoped>
* {
  font-family: 'NanumSquareRound';
  font-size: 0.95em;
}
#bg {
  background-color: #a8b1cf;
}
h1 {
  font-weight: 900;
  letter-spacing: 2px;
  font-size:48px;
  color: #58679A;
}
button{
  letter-spacing: -1px;
  color: #404c74;
  font-weight: 600;
}
p {
  font-weight: 100;
  letter-spacing: 1px;
  font-size:14px;
  color: #58679A;
}
.formInput {
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 1px 1px 1px 1px #58679a;
  border-radius: 70px;
  height: 36px;
  width: 300px;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.formInput:hover {
  background-color: rgba(256, 256, 256, 1);
  transition: 0.3s;
}
.etcBtn{
  margin: 3px 3px 3px 3px;
  padding: 0px 7px 0px 3px;
  color: #404c74;
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
.inner-header {
  height:83vh;
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