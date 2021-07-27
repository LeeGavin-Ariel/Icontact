<template>
    <div style="background-color: rgb(250, 215, 73);">
      <!-- 유저 정보 가져오는 로직 손보기. -->
        <ul class="nav">
              <li class="nav-item">
                  <!-- 메인으로 -->
                  <!-- 밥먹고 와서 여기 -->
                  <button @click="moveToMainPage">로고</button>
              </li>
              <li class="nav-item">
                  <!-- 메인으로 -->
                  <button @click="moveToMainPage">서비스명</button>
              </li>
              <li class="nav-item">
                  <button 
                  class="nav-link disabled" 
                  href="#"
                  v-if="$store.state.user.type == 2">{{ $store.state.user.className }} {{ $store.state.user.userName }} 선생님</button>
                  <button 
                  class="nav-link disabled" 
                  href="#"
                  v-if="$store.state.user.type == 1">{{ $store.state.user.className }} {{ $store.state.user.userName }} 보호자님</button>
              </li>

              <div class="dropdown">
                <button class="btn btn-link text-decoration-none " href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                  <v-avatar>
                    <img src="@/assets/seo.jpg" alt="profile-image">
                  </v-avatar>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                  <!-- 마이페이지 본인 id 쿼리 넘겨주기 -->
                  <li><button @click="$router.push({ name: 'MyPage', params: { userId: $store.state.user.userId }})">마이페이지</button></li>
                  <li><button>알림 끄기</button></li>
                  <li><button @click="logout">로그아웃</button></li>
                </ul>
              </div>


                <div class="dropdown">
                  <button class="btn btn-link text-decoration-none " href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                    <v-badge 
                      avatar
                      bordered
                      overlap
                    >
                      <template v-slot:badge>
                        <v-avatar>
                          <v-img src="@/assets/flaticon/bells.png"></v-img>
                        </v-avatar>
                      </template>

                      <v-avatar size="30">
                        <v-img src="@/assets/flaticon/bells.png"></v-img>
                      </v-avatar>
                    </v-badge>
                  </button>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <!-- 마이페이지 본인 id 쿼리 넘겨주기 -->
                    <li>알람1</li>
                    <li>알람2</li>
                    <li>알람3</li>
                  </ul>
                </div>
        </ul>
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
    }
  }
}
</script>

<style>

</style>