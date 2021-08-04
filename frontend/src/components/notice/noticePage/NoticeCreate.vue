<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    공지 작성페이지 <br />

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
        v-model="files"
        accept="image/*"
        label="File input"
      ></v-file-input>
    </v-sheet>

    <v-btn color="primary" fab small dark @click="createNewNotice">
      <v-icon>mdi-check </v-icon>
    </v-btn>
    <v-btn color="red" fab small dark @click="offCreateForm">
      <v-icon>mdi-window-close </v-icon>
    </v-btn>
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
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
    offCreateForm() {
      this.$emit('cancelCreateNotice')
    },

    // 공지 생성
    async createNewNotice() {
      if (this.title == "" || this.content == "") {
        alert("입력하세요");
        return;
      }

      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");


      const formData = new FormData();
      formData.append("img", this.files);
      formData.append("noticeType", 1);
      formData.append("userId", this.$store.state.user.userId);
      formData.append("classCode", this.$store.state.user.classCode);
      formData.append("title", this.title);
      formData.append("content", this.content);

      let result = await noticeApi
        .createNotice(formData, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
          "Content-Type": "multipart/form-data",
        })

      console.log('result');
      console.log(result);

      this.$emit("createNotice");
      // this.getNotice()
    },
  },
};
</script>

<style scoped>
</style>