<template >
  <div id="bg">
    <div class="inner-header flex flex-column">
      <!-- 로고 -->
      <div>
        <br />
        <br />
        <img src="@/assets/flaticon/toys.png" class="logo" alt="" />
        <h1>회원가입</h1>
        <br />
      </div>

      <!-- 회원가입 폼 -->
      <div style="width: 300px">
        <!-- 아이디 입력 -->
        <input
          class="formInput"
          type="text"
          color="#58679A"
          v-model="userid"
          placeholder="아이디"
          style="text-align: left"
        />
        <div
          v-if="error.userid"
          style="
            text-align: left;
            color: red;
            font-size: 12px;
            margin-left: 19px;
          "
        >
          {{ error.userid }}
        </div>

        <!-- 비밀번호 입력 -->
        <input
          class="formInput"
          type="password"
          color="#58679A"
          v-model="password"
          placeholder="비밀번호"
          style="text-align: left"
        />
        <div
          v-if="error.password"
          style="
            text-align: left;
            color: red;
            font-size: 12px;
            margin-left: 19px;
          "
        >
          {{ error.password }}
        </div>

        <!-- 비밀번호 확인 -->
        <input
          class="formInput"
          type="password"
          color="#58679A"
          v-model="passwordConfirm"
          placeholder="비밀번호 확인"
          style="text-align: left"
        />
        <div
          v-if="error.passwordConfirm"
          style="
            text-align: left;
            color: red;
            font-size: 12px;
            margin-left: 19px;
          "
        >
          {{ error.passwordConfirm }}
        </div>

        <!-- 이름 입력 -->
        <input
          class="formInput"
          color="#58679A"
          v-model="username"
          placeholder="이름"
          style="text-align: left"
        />

        <!-- 휴대폰 번호 입력 -->
        <div class="placement">
          <input
            class="formInput"
            oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            maxlength="11"
            color="#58679A"
            v-model="phoneNumber"
            placeholder="휴대전화"
            style="text-align: left; width: 200px"
          />

          <div style="width: 100px; margin: 3px 0px">
            <v-btn
              block
              rounded
              style="
                background-color: #58679a;
                color: white;
                letter-spacing: -0.8px;
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
            color="#58679A"
            v-model="authNumber"
            placeholder="authNumber"
            style="text-align: left; width: 200px"
          />
          <div style="width: 100px; margin: 3px 0px">
            <v-btn
              block
              rounded
              style="
                background-color: #58679a;
                color: white;
                letter-spacing: -0.8px;
              "
              v-if="!isAuth"
              @click="checkAuthNumber"
            >
              인증번호 확인
            </v-btn>
          </div>
        </div>

        <!-- 선생 학부모 모두 isAuth 인증 완료되어야만 이라는 조건 추가해야 함  -->
        <!-- :disabled="!userid || !password || !passwordConfirm || !username || !phoneNumber || error.userid || error.password || error.passwordConfirm || !isAuth"  -->
      
      </div>

      <!-- 선생님/학부모 유형 선택 버튼 -->
      <div style="d-flex flex-column">
        <button
          class="signupTypeBtn"
          @click="setTeacherInfo({ userid, password, username, phoneNumber })"
          :disabled="!userid || !password || !passwordConfirm || !username || !phoneNumber || error.userid || error.password || error.passwordConfirm || !isAuth"
        >
          <img
            src="@/assets/flaticon/teacher.png"
            class="logo"
            alt="signup-as-a-teacher"
          />선생님으로 가입
        </button>
        
        <button
          class="signupTypeBtn"
          @click="setParentInfo({ userid, password, username, phoneNumber })"
          :disabled="!userid || !password || !passwordConfirm || !username || !phoneNumber || error.userid || error.password || error.passwordConfirm || !isAuth"        
        >
          <img
            src="@/assets/flaticon/parents.png"
            class="logo"
            alt="signup-as-parents"
          />학부모로 가입
        </button>
      </div>
      <!-- 뒤로가기 버튼 -->
      <button @click="$router.go(-1)"
        class="backBtn"
      >
        뒤로 가기
      </button>

    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import PV from "password-validator";
import axios from "axios";
import SERVER from "@/api/drf.js";
export default {
  name: "Signup",
  data: () => {
    return {
      userid: "",
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      username: "",
      phoneNumber: "",
      authNumber: "",
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
    userid: function () {
      this.checkForm();
    },
    password: function () {
      this.checkForm();
    },
    passwordConfirm: function () {
      this.checkForm();
    },
  },
  methods: {
    ...mapActions(["setTeacherInfo", "setParentInfo"]),
    checkForm() {
      if (this.userid != this.userid.toLowerCase() || this.userid.length < 8) {
        this.error.userid = "8자 이상의 영문 소문자 또는 숫자";
      } else {
        this.error.userid = false;
      }
      if (
        !this.passwordSchema.validate(this.password) ||
        this.password.length < 8
      ) {
        this.error.password = "8자 이상의 영문 소문자와 숫자 조합";
      } else {
        this.error.password = false;
      }
      if (this.password != this.passwordConfirm) {
        this.error.passwordConfirm = "비밀 번호가 일치하지 않습니다";
      } else {
        this.error.passwordConfirm = false;
      }
    },
    // 인증번호 발송 요청.
    requestAuthNumber() {
      axios({
        // URL 체크 필요
        url: SERVER.URL + SERVER.ROUTES.sms,
        method: "post",
        data: {
          phoneNum: this.phoneNumber,
        },
      })
        .then((res) => {
          // res 확인해서 분기처리
          console.log(res);
          if (res.status == 200) {
            console.log("왔다!");
            this.isRequested = true;
          }
          alert("인증번호가 발송되었습니다");
        })
        .catch(() => {
          alert("인증번호 전송에 실패했습니다. 다시 시도해주세요");
        });
    },
    // 인증번호 확인 요청
    checkAuthNumber() {
      axios({
        url:
          SERVER.URL +
          SERVER.ROUTES.sms +
          "?phoneNum=" +
          this.phoneNumber +
          "&code=" +
          this.authNumber,
        method: "get",
      })
        .then((res) => {
          console.log(res);
          if (res.status === 200) {
            this.isAuth = true;
          }
        })
        .catch(() => {
          alert("인증에 실패했습니다.");
        });
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: "TmoneyRoundWindExtraBold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
button{
  font-size: 0.8em;
  font-family: "TmoneyRoundWindExtraBold";    
}
#bg {
  background-color: #a8b1cf;
  width: 100%;
  height: 100vw;
}
h1 {
  font-family: "TmoneyRoundWindExtraBold";  
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
  letter-spacing: -1px;
  color: #404c74;
}
.signupTypeBtn {
  border: 2px #58679A;
  border-style: solid;
  border-radius: 4%;
  height: 70px; 
  width: 150px; 
  margin: 30px 3px 3px 3px;
  padding: 0px 7px 0px 3px;  
  letter-spacing: -2px;
  font-family: "TmoneyRoundWindExtraBold";
  color: #404c74;

}
.signupTypeBtn:hover {
  transition: 0.3s;
  background-color: rgba(255, 255, 255, 0.7);
}
.placement {
  display: flex;
  flex-direction: row;
  width: 100%;
}

</style>
