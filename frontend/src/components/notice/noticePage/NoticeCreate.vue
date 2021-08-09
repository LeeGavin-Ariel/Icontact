<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <!-- 공지 작성페이지 <br /> -->
    <v-row>
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
    </v-row>
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
</style>