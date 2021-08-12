<template>
  <div class="item" v-bind="$attrs" v-on="$listeners" elevation="10">
    <!-- <div class="notice-img">
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
    </div> -->
    <div class="notice-text">
      <div class="notice-user">
        <span>[{{noticeInfo.userName }} 선생님] </span>
      </div>
      <div class="notice-title">
        <span>[공지사항] {{ trimTitle(noticeInfo.title) }}</span>
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
      if (title.length > 20) {
        title = title.substr(0, 20) + "...";
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
  /* border-radius: 10px; */
  /* box-shadow: 1px 0px 1px 0px black; */
  /* background: white; */
  /* border-bottom: solid 0.5px #a8b1cf; */
  position: relative;
  /* justify-content: space-between; */
  /* align-self: center; */

  width: 100%;
  height: auto;
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
.notice-user {
  /* margin-top: 0.3rem; */
  padding-top: 0.4rem;
  padding-left: 1rem;
  /* margin-right: 1.5rem; */
  font-size: 0.8rem;
  width: 100%;
  display: flex;
  align-self: center;
  justify-content: flex-start;
}
.notice-title {
  /* margin-top: 0.3rem; */
  /* background: ; */
  padding-top: 0.5rem;
  padding-left: 1rem;
  /* margin-right: 1.5rem; */
  font-family: "NanumSquareRound";
  font-weight: 900;
  font-size: 1.2rem;
  width: 100%;
  display: flex;
  align-self: center;
  justify-content: flex-start;
}
.notice-sub {
  /* top: 0.5em; */
  /* height: 100%; */
  /* background:; */
  padding: 0.2rem 0.5rem 0.3rem 1rem;
  width: 100%;
  display: flex;
  /* position: absolute; */
  /* bottom: 0; */
  /* top: 2rem; */
  /* 아이템을 어떻게 배치할지 */
  /* justify-content: flex-start; */
  /* align-items: flex-end; */
  /* align-content: flex-end; */
}
.notice-content {
  /* margin-left: 0.5rem; */
  color: rgb(150, 150, 150);
  display: inline-block;
  width: 60%;
  font-size: 1rem;
  text-align: left;
  /* align-self: flex-end; */
}
.notice-date {
  width: 40%;
  /* margin-right: 1.5rem; */

  font-size: 0.8rem;
  display: inline-block;
  /* background-color: red; */
  /* display: inline-block; */
  text-align: right;

  align-self: flex-end;
  /* display: flex; */
  /* justify-content: flex-end; */
}
</style>