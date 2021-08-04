<template>
  <div style="display: flex; height: 100vh;">

    
    <!-- 사이드바 -->
    <div style="width:20vw; border: solid 1px rgba(100,100,100, 0.3);">
      <Sidebar/>
    </div>
  
    <!-- 앨범 리스트 -->
    <div style="width:80vw;">
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

</style>