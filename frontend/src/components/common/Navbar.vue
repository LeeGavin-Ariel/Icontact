<template>
  <div>
    <nav class="navbar">
      <!-- 로고 -->
      <button @click="moveToMainPage">
        <img src="@/assets/icontact/dark_log.png" style="height: 2.1rem" alt="logo-image" />
      </button>

      <!-- 간격 조정 -->
      <v-spacer></v-spacer>

      <!-- 호칭 -->
      <p v-if="type == 2" class="userName">
        {{ className }}반 {{ userName }} 선생님
      </p>
      <p v-if="type == 1" class="userName">
        {{ className }}반 {{ kidName }} 보호자님
      </p>

      <!-- 프로필 사진 : 드롭 다운 -->
      <v-menu left bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on" style="margin-right: 1.3em">
            <v-badge v-if="type == 2" dot overlap bottom :color="badgeColor">
              <v-avatar size="40" class="scale">
                <img
                  :src="
                    'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                    $store.state.user.profileImg
                  "
                />
              </v-avatar>
            </v-badge>
            <v-avatar v-else-if="type == 1" size="40" class="scale">
              <img
                :src="
                  'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                  $store.state.user.profileImg
                "
              />
            </v-avatar>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            @click="
              $router.push({ name: 'MyPage', params: { userId: userId } })
            "
          >
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>
          <v-list-item @click="toggleAlarm" v-if="type == 2">
            <v-list-item-title v-if="stateCode == 1"
              >자리 비움</v-list-item-title
            >
            <v-list-item-title v-if="stateCode == 2"
              >온라인</v-list-item-title
            >
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <!-- 지도 아이콘 -->
      <button class="scale">
        <img src="@/assets/flaticon/bus.png" style="height: 3.2rem" />
      </button>
    </nav>
  </div>
</template>

<script>
import SERVER from "@/api/drf.js";
import axios from "axios";
import userApi from "@/api/user.js";
export default {
  name: "Navbar",
  data() {
    return {
      type: 0,
      className: "",
      userName: "",
      userId: "",
      kidName: "",
      stateCode: "",
      badgeColor: "green",
    };
  },

  methods: {
    async toggleAlarm() {
      await this.updateStateCode();

      console.log("토글 변경" + this.stateCode);
      console.log(this.$store.state.user);
    },

    async updateStateCode() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      await userApi
        .updateUserStateCode(
          {
            userId: this.$store.state.user.userId,
            stateCode: this.stateCode == 1 ? 2 : 1,
          },
          {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          }
        )
        .then((result) => {
          if (result) {
            console.log("상태코드가 변경되었습니다");
            this.stateCode = this.stateCode == 1 ? 2 : 1;
            this.badgeColor = this.badgeColor == "green" ? "red" : "green";
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    setInfo() {
      this.type = this.$store.state.user.type
      this.className = this.$store.state.user.className
      this.userName = this.$store.state.user.userName
      this.userId = this.$store.state.user.userId
      this.kidName = this.$store.state.user.kidName
      this.stateCode =  this.$store.state.user.stateCode
      
    },
    logout() {
      if (confirm("정말 로그아웃하시겠습니까?")) {
        axios({
          url:
            SERVER.URL +
            SERVER.ROUTES.logout +
            `?userId=${this.$store.state.user.userId}`,
          method: "get",
        }).then(() => {
          this.$store.dispatch("removeUser");
          if (this.$route.path !== "Login") this.$router.push("Login");
        });
      }
    },
    moveToMainPage() {
      if (this.$route.path !== "/") {
        this.$router.push({ name: "MainPage" });
      }
    },
  },
  computed: {
    checkLogin() {
      console.log("로그인됨");
      this.setInfo();

      return this.$store.getters.isLogged;
    },
  },
  watch: {
    checkLogin() {
      this.setInfo();
    },
  },
};
</script>

<style scoped>
* {
  font-family: "NanumSquareRound";
  letter-spacing: -0.5px;
  color: rgba(40, 40, 40, 0.8);
}
.navbar {
  background-color: rgba(102, 122, 188, 0.23);
  padding-left: 1.5em;
  padding-right: 1.5em;
  padding-top: 0.3em;
  padding-bottom: 0.3em;
}
.scale {
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out;
}
.scale:hover {
  transform: scale(1.2);
  -webkit-transform: scale(1.2);
  -moz-transform: scale(1.2);
  -ms-transform: scale(1.2);
  -o-transform: scale(1.2);
}
.img {
  width: 325px;
  height: 280px;
  overflow: hidden;
}
.userName {
  margin-bottom: 0px;
  margin-right: 1.2em;
  font-weight: 800;
  font-size: 1rem;
}
</style>