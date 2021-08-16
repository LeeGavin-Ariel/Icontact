<template>
  <div class="row letter-back" style="margin: 0; background-color: rgba(102, 122, 188, 0.1);">
    <div class="mx-auto" style="padding-bottom: 0px; width: 38%">
      <!-- 공지 사항 리스트 -->
      <channel-list />
    </div>
    <v-col class="mx-auto">
      <channel style="width: 97%"/>
    </v-col>
  </div>
</template>

<script>
import Channel from "./Channel.vue";
import ChannelList from "./ChannelList.vue";
import { mapState } from "vuex";
export default {
  name: "ChatMain",
  components: {
    Channel,
    ChannelList,
  },
  data() {
    return {
      choiceChannel:{},
    };
  },
  computed: {
    ...mapState(["channel"]),
  },
  watch: {
    channel: {
      async handler(newValue) {
        console.log('채널 오픈')
        console.log(newValue)
        this.choiceChannel = newValue;
        // await this.init(newValue.url);
      },
    },
  },
  methods: {},

  async created() {
    console.log("메인 크리에이트");
    await this.$store.commit("SET_CHANNEL", null);
  },
};
</script>

<style scoped>
</style>