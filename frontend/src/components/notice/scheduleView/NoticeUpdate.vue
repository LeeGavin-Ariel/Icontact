<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <!-- 공지
    <button  @click="createNewNotice">연필</button>
    | 
    <button @click="updateNotice">연필</button> -->
    |
    <button @click="updateNotice">글 수정하기</button>

    |
    <!-- <button @click="deleteNotice">글 삭제</button>-->
    |
    <button @click="offCreateForm">글 작성 취소</button> 

    <v-sheet rounded="lg">
      공지 수정 페이지
      {{ noticeDetail }}
      <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p>

      <p>내용</p>
      <input type="textarea" v-model="content" style="border: solid 1px" />

      <p>공지사항첨부사진 :</p>
      <v-file-input
        v-model="files"
        accept="image/*"
        label="File input"
      ></v-file-input>

      <!-- <p>작성 일자 : </p>
      <input type="number" v-model="createDate"> -->
      <br /><br />
      <p>첨부사진 :</p>
      <!-- <v-file-input v-model="files" accept="image/*" label="File input"></v-file-input> -->
    </v-sheet>
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
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
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,

      createMode: true,
      updateMode: false,
      title: this.noticeInfo.title,
      content: this.noticeInfo.content,
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getNoticeDetail();
      }
    },
  },

  created: {
    init() {
      this.title = this.noticeInfo.title;
      this.content = this.noticeInfo.content;
    },
  },

  methods: {
    offCreateForm() {
      this.creating = 0;
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

    // 글 작성 폼 띄우기
    async createNewNotice() {
      // 글 작성 중인 상태가 아니라면 글 작성 중 상태로 바꿈.

      if (this.updating) {
        this.updating = false;
      }

      if (this.creating === 0) {
        if (this.noticeType === 1) {
          this.symptom = "";
          this.medicineType = "";
          this.dosageVol = 0;
          this.dosageCnt = 0;
          this.dosageTime = "";
          this.storage = "";
          this.specialNote = "";
        } else if (this.noticeType === 2) {
          this.rhDate = "";
          this.rhTime = "";
          this.guardian = "";
          this.guardianTel = "";
          this.emergency = "";
          this.emergencyTel = "";
        }
        this.creating = 1;
      }
      // 글 작성 중인 상태라면 요청 보내기.
      else if (this.creating === 1) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
        if (this.noticeType === 1) {
          let data = {
            noticeType: this.noticeType,
            userId: this.$store.state.user.userId,
            symptom: this.symptom,
            medicineType: this.medicineType,
            dosageVol: this.dosageVol,
            dosageCnt: this.dosageCnt,
            dosageTime: this.dosageTime,
            storage: this.storage,
            specialNote: this.specialNote,
          };
          let result = await noticeApi.createNotice(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result);
        } else if (this.noticeType === 2) {
          let data = {
            noticeType: this.noticeType,
            userId: this.$store.state.user.userId,
            rhDate: this.rhDate,
            rhTime: this.rhTime,
            guardian: this.guardian,
            guardianTel: this.guardianTel,
            emergency: this.emergency,
            emergencyTel: this.emergencyTel,
          };
          let result = await noticeApi.createNotice(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result);
        }
        this.creating = 0;
        this.$emit("get-notebooklist", this.noticeType);
        // this.getNotice()
      }
    },

    async updateNotice() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      const formData = new FormData();
      formData.append("img", this.files);
      formData.append("noticeType", 1);
      formData.append("id", this.noticeInfo.noticeId);
      formData.append("userId", this.$store.state.user.userId);
      formData.append("title", this.title);
      formData.append("content", this.content);

      let result = await noticeApi.updateNotice(formData, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log('result');
      console.log(result);
      this.updating = 0;

      // this.$emit("get-notebooklist", this.noticeType);
      // this.getNotice()\
      window.location.reload()
     alert('공지사항이 수정되었습니다.');
    },

    async deleteNotice() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      if (this.noticeType === 1) {
        let data = {
          noticeType: this.noticeType,
          id: this.noticeDetail.dosageId,
        };
        let result = await noticeApi.deleteNotice(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        });
        console.log(result);
      } else if (this.noticeType === 2) {
        let data = {
          noticeType: this.noticeType,
          id: this.noticeDetail.rhId,
        };
        let result = await noticeApi.deleteNotice(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        });
        console.log(result);
      }
      this.$emit("get-notebooklist", this.noticeType);
      // window.location.reload()
    },
  },
};
</script>

<style scoped>
</style>