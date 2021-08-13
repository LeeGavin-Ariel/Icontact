<template>
  <div class="row letter-back"  style="width:82vw; margin:0;">

    <div
    class="mx-auto"
    style="padding-bottom: 0px; width: 38%;"
    >

      <!-- 요청사항 리스트 -->
      <v-col class="mx-auto">

      <!-- 요청사항 버튼 -->
      <v-tabs color="mainColor1" background-color="transparent" class="mt-3 mb-5">
        <v-tab  class="request-tab" @click="getDosage">투약 요청</v-tab>
        <v-tab class="request-tab" @click="getReturnHome">귀가 동의</v-tab>
        <v-tabs-slider color="#A8B1CF"></v-tabs-slider>
      </v-tabs>

      
      <!-- 투약 요청 리스트 -->
      <div v-if="requestType === 1" class="content-container list-col" style="overflow-y:scroll; height:80vh;">
        
        <div class="d-flex flex-column align-items-stretch flex-shrink-0" style="width: 100%;">
          <template v-for="(request, index) in dosageList" >
            <div class="list-group list-group-flush scrollarea border-bottom" :class="{selected : idx == index}" v-if="requestType === 1" :key="request.createDate" @click="setDetail(request.dosageId, index)" >
                <div class="list-group-item list-group-item-action py-2 lh-tight" style="background-color:rgb(256, 256, 256, 0.7);">
                  <div class="d-flex align-items-center justify-content-evenly" style="height: 9vh; width:100%">
                    
                    <!-- 리스트 내용 -->
                    <v-avatar size="60" class="profile-img mr-3">
                      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' + request.profileImg"/>
                    </v-avatar>
                    <div>
                    <div class="request-kid-name"><strong>{{request.kidName}}</strong> 학부모님의 투약 요청</div>
                    <div class="request-time">{{request.createDate}}</div>
                    </div>
                    <img src="@/assets/flaticon/letter.png" style="width: 1.8rem">
                  </div>
                </div>
            </div>
            <div
              v-if="index < dosageList.length - 1"
              :key="index"
            ></div>
          </template>
          
          <!-- 더보기 버튼 -->
          <button class="mt-2 more-btn" @click="getMoreDosageList" v-if="dosageList.length > 0 && (dosagePageNum <= dosagePageCnt)">더보기</button>
        
        </div>
      </div>


      <!-- 귀가 동의 리스트 -->
      <div v-if="requestType === 2" class="content-container list-col" style="overflow-y:scroll; height:80vh;">
        
        <div class="d-flex flex-column align-items-stretch flex-shrink-0" style="width: 100%;">
          <template v-for="(request, index) in returnHomeList" >
            <div class="list-group list-group-flush scrollarea border-bottom" :class="{selected : idx == index}" v-if="requestType === 2" :key="request.createDate" @click="setDetail(request.rhId, index)">
                <div class="list-group-item list-group-item-action py-2 lh-tight" style="background-color:rgb(256, 256, 256, 0.7);">
                  <div class="d-flex align-items-center justify-content-evenly" style="height: 9vh; width:100%">
                    
                    <!-- 리스트 내용 -->
                    <v-avatar size="60" class="profile-img mr-3">
                      <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' + request.profileImg"/>
                    </v-avatar>
                    <div>
                      <div class="request-kid-name"><strong>{{request.kidName}}</strong> 학부모님의 귀가 동의</div>
                      <div class="request-time">{{request.createDate}}</div>
                    </div>
                    <img src="@/assets/flaticon/letter.png" style="width: 1.8rem">
                  </div>
                </div>
            </div>
            <div
              v-if="index < returnHomeList.length - 1"
              :key="index"
            ></div>
          </template>
          
          <!-- 더보기 버튼 -->
          <button class="mt-2 more-btn" @click="getMoreReturnhomeList" v-if="returnHomeList.length > 0 && (returnhomePageNum <= returnhomePageCnt)">더보기</button>
        
        </div>
      </div>    
      </v-col>

    </div>
    
    <!-- 디테일 컴포넌트 -->
    <RequestDetail
    :identity="identity"
    :requestType="requestType"
    :id="id"
    @get-notebooklist="initRequestList"
    />

  </div>
</template>

<script>
import RequestDetail from '@/components/Request/RequestDetail.vue';
import requestApi from '@/api/request.js';

export default {
  name: "RequestList",
  components:{
    RequestDetail,
  },
  data () {
    return {
      // 디테일 값을 얻어 오기 위한 글의 아이디값
      id: 0,
      idx: 0,
      // 관계
      identity : 0,
      identity_str : '',

      // 투약 요청(1)인지, 귀가요청(2)인지. default는 투약요청
      requestType : 1,
      // 전체 요청 글을 저장할 리스트.
      // 어떤값 넘겨주는지 확인하기.
      dosageList: [],
      returnHomeList: [],

      // 더보기
      dosagePageNum: 1,
      returnhomePageNum: 1,
      // 더보기 전체 페이지 수
      dosagePageCnt: 1 ,
      returnhomePageCnt: 1 ,

      profileImg:this.$store.state.user.profileImg,
    }
  },
  watch: {
    'id' : function() {
      console.log('아이디값 변경!')
      if (this.id !== 0) {
        console.log('1')
      }
    }
  },

  methods: {
    // 글 작성, 수정, 삭제 이벤트 발생시 다시 목록 조회.
    initRequestList (requestType) {
      if (requestType === 1) {
        this.id = 0
        this.dosageList = []
        this.dosagePageNum = 1
        this.getDosageList()
      }
      else if (requestType === 2) {
        this.id = 0
        this.returnHomeList = []
        this.returnhomePageNum = 1
        this.getReturnHomeList()
      }
    },
    // 더보기 버튼
    getMoreDosageList() {
      if (this.dosagePageNum <= this.dosagePageCnt) {
        this.getDosageList()
      }
    },
    // 더보기 버튼
    getMoreReturnhomeList() {
      if (this.returnhomePageNum <= this.returnhomePageCnt) {
        this.getReturnHomeList()
      }
    },
    

    // 글을 클릭했을때 id값 저장
    setDetail(id, index) {
      this.id = id
      this.idx = index;
    },


    // 투약 탭 눌렀을때
    getDosage() {
      this.idx = 0;
      this.creating = 0
      if (this.requestType !== 1) {

        this.requestType = 1
        if (this.dosageList.length === 0) {
          this.getDosageList()
        }
        if (this.dosageList.length !== 0) {
        this.id = this.dosageList[0].dosageId
        }
      }
    },
    // 귀가 탭 눌렀을때
    async getReturnHome() {
      this.idx = 0;
      this.creating = 0
      if (this.requestType !== 2) {
        this.requestType = 2
        
        if (this.returnHomeList.length === 0) {
          await this.getReturnHomeList()
        }
        if (this.returnHomeList.length !== 0) {
          this.id = this.returnHomeList[0].rhId
        }
      }
    },

    // 투약 전체 글 불러오기. (투약 -> requestType = 1, 귀가 -> requestType = 2)
    // async getDosageList() {
    //   let accessToken = sessionStorage.getItem('access-token')
    //   let refreshToken = sessionStorage.getItem('refresh-token')
    //   let data = {
    //     type: this.identity_str,
    //     requestType: this.requestType,
    //     userId: this.$store.state.user.userId,
    //     pageNum: this.dosagePageNum
    //   }
    //   requestApi.getRequest(data, {
    //     "access-token": accessToken,
    //     "refresh-token": refreshToken,
    //   })
    //   .then(result => {
    //     this.dosagePageCnt = result.pageCnt
    //     this.dosageList.push(...result.dosageList)
    //     this.dosagePageNum += 1

    //     if (this.id === 0) {
    //       this.id = this.dosageList[0].dosageId
    //     }
    //   })
      
    // },

    async getDosageList() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        requestType: this.requestType,
        userId: this.$store.state.user.userId,
        pageNum: this.dosagePageNum
      }
      let result = await requestApi.getRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      this.dosagePageCnt = result.pageCnt
      this.dosageList.push(...result.dosageList)
      this.dosagePageNum += 1

      if (this.id === 0) {
        if (this.dosageList.length !== 0) {
          this.id = this.dosageList[0].dosageId
        }
      } else {
        if (this.dosageList.length === 0) {
          this.id = 0
        }
      }

    },

    // 귀가 전체 글 조회
    // getReturnHomeList() {
    //   let accessToken = sessionStorage.getItem('access-token')
    //   let refreshToken = sessionStorage.getItem('refresh-token')
    //   let data = {
    //     type: this.identity_str,
    //     requestType: this.requestType,
    //     userId: this.$store.state.user.userId,
    //     pageNum: this.returnhomePageNum
    //   }
    //   requestApi.getRequest(data, {
    //     "access-token": accessToken,
    //     "refresh-token": refreshToken,
    //   })
    //   .then(result => {
    //     this.returnhomePageCnt = result.pageCnt
    //     this.returnHomeList.push(...result.returnhomeList)
    //     this.returnhomePageNum += 1
    //     if (this.id === 0) {
    //       // 첫번째 글의 디테일 페이지 디폴트 값으로 올리기.
    //       this.id = this.returnHomeList[0].rhId
    //     }
    //   })
    // },

    // // 귀가 전체 글 조회
    async getReturnHomeList() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        requestType: this.requestType,
        userId: this.$store.state.user.userId,
        pageNum: this.returnhomePageNum
      }
      let result = await requestApi.getRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.returnhomePageCnt = result.pageCnt
      this.returnHomeList.push(...result.returnhomeList)

      this.returnhomePageNum += 1
      if (this.id === 0) {
        // 첫번째 글의 디테일 페이지 디폴트 값으로 올리기.
        if (this.returnHomeList.length !== 0) {
          this.id = this.returnHomeList[0].rhId
        }
      } else {
        if (this.returnHomeList.length === 0) {
          this.id = 0
        }
      }
    },
  },

  created() {
    console.log(this.$store.state.user);
    // 페이지 들어오자마자 getRequest 실행 (default 투약요청)
    this.identity = this.$store.state.user.type
    if (this.identity === 1) {
      this.identity_str = 'parents'
    }
    else if (this.identity === 2) {
      this.identity_str = 'teacher'
    }
    this.getDosageList()
  },


}
</script>

<style scoped>

.request-kid-name {
  font-size:22px;
  display:block;
  text-align:left; 
  margin-bottom:4px;
}
.request-time {
  font-size:14px;
  text-align:right;
}
/* 스크롤 */
.content-container{
  overflow-y:scroll; 
  height:80vh; 
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233,234,239, 0.5);
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233,234,239, 0.5);
  border-radius: 5px;
}
.list-col{
  background-color: rgba(256, 256, 256, 0.7)
}


.profile-img {
  box-shadow: 0px 0px 2px 2px rgba(168, 177, 207, 0.7);
}
.more-btn{
  color: rgb(156, 156, 156);
}
.more-btn:hover {
  color: black;
  
}
.list-col{
  background-color: rgba(256, 256, 256, 0.7)
}
.request-tab {
  font-family: 'NanumSquareRound';
  font-size: 16px;
  font-weight: 900;
}

.border-bottom{
  border-bottom: solid 0.5px #a8b1cf;
}

.letter-back{
  background-color:rgba(102,122,188, 0.1);
  background-size: 100% 100%;
}
.selected {
  background-color: #58679A;
}
</style>