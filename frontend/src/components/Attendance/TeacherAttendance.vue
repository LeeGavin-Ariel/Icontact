<template>
  <div>

    <!-- <div class="d-flex justify-content-center card-group row row-cols-6">
      <MovieItem
        v-for="(movie, idx) in allMovies"
        :key="idx + '3'"
        :movie="movie"
      />
      <div class="col" @click="goToDetail">
        <div class="card text-center">
          <div class="card-body p-0" >
            <div class="hover_effect_box hover_effect_1">
              <div class="content_bg" >
                <img :src="'http://image.tmdb.org/t/p/w200/' + movie.poster_path" style="width: 167px;  height: 250px; object-fit: cover;  z-index: 0;" class="card-img-top rounded d-block" alt="">
              </div>
              <div class="caption">
                <p class="caption_title">
                  {{ movie.title }}
                </p>
                <br v-if="movie.title.length <= 10">
                <div class="caption_desc">
                  <p></p>
                  <br>
                  평점 : {{ movie.rank_average ? movie.rank_average.toFixed(1)  + '★': '-'}}<br>
                  개봉일 <br>
                  {{ movie.release_date }}<br>
                  <br>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div> -->

    <div class="d-flex justify-content-center card-group row row-cols-4">
      <div class="col">
        <div class="card">
          <img :src="require('@/assets/profileImg/gustjs1111' + '.jpg')" class="card-img-top" style="height: 200px;" alt="...">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img :src="require('@/assets/profileImg/gustjs1234' + '.jpg')" class="card-img-top" style="height: 200px;" alt="...">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img :src="require('@/assets/profileImg/gustjs1234' + '.jpg')" class="card-img-top" style="height: 200px;" alt="...">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img :src="require('@/assets/profileImg/gustjs1234' + '.jpg')" class="card-img-top" style="height: 200px;" alt="...">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img :src="require('@/assets/profileImg/gustjs1111' + '.jpg')" class="card-img-top" style="height: 200px;" alt="...">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img :src="require('@/assets/profileImg/gustjs1111' + '.jpg')" class="card-img-top" style="height: 200px;" alt="...">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
          </div>
        </div>
      </div>  
    </div>





  </div>
</template>

<script>
export default {
  name:"TeacherAttendance",
  data () {
    return {
      // 달력관련
      date: new Date().toISOString().substr(0, 10),
      done: [false, false, false],
      mouseMonth: null,
    }
  },
  methods: {

    // 달력 관련
    // contextMenu (year, event) {
    //   this.$set(this.done, 2, true)

    //   event.preventDefault()

    //   alert(`You have activated context menu for year ${year}`)
    // },
    // dblClick (date) {
    //   this.$set(this.done, 0, true)

    //   alert(`You have just double clicked the following date: ${date}`)
    // },
    // mouseEnter (month) {
    //   this.$set(this.done, 1, true)
    //   this.mouseMonth = month
    // },
    // mouseLeave () {
    //   this.mouseMonth = null
    // },
  },
}
</script>

<style>

</style>