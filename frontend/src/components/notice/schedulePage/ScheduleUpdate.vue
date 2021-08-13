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

      <div v-if="scheduleInfo.scheduleImgUrl">
      <img
        :src="
          'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
          scheduleInfo.scheduleImgUrl
        "
        alt="profile-image"
      />
      <hr />
      </div>
      <v-file-input
        id="scheduleFile"
        v-model="files"
        accept="image/*"
        label="File input"
      ></v-file-input>
    </v-sheet>
  </div>
</template>

<script>
import scheduleApi from "@/api/schedule.js";
import awss3 from "@/utils/awss3.js";
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
      schedulImgUrl: this.scheduleInfo.schedulImgUrl,
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

    async updateSchedule() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let scheduleImgUrl = "";
      let photoKey = this.scheduleInfo.scheduleImgUrl;

      //s3 업로드 부분
      //기존에 사진파일이 없을때
      if (photoKey == null) {
        console.log("포토키없는데");

        //첨부한 사진이 있으면 업로드
        if (document.getElementById("scheduleFile").files.length != 0) {
          console.log("첨부파일있음");

          await awss3
            .uploadPhoto("schedule", "scheduleFile")
            .then((result) => (scheduleImgUrl = result[0]));
        }
      } else {
        console.log("포토키있는데");

        //기존에 사진파일이 있을 때
        //첨부한 사진이 있으면 업데이트
        if (document.getElementById("scheduleFile").files.length != 0) {
          console.log("첨부파일이있음");
          await awss3
            .updatePhoto("schedule", photoKey, "scheduleFile")
            .then((result) => (scheduleImgUrl = result[0]));
        } else {
          console.log("첨부파일이없음");
          //첨부한 사진이 없으면 기존 사진 삭제? 놔두기? 일단 놔두기로
          scheduleImgUrl = photoKey;
        }
      }

      let data = {
        scheduleImgUrl: scheduleImgUrl,
        noticeType: 2,
        userId: this.$store.state.user.userId,
        id: this.scheduleInfo.scheduleId,
        title: this.title,
        content: this.content,
      };

      let result = await scheduleApi.updateSchedule(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);

      this.$emit("updateSchedule");
      this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';">일정이 수정되었습니다.</p>`,
        focusConfirm: false,
        type: 'success'
      })
    },
  },
};
</script>

<style scoped>
</style>