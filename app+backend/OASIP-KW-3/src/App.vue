<script setup>
import { ref, onBeforeMount } from "vue";

import Navbar from "./components/Navbar.vue";
</script>

<template>
  <div
    class="
      bg-gradient-to-r
      from-emerald-100
      via-blue-200
      to-pink-200 to-pink-200
      relative
      flex
      min-h-screen
      flex-col
      justify-center
      overflow-hidden
      bg-gray-50
      py-6
      sm:py-12
    "
  >
    
      <!-- <Navbar /> -->
      <router-view> </router-view>

  </div>
</template>

<style>
@import url("https://fonts.googleapis.com/css?family=Rubik");

html,
body {
  font-family: "Rubik", sans-serif;
}

#app {
  font-family: "Rubik", sans-serif;
}
</style>
