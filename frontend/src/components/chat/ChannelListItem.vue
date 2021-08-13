<template>

  <div @click="openChannel(url)" class="list-group list-group-flush scrollarea">
    <div class="list-group-item list-group-item-action py-2 lh-tight border-bottom" style="background-color:rgb(256, 256, 256, 0.7);">
      <div class="d-flex align-items-center" style="height: 9vh; width:100%">
        
        <div align="center" class="col-3">
          <div class="mb-2">
            <img
            v-if="profileImg" class="profile-img" :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' + profileImg"/>
            <img
            v-else class="profile-img"
            :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/profileImg/noImg_1628231352109.png'"
            />
          </div>

          

        </div>
        <div class="col-9">
          <div class="chat-list-name mb-1">{{ nickName }}</div>
          <div class="chat-last">{{ getLastMessage() }}</div>
          <div class="chat-time" align="right">{{ getCreatedAt() }}</div>
        </div>
        
      </div>

    </div>
  </div>
                      
</template>

<script>
import sendBird from "@/services/SendBird.js";
import chatApi from "@/api/chat.js";

export default {
  name: "ChannelListItem",

  data() {
    return {
      idx: 0,
      nickName: "",
      connectionStatus: "",
      // lastMessage:"",
      createdAt: "",
      opponentId: "",
      profileImg: "",
    };
  },

  props: {
    coverUrl: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
    url: {
      type: String,
      required: true,
    },
    lastMessage: {
      type: Object,
    },
    members: [],
    channel: {
      type: Object,
    },
  },

  watch: {
    channel() {
      this.setData();
    },
  },

  methods: {
    async setData() {

      if (this.$store.state.user.userId == this.members[0].userId) {
        this.nickName = this.members[1].nickname;
        this.connectionStatus = this.members[1].connectionStatus;
        this.opponentId = this.members[1].userId;
      } else {
        this.nickName = this.members[0].nickname;
        this.connectionStatus = this.members[0].connectionStatus;
        this.opponentId = this.members[0].userId;
      }

      let result = await chatApi.getUserProfileImg(this.opponentId);
      if(result==""){
        this.profileImg = "profileImg/noImg_1628231352109.png";
        
      }else{
        this.profileImg = result;
      }
    },

    async openChannel(url) {
      console.log('채널오픈');
      await sendBird
        .getChannel(url)
        .then(async (channel) => {
          channel.myImg = await chatApi.getUserProfileImg(this.$store.state.user.userId);
          channel.oppoImg = this.profileImg;
          await this.$store.commit("SET_CHANNEL", channel);
          // 채널이 변경되면 computed 작동
        })
        .catch((error) => {
          console.error(error);
        });
    },

    toStringByFormatting(source, delimiter = "/") {
      const date = new Date(source);
      const year = date.getFullYear();
      const month = this.leftPad(date.getMonth() + 1);
      const day = this.leftPad(date.getDate());
      return [year, month, day].join(delimiter);
    },

    leftPad(value) {
      if (value >= 10) {
        return value;
      }
      return `0${value}`;
    },

    getLastMessage() {
      if (this.lastMessage === null) {
        return "";
      } else {
        return this.lastMessage.message;
      }
    },

    getCreatedAt() {
      if (this.lastMessage === null) {
        return "";
      } else {
        return this.toStringByFormatting(this.lastMessage.createdAt);
      }
    },
  },

  created() {
    this.setData();
  },
};
</script>
<style scoped>
.border-bottom{
  border-bottom: solid 0.5px #a8b1cf;
}
.chat-list-name {
  font-size: 1rem;
  display:block;
  font-weight: 900;
}
.chat-time {
  font-size:0.8rem;
}
.chat-last{
  font-size:1.2rem;
}
.profile-img {
  width: 50%;
  border-radius: 100%;
  box-shadow: 0px 0px 2px 2px rgba(168, 177, 207, 0.7);
}
</style>
