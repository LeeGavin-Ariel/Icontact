<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <!-- 공지
    <button  @click="createNewSchedule">연필</button>
    | 
    <button @click="updateSchedule">연필</button> -->
    |
    <button @click="updateSchedule">글 수정하기</button>

    |
    <!-- <button @click="deleteSchedule">글 삭제</button>-->
    |
    <button @click="offUpdateForm">글 작성 취소</button>

    <v-sheet rounded="lg">
      일정 수정 페이지
      {{ scheduleDetail }}
      <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p>

      <p>내용</p>
      <input type="textarea" v-model="content" style="border: solid 1px" />

      <p>공지사항첨부사진 :</p>
      <v-file-input
        v-model="files"
        accept="image/*"
        label="File input"
      ></v-file-input>

      <!-- <p>작성 일자 : </p>
      <input type="number" v-model="createDate"> -->
      <br /><br />
      <p>첨부사진 :</p>
      <!-- <v-file-input v-model="files" accept="image/*" label="File input"></v-file-input> -->
    </v-sheet>
  </div>
</template>

<script>
import scheduleApi from "@/api/schedule.js";
export default {
  name: "ScheduleUpdate",

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
    scheduleInfo: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      scheduleDetail: null,

      createMode: true,
      updateMode: false,
      title: this.scheduleInfo.title,
      content: this.scheduleInfo.content,
      files: this.scheduleInfo.files,
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getScheduleDetail();
      }
    },
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      console.log("scheduleInfo");
      console.log(this.scheduleInfo);
      this.title = this.scheduleInfo.title;
      this.content = this.scheduleInfo.content;
      this.files = this.scheduleInfo.files;
    },

    offUpdateForm() {
      this.$emit("cancelUpdateSchedule");
    },

    async getScheduleDetail() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        scheduleType: this.scheduleType,
        id: this.id,
      };
      // 선생인지, 학부모인지에 따라 다르게 받음.
      let result = await scheduleApi.getScheduleDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // 어떻게 날라오는지 확인후 데이터 집어넣기4
      if (this.scheduleType === 1) {
        this.scheduleDetail = result.dosage;
      } else if (this.scheduleType === 2) {
        this.scheduleDetail = result.returnhome;
      }
      console.log(this.scheduleDetail);
      this.creating = 0;
      this.updating = 0;
    },

    // 글 작성 폼 띄우기
    async createNewSchedule() {
      // 글 작성 중인 상태가 아니라면 글 작성 중 상태로 바꿈.

      if (this.updating) {
        this.updating = false;
      }

      if (this.creating === 0) {
        if (this.scheduleType === 1) {
          this.symptom = "";
          this.medicineType = "";
          this.dosageVol = 0;
          this.dosageCnt = 0;
          this.dosageTime = "";
          this.storage = "";
          this.specialNote = "";
        } else if (this.scheduleType === 2) {
          this.rhDate = "";
          this.rhTime = "";
          this.guardian = "";
          this.guardianTel = "";
          this.emergency = "";
          this.emergencyTel = "";
        }
        this.creating = 1;
      }
      // 글 작성 중인 상태라면 요청 보내기.
      else if (this.creating === 1) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
        if (this.scheduleType === 1) {
          let data = {
            scheduleType: this.scheduleType,
            userId: this.$store.state.user.userId,
            symptom: this.symptom,
            medicineType: this.medicineType,
            dosageVol: this.dosageVol,
            dosageCnt: this.dosageCnt,
            dosageTime: this.dosageTime,
            storage: this.storage,
            specialNote: this.specialNote,
          };
          let result = await scheduleApi.createSchedule(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result);
        } else if (this.scheduleType === 2) {
          let data = {
            scheduleType: this.scheduleType,
            userId: this.$store.state.user.userId,
            rhDate: this.rhDate,
            rhTime: this.rhTime,
            guardian: this.guardian,
            guardianTel: this.guardianTel,
            emergency: this.emergency,
            emergencyTel: this.emergencyTel,
          };
          let result = await scheduleApi.createSchedule(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result);
        }
        this.creating = 0;
        this.$emit("get-notebooklist", this.scheduleType);
        // this.getSchedule()
      }
    },

    async updateSchedule() {
      console.log("일정 업데이트 버튼 클릭11S");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      const formDataa = new FormData();
      formDataa.append("img", this.files);
      formDataa.append("scheduleType", 2);
      formDataa.append("id", this.scheduleInfo.scheduleId);
      formDataa.append("userId", this.$store.state.user.userId);
      formDataa.append("title", this.title);
      formDataa.append("content", this.content);

      let result = await scheduleApi.updateSchedule(formDataa, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);
      this.updating = 0;

      this.$emit("updateSchedule");
      alert("공지사항이 수정되었습니다.");
    },
  },
};
</script>

<style scoped>
</style>