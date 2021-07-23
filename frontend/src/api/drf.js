const URL = process.env.VUE_APP_SERVER_URL

export default {
  URL,
  ROUTES: {
    login: '/login',
    signup: '/user',
    sms: '/auth/sms',
    searchClass:'/user/kinder/class'
  }
}
