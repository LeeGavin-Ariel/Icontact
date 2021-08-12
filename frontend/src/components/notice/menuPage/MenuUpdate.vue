<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->


    <v-sheet rounded="lg">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container" style="overflow-y: scroll">
        <div class="item">
          <div class="item-type">오전 간식</div>
          <div class="item-img">
            <img
              v-if="menuInfo.amSnackImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                menuInfo.amSnackImgUrl
              "
              alt="profile-image"
            />
          </div>
          <input type="text" v-model="amSnackName" style="border: solid 1px" />
          <div class="item-name">{{ this.menuInfo.amSnack }}</div>
          <v-file-input
            id="amSnackFile"
            v-model="amSnackFile"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div>
        <div class="item">
          <div class="item-type">점심 식사</div>
          <div class="item-img">
            <img
              v-if="menuInfo.lunchImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                menuInfo.lunchImgUrl
              "
              alt="profile-image"
            />
          </div>
          <input type="text" v-model="lunchName" style="border: solid 1px" />
          <div class="item-name">{{ this.menuInfo.lunch }}</div>
          <v-file-input
            id="lunchFile"
            v-model="lunchFile"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div>
        <div class="item">
          <div class="item-type">오전 간식</div>
          <div class="item-img">
            <img
              v-if="menuInfo.pmSnackImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                menuInfo.pmSnackImgUrl
              "
              alt="profile-image"
            />
          </div>
          <input type="text" v-model="pmSnackName" style="border: solid 1px" />
          <div class="item-name">{{ this.menuInfo.pmSnack }}</div>
          <v-file-input
            id="pmSnackFile"
            v-model="pmSnackFile"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div>
        <!-- <div class="notice-detail-img">
          <p>
            오전 간식 :
            {{ this.menuInfo.amSnack }}
          </p>
          <img
            v-if="menuInfo.amSnackImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              menuInfo.amSnackImgUrl
            "
            alt="profile-image"
          />
          <v-file-input
            id="amSnackFile"
            v-model="amSnackFile"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div> -->
        <!-- <div class="notice-detail-img">
          <p>
            점심 식사 :
            {{ this.menuInfo.lunch }}
          </p>
          <img
            v-if="menuInfo.lunchImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              menuInfo.lunchImgUrl
            "
            alt="profile-image"
          />
          <v-file-input
            id="lunchFile"
            v-model="lunchFile"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div>
        <div class="notice-detail-img">
          <p>
            오후 간식 :
            {{ this.menuInfo.pmSnack }}
          </p>
          <img
            v-if="menuInfo.pmSnackImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              menuInfo.pmSnackImgUrl
            "
            alt="profile-image"
          />
          <v-file-input
            id="pmSnackFile"
            v-model="pmSnackFile"
            accept="image/*"
            label="File input"
          ></v-file-input>
        </div>-->
        <div class="btn-wrapper">
          <button @click="updateMenu" class="mr-2 update-return-btn">
            수정
          </button>
          <button @click="offUpdateForm" class="ml-2 update-return-btn">
            취소
          </button>
        </div> 
      </div>
      <!-- 식단 수정 페이지
      {{ menuDetail }}
      <p>
        오전 간식 :
        <input type="text" v-model="amSnackName" style="border: solid 1px" />
      </p>

      <p>오전 간식 사진 :</p>

        <v-file-input
          id="amSnackFile"
          v-model="amSnackFile"
          accept="image/*"
          label="File input"
        ></v-file-input>
      <p>
        점심 식사 :
        <input type="text" v-model="lunchName" style="border: solid 1px" />
      </p>
      <p>점심 식사 사진 :</p>

        <v-file-input
          id="lunchFile"
          v-model="lunchFile"
          accept="image/*"
          label="File input"
        ></v-file-input>
      <p>
        오후 간식 :
        <input type="text" v-model="pmSnackName" style="border: solid 1px" />
      </p>
      <p>오후 간식 사진 :</p>
        <v-file-input
          id="pmSnackFile"
          v-model="pmSnackFile"
          accept="image/*"
          label="File input"
        ></v-file-input> -->
    </v-sheet>
  </div>
</template>

<script>
import menuApi from "@/api/menu.js";
import awss3 from "@/utils/awss3.js";
export default {
  name: "MenuUpdate",

  props: {
    identity: {
      identity: Number,
    },
    menuType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
    menuInfo: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      menuDetail: null,

      createMode: true,
      updateMode: false,
      title: this.menuInfo.title,
      content: this.menuInfo.content,
      files: this.menuInfo.files,

      amSnackFile: this.menuInfo.amSnackImgUrl,
      pmSnackFile: this.menuInfo.pmSnackImgUrl,
      lunchFile: this.menuInfo.lunchImgUrl,
      amSnackName: this.menuInfo.amSnackName,
      pmSnackName: this.menuInfo.pmSnackName,
      lunchName: this.menuInfo.lunchName,
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getMenuDetail();
      }
    },
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      this.amSnackFile = this.menuInfo.amSnackFile;
      this.pmSnackFile = this.menuInfo.pmSnackFile;
      this.lunchFile = this.menuInfo.lunchFile;
      this.amSnackName = this.menuInfo.amSnack;
      this.pmSnackName = this.menuInfo.pmSnack;
      this.lunchName = this.menuInfo.lunch;
    },

    offUpdateForm() {
      this.$emit("cancelUpdateMenu");
    },

    async getMenuDetail() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        menuType: this.menuType,
        id: this.id,
      };
      // 선생인지, 학부모인지에 따라 다르게 받음.
      let result = await menuApi.getMenuDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // 어떻게 날라오는지 확인후 데이터 집어넣기4
      if (this.menuType === 1) {
        this.menuDetail = result.dosage;
      } else if (this.menuType === 2) {
        this.menuDetail = result.returnhome;
      }
      console.log(this.menuDetail);
      this.creating = 0;
      this.updating = 0;
    },

    async updateMenu() {
      console.log("일정 업데이트 버튼 클릭11S");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let amSnackPhotoKey = this.menuInfo.amSnackImgUrl;
      let lunchPhotoKey = this.menuInfo.lunchImgUrl;
      let pmSnackPhotoKey = this.menuInfo.pmSnackImgUrl;

      let amSnackImgUrl = await this.imgUpdate(amSnackPhotoKey, "amSnackFile");
      let lunchImgUrl = await this.imgUpdate(lunchPhotoKey, "lunchFile");
      let pmSnackImgUrl = await this.imgUpdate(pmSnackPhotoKey, "pmSnackFile");

      console.log("결과들");
      console.log(amSnackImgUrl);
      console.log(lunchImgUrl);
      console.log(pmSnackImgUrl);

      let data = {
        amSnackImgUrl: amSnackImgUrl,
        lunchImgUrl: lunchImgUrl,
        pmSnackImgUrl: pmSnackImgUrl,
        amSnack: this.amSnackName,
        pmSnack: this.pmSnackName,
        lunch: this.lunchName,
        noticeType: 3,
        id: this.menuInfo.menuId,
        userId: this.$store.state.user.userId,
        classCode: this.$store.state.user.classCode,
      };

      let result = await menuApi.updateMenu(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result111");
      console.log(result);
      this.updating = 0;

      this.$emit("updateMenu");
      alert("식단이 수정되었습니다.");
    },

    async imgUpdate(photoKey, elId) {
      var resultImgUrl = "";
      //s3 업로드 부분
      //기존에 사진파일이 없을때
      if (photoKey == null) {
        console.log(elId + " 포토키없는데");
        //첨부한 사진이 있으면 업로드
        if (document.getElementById(elId).files.length != 0) {
          console.log("첨부파일있음");

          await awss3
            .uploadPhoto("menu", elId)
            .then((result) => (resultImgUrl = result[0]));
        }
      } else {
        console.log("포토키있는데");

        //기존에 사진파일이 있을 때
        //첨부한 사진이 있으면 업데이트
        if (document.getElementById(elId).files.length != 0) {
          console.log("첨부파일이있음");
          await awss3
            .updatePhoto("menu", photoKey, elId)
            .then((result) => (resultImgUrl = result[0]));
        } else {
          console.log("첨부파일이없음");
          //첨부한 사진이 없으면 기존 사진 삭제? 놔두기? 일단 놔두기로
          resultImgUrl = photoKey;
        }
      }
      console.log("resultImgUrl");
      console.log(resultImgUrl);
      return resultImgUrl;
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  height: 84.6vh;
  /* flex-direction: column; */
  flex-wrap: wrap;
  justify-content: center;
  align-content: flex-start;
}
/* 스크롤바 너비 */
.container::-webkit-scrollbar {
  width: 5px;
}

/* 현재 스크롤의 위치바의 색 */
.container::-webkit-scrollbar-thumb {
  background-color: black;
}

/* 남는공간의 색 */
.container::-webkit-scrollbar-track {
  background-color: white;
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
.notice-detail-writer {
  width: 95%;
  /* display: ; */
  /* justify-content: flex-end; */
  /* border-bottom: #a8b1cf 0.1rem solid; */
  /* padding-right: 0.5rem; */
  /* margin-bottom: 1rem; */
  /* position: relative; */
  /* right: 0.5rem; */
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
  min-height: 5rem;
  padding: 0px 0.5rem 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: left;
  border-bottom: #a8b1cf 0.1rem solid;
}
.noticeImg {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}
.btn-wrapper {
  /* align-content: flex-end; */
  /* justify-content: flex-end; */
  width: 100%;
  text-align: right;
  margin-bottom: 1rem;
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

.item {
  width: 33%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}
.item-type {
  font-size: 20px;
  font-weight: bold;
  font-family: "NanumSquareRound";
  display: flex;
}
.item-name {
  display: flex;
}
.item-img {
  width: 90%;
  display: flex;
}
</style>