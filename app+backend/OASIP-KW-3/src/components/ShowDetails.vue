<script setup>
import RoundButton from "../components/RoundButton.vue";
const props = defineProps({
  eventLists: {
    type: Array,
    default: [],
  },
  number: {
    type: Number,
    default: 0,
  },
  showDiv: {
    type: Boolean,
    default: false,
  },
    id: {
    type: Number,
    default: 0,
  }
});

  const removeEvent = async (EventId) => {
    const res = await fetch(`http://localhost:8080/events/${EventId}`, {
    method: 'DELETE'
  })
  }
</script>

 
<template>
  <div>
    <div  v-if="props.showDiv">
      <div class="border-2 border-rose-200 ring-1 ring-red-900/5 sm:rounded-lg p-6">
        <p>
          <span class="font-bold">Booking Name : </span>
          {{ eventLists[number].bookingName }}
        </p>

        <p>
          <span class="font-bold">Email : </span>
          {{ eventLists[number].bookingEmail }}
        </p>

        <p>
          <span class="font-bold"> Date : </span
          >{{ eventLists[number].eventDate }}  
          <span class="font-bold"> Time : </span
          >{{ eventLists[number].eventTime }}
          <span class="font-bold">Duration : </span>
          {{ eventLists[number].eventDuration }}
          <span class="font-bold">Minutes</span>
        </p>
        <p class="font-bold">Message to Advisor : </p>
        <p>{{ eventLists[number].eventNotes }}</p>
      </div>
      <div class="grid grid-cols-2 mt-5 w-96 ml-56">
        <RoundButton bg-color="bg-emerald-400" button-name="edit" @click="vfgth" />
        <RoundButton
          bg-color="bg-rose-400"
          button-name="delete"
          @click="removeEvent(id)"
        />
      </div>
    </div>
  </div>
</template>
 
<style>
</style>