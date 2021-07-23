import Vue from 'vue'
import Vuex from 'vuex'
import SERVER from '@/api/drf.js'
import router from '@/router/index.js'
import axios from 'axios'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null,
    authToken: localStorage.getItem('jwt'),
    searchKindergarden:'',
    searchKindergardenClass:[],

    // 회원가입 공통화면 데이터
    sendUserId:'',
    sendPassword:'',
    sendUserName:'',
    sendPhoneNumber:'',

    // 로그인 결과 데이터.
    type: 0,
    accept: 0,
  },
  mutations: {
    SET_TOKEN: function (state, token) {
      state.authToken = token
      localStorage.setItem('jwt', token)
    },

    // 반 검색
    SET_KINDERGARDEN_CODE: function (state, inputValue) {
      state.searchKindergarden = inputValue
    },
    SET_KINDERGARDEN_CLASS: function (state, kindergarden) {
      state.searchKindergardenClass = kindergarden
    },
    SET_THEACHER_INFO: function (state, userid, password, username, phonenumber) {
      state.sendUserId = userid,
      state.sendPassword = password,
      state.sendUserName = username,
      state.searchClass = phonenumber
    },
    SET_PARENT_INFO: function (state, userid, password, username, phonenumber) {
      state.sendUserId = userid,
      state.sendPassword = password,
      state.sendUserName = username,
      state.searchClass = phonenumber 
    },
    SET_USER(state, payload) {
      state.user = payload;
    },
  },
  actions: {
    // 회원가입 관련
    setTeacherInfo: function({commit}, userid, password, username, phonenumber) {
      commit('SET_THEACHER_INFO', userid, password, username, phonenumber)
      router.push({ name: 'SignupTeacher' })
    },
    setParentInfo: function({commit}, userid, password, username, phonenumber) {
      commit('SET_PARENT_INFO', userid, password, username, phonenumber)
      router.push({ name: 'SignupParent' })
    },

    setUser(context, payload) {
      context.commit('SET_USER', payload);
    },

    // 유치원 코드로 반 정보 불러오기
    searchClass: function ({ commit, state}, event) {
      commit('SET_KINDERGARDEN_CODE', event.target.value)
      axios({
        // url 체크하기
        url: SERVER.URL + SERVER.ROUTES.searchClass + '?kinderCode=' + state.searchKindergarden,
        method: 'get',
      })
      .then((res) => {
        commit('SET_KINDERGARDEN_CLASS', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    // 비밀번호 변경 시 id 값 저장
    setSendUserIdForChangePW: function ({commit}, userid) {
      commit('SET_SEND_USER_ID_FOR_CHANGE_PW', userid)
    }

  },
  modules: {
  }
})
