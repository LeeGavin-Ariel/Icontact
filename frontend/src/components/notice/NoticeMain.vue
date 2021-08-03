<template>
  <div class="row" style="width: 80vw; margin: 0">
    <div class="col-5 mx-auto" style="padding-bottom: 0px">
      <!-- 공지 사항 리스트 -->
      <v-col class="mx-auto">
        <button @click="showNotice">공지</button>
        <div style="display: inline; margin-right: 10px"></div>
        <button @click="showSchedule">일정</button>
        <div style="display: inline; margin-right: 10px"></div>
        <button @click="showMenu">식단</button>
        <v-list two-line>
          <v-list-item-group active-class="pink--text">
            <!-- 공지사항 리스트 띄우기 -->
            <div
              v-if="noticeType === 1"
              style="overflow-y: scroll; height: 80vh"
            >
              <template v-for="(notice, index) in noticeList">
                <v-list-item
                  v-if="noticeType === 1"
                  :key="notice.createDate"
                  @click="setDetail(notice.noticeId)"
                >
                  <template>
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="notice.noticeId"
                      ></v-list-item-title>

                      <v-list-item-title
                        v-text="notice.title"
                      ></v-list-item-title>

                      <v-list-item-subtitle
                        v-text="notice.userId"
                      ></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text
                        v-text="notice.createDate"
                      ></v-list-item-action-text>
                    </v-list-item-action>
                  </template>
                </v-list-item>

                <v-divider
                  v-if="index < noticeList.length - 1"
                  :key="index"
                ></v-divider>
              </template>
              <button @click="getMoreNoticeList">더보기</button>
            </div>

            <!-- 공지사항 리스트 띄우기-->
            <div
              v-if="noticeType === 2"
              style="overflow-y: scroll; height: 80vh"
            >
              <template v-for="(request, index) in returnHomeList">
                <v-list-item
                  v-if="noticeType === 2"
                  :key="request.createDate"
                  @click="setDetail(request.rhId)"
                >
                  <template>
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="request.kidName"
                      ></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="request.headline"
                      ></v-list-item-subtitle>

                      <v-list-item-subtitle
                        v-text="request.rhTime"
                      ></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text
                        v-text="request.createDate"
                      ></v-list-item-action-text>
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

    <notice-view
      v-if="this.noticeType == 1"
      :id="id"
      @createNotice="initRequestList(1, 'create')"
      @updateNotice="afterUpdate"
    />
    <schedule-view v-if="this.noticeType == 2" :id="id" />
    <menu-view v-if="this.noticeType == 3" :id="id" />
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
import NoticeView from "./noticeView/NoticeView.vue";
import MenuView from "./MenuView.vue";
import ScheduleView from "./ScheduleView.vue";

export default {
  name: "NoticeList",
  components: {
    NoticeView,
    MenuView,
    ScheduleView,
  },
  data() {
    return {
      // 디테일 값을 얻어 오기 위한 글의 아이디값
      id: 0,

      noticeType: 1,
      noticeView: true,
      scheduleView: false,
      menuView: false,

      // 관계
      identity: 0,
      identity_str: "",

      noticeList: [],
      returnHomeList: [],

      // 더보기
      noticePageNum: 1,
      returnhomePageNum: 1,
      // 더보기 전체 페이지 수
      noticePageCnt: 1,
      returnhomePageCnt: 1,
    };
  },
  methods: {
    afterUpdate(id) {
      this.noticeList = [];
      this.noticePageNum = 1;
      this.setDetail(id);

      this.getNoticeList();
    },
    // 글 작성, 수정, 삭제 이벤트 발생시 다시 목록 조회.
    initRequestList(noticeType, mode) {
      if (noticeType === 1) {
        this.noticeList = [];
        this.noticePageNum = 1;
        this.id = 0;
        if (mode == "update") this.setDetail(this.id);

        this.getNoticeList();
      } else if (noticeType === 2) {
        this.returnHomeList = [];
        this.returnhomePageNum = 1;
        this.getReturnHomeList();
      }
    },

    // 더보기 버튼
    getMoreNoticeList() {
      if (this.noticePageNum <= this.noticePageCnt) {
        this.getNoticeList();
      }
    },

    // 글을 클릭했을때 id값 저장
    setDetail(id) {
      this.id = id;
    },

    // 공지 일정 식단 페이지를 변경하는 함수
    changeView(notice, schedule, menu) {
      this.noticeView = notice;
      this.menuView = schedule;
      this.scheduleView = menu;

      if (notice) this.noticeType = 1;
      else if (schedule) this.noticeType = 2;
      else if (menu) this.noticeType = 3;
    },

    // 공지 탭 눌렀을때
    showNotice() {
      this.changeView(true, false, false);
    },

    // 일정 탭 눌렀을때
    showSchedule() {
      this.changeView(false, true, false);
    },

    // 식단 탭 눌렀을때
    showMenu() {
      this.changeView(false, false, true);
    },

    //공지사항 조회
    async getNoticeList() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        // type: this.identity_str,
        noticeType: this.noticeType,
        userId: this.$store.state.user.userId,
        pageNum: this.noticePageNum,
      };
      let result = await noticeApi.getNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log(result);

      this.noticePageCnt = result.pageCnt;
      this.noticeList.push(...result.noticeList);
      this.noticePageNum += 1;

      if (this.id === 0) {
        this.id = this.noticeList[0].noticeId;
      }
    },
  },

  created() {
    this.getNoticeList();
  },
};
</script>

<style>
</style>