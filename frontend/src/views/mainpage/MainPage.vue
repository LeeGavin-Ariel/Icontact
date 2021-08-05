<template >
  <div style="height:100vh" class="d-flex align-center">
    <v-container id="container" >
      <v-row>
        <v-col
          v-for="func in funcName"
          :key="func"
          class="d-flex child-flex"
          cols="4"
        >

          <button @click="$router.push({ name: func })">
            <v-img
              :src="require(`@/assets/${func}.png`)"
              :lazy-src="require(`@/assets/${func}.png`)"
              aspect-ratio="1"
              class="grey lighten-2 img"
            >
              <template v-slot:placeholder>
                <v-row
                  class="fill-height ma-0"
                  align="center"
                  justify="center"
                >
                  <v-progress-circular
                    indeterminate
                    color="grey lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
            </v-img>
          </button>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "MainPage",
  data: () => {
    return {
      funcName: [
        "Notebook",
        "Chat",
        "Album",
        "Attendance",
        "Notice",
        "Request",
      ],
    };
  }
}
</script>

<style scoped>
#container {
  width: 800px;
}
</style>