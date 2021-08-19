<template>
  <div class="col">
    <!-- 새 글을 작성하는 버튼 -->
    <div class="ml-5 mr-5 mt-5">
      <button
        class="writeBtn"
        v-if="$store.state.user.type === 2 && updating === 0 && creating === 0"
        @click="createNotebook"
      >
        <img src="@/assets/flaticon/write.png" style="width: 3.8rem" />
      </button>

      <div v-if="!id && !(creating || updating)" style="font-size: 1.2rem; text-align:center;">
          등록된 알림장이 없습니다.
      </div>

      <!-- 알림장 디테일 -->
      <v-sheet
        rounded="lg"
        v-if="!creating && !updating && notebookDetail && id"
      >
        <div
          style="width: 100%; height: 15px; background-color: #a8b1cf"
          class="mt-3"
        ></div>
        <div
          class="container content-container"
          style="overflow-y: scroll; height: 84.8vh"
        >
          <div align="right" class="mt-2">
            <button
              v-if="
                $store.state.user.type === 2 &&
                updating === 0 &&
                creating === 0 &&
                id
              "
              @click="updateNotebook"
              class="mr-2 update-delete-btn"
            >
              수정
            </button>
            <button
              v-if="
                $store.state.user.type === 2 &&
                id &&
                updating === 0 &&
                creating === 0
              "
              @click="deleteNotebook"
              class="ml-2 mr-2 update-delete-btn"
            >
              삭제
            </button>
          </div>
          <div class="section-title"></div>
          <div align="center" class="mb-5">
            <div class="col-lg-11">
              <table class="table">
                <tbody>
                  <tr class="d-flex">
                    <td class="col-12">
                      <div style="font-size: 35px">
                        <strong>{{ notebookDetail.title }}</strong>
                      </div>
                      <div class="d-flex align-items-center mt-3 mb-2">
                        <div v-if="$store.state.user.type === 1">
                          {{ notebookDetail.writerName }} 선생님
                        </div>
                        <div v-if="$store.state.user.type === 2">
                          {{ notebookDetail.kidName }} 학부모님
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr class="d-flex">
                    <td
                      class="col-12 align-items-center mt-3"
                      style="min-height: 400px"
                    >
                      <div class="mb-5">
                        <img
                          style="max-width: 100%"
                          v-if="notebookDetail.noteImgUrl"
                          :src="
                            'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                            notebookDetail.noteImgUrl
                          "
                          alt="profile-image"
                        />
                      </div>
                      <div v-html="noteContent"></div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </v-sheet>

      <!-- 알림장 작성, 수정 -->
      <v-sheet rounded="lg" v-if="creating || updating">
        <div
          style="width: 100%; height: 15px; background-color: #a8b1cf"
          class="mt-3"
        ></div>
        <div class="container content-container" style="height: 84.8vh">
          <div align="right" class="mt-2"></div>
          <div align="center" class="mb-5">
            <div class="col-lg-11">
              <table class="table table-borderless mb-0">
                <tbody>
                  <tr class="d-flex">
                    <td class="col-12 align-items-center mt-3">
                      <strong style="font-size: 18px">알림장</strong>
                      <table class="table mt-5 mb-5">
                        <tr class="d-flex">
                          <td class="col-4">
                            <select
                              name="targetId"
                              id="targetId"
                              v-model="targetId"
                              class="select-form"
                            >
                              <option value="" selected>원아 선택</option>
                              <option
                                v-for="parent in parentList"
                                :key="parent.userId"
                                :value="parent.userId"
                              >
                                {{ parent.kidName }} 학부모님
                              </option>
                            </select>
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12">
                            <input
                              type="text"
                              v-model="title"
                              class="formInput"
                              placeholder="제목을 입력하세요."
                            />
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12">
                            <textarea
                              rows="15"
                              v-model="content"
                              class="formInputText"
                              placeholder="내용을 입력하세요."
                            ></textarea>
                          </td>
                        </tr>
                        <tr class="d-flex">
                          <td class="col-12 mb-0">
                            <!-- 파일 첨부-->
                            <v-file-input
                              id="notebookimgfile"
                              filled
                              prepend-icon="mdi-camera"
                              dense
                              @change="onFileSelected"
                            ></v-file-input>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- 새로운 글 작성 관련 버튼 -->
            <!-- 새 글을 작성하고, 저장하는 버튼 -->
            <button
              v-if="
                $store.state.user.type === 2 && updating === 0 && creating === 1
              "
              @click="createNotebook"
              :disabled="!title || !targetId"
              class="mr-2 update-return-btn"
            >
              작성
            </button>
            <!-- 글 작성(수정,새글) 취소 버튼 -->
            <button
              v-if="
                $store.state.user.type === 2 && updating === 1 && creating === 0
              "
              @click="updateNotebook"
              :disabled="!title || !targetId"
              class="mr-2 update-return-btn"
            >
              수정
            </button>
            <button
              v-if="
                $store.state.user.type === 2 &&
                (creating === 1 || updating === 1)
              "
              @click="offCreateForm"
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
import notebookApi from "@/api/notebook.js";
import awss3 from "@/utils/awss3.js";
export default {
  name: "NotebookDetail",
  data() {
    return {
      notebookDetail: null,

      userId: "",

      title: "",
      content: "",
      writerId: "",
      targetId: "",
      notebookfile: null,
      parentList: [],

      creating: 0,
      updating: 0,
    };
  },
  props: {
    identity: {
      identity: Number,
    },
    id: {
      id: Number,
    },
  },

  watch: {
    id: function () {
      this.getNotebookDetail();
    },
  },
  computed: {
    noteContent() {
      return this.notebookDetail.content.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
  methods: {
    offCreateForm() {
      this.updating = 0;
      this.creating = 0;
    },
    onFileSelected(event) {
      this.notebookfile = event;
    },

    async getNotebookDetail() {
      if (this.id !== 0) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
        let data = {
          Id: this.id,
        };
        let result = await notebookApi.getNotebookDetail(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        });
        this.notebookDetail = result;
        this.creating = 0;
        this.updating = 0;
      }
    },

    // 학부모 목록 조회
    async getParentList() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        userId: this.$store.state.user.userId,
      };
      let result = await notebookApi.getParent(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      this.parentList = result;
    },

    async createNotebook() {
      // 작성중이 아닌 상태
      if (this.updating === 1) {
        this.updating = 0;
      }

      if (this.creating === 0) {
        this.title = "";
        this.content = "";
        this.writerId = "";
        this.targetId = "";
        this.creating = 1;
      }
      else if (this.creating === 1) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");
        if (this.notebookfile !== null) {
          let notebookImgUrl = await awss3.uploadPhoto(
            "notebook",
            "notebookimgfile"
          );
          let data = {
            img: notebookImgUrl[0],
            title: this.title,
            content: this.content,
            targetId: this.targetId,
            writerId: this.$store.state.user.userId,
          };
          await notebookApi.createNotebook(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });

          this.creating = 0;
          this.$emit("get-notebooklist");
        } else {
          let data = {
            img: "",
            title: this.title,
            content: this.content,
            targetId: this.targetId,
            writerId: this.$store.state.user.userId,
          };
          await notebookApi.createNotebook(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });

          this.creating = 0;
          this.$emit("get-notebooklist");
        }
        this.notebookfile = null;
      }
    },

    async updateNotebook() {
      if (this.creating === 1) {
        this.creating = 0;
      }

      if (this.updating === 0) {
        this.title = this.notebookDetail.title;
        this.content = this.notebookDetail.content;
        this.writerId = this.notebookDetail.writerId;
        this.targetId = this.notebookDetail.targetId;
        this.updating = 1;
      }
      else if (this.updating === 1) {
        let accessToken = sessionStorage.getItem("access-token");
        let refreshToken = sessionStorage.getItem("refresh-token");

        let photoKey = this.notebookDetail.noteImgUrl;

        // 수정하기 전 사진이 존재할 때
        if (photoKey) {
          // 수정할 사진을 업로드 할 때
          if (this.notebookfile) {
            let notebookImgUrl = await awss3.updatePhoto(
              "notebook",
              photoKey,
              "notebookimgfile"
            );
            let data = {
              noteId: this.notebookDetail.noteId,
              img: notebookImgUrl[0],
              title: this.title,
              content: this.content,
              targetId: this.targetId,
              writerId: this.$store.state.user.userId,
            };
            await notebookApi.updateNotebook(data, {
              "access-token": accessToken,
              "refresh-token": refreshToken,
            });
            this.updating = 0;
            this.notebookDetail.noteImgUrl = notebookImgUrl[0];
            this.$emit("get-notebooklist");
            this.notebookfile = null;
          }
          // 업로드 안할 때
          else {
            await awss3.deletePhoto([photoKey], "");
            let data = {
              noteId: this.notebookDetail.noteId,
              img: "",
              title: this.title,
              content: this.content,
              targetId: this.targetId,
              writerId: this.$store.state.user.userId,
            };
            await notebookApi.updateNotebook(data, {
              "access-token": accessToken,
              "refresh-token": refreshToken,
            });
            this.updating = 0;
            this.notebookDetail.noteImgUrl = "";
            this.$emit("get-notebooklist");
            this.notebookfile = null;
          }
        }
        // 수정하기 전 사진이 존재하지 않을 때
        else {
          // 수정할때 사진 업로드 시
          if (this.notebookfile) {
            let notebookImgUrl = await awss3.uploadPhoto(
              "notebook",
              "notebookimgfile"
            );
            let data = {
              noteId: this.notebookDetail.noteId,
              img: notebookImgUrl[0],
              title: this.title,
              content: this.content,
              targetId: this.targetId,
              writerId: this.$store.state.user.userId,
            };
            await notebookApi.updateNotebook(data, {
              "access-token": accessToken,
              "refresh-token": refreshToken,
            });
            this.updating = 0;
            this.notebookDetail.noteImgUrl = notebookImgUrl[0];
            this.$emit("get-notebooklist");
            this.notebookfile = null;
          }
          // 수정할 때 사진 업로드 안할 시
          else {
            let data = {
              noteId: this.notebookDetail.noteId,
              img: "",
              title: this.title,
              content: this.content,
              targetId: this.targetId,
              writerId: this.$store.state.user.userId,
            };
            await notebookApi.updateNotebook(data, {
              "access-token": accessToken,
              "refresh-token": refreshToken,
            });
            this.updating = 0;
            this.notebookDetail.noteImgUrl = "";
            this.$emit("get-notebooklist");
            this.notebookfile = null;
          }
        }
      }
    },

    async deleteNotebook() {
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
          let notebookImgUrl = this.notebookDetail.noteImgUrl;
          await awss3.deletePhoto([notebookImgUrl], "");
          let data = {
            notebookId: this.notebookDetail.noteId,
          };
          await notebookApi.deleteNotebook(data, {
            "access-token": accessToken,
            "refresh-token": refreshToken,
          });

          this.$emit("get-notebooklist");
        }
      });
    },
  },
  created() {
    this.getParentList();
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
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
.formInputText {
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 100%;
  width: 100%;
  padding: 15px 15px 15px 15px;
  margin: 3px 3px 3px 3px;
}
.formInputText:hover {
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
}
.formInputText:focus {
  background-color: rgba(156, 156, 156, 0.2);
  transition: 0.3s;
  outline: none;
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
.select-form {
  outline: none;
  background-color: rgba(156, 156, 156, 0.1);
  border-radius: 5px;
  height: 100%;
  width: 100%;
  padding: 0px 0px 0px 15px;
  margin: 3px 3px 3px 3px;
}
select {
  background: url("../../assets/flaticon/arrow.png") no-repeat right 9px center;
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