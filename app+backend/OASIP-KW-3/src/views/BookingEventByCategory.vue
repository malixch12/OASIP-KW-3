<script setup>
import generateCategory from '../components/generateCategory.vue'
import Navbar from '../components/Navbar.vue'
import { ref } from "vue";

const CateLists = ref();
const getLinkAll = async () => {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/eventcategorys`);
  if (res.status === 200) {
    CateLists.value = await res.json();
  }
};
getLinkAll()
</script>

<template>
<div>
<Navbar/>
<generateCategory name="Select a clinic to booking" :categorys="CateLists" type="Booking"/>


</div>
</template>
 
<style>

</style>