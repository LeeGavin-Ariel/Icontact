<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    공지 작성페이지 <br />
    <button @click="createNewNotice">연필</button>
    |
    <button @click="offCreateForm">글 작성 취소</button>

    <v-sheet rounded="lg" v-if="createMode">
      <br />
      <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p>

      <p>내용</p>
      <input type="textarea"  v-model="content" style="border: solid 1px" />
      <!-- <p>작성 일자 : </p>
      <input type="number" v-model="createDate"> -->
      <p>공지사항첨부사진 :</p>
      <v-file-input v-model="files" accept="image/*" label="File input"></v-file-input>
    </v-sheet>
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
export default {
  name: "NoticeDetail",

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
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getNoticeDetail();
      }
    },
  },

  methods: {
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

    // 공지 생성
    async createNewNotice() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      const formData = new FormData();
      formData.append("img", this.files);
      formData.append("noticeType", 1);
      formData.append("userId", this.$store.state.user.userId);
      formData.append("classCode", this.$store.state.user.classCode);
      formData.append("title", this.title);
      formData.append("content", this.content);

      let result = await noticeApi.createNotice(formData, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
        'Content-Type': 'multipart/form-data'
      }).then(response=>{
        console.log('success!!');
        console.log(response.data);
      }).catch(function(e){
        console.log('error');
        console.log(e);
      })
      ;
      console.log(result);

      // this.$emit("get-notebooklist", this.noticeType);
      // this.getNotice()
    },

  },
};
</script>

<style scoped>
</style>