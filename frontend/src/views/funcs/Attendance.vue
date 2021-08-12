<template>
  <div id="bg">

    
    <!-- 사이드바 -->
    <div style="width:18vw;">
      <Sidebar/>
    </div>

    <!-- 관계에 따라 컴포넌트 분리 -->
    <ParentAttendance
    v-if="identity === 1"
    />
    <TeacherAttendance
    v-if="identity === 2"
    />

  </div>
</template>

<script>
import Sidebar from '@/components/common/Sidebar.vue';
import ParentAttendance from '@/components/Attendance/ParentAttendance.vue';
import TeacherAttendance from '@/components/Attendance/TeacherAttendance.vue';
export default {
  name: "Attendance",
  components:{
    Sidebar,
    ParentAttendance,
    TeacherAttendance,
  },
  data () {
    return {
      identity : this.$store.state.user.type
    }
  }
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