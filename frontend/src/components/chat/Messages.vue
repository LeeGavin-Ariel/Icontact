<template>
  <div class="messages">

    <loading :is-active="loadingIsActive"/>

    <h1 v-if="stateFlag">TEST</h1>

    <ul v-if="messages" ref="messagesList">
      <message
        v-for="(message, index) in messages"
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
      stateFlag: false
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

    init (channel) {

      sendBird
        .getChannelMessages(channel, 10)
        .then((messageList) => {
          this.$store.commit('SET_MESSAGES', messageList)
          this.scrollToBottom()
        })
        .catch((error) => {
          console.error(error)
        })

      sendBird.onMessageReceived(channel, (channel, message) => {
        this.$store.dispatch('addMessage', message)
      })

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
