<template>
  <div class="servererror-container">
    <div class="flex flex-column mt-5">

      <img class="servererror-img mt-5 mb-5" src="@/assets/servererror.png">

      <div class="servererror-title">
        서비스에 접속할 수 없습니다.
      </div>

      <div class="servererror-content mt-5">
        죄송합니다. 기술적인 문제로 일시적으로 접속되지 않았습니다.<br>
        잠시 후 다시 이용 부탁드리며 문제가 계속되는 경우 관리자에게 연락 부탁드립니다.
      </div>

      <div>
        <v-btn
        rounded
        class="mt-5 home-btn"
        @click="moveToHome">
        홈으로 이동
        </v-btn>
        </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "ServerError",
  methods: {
    moveToHome() {
      this.$router.push('/');
    }
  }
}
</script>
<style scoped>
@font-face {
    font-family: 'NanumSquareRound';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
*{
  font-family:'NanumSquareRound';
}
.servererror-img{
  width: 30%;
}
.servererror-container{
  background-color: #A8B1CF;
  width: 100%;
  height: 100%;
}
.servererror-title{
  font-weight: 700;
  letter-spacing: 2px;
  font-size:36px;
  color: black;
}
.servererror-content{
  font-size:18px;
  letter-spacing: 1px;
}
.home-btn{
  background-color: white;
  color: #58679A;
  width: 150px;
  font-size: 18px;
}
</style>


