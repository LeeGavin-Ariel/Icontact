<template>
  <div v-bind="$attrs" v-on="$listeners" elevation="10"  class="list-group list-group-flush scrollarea">
    <div class="list-group-item list-group-item-action py-2 lh-tight border-bottom" style="background-color:rgb(256, 256, 256, 0.7);">
      <div class="align-items-center" style="height: 9vh; width:100%">
         <div style="height:100%">
          <div class="col-12">
            <div class="notice-title">{{ trimTitle(noticeInfo.title) }}</div>

            <div class="notice-sub justify-content-between mt-2">
              <div class="notice-user">{{noticeInfo.userName}} 선생님</div>
              <div class="notice-date">{{ createDate }}</div>
            </div>

          </div>
          
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NoticeListItem",
  inheritAttrs: false,
  data() {
    return {
      createDate: "",
    };
  },

  props: {
    noticeInfo: {},
  },

  methods: {
    trimTitle(title) {
      if (title.length > 20) {
        title = title.substr(0, 20) + "...";
      }
      return title;
    },
    trimContent(content) {
      if (content.length > 20) {
        content = content.substr(0, 20) + "...";
      }
      return content;
    },
    refineDate() {
      this.createDate = this.noticeInfo.createDate.substr(0, 11);
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
.notice-sub {
  display: flex;
}
.notice-content {
  color: rgb(150, 150, 150);
  font-size: 1rem;
}
.notice-date {
  font-size: 0.8rem;
}
</style>