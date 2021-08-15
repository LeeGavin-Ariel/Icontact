<template>
  <div id="bg">
    <!-- 사이드바 -->
    <div style="width: 18vw">
      <Sidebar />
    </div>

    <!-- 노트북 리스트 -->
    <NotebookList />
  </div>
</template>

<script>
import Sidebar from "@/components/common/Sidebar.vue";
import NotebookList from "@/components/Notebook/NotebookList.vue";

export default {
  name: "Notebook",
  components: {
    Sidebar,
    NotebookList,
  },
};
</script>

<style scoped>
#bg {
  background-color: white;
  width: 100%;
  height: 100%;
  display: flex;
}
</style>