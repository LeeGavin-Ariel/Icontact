<template>
  <div id='bg'>

    <!-- 마이페이지 -->
    <div class="mypage-body col-sm-8 col-md-5 p-0">

      <!-- 마이페이지 타이틀-->
      <div class="mypage-title">
        <div class="ml-2" style="font-size: 24px">마이페이지</div>
      </div>
      
      <!-- 비밀번호 변경 버튼 -->
      <div align="right" class="mr-3 mt-2">
        <button data-bs-toggle="modal" data-bs-target="#passwordModal" class="pwdBtn">
          비밀번호 변경
        </button>
      </div>

      <!-- 프로필 이미지 변경 -->
      <div class="d-flex justify-content-center align-items-center m-4">
        
        <!-- 프로필 이미지 변경 -->
        <div class="pt-5" style="position:relative; ">
          <v-avatar
            size="200px"
          >
            <img
            :src="this.profileImg"
            alt="user-profile-image"
            id='profileImg'>
          </v-avatar>
          
          <!-- 사진 변경 버튼 -->
          <button data-bs-toggle="modal" data-bs-target="#profileModal" style="width:15%; height:15%; position:absolute; bottom:0; right:5px">
            <img src="@/assets/flaticon/pencil.png" style="width:100%; height:100%;" alt="" >
          </button>
        
        </div>
      </div>

      <!-- 호칭 -->
      <div class="col-sm-8 col-md-8 mx-auto text-center userName">
        <p v-if="type===2">[{{className}}반] {{ userName }} 선생님</p>
        <p v-else>[{{className}}] {{ kidName }} 학부모님 </p>
        <!-- 비밀 번호 변경 버튼 -->
        <!-- <button data-bs-toggle="modal" data-bs-target="#passwordModal">
          비밀번호 변경
        </button> -->
      </div>


      <!-- 내 정보 -->
      <div class="d-grid gap-2 col-8 mx-auto" align="center">
        <!-- <hr style="color:#a8b1cf; height:3px; margin:5px;"> -->
        <div>
        <table class="table table-borderless col-8" >
          <tr>
            <th class="col-6">아이디</th>
            <td class="col-6">{{ userId }}</td>
          </tr>
          <tr>
            <th class="col-6">이름</th>
            <td class="col-6">{{ userName }}</td>
          </tr>
          <tr v-if="type===1">
            <th class="col-4">아이 이름</th>
            <td class="col-6">{{ kidName }}</td>
          </tr>
          <tr>
            <th class="col-6">유치원 명</th>
            <td class="col-6">{{ kinderName }}</td>
          </tr>
          <tr>
            <th class="col-6">반 명</th>
            <td class="col-6">{{ className }}</td>
          </tr>
        </table>
        </div>
      </div>
  
    </div>
    <!-- 마이페이지 끝 -->
    
    
    <!-- 사진 변경 모달 -->
    <div class="modal fade" id="profileModal" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header" style="background-color:#a8b1cf;">  
            <h5 class="modal-title" id="profileModalLabel" style="color: rgb(256,256,256);">프로필 사진 변경</h5>
            <button type="button" class="btn-close m-0" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body d-flex justify-content-center">

          <!-- <input
          type="file" 
          name="photo" id="photo"
          @change="onFileSelected"
          accept="image/*"> -->

          <div>
            <div id="image_container" style="height: 125px;"></div>
          </div>
            

            
          </div>
          <div class="modal-footer">
            <label for="photo" class="btn btnstyle">사진 선택</label>
            <input type="file" name="myfile" id="photo" style="display:none " @change="onFileSelected"/>
            <button 
            type="button"
            class="btn btnstyle"
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


  <!-- <img v-if="window.width > 960" src="@/assets/notfound.png" style="width:20%; position:absolute; right:15px; bottom:15px;" alt=""> -->
  <img v-if="window.width > 960" src="@/assets/icontact/children.png" style="width:15%; position:absolute; right:23rem; bottom:3.2rem;" alt="">

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
        this.$fire({
            html: `<a href="javascript:void(0);"></a>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">비밀번호가 변경되었습니다.</p>`,
            type: 'success',
            confirmButtonColor: '#58679A',
            focusConfirm: false
        })
        this.currentPassword = ''
        this.changePassword = ''
        this.changePasswordConfirm = ''
        this.isChanged = true
      } catch (e) {
        this.$fire({
            html: `<a href="javascript:void(0);"></a>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">비밀번호 변경에 실패하였습니다.</p>
            <p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">다시 시도해주세요.</p>`,
            type: 'error',
            confirmButtonColor: '#58679A',
            focusConfirm: false
        })
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
  background-color: rgba(102,122,188, 0.1);
  width: 100%;
  height: 100%;
  display:flex;
  justify-content:center;
  align-items: center;
}
* {
  font-family: 'NanumSquareRound';
}
button:hover {
  color: rgba(0,0,0,0.4);
  transition:0.3s;
}
.mypage-body {
  background-color:rgba(256,256,256,0.7); 
  height: 85%;
  border-radius: 8px;
}
.mypage-title{
  color: rgba(256,256,256,0.9);
  background-color: #a8b1cf;
  border-radius: 5px 5px 0px 0px;
  height: 35px;
  font-weight: 500;
}
.modal {
  z-index: 10000;
}
/* .upload-btn-wrapper {
  position: relative;
  overflow: hidden;
  display: inline-block;
} */

.btnstyle {
  color: rgb(40, 40, 40);
  border-radius: 10px;
  font-size: 16px;
  background-color:#bfc4d6; 
  border-radius:40px;
}
/* .upload-btn-wrapper input[type=file] {
  font-size: 100px;
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
} */
.formInput {
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 1px 1px 1px 1px #58679a;
  border-radius: 70px;
  height: 36px;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.pwdBtn{
  font-size:18px;
}
.userName{
  font-size: 26px;
  font-weight: 800;
}
table{
  font-size: 18px;
}
</style>