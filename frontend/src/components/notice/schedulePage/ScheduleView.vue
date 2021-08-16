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
      <div v-if="noDataMode" style="font-size: 1.2rem; text-align:center;">
          등록된 일정이 없습니다.
      </div>
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
    if (this.scheduleList.length == 0) {
      this.noDataMode = true;
    }
  },
  watch: {
    id: function () {
      if (this.id == -1) {
        this.scheduleDetail = null;
        this.changeMode(false, false, false, true);
        return;
      }

      if (this.id !== 0) {
        this.getScheduleDetail();
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

      if (!choice.value) {
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

      this.$emit("deleteSchedule");
    },

    //일정 업데이트 완료
    updateSchedule() {
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
      this.changeMode(false, true, false, false);
    },

    // 공지 상세창 띄우기
    async showDetailScheduleForm() {
      this.changeMode(false, false, true, false);
    },

    async getScheduleDetail() {
      this.changeMode(false, false, true, false);
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