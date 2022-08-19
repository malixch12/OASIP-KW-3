<script setup>
import { ref , onBeforeMount } from "vue";

function push() {
  router.push({
    name: "ShowList",
    query: { categoryId: categoryId },
  });
}

const countTime = ref('');
function setTime() {
var today = new Date()
var now_date = (today.getDate() + '/' + (today.getMonth()+1) + '/' + (today.getFullYear()+543 ));
var now_time = (today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()).toString("th-TH")
  countTime.value = now_date + " - " + now_time
}

onBeforeMount(async () => {
setInterval(setTime, 1);
});
</script>
 
<template>
  <nav class="">
    <div class="container flex flex-wrap justify-between items-center pr-20 pl-12 py-5">
      <router-link :to="{ name: 'Home' }" class="flex items-center">
        <div class="text-sky-500 "> Clinic Booking</div>
      </router-link>

      <div class="w-full md:block md:w-auto">
        <ul
          class="
            flex flex-col
            md:flex-row md:space-x-8 md:mt-0 text-base md:font-medium
          "
        >
          <li>
            <router-link
              :to="{ name: 'Home' }"
              class="
                block

                text-gray-700
                md:hover:text-pink-500 md:p-0
              "
              >Home</router-link
            >
          </li>
          <li>
            <router-link
              :to="{ name: 'BookingEventByCate' }"
              class="
                 block
                text-gray-700
                
                md:hover:text-pink-500 md:p-0
              "
              >Booking</router-link
            >
          </li>

          <li>
            <router-link
              :to="{ name: 'ListAllByCate' }"
              class="
                 block
                text-gray-700
               
                md:hover:text-pink-500 md:p-0 
              "
              >Check Appointments
            </router-link>
          </li>
          <li>
            <span
              :to="{  }"
              class="
              text-gray-400 
              "
              >{{countTime}}</span
            >
          </li>
        </ul>
      </div>
    </div>

  </nav>
</template>
 
<style scoped>
.router-link-active {
  color: rgb(93, 176, 209);
  /* border-color: #0a0e0b; */
}
</style>