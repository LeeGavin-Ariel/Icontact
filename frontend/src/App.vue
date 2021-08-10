<template>
  <v-app id="app">
    <Navbar
    v-if="$store.state.isLoggedIn"
    />
    <router-view/>
  </v-app>
</template>

<script>
import { getTokenInfo, tokenExpired } from '@/utils/jwt.js';
import Navbar from '@/components/common/Navbar.vue';
import userApi from '@/api/user.js';

export default {
  name: 'App',
  components:{
    Navbar
  },
  methods: {
    async setUserInfo(userid, accessToken, refreshToken) {
      let result;
      if (!refreshToken) {
        result = await userApi.getUser(userid, {
          "access-token": accessToken,
        });
      } else {
        result = await userApi.getUser(userid, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        });
      }
      // 일단 이 밑으로 실행 안됨. 위에 에러나서 그런듯
      this.$store.dispatch('setUser', {
        userId: result.userId,
        userName: result.userName,
        type: result.type,
        kidId: result.kidId,
        kidName: result.kidName,
        accept: result.accept,
        userTel: result.userTel,
        stateCode: result.stateCode,
        profileImg: result.profileImg,
        kinderCode: result.kinderCode,
        kinderName: result.kinderName,
        classCode: result.classCode,
        className: result.className,
      });
      console.log(this.$store.state.user);
    }
  },
  mounted() {
    const accessToken = sessionStorage.getItem('access-token');
    if (accessToken) {
      const userid = getTokenInfo().userId;
      console.log(userid)
      if (tokenExpired()) {
        this.setUserInfo(userid, sessionStorage.getItem('access-token'), sessionStorage.getItem('refresh-token'));
      } else {
        this.setUserInfo(userid, sessionStorage.getItem('access-token'));
      }
    }
  },
};
</script>

<style>
#app {
  height: 100vh;
  width: 100vw;
}
@font-face {
  font-family: 'EliceDigitalBaeum_Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/EliceDigitalBaeum_Bold.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'NanumSquareRound';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
  font-weight: normal; 
}
</style>
