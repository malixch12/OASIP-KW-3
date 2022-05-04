<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from 'vue'
import { useRouter } from 'vue-router'
import ShowDetail from '../components/ShowDetails.vue'
const props = defineProps({
  eventLists: {
    type: Array,
    default: []
  }
})


const check = ref(true)

const send = ref(false)
onBeforeUpdate(async () => {
  if (props.eventLists.length == 0) {
    check.value = false
  }
});

const index = ref();
const sendIndex = (num) => {
  index.value = num
  send.value = true
}


</script>
 
<template>
  <div>

    <div v-if="check" class="flex justify-between grid grid-cols-5 gap-5 ">
      <div v-for="(event, index) in props.eventLists" :key="index">
        <div class="bg-red-100 mt-4 shadow-xl ring-1 ring-red-900/5 sm:rounded-lg p-6" @click="sendIndex(index)">

          <!-- <h1 class="text-1xl font-bold mb-4 mt-4">{{ event.eventCategory }}</h1> -->
          <p class="text-2xl font-bold">{{ event.bookingName }} </p>
          <p> {{ event.eventStartTime }} </p>
        </div>

        <br />
      </div>
    </div>


    <div v-if="check == false">Empty schedule ! ! !</div>


    <ShowDetail :eventLists="props.eventLists" :number="index" :showDiv="send" />

  </div>
</template>
 
<style>
body {
  font-size: 20px;
}
</style>