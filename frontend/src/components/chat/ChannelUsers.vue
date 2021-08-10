<template>
  <div class="channel-users">
    <h2>{{ opponent }}과의 대화</h2>
    <p class="statusMsg" v-if="connect == 'offline' && teacher">
      선생님은 현재 자리에 없습니다.
    </p>

    <!-- <ul>
      <channel-user
        v-for="(user, index) in channelUsers"
        :key="index"
        :profile-url="user.profileUrl"
        :user-id="user.userId"/>
    </ul> -->
  </div>
</template>

<script>
// import ChannelUser from '@/components/chat/ChannelUser'
import sendBird from "@/services/SendBird.js";
import { mapState } from "vuex";

export default {
  name: "ChannelUsers",

  components: {
    // ChannelUser
  },

  data() {
    return {
      opponent: "",
      connect: "",
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
    setData() {
      console.log("this.channelUsers");
      console.log(this.channelUsers);

      if (this.$store.state.user.userId == this.channelUsers[0].userId) {
        console.log("0번이 나");

        if (this.channelUsers[1].nickname.substr(-3) == "선생님") {
          console.log("선생님이닷");
          this.teacher = true;
        } else this.teacher = false;

        this.opponent = this.channelUsers[1].nickname;
        this.connect = this.channelUsers[1].connectionStatus;
      } else {
        console.log("1번이 나");

        if (this.channelUsers[0].nickname.substr(-3) == "선생님") {
          console.log("선생님이닷");
          this.teacher = true;
        } else this.teacher = false;
        console.log(this.channelUsers[0]);
        this.opponent = this.channelUsers[0].nickname;
        this.connect = this.channelUsers[0].connectionStatus;
      }

      console.log(this.opponent);
    },
  },
};
</script>

<style scoped>

</style>
