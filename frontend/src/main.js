import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'

import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon'

import VueGeolocationApi from 'vue-geolocation-api'
import VueSimpleAlert from 'vue-simple-alert'

Vue.use(VueGeolocationApi)
Vue.use(VueSimpleAlert)

Vue.component('icon', Icon)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')


