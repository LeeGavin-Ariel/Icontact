<template>
  <div>
    <nav class="navbar">
      <!-- 로고 -->
      <button @click="moveToMainPage">
        <img
          src="@/assets/icontact/dark_log.png"
          style="height: 2.1rem"
          alt="logo-image"
        />
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
          <v-list-item @click="moveToMyPage">
            <v-list-item-title style="text-align: right"
              >마이페이지</v-list-item-title
            >
          </v-list-item>
          <v-list-item @click="toggleAlarm" v-if="type == 2">
            <v-list-item-title style="text-align: right" v-if="stateCode == 1"
              ><img
                src="@/assets/flaticon/off.png"
                style="width: 0.7rem; margin-right: 0.3rem"
              />자리비움</v-list-item-title
            >
            <v-list-item-title style="text-align: right" v-if="stateCode == 2"
              ><img
                src="@/assets/flaticon/on.png"
                style="width: 0.7rem; margin-right: 0.3rem"
              />온라인</v-list-item-title
            >
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-title style="text-align: right"
              >로그아웃</v-list-item-title
            >
          </v-list-item>
        </v-list>
      </v-menu>

      <!-- 선생님 버튼 -->
      <button class="scale" v-if="type == 2 && bus == 1" @click="gpsOff">
        <v-badge dot overlap bottom color="busOn">
          <v-avatar size="40">
            <img src="@/assets/flaticon/bus-gps.png">
          </v-avatar>
        </v-badge>
      </button>

      <!-- 버스 미운행 -->
      <button class="scale" v-if="type == 2 && bus == 0" @click="gpsOn">
        <v-badge overlap bottom dot color="stateOff">
          <v-avatar size="40">
            <img src="@/assets/flaticon/bus-gps.png">
          </v-avatar>
        </v-badge>
      </button>
      
      <!-- 학부모 버튼 -->
      <button class="scale tooltip" v-if="type == 1" @click="moveToGPS">
        <v-avatar size="40">
          <img src="@/assets/flaticon/bus-gps.png">
        </v-avatar>
        <span class="tooltip-text">툴팁</span>
      </button>
    </nav>
  </div>
</template>

<script>
import SERVER from "@/api/drf.js";
import axios from "axios";
import userApi from "@/api/user.js";
import { requestPost } from "@/utils/request.js";

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
      badgeColor: "stateOn",
      bus: 0,
      latitude: "",
      longitude: "",
      socket: null,
      kinderCode: "",
      interval: null,
    };
  },
  created() {},
  methods: {
    async toggleAlarm() {
      await this.updateStateCode();
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
            this.$store.state.user.stateCode = this.stateCode =
              this.$store.state.user.stateCode == 1 ? 2 : 1;
            this.badgeColor =
              this.$store.state.user.stateCode == 2 ? "stateOff" : "stateOn";
          }
        });
    },

    setInfo() {
      this.type = this.$store.state.user.type;
      this.className = this.$store.state.user.className;
      this.userName = this.$store.state.user.userName;
      this.userId = this.$store.state.user.userId;
      this.kidName = this.$store.state.user.kidName;
      this.stateCode = this.$store.state.user.stateCode;
      this.kinderCode = this.$store.state.user.kinderCode;
      this.badgeColor =
        this.$store.state.user.stateCode == 2 ? "stateOff" : "stateOn";
    },
    logout() {
      this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">로그아웃 하시겠습니까?</p>`,
        type: "question",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#58679A",
      }).then(async (r) => {
        if (r.value) {
          if (this.bus === 1) {
            this.$fire({
              html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">버스 위치 정보가 활성화 중입니다.</p>`,
              type: "error",
              confirmButtonColor: "#58679A",
              focusConfirm: false,
            });
          } else {
            axios({
              url:
                SERVER.URL +
                SERVER.ROUTES.logout +
                `?userId=${this.$store.state.user.userId}`,
              method: "get",
            }).then(() => {
              this.$store.dispatch("removeUser");
              if (this.$route.path !== "Login")
                this.$router.push({ name: "Login" });
            });
          }
        }
      });
    },
    moveToMainPage() {
      if (this.$route.path !== "/") {
        this.$router.push({ name: "MainPage" });
      }
    },
    moveToMyPage() {
      this.$router
        .push({ name: "MyPage", params: { userId: this.userId } })
        .catch(() => {});
    },
    moveToGPS() {
      if (this.$route.path !== "/gps") {
        this.$router.push({ name: 'gps' })
      }
    },

    gpsOn() {
      this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">등하원 버스 운행을 시작하시겠습니까?</p>`,
        imageUrl: require('@/assets/flaticon/not_running.png'),
        imageWidth: 225,
        imageHeight: 185,
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#58679A",
      }).then(async (r) => {
        if (r.value) {
          this.$store.dispatch("setBus", 1);
          this.bus = this.$store.state.bus;
    
          this.socket = new WebSocket(`${SERVER.WS}/ws/gps`);
    
          this.socket.onopen = async (e) => {
            await this.createRoom();
            console.log(e);
            console.log("연결 성공");
          };
    
          this.socket.onmessage = ({ data }) => {
            console.log(data);
          };
          this.gps();
        }
      })
    },
    gpsOff() {
      this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">등하원 버스 운행을 종료하시겠습니까?</p>`,
        imageUrl: require('@/assets/flaticon/Delete.png'),
        imageWidth: 225,
        imageHeight: 185,
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#58679A",
      }).then(async (r) => {
        if (r.value) {
          this.$store.dispatch("setBus", 0);
          this.bus = this.$store.state.bus;
          var mes =
            '{"type": "Delete", "code": "' +
            this.kinderCode +
            '", "lat":"0", "lon":"0" }';
          console.log(mes);
          this.socket.send(mes);
          clearInterval(this.interval);
        }
      })
    },

    async createRoom() {
      await requestPost(`${SERVER.URL}/gps`, {
        name: "test",
        code: this.kinderCode,
      });
      console.log("방 생성");

      var mes =
        '{"type": "Enter", "code": "' +
        this.kinderCode +
        '", "lat":"0", "lon":"0" }';
      console.log(mes);
      this.socket.send(mes);
    },

    deleteRoom() {
      requestPost(`${SERVER.URL}/gps`, { name: "test", code: this.kinderCode });
      console.log("방 닫음");
    },

    geofind() {
      if (!("geolocation" in navigator)) {
        return;
      }
      // get position
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.latitude = pos.coords.latitude;
          this.longitude = pos.coords.longitude;
        },
        (err) => {
          console.log(err);
        }
      );
    },
    sendLatLng() {
      var mes = `{
				"type": "Update",
				"code": "${this.kinderCode}",
				"lat": "${this.latitude}",
				"lon": "${this.longitude}"
			}`;
      this.socket.send(mes);
      console.log(mes);
    },
    gps() {
      this.interval = setInterval(() => {
        this.geofind();
        this.sendLatLng();
      }, 1000);
    },
  },
  computed: {
    checkLogin() {
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