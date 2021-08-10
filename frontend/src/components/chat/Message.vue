<template>
  <v-list
    id="messagesScroll"
    :class="{ 'is-reverse is-user': isUser }"
    class="message columns is-gapless"
  >
    <!-- class="message columns is-gapless overflow-y-auto"
    v-scroll:#scroll-target="onScroll" -->
    <v-avatar>
      <img        
        :src="
          'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
          message.profileImg
        "
      />
    </v-avatar>
    <!-- {{message._sender.nickname}} -->
    <!-- {{message.profileImg}} {{message.senderId}}<br/>  {{message.message}}}} -->

    <v-card dense class="pr-1 ml-3 mr-3">
      <v-card-subtitle>
        <p>{{ message.message }}</p>
      </v-card-subtitle>
      <!-- <span>{{ message._sender.userId }}</span> -->
    </v-card>
    <div class="date">{{ date }}</div>

    <!-- <div class="column is-1">
      <user-thumbnail
        v-if="message._sender.profileUrl"
        :image-url="message._sender.profileUrl"/>
    </div> -->

    <!-- <div class="column is-10">
      <div class="message-text">
        {{ message.message }}

        <p class="message-infos">
          <span v-if="!isUser" class="message-name">{{ message._sender.userId }}</span>
          <span class="message-date has-text-primary"> {{ date }}</span>
        </p>

      </div>
    </div> -->
  </v-list>
</template>

<script>
// import UserThumbnail from '@/components/chat/UserThumbnail'
import moment from "moment";
import { mapState } from "vuex";

export default {
  name: "Message",

  data() {
    return {
    };
  },

  components: {
    // UserThumbnail
  },

  props: {
    message: {
      type: Object,
      default: null,
    },
  },

  computed: {
    ...mapState(["user", "locale", "channel"]),

    isUser() {
      return this.user.userId === this.message._sender.userId;
    },

    date() {
      moment.locale(this.locale);
      return moment(this.message.createdAt).format("LT");
    },
  },

  mounted() {},

  created() {
    // console.log("메시지 크리에이트");
    // console.log(this.message);
    // console.log(this.message.profileImg);
    // this.profileImg = this.message.profileImg;
  },
};
</script>
<style scoped lang="scss">
@import "../../assets/scss/index.scss";

.date {
  display: flex;
  align-items: flex-end;
}
</style>
