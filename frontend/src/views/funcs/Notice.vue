<template>
  <div id="bg"  >

    <!-- 사이드바 -->
    <div style="width:18vw;">
      <Sidebar/>
    </div>
  
    <!-- 공지사항 리스트 -->
    <NoticeMain/>

  </div>
</template>

<script>
import Sidebar from '@/components/common/Sidebar.vue';
import NoticeMain from '@/components/notice/NoticeMain.vue';

export default {
  name: "Notice",
  components:{
    Sidebar,
    NoticeMain,
  },  
}
</script>

<style scoped>
#bg {
  background-color: white;
  width: 100%;
  height: 100%;
  display: flex;
}
</style>
