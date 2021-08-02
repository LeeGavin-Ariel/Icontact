<template>
  <div class="app">

    <div class="columns is-gapless">

      <div class="column is-2">
        <div class="aside">
          <channel-list/>

        </div>
      </div>

      <channel class="content column"/>

    </div>

  </div>
</template>

<script>

import Channel from '@/components/chat/Channel.vue'
import ChannelList from '@/components/chat/ChannelList.vue'
import sendBird from '@/services/SendBird.js'
import { mapState } from 'vuex'

// TODO: user channel or empty channel

export default {
  name: 'App',

  components: {
    Channel,
    ChannelList
  },

  computed: {
    ...mapState([
      'user',
      'channels'
    ])
  },

  methods:{
  
  },

  created () {

    //사용자가 페이지를 떠날때 발생하는 이벤트
    // 떠날때 채널을 나간다.
    window.onbeforeunload = function() {
      sendBird.exitChannel('vuejs')
    }
    
    // console.log('this.$store.state.user.userId');
    // console.log(this.$store.state.user);

    // sendBird
    // .login(this.$store.state.user.userId)
    // .then(async user => {
    //   await this.$store.commit('SET_USER', user)
    // })
    // .catch(error => {
    //   // this.userId = ''
    //   this.message = error.message
    //   console.log(error);
    // })
    
  }

}
</script>
