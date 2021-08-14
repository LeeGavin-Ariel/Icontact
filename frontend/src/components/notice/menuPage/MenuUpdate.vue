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
                      <strong style="font-size: 18px">메뉴</strong>
                      <table class=" table mt-5 mb-5">
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
                        <td class="col-7"><input type="text" class="formInput" placeholder="오전 간식 메뉴" v-model="amSnackName"></td>
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
                        <td class="col-7"><input type="text" class="formInput" placeholder="점심 메뉴" v-model="lunchName"/></td>
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
                        <td class="col-7"><input type="text" class="formInput" placeholder="오후 간식 메뉴" v-model="pmSnackName"/></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- 새로운 글 작성 관련 버튼 -->
          <!-- 새 글을 작성하고, 저장하는 버튼 -->
          <button @click="updateMenu" class="mr-2 update-return-btn">수정</button>
          <!-- 글 작성(수정,새글) 취소 버튼 -->
          <button @click="offUpdateForm" class="ml-2 update-return-btn">취소</button>
      

      



        </div>
      </div>
      
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
      this.amSnackFile = this.menuInfo.amSnackImgUrl;
      this.pmSnackFile = this.menuInfo.pmSnackImgUrl;
      this.lunchFile = this.menuInfo.lunchImgUrl;
      this.amSnackName = this.menuInfo.amSnack;
      this.pmSnackName = this.menuInfo.pmSnack;
      this.lunchName = this.menuInfo.lunch;
      console.log('amSnackFile');
      console.log(this.amSnackFile);
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
.formInput {
  background-color: rgba(156, 156, 156, 0.08);
  border-radius: 5px;
  height: 36px;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}

.formInput:hover{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
</style>