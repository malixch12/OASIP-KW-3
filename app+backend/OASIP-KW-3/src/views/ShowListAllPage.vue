<script setup>
import { onBeforeMount, ref , onBeforeUpdate} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from '../components/ShowList.vue'
import Navbar from '../components/Navbar.vue'
const route = useRoute();
const eventLists = ref();
const page = ref(0)
const numPage = ref( )
const getLinkAll = async () => {
  // const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);
  
  const res = await fetch(`http://localhost:8080/api/events?page=${page.value}&pageSize=8`);
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8)
  }

};

onBeforeMount(async () => {
      getLinkAll()
});
</script>
 
<template>
<div>
  <Navbar />
   <ShowList :eventLists="eventLists.content" colNum='grid-cols-4'/>
   <div class = "bg-rose-300 shadow-xl rounded-b-lg ml-24 mr-24 text-center">
     <span v-for="(e,index) in numPage" :key="index" class="p-5 text-white hover:text-rose-400" >
     <button  @click="page=index , getLinkAll()">{{index+1}} </button>
    </span>
    
    </div>
    

  </div>
</template>
 
<style>

</style>