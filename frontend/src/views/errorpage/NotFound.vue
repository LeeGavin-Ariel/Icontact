<template>
  <div class="notfound-container" align="center">
    <div class="flex-column mt-5">
      <img class="notfound-img mt-5 mb-5" src="@/assets/notfound.png" />

      <div class="notfound-title">찾으시는 페이지가 없습니다.</div>

      <div class="notfound-content mt-5">
        잘못된 접근이거나 요청하신 페이지를 찾을 수 없습니다.<br />
        입력하신 페이지의 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.
      </div>

      <div>
        <v-btn rounded class="mt-5 home-btn" @click="moveToHome">
          홈으로 이동
        </v-btn>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "NotFound",
  methods: {
    moveToHome() {
      this.$router.push("/");
    },
  },
};
</script>
<style scoped>
* {
  font-family: "NanumSquareRound";
}
.notfound-img {
  width: 30%;
}
.notfound-container {
  background-color: #a8b1cf;
  width: 100%;
  height: 100%;
}
.notfound-title {
  font-weight: 900;
  letter-spacing: 2px;
  font-size: 36px;
  color: black;
}
.notfound-content {
  font-size: 18px;
  letter-spacing: 1px;
}
.home-btn {
  background-color: white;
  color: #58679a;
  width: 150px;
  font-size: 18px;
}
</style>