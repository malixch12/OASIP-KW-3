<script setup>
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
});
</script>
 
<template>
  <div>
    <div
      v-if="props.showDiv"
      class="border-2 border-black-600  ring-1 ring-red-900/5  sm:rounded-lg  p-6"
    >
      <p>
        <span class="font-bold">Booking Name</span>
        {{ eventLists[number].bookingName }}
      </p>
      
      <p><span class="font-bold">Email</span> {{ eventLists[number].bookingEmail }}</p>

      <p>
       <span class="font-bold"> วันที่นัด </span>{{ eventLists[number].eventStartTime }} 
       <span class="font-bold">ระยะเวลา</span>
        {{ eventLists[number].eventDuration }} <span class="font-bold">นาที</span>
      </p>
      <p class="font-bold">รายละเอียดถึงผู้จัด</p>
      <p>{{ eventLists[number].eventNotes }}</p>
    </div>
  </div>
</template>
 
<style>
</style>