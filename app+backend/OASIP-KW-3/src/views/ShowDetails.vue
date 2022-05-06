<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import RoundButton from "../components/RoundButton.vue";
import { useRouter } from "vue-router";
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

const removeEvent = async () => {
  const res = await fetch(
    `http://localhost:8080/events/${myRouter.query.BookingId}`,
    {
      method: "DELETE",
    }
  );
router.go(-1)
};

const goBack = () => router.go(-1)

const goAboutUs = () => appRouter.push({name:'About'})
</script>
 
<template>
  <div>
    <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
         <RoundButton bg-color="bg-black" button-name="ย้อนกลับจ้าพี่" @click="goBack" />
      <div
        class="border-2 border-rose-200 ring-1 ring-red-900/5 sm:rounded-lg p-6"
      >
        <p>
          <span class="font-bold">Booking Name : </span>
          {{ eventLists.bookingName }}
        </p>

        <p>
          <span class="font-bold">Email : </span>
          {{ eventLists.bookingEmail }}
        </p>

        <p>
          <span class="font-bold"> Date : </span>{{ eventLists.eventDate }}
          <span class="font-bold"> Time : </span>{{ eventLists.eventTime }}
          <span class="font-bold">Duration : </span>
          {{ eventLists.eventDuration }}
          <span class="font-bold">Minutes</span>
        </p>
        <p class="font-bold">Message to Advisor :</p>
        <p>{{ eventLists.eventNotes }}</p>
      </div>
      <div class="grid grid-cols-2 mt-5 w-96 ml-56">
        <RoundButton bg-color="bg-emerald-400" button-name="edit" />
        <RoundButton
          bg-color="bg-rose-400"
          button-name="delete"
          @click="removeEvent"
        />
      </div>
    </div>
  </div>
</template>
 
<style>
</style>