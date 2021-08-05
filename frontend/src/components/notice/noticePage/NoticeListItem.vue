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
          <v-row >
            <v-card-title>{{ noticeInfo.title }} </v-card-title>
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
  name: "NoticeListItem",
  inheritAttrs: false,
  data() {
    return {
      createDate: "",
    };
  },

  props: {
    noticeInfo: {},
  },

  methods: {
    refineDate() {
      this.createDate = this.noticeInfo.createDate.substr(0, 11);
    },
  },

  created() {
    this.refineDate();
  },
};
</script>

<style>
</style>