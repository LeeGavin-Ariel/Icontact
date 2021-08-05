<template>
<div>
  <div style="height:4vh; width:95%;">
    <label for="file-selector" style="float: right;">
      <input 
        id="file-selector"
        ref="file"
        type="file"
        style="display: none;"
      >
        <img src="@/assets/flaticon/upload.png"
        id="file-uploader-btn"
        alt="upload-button">
    </label>
  </div>
  <div class="container d-flex justify-content-center" style="height:80vh; margin:2.5rem; overflow:scroll; border: solid rgba(169,177,204, 0.7); border-radius: 8px;">
    <v-row>
      <v-col
        v-for="n in 36"
        :key="n"
        cols="3"
      >   
        <v-card
          class="mx-auto"
          max-width="300"
          max-height="300"
          type="button"
          @click="setDetail(n)"
        >
          <v-img
            src="@/assets/1.jpeg"
            height="200px"
          ></v-img>
          <v-card-subtitle style="font-size:0.7em; text-align:right;">
            학예회 2021/07/20
          </v-card-subtitle>
        </v-card>  
      </v-col>
      <AlbumDetail
        v-if="visible"
        :visible="visible"
        :selectedAlbumId="selectedAlbumId"
        @updateVisible="updateVisible"
      />



    </v-row>
  </div>

</div>
</template>

<script>
import AlbumDetail from '@/components/Album/AlbumDetail'
// import AWS from 'aws-sdk'

export default {
  name : "AlbumList",
  components: {
    AlbumDetail
  },
  data () {
    return {
      visible: false,
      selectedAlbumId: 0
    }
  },
  methods: {
    setDetail(n){
      this.selectedAlbumId = n
      this.updateVisible()
    },
    updateVisible(){
      this.visible = !this.visible
    }
  },
}
</script>

<style>
.container {
  max-width: 93%;
}
#file-uploader-btn {
  height: 2.5rem; 
  width: 2.5rem; 
  margin-top:1.7rem;
  cursor: pointer;
}

</style>