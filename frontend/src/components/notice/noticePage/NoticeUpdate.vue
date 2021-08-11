<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    |
    <button @click="updateNotice">글 수정하기</button>

    |
    <!-- <button @click="deleteNotice">글 삭제</button>-->
    |
    <button @click="offUpdateForm">글 수정 취소</button>

    <v-sheet rounded="lg">
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
          <img
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              noticeInfo.noticeImgUrl
            "
            alt="profile-image"
          />
          <v-file-input
            style="width: 100%"
            id="noticeFile"
            v-model="noticeImgUrl"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div>
        <!-- <div class="button">
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
        </div> -->
      </div>

      <!-- 공지 수정 페이지
      {{ noticeDetail }}
      <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p>

      <p>내용</p>
      <input type="textarea" v-model="content" style="border: solid 1px" />

      <p>공지사항첨부사진 :</p>
      <div v-if="noticeInfo.noticeImgUrl">
        <img
          :src="
            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
            noticeInfo.noticeImgUrl
          "
          alt="profile-image"
        />
        <hr />
      </div>
      <v-file-input
        id="noticeFile"
        v-model="noticeImgUrl"
        accept="image/*"
        label="File input"
      ></v-file-input> -->
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
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getNoticeDetail();
      }
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