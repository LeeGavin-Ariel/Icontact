<template>
  <div style="display: flex; height:100vh;">

    
    <!-- 사이드바 -->
    <div style="width:20vw;">
      <Sidebar/>
    </div>

    <!-- 관계에 따라 컴포넌트 분리 -->
    <ParentAttendance
    v-if="identity === 1"
    style="width:80vw;"
    />
    <TeacherAttendance
    v-if="identity === 2"
    style="width:80vw;"
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

<style>

</style>