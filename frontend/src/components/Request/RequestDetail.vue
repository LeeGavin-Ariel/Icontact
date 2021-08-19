<template>
  <!-- 디테일 -->
  <div class="col">
    <!-- 새 글 작성시 -->
    <div class="ml-5 mr-5 mt-5">
      <button 
        class="writeBtn"
        v-if="identity === 1 && updating === 0 && creating === 0"
        @click="createNewRequest"
      >
        <img src="@/assets/flaticon/write.png" style="width: 3.8rem" />
      </button>

      <div v-if="id === 0 && !(creating || updating)" style="font-size: 1.2rem; text-align:center;">
          등록된 요청이 없습니다.
      </div>

      <!-- 투약 요청 상세 -->
      <v-sheet
        rounded="lg"
        v-if="
          requestType === 1 &&
          requestDetail &&
          !creating &&
          !updating &&
          id !== 0
        "
      >
        <div
          style="width: 100%; height: 15px; background-color: #a8b1cf"
          class="mt-3"
        ></div>
        <div class="container content-container" style="height: 84.8vh">
          <div align="right" class="mt-2">
            <button
              v-if="identity === 1 && updating === 0 && creating === 0 && id"
              @click="updateRequest"
              class="mr-2 update-delete-btn"
            >
              수정
            </button>
            <button
              v-if="identity === 1 && updating === 0 && creating === 0 && id"
              @click="deleteRequest"
              class="ml-2 mr-2 update-delete-btn"
            >
              삭제
            </button>
          </div>
          <div align="center" class="mb-5">
            <div class="col-lg-11">
              <table class="table">
                <tbody>
                  <tr class="d-flex">
                    <td class="col-12">
                      <div style="font-size: 35px">
                        <strong>{{ requestDetail.kidName }}</strong>
                      </div>
                      <div class="mt-3 mb-2">
                        금일 자녀의 투약을 선생님께 의뢰합니다.
                      </div>
                    </td>
                  </tr>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">투약 요청 내용</strong>
                      <table
                        class="table mt-5 mb-5"
                        style="
                          border-radius: 10px;
                          background-color: rgba(156, 156, 156, 0.1);
                        "
                      >
                        <tr class="d-flex">
                          <th class="col-2">증상</th>
                          <td class="col-10">{{ requestDetail.symptom }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">약 종류</th>
                          <td class="col-10">
                            {{ requestDetail.medicineType }}
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">투약 용량</th>
                          <td class="col-10">{{ requestDetail.dosageVol }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">투약 횟수</th>
                          <td class="col-10">
                            {{ requestDetail.dosageCnt }} 회
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">투약 시간</th>
                          <td class="col-10">{{ requestDetail.dosageTime }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">보관 방법</th>
                          <td class="col-10">{{ requestDetail.storage }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">특이사항</th>
                          <td class="col-10">
                            {{ requestDetail.specialNote }}
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </tbody>
              </table>
              <div>투약으로 인한 책임은 의뢰자가 집니다.</div>
            </div>
          </div>
        </div>
      </v-sheet>

      <!-- 투약요청 작성, 수정 -->
      <v-sheet rounded="lg" v-if="requestType === 1 && (creating || updating)">
        <div
          style="width: 100%; height: 15px; background-color: #a8b1cf"
          class="mt-3"
        ></div>
        <div class="container content-container" style="height: 84.8vh">
          <div align="right" class="mt-2"></div>
          <div align="center" class="mb-5">
            <div class="col-lg-11">
              <table class="table">
                <tbody>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">투약 요청 내용</strong>
                      <table
                        class="table mt-5 mb-5"
                        style="border-radius: 10px"
                      >
                        <tr class="d-flex">
                          <th class="col-2 mt-2">증상</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="symptom"
                              placeholder="예) 기침, 몸살"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">약 종류</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="medicineType"
                              placeholder="예) 물약, 가루약"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">
                            투약 용량<!-- <span style="font-size: 12px">1회</span> -->
                          </th>
                          <td class="col-10">
                            <input
                              type="number"
                              class="formInput"
                              v-model="dosageVol"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">
                            투약 횟수<!-- <span style="font-size: 12px">1일</span> -->
                          </th>
                          <td class="col-10">
                            <input
                              type="number"
                              class="formInput"
                              v-model="dosageCnt"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">투약 시간</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="dosageTime"
                              placeholder="예) 오전/오후 00시 00분"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">보관 방법</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="storage"
                              placeholder="예) 실온/냉장"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">특이사항</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="specialNote"
                              placeholder="예) 가루약 넘기기를 힘들어해요"
                            />
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- 학부모 동의 체크 박스 -->
            <div class="d-flex justify-center">
              <v-checkbox
                style="margin: 0"
                v-model="checked"
                label="금일 자녀의 투약을 선생님께 의뢰합니다."
                color="mainColor1"
                hide-details
              >
              </v-checkbox>
            </div>

            <!-- 새로운 글 작성 관련 버튼 -->
            <button
              :disabled="!checked"
              v-if="identity === 1 && updating === 0 && creating === 1"
              @click="createNewRequest"
              class="mr-2 update-return-btn"
            >
              저장
            </button>
            <button
              v-if="identity === 1 && creating && id"
              @click="offCreateForm"
              class="ml-2 update-return-btn"
            >
              취소
            </button>

            <!-- 수정 관련 버튼-->
            <button
              :disabled="!checked"
              v-if="identity === 1 && updating === 1 && creating === 0 && id"
              @click="updateRequest"
              class="mr-2 update-return-btn"
            >
              수정
            </button>
            <button
              v-if="identity === 1 && updating && id"
              @click="offUpdateForm"
              class="ml-2 update-return-btn"
            >
              취소
            </button>
          </div>
        </div>
      </v-sheet>

      <!-- 귀가 동의 상세 -->
      <v-sheet
        rounded="lg"
        v-if="
          requestType === 2 &&
          requestDetail &&
          !creating &&
          !updating &&
          id !== 0
        "
      >
        <div
          style="width: 100%; height: 15px; background-color: #a8b1cf"
          class="mt-3"
        ></div>
        <div class="container content-container" style="height: 84.8vh">
          <div align="right" class="mt-2">
            <button
              v-if="identity === 1 && updating === 0 && creating === 0 && id"
              @click="updateRequest"
              class="mr-2 update-delete-btn"
            >
              수정
            </button>
            <button
              v-if="identity === 1 && updating === 0 && creating === 0 && id"
              @click="deleteRequest"
              class="ml-2 mr-2 update-delete-btn"
            >
              삭제
            </button>
          </div>
          <div align="center" class="mb-5">
            <div class="col-lg-11">
              <table class="table">
                <tbody>
                  <tr class="d-flex">
                    <td class="col-12">
                      <div style="font-size: 35px">
                        <strong>{{ requestDetail.kidName }}</strong>
                      </div>
                      <div class="mt-3 mb-2">
                        금일 자녀의 귀가를 선생님께 의뢰합니다.
                      </div>
                    </td>
                  </tr>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">귀가 의뢰 내용</strong>
                      <table
                        class="table mt-5 mb-5"
                        style="
                          border-radius: 10px;
                          background-color: rgba(156, 156, 156, 0.1);
                        "
                      >
                        <tr class="d-flex">
                          <th class="col-2">귀가 날짜</th>
                          <td class="col-4">{{ requestDetail.rhDate }}</td>
                          <th class="col-2">귀가 시간</th>
                          <td class="col-4">{{ requestDetail.rhTime }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">동행자</th>
                          <td class="col-10">{{ requestDetail.guardian }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">
                            <img
                              src="@/assets/flaticon/tel.png"
                              style="width: 2rem"
                            />
                          </th>
                          <td class="col-10">
                            {{ requestDetail.guardianTel }}
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">보호자</th>
                          <td class="col-10">{{ requestDetail.emergency }}</td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2">
                            <img
                              src="@/assets/flaticon/tel.png"
                              style="width: 2rem"
                            />
                          </th>
                          <td class="col-10">
                            {{ requestDetail.emergencyTel }}
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </tbody>
              </table>
              <div>
                영유아의 귀가 시 위 동행자에게 인도하여 주세요.<br />
                위 동행자 이외의 다른 사람에게 인계할때는 사전에 반드시 연락을
                취하겠습니다.
              </div>
            </div>
          </div>
        </div>
      </v-sheet>

      <!-- 귀가 동의 수정 작성 -->
      <v-sheet rounded="lg" v-if="requestType === 2 && (creating || updating)">
        <div
          style="width: 100%; height: 15px; background-color: #a8b1cf"
          class="mt-3"
        ></div>
        <div class="container content-container" style="height: 84.8vh">
          <div align="right" class="mt-2"></div>
          <div align="center" class="mb-5">
            <div class="col-lg-11">
              <table class="table">
                <tbody>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">귀가 동의 내용</strong>
                      <table
                        class="table mt-5 mb-5"
                        style="border-radius: 10px"
                      >
                        <tr class="d-flex">
                          <th class="col-2 mt-2">귀가 날짜</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="rhDate"
                              placeholder="예) 00월 00일"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">귀가 시간</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="rhTime"
                              placeholder="예) 오후 00시 00분"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">동행자</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="guardian"
                              placeholder="원아와의 관계"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">동행자 연락처</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="guardianTel"
                              placeholder="전화번호"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">보호자</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="emergency"
                              placeholder="원아와의 관계"
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <th class="col-2 mt-2">보호자 연락처</th>
                          <td class="col-10">
                            <input
                              type="text"
                              class="formInput"
                              v-model="emergencyTel"
                              placeholder="전화번호"
                            />
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- 학부모 동의 체크 박스 -->
            <div class="d-flex justify-center">
              <v-checkbox
                style="margin: 0"
                v-model="checked"
                label="금일 자녀의 귀가를 선생님께 의뢰합니다."
                color="mainColor1"
                hide-details
              >
              </v-checkbox>
            </div>

            <!-- 새로운 글 작성 관련 버튼 -->
            <button
              :disabled="!checked"
              v-if="identity === 1 && updating === 0 && creating === 1"
              @click="createNewRequest"
              class="mr-2 update-return-btn"
            >
              저장
            </button>
            <button
              v-if="identity === 1 && creating && id"
              @click="offCreateForm"
              class="ml-2 update-return-btn"
            >
              취소
            </button>

            <!-- 수정 관련 버튼-->
            <button
              :disabled="!checked"
              v-if="identity === 1 && updating === 1 && creating === 0 && id"
              @click="updateRequest"
              class="mr-2 update-return-btn"
            >
              수정
            </button>
            <button
              v-if="identity === 1 && updating && id"
              @click="offUpdateForm"
              class="ml-2 update-return-btn"
            >
              취소
            </button>
          </div>
        </div>
      </v-sheet>
    </div>
  </div>
</template> 

<script>
import requestApi from "@/api/request.js";
export default {
  name: "RequestDetail",

  props: {
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

  data() {
    return {
      requestDetail: null,

      checked: false,

      // 투약 관련 변수
      symptom: "",
      medicineType: "",
      dosageVol: 0,
      dosageCnt: 0,
      dosageTime: "",
      storage: "",
      specialNote: "",

      // 귀가 관련 변수
      rhDate: "",
      rhTime: "",
      guardian: "",
      guardianTel: "",
      emergency: "",
      emergencyTel: "",

      creating: 0,
      updating: 0,
    };
  },
  watch: {
    id: function () {
      if (this.id !== 0) {
        this.getRequestDetail();
      }
    },
  },

  methods: {
    offCreateForm() {
      this.creating = 0;
      this.checked = false;
    },

    offUpdateForm() {
      this.updating = 0;
      this.checked = false;
    },

    async getRequestDetail() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        requestType: this.requestType,
        id: this.id,
      };
      let result = await requestApi.getRequestDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      if (this.requestType === 1) {
        this.requestDetail = result.dosage;
      } else if (this.requestType === 2) {
        this.requestDetail = result.returnhome;
      }
      this.creating = 0;
      this.updating = 0;
    },

    // 글 작성 폼 띄우기
    async createNewRequest() {
      if (this.updating === 1) {
        this.updating = 0;
      }

      if (this.creating === 0) {
        if (this.requestType === 1) {
          this.checked = false;
          this.symptom = "";
          this.medicineType = "";
          this.dosageVol = 0;
          this.dosageCnt = 0;
          this.dosageTime = "";
          this.storage = "";
          this.specialNote = "";
        } else if (this.requestType === 2) {
          this.checked = false;
          this.rhDate = "";
          this.rhTime = "";
          this.guardian = "";
          this.guardianTel = "";
          this.emergency = "";
          this.emergencyTel = "";
        }
        this.creating = 1;
      }
      else if (this.creating === 1) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
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
          };
          await requestApi.createRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
        } else if (this.requestType === 2) {
          let data = {
            requestType: this.requestType,
            userId: this.$store.state.user.userId,
            rhDate: this.rhDate,
            rhTime: this.rhTime,
            guardian: this.guardian,
            guardianTel: this.guardianTel,
            emergency: this.emergency,
            emergencyTel: this.emergencyTel,
          };
          await requestApi.createRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
        }
        this.creating = 0;
        this.$emit("get-notebooklist", this.requestType);
      }
    },

    async updateRequest() {
      if (this.creating === 1) {
        this.creating = 0;
      }

      if (this.updating === 0) {
        if (this.requestType === 1) {
          this.checked = true;
          this.symptom = this.requestDetail.symptom;
          this.medicineType = this.requestDetail.medicineType;
          this.dosageVol = this.requestDetail.dosageVol;
          this.dosageCnt = this.requestDetail.dosageCnt;
          this.dosageTime = this.requestDetail.dosageTime;
          this.storage = this.requestDetail.storage;
          this.specialNote = this.requestDetail.specialNote;
        } else if (this.requestType === 2) {
          this.checked = true;
          this.rhDate = this.requestDetail.rhDate;
          this.rhTime = this.requestDetail.rhTime;
          this.guardian = this.requestDetail.guardian;
          this.guardianTel = this.requestDetail.guardianTel;
          this.emergency = this.requestDetail.emergency;
          this.emergencyTel = this.requestDetail.emergencyTel;
        }
        this.updating = 1;
      }

      // 여기서부터 다시 작성
      else if (this.updating === 1) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
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
          };
          await requestApi.updateRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
        } else if (this.requestType === 2) {
          let data = {
            requestType: this.requestType,
            userId: this.$store.state.user.userId,
            id: this.id,
            rhDate: this.rhDate,
            rhTime: this.rhTime,
            guardian: this.guardian,
            guardianTel: this.guardianTel,
            emergency: this.emergency,
            emergencyTel: this.emergencyTel,
          };
          await requestApi.updateRequest(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });
        }
        this.updating = 0;
        this.$emit("get-notebooklist", this.requestType);
      }
    },

    async deleteRequest() {
      this.$fire({
        html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">정말로 삭제하시겠습니까?</p>`,
        type: "question",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#58679A",
      }).then(async (r) => {
        if (r.value) {
          let accessToken = sessionStorage.getItem("access-token");
          let refreshToken = sessionStorage.getItem("refresh-token");
          if (this.requestType === 1) {
            let data = {
              requestType: this.requestType,
              id: this.requestDetail.dosageId,
            };
            await requestApi.deleteRequest(data, {
              "access-token": accessToken,
              "refresh-token": refreshToken,
            });
          } else if (this.requestType === 2) {
            let data = {
              requestType: this.requestType,
              id: this.requestDetail.rhId,
            };
            await requestApi.deleteRequest(data, {
              "access-token": accessToken,
              "refresh-token": refreshToken,
            });
          }
          this.$emit("get-notebooklist", this.requestType);
        }
      });
    },
  },
};
</script>

<style scoped>
.writeBtn {
  position: fixed;
  right: 60px;
  bottom: 65px;
  width: 3.8rem;
}
.update-delete-btn {
  font-size: 18px;
  color: darkgrey;
}
.update-delete-btn:hover {
  color: black;
  transition: 0.3s;
}
.formInput {
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 36px;
  width: 90%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.formInput:hover {
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
.update-return-btn {
  background-color: rgba(168, 177, 207, 1);
  border-radius: 70px;
  height: 36px;
  width: 70px;
  margin: 20px 3px 3px 3px;
  text-align: center;
  color: rgba(256, 256, 256);
  letter-spacing: -1px;
}
/* 스크롤 */
.content-container {
  overflow-y: scroll;
  height: 80vh;
}
.content-container::-webkit-scrollbar {
  width: 7px;
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-thumb {
  background-color: #a8b1cf;
  border-radius: 1px;
}
.content-container::-webkit-scrollbar-track {
  background-color: rgba(233, 234, 239, 0.5);
  border-radius: 1px;
}
</style>