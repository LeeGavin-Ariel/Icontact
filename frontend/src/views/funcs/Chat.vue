<template>
  <div style="display: flex; height: 100vh;">
    <div style="width: 18vw">
      <Sidebar />
    </div>

    <chat-main />
  </div>
</template>

<script>
import Sidebar from "@/components/common/Sidebar.vue";
import ChatMain from "@/components/chat/ChatMain.vue";
import { mapState } from "vuex";


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

};
</script>

<style scoped>
</style>
