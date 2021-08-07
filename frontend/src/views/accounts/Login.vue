<template>
  
  <div style="background-color: #A8B1CF">

    <!--Content before waves-->
    <div class="inner-header flex flex-column">
    
    
      <!-- 로고 -->
      <div>
        <br>
        <br>
        <img src="@/assets/flaticon/toys.png" class="logo" alt="">
        <h1>ICONTACT</h1>
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
          :disabled="!userId || !password || error.password || error.userid"
          >
            로그인
          </v-btn>    
        </div>
      </div>
      
      <hr>
      <div>
        <button @click="$router.push({ name: 'Signup' })">회원가입</button> |
        <button @click="$router.push({ name: 'ChangePasswordAuth' })">비밀번호 찾기</button>
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
      <p>ICONTACT | 2021 | Web for Kindergarden</p>
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
      }else {
        this.error.userid = false
      }
      if (!this.passwordSchema.validate(this.password) || this.password.length < 8){
        this.error.password = "비밀번호를 확인해주세요";
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
          alert('승인되지 않은 계정입니다. 관리자에게 문의하세요.')
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