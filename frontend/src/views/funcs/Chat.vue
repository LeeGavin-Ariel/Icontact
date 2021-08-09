<template>
  <div style="display: flex; height: 100vh;">
    <div style="width: 20vw">
      <Sidebar />
    </div>

    <chat-main />
  </div>
</template>

<script>
import Sidebar from "@/components/common/Sidebar.vue";
import ChatMain from "@/components/chat/ChatMain.vue";
import sendBird from "@/services/SendBird.js";
import { mapState } from "vuex";

// TODO: user channel or empty channel

export default {
  name: "Chat",

  components: {
    Sidebar,
    ChatMain,
  },

  computed: {
    ...mapState(["user", "channels"]),
  },

  methods: {},

  created() {
    //사용자가 페이지를 떠날때 발생하는 이벤트
    // 떠날때 채널을 나간다.
    window.onbeforeunload = function () {
      sendBird.exitChannel("vuejs");
    };

    // console.log('this.$store.state.user.userId');
    // console.log(this.$store.state.user);

    // sendBird
    // .login(this.$store.state.user.userId)
    // .then(async user => {
    //   await this.$store.commit('SET_USER', user)
    // })
    // .catch(error => {
    //   // this.userId = ''
    //   this.message = error.message
    //   console.log(error);
    // })
  },
};
</script>

<style scoped lang="scss">
// @import "@/assets/scss/index.scss";
</style>
