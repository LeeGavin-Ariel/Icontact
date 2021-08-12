<template>
  <div style="overflow-y:scroll;" class="col">
    <button v-if="$store.state.user.type === 2 && updating === 0" @click="createNotebook">연필</button>
    <button v-if="$store.state.user.type === 2 && updating === 1" @click="updateNotebook">연필</button>
    <button v-if="$store.state.user.type === 2 && updating === 0 && id" @click="updateNotebook">수정</button>
    <button v-if="$store.state.user.type === 2 && id" @click="deleteNotebook">삭제</button>

    <v-sheet
      rounded="lg"
      v-if="!id && !(creating || updating)"
    >
      등록된 알림장이 없습니다.
    </v-sheet>

    <v-sheet
      min-height="100vh"
      rounded="lg"
      v-if="!creating && !updating && notebookDetail && id"
    > 
      <p>{{notebookDetail}}</p>
      <p>작성 시간 : {{ notebookDetail.createDate }}</p>
      <!-- 고치기 -->
      <p>{{ notebookDetail.kidName }} 학부모님께</p>
      <p>제목: {{ notebookDetail.title }}</p>
      <!-- <img  
        :src="require('@/assets/profileImg/' + notebookDetail.noteImgUrl + '.jpg')"
        alt="notebook-image"> -->
      <p>content : {{ notebookDetail.content }}</p>
    </v-sheet>

    <v-sheet
      min-height="100vh"
      rounded="lg"
      v-if="creating || updating"
    >
      <p>제목: </p>
      <input type="text" v-model="title">


      <!-- 반 학부모 조회 추가로 url 만들어야 함-->
      
      <p>{{parentList}}</p>
      <select name="targetId" id="targetId" v-model="targetId">
        <option value="none" selected>=== 선택 ===</option>
        <option v-for="parent in parentList" :key="parent.userId" :value="parent.userId">
          {{ parent.kidName }} 학부모님
        </option>
      </select>

      <p>내용: </p>
      <input type="text" v-model="content">
      <form>
        <input type="file" @change="onFileSelected">
      </form>
    </v-sheet>



  </div>
</template>

<script>
import notebookApi from '@/api/notebook.js';
export default {
  name: "NotebookDetail",
  data () {
    return {

      // 상세 알림장을 저장할 변수
      notebookDetail: null,

      userId:'',
      // 작성폼
      title: '',
      content: '',
      writerId: '',
      targetId: '',
      img: '',

      parentList: [],

      creating: 0,
      updating: 0,
      

    }
  },
  props: {
    identity: {
      identity: Number,
    },
    id: {
      id: Number,
    },
  },

  watch: {
    'id' : function() {
      this.getNotebookDetail()
    }
  },

  methods: {

    async getNotebookDetail() {
      console.log('여기')
      console.log(this.id)
      if (this.id !== 0) {
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        let data = {
          Id: this.id,
        }
        let result = await notebookApi.getNotebookDetail(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        });
        this.notebookDetail = result
        this.creating = 0
        this.updating = 0
      }
      
    },


    onFileSelected(event) {
      this.img = event.target.files[0]
    },


    // 학부모 목록 조회
    async getParentList() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        userId: this.$store.state.user.userId,
      }
      let result = await notebookApi.getParent(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.parentList = result
    },




    async createNotebook() {
      // 작성중이 아닌 상태
      if (this.updating === 1) {
        this.updating = 0
      }

      if (this.creating === 0) {
        this.title = ''
        this.content = ''
        this.img = ''
        this.writerId = ''
        this.targetId = ''
        this.creating = 1
      }
      // creating 이 1이라는 것은 연필 버튼 눌렀을때고, 한번 더 누르면 글 생성.
      else if (this.creating === 1) {
        const formData = new FormData();
        formData.append("img", this.img)
        formData.append("title", this.title)
        formData.append("content", this.content)
        formData.append("targetId", this.targetId)
        formData.append("writerId", this.$store.state.user.userId)


        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')

        console.log(formData);
        let data = formData
        await notebookApi.createNotebook(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
          // 'Content-Type': 'multipart/form-data'
        });
        // 뭘로 날라오는 지 확인

        this.creating = 0
        this.$emit('get-notebooklist')
      }
    },

    async updateNotebook() {
      if (this.creating === 1) {
        this.creating = 0
      }

      // 0이면 작성 폼을 불러옴.
      if (this.updating === 0) {
        this.title = this.notebookDetail.title
        this.content = this.notebookDetail.content
        this.img = this.notebookDetail.img
        this.writerId = this.notebookDetail.writerId
        this.targetId = this.notebookDetail.targetId
        this.updating = 1
      }
      // 1이면 수정 요청 보냄
      else if (this.updating === 1) {
        const formData = new FormData();
        formData.append("noteId", this.notebookDetail.noteId)
        formData.append("img", this.img)
        formData.append("title", this.title)
        formData.append("content", this.content)
        formData.append("targetId", this.targetId)
        formData.append("writerId", this.$store.state.user.userId)
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        let data = formData
        let result = await notebookApi.updateNotebook(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
          'Content-Type': 'multipart/form-data'
        });
        // 뭘로 날라오는 지 확인
        console.log(result)
        this.updating = 0
        this.$emit('get-notebooklist')



      }
      
    },

    async deleteNotebook() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        notebookId: this.notebookDetail.noteId,
      }
      let result = await notebookApi.deleteNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      
      console.log(result)
      this.$emit('get-notebooklist')
    },
  },
  created () {
    this.getParentList()
  }
}
</script>

<style scoped>

</style>