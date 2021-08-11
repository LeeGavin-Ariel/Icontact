import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router/index.js'
import userApi from '@/api/user.js';
// import createPersistedState from "vuex-persistedstate"
Vue.use(Vuex)

export default new Vuex.Store({
  // plugins: [
  //       createPersistedState(),
  //     ],
  state: {
    user: null,

    //chatting용
    messages: [],
    channel: null,
    channels: null,
    channelUsers: [],
    

    // 회원가입 공통화면 데이터
    sendUserId:'',
    sendPassword:'',
    sendUserName:'',
    sendPhoneNumber:'',
    
    // 비밀번호 변경 데이터 
    sendUserIdForChangePW: '',
    
    // 로그인 결과 데이터
    type: 0,
    accept: 0,


    isLoggedIn: null,

    //bus 위치추적
    bus : 0,
  },
  mutations: {

    // 반 검색
    SET_THEACHER_INFO: function (state, data) {
      state.sendUserId = data.userid,
      state.sendPassword = data.password,
      state.sendUserName = data.username,
      state.sendPhoneNumber = data.phoneNumber
    },
    SET_PARENT_INFO: function (state, data) {
      state.sendUserId = data.userid,
      state.sendPassword = data.password,
      state.sendUserName = data.username,
      state.sendPhoneNumber = data.phoneNumber 
    },
    SET_USER(state, payload) {
      state.user = payload;
      state.isLoggedIn = true
    },
    REMOVE_USER(state) {
      state.user = null;
      state.isLoggedIn = false
    },
    SET_SEND_USER_ID_FOR_CHANGE_PW: function (state, userid) {
      state.sendUserIdForChangePW = userid
      
    },

    //sendbird 관련
    SET_CHANNELS: (state, channels) => {
      state.channels = channels
    },
    SET_CHANNEL: (state, channel) => {
      state.channel = channel
    },

    SET_CHANNEL_USERS: (state, channelUsers) => {
      state.channelUsers = channelUsers
    },

    SET_MESSAGES: (state, messages) => {
      state.messages = messages
    },
    //이전 메시지 포함한 메시지로 대체
    PLUS_MESSAGES:(state, messages) => {
      state.messages = messages
    },

    SET_BUS: (state, bus)=>{
      state.bus = bus
    },
    
  },
  actions: {
    // 회원가입
    setTeacherInfo: function({commit}, data) {
      commit('SET_THEACHER_INFO', data)
      router.push({ name: 'SignupTeacher' })
    },
    setParentInfo: function({commit}, data) {
      commit('SET_PARENT_INFO', data)
      router.push({ name: 'SignupParent' })
    },

    setUser(context, payload) {
      context.commit('SET_USER', payload);
    },


    async removeUser(context) {
      try{
        // 로그아웃할때 유저 아이디 넘겨주기 (가빈누나부탁)
        await userApi.updateUser({
          userId : context.state.user.userId,
        },
        {
          "access-token": sessionStorage.getItem('access-token')
        });
      } catch (e) {
        console.log(e)
      }
      context.commit('REMOVE_USER');
      sessionStorage.clear();
    },

    // 비밀번호 변경 - id 값 저장
    setSendUserIdForChangePW: function ({commit}, userid) {
      console.log(userid)
      commit('SET_SEND_USER_ID_FOR_CHANGE_PW', userid)
      router.push({ name: 'ChangePassword' })
    },

    //sendbird 관련
    addMessage: ({ commit, state }, message) => {
      commit('SET_MESSAGES', state.messages.concat(message))
    },

    addMessages: ({ commit, state }, messages) => {
      commit('SET_MESSAGES', messages.concat(state.messages))
    },
    //이전 메시지 가져오기
    loadPrevMessages: ({ commit }, messages) => {
      commit('PLUS_MESSAGES', messages)
    },

    addChannelUser: ({ commit, state }, user) => {
      commit('SET_CHANNEL_USERS', state.channelUsers.concat(user))
    },

    removeChannelUser: ({ commit, state }, user) => {
      commit('SET_CHANNEL_USERS', state.channelUsers.filter(it => it.userId !== user.userId))
    },

    setBus(context, payload) {
      context.commit('SET_BUS', payload);
    },

  },

  getters: {

    earliestMessage(state) {
      return state.messages[0]
    },

    isLogged(state) {
      return state.user
    }

  },

  modules: {
  },
})
