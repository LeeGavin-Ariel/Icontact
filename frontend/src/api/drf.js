const URL = 'http://localhost:8080'

export default {
  URL,
  ROUTES: {
    login: '/login',
    signup: '/user',
    sms: '/auth/sms',
    searchClass:'/user/kinder/class',
    changepw:'/user/changepw',
    getteacherinf:'/user/teacher',
    getparentinf:'/user/parent',
    updateteacherinf:'/user/teacher',
    updateparentinf:'/user/parent',
  }
}
