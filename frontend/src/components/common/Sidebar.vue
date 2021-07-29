<template>
  <div>
    <v-navigation-drawer
      permanent
    >
      <v-list dense>
        <v-list-item
          v-for="item in items"
          :key="item.title"
        >
          <v-list-item-content>
            <v-list-item-title @click="$router.push({ name: item.route })">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>

export default {
  name: 'Sidebar',
  data () {
    return {
      items: [
        { title: '알림장', route: 'Notebook' },
        { title: '채팅', route: 'Chat' },
        { title: '앨범', route: 'Album' },
        { title: '출석부', route: 'Attendance' },
        { title: '공지사항', route: 'Notice' },
        { title: '요청사항', route: 'Request' },
      ],
    }
  }  
}
</script>

<style>

</style>