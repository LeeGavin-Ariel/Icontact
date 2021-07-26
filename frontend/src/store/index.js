import Vue from 'vue'
import Vuex from 'vuex'
<<<<<<< HEAD
import SERVER from '@/api/drf.js'
import router from '@/router/index.js'
import axios from 'axios'
=======
import router from '@/router/index.js'
>>>>>>> ed61bac40c76f87f869f59b93fc103a0639f1859
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
<<<<<<< HEAD
    authToken: localStorage.getItem('jwt'),
    searchKindergarden:'',
    searchKindergardenClass:[],

=======
    user: null,
   
>>>>>>> ed61bac40c76f87f869f59b93fc103a0639f1859
    // 회원가입 공통화면 데이터
    sendUserId:'',
    sendPassword:'',
    sendUserName:'',
    sendPhoneNumber:'',
<<<<<<< HEAD

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
=======
    
    // 비밀번호 변경 데이터 
    sendUserIdForChangePW: '',
   
    // 로그인 결과 데이터.
    type: 0,
    accept: 0,

  },
  mutations: {

    // 반 검색
>>>>>>> ed61bac40c76f87f869f59b93fc103a0639f1859
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
<<<<<<< HEAD
  },
  actions: {
    // 로그인
    login: function ({ commit }, userid, password){
      const credentials = {
        userid,
        password
      }
      console.log(credentials)
      axios({
        // url 체크하기
        url: SERVER.URL + SERVER.ROUTES.login,
        method: 'post',
        data: credentials,
      })
      // int 학부모(1)/선생(2)/마스터(9)
      // 타입 별로 라우터 다르게 설정
      .then((res) => {
        // int 승인 대기(1)/승인 완료(2)/승인 거절(3)
        if (res.data.accept === 2){
          commit('SET_TOKEN', res.data.token)
          if (res.data.type === 1) {
            router.push({ name: '#'})
          }
          else if (res.data.type === 2) {
            router.push({ name: '#'})
          }
          else if (res.data.type === 9) {
            router.push({ name: '#'})
          }
        } else if (res.data.accept === 1) {
          alert('현재 승인 대기 상태입니다. 관리자에게 문의하세요.')
        } else {
          alert('승인 거절된 계정입니다. 관리자에게 문의하세요.')
        }
      })
      .catch((err) => {
        console.log(err)
        // 에러페이지로 간다.
        // 로그인 실패 모달 띄운다.
        alert('아이디 및 비밀번호를 다시 한 번 확인해주세요.')
      })
    },
    
   
=======
    SET_USER(state, payload) {
      state.user = payload;
    },
    SET_SEND_USER_ID_FOR_CHANGE_PW: function (state, userid) {
      state.sendUserIdForChangePW = userid
      
    }
  },
  actions: {
>>>>>>> ed61bac40c76f87f869f59b93fc103a0639f1859
    // 회원가입 관련
    setTeacherInfo: function({commit}, userid, password, username, phonenumber) {
      commit('SET_THEACHER_INFO', userid, password, username, phonenumber)
      router.push({ name: 'SignupTeacher' })
    },
    setParentInfo: function({commit}, userid, password, username, phonenumber) {
      commit('SET_PARENT_INFO', userid, password, username, phonenumber)
      router.push({ name: 'SignupParent' })
    },
<<<<<<< HEAD
    signup: function({state}, relationship, kindergardenCode, kidName, kindergardenClass){
      const signupdata = {
        userId: state.sendUserId,
        password: state.sendPassword,
        userName: state.sendUserName,
        userTel: state.sendPhoneNumber,
        type: relationship,
        // kindergardenCode 필요한가?? 물어보기
        kindergardenCode: kindergardenCode,
        kidName : kidName,
        classClass: kindergardenClass,
      }
      axios({
        // url 체크하기
        url: SERVER.URL + SERVER.ROUTES.signup,
        method: 'post',
        data: signupdata,
        })
      .then(() => {
        alert("승인 요청이 발송되었습니다. 승인시까지 대기해주세요")
        router.push({ name: 'Login' })
      })
      .catch((err) => {
        // 서버 에러인 경우만 
        console.log(err)
      })
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

    


=======

    setUser(context, payload) {
      context.commit('SET_USER', payload);
    },

    // 비밀번호 변경 시 id 값 저장
    setSendUserIdForChangePW: function ({commit}, userid) {
      commit('SET_SEND_USER_ID_FOR_CHANGE_PW', userid)
    }
>>>>>>> ed61bac40c76f87f869f59b93fc103a0639f1859

  },
  modules: {
  }
})
