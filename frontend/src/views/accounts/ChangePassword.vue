<template>
  <div>
  <h1>비밀번호 변경</h1>
  <!-- 패스워드 입력 -->
    <div>
      <label for="password">비밀번호: </label>
      <input type="password" 
      id="password" 
      v-model="password" 
      placeholder="비밀번호를 입력하세요">
      <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
      <div v-if="error.password">{{error.password}}</div>
    </div>

    <!-- 패스워드 확인 입력 -->
    <div>
      <label for="passwordConfirm">비밀번호확인: </label>
      <input type="password" 
      id="passwordConfirm" 
      v-model="passwordConfirm" 
      placeholder="비밀번호 확인을 입력하세요">
      <!-- 유효성 검사 통과 못하면 에러 메시지 출력 -->
      <div v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
    </div>
    
    <button 
    :disabled="!password && error.password && error.passwordConfirm && !(password==passwordConfirm)"
    @click="changePassword">변경하기</button>
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

</style>