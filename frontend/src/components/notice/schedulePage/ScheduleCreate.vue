<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    일정 작성페이지 <br />

    <v-sheet rounded="lg" v-if="createMode">
      <br />
      <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p>

      <p>내용</p>
      <input type="textarea" v-model="content" style="border: solid 1px" />
      <!-- <p>작성 일자 : </p>
      <input type="number" v-model="createDate"> -->
      <p>공지사항첨부사진 :</p>
      <v-file-input
        id="scheduleFile"
        v-model="files"
        accept="image/*"
        label="File input"
      ></v-file-input>
    </v-sheet>

    <v-btn color="primary" fab small dark @click="createNewSchedule">
      <v-icon>mdi-check </v-icon>
    </v-btn>
    <v-btn color="red" fab small dark @click="offCreateForm">
      <v-icon>mdi-window-close </v-icon>
    </v-btn>
  </div>
</template>

<script>
import scheduleApi from "@/api/schedule.js";
import awss3 from "@/utils/awss3.js";

export default {
  name: "ScheduleCreate",

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
      createMode: true,
      updateMode: false,
      title: "",
      content: "",
      files: null,
    };
  },

  methods: {
    offCreateForm() {
      this.$emit('cancelCreateSchedule')
    },

    // 일정 생성
    async createNewSchedule() {
      if (this.title == "" || this.content == "") {
        this.$fire({
          html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';">일정을 입력하세요.</p>`,
          focusConfirm: false,
          type: 'warning'
        })
        return;
      }

      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let scheduleImgUrl = "";
      if (document.getElementById("scheduleFile").files.length != 0) {
        scheduleImgUrl = await awss3.uploadPhoto("schedule", "scheduleFile");
        console.log("파일있음");
      }

      let data = {
        scheduleImgUrl: scheduleImgUrl[0],
        noticeType: 2,
        userId: this.$store.state.user.userId,
        classCode: this.$store.state.user.classCode,
        title: this.title,
        content: this.content,
      };

      let result = await scheduleApi
        .createSchedule(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        })

      console.log('result');
      console.log(result);

      this.$emit("createSchedule");
      // this.getSchedule()
    },
  },
};
</script>

<style scoped>
</style>