<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container">
        <div class="notice-detail-title">
          <input type="text" v-model="title" placeholder="제목을 입력하세요" />
        </div>
        <div class="notice-detail-content-container">
          <div class="notice-detail-content">
            <textarea
              rows="12"
              style="width: 100%"
              placeholder="내용을 입력하세요."
              v-model="content"
            >
            </textarea>
          </div>
        </div>
        <div class="notice-detail-img">
          <!-- <input id="customFile" type="file" /> -->
          <!-- <img
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              noticeInfo.noticeImgUrl
            "
            alt="profile-image"
          /> -->
          <!-- {{noticeInfo.noticeImgUrl}} -->
          <v-file-input
            style="width: 100%"
            id="scheduleFile"
            v-model="files"
            accept="image/*"
            label="File input"
          ></v-file-input>
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
        alert("입력하세요");
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
.container {
  display: flex;
  /* flex-direction: column; */
  flex-wrap: wrap;
  justify-content: center;
}
.notice-detail-title {
  /* margin: 0.3rem 0px; */
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 2em;
  border-bottom: #a8b1cf 0.1rem solid;
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
  width: 100%;
  display: flex;
  justify-content: center;
}
.notice-detail-content {
  width: 95%;
  padding: 0px 0.5rem 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-around;
  border-bottom: #a8b1cf 0.1rem solid;
}
.notice-detail-content textarea {
  width: 100%;
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