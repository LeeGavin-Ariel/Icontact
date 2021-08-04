<template>
  <div style="overflow-y: scroll" class="col">
    <v-spacer></v-spacer>
    <!-- <v-btn @click="showCreateNoticeForm">새 글</v-btn> -->
    <v-fab-transition>
      <v-btn
        color="red"
        fab
        small
        dark
        bottom
        left
        class="v-btn--example"
        @click="showCreateNoticeForm"
        v-if="!createMode"
      >
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </v-fab-transition>

    <button v-if="detailMode" @click="showUpdateNoticeForm">|글 수정</button>

    <button v-if="detailMode" @click="deleteNotice">|글 삭제</button>

    <!-- <button @click="offCreateForm">글 작성 취소</button> -->

    <!-- <notice-detail v-if="detailMode"/> -->
    <notice-create
      v-if="this.createMode"
      @cancelCreateNotice="cancelCreateNotice"
      @createNotice="createNotice"
    />
    <notice-update
      v-if="this.updateMode"
      :noticeInfo="this.noticeDetail"
      @cancelUpdateNotice="cancelUpdateNotice"
      @updateNotice="updateNotice"
    />
    <notice-detail v-if="this.detailMode" :noticeInfo="this.noticeDetail" />
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
import NoticeDetail from "./NoticeDetail.vue";
import NoticeCreate from "./NoticeCreate.vue";
import NoticeUpdate from "./NoticeUpdate.vue";
export default {
  name: "NoticeIndex",
  components: {
    NoticeDetail,
    NoticeCreate,
    NoticeUpdate,
  },
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
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,

      createMode: false,
      detailMode: false,
      updateMode: false,
    };
  },
  watch: {
    id: function () {
      console.log("아이디가 변했어요" + this.id);
      if (this.id !== 0) {
        this.getNoticeDetail();
      }
    },
  },

  methods: {
    //공지 삭제
    async deleteNotice(){
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      
      let data = {
        noticeType: 1,
        id: this.id,
      };

      let result = await noticeApi.deleteNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log(result);
      
      this.$emit("deleteNotice");
    },

    //공지 업데이트 완료
    updateNotice(){
      console.log('에밋 view111');
      console.log(this.noticeDetail);
      this.getNoticeDetail();

      this.changeMode(false, false, true);
      this.$emit("updateNotice", this.noticeDetail.noticeId);
    },
    //공지 작성 완료
    createNotice(){
      this.changeMode(false, false, true);
      this.$emit("createNotice");
    },
    //공지 작성 취소
    cancelCreateNotice() {
      console.log('생성취소');
      this.changeMode(false, false, true);
    },
    //공지 수정 취소
    cancelUpdateNotice() {
      this.changeMode(false, false, true);
    },
    //공지 (작성,수정,상세) 모드 변경
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },
    // 공지 작성창 띄우기
    async showCreateNoticeForm() {
      this.changeMode(true, false, false);
    },

    // 공지 수정창 띄우기
    async showUpdateNoticeForm() {
      console.log("업데이트폼");
      this.changeMode(false, true, false);
    },

    // 공지 상세창 띄우기
    async showDetailNoticeForm() {
      console.log("상세페이지폼");
      this.changeMode(false, false, true);
    },

    async getNoticeDetail() {
      this.changeMode(false, false, true);
      console.log("공지상세요청간다");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        noticeType: 1,
        id: this.id,
      };

      let result = await noticeApi.getNoticeDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.noticeDetail = result.notice;
      console.log(this.noticeDetail);
    },
  },
};
</script>

<style scoped>
</style>