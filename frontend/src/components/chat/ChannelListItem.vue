<template>
  <!-- <li class="channel-list-item" > -->
  <v-card two-line @click="openChannel(url)" class="mt-1" dense>
    <v-row no-gutters>
      <v-col align-self="center" class="" cols="2" style="border: solid 0px">
        <v-avatar>
          <img :src="coverUrl" alt="coverUrl" />
        </v-avatar>
      </v-col>
      <v-col>
        <v-row>
          <v-col cols="8">
            <v-card-title primary-title>
              {{ nickName }}
            </v-card-title>
            <v-card-subtitle>
              {{ getLastMessage() }}
            </v-card-subtitle>
          </v-col>

          <v-col align-self="center">
            <v-row>{{ connectionStatus }}</v-row>
            <v-row>{{ getCreatedAt() }}</v-row>
          </v-col>
          <!-- <v-col align-self="end"></v-col> -->
        </v-row>
        <!-- <v-row>
          <v-card-text>{{ getCreatedAt() }} </v-card-text>
        </v-row> -->
      </v-col>
    </v-row>
  </v-card>
  <!-- <div v-if="coverUrl" class="channel-list-item_thumb">
      <img :src="coverUrl" >
    </div> -->
  <!-- </li> -->
</template>

<script>
import sendBird from "@/services/SendBird.js";

export default {
  name: "ChannelListItem",

  data() {
    return {
      nickName: "",
      connectionStatus: "",
      // lastMessage:"",
      createdAt: "",
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
  },

  methods: {
    setData() {
      console.log("this.members");
      console.log(this.members);

      if (this.$store.state.user.userId == this.members[0].userId) {
        console.log("0번");

        this.nickName = this.members[1].nickname;
        this.connectionStatus = this.members[1].connectionStatus;
      } else {
        console.log("1번");
        this.nickName = this.members[0].nickname;
        this.connectionStatus = this.members[0].connectionStatus;
      }

      console.log(this.nickName);
    },

    openChannel(url) {
      sendBird
        .getChannel(url)
        .then((channel) => {
          this.$store.commit("SET_CHANNEL", channel);
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
<style scoped lang="scss">
@import "../../assets/scss/index.scss";
</style>
