<template>
  <div>

    <v-list shaped>
    <!-- <v-list style="background-color: #a8b1cf;">       -->
      <v-list-item
        v-for="(item) in items"
        :key="item.title"
        active-class="blue"
        class="py-3 sidebar-item"
      >
        <v-list-item-content @click="movePage(item.route)">
        <!-- <v-list-item-title @click="movePage(item.route)"><img src="item.imgUrl" style="height:2rem; margin-right:0.7rem">{{ item.title }}</v-list-item-title> -->
        <v-list-item-title ><img :src="require('@/assets/funcs/' + item.imgUrl + '.png')" style="height:1.8rem;" class="ml-3 mr-5"><span class="title-text">{{ item.title }}</span></v-list-item-title>
        
        </v-list-item-content>
      </v-list-item>
    </v-list>

  </div>
</template>

<script>
export default {
  name: "Sidebar",
  data() {
    return {
      items: [
        { title: "알림장", route: "Notebook", imgUrl:"note" },
        { title: "채팅", route: "Chat", imgUrl:"chat" },
        { title: "앨범", route: "Album", imgUrl:"album" },
        { title: "출석부", route: "Attendance",imgUrl:"attendance" },
        { title: "공지사항", route: "Notice", imgUrl:"notice" },
        { title: "요청사항", route: "Request", imgUrl:"request" },
      ],
    }
  },
  methods: {
    movePage (page) {
      if (this.$route.path !== `/${page.toLowerCase()}`) {
        this.$router.push({ name: page })
      }
    }
  },
  // created() {
  //   this.idx = 0;
  // }  
}
</script>

<style scoped>
* {
  font-family: 'NanumSquareRound';
  font-size: 1.02em;
  letter-spacing: 3.5px;
}
.sidebar-item{
  font-weight: bolder;
}
.sidebar-item:hover{
  background-color:#fff1c8;
  transition: 0.3s;
}
</style>
