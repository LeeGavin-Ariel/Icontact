<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <!-- 공지 상세페이지 <br /> -->
    <v-sheet rounded="lg" v-if="createMode && this.noticeInfo" >
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container" style="overflow-y: scroll">
        <div class="btn-wrapper" v-if="this.$store.state.user.type == 2">
          <button @click="showUpdateNoticeForm" class="mr-2 update-delete-btn">
            수정
          </button>
          <button @click="deleteNotice" class="ml-2 mr-2 update-delete-btn">
            삭제
          </button>
        </div>
        <div class="notice-detail-title">{{ this.noticeInfo.title }}</div>
        <!-- <div class="notice-detail-writer">{{this.noticeInfo.userName}}선생님 </div> -->
        <div class="notice-detail-date">{{ this.noticeInfo.createDate }}</div>
        <div class="notice-detail-img">
          <img
            v-if="noticeInfo.noticeImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              noticeInfo.noticeImgUrl
            "
            alt="profile-image"
          />
        </div>
        <div class="notice-detail-content-container">
          <!-- <div class="notice-detail-content"> -->
          <div
            class="notice-detail-content"
            v-html="contentReplace(this.noticeInfo.content)"
          ></div>
          <!-- <div v-html="contentReplace(this.noticeInfo.content)">
          </div> -->
        </div>
      </div>
    </v-sheet>
  </div>
</template>

<script>
// import noticeApi from "@/api/notice.js";
export default {
  name: "NoticeDetail",

  props: {
    identity: {
      identity: Number,
    },
    noticeType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
    noticeInfo: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,
      createMode: true,
      updateMode: false,
    };
  },
  watch: {
    // id: function () {
    //   console.log('noticedetail');
    //   if (this.id !== 0) {
    //     this.getNoticeDetail();
    //   }
    // },
  },

  methods: {
    showUpdateNoticeForm() {
      console.log("오픈업데이트폼");
      this.$emit("showUpdateNoticeForm");
    },
    deleteNotice() {
      console.log("삭제");
      this.$emit("deleteNotice");
    },
    contentReplace(content) {
      // 줄바꿈
      return content.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },

    // 생성창 띄우기
    async showCreateNoticeForm() {
      console.log("눌렀는데");
      this.changeMode(true, false, false);
    },
  },

  created() {
    console.log(this.noticeInfo);
  },
};
</script>

<style scoped>
.container {
  display: flex;
  height: 84.6vh;
  /* flex-direction: column; */
  flex-wrap: wrap;
  justify-content: center;
  align-content: flex-start;
}
/* 스크롤바 너비 */
.container::-webkit-scrollbar {
  width: 5px;
}

/* 현재 스크롤의 위치바의 색 */
.container::-webkit-scrollbar-thumb {
  background-color: black;
}

/* 남는공간의 색 */
.container::-webkit-scrollbar-track {
  background-color: white;
}
.notice-detail-title {
  /* margin: 0.3rem 0px; */
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 2em;
  border-bottom: #a8b1cf 0.1rem solid;
}
.notice-detail-writer {
  width: 95%;
  /* display: ; */
  /* justify-content: flex-end; */
  /* border-bottom: #a8b1cf 0.1rem solid; */
  /* padding-right: 0.5rem; */
  /* margin-bottom: 1rem; */
  /* position: relative; */
  /* right: 0.5rem; */
}
.notice-detail-date {
  width: 95%;
  display: flex;
  justify-content: flex-end;
  border-bottom: #a8b1cf 0.1rem solid;
  padding-right: 0.5rem;
  margin-bottom: 1rem;
  /* position: relative; */
  /* right: 0.5rem; */
}
.notice-detail-content-container {
  width: 100%;
  display: flex;
  justify-content: center;
}
.notice-detail-content {
  width: 95%;
  min-height: 5rem;
  padding: 0px 0.5rem 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: left;
  border-bottom: #a8b1cf 0.1rem solid;
}
.noticeImg {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}
.btn-wrapper {
  /* align-content: flex-end; */
  /* justify-content: flex-end; */
  width: 100%;
  text-align: right;
  margin-bottom: 1rem;
}
.update-delete-btn {
  /* justify-content: flex-end; */
  /* display: inline; */
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}
</style>