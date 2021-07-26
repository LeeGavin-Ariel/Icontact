<template>
  <v-app id="app">
    <router-view/>
  </v-app>
</template>

<script>
import { getTokenInfo, tokenExpired } from '@/utils/jwt.js';
import userApi from '@/api/user.js';
export default {
  name: 'App',
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
      this.$store.dispatch('setUser', {
        userId: result.userId,
        userName: result.userName,
        type: result.type,
        kidId: result.kidId,
        kidName: result.kidName,
        accept: result.accept,
        stateCode: result.stateCode,
        profileImg: result.profileImg,
        kinderCode: result.kinderCode,
        kinderName: result.kinderName,
        classCode: result.classCode,
        className: result.className,
      });
    }
  },
  mounted() {
    const accessToken = sessionStorage.getItem('access-token');
    if (accessToken) {
      const userid = getTokenInfo().userid;
      if (tokenExpired()) {
        this.setUserInfo(userid, sessionStorage.getItem('access-token'), sessionStorage.getItem('refresh-token'));
      } else {
        this.setUserInfo(userid, sessionStorage.getItem('access-token'));
      }
    }
  }
};
</script>
