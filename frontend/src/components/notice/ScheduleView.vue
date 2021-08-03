<template>
  <div style="overflow-y:scroll;" class="col">
    <!-- 디테일 -->

    일정
    <button v-if="identity === 1 && updating === 0" @click="createNewNotice">연필</button>
    | 
    <button v-if="identity === 1 && updating === 1" @click="updateNotice">연필</button>
    
    <button v-if="identity === 1 && updating === 0" @click="updateNotice">글 수정</button>
      | 
    <button v-if="identity === 1" @click="deleteNotice">글 삭제</button>
    <button v-if="identity === 1 && creating" @click="offCreateForm">글 작성 취소</button>
    <v-sheet
      rounded="lg"
      v-if="noticeType === 1 && noticeDetail && (!creating && !updating)"
    >
      {{noticeDetail}}
      <p>작성 시간 : {{noticeDetail.createDate}}</p>
      <p>작성자 : {{noticeDetail.kidName}} 학부모님</p>
      <p>투약 시간 : {{noticeDetail.dosageTime}}</p>
      <p>투약 용량 : {{noticeDetail.dosageVol}}</p>
      <p>특이사항 : {{noticeDetail.specialNote}}</p>
      <p>증상 : {{noticeDetail.symptom}}</p>
    </v-sheet>

    <v-sheet
      rounded="lg"
      v-if="noticeType === 1 && (creating || updating)"
    >
      <p>증상 : </p>
      <input type="text" v-model="symptom">
      <p>약 종류 : </p>
      <input type="text" v-model="medicineType">
      <p>투약 용량 : </p>
      <input type="number" v-model="dosageVol">
      <p>투약 횟수 : </p>
      <input type="number" v-model="dosageCnt">
      <p>투약 시간 : </p>
      <input type="text" v-model="dosageTime">
      <p>보관 방법 : </p>
      <input type="text" v-model="storage">
      <p>특이 사항 : </p>
      <input type="text" v-model="specialNote">
    </v-sheet>

    <v-sheet
      rounded="lg"
      v-if="noticeType === 2 && noticeDetail && (!creating && !updating)"
    >
    {{noticeDetail}}
      <p>작성 시간 : {{noticeDetail.createDate}}</p>
      <p>작성자 : {{noticeDetail.kidName}} 학부모님</p>
      <p>귀가 날짜 : {{noticeDetail.rhDate}}</p>
      <p>귀가 시간 : {{noticeDetail.rhTime}}</p>
      <p>동행인 : {{noticeDetail.guardian}}</p>
      <p>동행인 전화번호 : {{noticeDetail.guardianTel}}</p>
      <p>긴급연락망 : {{noticeDetail.emergency}}</p>
      <p>긴급연락처 : {{noticeDetail.emergencyTel}}</p>
    </v-sheet>

    <v-sheet
      rounded="lg"
      v-if="noticeType === 2 && (creating || updating)"
    >
      <p>귀가 날짜 : </p>
      <input type="text" v-model="rhDate">
      <p>귀가 시간 : </p>
      <input type="text" v-model="rhTime">
      <p>동행인 : </p>
      <input type="text" v-model="guardian">
      <p>동행인 전화번호 : </p>
      <input type="text" v-model="guardianTel">
      <p>긴급연락망 : </p>
      <input type="text" v-model="emergency">
      <p>긴급연락처 : </p>
      <input type="text" v-model="emergencyTel">
    </v-sheet>


  </div>

</template>

<script>
import noticeApi from '@/api/notice.js';
export default {
  name: "NoticeDetail",

  props:{
    identity: {
      identity: Number,
    },
    noticeType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
  },


  data () {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,

      // 투약 관련 변수
      symptom: '',
      medicineType: '',
      dosageVol: 0,
      dosageCnt: 0,
      dosageTime: '',
      storage: '',
      specialNote: '',
      
      // 귀가 관련 변수
      rhDate: '',
      rhTime: '',
      guardian: '',
      guardianTel: '',
      emergency: '',
      emergencyTel: '',

      creating: 0,
      updating: 0,
    }
  },
  watch: {
    'id' : function() {
      console.log('11111')
      if (this.id !== 0) {
        this.getNoticeDetail()
      }
    }
  },


  methods: {

    offCreateForm() {
      this.creating = 0
    },

    async getNoticeDetail() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        noticeType: this.noticeType,
        id: this.id
      }
      // 선생인지, 학부모인지에 따라 다르게 받음.
      let result = await noticeApi.getNoticeDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
        // 어떻게 날라오는지 확인후 데이터 집어넣기4
      if (this.noticeType === 1) {
        this.noticeDetail = result.dosage
      }
      else if (this.noticeType === 2){
        this.noticeDetail = result.returnhome
      }
      console.log(this.noticeDetail)
      this.creating = 0
      this.updating = 0
    },

    // 글 작성 폼 띄우기
    async createNewNotice() {
      // 글 작성 중인 상태가 아니라면 글 작성 중 상태로 바꿈.

      if (this.updating === 1){
        this.updating = 0
      }

      if (this.creating === 0) {
        if (this.noticeType === 1) {
          this.symptom = ''
          this.medicineType = ''
          this.dosageVol = 0
          this.dosageCnt = 0
          this.dosageTime = ''
          this.storage = ''
          this.specialNote = ''
        }
        else if (this.noticeType === 2) {
          this.rhDate = ''
          this.rhTime = ''
          this.guardian = ''
          this.guardianTel = ''
          this.emergency = ''
          this.emergencyTel = ''
        }
        this.creating = 1
      }
      // 글 작성 중인 상태라면 요청 보내기.
      else if (this.creating === 1) {
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        if (this.noticeType === 1) {
          let data = {
          noticeType: this.noticeType,
          userId: this.$store.state.user.userId,
          symptom: this.symptom,
          medicineType: this.medicineType,
          dosageVol: this.dosageVol,
          dosageCnt: this.dosageCnt,
          dosageTime: this.dosageTime,
          storage: this.storage,
          specialNote: this.specialNote,
          }
          let result = await noticeApi.createNotice(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
        }
        else if (this.noticeType === 2) {
          let data = {
          noticeType: this.noticeType,
          userId: this.$store.state.user.userId,
          rhDate: this.rhDate,
          rhTime: this.rhTime,
          guardian : this.guardian,
          guardianTel: this.guardianTel,
          emergency: this.emergency,
          emergencyTel: this.emergencyTel,
          }
          let result = await noticeApi.createNotice(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
          
        }
        this.creating = 0
        this.$emit('get-notebooklist', this.noticeType)
        // this.getNotice()
      }
    },

    async updateNotice() {

      if (this.creating === 1) {
        this.creating = 0
      }

      if (this.updating === 0 ) {
          if (this.noticeType === 1) {
          this.symptom = this.noticeDetail.symptom
          this.medicineType = this.noticeDetail.medicineType
          this.dosageVol = this.noticeDetail.dosageVol
          this.dosageCnt = this.noticeDetail.dosageCnt
          this.dosageTime = this.noticeDetail.dosageTime
          this.storage = this.noticeDetail.storage
          this.specialNote = this.noticeDetail.specialNote
        }
        else if (this.noticeType === 2) {
          this.rhDate = this.noticeDetail.rhDate
          this.rhTime = this.noticeDetail.rhTime
          this.guardian = this.noticeDetail.guardian
          this.guardianTel = this.noticeDetail.guardianTel
          this.emergency = this.noticeDetail.emergency
          this.emergencyTel = this.noticeDetail.emergencyTel
        }
        this.updating = 1
      }

      // 여기서부터 다시 작성
      else if (this.updating === 1) {
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        if (this.noticeType === 1) {
          let data = {
          noticeType: this.noticeType,
          userId: this.$store.state.user.userId,
          id: this.id,
          symptom: this.symptom,
          medicineType: this.medicineType,
          dosageVol: this.dosageVol,
          dosageCnt: this.dosageCnt,
          dosageTime: this.dosageTime,
          storage: this.storage,
          specialNote: this.specialNote,
          }
          let result = await noticeApi.updateNotice(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
        }
        else if (this.noticeType === 2) {
          let data = {
          noticeType: this.noticeType,
          userId: this.$store.state.user.userId,
          id: this.id,
          rhDate: this.rhDate,
          rhTime: this.rhTime,
          guardian : this.guardian,
          guardianTel: this.guardianTel,
          emergency: this.emergency,
          emergencyTel: this.emergencyTel,
          }
          let result = await noticeApi.updateNotice(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
        }
        this.updating = 0
        this.$emit('get-notebooklist', this.noticeType)
        // this.getNotice()
      }
    },

    async deleteNotice() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      if (this.noticeType === 1){
        let data = {
        noticeType: this.noticeType,
        id: this.noticeDetail.dosageId
      }
      let result = await noticeApi.deleteNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      }
      else if (this.noticeType === 2){
        let data = {
        noticeType: this.noticeType,
        id: this.noticeDetail.rhId
      }
      let result = await noticeApi.deleteNotice(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      }
      this.$emit('get-notebooklist', this.noticeType)
      // window.location.reload()
    }
  },


}
</script>

<style scoped>

</style>