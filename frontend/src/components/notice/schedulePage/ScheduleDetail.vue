<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode && this.scheduleInfo">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">

        <div align="right" class="mt-2"> 
          <button v-if="this.$store.state.user.type == 2" @click="showUpdateScheduleForm" class="mr-2 update-delete-btn">수정</button>
          <button v-if="this.$store.state.user.type == 2" @click="deleteSchedule" class="ml-2 mr-2 update-delete-btn">삭제</button>
        </div>
        <div class="section-title">
        </div>
        <div align="center" class="mb-5">

          <div class="col-lg-11">
            <table class="table">
              <tbody>
                  <tr class="d-flex">
                      <td class="col-12">
                        <div style="font-size:35px"><strong>{{this.scheduleInfo.title}}</strong></div>
                        <div class="d-flex align-items-center mt-3 mb-2">
                          <div>{{this.scheduleInfo.writerName}} 선생님</div>
                        </div>
                      </td>
                  </tr>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3" style="min-height: 400px;">
                      <div class="mb-5">
                        <img style="max-width: 100%" v-if="scheduleInfo.scheduleImgUrl" :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ scheduleInfo.scheduleImgUrl" alt="profile-image">
                      </div>
                      <div v-html="contentReplace(this.scheduleInfo.content)"></div>
                          
                    </td>
                  </tr>
              </tbody>
            </table>
          </div>
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

.update-delete-btn {
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}
</style>