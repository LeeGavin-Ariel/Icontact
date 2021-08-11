<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->
    <v-sheet rounded="lg" v-if="createMode">
      <div class="container">
        <div class="notice-detail-title">
          <input
            type="text"
            v-model="title"
            style="border-bottom: solid 1px"
            placeholder="제목을 입력하세요"
          />
        </div>
        <div class="notice-detail-content-container">
          <div class="notice-detail-content">
            <textarea
              rows="15"
              style="width: 100%; height: 50%"
              placeholder="내용을 입력하세요."
              v-model="content"
            >
            </textarea>
          </div>
        </div>
        <!-- <div class="notice-detail-date">날짜</div> -->
        <div class="notice-detail-img">
          <!-- <input id="customFile" type="file" /> -->
          <v-file-input
            style="width: 100%"
            id="noticeFile"
            v-model="files"
            accept="image/*"
            label="File input"
          ></v-file-input>
          <!-- <img
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              noticeInfo.noticeImgUrl
            "
            alt="profile-image"
          /> -->
        </div>
        <div class="button">
          <v-btn
            class="mr-3"
            color="primary"
            fab
            small
            dark
            @click="createNewNotice"
          >
            <v-icon>mdi-check </v-icon>
          </v-btn>
          <v-btn class="mr-3" color="red" fab small dark @click="offCreateForm">
            <v-icon>mdi-window-close </v-icon>
          </v-btn>
        </div>
      </div>
    </v-sheet>
    <!-- 공지 작성페이지 <br /> -->
    <!-- <v-row>
      <v-sheet rounded="lg" v-if="createMode">
        <br />
        <p>
          제목 : <input type="text" v-model="title" style="border: solid 1px" />
        </p>

        <p>내용</p>
        <input
          id=""
          type="textarea"
          v-model="content"
          style="border: solid 1px"
        />
        <p>공지사항첨부사진 :</p>
        <v-file-input
          id="noticeFile"
          v-model="files"
          accept="image/*"
          label="File input"
        ></v-file-input>
      </v-sheet>
    </v-row>
    <v-row justify="end">
      <v-btn
        class="mr-3"
        color="primary"
        fab
        small
        dark
        @click="createNewNotice"
      >
        <v-icon>mdi-check </v-icon>
      </v-btn>
      <v-btn class="mr-3" color="red" fab small dark @click="offCreateForm">
        <v-icon>mdi-window-close </v-icon>
      </v-btn>
    </v-row> -->
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
        alert("입력하세요");
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

      this.$emit("createNotice");
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
.noticeImg {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
}
</style>