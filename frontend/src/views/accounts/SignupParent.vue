<template>
  <div>
    <div>
      <!-- 유치원 코드 입력 -->
      <label for="kindergardenCode">유치원코드 : </label>
      <input type="text"
      oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
      id="kindergardenCode" 
      v-model="kindergardenCode"
      @input="searchClass"
      maxlength="4"
      placeholder="유치원 코드를 입력하세요">
    </div>
    <div v-if="!kindergardenCode || kindergardenClasses.length === 0">존재하지 않는 유치원 코드입니다.</div>

    <!-- 반 목록 선택 (드롭다운) -->
    <select name="classCode" id="classCode" v-model="classCode">
      <option value="noValue">반 선택</option>
      <!-- option v-for로 -->
      <option v-for="[Class, idx] in kindergardenClasses" v-bind:key="idx" v-bind:value="idx">
        {{ Class }}
      </option>
    </select>
  
    <div>
      <!-- 아이 이름 입력 -->
      <label for="kidName">아이 이름: </label>
      <input type="text" 
      id="kidName" 
      v-model="kidName"
      placeholder="아이 이름을 입력하세요">
    </div>
    <div v-if="!kidName">아이 이름을 입력해주세요.</div>

  <!-- 필요 : 회원가입 요청 날리기 -->
    <button 
      @click="signup" 
      :disabled="classCode === 'noValue' || !kidName"
      >회원가입</button>
  </div>
  
 
</template>

<script>
import {userApi} from '@/api/user.js';
import axios from 'axios'
import SERVER from '@/api/drf.js'

export default {
  name: "SignupParent",
  data: () => {
    return {
      relationship: 1, // 부모1 선생2
      kindergardenCode: '',
      kidName: '',
      kindergardenClasses:[],
      classCode: 'noValue'
    }
  },
  methods: {
    async signup(){
      try {
        await userApi.registerUser({
          userId: this.$store.state.sendUserId,
          password: this.$store.state.sendPassword,
          userName: this.$store.state.sendUserName,
          userTel: this.$store.state.sendPhoneNumber,
          type: this.relationship,
          classCode: this.classCode,
          kidName : this.kidName,
        })
        alert("회원가입에 성공하여 로그인 페이지로 이동합니다 !!");
        this.$router.push({ name: 'Login' });
      }
      catch (e) {
        alert('회원가입에 실패하였습니다.')
      }
    },
    searchClass() {
      axios({
        // url 체크하기
        url: SERVER.URL + SERVER.ROUTES.searchClass + '?kinderCode=' + this.kindergardenCode,
        method: 'get',
      })
      .then((res) => {
        this.kindergardenClasses = res.data
      })
      .catch(() => {
        this.kindergardenClasses = []
      })
    },
  }
}
</script>

<style scoped>

</style>