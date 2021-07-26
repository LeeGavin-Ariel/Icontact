<template>
  <div>
    <h1>선생님 마이페이지</h1>
    <!-- <img :src="user.profileImg" alt="user-profile-image"> -->
    <!-- <div>[{{user.className}}] 선생님</div> -->
    <div>내 정보
      <!-- <div>ID : {{ user.userId }}</div>
      <div>이름 : {{ user.userName }}</div>
      <div>원 : {{ user.kinderName }}</div>
      <div>반 : {{ user.className }}</div> -->
    </div>

    <!-- 
      1. 사진변경 시 requestPost
      2. 비밀번호 변경시 requestPost
      3. 처음 프로필 내용 불러올 때 requestGet
     -->

    <!-- 사진 변경 모달 -->
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
      사진변경
    </button>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">사진 변경</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <input 
            type="file" 
            @change="onFileSelected"
            accept="image/*">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            <button 
            type="button"
            class="btn btn-primary"
            @click="pictureUpload"
            >변경하기</button>
          </div>
        </div>
      </div>
    </div>


    <!-- 비밀번호 변경 모달 -->
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
      비밀번호 변경
    </button>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">비밀번호 변경</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            
            <!-- 현재 비밀번호 -->
            <div>
              <label for="password">비밀번호: </label>
              <input type="password" 
              id="password" 
              v-model="currentPassword" 
              placeholder="비밀번호를 입력하세요">
              <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
              <div v-if="error.currentPassword">{{error.currentPassword}}</div>
            </div>

            <!-- 변경할 비밀번호 -->
            <div>
              <label for="password">비밀번호: </label>
              <input type="password" 
              id="password" 
              v-model="changePassword" 
              placeholder="비밀번호를 입력하세요">
              <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
              <div v-if="error.changePassword">{{error.changePassword}}</div>
            </div>

            <!-- 변경할 비밀번호 확인 -->
            <div>
              <label for="passwordConfirm">비밀번호확인: </label>
              <input type="password" 
              id="passwordConfirm" 
              v-model="changePasswordConfirm" 
              placeholder="비밀번호 확인을 입력하세요">
              <!-- 유효성 검사 통과 못하면 에러 메시지 출력 -->
              <div v-if="error.changePasswordConfirm">{{error.changePasswordConfirm}}</div>
            </div>

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            <button 
            @click="savePassword"
            :disabled="!currentPassword && !changePassword && error.currentPassword && error.changePassword && !(changePassword==changePasswordConfirm)"
            type="button" class="btn btn-primary" :data-bs-dismiss="{modal : isChanged}">변경하기</button>
          </div>
        </div>
      </div>
    </div>



  </div>
</template>

<script>
import userApi from '@/api/user.js';
import PV from "password-validator";
// import axios from 'axios'
// import SERVER from '@/api/drf.js'
export default {
  name: 'MyPage',
  data: () => {
    return {
      userId: '',
      profileImg: '',
      className: '',
      userName: '',
      kinderName: '',

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
      this.changeImg = event.target.files[0]
      console.log(this.changeImg)
    },

    // userid, 변경된 password 날리기
    async savePassword() {
      try {
        await userApi.updateTeacher(this.userId, this.changePassword,
          // 뭘 날려줘야하는지 의논해서 결정하기.
          // 일단 아이디, 비밀번호  
        {
          "access-token": sessionStorage.getItem('access-token')
        });
        alert("비밀번호가 변경되었습니다");
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
      let result = await userApi.getTeacher(this.userId, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
      });
      this.profileImg = result.profileImg
      this.className = result.className
      this.userName = result.userName
      this.kinderName = result.kinderName
    },

    // userid, picture url 날리기
    async pictureUpload() {
      // picture에 대한 정보를 어떻게 날려야 하는지 의논 후 결정.
      const fd = new FormData();
      fd.append('image', this.selectedFile, this.selectedFile.name)
      
      // 이미지 파일 어떻게 넘기는지
      await userApi.updateUser({
          // 뭘 날려줘야하는지 의논해서 결정하기.
          // 
          // 
          // 
          // 
          //
        }, {
          "access-token": sessionStorage.getItem('access-token')
        });
    }

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
  }
}
</script>

<style scoped>

</style>