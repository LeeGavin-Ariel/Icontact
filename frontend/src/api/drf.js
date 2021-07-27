const URL = 'http://localhost:8080'

export default {
  URL,
  ROUTES: {
    login: '/login',
    signup: '/user',
    sms: '/auth/sms',
    searchClass:'/user/kinder/class',
    changepw:'/user/changepw',
    getuserinf:'/user/info',
    changeProfileImg:'/user/profileImg',
    changePWAfterLogin:'/user/password'
  }
}
