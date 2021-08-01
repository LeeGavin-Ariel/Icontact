<template>
  <div style="display: flex; height:100vh;" >

    
  <!-- 사이드바 -->
  <v-card>
    <Sidebar/>
  </v-card>
  

  <!-- 전체 조회 -->
  <v-card
    class="mx-auto"
    width="400"
    style="overflow-y:scroll;"
  >
    <NotebookList
    @selected-notebook="setId"
    />
    
  </v-card>

  <!-- 디테일 조회 -->
  <v-col
  style="overflow-y:scroll;"
  >
    <NotebookDetail
    :selectedNotebook="notebookDetail"
    />
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
    NotebookList,
    NotebookDetail,
  },

  data () {
    return {
      // 아이와의 관계
      identity: 0,
      // url을 위한 문자로 표현한 아이와의 관계
      identity_str: '',

      // 선택된 글의 아이디 값
      notebookId: 0,
      // 상세 알림장을 저장할 변수
      notebookDetail: null,

      // 선택 / 선택 아님 상태 저장
      selected: 1,


      // 알림장 생성 시 넘겨줄 변수
      title: '',
      content: '',
      notebookImg: '',
      writerId: '',
      targetId: '',
      
      // 1 작성 중 0 작성 중 아님
      creating: 0,      
      
    }
  },
  
  methods: {
    async setId(Id) {
      this.notebookId = Id
      this.getNotebookDetail()
    },

    // 7/29 할일 : 디테일값 notebook.vue에서 불러와서, NotebookDetail에 뿌려주기.
    async getNotebookDetail() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        Id: this.notebookId,
      }
      let result = await notebookApi.getNotebookDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // result 값 확인 후에 저장하기
      console.log(result)
      this.notebookDetail = result
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
  },
}
</script>

<style scoped>

</style>