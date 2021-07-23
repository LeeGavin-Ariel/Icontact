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
      @click="signup(relationship, kindergardenCode, kidName, kindergardenClass)" 
      :disabled="kindergardenClass === 'noValue' || !kidName"
      >회원가입</button>
  </div>
  
 
</template>

<script>
import { mapActions,mapState } from 'vuex'

export default {
  name: "SignupParent",
  data: () => {
    return {
      // 선생님 0, 부모 1
      relationship: 1,
      kindergardenCode: '',
      kindergardenClass: 'noValue',
      kidName: '',
    }
  },
  methods: {
    ...mapActions ([
      'searchClass',
      'signup'
    ]),
    ...mapState ([
      'searchKindergardenClass',
    ])
  }
}
</script>

<style>

</style>