<script setup>
import AddEvent from '../components/AddEvent.vue'
import { onBeforeMount, onBeforeUpdate, ref  } from 'vue'
import ShowList from '../components/ShowList.vue'


const eventLists = ref();
const id = ref();

const getLinkAll = async () => {
  const res = await fetch(`http://localhost:8080/api/events`);
  if (res.status === 200) {
  eventLists.value = await res.json();
  }
};



onBeforeUpdate( () => {
  
 if(eventLists.value.length>0) {
    id.value = eventLists.value[eventLists.value.length-1].bookingId
 }else{
  id.value = 0
 }
});

onBeforeMount( () => {
 
  getLinkAll();
 
});


const addEvent = async (dataBooking) => {
  console.log(dataBooking)
  getLinkAll()
  // if(dataBooking.bookingName == ''){
  //   console.log('empty')
  // }
  const res = await fetch('http://localhost:8080/api/events',{
    method: 'POST',
    headers: {
        'content-type' : 'application/json'
    },
    body:JSON.stringify(dataBooking)
})
getLinkAll()
}


</script>
 
<template>
<div> 
  <div  class="flex justify-between grid grid-cols-3 gap-2">
  
    <AddEvent :id="id" @addEvent="addEvent" @click="getLinkAll"/>
    <ShowList :eventLists="eventLists" class="col-span-2" />
  </div>
</div> 
</template>
 
<style>

</style>