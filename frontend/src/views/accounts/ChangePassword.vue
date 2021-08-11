<template>
  <div id="bg" >

    <!-- 로고 -->
    <div class="upperBox">
      <img src="@/assets/icontact/children.png" class="logo" alt="children-image"/>
      <h1>비밀번호 변경</h1>
    </div>
    
    <!-- 회원 가입 폼 -->
    <div class="lowerBox">
      <div style="width: 300px">

        <!-- 새 비밀번호 -->
        <input
          class="formInput"
          type="password"
          v-model="password" 
          placeholder="새 비밀번호"
        >
        <div v-if="error.password" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.password}}</div>


        <!-- 비밀번호 확인 -->
        <input
          class="formInput"
          type="password"
          v-model="passwordConfirm" 
          placeholder="새 비밀번호 확인"
        >
        <div v-if="error.passwordConfirm" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.passwordConfirm}}</div>

        <!-- 비밀번호 변경 버튼 -->
        <v-btn 
          block 
          rounded
          style="background-color: #58679A; color: white; margin: 3px 0px;"
          :disabled="!password || error.password || error.passwordConfirm || !(password==passwordConfirm)"
          @click="changePassword"
          >
          비밀번호 변경
        </v-btn>
        <button @click="$router.go(-1)"
          class="etcBtn"
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
* {
  font-family: 'NanumSquareRound';
    font-size: 0.95em;
}
#bg {
  background-color: #a8b1cf;
  width: 100%;
  height: 100%;
}
.upperBox{
  position: absolute;
  top: 28%;
  left: 50%;
  transform: translate(-50%, -50%); 
  text-align:center;
}
.lowerBox{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
h1 {
  font-weight: 900;
  letter-spacing: 2px;
  font-size:48px;
  color: #58679A;
}
.logo {
  width: 10rem;
  padding-right:10px;
  display:inline-block;
  vertical-align: middle;
}
button{
  letter-spacing: -1px;
  color: #404c74;
  font-weight: 600;
}
.formInput {
  background-color: rgba(256, 256, 256, 0.7);
  box-shadow: 1px 1px 1px 1px #58679a;
  border-radius: 70px;
  height: 36px;
  width: 300px;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
  text-align: left;
}
.formInput:hover {
  background-color: rgba(256, 256, 256, 1);
  transition: 0.3s;
}
.etcBtn{
  margin: 20px 3px 3px 3px;
  padding: 0px 7px 0px 3px;
  color: #404c74;
}
.etcBtn:hover {
  color: rgba(256, 256, 256, 1);
  transition: 0.3s;
}
</style>