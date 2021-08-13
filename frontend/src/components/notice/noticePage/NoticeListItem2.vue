<template>
  <div v-bind="$attrs" v-on="$listeners" elevation="10"  class="list-group list-group-flush scrollarea">
    <div class="list-group-item list-group-item-action py-2 lh-tight border-bottom" style="background-color:rgb(256, 256, 256, 0.7);">
      <div class="align-items-center" style="height: 9vh; width:100%">
        <div>
          <div class="notice-user" >
            <div>[{{noticeInfo.userName }} 선생님]</div>
          </div>
          
          <div class="notice-title">
            <div>{{ trimTitle(noticeInfo.title) }}</div>
          </div>
          
          <div class="notice-sub">
            <div class="notice-content">{{ trimContent(noticeInfo.content) }}</div>
            <div class="notice-date">{{ createDate }}</div>
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

.notice-text {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
}
.notice-user {
  padding-left: 1rem;
  padding-top: 0.1rem;
  font-size: 0.8rem;
  width: 100%;
  display: flex;
  align-self: center;
  justify-content: flex-start;
}
.notice-title {
  padding-left: 1rem;
  font-weight: 900;
  padding-top: 0.3rem;
  font-size: 1.3rem;
  width: 100%;
  display: flex;
  align-self: center;
  justify-content: flex-start;
}
.notice-sub {
  padding: 0rem 0.5rem 0rem 1rem;
  width: 100%;
  display: flex;
}
.notice-content {
  color: rgb(150, 150, 150);
  display: inline-block;
  width: 60%;
  font-size: 1rem;
  text-align: left;
}
.notice-date {
  width: 40%;
  font-size: 0.8rem;
  display: inline-block;
  text-align: right;
  align-self: flex-end;
}
</style>