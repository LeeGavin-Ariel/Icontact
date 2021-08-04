<template>
  <div style="overflow-y: scroll" class="col">
    <v-spacer></v-spacer>
    
    <v-fab-transition>
      <v-btn
        color="red"
        fab
        small
        dark
        bottom
        left
        class="v-btn--example"
        @click="showCreateMenuForm"
        v-if="!createMode"
      >
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </v-fab-transition>

    <button v-if="detailMode" @click="showUpdateMenuForm">|글 수정</button>

    <!-- <button v-if="detailMode" @click="deleteMenu">|글 삭제</button> -->

    <!-- <button @click="offCreateForm">글 작성 취소</button> -->

    <!-- <menu-detail v-if="detailMode"/> -->
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
    <menu-detail v-if="this.detailMode" :menuInfo="this.menuDetail" />
  </div>
</template>

<script>
import menuApi from "@/api/menu.js";
import MenuDetail from "./MenuDetail.vue";
import MenuCreate from "./MenuCreate.vue";
import MenuUpdate from "./MenuUpdate.vue";
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
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      menuDetail: null,

      createMode: false,
      detailMode: false,
      updateMode: false,
    };
  },
  watch: {
    id: function () {
      console.log("아이디가 변했어요" + this.id);
      if (this.id !== 0) {
        this.getMenuDetail();
      }
    },
  },

  methods: {
    //일정 업데이트 완료
    updateMenu(){
      console.log('에밋 view111');
      console.log(this.menuDetail);
      this.getMenuDetail();

      this.changeMode(false, false, true);
      this.$emit("updateMenu", this.menuDetail.menuId);
    },
    //일정 작성 완료
    createMenu(){
      this.changeMode(false, false, true);
      this.$emit("createMenu");
    },
    //공지 작성 취소
    cancelCreateMenu() {
      console.log('생성취소');
      this.changeMode(false, false, true);
    },
    //공지 수정 취소
    cancelUpdateMenu() {
      this.changeMode(false, false, true);
    },
    //공지 (작성,수정,상세) 모드 변경
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },
    // 공지 작성창 띄우기
    async showCreateMenuForm() {
      this.changeMode(true, false, false);
    },

    // 공지 수정창 띄우기
    async showUpdateMenuForm() {
      console.log("업데이트폼");
      this.changeMode(false, true, false);
    },

    // 공지 상세창 띄우기
    async showDetailMenuForm() {
      console.log("상세페이지폼");
      this.changeMode(false, false, true);
    },

    async getMenuDetail() {
      this.changeMode(false, false, true);
      console.log("공지상세요청간다");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        menuType: 2,
        id: this.id,
      };

      let result = await menuApi.getMenuDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.menuDetail = result.menu;
      console.log('this.menuDetail');
      console.log(this.menuDetail);
    },
  },
};
</script>

<style scoped>
</style>