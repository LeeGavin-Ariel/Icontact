<template>
  <div>
    <v-sheet rounded="lg" v-if="createMode && this.menuInfo">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      >
      </div>
      <div class="container content-container" style="height: 84.8vh">
        <!-- 버튼 -->
        <div align="right" class="mt-2">
          <button
            v-if="this.$store.state.user.type == 2"
            @click="showUpdateMenuForm"
            class="mr-2 update-delete-btn"
          >
            수정
          </button>
          <button
            v-if="this.$store.state.user.type == 2"
            @click="deleteMenu"
            class="ml-2 mr-2 update-delete-btn"
          >
            삭제
          </button>
        </div>
        
        
        <!-- 메뉴 -->
        <div align="center" class="mb-5">
          <div class="col-lg-11">
            <table class="table">
              <tbody>
                
                <!-- 메뉴 상단 -->
                <tr class="d-flex">
                  <td class="col-12">
                    <div style="font-size: 35px">
                      <strong>{{ menuTitle(menuInfo.createDate) }}</strong>
                    </div>
                    <div class="mt-3 mb-2">{{ menuInfo.userName }} 선생님</div>
                  </td>
                </tr>

                <!-- 내용 -->
                <tr class="d-flex">
                  <td class="col-12 align-items-center">
                    <table class="table mt-5 mb-5">
                      <tr class="d-flex">
                        <td class="col-12 menu-title d-flex align-items-center">
                          <div>오전 간식</div>
                        </td>
                      </tr>
                      <tr class="d-flex">
                        <td class="col-5">
                          <img
                            v-if="menuInfo.amSnackImgUrl"
                            style="width: 100%"
                            :src="
                              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                              menuInfo.amSnackImgUrl
                            "
                            alt="profile-image"
                          />
                        </td>
                        <td class="col-7">{{ this.menuInfo.amSnack }}</td>
                      </tr>

                      <tr class="d-flex">
                        <td class="col-12 menu-title d-flex align-items-center">
                          <div>점심</div>
                        </td>
                      </tr>
                      <tr class="d-flex">
                        <td class="col-5">
                          <img
                            v-if="menuInfo.lunchImgUrl"
                            style="width: 100%"
                            :src="
                              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                              menuInfo.lunchImgUrl
                            "
                            alt="profile-image"
                          />
                        </td>
                        <td class="col-7">{{ this.menuInfo.lunch }}</td>
                      </tr>

                      <tr class="d-flex">
                        <td class="col-12 menu-title d-flex align-items-center">
                          <div>오후 간식</div>
                        </td>
                      </tr>
                      <tr class="d-flex">
                        <td class="col-5">
                          <img
                            v-if="menuInfo.pmSnackImgUrl"
                            style="width: 100%"
                            :src="
                              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                              menuInfo.pmSnackImgUrl
                            "
                            alt="profile-image"
                          />
                        </td>
                        <td class="col-7">{{ this.menuInfo.pmSnack }}</td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </v-sheet>
  </div>
</template>


<script>
export default {
  name: "MenuDetail",

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
    };
  },

  methods: {
    menuTitle(content) {
      var dateform = "";
      dateform =
        content.substr(5, 2) + "월 " + content.substr(8, 2) + "일 식단";
      return dateform;
    },
    showUpdateMenuForm() {
      this.$emit("showUpdateMenuForm");
    },
    deleteMenu() {
      this.$emit("deleteMenu");
    },
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },

    // 생성창 띄우기
    async showCreateMenuForm() {
      this.changeMode(true, false, false);
    },
  },
};
</script>

<style scoped>
* {
  font-family: "NanumSquareRound";
}


/* 스크롤 */
.content-container {
  overflow-y: scroll;
  height: 80vh;
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 1px;
}

/* 수정 삭제 버튼 */
.update-delete-btn {
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}

/* 메뉴 타이틀 */
.menu-title {
  border-radius: 5px;
  background-color: rgba(228, 233, 251, 0.4);
  height: 1.7rem;
  font-size: 0.95rem;
  font-weight: 600;
}

</style>