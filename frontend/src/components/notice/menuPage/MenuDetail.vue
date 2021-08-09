<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    식단 상세페이지 <br />
    <!-- <v-btn @click="showCreateMenuForm">새 글 작성</v-btn>
    
    <v-btn @click="showUpdateMenuForm">글 수정</v-btn> -->

    <v-sheet rounded="lg" v-if="createMode && this.menuInfo">
      <br />
      <p>
        오전 간식 :
        {{ this.menuInfo.amSnack }}
      </p>

      <p>오전 간식 사진 :</p>
        <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ menuInfo.amSnackImgUrl" alt="profile-image">
 
      <!-- <v-file-input
        v-model="amSnackFile"
        accept="image/*"
        label="File input"
      ></v-file-input> -->

      <p>
        점심 식사 :
         {{ this.menuInfo.lunch }}
      </p>
      <p>점심 식사 사진 :</p>
      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ menuInfo.lunchImgUrl" alt="profile-image">
      <!-- <v-file-input
        v-model="lunchFile"
        accept="image/*"
        label="File input"
      ></v-file-input> -->

      <p>
        오후 간식 :
        {{ this.menuInfo.pmSnack }}
      </p>
      <p>오후 간식 사진 :</p>
      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ menuInfo.pmSnackImgUrl" alt="profile-image">
      <!-- <v-file-input
        v-model="pmSnackFile"
        accept="image/*"
        label="File input"
      ></v-file-input> -->
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
    console.log('created');
    console.log(this.menuInfo);
  },

  methods: {
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
</style>