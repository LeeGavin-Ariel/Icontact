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
  
  <!-- 필요 : 회원가입 요청 날리기 -->
  <button 
    @click="signup"
    :disabled="classCode === 'noValue'"
    >회원가입</button>
  </div>
</template>

<script>

import userApi from '@/api/user.js';
import axios from 'axios'
import SERVER from '@/api/drf.js'
import sendBird from '@/services/SendBird.js'
import {requestGet} from '@/utils/request.js'

export default {
  name: "SignupTeacher",
  data: () => {
    return {

      relationship: 2, // 부모1 선생2
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

        //sendbird 유저에 추가 , 닉네임 잘 들어가는지 확인
        const resultSendbird = await sendBird
            .addUser(this.$store.state.sendUserId, this.$store.state.sendUserName + ' 선생님')        

        this.$store.commit('SET_USER', resultSendbird);
        await sendBird.login(this.$store.state.sendUserId);

        //같은반 유저 불러오기
        var classMember = await requestGet(SERVER.URL + SERVER.ROUTES.getClassMember + '?classCode=' + this.classCode);
        console.log('classMember');
        console.log(classMember);

        var sendUserId = this.$store.state.sendUserId
         
        //같은반 사람들과 채팅방 생성
        classMember.forEach( (element, index) => {
          // console.log(element.userId != sendUserId?'다르다':'같다')

          // 두 아이디가 다르면 채팅방 생성
          if(element.userId != sendUserId){
              console.log(element.userId +","+ sendUserId);
              var roomName = sendUserId +"님과 " +element.userId +"의 방";
              
              (function(index){
                console.log('시간' + (250*index));
                setTimeout(()=>
                  sendBird 
                .createGroupChannelWithUserIds(sendUserId, element.userId, roomName, null, null)
                ,200*index);
              })(index);
          }
        });

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
        console.log(res.data)
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