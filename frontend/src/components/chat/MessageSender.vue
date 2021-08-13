<template>
  <div class="message-sender" style="background: white">
    <textarea
      v-model="message"
      placeholder="메시지를 입력하세요"
      @keyup.enter="send(message)"
    />
    <div class="send-btn" @click="send(message)">
      <icon name="paper-plane" />
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
      console.log("tesetsdafsdfasdf");
      // console.log(this.channel);
      // await this.$store.commit("SET_CHANNEL", this.channel);

      await sendBird
        .sendMessage(this.channel, striptags(message))
        .then((message) => {
          this.$store.dispatch("addMessage", message);
          this.message = "";
          this.updateChannels();
          // this.updateChannel();
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async updateChannels() {
      console.log("업데이트를 위한 새로운 채널을 불러올게요!!!");
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
// @import "../../assets/scss/index.scss";
.message-sender {
  // height: 110px;
  // position: absolute;
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

  & .send-btn:hover {
    // color: rgb(252, 255, 94);
    color: rgb(209, 219, 255, 1);
  }
}
</style>
