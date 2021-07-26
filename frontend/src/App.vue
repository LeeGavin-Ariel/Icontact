<template>
  <v-app id="app">
    <Navbar/>
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
    Navbar // state 방식 불가능식 props로 내려준다...
  },


  // getUser -> 우리가 거기서 뽑아낸 값으로 type을 봐서 처리를 한다 -> 노상관
  // 근데, 선생과 학부모 테이블 형태가 다르면 getUser 했을때 문제 생길 수 있다.
  // 그럼, 선생 따로, 학부모 따로 유저 정보를 가져와야하는가?
  // 프론트에서 -> 토큰을 보내서 디코딩해서 유저가 선생인지 학부모인지 체크를 해서,
  // 체크가 되면, 조건분기해서 그걸로 getTeacher, getParent 방식처럼 2가지로 나누어 불러와야함.
  methods: {
    async setUserInfo(userid, accessToken, refreshToken) {
      let result;

      console.log(this.$store.state.user)
      // getUser는 의상이 코드에서 확인하기.
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
    }
  },
  // 토큰만 확인하는 방식. or 이 방식 그대로.
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
