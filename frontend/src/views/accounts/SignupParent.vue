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
      placeholder="유치원 코드를 입력하세요">
    </div>
    <div v-if="kindergardenCode && searchKindergardenClass.length === 0">존재하지 않는 유치원 코드입니다.</div>

    <!-- 반 목록 선택 (드롭다운) -->
    <select name="kindergardenClass" id="kindergardenClass" v-model="kindergardenClass">
      <option value="noValue">반 선택</option>
      <!-- option v-for로 -->
      <option v-for="Class in searchKindergardenClass" v-bind:key="Class" v-bind:value="Class">
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
      :disabled="kindergardenClass === 'noValue' || !kidName"
      >회원가입</button>
  </div>
  
 
</template>

<script>
import { mapActions,mapState } from 'vuex'
import userApi from '@/api/auth.js';

export default {
  name: "SignupParent",
  data: () => {
    return {
      relationship: 1, // 부모1 선생2
      kindergardenCode: '',
      kindergardenClass: 'noValue',
      kidName: '',
    }
  },
  methods: {
    ...mapActions ([
      'searchClass',
    ]),
    ...mapState ([
      'searchKindergardenClass',
    ]),
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
  }
}
</script>

<style>

</style>