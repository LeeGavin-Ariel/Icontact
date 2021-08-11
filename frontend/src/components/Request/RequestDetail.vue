<template>
  <div class="col">
    <!-- 디테일 -->
    <!-- 새 글 작성시 -->
    <div class="ml-5 mr-5 mt-5">
    <button class="writeBtn" v-if="identity === 1 && updating === 0 && creating === 0" @click="createNewRequest"><img src="@/assets/write.png" style="width:3.8rem"></button>
    <!-- 글 작성하고 저장시 -->
    <button v-if="identity === 1 && updating === 0 && creating === 1" @click="createNewRequest">저장버튼<img src="@/assets/flaticon/toys.png" style="width:2rem"></button>
    <!-- 글 수정하고 저장시 -->
    <button v-if="identity === 1 && updating === 1 && creating === 0 && id" @click="updateRequest" style="">수정 완료</button>
    <!-- 글 수정할 시 -->
    <button v-if="identity === 1 && creating && id" @click="offCreateForm">글 작성 취소</button>
    <button v-if="identity === 1 && updating && id" @click="offUpdateForm">글 작성 취소</button>
    
    <v-sheet
      rounded="lg"
      v-if="id === 0 && !(creating || updating)"
    >
      등록된 요청이 없습니다.
    </v-sheet>
    
    
    <v-sheet
      rounded="lg"
      v-if="requestType === 1 && requestDetail && (!creating && !updating) && (id !== 0)"
    >
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container" style="height:84.8vh">
        <div align="right" class="mt-2"> 
          <button v-if="identity === 1 && updating === 0 && creating === 0 && id" @click="updateRequest" class="mr-2 text-deco">수정</button>
          <button v-if="identity === 1 && updating === 0 && creating === 0 && id" @click="deleteRequest" class="ml-2 mr-2 text-deco">삭제</button>
        </div>
        <div class="section-title">
        </div>
        <div align="center" class="mb-5">

          <div class="col-lg-11">
            <table class="table">
              <tbody>
                  <tr class="d-flex">
                      <td class="col-12">
                        <div style="font-size:35px"><strong>{{requestDetail.kidName}}</strong></div>
                        <div class="mt-3 mb-2">금일 자녀의 투약을 선생님께 의뢰합니다.</div>
                      </td>
                  </tr>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">투약내용</strong>
                      <table class=" table mt-5 mb-5" style="border-radius:10px; background-color: rgba(156,156,156,0.1)">
                        <tr class="d-flex">
                          <th class="col-2">증상</th>
                          <td class="col-10">{{requestDetail.symptom}}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">투약 용량</th>
                          <td class="col-10">{{requestDetail.dosageVol}}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">투약 횟수</th>
                          <td class="col-10">{{requestDetail.dosageCnt}} 회</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">투약 시간</th>
                          <td class="col-10">{{requestDetail.dosageTime}}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">보관 방법</th>
                          <td class="col-10">{{requestDetail.storage}}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">특이사항</th>
                          <td class="col-10">{{requestDetail.specialNote}}</td>
                        </tr>
                      </table>
                    </td>
                  </tr>
              </tbody>
            </table>
            <div>
              투약으로 인한 책임은 의뢰자가 집니다.
            </div>
          </div>

        </div>
      </div>
    </v-sheet>

    <v-sheet
      rounded="lg"
      v-if="requestType === 1 && (creating || updating)"
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
      v-if="requestType === 2 && requestDetail && (!creating && !updating) && (id !== 0)"
    >
      <p>작성 시간 : {{requestDetail.createDate}}</p>
      <p>작성자 : {{requestDetail.kidName}} 학부모님</p>
      <p>귀가 날짜 : {{requestDetail.rhDate}}</p>
      <p>귀가 시간 : {{requestDetail.rhTime}}</p>
      <p>동행인 : {{requestDetail.guardian}}</p>
      <p>동행인 전화번호 : {{requestDetail.guardianTel}}</p>
      <p>긴급연락망 : {{requestDetail.emergency}}</p>
      <p>긴급연락처 : {{requestDetail.emergencyTel}}</p>
    </v-sheet>

    <v-sheet
      rounded="lg"
      v-if="requestType === 2 && (creating || updating)"
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
  </div>

</template> 

<script>
import requestApi from '@/api/request.js';
export default {
  name: "RequestDetail",

  props:{
    identity: {
      identity: Number,
    },
    requestType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
  },


  data () {
    return {
      // 상세 내용을 저장할 변수
      requestDetail: null,

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
      console.log('아이디값 변경!')
      if (this.id !== 0) {
        this.getRequestDetail()
      }
    }
  },


  methods: {
    offCreateForm() {
      this.creating = 0
    },

    offUpdateForm() {
      this.updating = 0
    },

    async getRequestDetail() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        requestType: this.requestType,
        id: this.id
      }
      // 선생인지, 학부모인지에 따라 다르게 받음.
      let result = await requestApi.getRequestDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
        // 어떻게 날라오는지 확인후 데이터 집어넣기4
      if (this.requestType === 1) {
        this.requestDetail = result.dosage
      }
      else if (this.requestType === 2){
        this.requestDetail = result.returnhome
      }
      //console.log(this.requestDetail)
      this.creating = 0
      this.updating = 0
    },

    // 글 작성 폼 띄우기
    async createNewRequest() {
      // 글 작성 중인 상태가 아니라면 글 작성 중 상태로 바꿈.

      if (this.updating === 1){
        this.updating = 0
      }

      if (this.creating === 0) {
        if (this.requestType === 1) {
          this.symptom = ''
          this.medicineType = ''
          this.dosageVol = 0
          this.dosageCnt = 0
          this.dosageTime = ''
          this.storage = ''
          this.specialNote = ''
        }
        else if (this.requestType === 2) {
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
        if (this.requestType === 1) {
          let data = {
          requestType: this.requestType,
          userId: this.$store.state.user.userId,
          symptom: this.symptom,
          medicineType: this.medicineType,
          dosageVol: this.dosageVol,
          dosageCnt: this.dosageCnt,
          dosageTime: this.dosageTime,
          storage: this.storage,
          specialNote: this.specialNote,
          }
          let result = await requestApi.createRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
        }
        else if (this.requestType === 2) {
          let data = {
          requestType: this.requestType,
          userId: this.$store.state.user.userId,
          rhDate: this.rhDate,
          rhTime: this.rhTime,
          guardian : this.guardian,
          guardianTel: this.guardianTel,
          emergency: this.emergency,
          emergencyTel: this.emergencyTel,
          }
          let result = await requestApi.createRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
          
        }
        this.creating = 0
        this.$emit('get-notebooklist', this.requestType)
        // this.getRequest()
      }
    },

    async updateRequest() {

      if (this.creating === 1) {
        this.creating = 0
      }

      if (this.updating === 0 ) {
          if (this.requestType === 1) {
          this.symptom = this.requestDetail.symptom
          this.medicineType = this.requestDetail.medicineType
          this.dosageVol = this.requestDetail.dosageVol
          this.dosageCnt = this.requestDetail.dosageCnt
          this.dosageTime = this.requestDetail.dosageTime
          this.storage = this.requestDetail.storage
          this.specialNote = this.requestDetail.specialNote
        }
        else if (this.requestType === 2) {
          this.rhDate = this.requestDetail.rhDate
          this.rhTime = this.requestDetail.rhTime
          this.guardian = this.requestDetail.guardian
          this.guardianTel = this.requestDetail.guardianTel
          this.emergency = this.requestDetail.emergency
          this.emergencyTel = this.requestDetail.emergencyTel
        }
        this.updating = 1
      }

      // 여기서부터 다시 작성
      else if (this.updating === 1) {
        let accessToken = sessionStorage.getItem('access-token')
        let refreshToken = sessionStorage.getItem('refresh-token')
        if (this.requestType === 1) {
          let data = {
          requestType: this.requestType,
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
          let result = await requestApi.updateRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
        }
        else if (this.requestType === 2) {
          let data = {
          requestType: this.requestType,
          userId: this.$store.state.user.userId,
          id: this.id,
          rhDate: this.rhDate,
          rhTime: this.rhTime,
          guardian : this.guardian,
          guardianTel: this.guardianTel,
          emergency: this.emergency,
          emergencyTel: this.emergencyTel,
          }
          let result = await requestApi.updateRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
          console.log(result)
        }
        this.updating = 0
        this.$emit('get-notebooklist', this.requestType)
        // this.getRequest()
      }
    },

    async deleteRequest() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      if (this.requestType === 1){
        let data = {
        requestType: this.requestType,
        id: this.requestDetail.dosageId
      }
      let result = await requestApi.deleteRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      }
      else if (this.requestType === 2){
        let data = {
        requestType: this.requestType,
        id: this.requestDetail.rhId
      }
      let result = await requestApi.deleteRequest(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      }
      this.$emit('get-notebooklist', this.requestType)
      // window.location.reload()
    }
  },


}
</script>

<style scoped>
.writeBtn{
  position: fixed;
  right: 60px;
  bottom: 50px;
}
.request-container{
  overflow-y:scroll; 
  height:80vh; 
}
.request-container::-webkit-scrollbar {
  width: 10px;
  background-color: black;
}
.request-container::-webkit-scrollbar-thumb {
  background-color: #2f3542;
}
.request-container::-webkit-scrollbar-track {
  background-color: grey;
}

.text-deco{
  font-size: 18px;
}
/* 
.letter-back{
  background-image: url("../../assets/letter.png");
  background-size: 100% 100%;
} */

.letter-back{
  background-color:#fff1c8;
  background-size: 100% 100%;
}
</style>