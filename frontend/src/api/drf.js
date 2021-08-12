const URL = 'http://i5a304.p.ssafy.io/'
const WS = 'ws://i5a304.p.ssafy.io/'

export default {
  URL,
  WS,
  ROUTES: {
    login: '/login',
    signup: '/user',
    sms: '/auth/sms',
    searchClass:'/auth/kinder/class',
    changepw:'/user/changepw',
    getuserinf:'/user/info',
    changeProfileImg:'/user/profileImg',
    changePWAfterLogin:'/user/password',
    notebook: '/notebook',
    request: '/request',
    getClassMember:'/user/class',
    notice: '/notice',
    schedule: '/notice',
    menu: '/notice',
    attendance: '/attendance',
    album: '/album',
  }
}
