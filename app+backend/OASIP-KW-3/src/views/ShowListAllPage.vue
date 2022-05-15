<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
const route = useRoute();
const eventLists = ref();
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
    `${import.meta.env.VITE_APP_TITLE}/api/events/past/?pageSize=8&page=${page.value}`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
};

const getLinkFuture = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/future/?pageSize=8&page=${page.value}`
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
</script>

<template>
  <div>
    <Navbar />
    <ShowList
      :eventLists="eventLists.content"
      colNum="grid-cols-4"
      :numPage="numPage"
      @paging="paging"
      @pastFilter="pastFilter"
      @futureFilter="futureFilter"
      @allFilter="allFilter"
    />
  </div>
</template>

<style></style>
