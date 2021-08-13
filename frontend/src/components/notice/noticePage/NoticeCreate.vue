<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->
    <v-sheet rounded="lg" v-if="createMode">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container">
        <div class="notice-detail-top">
          <div class="notice-type">공지사항 작성</div>
        </div>
        <div class="notice-detail-title">
          <input
            type="text"
            v-model="title"
            style="text-align: center"
            placeholder="제목을 입력하세요"
          />
          <!-- style="border-bottom: solid 1px" -->
        </div>
        <div class="notice-detail-content-container">
          <textarea
            rows="10"
            placeholder="내용을 입력하세요."
            v-model="content"
          >
          </textarea>
          <div class="notice-detail-img">
            <!-- <input id="customFile" type="file" /> -->
            <v-file-input
              style="width: 100%"
              id="noticeFile"
              v-model="files"
              accept="image/*"
              label="File input"
            ></v-file-input>
          </div>
        </div>
        <!-- <div class="notice-detail-date">날짜</div> -->
        <div class="btn-wrapper">
          <button @click="createNewNotice" class="mr-2 update-return-btn">
            저장
          </button>
          <button @click="offCreateForm" class="ml-2 update-return-btn">
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
  name: "NoticeCreate",

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
    createStart: Boolean,
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,
      createMode: true,
      updateMode: false,
      title: "",
      content: "",
      files: null,
    };
  },

  watch: {
    createStart() {
      this.createNewNotice();
    },
  },

  methods: {
    async offCreateForm() {
      this.$emit("cancelCreateNotice");
    },

    // 공지 생성
    async createNewNotice() {
      //입력안하면 반환
      if (this.title == "" || this.content == "") {
        console.log(this.title);
        console.log(this.content);

        this.$fire({
          html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';"공지사항을 입력하세요.</p>`,
          focusConfirm: false,
          type: 'warning'
        })
        return;
      }

      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      //이미지 업로드처리
      let noticeImgUrl = "";
      if (document.getElementById("noticeFile").files.length != 0) {
        // noticeImgUrl = await awss3.uploadPhoto("notice", "noticeFile").then(result=>noticeImgUrl=result);
        await awss3
          .uploadPhoto("notice", "noticeFile")
          .then((result) => (noticeImgUrl = result[0]));
        console.log("파일있음");
      }

      let data = {
        noticeImgUrl: noticeImgUrl,
        noticeType: 1,
        userId: this.$store.state.user.userId,
        classCode: this.$store.state.user.classCode,
        title: this.title,
        content: this.content,
      };

      let result = await noticeApi.createNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);

      alert("새 공지가 등록되었습니다.");
      this.$emit("createNotice");
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  /* flex-direction: column; */
  height: 84.6vh;
  flex-wrap: wrap;
  justify-content: center;
  align-content: flex-start;
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
  width: 98%;
  display: inline-block;
  justify-content: center;
  text-align: center;
  font-size: 1.5rem;
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
  display: flex;
  justify-content: left;
}
.noticeImg {
  width: 50%;
  height: 200px;
  /* display: flex; */
  /* justify-content: left; */
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