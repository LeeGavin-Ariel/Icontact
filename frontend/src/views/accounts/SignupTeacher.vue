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
    <select name="kindergardenClass" id="kindergardenClass" v-model="kindergardenClass">
      <option value="noValue">반 선택</option>
      <!-- option v-for로 -->
      <option v-for="Class in kindergardenClasses" v-bind:key="Class" v-bind:value="Class">
        {{ Class }}
      </option>
    </select>
  
  <!-- 필요 : 회원가입 요청 날리기 -->
  <button 
    @click="signup"
    :disabled="kindergardenClass === 'noValue'"
    >회원가입</button>
  </div>
</template>

<script>

import userApi from '@/api/auth.js';
import axios from 'axios'
import SERVER from '@/api/drf.js'
export default {
  name: "SignupTeacher",
  data: () => {
    return {

      relationship: 2, // 부모1 선생2
      kindergardenCode: '',
      kindergardenClass: 'noValue',
      kidName: '',
      kindergardenClasses:[]
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
          kindergardenCode: this.kindergardenCode,
          kidName : this.kidName,
          classClass: this.kindergardenClass,
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

<style scope>

</style>