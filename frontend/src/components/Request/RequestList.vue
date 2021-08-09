<template>
  <div class="row" style="width:80vw; margin:0;">

    <div
    class="col-5 mx-auto"
    style="padding-bottom: 0px;"

    >
      <!-- 요청 사항 리스트 -->
      <v-col
        class="mx-auto"
      >
        <button @click="getDosage">투약 요청</button>
        <div style="display:inline; margin-right:10px;"></div>
        <button @click="getReturnHome">귀가 동의</button>
        <v-list two-line>
          <v-list-item-group
            active-class="pink--text"
            
          >


            <!-- 투약 리스트 띄우기 -->
            <div v-if="requestType === 1" style="overflow-y:scroll; height:80vh;">
              <template v-for="(request, index) in dosageList" >
                <v-list-item v-if="requestType === 1" :key="request.createDate" @click="setDetail(request.dosageId)">
                  <template >
                    <v-list-item-content >
                      <v-list-item-title v-text="request.kidName"></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="request.headline"
                      ></v-list-item-subtitle>

                      <v-list-item-subtitle v-text="request.dosageTime"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="request.createDate"></v-list-item-action-text>

                    </v-list-item-action>
                  </template>
                </v-list-item>

                <!-- <v-list-item v-if="requestType === 2" :key="request.createDate" @click="setDetail(request.rhId)">
                  <template>
                    <v-list-item-content>
                      <v-list-item-title v-text="request.kidName"></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="request.headline"
                      ></v-list-item-subtitle>

                      <v-list-item-subtitle v-text="request.rhTime"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="request.createDate"></v-list-item-action-text>

                    </v-list-item-action>
                  </template>
                </v-list-item> -->

                <v-divider
                  v-if="index < dosageList.length - 1"
                  :key="index"
                ></v-divider>

              </template>
              <button @click="getMoreDosageList">더보기</button>
              <!-- <infinite-loading @infinite="dosageInfiniteHandler" spinner="waveDots"></infinite-loading> -->
            </div>
            

            <!-- 귀가 리스트 띄우기-->
            <div v-if="requestType === 2" style="overflow-y:scroll; height:80vh;">
              <template v-for="(request, index) in returnHomeList" >
                <!-- <v-list-item v-if="requestType === 1" :key="request.createDate" @click="setDetail(request.dosageId)">
                  <template >
                    <v-list-item-content >
                      <v-list-item-title v-text="request.kidName"></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="request.headline"
                      ></v-list-item-subtitle>

                      <v-list-item-subtitle v-text="request.dosageTime"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="request.createDate"></v-list-item-action-text>

                    </v-list-item-action>
                  </template>
                </v-list-item> -->

                <v-list-item v-if="requestType === 2" :key="request.createDate" @click="setDetail(request.rhId)">
                  <template>
                    <v-list-item-content>
                      <v-list-item-title v-text="request.kidName"></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="request.headline"
                      ></v-list-item-subtitle>

                      <v-list-item-subtitle v-text="request.rhTime"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="request.createDate"></v-list-item-action-text>

                    </v-list-item-action>
                  </template>
                </v-list-item>

                <v-divider
                  v-if="index < returnHomeList.length - 1"
                  :key="index"
                ></v-divider>
              </template>
              <button @click="getMoreReturnhomeList">더보기</button>
              <!-- <infinite-loading @infinite="returnhomeInfiniteHandler" spinner="waveDots"></infinite-loading> -->
            </div>
            


          
          </v-list-item-group>
        </v-list>
      </v-col>
    </div>
    

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
    setDetail(id) {
      this.id = id
    },


    // 투약 탭 눌렀을때
    getDosage() {
      

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
    getReturnHome() {
      
      this.creating = 0
      if (this.requestType !== 2) {
        
        this.requestType = 2
        
        if (this.returnHomeList.length === 0) {
          this.getReturnHomeList()
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

<style>

</style>