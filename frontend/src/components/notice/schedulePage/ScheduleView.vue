<template>
  <div class="col">
    <div class="ml-5 mr-5 mt-5">
      <button
        class="writeBtn"
        @click="showCreateScheduleForm"
        v-if="!createMode & !updateMode & (this.$store.state.user.type == 2)"
      >
        <img src="@/assets/flaticon/write.png" style="width: 3.8rem" />
      </button>

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
        v-if="this.detailMode && this.scheduleDetail"
        :scheduleInfo="this.scheduleDetail"
        @showUpdateScheduleForm="showUpdateScheduleForm"
        @deleteSchedule="deleteSchedule"
      />
      <v-sheet rounded="lg" v-if="noDataMode">
        등록된 일정이 없습니다.
      </v-sheet>
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

    scheduleList: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      scheduleDetail: null,

      noDataMode: false,

      createMode: false,
      detailMode: false,
      updateMode: false,
    };
  },

  created() {
    console.log("scheduleList");
    if (this.scheduleList.length == 0) {
      console.log("scheduleList nodatamode");

      this.noDataMode = true;
    }
  },
  watch: {
    id: function () {
      if (this.id == -1) {
        this.scheduleDetail = null;
        console.log("글이 없습니다");
        this.changeMode(false, false, false, true);
        return;
      }

      if (this.id !== 0) {
        console.log("아이디가 변했어요" + this.id);
        this.getScheduleDetail();
      }
    },
    scheduleList(newValue) {
      console.log("길이");
      console.log(newValue);
      if (newValue.length == 0) {
        console.log("길이 0");
      }
    },
  },

  methods: {
    async deleteSchedule() {
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

      this.changeMode(false, false, true, false);
      this.$emit("updateSchedule", this.scheduleDetail.scheduleId);
    },
    //일정 작성 완료
    createSchedule() {
      this.changeMode(false, false, true, false);
      this.$emit("createSchedule");
    },
    //공지 작성 취소
    cancelCreateSchedule() {
      console.log("생성취소");
      this.changeMode(false, false, true, false);
    },
    //공지 수정 취소
    cancelUpdateSchedule() {
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
    async showCreateScheduleForm() {
      this.changeMode(true, false, false, false);
    },

    // 공지 수정창 띄우기
    async showUpdateScheduleForm() {
      console.log("업데이트폼");
      this.changeMode(false, true, false, false);
    },

    // 공지 상세창 띄우기
    async showDetailScheduleForm() {
      console.log("상세페이지폼");
      this.changeMode(false, false, true, false);
    },

    async getScheduleDetail() {
      this.changeMode(false, false, true, false);
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