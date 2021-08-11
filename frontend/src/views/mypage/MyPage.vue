<template>
  <div id='bg' style="display:flex; justify-content:center; height:100%; align-items: center; font-family: 'EliceDigitalBaeum_Bold';">
    <div class="col-sm-8 col-md-5" style="height: 90%; background-color: rgba(256,256,256,0.7); border-style: solid;  border-color:#a8b1cf; border-radius: 40px;">
      <div class="d-flex justify-content-center align-items-center m-4">
        <div style="position:relative; ">
          <v-avatar
            color="orange"
            size="200px"
          >
            <img
            :src="this.profileImg"
            alt="user-profile-image" id='profileImg'>
          </v-avatar>
          <button data-bs-toggle="modal" data-bs-target="#profileModal" style="width:15%; height:15%; position:absolute; bottom:0; right:5px">
            <img src="@/assets/flaticon/pencil.png" style="width:100%; height:100%;" alt="" >
          </button>
        </div>
        <!-- 사진 변경 버튼 -->
      
      </div>
      <div class="col-sm-8 col-md-8 mx-auto text-center">
        <h4 v-if="type===2">[{{className}}] {{ userName }} 선생님</h4>
        <h4 v-else>[{{className}}반] {{ kidName }} 학부모님 </h4>
        <!-- 비밀 번호 변경 버튼 -->
        <button data-bs-toggle="modal" data-bs-target="#passwordModal">
          비밀번호 변경
        </button>
      </div>
      <div class="text-left d-grid gap-2 col-6 mx-auto">
        <h4 style="margin-bottom:0;">내 정보</h4>
        <hr style="color:#a8b1cf; height:3px; margin:5px;">
        <h6>아이디 : {{ userId }}</h6>
        <h6>이름 : {{ userName }}</h6>
        <h6 v-if="type===1">아이 이름 : {{ kidName }}</h6>
        <h6>유치원 명 : {{ kinderName }}</h6>
        <h6>반 명 : {{ className }}</h6>
      </div>

      
      
    </div>

    <!-- 사진 변경 모달 -->
    <div class="modal fade" id="profileModal" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header" style="background-color:#a8b1cf;">  
            <h5 class="modal-title" id="profileModalLabel">프로필 사진 변경</h5>
            <button type="button" class="btn-close m-0" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body d-flex justify-content-center">


          <!-- <input
          type="file" 
          name="photo" id="photo"
          @change="onFileSelected"
          accept="image/*"> -->

          <div class="upload-btn-wrapper">
            <div id="image_container" style="margin-bottom:10px"></div>
            <button class="btn btnstyle">사진 선택</button>
            <input type="file" name="myfile" id="photo" @change="onFileSelected"/>
          </div>
            

            
          </div>
          <div class="modal-footer">
            <button 
            type="button"
            class="btn"
            style="background-color:#a8b1cf;"
            @click="pictureUpload"
            data-bs-dismiss="modal"
            :disabled="!changeImg"
            >변경하기</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 비밀번호 변경 모달 -->    
    <div class="modal fade" id="passwordModal" tabindex="-1" aria-labelledby="passwordModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header" style="background-color:#a8b1cf;">
            <h5 class="modal-title" id="passwordModalLabel">비밀번호 변경</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            
            <!-- 현재 비밀번호 -->
            <div>
              <!-- <input type="password" 
              id="currentPassword" 
              v-model="currentPassword" 
              placeholder="비밀번호를 입력하세요">
              <div v-if="error.currentPassword">{{error.currentPassword}}</div> -->
              <input
              class="formInput"
              type="password"
              color="#58679A"
              v-model="currentPassword" 
              placeholder="현재 비밀번호"
              style="text-align:left; margin:3px 0;"
              >
              <div v-if="error.currentPassword" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.currentPassword}}</div>
            </div>
            
            



            <!-- 변경할 비밀번호 -->
            <div>
              <!-- <label for="changePassword">변경할 비밀번호: </label>
              <input type="password" 
              id="changePassword" 
              v-model="changePassword" 
              placeholder="비밀번호를 입력하세요">
              <div v-if="error.changePassword">{{error.changePassword}}</div> -->

              <input
              class="formInput"
              type="password"
              color="#58679A"
              v-model="changePassword" 
              placeholder="변경할 비밀번호"
              style="text-align:left; margin:3px 0;"
              >
              <div v-if="error.changePassword" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.changePassword}}</div>
            </div>

            <!-- 변경할 비밀번호 확인 -->
            <div>
              <!-- <label for="changePasswordConfirm">변경할 비밀번호확인: </label>
              <input type="password" 
              id="changePasswordConfirm" 
              v-model="changePasswordConfirm" 
              placeholder="비밀번호 확인을 입력하세요">
              <div v-if="error.changePasswordConfirm">{{error.changePasswordConfirm}}</div> -->
              <input
              class="formInput"
              type="password"
              color="#58679A"
              v-model="changePasswordConfirm" 
              placeholder="변경할 비밀번호 확인"
              style="text-align:left; margin:3px 0;"
              >
              <div v-if="error.changePasswordConfirm" style="text-align:left; color: red; font-size: 12px; margin-left: 19px">{{error.changePasswordConfirm}}</div>
            </div>

          </div>
          <div class="modal-footer">
            <button 
            @click="savePassword"
            style="background-color:#a8b1cf;"
            :disabled="!currentPassword || !changePassword || error.currentPassword || error.changePassword || !(changePassword==changePasswordConfirm)"
            type="button" class="btn" data-bs-dismiss="modal">변경하기</button>
          </div>
        </div>
      </div>
    </div>


  <img v-if="window.width > 960" src="@/assets/notfound.png" style="width:20%; position:absolute; right:15px; bottom:15px;" alt="">
  <img v-if="window.width > 960" src="@/assets/servererror.png" style="width:20%; position:absolute; left:15px; bottom:15px;" alt="">

  </div>
</template>

<script>
import userApi from '@/api/user.js';
import PV from "password-validator";
import awss3 from '@/utils/awss3.js';
// import axios from 'axios'
// import SERVER from '@/api/drf.js'
const url = "https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/";

export default {
  name: 'MyPage',
  data: () => {
    return {
      userId: '',
      profileImg: '',
      className: '',
      userName: '',
      kinderName: '',
      kidName: '',
      type: '',

      currentPassword: '',
      changePassword: '',
      changePasswordConfirm: '',
      passwordSchema: new PV(),
      error: {
        currentPassword: '',
        changePassword: '',
        changePasswordConfirm: '',
      },
      isChanged: false,
      changeImg: '',

      // 화면 크기
      window: {
        width: 0,
        height: 0
      }
    }
  },
  watch: {
    currentPassword: function() {
      this.checkForm();
    },
    changePassword: function() {
      this.checkForm();
    },
    changePasswordConfirm: function() {
      this.checkForm();
    }
  },
  

  methods: {
    // 화면 크기 관련
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
    },


    checkForm() {
      if (!this.passwordSchema.validate(this.currentPassword) || this.currentPassword.length < 8){
        this.error.currentPassword = "비밀 번호 형식이 아닙니다.";
      }else {
        this.error.currentPassword = false
      }
      if (!this.passwordSchema.validate(this.changePassword) || this.changePassword.length < 8){
        this.error.changePassword = "비밀 번호 형식이 아닙니다.";
      }else {
        this.error.changePassword = false
      }
      if (this.changePassword != this.changePasswordConfirm){
        this.error.changePasswordConfirm = "비밀 번호가 틀립니다.";
      }else {
        this.error.changePasswordConfirm = false
      }
    },

    onFileSelected(event) {
      let item = document.querySelector("div#image_container")
      if (item.firstChild) {
        item.removeChild(item.firstChild)
      }

      let reader = new FileReader();
      reader.onload = function(event) {
        let img = document.createElement("img")
        img.style.width = '120px'
        img.style.height = '120px'
        img.style.borderRadius = '70%'
        img.setAttribute("src", event.target.result)
        document.querySelector("div#image_container").appendChild(img)
      }
      if (event.target.files[0]) {
        reader.readAsDataURL(event.target.files[0])
        this.changeImg = event.target.files[0]
      }
    },

    // userid, 변경된 password 날리기
    async savePassword() {
      try {
        await userApi.updateUserPassword({
          // 뭘 날려줘야하는지 의논해서 결정하기.
          // 일단 아이디, 비밀번호
          userId : this.userId,
          password : this.changePassword
        },
        {
          "access-token": sessionStorage.getItem('access-token')
        });
        alert("비밀번호가 변경되었습니다");
        this.currentPassword = ''
        this.changePassword = ''
        this.changePasswordConfirm = ''
        this.isChanged = true
      } catch (e) {
        alert("비밀번호 변경에 실패하였습니다.");
      }
    },

    // userid 
    async getuserprofile() {
      this.userId = this.$route.params.userId
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let result = await userApi.getUser(this.userId, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
      });
      this.profileImg = url + result.profileImg
      this.className = result.className
      this.userName = result.userName
      this.kinderName = result.kinderName
      this.kidName = result.kidName
      this.type = result.type
    },


    // userid, picture url 날리기
    async pictureUpload() {
      await awss3.updatePhoto('profileImg', this.$store.state.user.profileImg, 'photo')
      .then( (res) => {
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        userApi.updateUserProfileImg({
          userId: this.$store.state.user.userId,
          profileImg: res[0]
        },
        {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        })
        this.$store.state.user.profileImg = res[0];
        this.profileImg = url + res[0];
      });
    },
    

  },
  created() {
    this.getuserprofile(),
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();

    // 화면 크기변경시 최초 크기 설정
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  },
  // 화면 크기 변경시
  destroyed() {
    window.removeEventListener('resize', this.handleResize);
  },
}
</script>

<style scoped>
#bg {
  background-color: #a8b1cf;
  width: 100%;
  height: 100%;
}

.modal {
  z-index: 10000;
}




.upload-btn-wrapper {
  position: relative;
  overflow: hidden;
  display: inline-block;
}

.btnstyle {
  border: 2px solid gray;
  color: gray;
  background-color: white;
  padding: 8px 20px;
  border-radius: 8px;
  font-size: 20px;
  font-weight: bold;
}

.upload-btn-wrapper input[type=file] {
  font-size: 100px;
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
}



.formInput {
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 1px 1px 1px 1px #58679a;
  border-radius: 70px;
  height: 36px;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
</style>