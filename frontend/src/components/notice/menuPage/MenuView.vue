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
    if (this.menuList.length == 0) {

      this.noDataMode = true;
    }
  },
  watch: {
    id: function () {
      if (this.id == -1) {
        this.menuDetail = null;
        this.changeMode(false, false, false, true);
        return;
      }
      if (this.id != 0) {
        this.getMenuDetail();
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

      if (!choice.value) {
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

      this.$emit("deleteMenu");
    },

    //일정 업데이트 완료
    updateMenu() {
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
      this.changeMode(false, true, false, false);
    },

    // 공지 상세창 띄우기
    async showDetailMenuForm() {
      this.changeMode(false, false, true, false);
    },

    async getMenuDetail() {
      this.changeMode(false, false, true, false);
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