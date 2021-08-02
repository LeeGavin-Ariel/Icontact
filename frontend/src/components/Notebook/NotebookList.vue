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
              <button @click="getMoreNotebooklist">더보기</button>
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


    // infiniteHandler($state) {
    //   // 현건이한테 전체 페이지 수 받아서 처리하기.
    //   if (this.pageNum > this.pageCnt) {
    //     $state.complete();
    //   }
    //   else {
    //     setTimeout(() => {
    //       $state.loaded();
    //       this.getNotebook()
    //     },1000)
    //   }
    //     },


    // handleNotificationListScroll(e) {
    //   console.log(e)
    //   const { scrollHeight, scrollTop, clientHeight } = e.target;
    //   const isAtTheBottom = scrollHeight === scrollTop + clientHeight;
    //   if (isAtTheBottom) {
    //     this.getNotebook()
    //   }
    // },


    setDetail (Id) {
      this.id = Id
      // console.log(this.id)
    },

    async getNotebookList() {
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
      console.log('확인용')
      // 페이지 수가 1개일때 문제가 생길 수 있다.
      if (!this.pageCnt && result) {
        this.pageCnt = result[0].pageCnt
      }
      this.notebookList.push(...result)
      this.pageNum += 1

      if (this.id === 0) {
        // 최상단 알림장 디테일 페이지 디폴트 값으로 설정
        this.id = this.notebookList[0].noteId
      }
    },
    // checkBottom: function () {
    //   const {scrollTop, clientHeight, scrollHeight} = document.documentElement
    //   if (scrollHeight - scrollTop <= clientHeight) {
    //       this.getNotebook()
    //   }
    // },
    

  },
  created() {
    
    this.identity = this.$store.state.user.type
    this.userId = this.$store.state.user.userId
    if (this.identity === 1) {
      this.identity_str = 'parent'
    }
    else if (this.identity === 2) {
      this.identity_str = 'teacher'
    }
    this.getNotebookList()

  },
}
</script>

<style>

/* 위에서는 '@select-video=실행할 함수명' 으로 기다림. */
/* 밑에서 위로 올려보낼때는 $emit */
/* selectVideo: function () {
      this.$emit('select-video', this.video)
    } */

</style>



