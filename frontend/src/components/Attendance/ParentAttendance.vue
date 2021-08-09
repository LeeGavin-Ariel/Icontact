<template>
  <div>
    <div class="container d-flex justify-content-center" style="height:70vh; margin:2.5rem;  border: solid rgba(169,177,204, 0.7); border-radius: 8px;">
      
      
      <template>
        <v-row class="fill-height">
          <v-col>
            <v-sheet height="64">
              <v-toolbar flat color="white">

                <!-- 월 변경 -->
                <v-btn fab text small color="grey darken-2" @click="prev">
                  <v-icon small>mdi-chevron-left</v-icon>
                </v-btn>
                <v-toolbar-title v-if="$refs.calendar">
                  {{this.$refs.calendar.title}}
                </v-toolbar-title>
                <v-btn fab text small color="grey darken-2" @click="next">
                  <v-icon small>mdi-chevron-right</v-icon>
                </v-btn>
              
              </v-toolbar>
            </v-sheet>

            <!-- 켈린더 -->
            <v-sheet height="400">
              <v-calendar
                ref="calendar"
                v-model="focus"
                color="primary"
                :events="events"
                :type="type"
                @change="updateRange"
              >
              </v-calendar>
              
            </v-sheet>
          </v-col>
        </v-row>
      </template>
      
    </div>
    
  </div>
</template>

<script>
import attendanceApi from '@/api/attendance.js';
export default {
  name: "ParentAttendance",
  data () {
    return {
      userId: '',
      month: '',
      // 달력 관련
      attendance: [],
      focus: '',
      type: 'month',
      events: [],
      colors: ['#A8B1CF', 'red'],
      names: ['출석', '결석'],
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

        const events = []
        this.userId = this.$store.state.user.userId
        this.month = start.date
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
        for (let i = 0; i < this.attendance.length; i++) {
          // const allDay = this.rnd(0, 3) === 0

          // const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
          // const second = new Date(first.getTime() + secondTimestamp)
          // console.log(secondTimestamp)
          // console.log(second)

          
          // let state = this.kid[i] ? 0 : 1

          // 백엔드 처리 가능한 지에 따라 다르다...
          events.push({
            name: this.attendance[i].attend ? this.names[0] : this.names[1],
            start: this.attendance[i].date,
            end: this.attendance[i].date,
            color: this.attendance[i].attend ? this.colors[0] : this.colors[1],
          })


        }

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

<style>
.pl-1 {
  
  width: 100px;
  height: 100px;
}
</style>