<template>
  <div class="row" style="width:80vw;">

    <div
    class="col-5 mx-auto"
    style="overflow-y:scroll; padding-bottom: 0px;"

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
            <div v-if="requestType === 1">
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
            </div>
            

            <div v-if="requestType === 2">
              <!-- 귀가 리스트 띄우기-->
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

            </div>
            


            
            <!-- <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading> -->
          </v-list-item-group>
        </v-list>
      </v-col>
    </div>
    

    <RequestDetail
    :identity="identity"
    :requestType="requestType"
    :id="id"
    />



  </div>
</template>

<script>
import RequestDetail from '@/components/Request/RequestDetail.vue';
import requestApi from '@/api/request.js';
// import InfiniteLoading from "vue-infinite-loading";
export default {
  name: "RequestList",
  components:{
    RequestDetail,
    // InfiniteLoading,
  },
  data () {
    return {
      // 선택된 글 표시 -> 글의 id값으로 처리하기.
      // selectedRequest: 1,

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

      // 무한스크롤 용 pageNum
      pageNum: 1,
      // 전체 페이지 수
      possiblePageNum: 0 ,
    }
  },
  methods: {

    // infiniteHandler($state) {
    //   // 현건이한테 전체 페이지 수 받아서 처리하기.
    //   if (this.pageNum > 3) {
    //     $state.complete();
    //   }
    //   else {
    //     setTimeout(() => {
    //       $state.loaded();
    //       this.getRequest()
    //     },1000)
    //   }
    // },

    // 글을 클릭했을때 id값 저장
    setDetail(id) {
      this.id = id
    },

    getDosage() {
      this.creating = 0
      if (this.requestType !== 1) {
        this.requestType = 1
        this.pageNum = 0
        this.getDosageList()
      }
    },
    getReturnHome() {
      this.creating = 0
      if (this.requestType !== 2) {
        this.requestType = 2
        this.pageNum = 0
        this.getReturnHomeList()
      }
    },

    // 전체 글 불러오기. (투약 -> requestType = 1, 귀가 -> requestType = 2)
    async getDosageList() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        requestType: this.requestType,
        userId: this.$store.state.user.userId,
        pageNum: this.pageNum
      }
      let result = await requestApi.getRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.dosageList.push(...result.dosageList)
      this.pageNum += 1
      
      if (this.id === 0) {
        this.id = this.dosageList[0].dosageId
      }
    },


    async getReturnHomeList() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        requestType: this.requestType,
        userId: this.$store.state.user.userId,
        pageNum: this.pageNum
      }
      let result = await requestApi.getRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.returnHomeList.push(...result.returnHomeList)
      // this.requestList = result.returnhomeList

      this.pageNum += 1
      
      if (this.id === 0) {
        // 첫번째 글의 디테일 페이지 디폴트 값으로 올리기.
        this.id = this.returnHomeList[0].rhId
        
      }
    },



    // async getRequest() {
    //   let accessToken = sessionStorage.getItem('access-token')
    //   let refreshToken = sessionStorage.getItem('refresh-token')
    //   let data = {
    //     type: this.identity_str,
    //     requestType: this.requestType,
    //     userId: this.$store.state.user.userId,
    //     pageNum: this.pageNum
    //   }
    //   let result = await requestApi.getRequest(data, {
    //     "access-token": accessToken,
    //     "refresh-token": refreshToken,
    //   });
    //   if (this.requestType === 1) {
    //     this.dosageList.push(...result.dosageList)
    //     // this.requestList = result.dosageList
    //   }
    //   else if (this.requestType === 2) {
    //     this.returnHomeList.push(...result.returnHomeList)
    //     // this.requestList = result.returnHomeList
    //   }
    //   this.pageNum += 1
      
    //   if (this.id === 0) {
    //     // 첫번째 글의 디테일 페이지 디폴트 값으로 올리기.
    //     if (this.requestType === 1) {
    //       this.id = this.requestList[0].dosageId
    //     }
    //     else if (this.requestType === 2){
    //       this.id = this.requestList[0].rhId
    //     }
    //   }
    // },
  },


  // 상위 컴포넌트의 created -> 하위 컴포넌트의 created -> 하위 컴포넌트의 mounted -> 상위 컴포넌트의 mounted
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