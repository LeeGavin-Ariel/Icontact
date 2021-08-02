<template>
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

        <!-- 비밀번호 찾기 라우터 설정 해주기. -->
        <div class="btn" style="display:block;" @click="$router.push({ name: 'ChangePasswordAuth' })">비밀번호 찾기</div>
        <!-- 회원가입 화면으로 -->
        <div class="btn" style="display:block;" @click="$router.push({ name: 'Signup' })">회원가입</div>
      </div>

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
      }
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


</style>