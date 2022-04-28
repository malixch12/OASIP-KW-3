<script setup>
import { ref, onBeforeMount } from "vue";

import Navbar from './components/Navbar.vue';

</script>

<template>
  <div
    class="bg-gradient-to-r from-emerald-100 via-blue-200 to-pink-200 to-pink-200 relative flex min-h-screen flex-col justify-center overflow-hidden bg-gray-50 py-6 sm:py-12"
  >
    <div
      class="relative dark:bg-gray-800 bg-white px-16 pt-16 pb-16 shadow-xl ring-1 ring-gray-900/5 sm:mx-auto sm:max sm:rounded-lg grid grid-cols-1"
    >
     <Navbar />
  <router-view>
     </router-view>

  </div> </div>
</template>

<style></style>
