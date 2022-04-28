<script setup>
import { onBeforeMount, ref , onBeforeUpdate} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from '../components/ShowList.vue'

const route = useRoute();
const eventLists = ref();
const getLinkAll = async () => {
  const res = await fetch(`http://localhost:8080/api/events`);
  if (res.status === 200) {
    eventLists.value = await res.json();
    
  
  }
};

onBeforeMount(async () => {
  
      getLinkAll()
 
});


</script>
 
<template>
<div>
  
   <ShowList :eventLists="eventLists"/>
  </div>
</template>
 
<style>

</style>