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
      <v-file-input
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
        v-model="pmSnackFile"
        accept="image/*"
        label="File input"
      ></v-file-input>
    </v-sheet>
  </div>
</template>

<script>
import menuApi from "@/api/menu.js";
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

    // 글 작성 폼 띄우기
    async createNewMenu() {
      // 글 작성 중인 상태가 아니라면 글 작성 중 상태로 바꿈.

      if (this.updating) {
        this.updating = false;
      }

      if (this.creating === 0) {
        if (this.menuType === 1) {
          this.symptom = "";
          this.medicineType = "";
          this.dosageVol = 0;
          this.dosageCnt = 0;
          this.dosageTime = "";
          this.storage = "";
          this.specialNote = "";
        } else if (this.menuType === 2) {
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
        if (this.menuType === 1) {
          let data = {
            menuType: this.menuType,
            userId: this.$store.state.user.userId,
            symptom: this.symptom,
            medicineType: this.medicineType,
            dosageVol: this.dosageVol,
            dosageCnt: this.dosageCnt,
            dosageTime: this.dosageTime,
            storage: this.storage,
            specialNote: this.specialNote,
          };
          let result = await menuApi.createMenu(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result);
        } else if (this.menuType === 2) {
          let data = {
            menuType: this.menuType,
            userId: this.$store.state.user.userId,
            rhDate: this.rhDate,
            rhTime: this.rhTime,
            guardian: this.guardian,
            guardianTel: this.guardianTel,
            emergency: this.emergency,
            emergencyTel: this.emergencyTel,
          };
          let result = await menuApi.createMenu(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result);
        }
        this.creating = 0;
        this.$emit("get-notebooklist", this.menuType);
      }
    },

    async updateMenu() {
      console.log("일정 업데이트 버튼 클릭11S");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      const formDataa = new FormData();
      formDataa.append("imgs", this.amSnackFile);
      formDataa.append("imgs", this.pmSnackFile);
      formDataa.append("imgs", this.lunchFile);
      formDataa.append("amSnack", this.amSnackName);
      formDataa.append("pmSnack", this.pmSnackName);
      formDataa.append("lunch", this.lunchName);
      formDataa.append("menuType", 3);
      formDataa.append("id", this.menuInfo.menuId);
      formDataa.append("userId", this.$store.state.user.userId);

      let result = await menuApi.updateMenu(formDataa, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result111");
      console.log(result);
      this.updating = 0;

      this.$emit("updateMenu");
      alert("공지사항이 수정되었습니다.");
    },
  },
};
</script>

<style scoped>
</style>