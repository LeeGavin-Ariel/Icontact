<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode && this.scheduleInfo">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">
        <div class="notice-detail-top">
          <div class="notice-type">일정</div>
          <div class="btn-wrapper" v-if="this.$store.state.user.type == 2">
            <button
              @click="showUpdateScheduleForm"
              class="mr-2 update-delete-btn"
            >
              수정
            </button>
            <button @click="deleteSchedule" class="ml-2 mr-2 update-delete-btn">
              삭제
            </button>
          </div>
        </div>
        <div class="notice-detail-title">{{ this.scheduleInfo.title }}</div>
        <!-- <div class="notice-detail-writer">{{this.noticeInfo.userName}}선생님 </div> -->

        <div class="notice-detail-content-container">
          <div class="notice-detail-img" v-if="scheduleInfo.scheduleImgUrl">
            <img
              v-if="scheduleInfo.scheduleImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                scheduleInfo.scheduleImgUrl
              "
              alt="profile-image"
            />
          </div>
          <div
            class="notice-detail-content"
            v-html="contentReplace(this.scheduleInfo.content)"
          ></div>
          <!-- <div v-html="contentReplace(this.noticeInfo.content)">
          </div> -->
        </div>
        <div class="notice-detail-date">
          작성일 : {{ this.scheduleInfo.createDate }}
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
    scheduleInfo: {},
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
    showUpdateScheduleForm() {
      console.log("오픈업데이트폼");
      this.$emit("showUpdateScheduleForm");
    },
    deleteSchedule() {
      console.log("삭제");
      this.$emit("deleteSchedule");
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
    async showCreateScheduleForm() {
      console.log("눌렀는데");
      this.changeMode(true, false, false);

      // this.$emit("get-notebooklist", this.noticeType);
      // this.getNotice()
    },

    // async getNoticeDetail() {
    //   let accessToken = sessionStorage.getItem("access-token");
    //   let refreshToken = sessionStorage.getItem("refresh-token");
    //   let data = {
    //     noticeType: this.noticeType,
    //     id: this.id,
    //   };
    //   // 선생인지, 학부모인지에 따라 다르게 받음.
    //   let result = await noticeApi.getNoticeDetail(data, {
    //     "access-token": accessToken,
    //     "refresh-token": refreshToken,
    //   });
    //   // 어떻게 날라오는지 확인후 데이터 집어넣기4
    //   this.noticeDetail = result.notice;

    //   console.log('this.noticeDetail');
    //   console.log(this.noticeDetail);
    // },
  },
};
</script>

<style scoped>
/* 스크롤 */
.content-container{
  overflow-y:scroll; 
  height:80vh; 
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233,234,239, 0.5);
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233,234,239, 0.5);
  border-radius: 1px;
}

.notice-detail-top {
  width: 95%;
  display: flex;
  justify-content: space-between;
  /* padding-left: 1rem; */
  margin-bottom: 1rem;
  font-size: 1.2em;
  font-family: "NanumSquareRound";
  font-weight: bold;
}
.notice-type{
  width: 100%;
  display: inline-block;
}

.notice-detail-title {
  padding-bottom: 0.5rem;
  /* margin-bottom: 0.5rem; */
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 1.5em;
  font-family: "NanumSquareRound";
  font-weight: 900;
  /* border-bottom: #a8b1cf 0.1rem solid; */
}

.notice-detail-content-container {
  width: 95%;
  min-height: 50vh;
  display: flex;
  justify-content: center;
  align-content: flex-start;
  flex-wrap: wrap;
  border-radius: 5px;
  border-top: #a8b1cf 0.1rem solid;
  border-bottom: #a8b1cf 0.1rem solid;
}

.notice-detail-content {
  width: 100%;
  /* min-height: 15rem; */
  padding: 1rem;
  margin-bottom: 1rem;
  display: flex;
  /* justify-content: left; */
  /* border-radius: 5px;
  border-top: #a8b1cf 0.1rem solid;
  border-bottom: #a8b1cf 0.1rem solid; */
}

.notice-detail-img {
  width: 40%;
  min-height: 15rem;
  padding: 1rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: left;
}
.noticeImg {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
  /* margin-bottom: 2rem; */
}

.notice-detail-date {
  width: 95%;
  display: flex;
  justify-content: flex-end;
  /* border-bottom: #a8b1cf 0.1rem solid; */
  padding-right: 0.5rem;
  font-size: 0.8rem;
  /* margin-bottom: 1rem; */
  /* position: relative; */
  /* right: 0.5rem; */
}

.btn-wrapper {
  /* align-content: flex-end; */
  /* justify-content: flex-end; */
  width: 100%;
  text-align: right;
  /* margin-bottom: 1rem; */
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
/* @media all and (max-width: 1000px) {
  .container { font-size: 10px ; } 
} */
</style>