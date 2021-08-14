<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">
        <div class="notice-detail-top">
          <div class="notice-type">일정 작성</div>
        </div>
        <!-- <div class="notice-top">일정 작성</div> -->
        <div class="notice-detail-title">
          <input type="text" v-model="title" placeholder="제목을 입력하세요" />
        </div>
        <div class="notice-detail-content-container">
          <textarea
            rows="10"
            placeholder="내용을 입력하세요."
            v-model="content"
          >
          </textarea>
          <div class="notice-detail-img">
            <v-file-input
              style="width: 100%"
              id="scheduleFile"
              v-model="files"
              accept="image/*"
              label="File input"
            ></v-file-input>
          </div>
        </div>
        <!-- 수정 관련 버튼-->
        <div class="btn-wrapper">
          <button @click="createNewSchedule" class="mr-2 update-return-btn">
            저장
          </button>
          <button @click="offCreateForm" class="ml-2 update-return-btn">
            취소
          </button>
        </div>
      </div>
      <!-- <br />
      <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p>

      <p>내용</p>
      <input type="textarea" v-model="content" style="border: solid 1px" />
      <p>공지사항첨부사진 :</p>
      <v-file-input
        id="scheduleFile"
        v-model="files"
        accept="image/*"
        label="File input"
      ></v-file-input> -->
    </v-sheet>

    <!-- <v-btn color="primary" fab small dark @click="createNewSchedule">
      <v-icon>mdi-check </v-icon>
    </v-btn>
    <v-btn color="red" fab small dark @click="offCreateForm">
      <v-icon>mdi-window-close </v-icon>
    </v-btn> -->
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
      this.$emit("cancelCreateSchedule");
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

      let result = await scheduleApi.createSchedule(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);

      this.$emit("createSchedule");
      // this.getSchedule()
    },
  },
};
</script>

<style scoped>
/* 스크롤 */
.content-container{
  overflow-y:scroll; 
  height:80vh; 
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233,234,239, 0.5);
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233,234,239, 0.5);
  border-radius: 1px;
}
.notice-detail-top {
  width: 95%;
  display: flex;
  justify-content: space-between;
  /* padding-left: 1rem; */
  margin-bottom: 1rem;
  font-size: 1.2em;
  font-family: "NanumSquareRound";
  font-weight: bold;
}
.notice-type {
  width: 100%;
  display: inline-block;
}
.notice-detail-title {
  /* margin: 0.3rem 0px; */
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 2em;
  /* border-bottom: #a8b1cf 0.1rem solid; */
}

.notice-detail-title input {
  /* margin: 0.3rem 0px; */
  /* padding-bottom: 0.5rem;
  margin-bottom: 0.5rem; */
  width: 98%;
  display: inline-block;
  justify-content: center;
  text-align: center;
  font-size: 1.5rem;
  /* border-bottom: #a8b1cf 0.1rem solid; */
}
.notice-detail-date {
  width: 95%;
  display: flex;
  justify-content: flex-end;
  border-bottom: #a8b1cf 0.1rem solid;
  padding-right: 0.5rem;
  margin-bottom: 1rem;
  /* position: relative; */
  /* right: 0.5rem; */
}
.notice-detail-content-container {
  width: 95%;
  min-height: 50vh;
  display: flex;
  justify-content: center;
  align-content: flex-start;
  flex-wrap: wrap;
  border-radius: 5px;
  border-top: #a8b1cf 0.1rem solid;
  border-bottom: #a8b1cf 0.1rem solid;
}

.notice-detail-content-container textarea {
  width: 98%;
  margin-top: 1rem;
  padding: 1rem;
  border-radius: 5px;
  padding-left: 0.5em;
}
.notice-detail-img {
  width: 100%;
}
.noticeImg {
  width: 100%;
  height: 150px;
  display: flex;
  justify-content: center;
}
.notice-top {
  margin-top: 2.5rem;
  margin-bottom: 1rem;
  padding: 0px 0.5rem;
  width: 95%;
  display: flex;
  justify-content: flex-start;
  font-size: 1.3em;
  font-family: "NanumSquareRound";
  font-weight: bold;
}
.notice-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
input,
textarea {
  background-color: rgba(156, 156, 156, 0.1);
}

.btn-wrapper {
  /* align-content: flex-end; */
  /* justify-content: flex-end; */
  width: 100%;
  text-align: center;
  margin-bottom: 1rem;
}
.update-return-btn {
  background-color: rgba(168, 177, 207, 1);
  border-radius: 70px;
  height: 36px;
  width: 70px;
  margin: 20px 3px 3px 3px;
  text-align: center;
  color: rgba(256, 256, 256);
  letter-spacing: -1px;
}
</style>