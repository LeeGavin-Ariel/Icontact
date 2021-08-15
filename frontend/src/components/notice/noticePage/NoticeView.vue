<template>
  <div class="col">
    <div class="ml-5 mr-5 mt-5">
      <!-- <div style="margin-top:2.5rem;"></div> -->
      <!-- <v-btn @click="showCreateNoticeForm">새 글</v-btn> -->
      <button
        class="writeBtn"
        v-if="!createMode & !updateMode & (this.$store.state.user.type == 2)"
        @click="showCreateNoticeForm"
      >
        <img src="@/assets/flaticon/write.png" style="width: 3.8rem" />
      </button>

      <notice-create
        v-if="this.createMode"
        :createStart="createStart"
        @cancelCreateNotice="cancelCreateNotice"
        @createNotice="createNotice"
      />
      <notice-update
        v-if="this.updateMode"
        :noticeInfo="this.noticeDetail"
        :updateStart="updateStart"
        @cancelUpdateNotice="cancelUpdateNotice"
        @updateNotice="updateNotice"
      />
      <notice-detail
        v-if="this.detailMode && this.noticeDetail"
        :noticeInfo="this.noticeDetail"
        @showUpdateNoticeForm="showUpdateNoticeForm"
        @deleteNotice="deleteNotice"
      />
    </div>
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
import NoticeDetail from "./NoticeDetail.vue";
import NoticeCreate from "./NoticeCreate.vue";
import NoticeUpdate from "./NoticeUpdate.vue";
import awss3 from "@/utils/awss3.js";
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
      updateStart: false,
      createStart: false,
    };
  },
  watch: {
    id: function () {
      if (this.id == -1) {
        this.noticeDetail = null;
        console.log("글이 없습니다");
        return;
      }

      if (this.id !== 0) {
        console.log("아이디가 변했어요오오" + this.id);
        this.getNoticeDetail();
      }
    },
  },

  methods: {
    propUpdateNotice() {
      this.updateStart = !this.updateStart;
    },
    propCreateNotice() {
      this.createStart = !this.createStart;
    },
    //공지 삭제
    async deleteNotice() {
      let choice = await this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">정말로 삭제하시겠습니까?</p>`,
        type: "question",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#58679A",
      });

      // alert로 바꿔주세요.
      if (!choice.value) {
        console.log("삭제안함.");
        return;
      }

      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let PhotoKey = this.noticeDetail.noticeImgUrl;
      await awss3.deletePhoto([PhotoKey], "");

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
    updateNotice() {
      console.log("에밋 view111");
      console.log(this.noticeDetail);
      this.getNoticeDetail();

      this.changeMode(false, false, true);
      this.$emit("updateNotice", this.noticeDetail.noticeId);
    },
    //공지 작성 완료
    createNotice() {
      this.changeMode(false, false, true);
      this.$emit("createNotice");
    },
    //공지 작성 취소
    cancelCreateNotice() {
      console.log("생성취소");
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
.writeBtn {
  position: fixed;
  right: 60px;
  bottom: 50px;
  width: 3.8rem;
}
.notice-detail-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
.notice-update-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
.notice-create-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
</style>