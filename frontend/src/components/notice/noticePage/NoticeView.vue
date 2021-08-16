<template>
  <div class="col">
    <div class="ml-5 mr-5 mt-5">
      <button
        class="writeBtn"
        v-if="!createMode & !updateMode & (this.$store.state.user.type == 2)"
        @click="showCreateNoticeForm"
      >
        <img src="@/assets/flaticon/write.png" style="width: 3.8rem" />
      </button>

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
      <notice-detail
        v-if="this.detailMode && this.noticeDetail"
        :noticeInfo="this.noticeDetail"
        @showUpdateNoticeForm="showUpdateNoticeForm"
        @deleteNotice="deleteNotice"
      />
      <div v-if="noDataMode" style="font-size: 1.2rem; text-align:center;">
          등록된 공지가 없습니다.
      </div>
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
    noticeList: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,

      noDataMode: false,

      createMode: false,
      detailMode: false,
      updateMode: false,
    };
  },

  created() {
    if (this.noticeList.length == 0) {
      this.noDataMode = true;
    }
  },
  watch: {
    id: function () {
      if (this.id == -1) {
        this.noticeDetail = null;
        this.changeMode(false, false, false, true);
        return;
      }

      if (this.id !== 0) {
        this.getNoticeDetail();
      }
    },
  },

  methods: {
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

      if (!choice.value) {
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

      this.$emit("deleteNotice");
    },

    //공지 업데이트 완료
    updateNotice() {
      this.getNoticeDetail();

      this.changeMode(false, false, true, false);
      this.$emit("updateNotice", this.noticeDetail.noticeId);
    },
    //공지 작성 완료
    createNotice() {
      this.changeMode(false, false, true, false);
      this.$emit("createNotice");
    },
    //공지 작성 취소
    cancelCreateNotice() {
      this.changeMode(false, false, true, false);
    },
    //공지 수정 취소
    cancelUpdateNotice() {
      this.changeMode(false, false, true, false);
    },
    //공지 (작성,수정,상세) 모드 변경
    changeMode(create, update, detail, noData) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
      this.noDataMode = noData;
    },
    // 공지 작성창 띄우기
    async showCreateNoticeForm() {
      this.changeMode(true, false, false, false);
    },

    // 공지 수정창 띄우기
    async showUpdateNoticeForm() {
      this.changeMode(false, true, false, false);
    },

    // 공지 상세창 띄우기
    async showDetailNoticeForm() {
      this.changeMode(false, false, true, false);
    },

    async getNoticeDetail() {
      this.changeMode(false, false, true, false);
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
    },
  },
};
</script>

<style scoped>
.writeBtn {
  position: fixed;
  right: 60px;
  bottom: 65px;
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