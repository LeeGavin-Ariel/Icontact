<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">
        <div class="notice-detail-title">
          식단 작성
        </div>
        <div class="item-wrapper">
          <div class="item">
            <div class="item-menuType">
              오전 간식
              <input
                type="text"
                v-model="amSnackName"
              />
            </div>
            <!-- <div class="item-menuName">자장면</div> -->
            <div class="item-img">
              <v-file-input
                id="amSnackFile"
                v-model="amSnackFile"
                accept="image/*"
                label="File input"
              ></v-file-input>
            </div>
          </div>
          <div class="item">
            <div class="item-menuType">
              점심 식사 
              <input
                type="text"
                v-model="lunchName"
              />
            </div>
            <!-- <div class="item-menuName">자장면</div> -->
            <div class="item-img">
              <v-file-input
                id="lunchFile"
                v-model="lunchFile"
                accept="image/*"
                label="File input"
              ></v-file-input>
            </div>
          </div>
          <div class="item">
            <div class="item-menuType">
              오후 간식
              <input
                type="text"
                v-model="pmSnackName"
              />
            </div>
            <!-- <div class="item-menuName">자장면</div> -->
            <div class="item-img">
              <v-file-input
                id="pmSnackFile"
                v-model="pmSnackFile"
                accept="image/*"
                label="File input"
              ></v-file-input>
            </div>
          </div>
        </div>
        <div>
          <button @click="createNewMenu" class="mr-2 update-return-btn">
            저장
          </button>
          <button @click="offCreateForm" class="ml-2 update-return-btn">
            취소
          </button>
        </div>
      </div>

      <!-- <p>
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
      //점심명을 입력하지 않으면 생성불가
      if (this.lunchName == "") {
        this.$fire({
          html: `<a href="javascript:void(0);"></a><p style="font-size: 30px; font-family: 'NanumSquareRound';">점심 메뉴를 입력하세요.</p>`,
          focusConfirm: false,
          type: 'warning'
        })
        return;
      }

      console.log("식단생성");
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let amSnackImgUrl = await this.imgUpload("amSnackFile");
      let lunchImgUrl = await this.imgUpload("lunchFile");
      let pmSnackImgUrl = await this.imgUpload("pmSnackFile");

      console.log("결과들");
      console.log(amSnackImgUrl);
      console.log(lunchImgUrl);
      console.log(pmSnackImgUrl);

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
      console.log("result");
      console.log(result);

      this.$emit("createMenu");
    },

    async imgUpload(elId) {
      var resultImgUrl = "";
      if (document.getElementById(elId).files.length != 0) {
        await awss3
          .uploadPhoto("menu", elId)
          .then((result) => (resultImgUrl = result[0]));
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
.notice-detail-title {
  /* margin: 0.3rem 0px; */
  /* padding-bottom: 0.5rem; */
  margin-top: 2.5rem;
  margin-bottom: 1rem;
  padding: 0px 0.5rem;
  width: 95%;
  display: flex;
  justify-content: flex-start;
  font-size: 1.3em;
  font-family: "NanumSquareRound";
  font-weight: bold;
  /* border-bottom: #a8b1cf 0.1rem solid; */
  /* text-align: left; */
  /* border-radius: ; */
}

.item-wrapper {
  width: 95%;
  padding: 2rem 1rem 2rem 2rem;
  margin-bottom: 1rem;
  /* display: flex; */
  /* align-items: center; */
  /* background:rgba(156, 156, 156, 0.1); */
  background:white;
  border-radius: 5px;
  border-top: #a8b1cf 0.1rem solid;
  border-bottom: #a8b1cf 0.1rem solid;

}

.item {
  width: 100%;
  display: flex;
  align-items: center;
}
.item-menuType {
  font-size: 15px;
  font-family: "NanumSquareRound";
  font-weight: bold;
  display: inline-block;
}
.item-menuType input{
  margin-left: 2rem;
  padding-left: 0.5rem;
  background: rgba(156, 156, 156, 0.1);
  height: 2rem;
  /* border: 0 solid none; */
  border-radius: 5px;
}
.item-img {
  margin-left: 2rem;
  display: inline-block;
  width: 10vw;
  /* display: flex; */
  /* padding: 0px; */
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
</style>