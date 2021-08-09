<template>
  <div>
    <div
    >
      <v-list>

        <v-list-item
          v-for="item in items"
          :key="item.title"
          active-class="primary"
          class="py-1"
        >
          <v-list-item-content>
            <v-list-item-title @click="movePage(item.route)">{{ item.title }}</v-list-item-title>

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
        { title: "알림장", route: "Notebook" },
        { title: "채팅", route: "Chat" },
        { title: "앨범", route: "Album" },
        { title: "출석부", route: "Attendance" },
        { title: "공지사항", route: "Notice" },
        { title: "요청사항", route: "Request" },
      ],
    }
  },
  methods: {
    movePage (page) {
      if (this.$route.path !== `/${page.toLowerCase()}`) {
        this.$router.push({ name: page })
      }
    }
  }  
}
</script>

<style>
</style>