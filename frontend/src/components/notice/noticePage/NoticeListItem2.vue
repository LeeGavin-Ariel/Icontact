<template>
  <div class="item" v-bind="$attrs" v-on="$listeners" elevation="10">
    <div class="notice-img">
      <v-avatar size="70" class="profile-img mr-3">
        <img
          v-if="noticeInfo.noticeImgUrl"
          :src="
            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
            noticeInfo.noticeImgUrl
          "
        />
        <img
          v-else
          :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/profileImg/noImg_1628231352109.png'"
        />
      </v-avatar>
    </div>
    <div class="notice-text">
      <div class="notice-title">
        <span style="30px">{{ trimTitle(noticeInfo.title) }}</span>
      </div>
      <div class="notice-sub">
        <div class="notice-content">{{ trimContent(noticeInfo.content) }}</div>
        <div class="notice-date">{{ createDate }}</div>
      </div>
      <!-- <div class="notice-sub">
        <div class="notice-content">{{ noticeInfo.content }}</div>
        <div class="notice-date">{{ createDate }}</div>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "NoticeListItem",
  inheritAttrs: false,
  data() {
    return {
      createDate: "",
    };
  },

  props: {
    noticeInfo: {},
  },

  methods: {
    trimTitle(title) {
      if (title.length > 14) {
        title = title.substr(0, 14) + "...";
      }
      return title;
    },
    trimContent(content) {
      if (content.length > 20) {
        content = content.substr(0, 20) + "...";
      }
      return content;
    },
    refineDate() {
      this.createDate = this.noticeInfo.createDate.substr(0, 11);
    },
  },

  created() {
    this.refineDate();
  },
};
</script>

<style scoped>
.item {
  background: white;
  /* border-bottom: solid 0.5px #a8b1cf; */
  position: relative;
  /* justify-content: space-between; */
  /* align-self: center; */

  width: 100%;
  height: 5rem;
  border-bottom: #a8b1cf 0.1rem solid;
  margin-bottom: 0.1rem;
  /* border-radius: 20px; */

  display: flex;
}
.notice-img {
  padding-left: 1rem;
  align-self: center;
}
.notice-text {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
}
.notice-title {
  /* margin-top: 0.3rem; */
  padding-top: 0.4rem;
  margin-left: 0.5rem;
  /* margin-right: 1.5rem; */
  font-size: 1.5rem;
  width: 100%;
  display: flex;
  align-self: center;
  justify-content: flex-start;
}
.notice-sub {
  /* top: 0.5em; */
  /* height: 100%; */
  padding: 0.3rem 0.5rem 0rem 0.5rem;
  width: 100%;
  display: flex;
  /* position: absolute; */
  /* bottom: 0; */
  /* top: 2rem; */
  /* 아이템을 어떻게 배치할지 */
  justify-content: flex-start;
  /* align-items: flex-end; */
  align-content: flex-end;
}
.notice-content {
  /* margin-left: 0.5rem; */
  display: inline-block;
  width: 60%;
  /* align-self: flex-end; */
}
.notice-date {
  width: 40%;
  /* margin-right: 1.5rem; */
  display: inline-block;
  /* background-color: red; */
  /* display: inline-block; */
  text-align: right;

  /* display: flex; */
  /* justify-content: flex-end; */
}
</style>