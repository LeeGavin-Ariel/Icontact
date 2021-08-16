<template>
  <div class="message-sender" style="width: 100%; background: white; border-radius: 0px 0px 5px 5px;" v-if="channel">

    <textarea
      v-model="message"
      placeholder="메시지를 입력하세요"
      @keypress.enter="send(message)"
      
    />
    <div class="send-btn" @click="send(message)">
      전송
    </div>
    <!-- <div class="columns is-gapless is-centered">
      <div class="column">
        
      </div>

      <div class="column is-1 text">
        
      </div>
    </div> -->
  </div>
</template>

<script>
import sendBird from "@/services/SendBird.js";
import { mapState } from "vuex";
import striptags from "striptags";

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

      await sendBird
        .sendMessage(this.channel, striptags(message))
        .then((message) => {
          this.$store.dispatch("addMessage", message);
          this.message = "";
          this.updateChannels();
        })
    },

    async updateChannels() {
      await sendBird
        .getGroupChannelList()
        .then(async (channels) => {
          await this.$store.commit("SET_CHANNELS", channels);
        })
    },

    updateChannel() {
      sendBird
        .getGroupChannelList()
        .then(async (channels) => {
          await this.$store.commit("SET_CHANNELS", channels);
        })
    },
  },
};
</script>
<style scoped lang="scss">
.message-sender {
  display: flex;
  align-items: center;
  width: 98%;
  height: 10vh;
  bottom: 0;
  left: 0;
  right: 0;
  background: white;
  box-shadow: 0px -3px 10px rgba(black, 0.03);

  & textarea {
    border: none;
    overflow: auto;
    outline: none;
    box-shadow: none;
    resize: none;
    overflow: hidden;

    height: 100%;
    width: 95%;
    background: transparent;
    padding: 1rem 0rem 0rem 1.5rem;
  }

  & .send-btn {
    cursor: pointer;
    transition: all 600ms ease;
  }
}
</style>
