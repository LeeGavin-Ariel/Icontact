<template>
    <div style="background-color: rgb(250, 215, 73);">

      <nav
      class="navbar bg-light"
      style="background-color: rgb(230, 232, 240) padding-top; 0"
      >
        <button @click="moveToMainPage">
          <img src="@/assets/icontact2.png" style="height:2.8rem" alt="">
        </button>
        <!-- <v-btn plain @click="moveToMainPage">
          <v-icon>Icontact</v-icon>
        </v-btn> -->
        
        <v-spacer></v-spacer>
        <p v-if="type == 2" style="color: black; margin-bottom:0px; margin-right:20px">{{ className }} {{ userName }} 선생님</p>
        <p v-if="type == 1">{{ className }} {{ userName }} 보호자님</p>
        <v-menu
          left
          bottom
        >
          <template v-slot:activator="{ on, attrs }" >
            <v-btn
              icon
              v-bind="attrs"
              v-on="on"
            style="margin-right:15px">
              <v-avatar size="40">
                <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' + $store.state.user.profileImg">
              </v-avatar>
            </v-btn>
          </template>
          <v-list>
            <v-list-item @click="$router.push({ name: 'MyPage', params: { userId: userId }})">
              <v-list-item-title >마이페이지</v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>알람끄기</v-list-item-title>
            </v-list-item>
            <v-list-item @click="logout">
              <v-list-item-title >로그아웃</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

<!-- 선생님 버튼 -->
        <v-btn v-if="type == 2 && bus==1" icon  @click="gpsOff">  
          <v-badge
            overlap
            dot
            color="pink"
          >
            <v-avatar size="40">
              <v-img src="https://image.flaticon.com/icons/png/512/2891/2891045.png"></v-img>
            </v-avatar>
          </v-badge>
        </v-btn>
        <!-- 버스 미운행 -->
        <v-btn v-if="type == 2  && bus==0" icon @click="gpsOn">  
          <v-badge
            overlap
            dot
            color=""
          >
            <v-avatar size="40">
              <v-img src="https://image.flaticon.com/icons/png/512/2891/2891045.png"></v-img>
            </v-avatar>
          </v-badge>
        </v-btn>
<!-- 학부모 버튼 -->
<!-- Mypage를 GPS페이지로 변경해야한다 -->
        <v-btn v-if="type == 1" icon @click="$router.push({ name: 'gps'})">
          <v-avatar size="40">
              <v-img src="https://image.flaticon.com/icons/png/512/234/234270.png"></v-img>
          </v-avatar>

        </v-btn>

      </nav>
    </div>
</template>

<script>
import  { requestPost } from '@/utils/request.js';
import SERVER from '@/api/drf.js';

export default {
  name: 'Navbar',
  data() {
    return {
      type: 0,
      className: '',
      userName: '',
      userId: '',

      bus: 0,
			latitude: '',
			longitude: '',
			socket: null,
      kinderCode: '',
      interval: null,
    }
  },
  created(){

  },
  methods: {
    setInfo() {
      this.type = this.$store.state.user.type
      this.className = this.$store.state.user.className
      this.userName = this.$store.state.user.userName
      this.userId = this.$store.state.user.userId
      this.kinderCode = this.$store.state.user.kinderCode
    },
    logout() {
      if (confirm("정말 로그아웃하시겠습니까?")) {
        if(this.bus == 1){
          alert("버스 위치 정보가 활성화 중입니다.");
        }
        else{
          this.$store.dispatch('removeUser');
          if (this.$route.path !== '/') this.$router.push('/');
        }  
      }
    },
    moveToMainPage() {
      if (this.$route.path !== '/') {
        this.$router.push({ name: 'MainPage' })
      }
    },

    gpsOn(){
      this.$store.dispatch('setBus', 1);
      this.bus = this.$store.state.bus;

      this.socket = new WebSocket(`${SERVER.WS}/ws/gps`);

      this.socket.onopen = async (e) => {
        await this.createRoom();
        console.log(e);
        console.log('연결 성공');
      }

      this.socket.onmessage = ({data}) => {
        console.log(data);
      }
      this.gps();
    },
    gpsOff(){
      this.$store.dispatch('setBus', 0);
      this.bus = this.$store.state.bus;
      var mes = '{"type": "Delete", "code": "' +this.kinderCode+ '", "lat":"0", "lon":"0" }'
      console.log(mes);
      this.socket.send(mes);
      clearInterval(this.interval);
    },


		async createRoom() {
			await requestPost(`${SERVER.URL}/gps`, {name: 'test', code: this.kinderCode});
			console.log('방 생성');
      
      var mes = '{"type": "Enter", "code": "' +this.kinderCode+ '", "lat":"0", "lon":"0" }'
      console.log(mes);
      this.socket.send(mes);
		},

		deleteRoom() {
			requestPost(`${SERVER.URL}/gps`, {name: 'test', code: this.kinderCode});
			console.log('방 닫음');
		},

		geofind() {
			if(!("geolocation" in navigator)) {
				return;
			}
			// get position
			navigator.geolocation.getCurrentPosition(pos => {
				this.latitude = pos.coords.latitude;
				this.longitude = pos.coords.longitude;
			}, err => {
				console.log(err);
			});			
		},
		sendLatLng() {
			var mes = `{
				"type": "Update",
				"code": "${this.kinderCode}",
				"lat": "${this.latitude}",
				"lon": "${this.longitude}"
			}`
			this.socket.send(mes);
			console.log(mes);		
		},
		gps() {
			this.interval = setInterval(() => {
      this.geofind(); 
      this.sendLatLng();}, 1000);
		},
  },
  computed: {
    checkLogin() {
      console.log('로그인됨')
      this.setInfo()
      
      return this.$store.getters.isLogged
    }
  },
  watch: {
    checkLogin() {
      this.setInfo()
    }
  }
}
</script>

<style scoped>

</style>