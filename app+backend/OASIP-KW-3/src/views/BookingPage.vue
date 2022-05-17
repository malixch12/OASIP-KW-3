<script setup>
import AddEvent from "../components/AddEvent.vue";
import { onBeforeMount, onBeforeUpdate, ref } from "vue";
import ShowList from "../components/ShowList.vue";
import { useRoute } from "vue-router";
const route = useRoute();

const eventLists = ref({content:null});
const id = ref();
const page = ref(0)
const numPage = ref( )
const getLinkAll = async () => {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/category/${route.query.categoryId}?page=${page.value}&pageSize=6`);
  if (res.status === 200) {
    eventLists.value = await res.json();
      numPage.value = Math.ceil(eventLists.value.totalElements / 8)
  }
};

onBeforeUpdate(() => {

});

onBeforeMount(() => {
  getLinkAll();
});

// const yourISODateTime = computed(() => {
//   test1.value = new Date(yourDateTime.value).toISOString();
//  return new Date(yourDateTime.value).toISOString();
// });

const addEvent = async (dataBooking , AllDataCheck) => {
  if(AllDataCheck == true) {
 dataBooking.eventStartTime=new Date(dataBooking.eventStartTime).toISOString();
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(dataBooking),
  });
  
  
  }
 getLinkAll();
};

const categoryDetail = {
  categoryId: route.query.categoryId,
  categoryName: route.query.eventCategoryName,
  categoryDuration: route.query.eventDuration,
};



function paging(index , filter) {
  page.value = index;

  if (filter == 3) {
    getLinkAll();
  }
  if (filter == 2) {
    getLinkFuture();
  }
  if (filter == 1) {
    getLinkPast();
  }
}


const getLinkPast = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/category/past/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
};

const getLinkFuture = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/category/future/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
    console.log(eventLists.value);
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
    <div class="flex justify-between grid grid-cols-3 gap-2">
        <AddEvent  @addEvent="addEvent"  :categoryDetail="categoryDetail"/>

  <!-- <AddEvent :id="id" @addEvent="addEvent" @click="getLinkAll" :categoryDetail="categoryDetail"/> -->
      <ShowList
        :eventLists="eventLists.content"
        colNum="grid-cols-3"
        class="col-span-2" :numPage = "numPage" @paging="paging"  @pastFilter="pastFilter"
      @futureFilter="futureFilter"
      @allFilter="allFilter"
      />
    </div>
  </div>
</template>
 
<style>
</style>