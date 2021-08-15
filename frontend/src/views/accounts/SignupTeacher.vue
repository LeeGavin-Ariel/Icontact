<template>
  <div id="bg">
    <!-- 로고 -->
    <div class="upperBox">
      <img
        src="@/assets/icontact/children.png"
        class="logo"
        alt="children-image"
      />
      <h1>회원가입</h1>
    </div>

    <!-- 회원 가입 폼 -->
    <div class="lowerBox">
      <div style="width: 300px">
        <!-- 유치원 코드 -->
        <input
          class="formInput"
          type="text"
          oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
          id="kindergardenCode"
          v-model="kindergardenCode"
          @input="searchClass"
          maxlength="4"
          placeholder="유치원 코드"
        />
        <div
          v-if="!kindergardenCode || kindergardenClasses.length === 0"
          style="
            text-align: left;
            color: red;
            font-size: 12px;
            margin-left: 19px;
          "
        >
          존재하지 않는 유치원 코드입니다.
        </div>

        <!-- 반 목록 선택 (드롭다운) -->

        <select
          name="classCode"
          class="selectBoxForClasscode"
          v-model="classCode"
          v-if="kindergardenCode && kindergardenClasses.length !== 0"
        >
          <option value="noValue">반 선택</option>
          <option
            v-for="[Class, idx] in kindergardenClasses"
            v-bind:key="idx"
            v-bind:value="idx"
          >
            {{ Class }}
          </option>
        </select>

        <!-- 회원가입 버튼 -->
        <v-btn
          block
          rounded
          style="background-color: #58679a; color: white"
          :disabled="classCode === 'noValue'"
          @click="signup"
        >
          회원가입
        </v-btn>
        <button @click="$router.go(-1)" class="etcBtn">뒤로 가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from "@/api/user.js";
import axios from "axios";
import SERVER from "@/api/drf.js";
import sendBird from "@/services/SendBird.js";
import { requestGet } from "@/utils/request.js";

export default {
  name: "SignupTeacher",
  data: () => {
    return {
      relationship: 2,
      kindergardenCode: "",
      kidName: "",
      kindergardenClasses: [],
      classCode: "noValue",
    };
  },
  methods: {
    async signup() {
      try {
        await userApi.registerUser({
          userId: this.$store.state.sendUserId,
          password: this.$store.state.sendPassword,
          userName: this.$store.state.sendUserName,
          userTel: this.$store.state.sendPhoneNumber,
          type: this.relationship,
          classCode: this.classCode[1],
          kidName: this.kidName,
        });

        await sendBird.addUser(
          this.$store.state.sendUserId,
          this.$store.state.sendUserName + " 선생님"
        );

        await sendBird.login(this.$store.state.sendUserId);

        var classMember = await requestGet(
          SERVER.URL +
            SERVER.ROUTES.getClassMember +
            "?classCode=" +
            this.classCode[1]
        );

        var sendUserId = this.$store.state.sendUserId;

        classMember.forEach((element, index) => {
          if (element.userId != sendUserId) {
            var roomName = sendUserId + "님과 " + element.userId + "의 방";
            (function (index) {
              setTimeout(
                () =>
                  sendBird.createGroupChannelWithUserIds(
                    sendUserId,
                    element.userId,
                    roomName,
                    null,
                    null
                  ),
                200 * index
              );
            })(index);
          }
        });
        this.$fire({
          html: `<a href="javascript:void(0);"></a>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">회원가입에 성공하였습니다!</p>`,
          type: "success",
          confirmButtonColor: "#58679A",
          focusConfirm: false,
        }).then(() => {
          this.$router.push({ name: "Login" });
        });
      } catch (e) {
        this.$fire({
          html: `<a href="javascript:void(0);"></a>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">회원가입에 실패하였습니다!</p>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">다시 시도해주세요.</p>`,
          type: "error",
          confirmButtonColor: "#58679A",
          focusConfirm: false,
        });
      }
    },

    searchClass() {
      axios({
        url:
          SERVER.URL +
          SERVER.ROUTES.searchClass +
          "?kinderCode=" +
          this.kindergardenCode,
        method: "get",
      })
        .then((res) => {
          this.kindergardenClasses = res.data;
        })
        .catch(() => {
          this.kindergardenClasses = [];
        });
    },
  },
};
</script>

<style scoped>
* {
  font-family: "NanumSquareRound";
  font-size: 0.95em;
}
#bg {
  background-color: #a8b1cf;
  width: 100%;
  height: 100%;
}
.upperBox {
  position: absolute;
  top: 28%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
.lowerBox {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
h1 {
  font-weight: 900;
  letter-spacing: 2px;
  font-size: 48px;
  color: #58679a;
}
.logo {
  width: 10rem;
  padding-right: 10px;
  display: inline-block;
  vertical-align: middle;
}
button {
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
.etcBtn {
  margin: 20px 3px 3px 3px;
  padding: 0px 7px 0px 3px;
  color: #404c74;
}
.etcBtn:hover {
  color: rgba(256, 256, 256, 1);
  transition: 0.3s;
}
.selectBoxForClasscode {
  background-color: rgba(255, 255, 255, 0.7);
  box-shadow: 1px 1px 1px 1px #58679a;
  border-radius: 70px;
  height: 36px;
  width: 300px;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 6px 3px;
}
</style>