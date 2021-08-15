<template>
  <div
    style="
      background-color: rgba(240, 241, 247);
      font-family: 'NanumSquareRound';
    "
  >
    <div
      class="container d-flex justify-content-center mt-10"
      style="height: 90%"
    >
      <template style="border-radius: 10px; background-color: white">
        <v-row class="fill-height">
          <v-col>
            <v-sheet height="70" class="d-flex align-items-end">
              <v-toolbar flat color="white">
                <div
                  class="d-flex align-items-end"
                  style="width: 50%; height: 100%"
                >
                  <div id="circle" class="d-inline-block mx-1 mb-1"></div>
                  <p class="d-inline m-0">결석</p>
                  <div id="circle2" class="d-inline-block mx-1 mb-1"></div>
                  <p class="d-inline m-0">출석</p>
                </div>

                <div style="display: flex; justify-content: left; width: 75%">
                  <v-btn fab text small color="grey darken-2" @click="prev">
                    <v-icon small>mdi-chevron-left</v-icon>
                  </v-btn>
                  <v-toolbar-title
                    v-if="$refs.calendar"
                    style="display: flex; align-items: center"
                  >
                    {{ this.$refs.calendar.title }}
                  </v-toolbar-title>
                  <v-btn fab text small color="grey darken-2" @click="next">
                    <v-icon small>mdi-chevron-right</v-icon>
                  </v-btn>
                </div>
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

      <!-- 출석률 -->
      <div
        class="d-flex flex-column justify-content-end"
        style="
          width: 20%;
          margin-left: 40px;
          background-color: rgba(240, 241, 247);
        "
      >
        <h5
          style="
            text-align: center;
            margin-bottom: 1rem;
            color: rgb(40, 40, 40);
          "
        >
          {{ thisYear }}년 {{ thisMonth }}월
        </h5>
        <div style="width: 100%; display: flex; justify-content: center">
          <v-progress-circular
            :rotate="-90"
            :size="150"
            :width="25"
            :value="attendPercent"
            color="#667ABC"
            style="font-size: 0.85rem"
          >
            출석
            <br />
            {{ attendPercent }} %
          </v-progress-circular>
        </div>
        <hr />
        <p
          style="
            text-align: right;
            font-size: 0.95rem;
            margin-right: 0.5rem;
            color: rgb(40, 40, 40);
          "
        >
          {{ thisMonth }}월 수업 일수 : {{ totalDay }} 일
        </p>
        <p
          style="
            text-align: right;
            font-size: 0.95rem;
            margin-right: 0.5rem;
            color: rgb(40, 40, 40);
          "
        >
          출석 일수 : {{ attendCnt }} 일
        </p>
        <p
          style="
            text-align: right;
            font-size: 0.95rem;
            margin-right: 0.5rem;
            color: rgb(40, 40, 40);
          "
        >
          결석 일수 : {{ notattendCnt }} 일
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import attendanceApi from "@/api/attendance.js";
export default {
  name: "ParentAttendance",
  data() {
    return {
      thisYear: "",
      thisMonth: "",
      userId: "",
      month: "",

      attendance: [],
      focus: "",
      type: "month",
      events: [],
      colors: ["#FFE495", "#f0f1f8"],
      names: ["출석", "결석"],

      attendPercent: 0,
      attendCnt: 0,
      notattendCnt: 0,
      totalDay: 0,
    };
  },
  mounted() {
    this.$refs.calendar;
  },

  methods: {
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },

    async updateRange({ start, end }) {
      console.log(end)

      this.totalDay = 0;
      const events = [];
      this.userId = this.$store.state.user.userId;
      this.month = start.date;
      let time = new Date(this.month);

      this.thisYear = time.getFullYear();
      this.thisMonth = time.getMonth() + 1;
      await this.getChild();

      let attend = 0;
      for (let i = 0; i < this.attendance.length; i++) {
        if (this.attendance[i].attend === 9) continue;
        this.totalDay += 1;
        if (this.attendance[i].attend === 1) {
          attend += 1;
        }
        events.push({
          name: this.attendance[i].attend ? this.names[0] : this.names[1],
          start: this.attendance[i].date,
          end: this.attendance[i].date,
          color: this.attendance[i].attend ? this.colors[0] : this.colors[1],
        });
      }
      this.attendPercent = Math.round((attend / this.attendance.length) * 100);
      if (this.attendance.length === 0) {
        this.attendPercent = 0;
      }
      this.attendCnt = attend;
      this.notattendCnt = this.totalDay - attend;
      this.events = events;
    },

    async getChild() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        userId: this.userId,
        date: this.month,
      };
      let result = await attendanceApi.getChild(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.attendance = result;
    },
  },
};
</script>

<style scoped>
#circle {
  background-color: rgba(240, 241, 248);
  width: 20px;
  height: 20px;
  border-radius: 75px;
}
#circle2 {
  background-color: #ffe495;
  width: 20px;
  height: 20px;
  border-radius: 75px;
}
</style>