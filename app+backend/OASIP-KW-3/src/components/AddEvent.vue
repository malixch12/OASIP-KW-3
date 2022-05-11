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
   categoryDetail: {
    type: Object
  
  }
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
  eventCategory: props.categoryDetail.categoryName,
  eventStartTime: null,
  eventDuration: props.categoryDetail.categoryDuration,
  eventNotes: "",
  eventCategoryID: props.categoryDetail.categoryId
});

const cancelBooking = () => {
  appRouter.push({ name: "Home" });
};

const reSet = () => {

    dataBooking.value.bookingId = ""
    dataBooking.value.bookingName = null
    dataBooking.value.bookingEmail = null
    dataBooking.value.eventStartTime = null   
    dataBooking.value.eventNotes = ""
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
      <h2 class="text-2xl font-semibold text-center ">Information for booking {{ categoryDetail.categoryName }}</h2>
      <p>
        Name :
        <input
          type="text"
          v-model="dataBooking.bookingName"
          placeholder="Please enter your name"
          class="border-2 pl-2 border-sky-200 w-8/12 rounded-lg"
        />
      </p>
      <p>
        Email :
        <input
          type="text"
          v-model="dataBooking.bookingEmail"
          placeholder="Please enter your email"
          class="pl-2 border-2 border-sky-200 w-8/12 rounded-lg"
        />
      </p>
      <hr />
      <p class="text-2xl font-semibold text-center">Date and Time for Booking</p>
      <p>
        DateTime :
        <input
          type="datetime-local"
          v-model="dataBooking.eventStartTime"
          class="border-2 border-sky-200 w-9/12 rounded-lg"
        />
      </p>
      <p>
        Duration {{ categoryDetail.categoryDuration }}  minutes
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
          @click="$emit('addEvent', dataBooking) , reSet()"
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