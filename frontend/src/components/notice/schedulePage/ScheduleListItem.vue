<template>
  <v-container fluid>
    <v-card
      color="blue lighten-4 radius"
      dense
      outlined
      elevation="10"
      shaped
      v-bind="$attrs"
      v-on="$listeners"
    >
      <v-row no-gutters>
        <v-col cols="9" class="pl-6">
          <v-row>
            <v-card-title>{{ scheduleInfo.title }} </v-card-title>
            <v-card-subtitle> </v-card-subtitle>
          </v-row>
        </v-col>
        <v-col align-self="end" class="pr-3">
          {{ createDate }}
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "ScheduleListItem",
  inheritAttrs: false,
  data() {
    return {
      createDate: "",
    };
  },

  props: {
    scheduleInfo: {},
  },

  methods: {
    refineDate() {
      this.createDate = this.scheduleInfo.createDate.substr(0, 11);
    },
  },

  created() {
    this.refineDate();
  },
};
</script>

<style>
</style>