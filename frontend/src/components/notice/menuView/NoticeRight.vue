<template>
  <div style="overflow-y:scroll;" class="col">
    공지
    <!-- <button  @click="createNewNotice">연필</button>
    | 
    <button @click="updateNotice">연필</button>
    |
    <button @click="updateNotice">글 수정</button>
      | 
    <button @click="deleteNotice">글 삭제</button>
    |
    <button @click="offCreateForm">글 작성 취소</button> -->

    <notice-detail v-if="detailMode"/>
    <notice-create v-if="createMode"/>
    <notice-update  v-if="updateMode"/>
      

  </div>

</template>

<script>
// import noticeApi from '@/api/notice.js';
import NoticeDetail from './NoticeDDD.vue';
import NoticeCreate from './NoticeCreate.vue';
import NoticeUpdate from './NoticeUpdate.vue';
export default {
  name: "NoticeDetail",
components:{
    NoticeDetail,
    NoticeCreate,
    NoticeUpdate,
  },  
  props:{
    identity: {
      identity: Number,
    },
    noticeType: {
      requestType: Number,
    },
    // id: {
    //   id: Number,
    // },
  },


  data () {
    return {
      // 상세 내용을 저장할 변수
      noticeDetail: null,
     
      detailMode: true,
      createMode: false,
      updateMode: false,
    }
  },
  watch: {
    // 'id' : function() {
    //   if (this.id !== 0) {
    //     this.getNoticeDetail()
    //   }
    // }
  },


  methods: {

    offCreateForm() {
      this.creating = 0
    },

    // async getNoticeDetail() {
    //   let accessToken = sessionStorage.getItem('access-token')
    //   let refreshToken = sessionStorage.getItem('refresh-token')
    //   let data = {
    //     noticeType: this.noticeType,
    //     id: this.id
    //   }
    //   // 선생인지, 학부모인지에 따라 다르게 받음.
    //   let result = await noticeApi.getNoticeDetail(data, {
    //     "access-token": accessToken,
    //     "refresh-token": refreshToken,
    //   });
    //     // 어떻게 날라오는지 확인후 데이터 집어넣기4
    //   if (this.noticeType === 1) {
    //     this.noticeDetail = result.dosage
    //   }
    //   else if (this.noticeType === 2){
    //     this.noticeDetail = result.returnhome
    //   }
    //   console.log(this.noticeDetail)
    //   this.creating = 0
    //   this.updating = 0
    // },

    
    // async updateNotice() {
    //   this.createMode=false;
    //   this.updateMode=true;

      // if (this.creating === 1) {
      //   this.creating = 0
      // }

      // if (this.updating === 0 ) {
      //     if (this.noticeType === 1) {
      //     this.symptom = this.noticeDetail.symptom
      //     this.medicineType = this.noticeDetail.medicineType
      //     this.dosageVol = this.noticeDetail.dosageVol
      //     this.dosageCnt = this.noticeDetail.dosageCnt
      //     this.dosageTime = this.noticeDetail.dosageTime
      //     this.storage = this.noticeDetail.storage
      //     this.specialNote = this.noticeDetail.specialNote
      //   }
      //   else if (this.noticeType === 2) {
      //     this.rhDate = this.noticeDetail.rhDate
      //     this.rhTime = this.noticeDetail.rhTime
      //     this.guardian = this.noticeDetail.guardian
      //     this.guardianTel = this.noticeDetail.guardianTel
      //     this.emergency = this.noticeDetail.emergency
      //     this.emergencyTel = this.noticeDetail.emergencyTel
      //   }
      //   this.updating = 1
      // }

      // // 여기서부터 다시 작성
      // else if (this.updating === 1) {
      //   let accessToken = sessionStorage.getItem('access-token')
      //   let refreshToken = sessionStorage.getItem('refresh-token')
      //   if (this.noticeType === 1) {
      //     let data = {
      //     noticeType: this.noticeType,
      //     userId: this.$store.state.user.userId,
      //     id: this.id,
      //     symptom: this.symptom,
      //     medicineType: this.medicineType,
      //     dosageVol: this.dosageVol,
      //     dosageCnt: this.dosageCnt,
      //     dosageTime: this.dosageTime,
      //     storage: this.storage,
      //     specialNote: this.specialNote,
      //     }
      //     let result = await noticeApi.updateNotice(data, {
      //       "access-token": accessToken,
      //       "refresh-token": refreshToken,
      //     });
      //     console.log(result)
      //   }
      //   else if (this.noticeType === 2) {
      //     let data = {
      //     noticeType: this.noticeType,
      //     userId: this.$store.state.user.userId,
      //     id: this.id,
      //     rhDate: this.rhDate,
      //     rhTime: this.rhTime,
      //     guardian : this.guardian,
      //     guardianTel: this.guardianTel,
      //     emergency: this.emergency,
      //     emergencyTel: this.emergencyTel,
      //     }
      //     let result = await noticeApi.updateNotice(data, {
      //       "access-token": accessToken,
      //       "refresh-token": refreshToken,
      //     });
      //     console.log(result)
      //   }
      //   this.updating = 0
      //   this.$emit('get-notebooklist', this.noticeType)
      //   // this.getNotice()
      // }
    // },

  //   async deleteNotice() {
  //     let accessToken = sessionStorage.getItem('access-token')
  //     let refreshToken = sessionStorage.getItem('refresh-token')
  //     if (this.noticeType === 1){
  //       let data = {
  //       noticeType: this.noticeType,
  //       id: this.noticeDetail.dosageId
  //     }
  //     let result = await noticeApi.deleteNotice(data, {
  //       "access-token": accessToken,
  //       "refresh-token": refreshToken,
  //     });
  //     console.log(result)
  //     }
  //     else if (this.noticeType === 2){
  //       let data = {
  //       noticeType: this.noticeType,
  //       id: this.noticeDetail.rhId
  //     }
  //     let result = await noticeApi.deleteNotice(data, {
  //       "access-token": accessToken,
  //       "refresh-token": refreshToken,
  //     });
  //     console.log(result)
  //     }
  //     this.$emit('get-notebooklist', this.noticeType)
  //     // window.location.reload()
  //   }
  },


}
</script>

<style scoped>

</style>