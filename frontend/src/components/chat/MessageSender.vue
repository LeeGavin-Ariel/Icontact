<template>
  <div class="message-sender" style="background:white">
    <div class="columns is-gapless is-centered">
      <div class="column">
        <textarea
          v-model="message"
          placeholder="메시지를 입력하세요"
          @keyup.enter="send(message)"
          
        />
      </div>

      <div class="column is-1 text">
        <div @click="send(message)">
          <icon name="paper-plane" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import sendBird from "@/services/SendBird.js";
import { mapState } from "vuex";
import striptags from 'striptags'

export default {
  name: "Message",

  data() {
    return {
      message: "",
    };
  },

  computed: {
    ...mapState(["channel"]),
  },

  methods: {
    async send(message) {
      if (!message) return;
      console.log("tesetsdafsdfasdf");
      // console.log(this.channel);
      // await this.$store.commit("SET_CHANNEL", this.channel);

      await sendBird
        .sendMessage(this.channel, striptags(message))
        .then((message) => {
          this.$store.dispatch('addMessage', message)
          this.message = ''
          this.updateChannels();
          // this.updateChannel();
        })
        .catch((error) => {
          console.error(error)
        })
    },

    async updateChannels() {
      console.log('업데이트를 위한 새로운 채널을 불러올게요!!!');
      await sendBird
        .getGroupChannelList()
        .then(async (channels) => {
          await this.$store.commit("SET_CHANNELS", channels);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    updateChannel() {
      sendBird
        .getGroupChannelList()
        .then(async (channels) => {
          await this.$store.commit("SET_CHANNELS", channels);
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
