<template>
  <div style="background-color: rgba(240, 241, 247); width:100%; font-family: 'NanumSquareRound';">
    <div class="container d-flex justify-content-center mt-10" style="height:90%;">
      
      <template style="border-radius: 10px; background-color:white;">
        <v-row class="fill-height">
          <v-col>
            <v-sheet height="70" class="d-flex align-items-end">
              <v-toolbar flat color="white">

                <div class="d-flex align-items-end" style="width:50%; height:100%">
                  <div id="circle" class="d-inline-block mx-1 mb-1"></div>
                  <p class="d-inline m-0">결석</p>
                  <div id="circle2" class="d-inline-block mx-1 mb-1"></div>
                  <p class="d-inline m-0">출석</p>
                </div>

                <div style="display:flex; justify-content:left; width:75%">
                  <v-btn fab text small color="grey darken-2" @click="prev">
                    <v-icon small>mdi-chevron-left</v-icon>
                  </v-btn>
                  <v-toolbar-title v-if="$refs.calendar" style="display:flex; align-items:center;">
                    {{this.$refs.calendar.title}}
                  </v-toolbar-title>
                  <v-btn fab text small color="grey darken-2" @click="next">
                    <v-icon small>mdi-chevron-right</v-icon>
                  </v-btn>

                </div>
                <!-- 월 변경 -->
              
              </v-toolbar>
            </v-sheet>

            <!-- 켈린더 -->
            <v-sheet height="67vh">
              <v-calendar
                ref="calendar"
                v-model="focus"
                color="#aab2cc"
                :events="events"
                :type="type"
                @change="updateRange"
                event-text-color="dark"
                class="text-center"
              >
              </v-calendar>
              
            </v-sheet>
          </v-col>
        </v-row>
      </template>
      

      <!-- 출석률 ㅎㅎ -->
      <div class="d-flex flex-column justify-content-end" style="width:20%; margin-left:40px; background-color:rgba(240, 241, 247);">
        <h5 style="text-align:center; margin-bottom:1rem; color:rgb(40,40,40);">{{thisYear}}년 {{thisMonth}}월</h5>
        <div style="width:100%; display:flex; justify-content:center;">
          <v-progress-circular
            :rotate="-90"
            :size="150"
            :width="25"
            :value="attendPercent"
            color="#667ABC"
            style="font-size:0.85rem;"
          >
          출석
          <br>
            {{ attendPercent }} %
          </v-progress-circular>
        </div>
        <hr>
        <p style="text-align:right; font-size:0.95rem; margin-right: 0.5rem; color:rgb(40,40,40)">{{thisMonth}}월 수업 일수 : {{totalDay}} 일</p>
        <p style="text-align:right; font-size:0.95rem; margin-right: 0.5rem; color:rgb(40,40,40)">출석 일수 : {{attendCnt}} 일</p>
        <p style="text-align:right; font-size:0.95rem; margin-right: 0.5rem; color:rgb(40,40,40)">결석 일수 : {{notattendCnt}} 일</p>

      </div>
    </div>
    
  </div>
</template>

<script>
import attendanceApi from '@/api/attendance.js';
export default {
  name: "ParentAttendance",
  data () {
    return {
      thisYear:'',
      thisMonth: '',

      userId: '',
      month: '',
      // 달력 관련
      attendance: [],
      focus: '',
      type: 'month',
      events: [],
      colors: ['#FFE495', '#f0f1f8'],
      names: ['출석', '결석'],

      // 출석률 관련
      attendPercent: 0,
      attendCnt: 0,
      notattendCnt: 0,
      totalDay: 0,
    }
  },
  // 달력 관련
  mounted () {
    this.$refs.calendar
  },

  methods: {

    // 달력 관련
    viewDay ({ date }) {
      this.focus = date
      this.type = 'day'
    },
    prev () {
      this.$refs.calendar.prev()
      // console.log(this.$refs.calendar)
    },
    next () {
      this.$refs.calendar.next()
    },
    // updateRange ({ start, end }) {
    //   console.log(start.date)
    //   console.log(end.date)
    //   const min = new Date(`${start.date}T00:00:00`)
    //   const max = new Date(`${end.date}T23:59:59`)
    //   console.log(min)
    //   console.log(max)

    //   console.log('달바꿈')
    //   // const events = []
  




    //   // this.events = events
    // },



    async updateRange ({ start, end }) {

        // 날짜
        this.totalDay = 0
        const events = []
        this.userId = this.$store.state.user.userId
        this.month = start.date
        let time = new Date(this.month)
        // 오른쪽 출석률 처리 관려
        this.thisYear = time.getFullYear()
        this.thisMonth = time.getMonth() + 1


        await this.getChild()
        // const min = new Date(`${start.date}T00:00:00`)
        // const max = new Date(`${end.date}T23:59:59`)
        // console.log(start.date)
        // console.log(min)
        console.log(end)
        // const days = (max.getTime() - min.getTime()) / 86400000
        // const eventCount = this.rnd(days, days + 20)
        // 날짜만큼 진행. i = start.date.substr(9,11), i < end.date.substr(9,11)
        // 혹은 리턴받은 배열 길이 만큼 진행.
        let attend = 0
        for (let i = 0; i < this.attendance.length; i++) {
          // const allDay = this.rnd(0, 3) === 0

          // const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
          // const second = new Date(first.getTime() + secondTimestamp)
          // console.log(secondTimestamp)
          // console.log(second)

          
          // let state = this.kid[i] ? 0 : 1
          // let end = new Date(this.attendance[i].date)
          // let end1 = end.getTime() + 9999999
          // let start = new Date(this.attendance[i].date)
          // let start1 = start.getTime() + 10000000
          // console.log(end1)
          // console.log(end1 + 1)
          // 백엔드 처리 가능한 지에 따라 다르다...
          this.totalDay += 1
          if (this.attendance[i].attend === 1) {
            attend += 1
          }
          events.push({
            name: this.attendance[i].attend ? this.names[0] : this.names[1],
            start: this.attendance[i].date,
            end: this.attendance[i].date,
            color: this.attendance[i].attend ? this.colors[0] : this.colors[1],
          })


        }
        this.attendPercent = Math.round(attend / this.attendance.length * 100)
        if (this.attendance.length === 0) {
          this.attendPercent = 0
        }
        this.attendCnt = attend
        this.notattendCnt = this.totalDay - attend
        this.events = events
        


      },





    // 아이 출석 상태 불러오기.
    // 근데, 그 달의 출석 상태를 다 불러오려면, 달만 보내줘야할 듯?????
    // 
    async getChild () {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        userId: this.userId,
        // yyyy-mm 꼴로 보내주기.
        date: this.month,
      }
      let result = await attendanceApi.getChild(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      // console.log(result)
      this.attendance = result
    },
  },
}
</script>

<style scoped>
.pl-1 {
  
  width: 100px;
  height: 100px;
}

#circle {
    background-color: rgba(240, 241, 248);
    width:20px;
    height:20px;
    border-radius:75px;
  }

  #circle2 {
    background-color:#FFE495;
    width:20px;
    height:20px;
    border-radius:75px;
  }
</style>