<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    일정 상세페이지 <br />
    <!-- <v-btn @click="showCreateNoticeForm">새 글 작성</v-btn>
    
    <v-btn @click="showUpdateNoticeForm">글 수정</v-btn> -->

    <v-sheet rounded="lg" v-if="createMode && this.scheduleInfo">
      <br />
      <p>
        제목 : {{this.scheduleInfo.title}}
      </p>

      <p>내용</p>
       {{this.scheduleInfo.content}}
      <!-- <p>작성 일자 : </p>
      <input type="number" v-model="createDate"> -->
      <br/><br/>
      <p>첨부사진 :</p>
      <div v-if="scheduleInfo.scheduleImgUrl">
      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ scheduleInfo.scheduleImgUrl" alt="profile-image">
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
    scheduleInfo:{}
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
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },

     // 생성창 띄우기
    async showCreateNoticeForm() {
      console.log('눌렀는데');
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
</style>