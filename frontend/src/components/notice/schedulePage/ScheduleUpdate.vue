<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">
        <div class="notice-detail-top">
          <div class="notice-type">일정 수정</div>
        </div>
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
              v-model="scheduleInfo.scheduleImgUrl"
              accept="image/*"
              label="File input"
            ></v-file-input>
          </div>
        </div>
        <!-- 수정 관련 버튼-->
        <div class="btn-wrapper">
          <button @click="updateSchedule" class="mr-2 update-return-btn">
            수정
          </button>
          <button @click="offUpdateForm" class="ml-2 update-return-btn">
            취소
          </button>
        </div>
      </div>
      <!-- 일정 수정 페이지
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
      ></v-file-input> -->
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
      alert("공지사항이 수정되었습니다.");
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
  border-radius: 5px;
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
  /* align-self: flex-end; */
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