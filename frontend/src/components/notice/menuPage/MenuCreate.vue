<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container content-container" style="height: 84.8vh">
        <div align="right" class="mt-2"></div>
        <div align="center" class="mb-5">
          <div class="col-lg-11">
            <table class="table table-borderless mb-0">
              <tbody>
                <tr class="d-flex">
                  <td class="col-12 align-items-center mt-3">
                    <strong style="font-size: 18px">메뉴</strong>
                    <table class="table mt-5 mb-5">
                      <tr class="d-flex">
                        <td class="col-12"><strong>오전 간식</strong></td>
                      </tr>
                      <tr class="d-flex">
                        <td class="col-5">
                          <v-file-input
                            filled
                            prepend-icon="mdi-camera"
                            dense
                            id="amSnackFile"
                            v-model="amSnackFile"
                            accept="image/*"
                          ></v-file-input>
                        </td>
                        <td class="col-7">
                          <input
                            type="text"
                            class="formInput"
                            placeholder="오전 간식 메뉴"
                            v-model="amSnackName"
                          />
                        </td>
                      </tr>

                      <tr class="d-flex">
                        <td class="col-12"><strong>점심</strong></td>
                      </tr>
                      <tr class="d-flex">
                        <td class="col-5">
                          <v-file-input
                            filled
                            prepend-icon="mdi-camera"
                            dense
                            id="lunchFile"
                            v-model="lunchFile"
                            accept="image/*"
                          ></v-file-input>
                        </td>
                        <td class="col-7">
                          <input
                            type="text"
                            class="formInput"
                            placeholder="점심 메뉴"
                            v-model="lunchName"
                          />
                        </td>
                      </tr>

                      <tr class="d-flex">
                        <td class="col-12"><strong>오후간식</strong></td>
                      </tr>
                      <tr class="d-flex">
                        <td class="col-5">
                          <v-file-input
                            filled
                            prepend-icon="mdi-camera"
                            dense
                            id="pmSnackFile"
                            v-model="pmSnackFile"
                            accept="image/*"
                          ></v-file-input>
                        </td>
                        <td class="col-7">
                          <input
                            type="text"
                            class="formInput"
                            placeholder="오후 간식 메뉴"
                            v-model="pmSnackName"
                          />
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- 새로운 글 작성 관련 버튼 -->
          <!-- 새 글을 작성하고, 저장하는 버튼 -->
          <button
            @click="createNewMenu"
            class="mr-2 update-return-btn"
            :disabled="saveDisabled"
          >
            작성
          </button>
          <!-- 글 작성(수정,새글) 취소 버튼 -->
          <button @click="offCreateForm" class="ml-2 update-return-btn">
            취소
          </button>
        </div>
      </div>
    </v-sheet>
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
      saveDisabled: true,
    };
  },
  watch: {
    amSnackName() {
      this.checkInput();
    },
    pmSnackName() {
      this.checkInput();
    },
    lunchName() {
      this.checkInput();
    },
    amSnackFile() {
      this.checkInput();
    },
    pmSnackFile() {
      this.checkInput();
    },
    lunchFile() {
      this.checkInput();
    },
  },

  methods: {
    checkInput() {
      if (
        this.amSnackName != "" ||
        this.pmSnackName != "" ||
        this.lunchName != "" ||
        this.amSnackFile != null ||
        this.pmSnackFile != null ||
        this.lunchFile != null
      ) {
        this.saveDisabled = false;
      } else {
        this.saveDisabled = true;
      }
    },
    offCreateForm() {
      this.$emit("cancelCreateMenu");
    },

    // 식단 생성
    async createNewMenu() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let amSnackImgUrl = await this.imgUpload("amSnackFile");
      let lunchImgUrl = await this.imgUpload("lunchFile");
      let pmSnackImgUrl = await this.imgUpload("pmSnackFile");

      let data = {
        amSnackImgUrl: amSnackImgUrl,
        lunchImgUrl: lunchImgUrl,
        pmSnackImgUrl: pmSnackImgUrl,
        amSnack: this.amSnackName,
        lunch: this.lunchName,
        pmSnack: this.pmSnackName,
        noticeType: 3,
        userId: this.$store.state.user.userId,
        classCode: this.$store.state.user.classCode,
      };

      let result = await menuApi.createMenu(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.$emit("createMenu");
    },

    async imgUpload(elId) {
      var resultImgUrl = "";
      if (document.getElementById(elId).files.length != 0) {
        await awss3
          .uploadPhoto("menu", elId)
          .then((result) => (resultImgUrl = result[0]));
      }
      return resultImgUrl;
    },
  },
};
</script>

<style scoped>
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
.formInput {
  background-color: rgba(156, 156, 156, 0.08);

  border-radius: 5px;
  height: 36px;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.formInputText {
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 100%;
  width: 100%;
  padding: 15px 15px 15px 15px;
  margin: 3px 3px 3px 3px;
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
.formInput:hover {
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
</style>