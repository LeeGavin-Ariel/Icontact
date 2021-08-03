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
        :value="searchPerson" 
        @input="searchPerson=$event.target.value"
        @keypress.enter="searchNotebookList">

        <v-list two-line>
          <v-list-item-group
            active-class="pink--text"
          >

            <!-- 노트북 리스트 띄우기 -->
            <div style="overflow-y:scroll; height:80vh;">
              <template v-for="(note, index) in notebookList">
                <v-list-item :key="note.createDate" @click="setDetail(note.noteId)">
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
      searchPerson: '',

    }
  },
  
  methods: {
    initNotebookList() {
      this.notebookList = []
      this.pageNum = 0
      this.getNotebookList()
    },

    getMoreNotebooklist () {
      if (this.pageNum <= this.pageCnt) {
        this.getNotebookList()
      }
    },


    setDetail (Id) {
      this.id = Id
    },

    async getNotebookList() {
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
        this.pageCnt = result[0].pageCnt
        // 아니라면?
      } else {
        // 만약 반환받은 result 값이 없다면 pageCnt를 0으로 해줌.ㄴ
        this.pageCnt = 0
      }
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
      console.log(this.id)
    },

    // 검색 관련
    searchNotebookList () {
      console.log(this.searchPerson)
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



