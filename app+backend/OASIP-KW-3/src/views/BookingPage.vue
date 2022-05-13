<script setup>
import AddEvent from "../components/AddEvent.vue";
import { onBeforeMount, onBeforeUpdate, ref } from "vue";
import ShowList from "../components/ShowList.vue";
import { useRoute } from "vue-router";
const route = useRoute();

const eventLists = ref();
const id = ref();

const getLinkAll = async () => {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/category/${route.query.categoryId}`);
  if (res.status === 200) {
    eventLists.value = await res.json();
  }
};

onBeforeUpdate(() => {
  if (eventLists.value.length > 0) {
    id.value = eventLists.value[eventLists.value.length - 1].bookingId;
  } else {
    id.value = 0;
  }
});

onBeforeMount(() => {
  getLinkAll();
});

// const yourISODateTime = computed(() => {
//   test1.value = new Date(yourDateTime.value).toISOString();
//  return new Date(yourDateTime.value).toISOString();
// });

const addEvent = async (dataBooking) => {

  
  dataBooking.eventStartTime=new Date(dataBooking.eventStartTime).toISOString();

 console.log("test " +dataBooking);
  console.log("varar " + dataBooking.eventStartTime);
  getLinkAll();
  // if(dataBooking.bookingName == ''){
  //   console.log('empty')
  // }
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(dataBooking),
  });
  
  getLinkAll();
};

const categoryDetail = {
  categoryId: route.query.categoryId,
  categoryName: route.query.eventCategoryName,
  categoryDuration: route.query.eventDuration,
};

</script>
 
<template>
  <div>
    <div class="flex justify-between grid grid-cols-3 gap-2">
      <AddEvent :id="id" @addEvent="addEvent" @click="getLinkAll" :categoryDetail="categoryDetail"/>
      <ShowList
        :eventLists="eventLists"
        colNum="grid-cols-3"
        class="col-span-2"
      />
    </div>
  </div>
</template>
 
<style>
</style>