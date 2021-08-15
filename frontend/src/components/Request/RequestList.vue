<template>
  <div class="row bg" style="width: 82vw; margin: 0">
    <div class="mx-auto" style="padding-bottom: 0px; width: 38%">
      <!-- 요청사항 리스트 -->
      <v-col class="mx-auto">
        <!-- 요청사항 버튼 -->
        <v-tabs
          color="mainColor1"
          background-color="transparent"
          class="mt-3 mb-5"
        >
          <v-tab class="request-tab" @click="getDosage">투약 요청</v-tab>
          <v-tab class="request-tab" @click="getReturnHome">귀가 동의</v-tab>
          <v-tabs-slider color="#A8B1CF"></v-tabs-slider>
        </v-tabs>

        <!-- 투약 요청 리스트 -->
        <div
          v-if="requestType === 1"
          class="content-container list-col"
          style="overflow-y: scroll; height: 80vh"
        >
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="(request, index) in dosageList">
              <div
                class="list-group list-group-flush scrollarea border-bottom"
                :class="{ selected: idx == index }"
                v-if="requestType === 1"
                :key="request.createDate"
                @click="setDetail(request.dosageId, index)"
              >
                <div
                  class="list-group-item list-group-item-action py-2 lh-tight"
                  style="background-color: rgb(256, 256, 256, 0.7)"
                >
                  <div
                    class="d-flex align-items-center"
                    style="height: 9vh; width: 100%"
                  >
                    <div align="center" class="col-3">
                      <div class="mb-2">
                        <img
                          class="profile-img"
                          :src="
                            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                            request.profileImg
                          "
                        />
                      </div>
                      <div class="request-kid-name">
                        {{ request.kidName }} 학부모님
                      </div>
                    </div>
                    <div class="col-7">
                      <div class="request-title mb-1">투약 요청</div>
                      <div class="request-time mt-1" align="right">
                        {{ dateform(request.createDate) }}
                      </div>
                    </div>
                    <div class="col-2" align="center">
                      <img
                        src="@/assets/flaticon/letter.png"
                        style="width: 1.8rem"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="index < dosageList.length - 1" :key="index"></div>
            </template>

            <!-- 더보기 버튼 -->
            <button
              class="mt-2 more-btn"
              @click="getMoreDosageList"
              v-if="dosageList.length > 0 && dosagePageNum <= dosagePageCnt"
            >
              더보기
            </button>
          </div>
        </div>

        <!-- 귀가 동의 리스트 -->
        <div
          v-if="requestType === 2"
          class="content-container list-col"
          style="overflow-y: scroll; height: 80vh"
        >
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="(request, index) in returnHomeList">
              <div
                class="list-group list-group-flush scrollarea border-bottom"
                :class="{ selected: idx == index }"
                v-if="requestType === 2"
                :key="request.createDate"
                @click="setDetail(request.rhId, index)"
              >
                <div
                  class="list-group-item list-group-item-action py-2 lh-tight"
                  style="background-color: rgb(256, 256, 256, 0.7)"
                >
                  <div
                    class="d-flex align-items-center"
                    style="height: 9vh; width: 100%"
                  >
                    <div align="center" class="col-3">
                      <div class="mb-2">
                        <img
                          class="profile-img"
                          :src="
                            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                            request.profileImg
                          "
                        />
                      </div>
                      <div class="request-kid-name">
                        {{ request.kidName }} 학부모님
                      </div>
                    </div>
                    <div class="col-7" style="width: 60%">
                      <div class="request-title mb-1">귀가 동의</div>
                      <div class="request-time mt-1" align="right">
                        {{ dateform(request.createDate) }}
                      </div>
                    </div>
                    <div class="col-2" align="center">
                      <img
                        src="@/assets/flaticon/letter.png"
                        style="width: 1.8rem"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="index < returnHomeList.length - 1" :key="index"></div>
            </template>

            <!-- 더보기 버튼 -->
            <button
              class="mt-2 more-btn"
              @click="getMoreReturnhomeList"
              v-if="
                returnHomeList.length > 0 &&
                returnhomePageNum <= returnhomePageCnt
              "
            >
              더보기
            </button>
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
import RequestDetail from "@/components/Request/RequestDetail.vue";
import requestApi from "@/api/request.js";

export default {
  name: "RequestList",
  components: {
    RequestDetail,
  },
  data() {
    return {
      id: 0,
      idx: 0,
      identity: 0,
      identity_str: "",

      requestType: 1,
      dosageList: [],
      returnHomeList: [],

      dosagePageNum: 1,
      returnhomePageNum: 1,
      dosagePageCnt: 1,
      returnhomePageCnt: 1,

      profileImg: this.$store.state.user.profileImg,
    };
  },

  methods: {
    dateform(content) {
      var dateform = "";
      dateform =
        content.substr(0, 4) +
        "년 " +
        content.substr(5, 2) +
        "월 " +
        content.substr(8, 2) +
        "일";
      return dateform;
    },

    // 글 작성, 수정, 삭제 이벤트 발생시 다시 목록 조회.
    initRequestList(requestType) {
      this.idx = 0;
      if (requestType === 1) {
        this.id = 0;
        this.dosageList = [];
        this.dosagePageNum = 1;
        this.getDosageList();
      } else if (requestType === 2) {
        this.id = 0;
        this.returnHomeList = [];
        this.returnhomePageNum = 1;
        this.getReturnHomeList();
      }
    },

    // 더보기 버튼
    getMoreDosageList() {
      if (this.dosagePageNum <= this.dosagePageCnt) {
        this.getDosageList();
      }
    },

    // 더보기 버튼
    getMoreReturnhomeList() {
      if (this.returnhomePageNum <= this.returnhomePageCnt) {
        this.getReturnHomeList();
      }
    },

    // 글을 클릭했을때 id값 저장
    setDetail(id, index) {
      this.id = id;
      this.idx = index;
    },

    // 투약 탭 눌렀을때
    getDosage() {
      this.idx = 0;
      this.creating = 0;
      if (this.requestType !== 1) {
        this.requestType = 1;
        if (this.dosageList.length === 0) {
          this.getDosageList();
        }
        if (this.dosageList.length !== 0) {
          this.id = this.dosageList[0].dosageId;
        }
      }
    },
    // 귀가 탭 눌렀을때
    async getReturnHome() {
      this.idx = 0;
      this.creating = 0;
      if (this.requestType !== 2) {
        this.requestType = 2;

        if (this.returnHomeList.length === 0) {
          await this.getReturnHomeList();
        }
        if (this.returnHomeList.length !== 0) {
          this.id = this.returnHomeList[0].rhId;
        }
      }
    },

    async getDosageList() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        type: this.identity_str,
        requestType: this.requestType,
        userId: this.$store.state.user.userId,
        pageNum: this.dosagePageNum,
      };
      let result = await requestApi.getRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.dosagePageCnt = result.pageCnt;
      this.dosageList.push(...result.dosageList);
      this.dosagePageNum += 1;

      if (this.id === 0) {
        if (this.dosageList.length !== 0) {
          this.id = this.dosageList[0].dosageId;
        }
      } else {
        if (this.dosageList.length === 0) {
          this.id = 0;
        }
      }
    },
    // 귀가 전체 글 조회
    async getReturnHomeList() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        type: this.identity_str,
        requestType: this.requestType,
        userId: this.$store.state.user.userId,
        pageNum: this.returnhomePageNum,
      };
      let result = await requestApi.getRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.returnhomePageCnt = result.pageCnt;
      this.returnHomeList.push(...result.returnhomeList);

      this.returnhomePageNum += 1;
      if (this.id === 0) {
        if (this.returnHomeList.length !== 0) {
          this.id = this.returnHomeList[0].rhId;
        }
      } else {
        if (this.returnHomeList.length === 0) {
          this.id = 0;
        }
      }
    },
  },

  created() {
    // 페이지 들어오자마자 getRequest 실행 (default 투약요청)
    this.identity = this.$store.state.user.type;
    if (this.identity === 1) {
      this.identity_str = "parents";
    } else if (this.identity === 2) {
      this.identity_str = "teacher";
    }
    this.getDosageList();
  },
};
</script>

<style scoped>
*{
    font-family: "NanumSquareRound";
}
.request-kid-name {
  font-size: 0.8rem;
  display: block;
  font-weight: 900;
}
.request-time {
  font-size: 13px;
}
.request-title {
  font-size: 20px;
}
/* 스크롤 */
.content-container {
  overflow-y: scroll;
  height: 80vh;
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 5px;
}
.list-col {
  background-color: rgba(256, 256, 256, 0.7);
}

.profile-img {
  width: 50%;
  border-radius: 100%;
  box-shadow: 0px 0px 2px 2px rgba(168, 177, 207, 0.7);
}
.more-btn {
  color: rgb(156, 156, 156);
}
.more-btn:hover {
  color: black;
}
.list-col {
  background-color: rgba(256, 256, 256, 0.7);
}
.request-tab {
  font-size: 16px;
  font-weight: 900;
}
.border-bottom {
  border-bottom: solid 0.5px #a8b1cf;
}
.bg {
  background-color: rgba(102, 122, 188, 0.1);
  background-size: 100% 100%;
}
.selected {
  background-color: #58679a;
}
</style>