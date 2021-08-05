<template>
  <div class="channel-users">

    <h2>{{ opponent }}과의 대화</h2>

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
import sendBird from '@/services/SendBird.js'
import { mapState } from 'vuex'

export default {
  name: 'ChannelUsers',

  components: {
    // ChannelUser
  },

  data(){
    return{
      opponent:"",
    };
  },

  computed: {

    ...mapState([
      'channel',
      'channelUsers'
    ])

  },

  watch: {

    channel: {
      handler: function(newValue) {
        this.init(newValue)
      },
      deep: true
    }
  },

  mounted () {

    // this.init(this.channel)

  },

  methods: {
    init (channel) {
      sendBird
        .getChannelUsers(channel)
        .then((participantList) => {
          this.$store.commit('SET_CHANNEL_USERS', participantList)
          // console.log('this.channelUsers')
          // console.log(this.channelUsers)
        })
        .catch((error) => {
          console.error(error)
        })

      sendBird.onUserEntered(channel, (channel, user) => {
        this.$store.dispatch('addChannelUser', user)
      })

      sendBird.onUserExited(channel, (channel, user) => {
        this.$store.dispatch('removeChannelUser', user)
      })
      
      this.setData();
    },
    setData() {
      console.log("this.channelUsers");
      console.log(this.channelUsers);

      if (this.$store.state.user.userId == this.channelUsers[0].userId) {
        console.log("0번");
        this.opponent = this.channelUsers[1].nickname;
      } else {
        console.log("1번");
        this.opponent = this.channelUsers[0].nickname;
      }

      console.log(this.opponent);
    },
  }
}
</script>
