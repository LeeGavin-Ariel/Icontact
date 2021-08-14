<template>
  <div class="row letter-back" style="width: 82vw; margin: 0">
    <div class="mx-auto" style="padding-bottom: 0px; width: 38%">
      <!-- 공지 사항 리스트 -->
      <v-col class="mx-auto">
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

        <!-- 공지사항 리스트 -->
        <div
          v-if="noticeType === 1"
          class="content-container list-col mt-5"
          style="height: 80vh"
        >
          <!-- v-if="noticeList.length > 0" -->
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="notice in noticeList">
              <!-- :class="{ selected: idx == index }" -->
              <notice-list-item
                :key="notice.createDate"
                :noticeInfo="notice"
                @click="setNoticeDetail(notice.noticeId)"
              />
            </template>
            <button
              class="mt-2 moreBtn"
              @click="getMoreNoticeList"
              v-if="noticeList.length > 0 && noticePageNum <= noticePageCnt"
            >
              더보기
            </button>
          </div>
          <!-- <div v-else>없음</div> -->
        </div>

        <!-- 일정 리스트-->
        <div
          v-if="noticeType === 2"
          class="content-container list-col mt-5"
          style="height: 80vh"
        >
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="schedule in scheduleList">
              <schedule-list-item
                :key="schedule.createDate"
                :scheduleInfo="schedule"
                @click="setScheduleDetail(schedule.scheduleId)"
              />
            </template>

            <button
              class="mt-2 moreBtn"
              @click="getMoreScheduleList"
              v-if="
                scheduleList.length > 0 && schedulePageNum <= schedulePageCnt
              "
            >
              더보기
            </button>
          </div>
        </div>

        <!-- 식단 리스트 -->
        <div
          v-if="noticeType === 3"
          class="content-container list-col mt-5"
          style="height: 80vh"
        >
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="menu in menuList">
              <menu-list-item
                :key="menu.createDate"
                :menuInfo="menu"
                @click="setMenuDetail(menu.menuId)"
              />
            </template>

            <button
              class="mt-2 moreBtn"
              @click="getMoreMenuList"
              v-if="menuList.length > 0 && menuPageNum <= menuPageCnt"
            >
              더보기
            </button>
          </div>
        </div>

        <!-- </v-list-item-group>
        </v-list> -->
      </v-col>
    </div>

    <notice-view
      v-if="this.noticeType == 1"
      :id="noticeId"
      @createNotice="afterCreate"
      @updateNotice="afterUpdate"
      @deleteNotice="afterDelete"
    />
    <schedule-view
      v-if="this.noticeType == 2"
      :id="scheduleId"
      @createSchedule="afterCreate"
      @updateSchedule="afterUpdate"
      @deleteSchedule="afterDelete"
    />
    <menu-view
      v-if="this.noticeType == 3"
      :id="menuId"
      @createMenu="afterCreate"
      @updateMenu="afterUpdate"
      @deleteMenu="afterDelete"
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
      noticeId: 0,
      scheduleId: 0,
      menuId: 0,

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
    async afterCreate() {
      console.log("after create");
      if (this.noticeType == 1) {
        this.noticeList = [];
        this.noticePageNum = 1;
        await this.getNoticeList();
        this.setNoticeDetail(this.noticeList[0].noticeId);
      } else if (this.noticeType == 2) {
        this.scheduleList = [];
        this.schedulePageNum = 1;
        await this.getScheduleList();
        this.setScheduleDetail(this.scheduleList[0].scheduleId);
      } else if (this.noticeType == 3) {
        this.menuList = [];
        this.menuPageNum = 1;
        await this.getMenuList();
        this.setMenuDetail(this.menuList[0].menuId);
      }
    },

    async afterUpdate(id) {
      if (this.noticeType == 1) {
        this.noticeList = [];
        this.noticePageNum = 1;
        await this.getNoticeList();
        this.setNoticeDetail(id);
      } else if (this.noticeType == 2) {
        this.scheduleList = [];
        this.schedulePageNum = 1;
        await this.getScheduleList();
        this.setScheduleDetail(id);
      } else if (this.noticeType == 3) {
        this.menuList = [];
        this.menuPageNum = 1;
        await this.getMenuList();
        this.setMenuDetail(id);
      }
    },

    async afterDelete() {
      console.log("after delete");
      if (this.noticeType == 1) {
        this.noticeList = [];
        this.noticePageNum = 1;
        await this.getNoticeList();
        if (this.noticeList.length > 0)
          this.setNoticeDetail(this.noticeList[0].noticeId);
        else this.setNoticeDetail(-1);
      } else if (this.noticeType == 2) {
        this.scheduleList = [];
        this.schedulePageNum = 1;
        await this.getScheduleList();
        if (this.scheduleList.length > 0)
          this.setScheduleDetail(this.scheduleList[0].scheduleId);
        else this.setScheduleDetail(-1);
      } else if (this.noticeType == 3) {
        this.menuList = [];
        this.menuPageNum = 1;
        await this.getMenuList();
        if (this.menuList.length > 0)
          this.setMenuDetail(this.menuList[0].menuId);
        else this.setMenuDetail(-1);
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
    setNoticeDetail(id) {
      console.log("공지 아이디 변경", id);
      this.noticeId = id;
    },
    setScheduleDetail(id) {
      this.scheduleId = id;
    },
    setMenuDetail(id) {
      this.menuId = id;
    },

    // 공지 일정 식단 페이지를 변경하는 함수
    changeView(notice, schedule, menu) {
      // this.id = 0;
      this.noticeView = notice;
      this.menuView = schedule;
      this.scheduleView = menu;

      if (notice) {
        this.noticeType = 1;
        console.log("this.noticeList[0].noticeId");
        // console.log(this.noticeList[0].noticeId);
      } else if (schedule) {
        this.noticeType = 2;
        console.log("this.scheduleList[0].scheduleId");
        // console.log(this.scheduleList[0].scheduleId);
        // this.setDetail(this.scheduleList[0].scheduleId);
      } else if (menu) {
        this.noticeType = 3;
        console.log("this.menuList[0].menuId");
        // console.log(this.menuList[0].menuId);
        // this.setDetail(this.menuList[0].menuId);
      }

      console.log("ID");
      // console.log(this.id);
    },

    // 공지 탭 눌렀을때
    async showNotice() {
      this.scheduleId = 0;
      this.menuId = 0;
      await this.changeView(true, false, false);
      if (this.noticeList[0] != null) {
        console.log("공지탭누름");
        await this.setNoticeDetail(this.noticeList[0].noticeId);
      }
    },

    // 일정 탭 눌렀을때
    async showSchedule() {
      this.noticeId = 0;
      this.menuId = 0;
      await this.changeView(false, true, false);
      if (this.scheduleList[0] != null) {
        await this.setScheduleDetail(this.scheduleList[0].scheduleId);
      }
    },

    // 식단 탭 눌렀을때
    async showMenu() {
      this.noticeId = 0;
      this.scheduleId = 0;
      await this.changeView(false, false, true);
      if (this.menuList[0] != null) {
        await this.setMenuDetail(this.menuList[0].menuId);
      }
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
      console.log(this.noticeList.length === 0);
      console.log(this.noticeList.length);
      console.log(this.noticeList);

      if (this.noticeList.length === 0) {
        this.noticeList = [];
        return;
      }

      if (this.noticeId === 0) {
        console.log("공지id변경");
        // this.noticeId = this.noticeList[0].noticeId;
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
      if (this.scheduleId === 0 && this.scheduleList.length > 0) {
        // this.scheduleId = this.scheduleList[0].scheduleId;
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
      if (this.menuId === 0 && this.menuList.length > 0) {
        // this.menuId = this.menuList[0].menuId;
      }
    },

    async init() {
      console.log("노티스 메뉴 이닛");
      await this.getNoticeList();
      await this.getScheduleList();
      await this.getMenuList();
      this.showNotice();
    },
  },

  created() {
    this.init();
  },
};
</script>

<style scoped>
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

.notice-tab {
  font-size: 16px;
  font-family: "NanumSquareRound";
  font-weight: 900;
}

.letter-back {
  background-color: rgba(102, 122, 188, 0.1);
  background-size: 100% 100%;
}
.showMoreBtn {
  margin-top: 1rem;
}
.moreBtn {
  color: rgb(156, 156, 156);
}
.moreBtn:hover {
  color: black;
}
/*부드러운 스크롤 효과*/
html {
  scroll-behavior: smooth;
}
</style>