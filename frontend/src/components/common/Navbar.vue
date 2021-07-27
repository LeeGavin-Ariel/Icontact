<template>
    <div style="background-color: rgb(250, 215, 73);">

      <v-app-bar
        color="mainColor accent-4"
        dense
        dark
      >
        <v-btn icon plain @click="moveToMainPage">
          <v-icon>로고</v-icon>
        </v-btn>
        <v-btn plain @click="moveToMainPage">
          <v-icon>Icontact</v-icon>
        </v-btn>
        
        <v-spacer></v-spacer>
        <p v-if="$store.state.user.type == 2" style="color: black; margin-bottom:0px; margin-right:20px">{{ $store.state.user.className }} {{ $store.state.user.userName }} 선생님</p>
        <p v-if="$store.state.user.type == 1">{{ $store.state.user.className }} {{ $store.state.user.userName }} 보호자님</p>
        <v-menu
          left
          bottom
        >
          <template v-slot:activator="{ on, attrs }" >
            <v-btn
              icon
              v-bind="attrs"
              v-on="on"
            style="margin-right:15px">
              <v-avatar size="40">
                <img :src="require('@/assets/profileImg/' + $store.state.user.userId + '.jpg')" alt="profile-image">
              </v-avatar>
            </v-btn>
          </template>
          <v-list>
            <v-list-item @click="$router.push({ name: 'MyPage', params: { userId: $store.state.user.userId }})">
              <v-list-item-title >마이페이지</v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>알람끄기</v-list-item-title>
            </v-list-item>
            <v-list-item @click="logout">
              <v-list-item-title >로그아웃</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>


        <v-btn icon>  
          <v-badge
            overlap
            dot
            color="pink"
          >
            <v-avatar size="40">
              <v-img src="https://image.flaticon.com/icons/png/512/2645/2645883.png"></v-img>
            </v-avatar>
          </v-badge>
        </v-btn>

      </v-app-bar>
    </div>
</template>

<script>
export default {
  name: 'Navbar',
  methods: {
    logout() {
      if (confirm("정말 로그아웃하시겠습니까?")) {
        this.$store.dispatch('removeUser');
        if (this.$route.path !== '/') this.$router.push('/');
      }
    },
    moveToMainPage() {
      if (this.$route.path !== '/') {
        this.$router.push({ name: 'MainPage' })
      }
    },
  }
}
</script>

<style>

</style>