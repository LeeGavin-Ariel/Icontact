<template>
  <div id="bg">
    <div class="inner-header flex flex-column">
      <!-- 로고 -->
      <div>
        <br>
        <br>
        <img src="@/assets/flaticon/toys.png" class="logo" alt="">
        <h1>비밀번호 변경</h1>
        <br>
      </div>
    
    
      <div style="width: 300px;">

        <!-- 새 비밀번호 입력 -->
        <input
          class="formInput"
          type="password"
          color="#58679A"
          v-model="password" 
          placeholder="새 비밀번호"
          style="text-align:left;"
        >
        <div v-if="error.password" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.password}}</div>


        <input
          class="formInput"
          type="password"
          color="#58679A"
          v-model="passwordConfirm" 
          placeholder="새 비밀번호 확인"
          style="text-align:left;"
        >
        <div v-if="error.passwordConfirm" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.passwordConfirm}}</div>


        <v-btn 
          block 
          rounded
          style="background-color: #58679A; color: white; margin: 3px 0px;"
          :disabled="!password || error.password || error.passwordConfirm || !(password==passwordConfirm)"
          @click="changePassword"
          >
          변경하기
        </v-btn>
        <button @click="$router.go(-1)"
          class="backBtn"
        >
          뒤로 가기
        </button>        
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import axios from 'axios'
import SERVER from '@/api/drf.js'

export default {
  name: 'ChangePassword',
  data: () => {
    return {
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      error: {
        userid: false,
        password: false,
        passwordConfirm: false,
      },
    };
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
    password: function() {
      this.checkForm();
    },
    passwordConfirm: function() {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (!this.passwordSchema.validate(this.password) || this.password.length < 8){
        this.error.password = "비밀 번호 형식이 아닙니다.";
      }else {
        this.error.password = false
      }
      if (this.password != this.passwordConfirm){
        this.error.passwordConfirm = "비밀 번호가 틀립니다.";
      }else {
        this.error.passwordConfirm = false
      }
    },
    changePassword() {
      axios({
        // URL 체크 필요
        url: SERVER.URL + SERVER.ROUTES.changepw,
        method: 'post',
        data: {
          userId: this.$store.state.sendUserIdForChangePW,
          password: this.password
          }
      })
      .then((res) => {
        if (res.status == 200) {
          alert('비밀번호 변경이 완료되었습니다.')
          this.$router.push({ name: 'Login' })
        }
      })
      .catch((err)=>{
        alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요') 
        console.log(err)
      })
    }
  }
}  
</script>

<style scoped>
@font-face {
  font-family: "TmoneyRoundWindExtraBold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
button, h1 {
  font-family: "TmoneyRoundWindExtraBold";
}
#bg {
  background-color: #a8b1cf;
  width: 100%;
  height: 100%;
}
h1 {
  font-weight: 900;
  letter-spacing: 2px;
  font-size:48px;
  color: #58679A;
}
.logo {
  width:60px;
  fill:white;
  padding-right:10px;
  display:inline-block;
  vertical-align: middle;
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
.backBtn{
  border-radius: 4%;
  width: 150px;
  margin: 15px 3px 3px 3px;
  padding: 0px 7px 0px 3px;
  font-size: small;
  letter-spacing: -2px;
  color: #404c74;
}
</style>