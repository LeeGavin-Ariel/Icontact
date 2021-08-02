<template>
  <div>
    <h1>회원가입</h1>
    <!-- 아이디 입력 -->
    <div>
      <label for="userid">아이디: </label>
      <input type="text" 
      id="userid" 
      v-model="userid"
      placeholder="아이디를 입력하세요.">
      <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
      <div v-if="error.userid">{{error.userid}}</div>
    </div>

    <!-- 패스워드 입력 -->
    <div>
      <label for="password">비밀번호: </label>
      <input type="password" 
      id="password" 
      v-model="password" 
      placeholder="비밀번호를 입력하세요.">
      <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
      <div v-if="error.password">{{error.password}}</div>
    </div>

    <!-- 패스워드 확인 입력 -->
    <div>
      <label for="passwordConfirm">비밀번호확인: </label>
      <input type="password" 
      id="passwordConfirm" 
      v-model="passwordConfirm" 
      placeholder="비밀번호을 한 번 더 입력하세요.">
      <!-- 유효성 검사 통과 못하면 에러 메시지 출력 -->
      <div v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
    </div>

    <!-- 이름 입력 -->
    <div>
      <label for="username">이름: </label>
      <input type="text" 
      id="username" 
      v-model="username"
      placeholder="이름을 입력하세요.">
    </div>

    <!-- 휴대폰번호 입력 -->
    <div>
      <label for="phoneNumber">휴대폰번호 : </label>
      <input type="text"
      oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
      maxlength="11"
      id="phoneNumber" 
      v-model="phoneNumber"
      placeholder="휴대폰번호를 입력하세요">
    </div>
    <button @click="requestAuthNumber">인증번호 받기</button>
    <div v-if="isRequested">인증번호가 발송되었습니다.</div>

    <!-- 인증번호 입력 -->
    <div>
      <label for="authNumber">인증번호 : </label>
      <input type="text"
      id="authNumber"
      v-model="authNumber"
      placeholder="인증번호를 입력하세요">
    </div>
    <button v-if="!isAuth" @click="checkAuthNumber">인증</button>

    <!-- 선생님, 학부모 회원가입 페이지로 이동 -->
    <!-- 선생 학부모 모두 isAuth 인증 완료되어야만 이라는 조건 추가해야 함  -->
    <!-- <button 
    @click="setTeacherInfo({userid, password, username, phoneNumber})"
    :disabled="!userid || !password || !passwordConfirm || !username || !phoneNumber || error.userid || error.password || error.passwordConfirm || !isAuth"
    >선생님</button> -->
        <button 
    @click="setTeacherInfo({userid, password, username, phoneNumber})"
    >선생님</button>
    <!-- <button
    @click="setParentInfo({userid, password, username, phoneNumber})"
    :disabled="!userid || !password || !passwordConfirm || !username || !phoneNumber || error.userid || error.password || error.passwordConfirm || !isAuth"
    >학부모</button> -->
     <button
    @click="setParentInfo({userid, password, username, phoneNumber})"
    >학부모</button>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import PV from "password-validator";
import axios from 'axios'
import SERVER from '@/api/drf.js'
export default {
  name: "Signup",
  data: () => {
    return {
      userid: "",
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      username:"",
      phoneNumber:"",
      authNumber:"",
      error: {
        userid: false,
        password: false,
        passwordConfirm: false,
      },
      // 요청을 받았다.
      isRequested: 0,
      // 성공: 인증을 받았다(200) 인증에 실패했다(400) / 실패: 에러케이스(500)
      isAuth: false,
      
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
    userid: function() {
      this.checkForm();
    },
    password: function() {
      this.checkForm();
    },
    passwordConfirm: function() {
      this.checkForm();
    },
  },
  methods: {
    ...mapActions ([
      'setTeacherInfo',
      'setParentInfo',
    ]),
    checkForm() {
      if (this.userid != this.userid.toLowerCase() || this.userid.length < 8){
        this.error.userid = "아이디 형식이 아닙니다.";
      }else {
        this.error.userid = false
      }
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
     // 인증번호 발송 요청.
    requestAuthNumber() {
      axios({
        // URL 체크 필요
        url: SERVER.URL + SERVER.ROUTES.sms,
        method: 'post',
        data: {
          phoneNum: this.phoneNumber
          }
      })
      .then((res) => {
        // res 확인해서 분기처리
        console.log(res)
        if (res.status == 200) {
          console.log('왔다!')
          this.isRequested = true
        }
        alert('인증번호가 발송되었습니다')
      })
      .catch(()=>{
        alert('인증번호 전송에 실패했습니다. 다시 시도해주세요')
      })
    },
    // 인증번호 확인 요청
    checkAuthNumber() {
      axios({
        url: SERVER.URL + SERVER.ROUTES.sms + '?phoneNum=' + this.phoneNumber + '&code=' + this.authNumber,
        method: 'get'
      })
      .then((res) => {
        console.log(res)
        if (res.status === 200) {
            this.isAuth = true
          }
      })
      .catch(()=>{
        alert('인증에 실패했습니다.')
      })
    }
  }
}
</script>

<style scoped>

</style>