<script setup>
import generateCategory from '../components/generateCategory.vue'
import Navbar from '../components/Navbar.vue'
import { ref } from "vue";

const eventLists = ref();
const getLinkAll = async () => {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/eventcategorys`);
  if (res.status === 200) {
    eventLists.value = await res.json();
  }
};
getLinkAll()
</script>

<template>
<div>
<Navbar/>
<generateCategory name="Select a clinic to Booking" :categorys="eventLists" type="Booking"/>


</div>
</template>
 
<style>

</style>