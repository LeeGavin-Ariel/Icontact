<template>
  <div class="channel" style="height: 100%"> 
    <channel-users style="height:5rem" v-if="choiceChannel" />
    <!-- <hr /> -->
    <messages style="height:70vh" v-if="choiceChannel" />

    <message-sender v-if="choiceChannel" />
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

  data(){
    return{
      choiceChannel:{},
    };
  },

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
      async handler(newValue) {
        console.log('채널 오픈')
        console.log(newValue)
        this.choiceChannel = newValue;
        // await this.init(newValue.url);
      },
    },
  },

  mounted() {
    // this.init('test')
  },

  methods: {
    async init(url) {
      await sendBird
        .getChannel(url)
        .then(async (channel) => {
          console.log('채널 또받음');
          console.log(channel);
          // await this.$store.commit("SET_CHANNEL", channel);
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
