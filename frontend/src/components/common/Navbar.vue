<template>
    <div>

    </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Navbar',
  
  methods: {
    ...mapState([
        'user'
    ]),
  },
}
</script>

<style>

</style>