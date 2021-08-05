<template>
  <div class="channel" >
    <channel-users v-if="channel" />
    <!-- <hr /> -->
    <messages v-if="channel" />

    <message-sender v-if="channel" />
  </div>
</template>

<script>
import sendBird from "@/services/SendBird.js";
import Messages from "@/components/chat/Messages";
import MessageSender from "@/components/chat/MessageSender";
import ChannelUsers from "@/components/chat/ChannelUsers";
import { mapState } from "vuex";

export default {
  name: "Channel",

  components: {
    Messages,
    MessageSender,
    ChannelUsers,
  },

  computed: {
    ...mapState(["channel"]),
  },

  watch: {
    channel: {
      handler(newValue) {
        this.init(newValue.url);
      },
    },
  },

  mounted() {
    // this.init('test')
  },

  methods: {
    init(url) {
      sendBird
        .getChannel(url)
        .then((channel) => {
          this.$store.commit("SET_CHANNEL", channel);
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped lang="scss">
@import "../../assets/scss/index.scss";
</style>
