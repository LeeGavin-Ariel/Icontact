<template>
  <li class="channel-list-item" @click="openChannel(url)">

    <div
      v-if="coverUrl"
      class="channel-list-item_thumb">

      <img :src="coverUrl" >

    </div>

    <span v-if="this.$store.state.user.userId==members[0].userId">{{members[1].nickname}} {{members[1].connectionStatus}}  {{getLastMessage()}} {{getCreatedAt()}}</span> 
    <span v-if="this.$store.state.user.userId==members[1].userId">{{members[0].nickname}}  {{members[0].connectionStatus}}  {{getLastMessage()}} {{getCreatedAt()}}</span>

  </li>
</template>

<script>

import sendBird from '@/services/SendBird.js'

export default {
  name: 'ChannelListItem',

  props: {
    coverUrl: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    url: {
      type: String,
      required: true
    },
    lastMessage:{
      type:Object,      
    },
    members: []
  },

  methods: {
    openChannel (url) {
      sendBird
        .getChannel(url)
        .then((channel) => {
          this.$store.commit('SET_CHANNEL', channel)
        })
        .catch((error) => {
          console.error(error)
        })
    },

    toStringByFormatting(source, delimiter = '/') {
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

    getLastMessage(){
      if(this.lastMessage===null){
        return '';
      }else{
        return this.lastMessage.message;
      }
    },

    getCreatedAt(){
      if(this.lastMessage===null){
        return '';
      }else{
        return this.toStringByFormatting(this.lastMessage.createdAt);
      }
    },



  }

}
</script>
