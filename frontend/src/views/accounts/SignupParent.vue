<template>
  <div id="bg">
    <div class="inner-header flex flex-column">
      <!-- 로고 -->
      <div>
        <br>
        <br>
        <img src="@/assets/flaticon/toys.png" class="logo" alt="">
        <h1>회원가입</h1>
        <br>
      </div>
    
      <!-- 학부모 회원가입 폼 -->
      <div style="width: 300px;" >
        <!-- 아이 이름 입력 -->
        <input
          class="formInput"
          type="text"
          id="kidName"
          color="#58679A"
          v-model="kidName"
          placeholder="아이 이름"
          style="text-align:left;"
        >


        <!-- 유치원 코드-->
        <input
          class="formInput"
          type="text"
          oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
          id="kindergardenCode"
          color="#58679A"
          v-model="kindergardenCode"
          @input="searchClass"
          maxlength="4"
          placeholder="유치원 코드"
          style="text-align:left;"
        >
        <div v-if="!kindergardenCode || kindergardenClasses.length === 0" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">존재하지 않는 유치원 코드입니다.</div>
    


        <v-select
          v-model="classCode"
          :items="kindergardenClasses"
          label="반 선택"
          dense
        ></v-select>

        <!-- 반 목록 선택 (드롭다운) -->
        <!--
        <select name="classCode" id="classCode" v-model="classCode">
          <option value="noValue">반 선택</option>
          <option v-for="[Class, idx] in kindergardenClasses" v-bind:key="idx" v-bind:value="idx">
            {{ Class }}
          </option>
        </select>
        -->
        

        <!-- 회원가입 버튼 -->
        <v-btn 
          block 
          rounded
          style="background-color: #58679A; color: white;"
          :disabled="classCode === 'noValue' || !kidName"
          @click="signup"
          >
          회원가입
        </v-btn>

      </div>
    </div>
  </div>
</template>

<script>
import {userApi} from '@/api/user.js';
import axios from 'axios'
import SERVER from '@/api/drf.js'
import sendBird from '@/services/SendBird.js'
import {requestGet} from '@/utils/request.js'

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

        //sendbird 유저에 추가 , 닉네임 잘 들어가는지 확인
        const resultSendbird = await sendBird
            .addUser(this.$store.state.sendUserId, this.kidName + ' 부모님')        

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
                console.log('시간' + (200*index));
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
        console.log(e);
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