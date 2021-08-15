<template>
  <div id="bg">

    <!-- 사이드바 -->
    <div style="width:18vw;">
      <Sidebar/>
    </div>
  
    <!-- 앨범 리스트 -->
    <div style="width:82vw;">
      <AlbumList/>
    </div>
  </div>
</template>

<script>
import Sidebar from '@/components/common/Sidebar.vue';
import AlbumList from '@/components/Album/AlbumList.vue';

export default {
  name: "Album",
  components:{
    Sidebar,
    AlbumList

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