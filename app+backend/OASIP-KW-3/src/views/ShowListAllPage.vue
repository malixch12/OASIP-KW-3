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

const test = ref()
const now_date = ref()
function dateFilter (FilterDate) {
console.log(FilterDate)
test.value =   new Date(FilterDate)
now_date.value =
  test.value.getDate() +
  "/" +
  (test.value.getMonth() + 1) +
  "/" +
  (test.value.getFullYear() + 543);


  let dateArrayFilter = eventLists.value.content.filter((event)=>{
  return event.eventStartTime == "2022-05-18T20:17:00Z"
})
console.log(dateArrayFilter)
}


  const eventLists2 = ref([])

//   function test () {

//     eventLists2.value = eventLists.value.content.filter((event)=>{
//   return event.bookingId = 23

// })
//   }


    
</script>

<template>
  <div>
   back : {{ new Date(eventLists.content[0].eventStartTime).toLocaleDateString("th-TH")}} <br>
    {{dateArrayFilter}}
     <br>
      {{eventLists.content}} 

   choose :  {{now_date}}
    <Navbar />
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
