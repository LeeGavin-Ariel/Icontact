<template>
  <div>
    {{date}}
    {{kids}}
    <!-- vuetify menus 참고 -->
    <template>
      <div class="text-center">
        <v-menu
          v-model="menu"
          :close-on-content-click="false"
          :nudge-width="100"
          offset-y
          nudge-top
          
        >
          <template v-slot:activator="{ on, attrs }">
            <!-- <button style="float: right; margin-top:1.5rem;">
              <img src="@/assets/flaticon/calendar.png" style="height: 2.5rem; width: 2.5rem; margin:0.5rem;" alt="">
            </button> -->
            <v-btn
              color="indigo"
              dark
              v-bind="attrs"
              v-on="on"
            >
              날짜변경
            </v-btn>
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

            <!-- <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn text @click="menu = false">Cancel</v-btn>
              <v-btn color="primary" text @click="menu = false">Save</v-btn>
            </v-card-actions> -->
          </v-card>
        </v-menu>
      </div>
    </template>


    <div style="height:4vh; width:96%;">
      <div>
        <div id="circle"></div>
        <p class="d-inline">결석</p>
        <div id="circle2"></div>
        <p class="d-inline">출석</p>
      </div>
      <!-- <button style="float: right; margin-top:1.5rem;"><img src="@/assets/flaticon/calendar.png" style="height: 2.5rem; width: 2.5rem; margin:0.5rem;" alt=""></button> -->
    </div>
    
    
    <div class="container d-flex justify-content-center" style="height:80vh; margin:2.5rem; overflow-y:scroll; border: solid rgba(169,177,204, 0.7); border-radius: 8px;">
      <v-row>

        <!-- v-for="kid in kids" -->
        <!-- :key="kid.userId" -->
        <v-col
          v-for="(kid,index) in kids"
          :key="kid.userId"
          cols="2"
        >
        <!--  -->
          <v-card
            class="mx-auto"
            max-width="200"
            max-height="300"
            outlined
            
          >
          <!-- 여기 테두리 주기, 아이 사진으로 넣기 반복문에 들어오는 아이의 키값을 아이의 아이디 값으로 설정. -->
            <v-img
              src="@/assets/1.jpeg"
              height="200px"
              @click="setKid(index)"
              :class="{ attend: (kid.attend===1), notattend: (kid.attend===0) }"
            ></v-img>
            <!-- 여기 아이 이름 넣어주기. -->
          </v-card>
          <p style="text-align:center;">{{kid.kidName}}</p>
        </v-col>
      </v-row>
    </div>
    


    



    <!-- 달력 -->
    <!-- <template>
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
            @dblclick:date="dblClick"
            @mouseenter:month="mouseEnter"
            @mouseleave:month="mouseLeave"
          ></v-date-picker>
        </v-col>
      </v-row>
    </template> -->


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

<style>
  #circle {
    background-color:#8ca3a3;
    width:20px;
    height:20px;
    border-radius:75px;
  }

  #circle2 {
    background-color:#eeea0a;
    width:20px;
    height:20px;
    border-radius:75px;
  }

  .attend {
    border-style: solid;
    border-color: #eeea0a;
    border-width: 12px;
    margin: auto 0;
  }

  .notattend {
    border-style: solid;
    border-color: #8ca3a3;
    border-width: 12px;
    margin: auto 0;
  }

</style>