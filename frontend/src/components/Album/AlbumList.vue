<template>
<div>
  <div style="height:4vh; width:96%;">
    <button style="float: right; margin-top:1.5rem;"><img src="@/assets/flaticon/upload.png" style="height: 2.5rem; width: 2.5rem; margin:0.5rem;" alt=""></button>
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
      </v-row>
    </div>

</div>
</template>

<script>
export default {
  name : "AlbumList",
}
</script>

<style>
.container {
    max-width: 93%;
}
</style>