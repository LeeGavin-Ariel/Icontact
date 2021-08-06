<template>
  <div class="messages" id="messagesScroll">

    <loading :is-active="loadingIsActive"/>

    <!-- <h1 v-if="stateFlag">TEST</h1>
    <h1 >{{messages[0].createdAt}}</h1> -->

    <ul v-if="messages" ref="messagesList">
      <message
        v-for="(message, index) in transferMessage"
        :key="index"
        :message="message"/>
    </ul>

  </div>
</template>

<script>

// TODO:
// add alert message when all messages is loaded
// add day of the message if > current day like skype

import Loading from '@/components/chat/Loading.vue'
import Message from '@/components/chat/Message.vue'
import sendBird from '@/services/SendBird.js'
import { mapState, mapGetters, mapActions } from 'vuex'

export default {
  name: 'Channel',

  components: {
    Loading,
    Message
  },

  data () {
    return {
      loadingIsActive: false,
      allMessagesIsLoaded: false,
      stateFlag: false,
      transferMessage:[],
    }
  },

  computed: {

    ...mapState([
      'channel',
      'messages'
    ]),

    ...mapGetters([
      'earliestMessage'
    ]),

    ...mapActions([
      'addMessages'
    ])

  },

  watch: {

    messages: {
      handler: async function(newValue) {
        console.log("메시지스 : 메시지스 변경");
        console.log(newValue);
        this.transferMessage = newValue;
        newValue.forEach(element => {
          console.log(element._sender.nickname+":"+element.message);
        });
        if (newValue && this.$el.offsetHeight + this.$el.scrollTop === this.$el.scrollHeight) {

          this.$nextTick(() => {
            this.scrollToBottom()
          })
        }
        await this.getTeacherState();
      },
      deep: true
    },

    channel: {
      handler: function(newValue) {
        console.log("메시지스 채널 변경");
        this.init(newValue)
      }
    },
  },

  mounted () {

    // this.init(this.channel)

  },

  destroyed () {
    this.$el.removeEventListener('scroll', this.handleScroll)
  },

  methods: {

    async init (channel) {
      console.log('메시지스 이닛(채널)');
      console.log(channel==this.$store.state.channel);
      await sendBird
        .getChannelMessages(channel, 10)
        .then((messageList) => {
          this.$store.commit('SET_MESSAGES', messageList)
          this.scrollToBottom()
          console.log('메시지스 이닛 겟채널 리스트');
          console.log(messageList);
          console.log(this.$store.state.messages);
          console.log(this.$store.state.messages==messageList);
          messageList.forEach(element => {
            console.log(element._sender.userId+":"+element.message);
          });
          
        })
        .catch((error) => {
          console.error(error)
        })

      // sendBird.onMessageReceived(channel, (channel, message) => {
      //   this.$store.dispatch('addMessage', message)
      // })

      this.$nextTick(() => {
        this.$el.addEventListener('scroll', this.handleScroll)
      })
    },

    scrollToBottom () {
      console.log('scroll');
      this.$el.scrollTop = this.$refs.messagesList.offsetHeight
    },

    handleScroll () {

      console.log('scroll');
      const oldHeight = this.$refs.messagesList.offsetHeight

      if (this.$el.scrollTop === 0 && !this.allMessagesIsLoaded && this.messages.length > 0) {

        this.loadingIsActive = true

        sendBird
          // .getPreviousMessages(this.channel, this.earliestMessage.createdAt, 10)
          .getPreviousGroupMessages(this.channel)
          .then((messageList) => {
            if (messageList.length === 0) {
              this.loadingIsActive = false
              this.allMessagesIsLoaded = true
              return
            }

            // this.$store.dispatch('addMessages', messageList)
            this.$store.dispatch('loadPrevMessages', messageList)

            this.$nextTick(() => {
              this.$el.scrollTop = this.$refs.messagesList.offsetHeight - oldHeight
              this.loadingIsActive = false
            })

          })
          .catch((error) => {
            console.error(error)
          })
      }
    },

    

    getTeacherState() {
      sendBird
        .getChannelUsers(this.channel)
        .then((participantList) => {
          var opponent = null;
          participantList.forEach(el => {
            if(el.userId !== this.$store.state.user.userId) opponent = el;
          });
          if(opponent && opponent.nickname.includes("부모님") && opponent.connectionStatus == "offline"){ 
            this.stateFlag = true;
          }else this.stateFlag = false;          
        })
        .catch((error) => {
          console.error(error)
        })
    }
  }

}
</script>
<style scoped lang="scss">
  @import "../../assets/scss/index.scss";

// .overflow-y-auto::-webkit-scrollbar {
//   /* display: none; */
//   /* width: 0 !important; */
//   width: 3px;
// }

// /* 현재 스크롤의 위치바의 색 */
// .overflow-y-auto::-webkit-scrollbar-thumb {
//   background-color: black;
// }

// /* 남는공간의 색 */
// .overflow-y-auto::-webkit-scrollbar-track {
//   background-color: white;
// }

#messagesScroll::-webkit-scrollbar {
  width: 3px;
}

/* 현재 스크롤의 위치바의 색 */
#messagesScroll::-webkit-scrollbar-thumb {
  background-color: black;
}

/* 남는공간의 색 */
#messagesScroll::-webkit-scrollbar-track {
  background-color:white;
}
</style>
