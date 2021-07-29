<template>
  <div style="display: flex">

    
  <!-- 사이드바 -->
  <v-card height="100vh">
    <Sidebar/>
  </v-card>
  

  <!-- 전체 조회 -->
  <v-card
    class="mx-auto"
    max-width="500"
  >
    <NotebookList/>
    <v-list two-line>
      <v-list-item-group
        v-model="selected"
        active-class="pink--text"
        multiple
      >
        <template v-for="(note, index) in notes">
          <v-list-item :key="note.title">
            <template v-slot:default="{ active }">
              <v-list-item-content>
                <v-list-item-title v-text="note.title"></v-list-item-title>

                <v-list-item-subtitle
                  class="text--primary"
                  v-text="note.headline"
                ></v-list-item-subtitle>

                <v-list-item-subtitle v-text="note.subtitle"></v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-list-item-action-text v-text="note.action"></v-list-item-action-text>

                <v-icon
                  v-if="!active"
                  color="grey lighten-1"
                >
                  mdi-star-outline
                </v-icon>

                <v-icon
                  v-else
                  color="yellow darken-3"
                >
                  mdi-star
                </v-icon>
              </v-list-item-action>
            </template>
          </v-list-item>

          <v-divider
            v-if="index < notes.length - 1"
            :key="index"
          ></v-divider>
        </template>
      </v-list-item-group>
    </v-list>
  </v-card>

  <!-- 디테일 -->
  <v-col>
    <button v-if="identity === 2" @click="onNotebookForm">새 글</button>
    <button v-if="identity === 2 && creating" @click="offNotebookForm">뒤로 가기</button>
    <button v-if="identity === 2" @click="updateNotebook">수정</button>
    <button v-if="identity === 2" @click="deleteNotebook">삭제</button>
    <v-sheet
      min-height="100vh"
      rounded="lg"
      v-if="notebookDetail && !creating"      
    >
      {{ notebookDetail }}
      <p>작성 시간 : {{ notebookDetail.createDate }}</p>
      <!-- 고치기 -->
      <p>{{ notebookDetail.createDate }} 학부모님께</p>
      <p>제목: {{ notebookDetail.title }}</p>
      <img  
        :src="require('@/assets/profileImg/' + userId + '.jpg')"
        alt="notebook-image">
      <p>content : {{ notebookDetail.content }}</p>
    </v-sheet>

    <v-sheet
      min-height="100vh"
      rounded="lg"
      v-if="creating"
    >
      <p>제목: </p>
      <input type="text" v-model="title">


      <!-- 반 학부모 조회 추가로 url 만들어야 함-->
      <select name="receiver" id="receiver" v-model="receiver">
        <option value="noValue">알림장 받을 분</option>
        <!-- option v-for로 -->
        <option v-for="[Class, idx] in kindergardenClasses" v-bind:key="idx" v-bind:value="idx">
          {{ Class }}
        </option>
      </select>

      <p>내용: </p>
      <input type="text" v-model="content">
      <input type="file" @change="selectImg">
    </v-sheet>
  </v-col>
  </div>
</template>

<script>
import notebookApi from '@/api/notebook.js';
import Sidebar from '@/components/common/Sidebar.vue';
import NotebookList from '@/components/Notebook/NotebookList.vue';
import NotebookDetail from '@/components/Notebook/NotebookDetail.vue';
export default {
  name: "Notebook",
  components:{
    Sidebar,
  },  
  data () {
    return {
      // 아이와의 관계
      identity: 0,
      // url을 위한 문자로 표현한 아이와의 관계
      identity_str: '',

      // 전체 알림장을 저장할 리스트
      notebookList: [],

      // 상세 알림장을 저장할 변수
      notebookDetail: null,

      // 선택 / 선택 아님 상태 저장
      selected: 1,

      // 디테일 값을 얻어 오기 위한 선택된 글의 아이디값
      notebookId: 0,

      // 전체 알림장 조회 미리보기 내용
      notes: [
        {
          action: '1분 전',
          headline: '김철수 학부모님',
          subtitle: '안녕하세요 김철수 학부모님. 조의상 선생님입니다.',
          title: '7월 29일자 알림장',
        },
      ],

      // 알림장 생성 시 넘겨줄 변수
      title: '',
      content: '',
      notebookImg: '',
      writerId: '',
      targetId: '',
      
      // 1 작성 중 0 작성 중 아님
      creating: 0,      
      
      // 사이드바 
      items: [
        { title: '알림장', route: 'Notebook' },
        { title: '채팅', route: 'Chat' },
        { title: '앨범', route: 'Album' },
        { title: '출석부', route: 'Attendance' },
        { title: '공지사항', route: 'Notice' },
        { title: '요청사항', route: 'Request' },
      ],
    }
  },
  methods: {

    // [알림장 전체 조회]
    async getNotebook() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        userId: this.$store.state.user.userId,
      }
      let result = await notebookApi.getNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // 현건이 물어봐서 어떤 거 주는 지 확인 필요
      // 혹은 내가 찍어볼 것
      console.log(result)      
      this.notebookList = result.dosageList
      console.log(this.notebookList)
      if (this.notebookList.length !== 0) {
        // 최상단 알림장 디테일 페이지 디폴트 값으로 설정
        this.notebookId = this.notebookList[0].noteId
        this.getNotebookDetail()
      }
    },

    // [알림장 세부 조회]  
    async getNotebookDetail() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        notebookId: this.notebookId
      }
      let result = await notebookApi.getNotebookDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // result 값 확인 후에 저장하기
      console.log(result)
      this.notebookDetail = result.dosage
      console.log(this.notebookDetail)      
    },

    // 연필 클릭 시 작성 폼
    onNotebookForm() {
      this.creating = 1
    },

    // 작성 취소 시 조회 폼
    offNotebookForm() {
      this.creating = 0
    },

    // [알림장 생성] 사진 선택
    selectImg(event) {
      this.notebookImg = event.target.files[0]
    },

    // [알림장 생성] 버튼 클릭 시 요청
    async createNotebook() {

      // 
      if (this.creating === 0) {
        this.title = ''
        this.content = ''
        this.notebookImg = ''
        this.writerId = ''
        this.targetId = ''
      }
      else if (this.creating === 1) {
        const formData = new FormData();
        formData.append("profileimg", this.notebookImg)      
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        let data = {
          title: this.title,
          content: this.content,
          notebookImg: formData,
          writerId: this.writerId,
          targetId: this.targetId,
        }
        let result = await notebookApi.createNotebook(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
          'Content-Type': 'multipart/form-data'
        });
        // 뭘로 날라오는 지 확인
        this.notebookImg = result.message
        window.location.reload()
      }
    },

    // [알림장 수정]
    updateNotebook(){

    },

    // [알림장 삭제]
    async deleteNotebook() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
      id: this.notebookDetail.notebookId
      }
      let result = await notebookApi.deleteRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      this.getNotebook()
    },

    selectedDetail(event) {
      this.notebookId = event.target.value.noteId
      this.getNotebookDetail()
    },

    

  // 페이지 로드 시 getNotebook 요청 실행
  // created() {
  //   this.identity = this.$store.state.user.type
  //   if (this.identity === 1) {
  //     this.identity_str = 'parent'
  //   }
  //   else if (this.identity === 2) {
  //     this.identity_str = 'teacher'
  //   }
  //   this.getNotebook()
  //   console.log(this.notebookList.length)
  //   } ,
  }
}
</script>

<style scoped>

</style>