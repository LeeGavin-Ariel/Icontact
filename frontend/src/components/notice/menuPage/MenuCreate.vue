<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    식단 작성페이지 <br />

    <v-sheet rounded="lg" v-if="createMode">
      <br />
      <!-- <p>
        제목 : <input type="text" v-model="title" style="border: solid 1px" />
      </p> -->

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
      ></v-file-input>
    </v-sheet>

    <v-btn color="primary" fab small dark @click="createNewMenu">
      <v-icon>mdi-check </v-icon>
    </v-btn>
    <v-btn color="red" fab small dark @click="offCreateForm">
      <v-icon>mdi-window-close </v-icon>
    </v-btn>
  </div>
</template>

<script>
import menuApi from "@/api/menu.js";
import awss3 from "@/utils/awss3.js";
export default {
  name: "MenuCreate",

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
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      menuDetail: null,
      createMode: true,
      updateMode: false,
      files: [],
      amSnackFile: null,
      pmSnackFile: null,
      lunchFile: null,
      amSnackName: "",
      pmSnackName: "",
      lunchName: "",
    };
  },
  watch: {
    lunchFile(value) {
      console.log(value);
      console.log(value);
    },
  },

  methods: {
    offCreateForm() {
      this.$emit("cancelCreateMenu");
    },

    // 식단 생성
    async createNewMenu() {
      if (this.lunchFile == "") {
        alert("입력하세요");
        return;
      }

      console.log("식단생성")
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let amSnackImgUrl = await awss3.uploadPhoto("menu", "amSnackFile");
      let lunchImgUrl = await awss3.uploadPhoto("menu", "lunchFile");
      let pmSnackImgUrl = await awss3.uploadPhoto("menu", "pmSnackFile");

      let data = {
        amSnackImgUrl: amSnackImgUrl[0],
        lunchImgUrl: lunchImgUrl[0],
        pmSnackImgUrl: pmSnackImgUrl[0],
        amSnack: this.amSnackName,
        pmSnack: this.pmSnackName,
        lunch: this.lunchName,
        noticeType: 3,
        userId: this.$store.state.user.userId,
        classCode: this.$store.state.user.classCode,
      };

      let result = await menuApi.createMenu(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
        // "Content-Type": "multipart/form-data",
      });
      console.log("result");
      console.log(result);

      this.$emit("createMenu");
    },
  },
};
</script>

<style scoped>
</style>