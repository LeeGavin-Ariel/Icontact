


<template>

  <!-- <template>
  <div class="row" style=" height:100vh;" >
      <div style="width:20vw;">
        <Sidebar/>
      </div>

      <div class="row" style="width:80vw;">
        <div
          class="col-5 mx-auto"
          style="overflow-y:scroll;"
        >
          <NotebookList
          @selected-notebook="setId"
          />
        
        </div>

        <div
        class="col"
        style="overflow-y:scroll;"
        >
          <NotebookDetail
          :selectedNotebook="notebookDetail"
          />
        </div>
      </div>
    </div>
</template> -->


  <div style="display: flex; height:100vh;" >

    <!-- 사이드바 -->
      <div style="width:20vw;">
        <Sidebar/>
      </div>
    
    <RequestList/>

  </div>
</template>


<script>
import Sidebar from '@/components/common/Sidebar.vue';

import RequestList from '@/components/Request/RequestList.vue';
export default {
  name: "Request",
  components:{
    Sidebar,
    RequestList,
  },
  data () {
    return {
    }
  },
  
  methods: {
  },
}
</script>

<style>

</style>
