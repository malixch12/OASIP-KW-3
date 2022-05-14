<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";

// const myRouter = useRoute();
const router = useRouter();
const props = defineProps({
  eventLists: {
    type: Array,
  },
  colNum:{
    type: String
  } ,
    name:{
    type: String
  } 
});

const check = ref(true);
const send = ref(false);
onBeforeUpdate(async () => {
  if (props.eventLists.length == 0) {
    check.value = false;
  }
   if (props.eventLists.length > 0) {
    check.value = true;
  }
});

const index = ref();
const SendBookId = ref();
const sendIndex = (num, id) => {
  index.value = num;
  SendBookId.value = id;
  send.value = true;
  console.log( index.value)
};

function scrollWin() {
  window.scrollTo(0, 1500);
}

const showDet = (BookingId) => {
  router.push({
    name: "ShowDetails",
    query: { BookingId: BookingId }
  });
};
const style = "flex justify-between grid gap-4"

</script>
 
<template>
  <div>
    <div class="bg-white shadow-xl  ml-24 mr-24 p-12">
      <h1 class="text-4xl pb-5 text-center font-bold">{{ name }}</h1>
      <div v-if="check" :class="[style,colNum]">
        <div v-for="(event, index) in props.eventLists" :key="index">
          <div
            class="
              bg-red-100
              shadow-xl
              ring-1 ring-red-900/5
              sm:rounded-lg
              mt-4
              p-6
              space-y-3
            "
          >
            
            <p>Name : {{ event.bookingName }}</p>
            <p>Date : {{ new Date(event.eventStartTime).toLocaleDateString("th-TH") }}</p>
            <p>Time : {{ new Date(event.eventStartTime).toLocaleTimeString("th-TH") }}</p>
            <p class="font-semibold">{{ event.eventCategory }}</p>
            <RoundButton bg-color="bg-rose-300" button-name="Show detail >>" @click="showDet(event.bookingId)" />
          </div>

          <br />
        </div>
      </div>

      <div v-if="check == false" class="text-slate-400 text-center">Empty schedule ! ! ! </div>


    </div>
  </div>
</template>
 
<style>
body {
  font-size: 20px;
}
</style>