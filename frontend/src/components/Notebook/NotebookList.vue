<template>
  <div>

  </div>
</template>

<script>
import notebookApi from '@/api/notebook.js';
export default {
  name: "NotebookList",
  data () {
    return {
      identity:0
      identity_str: ''
      userId: ''
      notebookList: [],

    }
  },


  methods: {
    async getNotebook() {
      let accessToken = sessionStorage.getItem('access-token')
      let refreshToken = sessionStorage.getItem('refresh-token')
      let data = {
        type: this.identity_str,
        userId: this.userId,
      }
      let result = await notebookApi.getNotebook(data, {
        "access-token": accessToken,
        "refresh-token": refreshToken,
      });
      console.log(result)
      this.notebookList = result

    }
  },
  created() {
    this.identity = this.$store.state.user.type
    this.userId = this.$store.state.user.userId
    if (this.identity === 1) {
      this.identity_str = 'parent'
    }
    else if (this.identity === 2) {
      this.identity_str = 'teacher'
    }
    this.getNotebook()
  },
}
</script>

<style>

</style>