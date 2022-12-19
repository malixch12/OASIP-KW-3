<script setup>
import { ref, onBeforeMount , onBeforeUpdate } from "vue";
import Navbar from '../src/components/Navbar.vue'

const jwtToken = ref()
onBeforeMount(() => {

 
  jwtToken.value = localStorage.getItem('jwtToken');
  if(jwtToken.value==null) {
    localStorage.setItem('UserRole', "Guest");

  }


});


</script>

<template>
  <div
    class="
    
      relative
       
      min-h-screen
      flex-col
      justify-center
      overflow-hidden
      bg-gray-50
      font-rubik
      
    "
  >
    
    <Navbar/>
      <div class="mt-3"><router-view> </router-view> </div>

  </div>
</template>

<style>
/* @import url("https://fonts.googleapis.com/css?family=Rubik"); */
@import url('https://fonts.googleapis.com/css2?family=Itim&display=swap');

html,
body {
  font-family: "Rubik", sans-serif;
}

#app {
  font-family: "Rubik", sans-serif;
}
</style>
