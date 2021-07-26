<template>
  <div>
    <h1 class="text-center">마이페이지</h1>
    <!-- <img :src="user.profileImg" alt="user-profile-image"> -->
    <!-- <div>[{{user.className}}] 선생님</div> -->
    <div class="text-center d-grid gap-2 col-6 mx-auto border border-5 rounded-3 border-warning">내 정보
      <div>ID : {{ userId }}</div>
      <div>이름 : {{ userName }}</div>
      <div v-if="type===1">아이 이름 : {{ kidName }}</div>
      <div>원 : {{ kinderName }}</div>
      <div>반 : {{ className }}</div>
    </div>

    <!-- 
      1. 사진변경 시 requestPost
      2. 비밀번호 변경시 requestPost
      3. 처음 프로필 내용 불러올 때 requestGet
     -->

    <!-- 의상이 코드로 확인할 것. -->
    <!-- get user 잘 되는지 확인 (get방식 요청 날리는 방법이 맞는지.) -->
    <!-- 프로필 수정 요청 어떤것 날려야하는지. 반환값은 뭔지(url일듯) -->


    <!-- 사진 변경 모달 -->
    <button type="button" class="btn btn-primary d-grid gap-2 col-6 mx-auto" data-bs-toggle="modal" data-bs-target="#profileModal">
      사진변경
    </button>
    <div class="modal fade" id="profileModal" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="profileModalLabel">사진 변경</h5>
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

    <!-- 개인정보 수정할때 비밀번호 변경 : 유저 이름, 비밀번호, 프로필이미지 변경 : 유저이름, profile 이미지.
    이미지는 로컬에 저장하는걸로 (디폴트 이미지 포함)
    이미지 자체를 데이터로 어떻게 넘기고 받을것인가.
    멀티파트? 가빈 누나한테 물어보기. -->

    <!-- 비밀번호 변경 모달 -->
    <button type="button" class="btn btn-primary d-grid gap-2 col-6 mx-auto" data-bs-toggle="modal" data-bs-target="#passwordModal">
      비밀번호 변경
    </button>
    <div class="modal fade" id="passwordModal" tabindex="-1" aria-labelledby="passwordModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="passwordModalLabel">비밀번호 변경</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            
            <!-- 현재 비밀번호 -->
            <div>
              <label for="currentPassword">현재 비밀번호: </label>
              <input type="password" 
              id="currentPassword" 
              v-model="currentPassword" 
              placeholder="비밀번호를 입력하세요">
              <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
              <div v-if="error.currentPassword">{{error.currentPassword}}</div>
            </div>

            <!-- 변경할 비밀번호 -->
            <div>
              <label for="changePassword">변경할 비밀번호: </label>
              <input type="password" 
              id="changePassword" 
              v-model="changePassword" 
              placeholder="비밀번호를 입력하세요">
              <!-- 유효성 검사 통과 못하면 에러메시지 출력 -->
              <div v-if="error.changePassword">{{error.changePassword}}</div>
            </div>

            <!-- 변경할 비밀번호 확인 -->
            <div>
              <label for="changePasswordConfirm">변경할 비밀번호확인: </label>
              <input type="password" 
              id="changePasswordConfirm" 
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
        await userApi.updateUser(this.userId, this.changePassword,
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
      let result = await userApi.getUser(this.userId, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
      });
      this.profileImg = result.profileImg
      this.className = result.className
      this.userName = result.userName
      this.kinderName = result.kinderName
      this.kidName = result.kidName
      this.type = result.type
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