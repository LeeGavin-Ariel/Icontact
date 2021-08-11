<template>
  <div style="overflow-y: scroll" class="col">
    <!-- 디테일 -->

    <!-- 공지 상세페이지 <br /> -->
    <v-sheet rounded="lg" v-if="createMode && this.noticeInfo">
      <div class="container">
        <div class="notice-detail-title">{{ this.noticeInfo.title }}</div>
        <div class="notice-detail-date">{{ this.noticeInfo.createDate }}</div>
        <div class="notice-detail-content-container">
          <div class="notice-detail-content">
            {{ this.noticeInfo.content }}<br/><br/>언어에 따라 명사의 앞 뒤에 놓여지는
            말이다.<br/><br/> 이어지는 명사의 배경(수, 성 격 등)에 따라 변화 할 수도 있다.
            지시사 등과 함께 한정하는 품사를 구성할 수도 있다 (영어 등). 한편,
            지시사와 관사는 별도의 위치를 차지할 수 있다. 예를 들어, 카나 어에서
            관사는 명사의 앞에, 지시사는 명사 뒤에 놓이고 동시에 사용할 수 있다.<br/>
            종종 접어이며, 또한 바로 뒤에 오는 단어의 발음에 따라 변화할 수
            있다. 예를 들어, 다음 단어의 어두가 모음일 때 다음이 자음일 때 비해
            모음을 생략하고 자음을 보충하는 일이 자주 발생한다. 영어 관사는
            다음이 자음일 때 생략된 발음을 한다.<br/><br/>(an에서 n이 생략되어 발음) 일부
            언어에서는 전치사와 인접할 때 전치사와 결합하여 축약형이 될 수도
            있다. 프랑스어에서는 축약형을 가진 조합 시에는 반드시 축약형을
            사용한다.<br/> (예 : de + le → du ) 이러한 현상을 독일어에서는 의미의
            차이로 구분한다.<br/><br/> (예 : 보통 von + dem → vom 하지만 지시사인 경우 von
            dem처럼 한다) 이처럼 언어에 따라 형태가 달라진다. 또한, 로망스어군의
            원류인 라틴어에는 관사가 없으며, 러시아어와 많은슬라브어, 그리고
            페르시아어처럼 인도유럽어족에 속하는 언어도 관사가 없는 것도있다.
          </div>
        </div>
        <div class="notice-detail-img">
          <img
            class="noticeImg"
            :src="
              'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
              noticeInfo.noticeImgUrl
            "
            alt="profile-image"
          />
        </div>
      </div>
      <!-- <br />
      <p>제목 : {{ this.noticeInfo.title }}</p>

      <p>내용</p>
      {{ this.noticeInfo.content }}
       <p>작성 일자 : </p>
      <input type="number" v-model="createDate"> 
      <br /><br />
      <p>첨부사진 :</p>
      <v-file-input v-model="files" accept="image/*" label="File input"></v-file-input> 
      <div v-if="noticeInfo.noticeImgUrl">
        <img class="noticeImg"
          :src="
            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
            noticeInfo.noticeImgUrl
          "
          alt="profile-image"
        />
      </div> -->
    </v-sheet>
  </div>
</template>

<script>
// import noticeApi from "@/api/notice.js";
export default {
  name: "NoticeDetail",

  props: {
    identity: {
      identity: Number,
    },
    noticeType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
    noticeInfo: {},
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,
      createMode: true,
      updateMode: false,
    };
  },
  watch: {
    // id: function () {
    //   console.log('noticedetail');
    //   if (this.id !== 0) {
    //     this.getNoticeDetail();
    //   }
    // },
  },

  methods: {
    changeMode(create, update, detail) {
      this.createMode = create;
      this.updateMode = update;
      this.detailMode = detail;
    },

    // 생성창 띄우기
    async showCreateNoticeForm() {
      console.log("눌렀는데");
      this.changeMode(true, false, false);

      // this.$emit("get-notebooklist", this.noticeType);
      // this.getNotice()
    },

    // async getNoticeDetail() {
    //   let accessToken = sessionStorage.getItem("access-token");
    //   let refreshToken = sessionStorage.getItem("refresh-token");
    //   let data = {
    //     noticeType: this.noticeType,
    //     id: this.id,
    //   };
    //   // 선생인지, 학부모인지에 따라 다르게 받음.
    //   let result = await noticeApi.getNoticeDetail(data, {
    //     "access-token": accessToken,
    //     "refresh-token": refreshToken,
    //   });
    //   // 어떻게 날라오는지 확인후 데이터 집어넣기4
    //   this.noticeDetail = result.notice;

    //   console.log('this.noticeDetail');
    //   console.log(this.noticeDetail);
    // },
  },

  created() {
    console.log(this.noticeInfo);
  },
};
</script>

<style scoped>
.container {
  display: flex;
  /* flex-direction: column; */
  flex-wrap: wrap;
  justify-content: center;
}
.notice-detail-title {
  /* margin: 0.3rem 0px; */
  padding-bottom: 0.5rem;
  width: 95%;
  display: flex;
  justify-content: center;
  font-size: 2em;
  border-bottom: #a8b1cf 0.1rem solid;
}
.notice-detail-date {
  width: 95%;
  display: flex;
  justify-content: flex-end;
  border-bottom: #a8b1cf 0.1rem solid;
  padding-right: 0.5rem;
  margin-bottom: 1rem;
  /* position: relative; */
  /* right: 0.5rem; */
}
.notice-detail-content-container{
  width: 100%;
  display: flex;
  justify-content: center;
  
}
.notice-detail-content {
  width: 95%;
  padding: 0px 0.5rem 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-around;
  border-bottom: #a8b1cf 0.1rem solid;
}
.noticeImg {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
}
</style>