<script setup>
import { ref, onBeforeMount } from "vue";

const props = defineProps({
  Category: {
    type: String,
    require: false,
  },
});

const check = ref(true);
const eventLists = ref();
const getLink = async () => {
  const res = await fetch(`http://localhost:8080/api/events`);
  if (res.status === 200) {
    eventLists.value = await res.json();
    console.log(eventLists.value);
    if (eventLists.value.length == 0) {
      check.value = false;
    }
  }
};
getLink();

console.log;
</script>

<template>
  <div>
    <ul v-if="check">
      <li v-for="(event, index) in eventLists" :key="index">
        <h1 class="text-3xl font-bold">{{ event.eventCategory }}</h1>
        Booking Name : {{ event.bookingName }} <br />
        Email : {{ event.bookingEmail }} <br />

        Date : {{ event.eventStartTime }} <br />
        Booking period : {{ event.eventDuration }} นาที <br />
        Details : {{ event.eventNotes }} <br />
        test : {{ event.eventCategoryID }}
        <hr />

        <br />
      </li>
    </ul>

    <div v-if="check == false">Empty schedule ! ! !</div>
  </div>
</template>

<style></style>
