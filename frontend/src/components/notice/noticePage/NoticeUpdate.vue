<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">

        <div align="right" class="mt-2"> 
        </div>
        <div align="center" class="mb-5">

          <div class="col-lg-11">
            <table class="table table-borderless mb-0">
              <tbody>
                <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">공지</strong>
                      <table class=" table mt-5 mb-5">

                        <tr class="d-flex">
                          <td class="col-12"><input type="text"  v-model="title" class="formInput" placeholder="제목을 입력하세요."></td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12">
                            <textarea rows="15" v-model="content" class="formInputText" placeholder="내용을 입력하세요."></textarea>
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12 mb-0">
                            <!-- 파일 첨부-->
                            <v-file-input
                              id="noticeFile"
                              v-model="noticeInfo.noticeImgUrl"
                              filled
                              prepend-icon="mdi-camera"
                              dense
                              accept="image/*"
                              style="width: 100%"
                            ></v-file-input>                            
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
              </tbody>
            </table>
            
          </div>

          <button @click="updateNotice" class="mr-2 update-return-btn">수정</button>
          <button @click="offUpdateForm" class="ml-2 update-return-btn">취소</button>


        </div>
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
        </div> 
      </div>-->

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
      // this.$fire({
      //   html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';">공지사항이 수정되었습니다.</p>`,
      //   focusConfirm: false,
      //   type: 'success'
      // })
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
.formInput {
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 36px;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.formInputText{
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 100%;
  width: 100%;
  padding: 15px 15px 15px 15px;
  margin: 3px 3px 3px 3px;
}
.formInput:hover{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
.formInputText:hover{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
.formInputText:focus{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
  outline: none;
}
.update-return-btn {
  background-color: rgba(168, 177, 207, 1);
  border-radius: 70px;
  height: 36px;
  width: 70px;
  margin: 0px 3px 3px 3px;
  text-align: center;
  color: rgba(256, 256, 256);
  letter-spacing: -1px;
}
.update-delete-btn {
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}
</style>