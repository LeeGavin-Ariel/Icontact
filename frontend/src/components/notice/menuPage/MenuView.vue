<template>
  <div class="col">
    <div class="ml-5 mr-5 mt-5">
      <button
        class="writeBtn"
        @click="showCreateMenuForm"
        v-if="!createMode & !updateMode & (this.$store.state.user.type == 2)"
      >
        <img src="@/assets/flaticon/write.png" style="width: 3.8rem" />
      </button>

      <menu-create
        v-if="this.createMode"
        @cancelCreateMenu="cancelCreateMenu"
        @createMenu="createMenu"
      />
      <menu-update
        v-if="this.updateMode"
        :menuInfo="this.menuDetail"
        @cancelUpdateMenu="cancelUpdateMenu"
        @updateMenu="updateMenu"
      />
      <menu-detail
        v-if="this.detailMode && this.menuDetail"
        :menuInfo="this.menuDetail"
        @showUpdateMenuForm="showUpdateMenuForm"
        @deleteMenu="deleteMenu"
      />
      <div v-if="noDataMode" style="font-size: 1.2rem; text-align:center;">
          등록된 식단이 없습니다.
      </div>
    </div>
  </div>
</template>

<script>
import menuApi from "@/api/menu.js";
import MenuDetail from "./MenuDetail.vue";
import MenuCreate from "./MenuCreate.vue";
import MenuUpdate from "./MenuUpdate.vue";
import awss3 from "@/utils/awss3.js";
export default {
  name: "MenuIndex",
  components: {
    MenuDetail,
    MenuCreate,
    MenuUpdate,
  },
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

    menuList: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      menuDetail: null,
      noDataMode: false,

      createMode: false,
      detailMode: false,
      updateMode: false,
    };
  },
  created() {
    console.log("menuList");
    if (this.menuList.length == 0) {
      console.log("menuList nodatamode");

      this.noDataMode = true;
    }
  },
  watch: {
    id: function () {
      if (this.id == -1) {
        this.menuDetail = null;
        console.log("글이 없습니다");
        this.changeMode(false, false, false, true);
        return;
      }
      if (this.id != 0) {
        console.log("아이디가 변했어요" + this.id);
        this.getMenuDetail();
      }
    },
    menuList(newValue) {
      console.log("길이");
      console.log(newValue);
      if (newValue.length == 0) {
        console.log("길이 0");
      }
    },
  },

  methods: {
    async deleteMenu() {
      let choice = await this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">정말로 삭제하시겠습니까?</p>`,
        type: "question",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#58679A",
      });

      // alert로 바꿔주세요.
      if (!choice.value) {
        console.log("삭제안함.");
        return;
      }
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let amSnackPhotoKey = this.menuDetail.amSnackImgUrl;
      let lunchPhotoKey = this.menuDetail.lunchImgUrl;
      let pmSnackPhotoKey = this.menuDetail.pmSnackImgUrl;

      await awss3.deletePhoto([amSnackPhotoKey], "");
      await awss3.deletePhoto([lunchPhotoKey], "");
      await awss3.deletePhoto([pmSnackPhotoKey], "");

      let data = {
        menuType: 3,
        id: this.id,
      };

      let result = await menuApi.deleteMenu(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log(result);

      this.$emit("deleteMenu");
    },

    //일정 업데이트 완료
    updateMenu() {
      console.log("에밋 view111");
      console.log(this.menuDetail);
      this.getMenuDetail();

      this.changeMode(false, false, true, false);
      this.$emit("updateMenu", this.menuDetail.menuId);
    },
    //일정 작성 완료
    createMenu() {
      this.changeMode(false, false, true, false);
      this.$emit("createMenu");
    },
    //공지 작성 취소
    cancelCreateMenu() {
      console.log("생성취소");
      this.changeMode(false, false, true, false);
    },
    //공지 수정 취소
    cancelUpdateMenu() {
      this.changeMode(false, false, true, false);
    },
    //공지 (작성,수정,상세) 모드 변경
    changeMode(create, update, detail, noData) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
      this.noDataMode = noData;
    },
    // 공지 작성창 띄우기
    async showCreateMenuForm() {
      this.changeMode(true, false, false, false);
    },

    // 공지 수정창 띄우기
    async showUpdateMenuForm() {
      console.log("업데이트폼");
      this.changeMode(false, true, false, false);
    },

    // 공지 상세창 띄우기
    async showDetailMenuForm() {
      console.log("상세페이지폼");
      this.changeMode(false, false, true, false);
    },

    async getMenuDetail() {
      this.changeMode(false, false, true, false);
      console.log("메뉴상세요청간다");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        menuType: 3,
        id: this.id,
      };

      let result = await menuApi.getMenuDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.menuDetail = result.menu;
      console.log("this.menuDetail");
      console.log(this.menuDetail);
    },
  },
};
</script>

<style scoped>
.writeBtn {
  position: fixed;
  right: 60px;
  bottom: 65px;
  width: 3.8rem;
}
.notice-detail-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
.notice-update-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
.notice-create-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}
</style>