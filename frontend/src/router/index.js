import Vue from 'vue'
import VueRouter from 'vue-router'
import Signup from '@/views/accounts/Signup'
import SignupParent from '@/views/accounts/SignupParent'
import SignupTeacher from '@/views/accounts/SignupTeacher'
import Login from '@/views/accounts/Login'
import ChangePasswordAuth from '@/views/accounts/ChangePasswordAuth'
import ChangePassword from '@/views/accounts/ChangePassword'
import MyPage from '@/views/mypage/MyPage'
import MyPageMaster from '@/views/mypage/MyPageMaster'
import MainPage from '@/views/mainpage/MainPage'
import Notebook from '@/views/funcs/Notebook'
import Chat from '@/views/funcs/Chat'
import Album from '@/views/funcs/Album'
import Attendance from '@/views/funcs/Attendance'
import Notice from '@/views/funcs/Notice'
import Request from '@/views/funcs/Request'


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

  // 마이페이지
  {
    path: '/mypage/:userId',
    name: 'MyPage',
    component: MyPage
  },
  // 관리자 마이페이지
  {
    path: '/mypage/master',
    name: 'MyPageMaster',
    component: MyPageMaster
  },
  {
    path: '/',
    name: 'MainPage',
    component: MainPage
  },
  {
    path: '/notebook',
    name: 'Notebook',
    component: Notebook
  },  
  {
    path: '/chat',
    name: 'Chat',
    component: Chat
  },
  {
    path: '/album',
    name: 'Album',
    component: Album
  },
  {
    path: '/attendance',
    name: 'Attendance',
    component: Attendance
  },
  {
    path: '/notice',
    name: 'Notice',
    component: Notice
  },
  {
    path: '/request',
    name: 'Request',
    component: Request
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

// 비밀번호 재설정, 회원가입 상세로 들어갈때 인증 안되면 막는것 구현.
router.beforeEach((to, from, next) => {
  //1-1. 로그인이 필요한 컴포넌트
  const authPages = [
    'MyPage', 
    'MainPage',
    'Notebook',
    'Chat',
    'Album',
    'Attendence',
    'Notice',
    'Request',
    // 'MyPageMaster'
  ]
  //1-2. 로그아웃이 필요한 컴포넌트(로그인 상태가 아닌 경우에 방문해야 하는 컴포넌트)
  const publicPages = [
    'Login', 
    'Signup',
    'SignupTeacher',
    'SignupParent',
    'ChangePasswordAuth',
    'ChangePassword',
  ]

  //2. 
  // 가려고 하는 곳(to)이 로그인이 필요한 곳인지 여부를 체크
  const authRequired = authPages.includes(to.name)
  // 가려고 하는 곳이 로그인이 필요하지 않은 곳은지 여부를 체크
  const authNotRequired = publicPages.includes(to.name)
  // 로그인이 되어있는지 여부를 체크하자 -> true / false
  const isLoggedIn = sessionStorage.getItem('access-token') ? true : false


  //3. 
  //3-1. 만약 로그인이 필요한 컴포넌트인데 로그인이 안되어 있는 경우에 강제로 가려고 하면?
  if (authRequired && !isLoggedIn) {
    // 로그인을 할 수 있도록 (가드) -> Login 컴포넌트로 보내자
    next({ name: 'Login' })
  //3-2. 만약 로그인이 필요하지 않은 컴포넌트인데 로그인이 되어있는 상태에서 강제로 가려고 하면?
  } 
  else if (authNotRequired && isLoggedIn) {
    next({ name: 'Login' })
  } 
  // //3-3. 전부 아니라면
  else {
    // 가던 길을 가자
    next()
  }
})