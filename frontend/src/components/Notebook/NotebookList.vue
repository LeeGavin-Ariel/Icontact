<template>
  <div class="row letter-back" style="width: 82vw; margin: 0">
    <div class="mx-auto" style="padding-bottom: 0px; width: 38%">
      <!-- 노트북 리스트 -->
      <v-col class="mx-auto">
        <!-- 검색  -->
        <div align="center">
          <input
            type="text"
            class="searchInput mb-1"
            placeholder="이름으로 검색"
            @input="searchPerson = $event.target.value"
            @keypress.enter="search"
          />
          <button class="searchBtn" @click="search">검색</button>
          <button class="searchBtn" @click="getNotebookList">전체보기</button>
        </div>

        <!-- 알림장 리스트 -->
        <div
          v-if="!searchFlag"
          class="content-container list-col mt-5"
          style="height: 80vh"
        >
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="(note, index) in notebookList">
              <div
                class="list-group list-group-flush scrollarea border-bottom"
                :class="{ selected: idx == index }"
                :key="note.createDate"
                @click="setDetail(note.noteId, index)"
              >
                <div
                  class="list-group-item list-group-item-action py-2 lh-tight"
                  style="background-color: rgb(256, 256, 256, 0.7)"
                >
                  <div
                    class="d-flex align-items-center"
                    style="height: 9vh; width: 100%"
                  >
                    <!-- 리스트 내용 -->
                    <div align="center" class="col-3">
                      <div class="mb-2">
                        <img
                          class="profile-img"
                          :src="
                            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                            note.profileImg
                          "
                        />
                      </div>
                      <!-- 학부모일 경우 -->
                      <div
                        v-if="$store.state.user.type === 1"
                        class="note-list-name"
                      >
                        {{ note.writerName }} 선생님
                      </div>
                      <!-- 선생님일 경우 -->
                      <div
                        v-if="$store.state.user.type === 2"
                        class="note-list-name"
                      >
                        {{ note.targetName }} 학부모님
                      </div>
                    </div>
                    <div class="col-7">
                      <div class="note-title mb-1">{{ trimTitle(note.title) }}</div>
                      <div class="note-time mt-1" align="right">
                        {{ dateform(note.createDate) }}
                      </div>
                    </div>
                    <div class="col-2" align="center">
                      <img
                        src="@/assets/flaticon/alarm.png"
                        style="width: 1.6rem"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="index < notebookList.length - 1" :key="index"></div>
            </template>

            <!-- 더보기 버튼 -->
            <button
              class="mt-2 more-btn"
              @click="getMoreNotebooklist"
              v-if="notebookList.length > 0 && pageNum < pageCnt"
            >
              더보기
            </button>
          </div>
        </div>

        <!-- 알림장 검색 리스트 -->
        <div
          v-if="searchFlag"
          class="content-container list-col mt-5"
          style="overflow-y: scroll; height: 80vh"
        >
          <div
            class="d-flex flex-column align-items-stretch flex-shrink-0"
            style="width: 100%"
          >
            <template v-for="(note, index) in searchedNotebookList">
              <div
                class="list-group list-group-flush scrollarea border-bottom"
                :class="{ selected: idx == index }"
                :key="note.noteId"
                @click="setDetail(note.noteId, index)"
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
                            note.profileImg
                          "
                        />
                      </div>
                      <!-- 학부모일 경우 -->
                      <div
                        v-if="$store.state.user.type === 1"
                        class="note-list-name"
                      >
                        {{ note.writerName }} 선생님
                      </div>
                      <!-- 선생님일 경우 -->
                      <div
                        v-if="$store.state.user.type === 2"
                        class="note-list-name"
                      >
                        {{ note.targetName }} 학부모님
                      </div>
                    </div>
                    <div class="col-7">
                      <div class="note-title mb-1">{{ note.title }}</div>
                      <div class="note-time mt-1" align="right">
                        {{ dateform(note.createDate) }}
                      </div>
                    </div>
                    <div class="col-2" align="center">
                      <img
                        src="@/assets/flaticon/alarm.png"
                        style="width: 1.6rem"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div
                v-if="index < searchedNotebookList.length - 1"
                :key="index"
              ></div>
            </template>

            <!-- 더보기 버튼 -->
            <button
              class="mt-2 more-btn"
              @click="getMoreSearchNotebooklist"
              v-if="
                searchedNotebookList.length > 0 && searchPageNum < searchPageCnt
              "
            >
              더보기
            </button>
          </div>
        </div>
      </v-col>
    </div>

    <NotebookDetail
      :identity="identity"
      :id="id"
      @get-notebooklist="initNotebookList"
    />
  </div>
</template>

<script>
import notebookApi from "@/api/notebook.js";
import NotebookDetail from "@/components/Notebook/NotebookDetail.vue";
export default {
  name: "NotebookList",
  components: {
    NotebookDetail,
  },
  data() {
    return {
      id: 0,
      idx: 0,

      identity: 0,
      identity_str: "",

      userId: "",
      notebookList: [],

      pageNum: 0,
      pageCnt: 0,

      searchFlag: 0,
      searchPerson: "",
      searchedNotebookList: [],

      searchPageNum: 0,
      searchPageCnt: 0,
    };
  },

  methods: {
    trimTitle(title) {
      if (title.length > 9) {
        title = title.substr(0, 9) + "...";
      }
      return title;
    },
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
    initNotebookList() {
      this.id = 0;
      this.searchFlag = 0;
      this.notebookList = [];
      this.searchedNotebookList = [];
      this.pageNum = 0;
      this.pageCnt = 0;
      this.getNotebookList();
    },

    getMoreNotebooklist() {
      if (this.pageNum <= this.pageCnt) {
        this.getNotebookList();
      }
    },

    getMoreSearchNotebooklist() {
      if (this.searchPageNum <= this.searchPageCnt) {
        this.getSearchNotebookList();
      }
    },

    search() {
      this.idx = 0;
      this.searchPageNum = 0;
      this.searchedNotebookList = [];
      this.id = 0;
      this.searchPageCnt = 0;
      this.getSearchNotebookList();
    },

    setDetail(Id, index) {
      this.id = Id;
      this.idx = index;
    },

    async getNotebookList() {
      this.searchedNotebookList = [];
      this.idx = 0;
      this.searchFlag = 0;
      this.searchPageNum = 0;
      this.searchPageCnt = 0;
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let data = {
        type: this.identity_str,
        userId: this.userId,
        pageNum: this.pageNum,
      };
      let result = await notebookApi.getNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      if (result.length !== 0) {
        this.pageCnt = result[0].totalNum;
      } else {
        this.pageCnt = 0;
      }
      this.notebookList.push(...result);
      this.pageNum += 1;

      if (this.id === 0) {
        if (this.notebookList.length !== 0) {
          this.id = this.notebookList[0].noteId;
        }
      } else {
        if (this.notebookList.length === 0) {
          this.id = 0;
        } else {
          this.id = this.notebookList[0].noteId;
        }
      }
    },

    // 검색 관련
    async getSearchNotebookList() {
      this.pageNum = 0;
      this.pageCnt = 0;
      this.idx = 0;
      this.notebookList = [];
      this.searchFlag = 1;
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        userId: this.userId,
        pageNum: this.searchPageNum,
        searchParam: this.searchPerson,
      };
      let result = await notebookApi.getSearchNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.searchPageCnt = result.pageCnt;
      this.searchedNotebookList.push(...result.result);
      this.searchPageNum += 1;
      if (this.id === 0) {
        if (this.searchedNotebookList.length !== 0) {
          this.id = this.searchedNotebookList[0].noteId;
        }
      } else {
        if (this.searchedNotebookList.length === 0) {
          this.id = 0;
        } else {
          this.id = this.searchedNotebookList[0].noteId;
        }
      }
    },
  },

  created() {
    this.identity = this.$store.state.user.type;
    this.userId = this.$store.state.user.userId;
    if (this.identity === 1) {
      this.identity_str = "parents";
    } else if (this.identity === 2) {
      this.identity_str = "teacher";
    }
    this.getNotebookList();
  },
};
</script>

<style scoped>
.searchInput {
  background-color: rgba(256, 256, 256, 0.7);
  border-radius: 20px;
  height: 36px;
  width: 60%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.searchBtn {
  background-color: rgba(168, 177, 207, 1);
  border-radius: 70px;
  height: 36px;
  width: 15%;
  margin: 20px 3px 3px 3px;
  text-align: center;
  color: rgba(256, 256, 256);
  letter-spacing: -1px;
}

.note-list-name {
  font-size: 0.8rem;
  display: block;
  font-weight: 900;
}
.note-time {
  font-size: 13px;
}
.note-title {
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

.border-bottom {
  border-bottom: solid 0.5px #a8b1cf;
}

.letter-back {
  background-color: rgba(102, 122, 188, 0.1);
  background-size: 100% 100%;
}
.selected {
  background-color: #58679a;
}
</style>



