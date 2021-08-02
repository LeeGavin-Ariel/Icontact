<template>
  <div v-if="channels" class="channel-list">
    <h2>{{ title }}</h2>
    <input
      type="text"
      id="searchId"
      v-model="searchId"
      placeholder="검색할 사용자 입력"
      @keypress.enter="searchById(searchId)"
    />
    <v-btn @click="searchById">검색</v-btn>

    <v-btn @click="disconnectSendbird">연결해제</v-btn>

    <ul ref="channelFrame" class="overflow-y-auto" v-scroll:#scroll-target="onScroll"
        align="center"
        justify="center"
        style="height: 80vh">
      <channel-list-item
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
      />
    </ul>
  </div>
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
      channelList: [], //페이지에서 다루는 채널리스트
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
      console.log('channelList 변경되었습니다')
      console.log(this.channelList)
      
      if (newValue && this.$el.offsetHeight + this.$el.scrollTop === this.$el.scrollHeight ) {
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
  },

  methods: {
    onScroll (e) {
        this.offsetTop = e.target.scrollTop
      },

    handleScroll() {
      console.log("scroll");
      const oldHeight = this.$refs.channelFrame.offsetHeight;

      // if (this.$el.scrollTop === 0 && !this.allMessagesIsLoaded && this.messages.length > 0) {

      // this.loadingIsActive = true

      sendBird
        // .getPreviousMessages(this.channel, this.earliestMessage.createdAt, 10)
        .getPreviousGroupMessages(this.channel)
        .then((channels) => {
          if (channels.length === 0) {
            this.loadingIsActive = false;
            this.allChannelsIsLoaded = true;
            return;
          }

          // this.$store.dispatch('addMessages', messageList)
          // this.$store.dispatch('loadPrevMessages', messageList)

          this.$nextTick(() => {
            this.$el.scrollTop = this.$refs.channelFrame.offsetHeight - oldHeight;
            this.loadingIsActive = false;
          });
        })
        .catch((error) => {
          console.error(error);
        });
    },

    scrollToBottom() {
      console.log("scroll");
      this.$el.scrollTop = this.$refs.channelFrame.offsetHeight;
    },

    async searchById(searchId) {
      sendBird
        .getGroupChannelListByNickname(searchId)
        .then(async (channels) => {
          this.channelList = channels;
          console.log("검색 뒤 channels");
          console.log(this.channelList);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async init() {
      console.log("this.$store.state.user.userId");
      console.log(this.$store.state.user);
      console.log(this.$store.state.channels);

      await sendBird
      .login(this.$store.state.user.userId)      

      sendBird
      .getGroupChannelList()
      .then(async channels => {
        await this.$store.commit('SET_CHANNELS', channels)
      })
      .catch((error) => {
        console.error(error)
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

<style>
.vscroll{
  height: "inherit";
}

.overflow-y-auto::-webkit-scrollbar { 
    display: none;
    width: 0 !important;
}

</style>