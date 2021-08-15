import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        mainColor1: '#667ABC',
        stateOn: '#8BC34A',
        stateOff: "#757575",
      },
    },
  },
});
