import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
// import Vuesax from 'vuesax'

import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon'
// import 'vuesax/dist/vuesax.css'

import VueSimpleAlert from "vue-simple-alert"

Vue.use(VueSimpleAlert);

// Vue.use(Vuesax, {

// })

Vue.component('icon', Icon)

// import vScroll from 'vue-scroll'
// Vue.use(vScroll)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')


