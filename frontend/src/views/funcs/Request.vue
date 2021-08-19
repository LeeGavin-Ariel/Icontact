<template>
  <div id="bg">
    <!-- 사이드바 -->
    <div style="width: 18vw">
      <Sidebar />
    </div>

    <!-- 요청사항 리스트 -->
    <RequestList />
  </div>
</template>


<script>
import Sidebar from "@/components/common/Sidebar.vue";
import RequestList from "@/components/Request/RequestList.vue";

export default {
  name: "Request",
  components: {
    Sidebar,
    RequestList,
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
