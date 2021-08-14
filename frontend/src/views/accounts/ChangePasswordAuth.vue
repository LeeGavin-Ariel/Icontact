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

        <!-- 아이디 입력 -->
        <input
          class="formInput"
          type="text"
          v-model="userid"
          placeholder="아이디"
        >
    
        
         <!-- 휴대폰 번호 입력 -->
        <div class="placement">
          <input
            class="formInput"
            oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            maxlength="11"
            v-model="phoneNumber"
            placeholder="휴대전화"
          />

          <div style="width: 100px; margin: 3px 0px">
            <v-btn
              block
              rounded
              style="
                background-color: #58679a;
                color: white;
              "
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
            v-model="authNumber"
            placeholder="authNumber"
          />
          <div style="width: 100px; margin: 3px 0px">
            <v-btn
              block
              rounded
              style="
                background-color: #58679a;
                color: white;
              "
              v-if="!isAuth"
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
          :disabled="!userid || !isAuth"
          >
          비밀번호 재설정
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
          this.$fire({
            html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">인증번호 전송에 실패했습니다.</p>`,
            focusConfirm: false,
            type: 'error',
            confirmButtonColor: '#58679A',
          })
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
          this.$fire({
            html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">인증에 성공하였습니다.</p>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">다음 단계를 진행해주세요.</p>`,
            focusConfirm: false,
            confirmButtonColor: '#58679A',
            type: 'success'
          })
          if (res.status === 200) {
            this.isAuth = true
          }
        })
        .catch((err)=>{
          console.log(err)
          this.$fire({
            html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">인증번호 확인에 실패했습니다.</p>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">다시 시도해주세요.</p>`,
            focusConfirm: false,
            confirmButtonColor: '#58679A',
            type: 'error'
          })
        })
      },
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
.placement {
  display: flex;
  flex-direction: row;
  width: 100%;
}
</style>