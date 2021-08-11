<template>
  <div>
    <nav class="navbar">
      <!-- 로고 -->
      <button @click="moveToMainPage">
        <img src="@/assets/logo.png" style="height: 1.8rem" alt="" />
      </button>

      <!-- 간격 조정 -->
      <v-spacer></v-spacer>

      <!-- 호칭 -->
      <p v-if="type == 2" style="margin-bottom:0px; margin-right: 1em">{{ className }} {{ userName }} 선생님</p>
      <p v-if="type == 1" style="margin-bottom:0px; margin-right: 1em">{{ className }} {{ kidName }} 보호자님</p>
      
      <!-- 프로필 사진 : 드롭 다운 -->
      <v-menu left bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on" style="margin-right: 1.1em">
            <v-badge v-if="type == 2" dot overlap :color="badgeColor">
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
              >알람 끄기</v-list-item-title
            >
            <v-list-item-title v-if="stateCode == 2"
              >알람 켜기</v-list-item-title
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
import SERVER from '@/api/drf.js';
import axios from 'axios'
import userApi from "@/api/user.js";
export default {
  name: "Navbar",
  data() {
    return {
      type: 0,
      className: '',
      userName: '',
      userId: '',
      kidName: '',
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
    },
    logout() {
      if (confirm("정말 로그아웃하시겠습니까?")) {
        axios({
          url: SERVER.URL + SERVER.ROUTES.logout + `?userId=${this.$store.state.user.userId}`,
          method: "get",
        })
        .then(() => {
          this.$store.dispatch('removeUser');
          if (this.$route.path !== 'Login') this.$router.push('Login');
        })
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
.navbar {
  padding-left: 1em;
  padding-right: 1em;
  padding-top: 0.3em;
  padding-bottom: 0.3em;
  background-color: #A8B1CF;
}
.scale {
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out; /* 부드러운 모션을 위해 추가*/
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
</style>