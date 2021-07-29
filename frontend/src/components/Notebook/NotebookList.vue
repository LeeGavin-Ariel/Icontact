<template>
  <div>
    <v-list two-line>
      <v-list-item-group
        active-class="pink--text"
      >
        <template v-for="(note, index) in notebookList">
          <v-list-item :key="note.noteId" @click="selectedNotebook(note.noteId)">
            <template>
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
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script>
import notebookApi from '@/api/notebook.js';
export default {
  name: "NotebookList",
  data () {
    return {
      identity:0,
      identity_str: '',
      userId: '',
      notebookList: [],
      notebookId: 0
    }
  },


  methods: {
    selectedNotebook (Id) {
      this.notebookId = Id
      // console.log(this.notebookId)
      this.$emit('selected-notebook', this.notebookId)

    },

    async getNotebook() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        userId: this.userId,
      }
      let result = await notebookApi.getNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.notebookList = result
      if (this.notebookList.length !== 0) {
        // 최상단 알림장 디테일 페이지 디폴트 값으로 설정
        this.notebookId = this.notebookList[0].noteId
        this.$emit('selected-notebook', this.notebookId)
      }
    }
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
    this.getNotebook()
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



