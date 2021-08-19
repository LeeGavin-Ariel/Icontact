<template>
  <v-col class="mx-auto">
    <div align="center">
      <input
        type="text"
        class="searchInput mb-1"
        placeholder="이름으로 검색"
        v-model="searchId"
        @keypress.enter="searchById(searchId)"
      />
      <button class="searchBtn" @click="searchById(searchId)">검색</button>
    </div>

    <div
      id="channelFrame"
      ref="channelFrame"
      class="content-container list-col mt-5"
      style="height: 80vh"
    >
      <div
        class="d-flex flex-column align-items-stretch flex-shrink-0"
        style="width: 100%"
      >
        <channel-list-item
          style="border: collpase"
          v-for="(channel, index) in this.channelList"
          :key="index"
          :cover-url="channel.coverUrl"
          :name="channel.name"
          :url="channel.url"
          :members="channel.members"
          :searchId="searchId"
          :lastMessage="
            channel.lastMessage === undefined ? null : channel.lastMessage
          "
          :channel="channel"
        />
      </div>
    </div>
  </v-col>
</template>

<script>
import ChannelListItem from "@/components/chat/ChannelListItem";
import sendBird from "@/services/SendBird.js";
import { mapState } from "vuex";

export default {
  name: "ChannelList",

  components: {
    ChannelListItem,
  },

  data() {
    return {
      title: this.$store.state.user.userId,
      searchId: "",
      channelList: [],
      allChannelsIsLoaded: false,
      loadingIsActive: false,
      offsetTop: 0,
    };
  },

  computed: {
    ...mapState(["channels"]),
  },

  watch: {
    channels() {
      this.channelList = this.channels;
    },

    channelList(newValue) {
      if (
        newValue &&
        this.$el.offsetHeight + this.$el.scrollTop === this.$el.scrollHeight
      ) {
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
  },

  methods: {
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },

    handleScroll() {
      const oldHeight = this.$refs.channelFrame.offsetHeight;

      sendBird
        .getPreviousGroupMessages(this.channel)
        .then((channels) => {
          if (channels.length === 0) {
            this.loadingIsActive = false;
            this.allChannelsIsLoaded = true;
            return;
          }

          this.$nextTick(() => {
            this.$el.scrollTop =
              this.$refs.channelFrame.offsetHeight - oldHeight;
            this.loadingIsActive = false;
          });
        })
    },

    scrollToBottom() {
      this.$el.scrollTop = this.$refs.channelFrame.offsetHeight;
    },

    async searchById(searchId) {
      sendBird
        .getGroupChannelListByNickname(searchId)
        .then(async (channels) => {
          this.channelList = channels;
        })
    },

    async init() {

      await sendBird.login(this.$store.state.user.userId);

      sendBird
        .getGroupChannelList()
        .then(async (channels) => {
          await this.$store.commit("SET_CHANNELS", channels);
        })
    },

    disconnectSendbird() {
      sendBird.disconnect();
    },
  },

  mounted() {
    this.init();
  },
};
</script>

<style scoped>
.border-bottom {
  border-bottom: solid 0.5px #a8b1cf;
}

.searchInput {
  background-color: rgba(256, 256, 256, 0.7);
  border-radius: 20px;
  height: 36px;
  width: 71%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.searchBtn {
  background-color: rgba(168, 177, 207, 1);
  border-radius: 70px;
  height: 36px;
  width: 20%;
  margin: 20px 3px 3px 3px;
  text-align: center;
  color: rgba(256, 256, 256);
  letter-spacing: -1px;
}
/* 스크롤 */
.content-container {
  overflow-y: scroll;
  height: 80vh;
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 5px;
}
.selected {
  background-color: #58679a;
}
.list-col {
  background-color: rgba(256, 256, 256, 0.7);
}
</style>