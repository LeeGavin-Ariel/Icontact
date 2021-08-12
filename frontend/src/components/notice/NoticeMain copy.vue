<template>
  <div class="row letter-back" style="width: 82vw; margin: 0">
    <div  style="padding-bottom: 0px; width: 38%">
      <!-- 공지 사항 리스트 -->
      <v-col >
        <v-tabs
          color="mainColor1"
          background-color="transparent"
          class="mt-3 mb-5"
        >
          <v-tab class="notice-tab" @click="showNotice">공지</v-tab>
          <v-tab class="notice-tab" @click="showSchedule">일정</v-tab>
          <v-tab class="notice-tab" @click="showMenu">식단</v-tab>
          <v-tabs-slider color="#A8B1CF"></v-tabs-slider>
        </v-tabs>

        <!-- <v-list two-line>
          <v-list-item-group active-class="pink--text"> -->
        <!-- 공지사항 리스트 띄우기 -->
        <div
          class="container"
          id="noticeListContainer"
          v-if="noticeType === 1"
          style="overflow-y: scroll"
        >
          <template v-for="notice in noticeList">
            <notice-list-item
              :key="notice.createDate"
              :noticeInfo="notice"
              @click="setDetail(notice.noticeId)"
            />
          </template>

          <button class="mt-2 moreBtn" @click="getMoreNoticeList">더보기</button>
        
          <!-- <v-btn
            class="showMoreBtn"
            outlined
            color="indigo"
            @click="getMoreNoticeList"
          >
            더보기
          </v-btn> -->
        </div>

        <!-- 일정 리스트 띄우기-->
        <div
          class="container"
          v-if="noticeType === 2"
          style="overflow-y: scroll; height: 80vh"
        >
          <template v-for="schedule in scheduleList">
            <schedule-list-item
              :key="schedule.createDate"
              :scheduleInfo="schedule"
              @click="setDetail(schedule.scheduleId)"
            />
          </template>
          <v-row fluid dense justify="center" no-gutters>
            <v-col></v-col>
            <v-col>
              <v-btn
                class="mt-5"
                outlined
                color="indigo"
                @click="getMoreScheduleList"
              >
                더보기
              </v-btn>
            </v-col>
            <v-col></v-col>
          </v-row>
        </div>

        <!-- 식단 리스트 띄우기-->
        <div v-if="noticeType === 3" style="overflow-y: scroll; height: 80vh">
          <template v-for="menu in menuList">
            <menu-list-item
              :key="menu.createDate"
              :menuInfo="menu"
              @click="setDetail(menu.menuId)"
            />
          </template>
          <v-row fluid dense justify="center" no-gutters>
            <v-col></v-col>
            <v-col>
              <v-btn
                class="mt-5"
                outlined
                color="indigo"
                @click="getMoreMenuList"
              >
                더보기
              </v-btn>
            </v-col>
            <v-col></v-col>
          </v-row>
        </div>
        <!-- </v-list-item-group>
        </v-list> -->
      </v-col>
    </div>

    <notice-view
      v-if="this.noticeType == 1"
      :id="id"
      @createNotice="initRequestList(1, 'create')"
      @updateNotice="afterUpdate"
      @deleteNotice="initRequestList(1, 'delete')"
    />
    <schedule-view
      v-if="this.noticeType == 2"
      :id="id"
      @createSchedule="initRequestList(2, 'create')"
      @updateSchedule="afterUpdate"
      @deleteSchedule="initRequestList(2, 'delete')"
    />
    <menu-view
      v-if="this.noticeType == 3"
      :id="id"
      @createMenu="initRequestList(3, 'create')"
      @updateMenu="afterUpdate"
      @deleteMenu="initRequestList(3, 'delete')"
    />
  </div>
</template>

<script>
import noticeApi from "@/api/notice.js";
import scheduleApi from "@/api/schedule.js";
import menuApi from "@/api/menu.js";
import NoticeView from "./noticePage/NoticeView.vue";
import NoticeListItem from "./noticePage/NoticeListItem2.vue";
import MenuView from "./menuPage/MenuView.vue";
import MenuListItem from "./menuPage/MenuListItem.vue";
import ScheduleView from "./schedulePage/ScheduleView.vue";
import ScheduleListItem from "./schedulePage/ScheduleListItem.vue";

export default {
  name: "NoticeMain",
  components: {
    NoticeView,
    MenuView,
    ScheduleView,
    NoticeListItem,
    ScheduleListItem,
    MenuListItem,
  },
  data() {
    return {
      // 디테일 값을 얻어 오기 위한 글의 아이디값
      id: 0,

      noticeType: 1,
      noticeView: true,
      scheduleView: false,
      menuView: false,

      //목록 리스트
      noticeList: [],
      scheduleList: [],
      menuList: [],

      // 더보기
      noticePageNum: 1,
      schedulePageNum: 1,
      menuPageNum: 1,
      // 더보기 전체 페이지 수
      noticePageCnt: 1,
      schedulePageCnt: 1,
      menuPageCnt: 1,
    };
  },
  methods: {
    afterUpdate(id) {
      if (this.noticeType == 1) {
        this.noticeList = [];
        this.noticePageNum = 1;
        this.getNoticeList();
      } else if (this.noticeType == 2) {
        this.scheduleList = [];
        this.schedulePageNum = 1;
        this.getScheduleList();
      } else if (this.noticeType == 3) {
        this.menuList = [];
        this.menuPageNum = 1;
        this.getMenuList();
      }
      this.setDetail(id);
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
        this.scheduleList = [];
        this.schedulePageNum = 1;
        this.getScheduleList();
        this.id = 0;
        if (mode == "update") this.setDetail(this.id);
      } else if (noticeType === 3) {
        this.menuList = [];
        this.menuPageNum = 1;
        this.getMenuList();
        this.id = 0;
        if (mode == "update") this.setDetail(this.id);
      }
    },

    // 일반공지 더보기 버튼
    getMoreNoticeList() {
      if (this.noticePageNum <= this.noticePageCnt) {
        this.getNoticeList();
      }
    },

    // 일정 더보기 버튼
    getMoreScheduleList() {
      if (this.schedulePageNum <= this.schedulePageCnt) {
        this.getScheduleList();
      }
    },

    // 식단 더보기 버튼
    getMoreMenuList() {
      if (this.menuPageNum <= this.menuPageCnt) {
        this.getMenuList();
      }
    },

    // 글을 클릭했을때 id값 저장
    setDetail(id) {
      this.id = id;
    },

    // 공지 일정 식단 페이지를 변경하는 함수
    changeView(notice, schedule, menu) {
      this.id = 0;
      this.noticeView = notice;
      this.menuView = schedule;
      this.scheduleView = menu;

      if (notice) {
        this.noticeType = 1;
        console.log("this.noticeList[0].noticeId");
        console.log(this.noticeList[0].noticeId);
      } else if (schedule) {
        this.noticeType = 2;
        console.log("this.scheduleList[0].scheduleId");
        console.log(this.scheduleList[0].scheduleId);
        // this.setDetail(this.scheduleList[0].scheduleId);
      } else if (menu) {
        this.noticeType = 3;
        console.log("this.menuList[0].menuId");
        console.log(this.menuList[0].menuId);
        // this.setDetail(this.menuList[0].menuId);
      }

      console.log("ID");
      console.log(this.id);
    },

    // 공지 탭 눌렀을때
    async showNotice() {
      await this.changeView(true, false, false);
      await this.setDetail(this.noticeList[0].noticeId);
    },

    // 일정 탭 눌렀을때
    async showSchedule() {
      await this.changeView(false, true, false);
      await this.setDetail(this.scheduleList[0].scheduleId);
    },

    // 식단 탭 눌렀을때
    async showMenu() {
      await this.changeView(false, false, true);
      await this.setDetail(this.menuList[0].menuId);
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

      this.noticePageCnt = result.pageCnt;
      this.noticeList.push(...result.noticeList);
      this.noticePageNum += 1;

      console.log("공지사항리스트");
      console.log(this.noticeList);

      if (this.id === 0) {
        this.id = this.noticeList[0].noticeId;
      }
    },

    //일정 조회
    async getScheduleList() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        scheduleType: 2,
        userId: this.$store.state.user.userId,
        pageNum: this.schedulePageNum,
      };

      let result = await scheduleApi.getSchedule(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.schedulePageCnt = result.pageCnt;
      this.scheduleList.push(...result.scheduleList);
      this.schedulePageNum += 1;

      console.log("일정리스트");
      console.log(this.scheduleList);
      if (this.id === 0) {
        this.id = this.scheduleList[0].scheduleId;
      }
    },
    //식단 조회
    async getMenuList() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        menuType: 3,
        userId: this.$store.state.user.userId,
        pageNum: this.menuPageNum,
      };

      let result = await menuApi.getMenu(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      this.menuPageCnt = result.pageCnt;
      this.menuList.push(...result.menuList);
      this.menuPageNum += 1;

      console.log("식단리스트");
      console.log(this.menuList);
      if (this.id === 0) {
        this.id = this.menuList[0].menuId;
      }
    },

    async init() {
      console.log("노티스 메뉴 이닛");
      await this.getNoticeList();
      await this.getScheduleList();
      await this.getMenuList();
    },
  },

  created() {
    this.init();
  },
};
</script>

<style scoped>
/* 스크롤바 너비 */
.container::-webkit-scrollbar {
  width: 3px;
}

/* 현재 스크롤의 위치바의 색 */
.container::-webkit-scrollbar-thumb {
  background-color: white;
}

/* 남는공간의 색 */
.container::-webkit-scrollbar-track {
  background-color: black;
}

.container {
  height: 84vh;
  padding: 5px;
  background: white;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-content: flex-start;
}

.notice-tab {
  font-size: 20px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}

.letter-back {
  background-color: rgba(102, 122, 188, 0.1);
  background-size: 100% 100%;
}
.showMoreBtn{
  margin-top: 1rem;
}
.moreBtn{
  color: rgb(156, 156, 156);
}
.moreBtn:hover {
  color: black;
  
}
</style>