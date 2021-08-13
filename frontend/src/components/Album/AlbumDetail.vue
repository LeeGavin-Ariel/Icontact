<template>
<div>
  <!-- 모달 배경 -->
  <div 
    class="modalBackground"
    v-if="visible"
    @click="$emit('updateVisible', !visible)"
    >
  </div>

    <!-- 모달 박스 -->
    <div class="modalBox flex-column" v-if="visible">
      <div style="width:100%; display:flex; justify-content: flex-end;">
        <!-- 삭제 버튼 -->
        <button style="margin-right:1.3rem; margin-bottom:1.3rem;" v-if="user.type === 2">
          <img @click="deleteAlbum" class="modalBtn" src="@/assets/flaticon/trash.png" alt="close-icon">
        </button>
        <!-- 다운로드 버튼 -->
        <button style="margin-right:1.3rem; margin-bottom:1.3rem;"> 
          <a :href="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/' + downloadUrl" download>
            <img class="modalBtn"  src="@/assets/flaticon/download.png" alt="close-icon">
          </a>
        </button >
        <!-- 닫기 버튼 -->
        <button style="margin-right:1.3rem; margin-bottom:1.2rem;">
          <img @click="$emit('updateVisible', !visible)" class="modalBtn" src="@/assets/flaticon/close1.png" alt="close-icon">
        </button>
        
      </div>
      
      <!-- 슬라이더 -->
      <div
        class="modalSlide"
      >
        <swiper class="swiper gallery-top" :options="swiperOptionTop" ref="swiperTop">
          <!-- <swiper-slide class="slide-1"></swiper-slide> -->
          <swiper-slide v-for="image in images" :key="image.photoId">
            <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ image.photoId" style="height:100%; object-fit: cover" alt="">
          </swiper-slide>

          <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
          <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
        </swiper>
        <!-- swiper2 Thumbs -->
        <swiper class="swiper gallery-thumbs" :options="swiperOptionThumbs" ref="swiperThumbs">
          <swiper-slide v-for="image in images" :key="image.photoId">
            <img :src="'https://ssafy-cmmpjt304.s3.ap-northeast-2.amazonaws.com/'+ image.photoId" style="width:100%; height: 100%; object-fit: cover;" alt="">
          </swiper-slide>
        </swiper>
      </div>

      


        
    </div>
</div>  
</template>

<script>
import { mapState } from 'vuex'
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'
import albumApi from '@/api/album.js';
import awss3 from '@/utils/awss3.js'


export default {
  name: 'AlbumDetail',
  components: {
    Swiper,
    SwiperSlide
  },
  props: {
    visible: {
      type: Boolean,
      require:true,
      default:false
    },
    selectedAlbumId: {
      type: Number,
    }
  },
  data() {
    return {
      images : [],
      downloadUrl : "",
      photoKeyList:[],

      swiperOptionTop: {
        loop: false,
        loopedSlides: 5, // looped slides should be the same
        spaceBetween: 10,
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
      },
      swiperOptionThumbs: {
        loop: false,
        loopedSlides: 5, // looped slides should be the same
        spaceBetween: 10,
        centeredSlides: true,
        slidesPerView: 'auto',
        touchRatio: 0.2,
        slideToClickedSlide: true
      }
    }
  },
  computed: {
    // 배열안에 ['videos']로 쓴 경우는 이름을 바꿀 수 없음 
    ...mapState({
      'user': 'user',
    })
  },
  methods: {
    async getAlbumDetail() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        albumId: this.selectedAlbumId
      }
      let result = await albumApi.getAlbumDetail(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      })
      this.downloadUrl = result.downloadUrl
      this.images = result.albumImgs
      console.log(this.images)
      for(var image of this.images) {
        this.photoKeyList.push(image.photoId)
      }
    },

    async deleteAlbum() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')

      let data = {
        albumId: this.selectedAlbumId
      }
      await albumApi.deleteAlbum(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      })
      // testlist = photoKeyList, testzip = photoKey, 
      awss3.deletePhoto(this.photoKeyList, this.downloadUrl);
      this.photoKeyList = []
      this.downloadUrl = ""
      this.$emit('updateVisible', !this.visible)
      this.$emit('get-album')
    },


    },
  created() {
    console.log(this.selectedAlbumId)
    this.getAlbumDetail()
  },
  mounted() {
    this.$nextTick(() => {
      const swiperTop = this.$refs.swiperTop.$swiper
      const swiperThumbs = this.$refs.swiperThumbs.$swiper
      swiperTop.controller.control = swiperThumbs
      swiperThumbs.controller.control = swiperTop
    })
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

// 모달 관련
.modalBackground {
  position: fixed;
  top:0; left: 0; bottom: 0; right: 0;
  background: rgba(0, 0, 0, 0.8);
  display: block;
}
 
.modalBox {
  position: fixed;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);

  background-color: rgba(240, 241, 247);
  display: flex; justify-content: center;
  align-items: center;
  border-radius: 4px;
  width: 70vw;
  height: 80vh;
}

.modalBtn {
  height: 1.1rem;
  width: 1.1rem;

}
.modalSlide {
  height: 90%;
  width: 100%;
}
// 슬라이더 관련
.thumb-example {
  height: 480px;
  background-color: $black;
}

.swiper {
  .swiper-slide {
    background-size: cover;
    background-position: center;
  }

  &.gallery-top {
    height: 80%;
    width: 100%;
  }
  &.gallery-thumbs {
    height: 25%;
    box-sizing: border-box;
    padding: $gap 0;
  }
  &.gallery-thumbs .swiper-slide {
    width: 10%;
    height: 100%;
    opacity: 0.4;
    cursor: pointer;
  }
  &.gallery-thumbs .swiper-slide:hover {
    opacity: 0.8;
    transition: 0.4s;
  }
  &.gallery-thumbs .swiper-slide-active {
    opacity: 1;
  }
}

</style>