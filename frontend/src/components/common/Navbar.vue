<template>
    <div>
        <!-- 유저 정보 가져오는 로직 손보기. -->

        <ul class="nav">
            <li class="nav-item">
                <!-- 메인으로 -->
                <a class="nav-link active" href="#">로고</a>
            </li>
            <li class="nav-item">
                <!-- 메인으로 -->
                <a class="nav-link" href="#">서비스명</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">{{ $store.state.user.kindergardenClass }}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" 
                href="#"
                v-if="user.type==2"
                >SNS</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">{{ user.kindergardenClass }} {{ user.userName }} 선생님</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">프로필 사진</a>
                <div class="dropdown-menu">
                <a class="dropdown-item" href="#">마이페이지</a>
                <a class="dropdown-item" href="#" v-if="user.type==2">알림 끄기</a>
                <a class="dropdown-item" href="#">로그아웃</a>
                <div class="dropdown-divider"></div>
            </div>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">알람</a>
            </li>
        </ul>
    </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Navbar',
  
  methods: {
    ...mapState([
        'user'
    ]),
  },
}
</script>

<style>

</style>