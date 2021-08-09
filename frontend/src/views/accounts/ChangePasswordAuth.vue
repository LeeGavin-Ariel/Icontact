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
    
    
      <div style="width: 300px;" >

        <!-- 아이디 입력 -->
        <input
          class="formInput"
          type="text"
          color="#58679A"
          v-model="userid"
          placeholder="아이디"
          style="text-align:left;"
        >
    
        
        <!-- 휴대폰 번호 입력 -->
        <div class="placement">
          <input
            class="formInput"
            type="text"
            oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            maxlength="11"
            color="#58679A"
            v-model="phoneNumber"
            placeholder="비밀번호"
            style="text-align:left; width:62%"
          >
          
          
          <div style="width: 30%; margin: 3px 0px;">
            <v-btn 
              block 
              rounded
              style="background-color: #58679A; color: white; letter-spacing: -0.8px;"
              @click="requestAuthNumber"
              >
              인증번호 받기
            </v-btn>
          </div>
        </div>

        

        <!-- 인증 번호 입력 -->
        <div class="placement">
          <input
            class="formInput"
            oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            maxlength="4"
            color="#58679A"
            v-model="authNumber"
            placeholder="인증 번호"
            style="text-align:left; width:62%"
          >
          <div style="width: 30%; margin: 3px 0px;">
            <v-btn 
              block 
              rounded 
              style="background-color: #58679A; color: white; letter-spacing: -0.8px;"
              :disabled="!isAuth" 
              @click="checkAuthNumber"
              >
              인증번호 확인
            </v-btn>
          </div>
        </div>
        <v-btn 
          block 
          rounded
          style="background-color: #58679A; color: white; margin: 3px 0px;"
          @click="setSendUserIdForChangePW(userid)"
          >
            <!-- :disabled="!userid || !isAuth" -->
          비밀번호 재설정
        </v-btn>

      </div>
    </div>
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
          console.log(res)
          alert('인증에 성공하였습니다. 다음 단계를 진행해주세요')
          if (res.status === 200) {
            this.isAuth = true
          }
        })
        .catch((err)=>{
          console.log(err)
          alert('인증번호 확인에 실패했습니다. 다시 시도해주세요')
        })
      },
    }
}
</script>

<style scoped>
</style>