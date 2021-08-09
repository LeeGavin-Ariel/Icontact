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
    
        <select name="classCode" class="select-class-code" v-model="classCode">
          <option value="noValue">반 선택</option>
          <option v-for="[Class, idx] in kindergardenClasses" v-bind:key="idx" v-bind:value="idx">
            {{ Class }}
          </option>
        </select>
        

        <!-- 회원가입 버튼 -->
        <v-btn 
          block 
          rounded
          style="background-color: #58679A; color: white;"
          :disabled="classCode === 'noValue'"
          @click="signup"
          >
          회원가입
        </v-btn>
        <button @click="$router.go(-1)"
          class="backBtn"
        >
          뒤로 가기
        </button>
      </div>
    </div>
  
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
        await sendBird.addUser(this.$store.state.sendUserId, this.$store.state.sendUserName + ' 선생님')        
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
        alert("회원가입에 성공하였습니다");
        this.$router.push({ name: 'Login' });
      }
      catch (e) {
        alert('회원가입에 실패하였습니다')
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
@font-face {
  font-family: "TmoneyRoundWindExtraBold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
button, h1 {
  font-family: "TmoneyRoundWindExtraBold";
}
#bg {
  background-color: #a8b1cf;
  width: 100%;
  height: 100%;
}
h1 {
  font-weight: 900;
  letter-spacing: 2px;
  font-size:48px;
  color: #58679A;
}
.logo {
  width:60px;
  fill:white;
  padding-right:10px;
  display:inline-block;
  vertical-align: middle;
}
.formInput {
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 1px 1px 1px 1px #58679a;
  border-radius: 70px;
  height: 36px;
  width: 300px;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.backBtn{
  border-radius: 4%;
  width: 150px;
  margin: 15px 3px 3px 3px;
  padding: 0px 7px 0px 3px;
  font-size: small;
  letter-spacing: -1px;
  color: #404c74;
}
.select-class-code {
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 1px 1px 1px 1px #58679A;
  border-radius: 70px; 
  height: 36px; 
  width: 300px; 
  padding:0px 0px 0px 15px;
  margin: 3px 3px 6px 3px;
}
</style>