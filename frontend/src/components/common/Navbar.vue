<template>
    <div style="background-color: rgb(250, 215, 73);">

      <nav
      class="navbar bg-light"
      style="background-color: rgb(230, 232, 240)"
      >
        <v-btn icon plain @click="moveToMainPage">
          <v-icon>로고</v-icon>
        </v-btn>
        <v-btn plain @click="moveToMainPage">
          <v-icon>Icontact</v-icon>
        </v-btn>
        
        <v-spacer></v-spacer>
        <p v-if="type == 2" style="color: black; margin-bottom:0px; margin-right:20px">{{ className }} {{ userName }} 선생님</p>
        <p v-if="type == 1">{{ className }} {{ userName }} 보호자님</p>
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
                <img :src="require('@/assets/profileImg/' + userId + '.jpg')" alt="profile-image">
              </v-avatar>
            </v-btn>
          </template>
          <v-list>
            <v-list-item @click="$router.push({ name: 'MyPage', params: { userId: userId }})">
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

      </nav>
    </div>
</template>

<script>
export default {
  name: 'Navbar',
  data() {
    return {
      type: 0,
      className: '',
      userName: '',
      userId: '',
    }
  },
  methods: {
    setInfo() {
      this.type = this.$store.state.user.type
      this.className = this.$store.state.user.className
      this.userName = this.$store.state.user.userName
      this.userId = this.$store.state.user.userId
    },
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
  },
  computed: {
    checkLogin() {
      console.log('로그인됨')
      this.setInfo()
      
      return this.$store.getters.isLogged
    }
  },
  watch: {
    checkLogin() {
      this.setInfo()
    }
  }
}
</script>

<style scoped>

</style>