<template>
  <div
    v-if="opponentNickname"
    class="justify-content-between channel-users"
    style="background-color: #d4daee; border-radius: 5px 5px 0px 0px"
  >
    <div class="room" style="font-size: 1.7rem; font-weight: 600">
      {{ opponentNickname }}
    </div>

    <!-- <h2>{{ opponentNickname }}과의 대화</h2> -->
    <!-- <p class="statusMsg" v-if="connect == 'offline' && teacher">
      선생님은 현재 자리에 없습니다.
    </p> -->
    <div class="status-wrapper">
      <div class="status-msg" v-if="stateCode == 1 && teacher">
        온라인
        <img src="@/assets/flaticon/on.png" style="width: 1rem" />
      </div>
      <div class="status-msg" v-else-if="stateCode == 2 && teacher">
        현재 자리비움 상태로 답장이 늦어질 수 있습니다.
        <img src="@/assets/flaticon/stop.png" style="width: 1rem" />
      </div>
      <div class="status-msg" v-else-if="stateCode == 3 && teacher">
        오프라인
        <img src="@/assets/flaticon/off.png" style="width: 1rem" />
      </div>
    </div>
  </div>
</template>

<script>
import sendBird from "@/services/SendBird.js";
import chatApi from "@/api/chat.js";
import { mapState } from "vuex";

export default {
  name: "ChannelUsers",

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
      handler: async function (newValue) {
        await this.init(newValue);
      },
      deep: true,
    },
  },

  methods: {
    async getOpponent(oppoId) {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      var result = await chatApi.getUserStatus(oppoId, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      return result;
    },

    async init(channel) {
      await sendBird
        .getChannelUsers(channel)
        .then(async (participantList) => {
          await this.$store.commit("SET_CHANNEL_USERS", participantList);
        })

      await sendBird.onMessageReceived(channel, (receivedChannel, message) => {
        if (channel == receivedChannel)
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

      if (this.$store.state.user.userId == this.channelUsers[0].userId) {

        if (this.channelUsers[1].nickname.substr(-3) == "선생님") {
          this.teacher = true;
        } else this.teacher = false;

        this.opponentNickname = this.channelUsers[1].nickname;
        this.opponentId = this.channelUsers[1].userId;
        this.connect = this.channelUsers[1].connectionStatus;
      } else {

        if (this.channelUsers[0].nickname.substr(-3) == "선생님") {
          this.teacher = true;
        } else this.teacher = false;
        this.opponentNickname = this.channelUsers[0].nickname;
        this.opponentId = this.channelUsers[0].userId;
        this.connect = this.channelUsers[0].connectionStatus;
      }

      this.stateCode = await this.getOpponent(this.opponentId);
    },
  },
};
</script>

<style scoped lang="scss">

</style>
