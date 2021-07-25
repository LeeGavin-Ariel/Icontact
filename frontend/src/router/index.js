import Vue from 'vue'
import VueRouter from 'vue-router'
import Signup from '@/views/accounts/Signup'
import SignupParent from '@/views/accounts/SignupParent'
import SignupTeacher from '@/views/accounts/SignupTeacher'
import Login from '@/views/accounts/Login'
import ChangePasswordAuth from '@/views/accounts/ChangePasswordAuth'
import ChangePassword from '@/views/accounts/ChangePassword'
import MyPageTeacher from '@/views/mypage/MyPageTeacher'
import MyPageMaster from '@/views/mypage/MyPageMaster'

Vue.use(VueRouter)

const routes = [
  // 로그인페이지
  {
    path: '/login',
    name: 'Login',
    component: Login
  },

  // 회원가입 공통 페이지
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },

  // 회원가입 선생님 페이지
  {
    path: '/signup/teacher',
    name: 'SignupTeacher',
    component: SignupTeacher
  },
  
  // 회원가입 학부모 페이지
  {
    path: '/signup/parent',
    name: 'SignupParent',
    component: SignupParent
  },

  // 비밀번호 변경 인증 페이지
  {
    path: '/changepasswordauth',
    name: 'ChangePasswordAuth',
    component: ChangePasswordAuth
  },
  // 비밀번호 변경 페이지
  {
    path: '/changepassword',
    name: 'ChangePassword',
    component: ChangePassword
  },

  // 선생님 마이페이지
  {
    path: '/mypage/teacher/:userId',
    name: 'MyPageTeacher',
    component: MyPageTeacher
  },
  // 관리자 마이페이지
  {
    path: '/mypage/master',
    name: 'MyPageMaster',
    component: MyPageMaster
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

// 비밀번호 재설정, 회원가입 상세로 들어갈때 인증 안되면 막는것 구현.