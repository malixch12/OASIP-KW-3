<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";

const router = useRouter();
const myRouter = useRoute();
const eventLists = ref({
  bookingId: "",
  bookingName: null,
  bookingEmail: null,
  eventCategory: null,
  eventStartTime: null,
  eventDuration: null,
  eventNotes: "",
  eventCategoryID: null,
});

const getLinkAll = async () => {
  const res = await fetch(
    `http://localhost:8080/api/events/${myRouter.query.BookingId}`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
  }
};

onBeforeMount(async () => {
  getLinkAll();
});

let text;
const removeEvent = async () => {
  
if (confirm("Would you like to cancel your appointment?") == true) {

   const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/${myRouter.query.BookingId}`,
    {
      method: "DELETE",
    }
  );
  router.go(-1);
} else {
  
}
 
};

const goBack = () => router.go(-1);

const goAboutUs = () => appRouter.push({ name: "About" });
</script>
 
<template>
  <div class="flex justify-center">
    <div class="bg-white space-y-7  shadow-xl rounded-lg ml-48 mr-48 p-12 w-2/5">
      <RoundButton  bg-color="bg-slate-400 text-sm" button-name="<< go back" @click="goBack" />
       
      <div class="col-span-1 grid grid-cols-1 place-items-center">
         
      <div class="space-y-5">
        <p class="text-3xl font-bold text-rose-400">{{ eventLists.eventCategory}}</p>
           <p>
            <span class="font-bold text-slate-600">Booking Name : </span>
            {{ eventLists.bookingName }}
          </p>

          <p>
            <span class="text-slate-600 font-bold">Email : </span>
            {{ eventLists.bookingEmail }}
          </p>

          <p>
            <span class="text-slate-600 font-bold"> Date : </span>
            {{ eventLists.eventDate }}
          </p>

          <p>
            <span class="text-slate-600 font-bold"> Time : </span>
            {{ eventLists.eventTime }}
            </p>
            <p>
              <span class="text-slate-600 font-bold">Duration </span>
            {{ eventLists.eventDuration }}
            <span class="text-slate-600 font-bold">Minutes</span>
            </p>
   
          <p class="text-slate-600 font-bold">Message to Advisor </p>
          <p>{{ eventLists.eventNotes }}</p>
            <div class="grid grid-cols-2  pt-3">
      <RoundButton bg-color="bg-emerald-400" button-name="edit" />
      <RoundButton bg-color="bg-rose-400" button-name="delete" @click="removeEvent" />
      </div>
       </div >
     
      </div>
        
     
    </div>
  </div>
</template>
 
<style>
</style>