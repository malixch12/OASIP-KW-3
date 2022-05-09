<script setup>
import { onBeforeMount, ref , onBeforeUpdate} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from '../components/ShowList.vue'
import Navbar from '../components/Navbar.vue'
const route = useRoute();

console.log(route.query.categoryId)


const eventLists = ref();
const getLink = async () => {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/category/${route.query.categoryId}`);
  if (res.status === 200) {
    eventLists.value = await res.json();
    
  }
};



onBeforeMount(async () => {
  getLink();
});


</script>
 
<template>
<div>
  <Navbar/>
  <ShowList :eventLists="eventLists" colNum='grid-cols-4'/>
  </div>
</template>
 
<style>

</style>