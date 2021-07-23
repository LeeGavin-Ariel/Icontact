import Vue from 'vue'
import VueRouter from 'vue-router'
import Signup from '@/views/accounts/Signup'
import SignupParent from '@/views/accounts/SignupParent'
import SignupTeacher from '@/views/accounts/SignupTeacher'
import Login from '@/views/accounts/Login'


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
    path: '/signupteacher',
    name: 'SignupTeacher',
    component: SignupTeacher
  },
  
  // 회원가입 학부모 페이지
  {
    path: '/signupparent',
    name: 'SignupParent',
    component: SignupParent
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
