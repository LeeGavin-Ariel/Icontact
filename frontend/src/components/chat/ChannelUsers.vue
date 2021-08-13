<template>
  <div class="channel-users">
    <div class="room">{{ opponentNickname }}과의 대화</div>
    <!-- <h2>{{ opponentNickname }}과의 대화</h2> -->
    <!-- <p class="statusMsg" v-if="connect == 'offline' && teacher">
      선생님은 현재 자리에 없습니다.
    </p> -->
    <div class="status-wrapper">
      <div class="status-msg" v-if="stateCode == 1 && teacher">
        {{ opponentNickname }}은 현재 온라인 🟢
      </div>
      <div class="status-msg" v-else-if="stateCode == 2 && teacher">
        {{ opponentNickname }}은 현재 자리비움 🟠
      </div>
      <div class="status-msg" v-else-if="stateCode == 3 && teacher">
        {{ opponentNickname }}은 현재 오프라인 🔴
      </div>
    </div>
  </div>
</template>

<script>
// import ChannelUser from '@/components/chat/ChannelUser'
import sendBird from "@/services/SendBird.js";
import chatApi from "@/api/chat.js";
import { mapState } from "vuex";

export default {
  name: "ChannelUsers",

  components: {
    // ChannelUser
  },

  data() {
    return {
      opponentNickname: "",
      opponentId: "",
      connect: "",
      stateCode: 0,
      teacher: false,
    };
  },

  computed: {
    ...mapState(["channel", "channelUsers"]),
  },

  watch: {
    channel: {
      handler: function (newValue) {
        console.log("채널유저스 채널 변경");
        this.init(newValue);
      },
      deep: true,
    },
  },

  mounted() {
    // this.init(this.channel)
  },

  methods: {
    async getOpponent(oppoId) {
      console.log("확인할 id");
      console.log(oppoId);
      var result = await chatApi.getUserStatus(oppoId);
      return result;
    },

    async init(channel) {
      await sendBird
        .getChannelUsers(channel)
        .then((participantList) => {
          this.$store.commit("SET_CHANNEL_USERS", participantList);
          // console.log('this.channelUsers')
          // console.log(this.channelUsers)
        })
        .catch((error) => {
          console.error(error);
        });

      //이게 있어야 됩니다.. 메시지를 받을때 채팅방이 업데이트됨
      await sendBird.onMessageReceived(channel, (channel, message) => {
        console.log("메시지를 받았습니다");
        this.$store.dispatch("addMessage", message);
      });

      await sendBird.onUserEntered(channel, (channel, user) => {
        this.$store.dispatch("addChannelUser", user);
      });

      await sendBird.onUserExited(channel, (channel, user) => {
        this.$store.dispatch("removeChannelUser", user);
      });

      this.setData();
    },
    async setData() {
      console.log("this.channelUsers");
      console.log(this.channelUsers);

      if (this.$store.state.user.userId == this.channelUsers[0].userId) {
        console.log("0번이 나");

        if (this.channelUsers[1].nickname.substr(-3) == "선생님") {
          console.log("선생님이닷");
          this.teacher = true;
        } else this.teacher = false;

        this.opponentNickname = this.channelUsers[1].nickname;
        this.opponentId = this.channelUsers[1].userId;
        this.connect = this.channelUsers[1].connectionStatus;
      } else {
        console.log("1번이 나");

        if (this.channelUsers[0].nickname.substr(-3) == "선생님") {
          console.log("선생님이닷");
          this.teacher = true;
        } else this.teacher = false;
        console.log(this.channelUsers[0]);
        this.opponentNickname = this.channelUsers[0].nickname;
        this.opponentId = this.channelUsers[0].userId;
        this.connect = this.channelUsers[0].connectionStatus;
      }

      this.stateCode = await this.getOpponent(this.opponentId);

      console.log("선생님 상태!!!");
      console.log(this.stateCode);
      console.log(this.opponentNickname);
    },
  },
};
</script>

<style scoped lang="scss">
.channel-users {
  display: flex;
  // align-content: flex-end;
  align-items: flex-end;
  justify-content: space-between;
  .room {
    font-size: 2rem;
  }
  .status-wrapper {
    display: flex;
    // align-content: flex-end;
    // align-items: flex-start;
    align-self: flex-end;
    // align-items: flex-end;
    .status-msg {
      align-self: flex-end;
      // margin: 0px;
    }
  }
}
</style>
