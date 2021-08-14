<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode && this.menuInfo">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">
        <div class="notice-detail-top">
          <div class="notice-type">식단</div>
          <div class="btn-wrapper" v-if="this.$store.state.user.type == 2">
            <button @click="showUpdateMenuForm" class="mr-2 update-delete-btn">
              수정
            </button>
            <button @click="deleteMenu" class="ml-2 mr-2 update-delete-btn">
              삭제
            </button>
          </div>
        </div>
        <div class="menu-detail-title">
          {{ this.menuInfo.createDate.substr(0, 11) }} 식단
        </div>
        <div class="item-wrapper">
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
        </div>
        <div class="menu-detail-date">
          작성일 : {{ this.menuInfo.createDate }}
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
.notice-detail-top {
  width: 95%;
  display: flex;
  justify-content: space-between;
  /* padding-left: 1rem; */
  /* margin-bottom: 1rem; */
  font-size: 1.2em;
  font-family: "NanumSquareRound";
  font-weight: bold;
}
.notice-type {
  width: 100%;
  display: inline-block;
}
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

.menu-detail-title {
  padding-bottom: 0.5rem;
  /* margin-bottom: 0.5rem; */
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 1.5em;
  font-family: "NanumSquareRound";
  font-weight: 900;
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
  /* border-bottom: #a8b1cf 0.1rem solid; */
  padding-right: 0.5rem;
  font-size: 0.8rem;
  /* margin-bottom: 1rem; */
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
.item-wrapper {
  width: 95%;
  height: 60vh;
  padding: 2rem 0rem 2rem 0rem;
  /* margin-bottom: 1rem; */
  /* display: flex; */
  /* align-items: center; */
  /* background:rgba(156, 156, 156, 0.1); */
  background: white;
  display: flex;
  border-radius: 5px;
  border-top: #a8b1cf 0.1rem solid;
  border-bottom: #a8b1cf 0.1rem solid;
}
.item {
  width: 33%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}
.item-type {
  width: 90%;
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
  font-size: 20px;
  font-weight: bold;
  font-family: "NanumSquareRound";
  display: inline-block;
  text-align: center;
  border-bottom: #a8b1cf 0.1rem solid;
}
.item-img {
  width: 90%;
  height: 250px;
  display: flex;
  border-bottom: #a8b1cf 0.1rem solid;
}
.item-img img {
  width: 100%;
  /* height: 200px; */
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
  height: 95%;
}
.item-name {
  margin-top: 1rem;
  width: 80%;
  text-align: center;
  display: inline-block;
}
</style>