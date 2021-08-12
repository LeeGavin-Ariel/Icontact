<template>
  <div class="item" v-bind="$attrs" v-on="$listeners" elevation="10" >
    <div class="notice-title">
      <!-- <span style="30px">{{ scheduleInfo.title }}</span> -->
    </div>
    <div class="notice-sub">
      <div class="notice-content">{{menuInfo.amSnack}}</div>
      <div class="notice-content">{{menuInfo.lunch}}</div>
      <div class="notice-content">{{menuInfo.pmSnack}}</div>
      <div class="notice-date">{{ createDate }}</div>
    </div>
  </div>
  <!-- <v-container fluid>
    <v-card
      color="blue lighten-4 radius"
      dense
      outlined
      elevation="10"
      shaped
      v-bind="$attrs"
      v-on="$listeners"
    >
      <v-row no-gutters dense>
        <v-col cols="9" class="pl-6">
          <v-row justify="center">
              <v-col align-self="end">{{menuInfo.amSnack}}</v-col>
              <v-col align-self="end">{{menuInfo.lunch}}</v-col>
              <v-col align-self="end">{{menuInfo.pmSnack}}</v-col>
          </v-row>
        </v-col>
        <v-col align-self="end" class="pr-3">
          {{ createDate }}          
        </v-col>
      </v-row>
    </v-card>
  </v-container> -->
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
  },

  created() {
    this.refineDate();
  },
};
</script>

<style scoped>
.item {
  background: #BBDEFB ;
  position: relative;
  /* justify-content: space-between; */
  /* align-self: center; */

  width: 100%;
  height: 5rem;
  border-bottom: aliceblue 0.1rem solid;
  margin-bottom: 0.1rem;
  /* border-radius: 20px; */

  display: flex;
}
.notice-title {
  margin-top: 0.3rem;
  margin-left: 2rem;
  font-size: 1.8rem;
  width: 100%;
  display: block;
  justify-self: flex-end;
}
.notice-sub {
  /* top: 0.5em; */
  /* height: 100%; */
  padding: 0.2rem 2rem;
  width: 100%;
  display: flex;
  position: absolute;
  bottom: 0;
  /* top: 2rem; */
  /* 아이템을 어떻게 배치할지 */
  justify-content: space-between;
  /* align-items: flex-end; */
  align-content: flex-end;
}
.notice-content {
  /* margin-left: 0.5rem; */
  width: 60%;
  align-self: flex-end;
}
.notice-date {
  display: inline-block;
  /* background-color: red; */
  /* display: inline-block; */

  /* display: flex; */
  align-self: flex-end;
}
</style>