<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode && this.menuInfo">
      <div
        style="width: 100%; height: 15px; background-color: #a8b1cf"
        class="mt-3"
      ></div>
      <div class="container" style="overflow-y: scroll">
        <div class="btn-wrapper" v-if="this.$store.state.user.type == 2">
          <button @click="showUpdateMenuForm" class="mr-2 update-delete-btn">
            수정
          </button>
          <button @click="deleteMenu" class="ml-2 mr-2 update-delete-btn">
            삭제
          </button>
        </div>
        <div class="menu-detail-title">{{ this.menuInfo.title }}</div>
        <!-- <div class="menu-detail-writer">{{this.noticeInfo.userName}}선생님 </div> -->
        <div class="menu-detail-date">{{ this.menuInfo.createDate }}</div>
        <div class="item">
          <div class="item-type">오전 간식</div>
          <div class="item-img">
            <img
              v-if="menuInfo.amSnackImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                menuInfo.amSnackImgUrl
              "
              alt="profile-image"
            />
          </div>
          <div class="item-name">{{ this.menuInfo.amSnack }}</div>
        </div>
        <div class="item">
          <div class="item-type">점심 식사</div>
          <div class="item-img">
            <img
              v-if="menuInfo.lunchImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                menuInfo.lunchImgUrl
              "
              alt="profile-image"
            />
          </div>
          <div class="item-name">{{ this.menuInfo.lunch }}</div>
        </div>
        <div class="item">
          <div class="item-type">오후 간식</div>
          <div class="item-img">
            <img
              v-if="menuInfo.pmSnackImgUrl"
              class="noticeImg"
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                menuInfo.pmSnackImgUrl
              "
              alt="profile-image"
            />
          </div>
          <div class="item-name">{{ this.menuInfo.pmSnack }}</div>
        </div>
        <!-- <div class="menu-detail-img">
          <p>
            오전 간식 :
            {{ this.menuInfo.amSnack }}
          </p>
          <img
            v-if="menuInfo.amSnackImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              menuInfo.amSnackImgUrl
            "
            alt="profile-image"
          />
        </div>
        <div class="menu-detail-img">
          <p>
            점심 식사 :
            {{ this.menuInfo.lunch }}
          </p>
          <img
            v-if="menuInfo.lunchImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              menuInfo.lunchImgUrl
            "
            alt="profile-image"
          />
        </div>
        <div class="menu-detail-img">
          <p>
            오후 간식 :
            {{ this.menuInfo.pmSnack }}
          </p>
          <img
            v-if="menuInfo.pmSnackImgUrl"
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              menuInfo.pmSnackImgUrl
            "
            alt="profile-image"
          />
        </div> -->
      </div>
      <!-- <p>
        오전 간식 :
        {{ this.menuInfo.amSnack }}
      </p>

      <p>오전 간식 사진 :</p>
      <div v-if="menuInfo.amSnackImgUrl">
        <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ menuInfo.amSnackImgUrl" alt="profile-image">
      </div>
 

      <p>
        점심 식사 :
         {{ this.menuInfo.lunch }}
      </p>
      <p>점심 식사 사진 :</p>
      <div v-if="menuInfo.lunchImgUrl">
      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ menuInfo.lunchImgUrl" alt="profile-image">
      </div>
  

      <p>
        오후 간식 :
        {{ this.menuInfo.pmSnack }}
      </p>
      <p>오후 간식 사진 :</p>
      <div v-if="menuInfo.pmSnackImgUrl">
      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ menuInfo.pmSnackImgUrl" alt="profile-image">
      </div>
    -->
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

      // amSnackFile: this.menuInfo.amSnackImgUrl==null?"":this.menuInfo.amSnackImgUrl,
      // pmSnackFile: this.menuInfo.pmSnackImgUrl==null?"":this.menuInfo.pmSnackImgUrl,
      // lunchFile: this.menuInfo.lunchSnackImgUrl==null?"":this.menuInfo.lunchSnackImgUrl,
    };
  },

  created() {
    console.log("created");
    console.log(this.menuInfo);
  },

  methods: {
    showUpdateMenuForm() {
      console.log("오픈업데이트폼");
      this.$emit("showUpdateMenuForm");
    },
    deleteMenu() {
      console.log("삭제");
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
.container {
  display: flex;
  height: 84.6vh;
  /* flex-direction: column; */
  flex-wrap: wrap;
  justify-content: center;
  align-content: flex-start;
}
/* 스크롤바 너비 */
.container::-webkit-scrollbar {
  width: 5px;
}

/* 현재 스크롤의 위치바의 색 */
.container::-webkit-scrollbar-thumb {
  background-color: black;
}

/* 남는공간의 색 */
.container::-webkit-scrollbar-track {
  background-color: white;
}
.menu-detail-title {
  /* margin: 0.3rem 0px; */
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 2em;
  border-bottom: #a8b1cf 0.1rem solid;
}
.menu-detail-writer {
  width: 95%;
  /* display: ; */
  /* justify-content: flex-end; */
  /* border-bottom: #a8b1cf 0.1rem solid; */
  /* padding-right: 0.5rem; */
  /* margin-bottom: 1rem; */
  /* position: relative; */
  /* right: 0.5rem; */
}
.menu-detail-date {
  width: 95%;
  display: flex;
  justify-content: flex-end;
  border-bottom: #a8b1cf 0.1rem solid;
  padding-right: 0.5rem;
  margin-bottom: 1rem;
  /* position: relative; */
  /* right: 0.5rem; */
}
.menu-detail-content-container {
  width: 100%;
  display: flex;
  justify-content: center;
}
.menu-detail-content {
  width: 95%;
  min-height: 5rem;
  padding: 0px 0.5rem 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: left;
  border-bottom: #a8b1cf 0.1rem solid;
}
.noticeImg {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}
.btn-wrapper {
  /* align-content: flex-end; */
  /* justify-content: flex-end; */
  width: 100%;
  text-align: right;
  margin-bottom: 1rem;
}
.update-delete-btn {
  /* justify-content: flex-end; */
  /* display: inline; */
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}
.item {
  width: 33%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}
.item-type {
  font-size: 20px;
  font-family: "NanumSquareRound";
  display: flex;
}
.item-name {
  display: flex;
}
.item-img {
  width: 90%;
  display: flex;
}
</style>