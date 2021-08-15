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
import NotFound from '@/views/errorpage/NotFound'
import ServerError from '@/views/errorpage/ServerError'


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signup/teacher',
    name: 'SignupTeacher',
    component: SignupTeacher
  },
  {
    path: '/signup/parent',
    name: 'SignupParent',
    component: SignupParent
  },
  {
    path: '/changepasswordauth',
    name: 'ChangePasswordAuth',
    component: ChangePasswordAuth
  },
  {
    path: '/changepassword',
    name: 'ChangePassword',
    component: ChangePassword
  },
  {
    path: '/mypage/:userId',
    name: 'MyPage',
    component: MyPage
  },
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
  {
    path: '*',
    redirect: "/notfound"
  },
  {
    path: '/notfound',
    name: 'NotFound',
    component: NotFound
  },
  {
    path: '/servererror',
    name: 'ServerError',
    component: ServerError
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

router.beforeEach((to, from, next) => {
  const authPages = [
    'MyPage', 
    'MainPage',
    'Notebook',
    'Chat',
    'Album',
    'Attendence',
    'Notice',
    'Request',
  ]
  const publicPages = [
    'Login', 
    'Signup',
    'SignupTeacher',
    'SignupParent',
    'ChangePasswordAuth',
    'ChangePassword',
  ]

  const authRequired = authPages.includes(to.name)
  const authNotRequired = publicPages.includes(to.name)
  const isLoggedIn = sessionStorage.getItem('access-token') ? true : false


  if (authRequired && !isLoggedIn) {
    next({ name: 'Login' })
  } 
  else if (authNotRequired && isLoggedIn) {
    next({ name: 'MainPage' })
  } 
  else {
    next()
  }
})