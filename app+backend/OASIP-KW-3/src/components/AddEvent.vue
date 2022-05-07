<script setup>
import RoundButton from "../components/RoundButton.vue";
import ErrorForm from "../components/ErrorForm.vue";
import { useRouter, useRoute } from "vue-router";
import { ref, onBeforeMount, onBeforeUpdate } from "vue";

const goBack = () => appRouter.go(-1)

const props = defineProps({
  id: {
    type: Number,
    default:0
  },
});
defineEmits(["addEvent"]);

onBeforeUpdate(() => {
  console.log(props.id + ' id')
  dataBooking.value.bookingId = props.id + 1;
  
  
});

//
const appRouter = useRouter();

const dataBooking = ref({
  bookingId: "",
  bookingName: null,
  bookingEmail: null,
  eventCategory: "DevOps/Infra Clinic",
  eventStartTime: null,
  eventDuration: null,
  eventNotes: "",
  eventCategoryID: 2,
});

const cancelBooking = () => {
  appRouter.push({ name: "Home" });
};

console.log(dataBooking.value);

</script>
 
<template>
  <div>
    <div class="space-y-7 bg-white shadow-xl rounded-lg ml-24 p-10">
      <RoundButton
          bg-color="bg-slate-400 text-sm"
          button-name="<< go back"
          @click="goBack"
        />
      <h2 class="text-2xl font-semibold text-center ">Information for booking clinic</h2>
      <p>
        Name :
        <input
          type="text"
          v-model="dataBooking.bookingName"
          class="border-2 pl-2 border-sky-200 w-8/12 rounded-lg"
        />
      </p>
      <p>
        Email :
        <input
          type="text"
          v-model="dataBooking.bookingEmail"
          class="pl-2 border-2 border-sky-200 w-8/12 rounded-lg"
        />
      </p>
      <hr />
      <p class="text-2xl font-semibold text-center">Date and Time for booking clinic</p>
      <p>
        DateTime :
        <input
          type="datetime-local"
          v-model="dataBooking.eventStartTime"
          class="border-2 border-sky-200 w-9/12 rounded-lg"
        />
      </p>
      <p>
        Duration :
        <input
          type="number"
          v-model="dataBooking.eventDuration"
          class="pl-2 border-2 border-sky-200 w-16 rounded-lg"
        />
        minutes
      </p>
      <p class="text-xs text-red-600">
        * Duration for booking can not exceed 30 minutes.*
      </p>
      <p>Message to Advisor</p>
        <textarea
        type="text"
        v-model="dataBooking.eventNotes"
        class="border-2 border-sky-200 w-11/12 h-56 rounded-lg"
      ></textarea>
    

      <div class="grid grid-cols-2 place-items-center">
        <RoundButton
          bg-color="bg-emerald-400"
          button-name="add"
          @click="$emit('addEvent', dataBooking)"
        />
        <RoundButton
          bg-color="bg-rose-400"
          button-name="cancel"
          @click="cancelBooking"
        />
      </div>
    </div>
  </div>
</template>
 
<style>
</style>