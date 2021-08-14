<template>
  <div class="col">

    <div class="ml-5 mr-5 mt-5">

    <button class="writeBtn" @click="showCreateScheduleForm" v-if="!createMode & !updateMode & (this.$store.state.user.type == 2)"><img src="@/assets/flaticon/write.png" style="width:3.8rem"></button>

    <!-- <schedule-detail v-if="detailMode"/> -->
    <schedule-create
      v-if="this.createMode"
      @cancelCreateSchedule="cancelCreateSchedule"
      @createSchedule="createSchedule"
    />
    <schedule-update
      v-if="this.updateMode"
      :scheduleInfo="this.scheduleDetail"
      @cancelUpdateSchedule="cancelUpdateSchedule"
      @updateSchedule="updateSchedule"
    />
    <schedule-detail
      v-if="this.detailMode"
      :scheduleInfo="this.scheduleDetail"
      @showUpdateScheduleForm="showUpdateScheduleForm"
      @deleteSchedule="deleteSchedule"
    />
  </div>
  </div>
</template>

<script>
import scheduleApi from "@/api/schedule.js";
import ScheduleDetail from "./ScheduleDetail.vue";
import ScheduleCreate from "./ScheduleCreate.vue";
import ScheduleUpdate from "./ScheduleUpdate.vue";
import awss3 from "@/utils/awss3.js";
export default {
  name: "ScheduleIndex",
  components: {
    ScheduleDetail,
    ScheduleCreate,
    ScheduleUpdate,
  },
  props: {
    identity: {
      identity: Number,
    },
    scheduleType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      scheduleDetail: null,

      createMode: false,
      detailMode: false,
      updateMode: false,
    };
  },
  watch: {
    id: function () {
      console.log("아이디가 변했어요" + this.id);
      if (this.id !== 0) {
        this.getScheduleDetail();
      }
    },
  },

  methods: {
    async deleteSchedule() {
      console.log("삭제시작");

      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let PhotoKey = this.scheduleDetail.scheduleImgUrl;
      await awss3.deletePhoto([PhotoKey], "");

      let data = {
        scheduleType: 2,
        id: this.id,
      };

      let result = await scheduleApi.deleteSchedule(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log(result);

      this.$emit("deleteSchedule");
    },

    //일정 업데이트 완료
    updateSchedule() {
      console.log("에밋 view111");
      console.log(this.scheduleDetail);
      this.getScheduleDetail();

      this.changeMode(false, false, true);
      this.$emit("updateSchedule", this.scheduleDetail.scheduleId);
    },
    //일정 작성 완료
    createSchedule() {
      this.changeMode(false, false, true);
      this.$emit("createSchedule");
    },
    //공지 작성 취소
    cancelCreateSchedule() {
      console.log("생성취소");
      this.changeMode(false, false, true);
    },
    //공지 수정 취소
    cancelUpdateSchedule() {
      this.changeMode(false, false, true);
    },
    //공지 (작성,수정,상세) 모드 변경
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },
    // 공지 작성창 띄우기
    async showCreateScheduleForm() {
      this.changeMode(true, false, false);
    },

    // 공지 수정창 띄우기
    async showUpdateScheduleForm() {
      console.log("업데이트폼");
      this.changeMode(false, true, false);
    },

    // 공지 상세창 띄우기
    async showDetailScheduleForm() {
      console.log("상세페이지폼");
      this.changeMode(false, false, true);
    },

    async getScheduleDetail() {
      this.changeMode(false, false, true);
      console.log("일정상세요청간다");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        scheduleType: 2,
        id: this.id,
      };

      let result = await scheduleApi.getScheduleDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.scheduleDetail = result.schedule;
      console.log("this.scheduleDetail");
      console.log(this.scheduleDetail);
    },
  },
};
</script>

<style scoped>
.writeBtn {
  position: fixed;
  right: 60px;
  bottom: 50px;
  width:3.8rem;
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