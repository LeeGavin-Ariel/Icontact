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
        <div class="text-date">
          <div class="text">{{ message.message }}</div>
          <div class="date">{{ date }}</div>
        </div>
      </div>
    </div>
  </v-list>
</template>

<script>
import moment from "moment";
import { mapState } from "vuex";

export default {
  name: "Message",

  data() {
    return {};
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
};
</script>
<style scoped lang="scss">
#messagesScroll {
  background: none;
}
.date {
  display: flex;
  align-items: flex-end;
}
.item {
  display: flex;
  margin: 1rem 0rem 0rem 1rem;

  .user {
    padding: 0rem 0.5rem 0.3rem 0.5rem;
    font-size: 0.9rem;
  }
  .text {
    display: inline;
    background: white;
    margin-left: 0.5rem;
    padding: 0.2rem 0.5rem 0.2rem 0.5rem;
    font-size: 1.1rem;
    border-radius: 5px;
    align-self: flex-end;
  }
  .content {
    text-align: left;
    align-self: flex-start;
  }
  .text-date {
    display: flex;
    .text {
      max-width: 20vw;
      word-break: break-all;
      text-align: left;
    }
  }
  .date {
    padding: 0rem 0.3rem;
    font-size: 0.7rem;
    font-family: inherit;
  }
}
.is-user {
  flex-direction: row-reverse;
  .content {
    text-align: right;
    align-self: flex-end;
  }
  .text-date {
    flex-direction: row-reverse;
    .text {
      max-width: 20vw;
      word-break: break-all;
      text-align: left;
    }
  }
  .user {
    display: none;
  }
  .item .text {
    background: rgb(209, 219, 255, 0.5);
    margin-left: 0rem;
    margin-right: 0.5rem;
    justify-items: self-end;
  }
  .profile {
    margin: 0rem 1rem 0rem 0rem;
  }
}
</style>
