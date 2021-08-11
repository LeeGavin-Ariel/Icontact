<template>
  <div style="display: flex; height:100vh;">

    
    <!-- 사이드바 -->
    <div style="width:18vw;">
      <Sidebar/>
    </div>
  
    <!-- 노트북 리스트 -->
    <NotebookList/>

  </div>
</template>

<script>
import Sidebar from '@/components/common/Sidebar.vue';
import NotebookList from '@/components/Notebook/NotebookList.vue';

export default {
  name: "Notebook",
  components:{
    Sidebar,
    NotebookList,
  },
}
</script>

<style scoped>

</style>