<template>
  <div style="font-family: 'NanumSquareRound'; width:82vw; background-color: rgba(240, 241, 247); ">
    <!-- vuetify menus 참고 -->
      <div class="mx-auto d-flex" style="width:57vw; margin-top:3%; margin-bottom:10px">
        <div class="d-flex align-items-end" style="width:50%;">
          <div id="circle" class="d-inline-block me-1" style="margin-bottom:0.4rem"></div>
          <p class="d-inline my-0 me-2">결석</p>
          <div id="circle2" class="d-inline-block me-1" style="margin-bottom:0.4rem"></div>
          <p class="d-inline m-0">출석</p>
        </div>
        <template>
          <div class="text-right d-flex justify-content-end align-items-end" style="width:50%; display: inline; ">
            <p style="font-weight: 800; display:inline; margin-right:1rem; margin-bottom:0">{{date.substr(0,4)}}년 {{date[5] + date[6]}}월 {{date[8]+date[9]}}일</p>
            
            <v-menu
              v-model="menu"
              :close-on-content-click="false"
              :nudge-width="100"
              offset-y
              nudge-top
              
            >
              <template v-slot:activator="{ on, attrs }">
                <button v-bind="attrs" v-on="on">
                  <img src="@/assets/flaticon/calendar.png" style="width:2rem" alt="">
                </button>
              </template>
            
              
              





              <v-card style="overflow:hidden">
                <template>
                  <v-row>
                    <v-col
                      class="my-2 px-1"
                      cols="12"
                      sm="6"
                    >
                      <v-date-picker
                        v-model="date"
                        no-title
                        @contextmenu:year="contextMenu"
                        @mouseenter:month="mouseEnter"
                        @mouseleave:month="mouseLeave"
                      ></v-date-picker>
                    </v-col>
                  </v-row>
                </template>
              </v-card>
            </v-menu>
            
          </div>
        </template>
      </div>

    <div class="container d-flex flex-wrap justify-content-center content-container" style="height:75vh; width:57vw; margin:0 auto; border-radius: 8px; background-color:white;">
      <div class="row row-cols-5">
        <div v-for="(kid, index) in kids" :key="kid.userId" cols="3">
          <div class="thumbnailWrap d-flex flex-column align-items-center">
            <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' + kid.profileImg" 
            style="width:91%; height:18vh ;margin-top:1rem; margin-bottom:0.4rem; " 
            :class="{ attend: (kid.attend===1), notattend: (kid.attend===0) }"
            class="thumbnailImg"
            @click="setKid(index)">
            <p class="m-0 mt-1" style="text-align:center; width:91%; font-weight: 600;">{{kid.kidName}}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import attendanceApi from '@/api/attendance.js';

export default {
  name:"TeacherAttendance",
  data () {
    return {
      // 출석 관련. kids 목록을 전부 다 받으면 될 듯
      // 1. kid의 이미지 url (profileImg)
      // 2. kid의 이름. (kidName)
      // 3. kid의 출석 상태 (attend)
      // 4. 날짜? <- 필요할까....
      userId: '',
      kids: [],
      // 클릭된 아이의 아이디
      kidIndex: 0,
      kidId: 0,

      // 달력관련
      date: new Date().toISOString().substr(0, 10),
      done: [false, false, false],
      mouseMonth: null,

      fav: true,
      menu: false,
      message: false,
      hints: true,
    }
  },
  watch: {
    'date': function() {
      this.menu = false
      this.getChildren()
    }
  },
  methods: {

    // 해당 날짜에 아이들의 출석 상태를 불러와야함. 아이들의 목록, 출석 상황.
    // 아이 사진 클릭 시, 그 아이의 출석 상태가 변경되어야함. 그리고 그 즉시 화면에서 테두리 색깔이 변해야함.
    setKid(index) {
      this.kidId = this.kids[index].userId
      this.kidIndex = index
      this.updateAttendance()
    },
    
    // 아이들 목록 불러오기 
    async getChildren () {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        userId: this.userId,
        date: this.date,
      }
      let result = await attendanceApi.getChildren(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      this.kids = result
    },


    // 아이 출석 상태 변경
    async updateAttendance () {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        userId: this.kidId,
        date: this.date,
      }
      let result = await attendanceApi.updateAttendence(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.kids[this.kidIndex].attend = result
      //이게 맞나, 그냥 result를 변수에 담아서 써야할까.
      // this.getChildren()
    },







    // 달력 관련
    contextMenu (year, event) {
      this.$set(this.done, 2, true)

      event.preventDefault()

      alert(`You have activated context menu for year ${year}`)
    },
    
    mouseEnter (month) {
      this.$set(this.done, 1, true)
      this.mouseMonth = month
    },
    mouseLeave () {
      this.mouseMonth = null
    },
  },

  created() {
    this.userId = this.$store.state.user.userId
    this.getChildren()
  }
}
</script>

<style scoped>

#circle {
  background-color:#ffc1c2;
  width:10px;
  height:10px;
  border-radius:75px;
}

#circle2 {
  background-color:#a1c8ff;
  width:10px;
  height:10px;
  border-radius:75px;
}

.attend {
  border-style: solid;
  border-color:#a1c8ff;
  border-width: 8px;
  margin: auto 0;
}

.notattend {
  border-style: solid;
  border-color: #ffc1c2;
  border-width: 8px;
  margin: auto 0;
}
.content-container{
  overflow-y:scroll; 
  height:80vh; 
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233,234,239, 0.5);
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 5px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233,234,239, 0.5);
  border-radius: 5px;
}


/* 썸네일 */
.thumbnailImg {
  width:100%;
  height:15vh;
  margin-top:1rem;
  margin-bottom:0.4rem;
  transition-duration: 1s;
  object-fit: cover;
  cursor: pointer;
}
.thumbnailImg:hover {
  transition: 0.4s;
  transform: scale(1.06, 1.06);
  transition-duration: 0.5s;
}

.thumbnailWrap {
  overflow: hidden;
}
</style>