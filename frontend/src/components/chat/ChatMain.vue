<template>
  <div class="row mt-5 mb-5" style="width: 100vw; margin: 0">
    <div
      class="col-4 mx height:100vh"
      style="padding-bottom: 0px; border-right: 1px solid"
    >
   
      <channel-list />
    </div>
    <v-col class="mx height:100vh">
      <channel />
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
</style>