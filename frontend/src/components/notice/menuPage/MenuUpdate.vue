<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    식단 수정
    <!-- 공지
    <button  @click="createNewMenu">연필</button>
    | 
    <button @click="updateMenu">연필</button> -->
    |
    <button @click="updateMenu">글 수정하기</button>

    |
    <!-- <button @click="deleteMenu">글 삭제</button>-->
    |
    <button @click="offUpdateForm">글 작성 취소</button>

    <v-sheet rounded="lg">
      식단 수정 페이지
      {{ menuDetail }}
      <p>
        오전 간식 :
        <input type="text" v-model="amSnackName" style="border: solid 1px" />
      </p>

      <p>오전 간식 사진 :</p>

      <!-- <div v-if="menuInfo.amSnackImgUrl"> -->
        <v-file-input
          id="amSnackFile"
          v-model="amSnackFile"
          accept="image/*"
          label="File input"
        ></v-file-input>
      <!-- </div> -->
      <p>
        점심 식사 :
        <input type="text" v-model="lunchName" style="border: solid 1px" />
      </p>
      <p>점심 식사 사진 :</p>

      <!-- <div v-if="menuInfo.lunchImgUrl"> -->
        <v-file-input
          id="lunchFile"
          v-model="lunchFile"
          accept="image/*"
          label="File input"
        ></v-file-input>
      <!-- </div> -->
      <p>
        오후 간식 :
        <input type="text" v-model="pmSnackName" style="border: solid 1px" />
      </p>
      <p>오후 간식 사진 :</p>
<!-- 
      <div v-if="menuInfo.pmSnackImgUrl"> -->
        <v-file-input
          id="pmSnackFile"
          v-model="pmSnackFile"
          accept="image/*"
          label="File input"
        ></v-file-input>
      <!-- </div> -->
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
      this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';">식단이 수정되었습니다.</p>`,
        focusConfirm: false,
        type: 'success'
      })  
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
</style>