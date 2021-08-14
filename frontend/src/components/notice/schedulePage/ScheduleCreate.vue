<template>
  <div>
    <!-- 디테일 -->

    <v-sheet rounded="lg" v-if="createMode">
      <div style="width:100%; height:15px; background-color:#a8b1cf" class="mt-3"></div>
      <div class="container content-container" style="height:84.8vh">

        <div align="right" class="mt-2"> 
        </div>
        <div align="center" class="mb-5">

          <div class="col-lg-11">
            <table class="table table-borderless mb-0">
              <tbody>
                <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">일정</strong>
                      <table class=" table mt-5 mb-5">

                        <tr class="d-flex">
                          <td class="col-12"><input type="text"  v-model="title" class="formInput" placeholder="제목을 입력하세요."></td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12">
                            <textarea rows="15" v-model="content" class="formInputText" placeholder="내용을 입력하세요."></textarea>
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12 mb-0">
                            <!-- 파일 첨부-->
                            <v-file-input
                              id="scheduleFile"
                              v-model="files"
                              filled
                              prepend-icon="mdi-camera"
                              dense
                              accept="image/*"
                              style="width: 100%"
                            ></v-file-input>                            
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
              </tbody>
            </table>
            
          </div>
  
          <button @click="createNewSchedule" class="mr-2 update-return-btn">작성</button>

          <button @click="offCreateForm" class="ml-2 update-return-btn">취소</button>


        </div>
      </div>

    </v-sheet>

  </div>
</template>

<script>
import scheduleApi from "@/api/schedule.js";
import awss3 from "@/utils/awss3.js";

export default {
  name: "ScheduleCreate",

  props: {
    identity: {
      identity: Number,
    },
    scheduleType: {
      requestType: Number,
    },
    id: {
      id: Number,
    },
  },

  data() {
    return {
      // 상세 내용을 저장할 변수
      scheduleDetail: null,
      createMode: true,
      updateMode: false,
      title: "",
      content: "",
      files: null,
    };
  },

  methods: {
    offCreateForm() {
      this.$emit("cancelCreateSchedule");
    },

    // 일정 생성
    async createNewSchedule() {
      if (this.title == "" || this.content == "") {
        
        return;
      }

      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");

      let scheduleImgUrl = "";
      if (document.getElementById("scheduleFile").files.length != 0) {
        scheduleImgUrl = await awss3.uploadPhoto("schedule", "scheduleFile");
        console.log("파일있음");
      }

      let data = {
        scheduleImgUrl: scheduleImgUrl[0],
        noticeType: 2,
        userId: this.$store.state.user.userId,
        classCode: this.$store.state.user.classCode,
        title: this.title,
        content: this.content,
      };

      let result = await scheduleApi.createSchedule(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });

      console.log("result");
      console.log(result);

      this.$emit("createSchedule");
      // this.getSchedule()
    },
  },
};
</script>

<style scoped>
/* 스크롤 */
.content-container{
  overflow-y:scroll; 
  height:80vh; 
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233,234,239, 0.5);
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233,234,239, 0.5);
  border-radius: 1px;
}
.formInput {
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 36px;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.formInputText{
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 100%;
  width: 100%;
  padding: 15px 15px 15px 15px;
  margin: 3px 3px 3px 3px;
}
.formInputText:hover{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
.formInputText:focus{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
  outline: none;
}
.formInput:hover{
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
.update-return-btn {
  background-color: rgba(168, 177, 207, 1);
  border-radius: 70px;
  height: 36px;
  width: 70px;
  margin: 0px 3px 3px 3px;
  text-align: center;
  color: rgba(256, 256, 256);
  letter-spacing: -1px;
}
.update-delete-btn {
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}
</style>