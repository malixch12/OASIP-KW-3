<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
const route = useRoute();
const eventLists = ref({content:[{test:"test"}]});
const page = ref(0);
const numPage = ref();
const getLinkAll = async () => {
  // const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events?page=${page.value}&pageSize=8`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
};

const getLinkAllNoPage = async (FilterDate) => {
  // const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events?page=${page.value}&pageSize=100000`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();

    let dateArrayFilter = eventLists.value.content.filter((event)=>{
  return event.date == FilterDate
})
  eventLists.value.content = dateArrayFilter
      numPage.value = 0
  }
};


onBeforeMount(async () => {
  getLinkAll();
});

function paging(index,filter) {
  page.value = index;
 
  if(filter==3){
getLinkAll()
}
if(filter==2){
getLinkFuture()
}
if(filter==1){
getLinkPast()
}
}

const getLinkPast = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/pastdays/?pageSize=8&page=${page.value}`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
};

const getLinkFuture = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/futuredays/?pageSize=8&page=${page.value}`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
    console.log( eventLists.value)
  }
};

function pastFilter() {
  getLinkPast();
}

function futureFilter() {
  getLinkFuture();
}

function allFilter() {
  getLinkAll();
 
}


const dateFilter =  (FilterDate) =>  {
getLinkAllNoPage(FilterDate);
}

    
</script>

<template>
  <div>
    
   
    <ShowList
      :eventLists="eventLists.content"
      colNum="grid-cols-4"
      :numPage="numPage"
      @paging="paging"
      @pastFilter="pastFilter"
      @futureFilter="futureFilter"
      @allFilter="allFilter"
      @dateFilter="dateFilter"
    />
  </div>
</template>

<style></style>
