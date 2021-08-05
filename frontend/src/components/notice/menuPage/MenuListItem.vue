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
          <v-row justify="center">
              <v-col align-self="end">{{menuInfo.amSnack}}</v-col>
              <v-col align-self="end">{{menuInfo.lunch}}</v-col>
              <v-col align-self="end">{{menuInfo.pmSnack}}</v-col>
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
  name: "MenuListItem",
  inheritAttrs: false,
  data() {
    return {
      createDate: "",
    };
  },

  props: {
    menuInfo: {},
  },

  methods: {
    refineDate() {
      this.createDate = this.menuInfo.createDate.substr(0, 11);
    },
  },

  created() {
    this.refineDate();
  },
};
</script>

<style>
</style>