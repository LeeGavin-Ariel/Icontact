<template>
  <div style="display: flex; height:100vh;" >

    <!-- 사이드바 -->
    <div style="width:20vw;">
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

<style>

</style>
