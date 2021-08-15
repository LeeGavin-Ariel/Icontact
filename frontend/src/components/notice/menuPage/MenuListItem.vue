<template>

  <div v-bind="$attrs" v-on="$listeners" elevation="10"  class="list-group list-group-flush scrollarea">
    <div class="list-group-item list-group-item-action py-2 lh-tight border-bottom" style="background-color:rgb(256, 256, 256, 0.7);">
      <div class="d-flex flex-column justify-content-center" style="height: 9vh; width:100%">
       
       
       <div class="d-flex flex-row">
          <div class="col-10">
            <div class="notice-title">{{ menuTitle(createDate) }}</div>
            <div class="notice-user mt-2">{{ menuInfo.userName }} 선생님</div>
          </div>
          <div class="col-2 justify-content-center align-items-center">
            <img  src="@/assets/flaticon/menu.png" style="width: 100%; height:100%">  
          </div>
        </div>
      </div>

     

    </div>
  </div>
</template>

<script>
export default {
  name: "MenuListItem",
  inheritAttrs: false,
  data() {
    return {
      createDate: "",
    };
  },

  props: {
    menuInfo: {},
  },

  methods: {
    refineDate() {
      this.createDate = this.menuInfo.createDate.substr(0, 11);
    },
    menuTitle(content) {
      var dateform = ''
      dateform = content.substr(0, 4) + "년 "+content.substr(5,2)+"월 "+content.substr(8,2)+"일 식단";
      return  dateform;
    },
  },

  created() {
    this.refineDate();
  },
};
</script>

<style scoped>
.notice-user {
  font-size: 0.8rem;

}
.notice-title {
  font-weight: 900;
  font-size: 1.3rem;

}
</style>