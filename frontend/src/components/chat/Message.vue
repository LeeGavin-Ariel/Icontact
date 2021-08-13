<template>
  <v-list
    id="messagesScroll"
    :class="{ 'is-reverse is-user': isUser }"
    class="message columns is-gapless"
  >
    <!-- class="message columns is-gapless overflow-y-auto"
    v-scroll:#scroll-target="onScroll" -->

    <!-- {{message._sender.nickname}} -->
    <!-- {{message.profileImg}} {{message.senderId}}<br/>  {{message.message}}}} -->

    <div class="item" :class="{ 'is-reverse is-user': isUser }">
      <v-avatar class="profile">
        <img
          :src="
            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
            message.profileImg
          "
        />
      </v-avatar>
      <div class="content">
        <div class="user">{{ message._sender.nickname }}</div>
        <div class="text">{{ message.message }}</div>
      </div>
      <div class="date">{{ date }}</div>
    </div>

    <!-- <v-card dense class="pr-1 ml-3 mr-3">
      <v-card-subtitle>
        <p>{{ message.message }}</p>
      </v-card-subtitle> -->
    <!-- <span>{{ message._sender.userId }}</span> -->
    <!-- </v-card>
    <div class="date">{{ date }}</div> -->

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
    return {};
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
// @import "../../assets/scss/index.scss";
#messagesScroll {
  background: none;
}
.date {
  display: flex;
  align-items: flex-end;
}
.item {
  display: flex;

  .user {
    padding: 0rem 0.5rem 0.3rem 0.5rem;
  }
  .text {
    display: inline;
    width: auto;
    // height: 2rem;
    background: white;
    // background: rgba(209, 219, 255, 0.5);
    // background: #fffbdb;
    margin-left: 0.5rem;
    padding: 0.5rem 1rem 0.3rem 1rem;
    font-size: 1.2rem;
    border-radius: 5px;
    align-self: flex-end;
    // align-content: flex-end;
  }
  .date {
    padding: 0rem 0.3rem;
    font-size: 0.6rem;
    font-family: inherit;
  }
}
.is-user {
  flex-direction: row-reverse;
  .content {
    text-align: right;
    align-self: flex-end;
  }
  .user{
    display: none;
  }
  .item .text {
    background: rgb(209, 219, 255, 0.5);
    // background: rgba(102, 122, 188, 0.23);
    // background: #fffbdb;
    // background: #FEF7D1;
    // background: yellow;
    // background: rgba(255, 255, 0, 0.8);
    margin-left: 0rem;
    margin-right: 0.5rem;
    justify-items: self-end;
    // text-align: right;
    // justify-content: flex-end;
  }
  .profile {
    margin: 0rem 1rem 0rem 0rem;
  }
}
</style>
