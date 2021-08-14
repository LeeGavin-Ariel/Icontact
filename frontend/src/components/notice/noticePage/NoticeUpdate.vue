<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <v-sheet rounded="lg">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container">
        <div class="notice-detail-top">
          <div class="notice-type">공지사항 수정</div>
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
              id="noticeFile"
              v-model="noticeInfo.noticeImgUrl"
              accept="image/*"
              label="File input"
            ></v-file-input>
          </div>
        </div>
        <!-- 수정 관련 버튼-->
        <div class="btn-wrapper">
          <button @click="updateNotice" class="mr-2 update-return-btn" :disabled="saveDisabled">
            수정
          </button>
          <button @click="offUpdateForm" class="ml-2 update-return-btn">
            취소
          </button>
        </div>
      </div>
    </v-sheet>
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
import awss3 from "@/utils/awss3.js";
export default {
  name: "NoticeUpdate",

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
    noticeInfo: {},
    updateStart: Boolean,
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,

      createMode: true,
      updateMode: false,
      title: this.noticeInfo.title,
      content: this.noticeInfo.content,
      noticeImgUrl: this.noticeInfo.noticeImgUrl,
      saveDisabled: true,
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getNoticeDetail();
      }
    },
    updateStart() {
      this.updateNotice();
    },
    title(newValue) {
      if (newValue != "") this.saveDisabled = false;
      else this.saveDisabled = true;
    },
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      console.log(this.noticeInfo);
      console.log(this.noticeInfo.noticeImgUrl);
      this.title = this.noticeInfo.title;
      this.content = this.noticeInfo.content;
      this.noticeImgUrl = this.noticeInfo.noticeImgUrl;
      console.log(this.noticeImgUrl);
    },

    offUpdateForm() {
      this.$emit("cancelUpdateNotice");
    },

    async getNoticeDetail() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        noticeType: this.noticeType,
        id: this.id,
      };
      // 선생인지, 학부모인지에 따라 다르게 받음.
      let result = await noticeApi.getNoticeDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // 어떻게 날라오는지 확인후 데이터 집어넣기4
      if (this.noticeType === 1) {
        this.noticeDetail = result.dosage;
      } else if (this.noticeType === 2) {
        this.noticeDetail = result.returnhome;
      }
      console.log(this.noticeDetail);
      this.creating = 0;
      this.updating = 0;
    },

    async updateNotice() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      console.log("this.noticeInfo");
      console.log(this.noticeInfo);

      let noticeImgUrl = "";
      let photoKey = this.noticeInfo.noticeImgUrl;

      //s3 업로드 부분
      //기존에 사진파일이 없을때
      if (photoKey == null) {
        console.log("포토키없는데");

        //첨부한 사진이 있으면 업로드
        if (document.getElementById("noticeFile").files.length != 0) {
          console.log("첨부파일있음");

          await awss3
            .uploadPhoto("notice", "noticeFile")
            .then((result) => (noticeImgUrl = result[0]));
        }
      } else {
        console.log("포토키있는데");

        //기존에 사진파일이 있을 때
        //첨부한 사진이 있으면 업데이트
        if (document.getElementById("noticeFile").files.length != 0) {
          console.log("첨부파일이있음");
          await awss3
            .updatePhoto("notice", photoKey, "noticeFile")
            .then((result) => (noticeImgUrl = result[0]));
        } else {
          console.log("첨부파일이없음");
          //첨부한 사진이 없으면 기존 사진 삭제? 놔두기? 일단 놔두기로
          noticeImgUrl = photoKey;
        }
      }

      console.log("업데이트하자");

      console.log("noticeImgUrl");
      console.log(noticeImgUrl);
      console.log(this.noticeInfo.noticeId);

      let data = {
        noticeImgUrl: noticeImgUrl,
        noticeType: 1,
        userId: this.$store.state.user.userId,
        id: this.noticeInfo.noticeId,
        title: this.title,
        content: this.content,
      };

      let result = await noticeApi.updateNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);
      this.updating = 0;

      this.$emit("updateNotice");
      alert("공지사항이 수정되었습니다.");
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
  font-size: 1.8rem;
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
.notice-detail-content {
  width: 95%;
  padding: 0px 0.5rem 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-around;
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