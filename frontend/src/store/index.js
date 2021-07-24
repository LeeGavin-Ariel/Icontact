import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router/index.js'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null,
   
    // 회원가입 공통화면 데이터
    sendUserId:'',
    sendPassword:'',
    sendUserName:'',
    sendPhoneNumber:'',
    
    // 비밀번호 변경 데이터 
    sendUserIdForChangePW: '',
   
    // 로그인 결과 데이터.
    type: 0,
    accept: 0,

  },
  mutations: {

    // 반 검색
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
    SET_SEND_USER_ID_FOR_CHANGE_PW: function (state, userid) {
      state.sendUserIdForChangePW = userid
      
    }
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

    // 비밀번호 변경 시 id 값 저장
    setSendUserIdForChangePW: function ({commit}, userid) {
      commit('SET_SEND_USER_ID_FOR_CHANGE_PW', userid)
    }

  },
  modules: {
  }
})
