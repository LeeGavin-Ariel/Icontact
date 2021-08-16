<template>
  <div class="messages messagesScroll" v-if="channel">
    <loading :is-active="loadingIsActive" />

    <!-- <h1 v-if="stateFlag">TEST</h1>
    <h1 >{{messages[0].createdAt}}</h1> -->

    <ul v-if="messages" ref="messagesList">
      <message
        v-for="(message, index) in transferMessage"
        :key="index"
        :message="message"
      />
    </ul>
  </div>
</template>

<script>

import Loading from "@/components/chat/Loading.vue";
import Message from "@/components/chat/Message.vue";
import sendBird from "@/services/SendBird.js";
import chatApi from "@/api/chat.js";
import { mapState, mapGetters, mapActions } from "vuex";

export default {
  name: "Messages",

  components: {
    Loading,
    Message,
  },

  data() {
    return {
      loadingIsActive: false,
      allMessagesIsLoaded: false,
      stateFlag: false,
      transferMessage: [],
      channelUsersInfo: {},

      oldHeight: 0,
    };
  },

  computed: {
    ...mapState(["channel", "messages"]),

    ...mapGetters(["earliestMessage"]),

    ...mapActions(["addMessages"]),
  },

  watch: {
    messages: {
      handler: async function (newValue) {

        var myImg = "";
        var oppoImg = "";
        var myId = "";
        var oppoId = "";

        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
        let header = {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        };

        if (this.channel.members[0].userId == this.$store.state.user.userId) {
          myImg = await chatApi.getUserProfileImg(
            this.channel.members[0].userId,
            header
          );
          myId = this.channel.members[0].userId;
          oppoImg = await chatApi.getUserProfileImg(
            this.channel.members[1].userId,
            header
          );
          oppoId = this.channel.members[1].userId;
        } else if (
          this.channel.members[1].userId == this.$store.state.user.userId
        ) {
          myImg = await chatApi.getUserProfileImg(
            this.channel.members[1].userId,
            header
          );
          myId = this.channel.members[1].userId;
          oppoImg = await chatApi.getUserProfileImg(
            this.channel.members[0].userId,
            header
          );
          oppoId = this.channel.members[0].userId;
        }

        //각 메시지의 발신자 프로필을 넣어줌
        newValue.forEach((element) => {
          if (element._sender.userId == myId) {
            element.profileImg = myImg;
            element.senderId = myId;
          } else {
            element.profileImg = oppoImg;
            element.senderId = oppoId;
          }
        });

        this.transferMessage = newValue;

        if (newValue) {
          this.$nextTick(() => {
            this.loadingIsActive = false;
            this.scrollToBottom();
          });
        }
        await this.getTeacherState();
      },
      deep: true,
    },

    channel: {
      handler: function (newValue) {
        this.init(newValue);
      },
    },
  },

  destroyed() {
    this.$el.removeEventListener("scroll", this.handleScroll);
  },

  methods: {
    async init(channel) {
      await sendBird
        .getChannelMessages(channel, 10)
        .then(async (messageList) => {
          await this.$store.commit("SET_MESSAGES", messageList);
          this.scrollToBottom();
        })

      await sendBird.onMessageReceived(channel, (receivedChannel, message) => {

        if (channel == receivedChannel)
          this.$store.dispatch("addMessage", message);
      });

      this.$nextTick(() => {
        this.$el.addEventListener("scroll", this.handleScroll);
      });
    },

    scrollToBottom() {
      this.$el.scrollTop = this.$el.scrollHeight + 20;
    },

    async handleScroll() {

      // 스크롤이 맨위로 올라갔으면
      if (this.$el.scrollTop === 0) {
        this.loadingIsActive = true;
        var listQuery = await this.channel.createPreviousMessageListQuery();
        listQuery.limit = 100;
        listQuery.reverse = false;
        listQuery.includeMetaArray = true;
        listQuery.includeReaction = true;

        var preMsgs = await listQuery.load();

        this.oldHeight = this.$el.scrollHeight + 20;
        await this.$store.dispatch("loadPrevMessages", preMsgs);
        this.$nextTick(() => {
          this.loadingIsActive = false;
        });
      }
    },

    getTeacherState() {
      sendBird
        .getChannelUsers(this.channel)
        .then((participantList) => {
          var opponent = null;
          participantList.forEach((el) => {
            if (el.userId !== this.$store.state.user.userId) opponent = el;
          });
          if (
            opponent &&
            opponent.nickname.includes("부모님") &&
            opponent.connectionStatus == "offline"
          ) {
            this.stateFlag = true;
          } else this.stateFlag = false;
        })
    },
  },
};
</script>
<style scoped>
.messages {
  background: rgba(256, 256, 256, 0.4);
}

/* 스크롤 */
.messagesScroll {
  overflow-y: scroll;
  height: 80vh;
}
.messagesScroll::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 1px;
}
.messagesScroll::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 1px;
}
.messagesScroll::-webkit-scrollbar-track {
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 1px;
}

ul {
  padding: 0px;
  margin: 0px;
}
</style>
