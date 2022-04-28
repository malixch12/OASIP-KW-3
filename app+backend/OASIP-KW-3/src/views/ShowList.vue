<script setup>
import { onBeforeMount, ref , onBeforeUpdate} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from '../components/ShowList.vue'

const route = useRoute();

console.log(route.query.categoryId)


const eventLists = ref();
const getLink = async () => {
  const res = await fetch(`http://localhost:8080/api/events/category/${route.query.categoryId}`);
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
  
   <ShowList :eventLists="eventLists"/>
  </div>
</template>
 
<style>

</style>