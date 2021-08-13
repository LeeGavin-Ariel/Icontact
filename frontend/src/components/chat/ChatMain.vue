<template>
  <div class="row letter-back" style="margin: 0">
    <div
      class="mx-auto"
      style="padding-bottom: 0px; width: 38%;"
    >
   
      <channel-list />
    </div>
    <v-col class="mx height:100vh">
      <channel style="width: 97.5%;"/>
    </v-col>
  </div>
</template>

<script>
import Channel from "./Channel.vue";
import ChannelList from "./ChannelList.vue";
export default {
  name: "ChatMain",
  components: {
    Channel,
    ChannelList,
  },
  data() {
    return {};
  },
  methods: {},

  created() {},
};
</script>

<style>
/* 스크롤바 너비 */
#noticeListContainer::-webkit-scrollbar {
  width: 3px;
}

/* 현재 스크롤의 위치바의 색 */
#noticeListContainer::-webkit-scrollbar-thumb {
  background-color: white;
}

/* 남는공간의 색 */
#noticeListContainer::-webkit-scrollbar-track {
  background-color: black;
}
.letter-back{
  background-color:rgba(102,122,188, 0.1);
  background-size: 100% 100%;
}
</style>