<script setup>
import { onBeforeMount, ref , onBeforeUpdate} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from '../components/ShowList.vue'
import Navbar from '../components/Navbar.vue'
const route = useRoute();
const eventLists = ref();
const getLinkAll = async () => {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);
  if (res.status === 200) {
    eventLists.value = await res.json();
  }
};

onBeforeMount(async () => {
  
      getLinkAll()
      console.log("สักทีไอ้สัส2")
 
});




  

</script>
 
<template>
<div>
  <Navbar />
   <ShowList :eventLists="eventLists" colNum='grid-cols-4'/>

  
  </div>
</template>
 
<style>

</style>