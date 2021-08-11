<template>
  <div class="row" style="width:80vw; margin:0;">

    <div
    class="col-5 mx-auto"
    style="padding-bottom: 0px;"
    >
      <!-- 노트북 리스트 -->
      <v-col
        class="mx-auto"
      >

        <!-- 여기가 그 검색창 들어갈 부분 (선생님만) -->
        <input 
        type="text" 
        v-if="identity === 2" 
        @input="searchPerson=$event.target.value"
        @keypress.enter="search">
        <button @click="search" v-if="!searchFlag && identity === 2">검색</button>
        <button @click="getNotebookList" v-if="searchFlag && identity === 2">초기화</button>

        <v-list two-line>
          <v-list-item-group
            active-class="pink--text"
          >

            <!-- 노트북 리스트 띄우기 -->
            <div style="overflow-y:scroll; height:80vh;" v-if="!searchFlag">
              <template v-for="(note, index) in notebookList">
                <v-list-item :key="note.noteId" @click="setDetail(note.noteId)">
                  <template >
                    <v-list-item-content >
                      <v-list-item-title v-text="note.title"></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="note.headline"
                      >
                      </v-list-item-subtitle>

                      <v-list-item-subtitle v-text="note.targetName"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="note.targetId"></v-list-item-action-text>

                    </v-list-item-action>
                  </template>
                </v-list-item>

                <v-divider
                  v-if="index < notebookList.length - 1"
                  :key="index"
                ></v-divider>

              </template>

              <!-- 조건문. 리스트의 길이가 0이거나, 다 불러온 경우에만 더보기 버튼 활성화. -->
              <button @click="getMoreNotebooklist" v-if="notebookList.length > 0 && (pageNum < pageCnt)">더보기</button>
              <p v-else>불러올 글이 없습니다</p>
              <p>리스트 길이 : {{notebookList.length}}</p>
              <p>페이지 넘버 : {{pageNum}}</p>
              <p>페이지 카운트 : {{pageCnt}}</p>
            </div>

            <div style="overflow-y:scroll; height:80vh;" v-if="searchFlag">
              <template v-for="(note, index) in searchedNotebookList">
                <v-list-item :key="note.noteId" @click="setDetail(note.noteId)">
                  <template >
                    <v-list-item-content >
                      <v-list-item-title v-text="note.title"></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="note.headline"
                      >
                      </v-list-item-subtitle>

                      <v-list-item-subtitle v-text="note.targetName"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="note.targetId"></v-list-item-action-text>

                    </v-list-item-action>
                  </template>
                </v-list-item>

                <v-divider
                  v-if="index < searchedNotebookList.length - 1"
                  :key="index"
                ></v-divider>

              </template>

              <!-- 조건문. 리스트의 길이가 0이거나, 다 불러온 경우에만 더보기 버튼 활성화. -->
              <button @click="getMoreSearchNotebooklist" v-if="searchedNotebookList.length > 0 && (searchPageNum < searchPageCnt)">더보기</button>
              <p v-else>불러올 글이 없습니다</p>
              <p>리스트 길이 : {{searchedNotebookList.length}}</p>
              <p>페이지 넘버 : {{searchPageNum}}</p>
              <p>페이지 카운트 : {{searchPageCnt}}</p>
            </div>
          </v-list-item-group>
        </v-list>
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
import notebookApi from '@/api/notebook.js';
import NotebookDetail from '@/components/Notebook/NotebookDetail.vue'
export default {
  name: "NotebookList",
  components: {
    NotebookDetail
  },
  data () {
    return {
      // 디테일 값 얻어오기 위한 글의 아이디값
      id:0,

      // 관계
      identity: 0,
      identity_str: '',


      userId: '',
      notebookList: [],

      pageNum: 0,
      pageCnt: 0,

      // 검색
      searchFlag: 0,
      searchPerson: '',
      searchedNotebookList: [],

      searchPageNum: 0,
      searchPageCnt: 0,

    }
  },
  
  methods: {
    initNotebookList() {
      this.searchFlag = 0
      this.notebookList = []
      this.pageNum = 0
      this.pageCnt = 0
      this.getNotebookList()
    },

    getMoreNotebooklist () {
      if (this.pageNum <= this.pageCnt) {
        this.getNotebookList()
      }
    },

    getMoreSearchNotebooklist () {
      if (this.searchPageNum <= this.searchPageCnt) {
        this.getSearchNotebookList()
      }
    },

    search() {
      this.searchPageNum = 0
      this.searchedNotebookList = []
      this.id = 0
      this.searchPageCnt = 0
      this.getSearchNotebookList()
    },

    setDetail (Id) {
      this.id = Id
    },

    async getNotebookList() {
      this.searchedNotebookList = []
      this.searchFlag = 0
      this.searchPageNum = 0
      this.searchPageCnt = 0
      console.log(this.id)
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      
      let data = {
        type: this.identity_str,
        userId: this.userId,
        pageNum: this.pageNum,
      }
      let result = await notebookApi.getNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // 만약 pageCnt가 0인데, result의 길이가 0이 아니라면 (즉 글이 있다면) pageCnt 값을 result값의 pageCnt로 바꿔줌
      if (result.length !== 0) {
        this.pageCnt = result[0].totalNum
        // 아니라면?
      } else {
        // 만약 반환받은 result 값이 없다면 pageCnt를 0으로 해줌.ㄴ
        this.pageCnt = 0
      }
      console.log(result)
      this.notebookList.push(...result)
      this.pageNum += 1

      if (this.id === 0) {
        // 최상단 알림장 디테일 페이지 디폴트 값으로 설정
        if (this.notebookList.length !== 0) {
          this.id = this.notebookList[0].noteId
        }
      } else {
        if (this.notebookList.length === 0) {
          this.id = 0
        } else {
          this.id = this.notebookList[0].noteId
        }
      }
    },

    // 검색 관련
    async getSearchNotebookList () {
      this.pageNum = 0
      this.pageCnt = 0
      this.notebookList = []
      this.searchFlag = 1
      console.log(this.searchPerson)
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        userId: this.userId,
        pageNum: this.searchPageNum,
        searchParam: this.searchPerson,
      }
      let result = await notebookApi.getSearchNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      })
      console.log(result)
      this.searchPageCnt = result.pageCnt
      // if (result.result.length !== 0) {
      //   this.searchPageCnt = result.pageCnt
      //   // 아니라면?
      // } else {
      //   // 만약 반환받은 result 값이 없다면 pageCnt를 0으로 해줌.
      //   this.searchPageCnt = 0
      // }
      this.searchedNotebookList.push(...result.result)
      this.searchPageNum += 1
      if (this.id === 0) {
        // 최상단 알림장 디테일 페이지 디폴트 값으로 설정
        if (this.searchedNotebookList.length !== 0) {
          console.log('여기?')
          this.id = this.searchedNotebookList[0].noteId
          console.log('여기??')
        }
      } else {
        if (this.searchedNotebookList.length === 0) {
          this.id = 0
        } else {
          this.id = this.searchedNotebookList[0].noteId
        }
      }
      console.log(this.searchPageCnt)
    },
  },


  // 새로고침 문제 보류..............
  // computed: {
  //   checkLogin() {
  //     console.log('안더ㅣㅁ?')
  //     console.log(this.$store.getters.isLogged)
  //     return this.$store.getters.isLogged
  //   }
  // },
  // watch: {
  //   checkLogin(user) {
  //     this.identity = user.type
  //     this.userId = user.userId
  //     if (this.identity === 1) {
  //     this.identity_str = 'parents'
  //     }
  //     else if (this.identity === 2) {
  //       this.identity_str = 'teacher'
  //     }
  //     this.getNotebookList()
  //   }
  // },



  created() {  
    this.identity = this.$store.state.user.type
    this.userId = this.$store.state.user.userId
    if (this.identity === 1) {
      this.identity_str = 'parents'
    }
    else if (this.identity === 2) {
      this.identity_str = 'teacher'
    }
    this.getNotebookList()
  },


}
</script>

<style scoped>

</style>



