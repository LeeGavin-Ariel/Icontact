<template>
  <div>
    <div style="height: 4vh; width: 95%">
      <button
        data-bs-toggle="modal"
        style="float: right"
        data-bs-target="#profileModal"
      >
        <img
          src="@/assets/flaticon/upload.png"
          id="file-uploader-btn"
          alt="upload-button"
        />
      </button>
      <div
        class="modal fade"
        id="profileModal"
        tabindex="-1"
        aria-labelledby="profileModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="profileModalLabel">사진 변경</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <input type="text" id="title" />

              <input type="file" id="photoupload" multiple />
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                닫기
              </button>
              <button
                type="button"
                class="btn btn-primary"
                @click="uploadZip"
                data-bs-dismiss="modal"
              >
                앨범 생성
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="container d-flex justify-content-center"
      style="
        height: 80vh;
        margin: 2.5rem;
        overflow: scroll;
        border: solid rgba(169, 177, 204, 0.7);
        border-radius: 8px;
      "
    >
      <v-row>
        <v-col v-for="album in albumList" :key="album.albumId" cols="3">
          <v-card
            class="mx-auto"
            max-width="300"
            max-height="300"
            type="button"
            @click="setDetail(album.albumId)"
          >
            <v-img
              :src="
                'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' +
                album.thumbnailUrl
              "
              height="200px"
            ></v-img>
            <v-card-subtitle style="font-size: 0.7em; text-align: right">
              {{ album.title }}
              {{ album.createDate2 }}
            </v-card-subtitle>
          </v-card>
        </v-col>
        <button
          @click="getMoreThumbnail"
          v-if="albumList.length > 0 && pageNum < pageCnt"
        >
          더보기
        </button>
        <p v-else>불러올 글이 없습니다</p>
        <AlbumDetail
          v-if="visible"
          :visible="visible"
          :selectedAlbumId="selectedAlbumId"
          @updateVisible="updateVisible"
          @get-album="reGetAlbumthumbnail"
        />
      </v-row>
    </div>
  </div>
</template>

<script>
import AlbumDetail from "@/components/Album/AlbumDetail";
import albumApi from "@/api/album.js";
import awss3 from "@/utils/awss3.js";

export default {
  name: "AlbumList",
  components: {
    AlbumDetail,
  },
  data() {
    return {
      title: "",
      photoList: [],
      ZipUrl: "",

      pageNum: 0,
      pageCnt: 0,

      albumList: [],

      visible: false,
      selectedAlbumId: 0,
    };
  },
  methods: {
    reGetAlbumthumbnail() {
      this.pageNum = 0;
      this.albumList = [];
      this.getMoreThumbnail();
    },
    getMoreThumbnail() {
      if (this.pageNum <= this.pageCnt) {
        this.getAlbumthumbnail();
      }
    },

    // 썸네일 조회
    async getAlbumthumbnail() {
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let classCode = this.$store.state.user.classCode;
      let data = {
        classCode: classCode,
        pageNum: this.pageNum,
      };
      let result = await albumApi.getAlbumthumbnail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result);
      this.pageCnt = result.pageCnt;
      this.albumList.push(...result.thumbnailList);
      this.pageNum += 1;
    },

    // 사진 업로드
    async uploadZip() {
      // 기존 title, classCode, writerId(userId)
      // 1. urlList -> photoList
      this.title = document.getElementById("title").value;
      var list1 = await awss3.uploadPhoto("album", "photoupload");
      // 2. downloadUrl
      var list2 = awss3.uploadZip();
      console.log({ photoKeyLilst: list1, zipKey: list2 });

      this.photoList = list1;
      this.ZipUrl = list2;
      let classCode = this.$store.state.user.classCode;
      let writerId = this.$store.state.user.userId;
      console.log(this.title);
      let accessToken = sessionStorage.getItem("access-token");
      let refreshToken = sessionStorage.getItem("refresh-token");
      let data = {
        title: this.title,
        classCode: classCode,
        writerId: writerId,
        photoList: this.photoList,
        downloadUrl: this.ZipUrl,
      };
      await albumApi
        .createAlbum(data, {
          "access-token": accessToken,
          "refresh-token": refreshToken,
        })
        .then(() => {
          alert("업로드 성공");
        })
        .catch(() => {
          alert("업로드 실패");
        });
      this.pageNum = 0;
      this.albumList = [];
      this.getAlbumthumbnail();
    },

    setDetail(n) {
      this.selectedAlbumId = n;
      this.updateVisible();
    },
    updateVisible() {
      this.visible = !this.visible;
    },
  },
  created() {
    this.getAlbumthumbnail();
  },
};
</script>

<style scoped>
.container {
  max-width: 93%;
}
#file-uploader-btn {
  height: 2.5rem;
  width: 2.5rem;
  margin-top: 1.7rem;
  cursor: pointer;
}
</style>