<template>
  <div class="message-sender">
    <div class="columns is-gapless is-centered">

      <div class="column">
        <textarea v-model="message" placeholder="메시지를 입력하세요" @keyup.enter="send(message)"/>
      </div>

      <div class="column is-1 text">
        <div @click="send(message)">
          <icon name="paper-plane"/>
        </div>
      </div>

    </div>
  </div>
</template>

<script>

import sendBird from '@/services/SendBird.js'
import { mapState } from 'vuex'
import striptags from 'striptags'

export default {
  name: 'Message',

  data () {
    return {
      message: ''
    }
  },

  computed: {

    ...mapState([
      'channel'
    ])

  },

  methods: {
    send(message) {

      if (!message) return
      console.log('tesetsdafsdfasdf');
      console.log(this.channel)
      this.$store.commit('SET_CHANNEL', this.channel)

      sendBird
        .sendMessage(this.channel, striptags(message))
        .then((message) => {
          this.$store.dispatch('addMessage', message)
          this.message = ''
          this.updateChannel();
        })
        .catch((error) => {
          console.error(error)
        })

    },

    updateChannel() {
      sendBird
        .getGroupChannelList()
        .then(async channels => {
          await this.$store.commit('SET_CHANNELS', channels)
        })
        .catch((error) => {
          console.error(error)
        })
    },
  }

}
</script>
