<template>
  <div>
    <p>{{notebookDetail}}</p>
    <button>연필</button>
    <button>뒤로 가기</button>
    <button>수정</button>
    <button>삭제</button>
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
  </div>
</template>

<script>
// import notebookApi from '@/api/notebook.js';
export default {
  name: "NotebookDetail",
  data () {
    return {
      notebookDetail: null,
      creating:0
    }
  },
  props: {
    selectedNotebookId: {
      notebookId: Number
    },
  },
  methods: {
    // [알림장 세부 조회]  
  }
}
</script>

<style>

</style>