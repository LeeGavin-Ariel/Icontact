<template>
  <div>
    <h1>비밀번호 변경</h1>
    <div>
      <label for="userid">아이디: </label>
      <input type="text" 
      id="userid" 
      v-model="userid"
      placeholder="아이디를 입력하세요">
      <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
      <div v-if="error.userid">{{error.userid}}</div>
    </div>

    <div>
      <label for="phoneNumber">휴대폰번호 : </label>
      <input type="text"
      oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
      maxlength="11"
      id="phoneNumber" 
      v-model="phoneNumber"
      placeholder="휴대폰번호를 입력하세요">
    </div>
    <div v-if="error.userid">{{ error.phoneNumber }}</div>
    <button @click="requestAuthNumber(phoneNumber)">인증번호 받기</button>
    <div v-if="isRequested">인증번호가 발송되었습니다.</div>

    <!-- 인증번호 입력 -->
    <div>
      <label for="authNumber">인증번호 : </label>
      <input type="text"
      id="authNumber"
      v-model="authNumber"
      placeholder="인증번호를 입력하세요">
    </div>
    
    <!--isAuth 인증 완료되어야만(상태코드로 업데이트 필요함..) 이라는 조건 추가해야 함  -->
    <button 
    :disabled="isAuth" 
    @click="checkAuthNumber(phoneNumber, authNumber)">
    인증</button>
    <div v-if="isAuth">인증이 완료되었습니다</div>
    <button
    @click="GoToChangePassword"
    :disabled="!userid || !isAuth"
    >비밀번호 변경</button>
  </div>
</template>


<script>
import { mapActions } from 'vuex'
import axios from 'axios'
import SERVER from '@/api/drf.js'
export default {
  name: 'ChangePassword',
  data: function () {
    return {
      userid: '',
      isRequested: false,
      isAuth:false,
      phoneNumber: '',
      authNumber: '',
      error:{
        userid : false,
        phoneNumber: false
      }
    }
  },
  watch: {
      userid: function() {
        this.checkForm();
      },
      phoneNumber: function() {
        this.checkForm();
      }
    },
  methods: {
    ...mapActions ([
      'setSendUserIdForChangePW',
    ]),
    checkForm() {
        if (this.userid != this.userid.toLowerCase() || this.userid.length < 8){
          this.error.userid = "아이디 형식이 아닙니다.";
        }else {
          this.error.userid = false
        }
        if (this.phoneNumber.length < 2) {
          this.error.phoneNumber = "휴대폰 번호를 입력해주세요"
        }
      },
      // 인증번호 발송 요청.
      requestAuthNumber() {
        axios({
          url: SERVER.URL + SERVER.ROUTES.sms,
          method: 'post',
          data: {
            phoneNum: this.phoneNumber
            }
        })
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            this.isRequested = true
          }
        })
        .catch(()=>{
          alert('인증번호 전송에 실패했습니다.')
        })
      },
      
      // 인증번호 확인 요청
      checkAuthNumber() {
        axios({
          url: SERVER.URL + SERVER.ROUTES.sms + '?phoneNum=' + this.phoneNumber + '&code=' + this.authNumber,
          method: 'get'
        })
        .then((res) => {
          if (res.stauts === 200) {
            this.isAuth = true
          }
        })
        .catch((err)=>{
          console.log(err)
          alert('인증번호 확인에 실패했습니다. 다시 시도해주세요')
        })
      },
      GoToChangePassword() {
        this.$store.actions.setSendUserIdForChangePW(this.userid)
        this.$router.push({ name: 'ChangePassword' })
      }
    }
}
</script>

<style scope>

</style>