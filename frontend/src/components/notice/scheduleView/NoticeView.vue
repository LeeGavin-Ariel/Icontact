<template>
  <div style="overflow-y: scroll" class="col">
    공지
    <button @click="showCreateNoticeForm">이거클릭</button>
    |
    <!-- <button @click="updateNotice">연필</button> -->
    |
    <button v-if="detailMode == true" @click="showUpdateNoticeForm">
      글 수정1111
    </button>
    |
    <button v-if="detailMode" @click="deleteNotice">글 삭제</button>
    |
    <!-- <button @click="offCreateForm">글 작성 취소</button> -->

    <!-- <notice-detail v-if="detailMode"/> -->
    <notice-create v-if="this.createMode" />
    <notice-update v-if="this.updateMode" :noticeInfo="this.noticeDetail" />
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
    offCreateForm() {
      this.creating = 0;
    },

    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },

    // 생성창 띄우기
    async showCreateNoticeForm() {
      this.changeMode(true, false, false);

      // this.$emit("get-notebooklist", this.noticeType);
      // this.getNotice()
    },

    // 수정창 띄우기
    async showUpdateNoticeForm() {
      console.log("업데이트폼");
      this.changeMode(false, true, false);
    },

    // 수정창 띄우기
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
      // 선생인지, 학부모인지에 따라 다르게 받음.
      let result = await noticeApi.getNoticeDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);
      // 어떻게 날라오는지 확인후 데이터 집어넣기4
      this.noticeDetail = result.notice;
      console.log(this.noticeDetail);
    },

    // async updateNotice() {
    //   this.createMode=false;
    //   this.updateMode=true;

    // if (this.creating === 1) {
    //   this.creating = 0
    // }

    // if (this.updating === 0 ) {
    //     if (this.noticeType === 1) {
    //     this.symptom = this.noticeDetail.symptom
    //     this.medicineType = this.noticeDetail.medicineType
    //     this.dosageVol = this.noticeDetail.dosageVol
    //     this.dosageCnt = this.noticeDetail.dosageCnt
    //     this.dosageTime = this.noticeDetail.dosageTime
    //     this.storage = this.noticeDetail.storage
    //     this.specialNote = this.noticeDetail.specialNote
    //   }
    //   else if (this.noticeType === 2) {
    //     this.rhDate = this.noticeDetail.rhDate
    //     this.rhTime = this.noticeDetail.rhTime
    //     this.guardian = this.noticeDetail.guardian
    //     this.guardianTel = this.noticeDetail.guardianTel
    //     this.emergency = this.noticeDetail.emergency
    //     this.emergencyTel = this.noticeDetail.emergencyTel
    //   }
    //   this.updating = 1
    // }

    // // 여기서부터 다시 작성
    // else if (this.updating === 1) {
    //   let accessToken = sessionStorage.getItem('access-token')
    //   let refreshToken = sessionStorage.getItem('refresh-token')
    //   if (this.noticeType === 1) {
    //     let data = {
    //     noticeType: this.noticeType,
    //     userId: this.$store.state.user.userId,
    //     id: this.id,
    //     symptom: this.symptom,
    //     medicineType: this.medicineType,
    //     dosageVol: this.dosageVol,
    //     dosageCnt: this.dosageCnt,
    //     dosageTime: this.dosageTime,
    //     storage: this.storage,
    //     specialNote: this.specialNote,
    //     }
    //     let result = await noticeApi.updateNotice(data, {
    //       "access-token": accessToken,
    //       "refresh-token": refreshToken,
    //     });
    //     console.log(result)
    //   }
    //   else if (this.noticeType === 2) {
    //     let data = {
    //     noticeType: this.noticeType,
    //     userId: this.$store.state.user.userId,
    //     id: this.id,
    //     rhDate: this.rhDate,
    //     rhTime: this.rhTime,
    //     guardian : this.guardian,
    //     guardianTel: this.guardianTel,
    //     emergency: this.emergency,
    //     emergencyTel: this.emergencyTel,
    //     }
    //     let result = await noticeApi.updateNotice(data, {
    //       "access-token": accessToken,
    //       "refresh-token": refreshToken,
    //     });
    //     console.log(result)
    //   }
    //   this.updating = 0
    //   this.$emit('get-notebooklist', this.noticeType)
    //   // this.getNotice()
    // }
    // },

    async deleteNotice() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        noticeType: 1,
        id: this.noticeDetail.noticeId,
      };
      let result = await noticeApi.deleteNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result);

      // this.$emit("get-notebooklist", this.noticeType);
      alert('삭제되었습니다');
      window.location.reload()
    },
  },
};
</script>

<style scoped>
</style>