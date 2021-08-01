<template>
  <div>
    <v-list two-line >
      <v-list-item-group
        active-class="pink--text"
      >
        <template v-for="(note, index) in notebookList">
          <v-list-item :key="note.createDate" @click="selectedNotebook(note.noteId)">
            <template >
              <v-list-item-content>
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
        
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
      </v-list-item-group>
    </v-list>
    
  </div>
</template>

<script>
import notebookApi from '@/api/notebook.js';
import InfiniteLoading from "vue-infinite-loading";
export default {
  name: "NotebookList",
  data () {
    return {
      identity: 0,
      identity_str: '',
      userId: '',
      notebookList: [],
      notebookId: 0,

      pageNum: 0,

    }
  },
  components: {
    InfiniteLoading
  },

  methods: {
    infiniteHandler($state) {
      // 현건이한테 전체 페이지 수 받아서 처리하기.
      if (this.pageNum > 3) {
        $state.complete();
      }
      else {
        setTimeout(() => {
          $state.loaded();
          this.getNotebook()
        },1000)
      }
        },


    // handleNotificationListScroll(e) {
    //   console.log(e)
    //   const { scrollHeight, scrollTop, clientHeight } = e.target;
    //   const isAtTheBottom = scrollHeight === scrollTop + clientHeight;
    //   if (isAtTheBottom) {
    //     this.getNotebook()
    //   }
    // },


    selectedNotebook (Id) {
      this.notebookId = Id
      // console.log(this.notebookId)
      this.$emit('selected-notebook', this.notebookId)
      this.$store.state.notebookcreate = 0
      this.$store.state.notebookupdate = 0
    },

    async getNotebook() {
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
      this.notebookList.push(...result)
      console.log(this.notebookList)
      this.pageNum += 1

      if (this.notebookId === 0) {
        // 최상단 알림장 디테일 페이지 디폴트 값으로 설정
        this.notebookId = this.notebookList[0].noteId
        this.$emit('selected-notebook', this.notebookId)
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
    // this.getNotebook()

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



