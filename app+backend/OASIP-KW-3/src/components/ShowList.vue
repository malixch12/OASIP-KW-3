<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps({
  eventLists: {
    type: Array,
   
  },
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

// onBeforeMount(async () => {
//   // console.log(props.eventLists.length)
//   if (props.eventLists.length == 0) {
//     check.value = false;
//   }
// });

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

// function DeleteEvent () {
// //  props.eventLists = props.eventLists.filter((i) => i.bookingId != SendBookId.value)
// //  console.log(SendBookId.value)
// //  console.log(props.eventLists)
  
//   props.eventLists.splice( index.value, 1);

// }

// if (res.status === 200) {
//     //ทำการลบที่หน้าเว็บด้วย ถ้าไม่เขียนจะลบแค่ที่ backend แต่ front ไม่ลบตาม
//     notes.value = notes.value.filter((note) => note.id != deleteNoteId)
//     console.log('delete successfully')
//   } else console.log('error, cannot delete')


const showDet = (BookingId) => {
  router.push({
    name: "ShowDetails",
    query: { BookingId: BookingId }
  });
};

</script>
 
<template>
  <div>
    <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
      <div v-if="check" class="flex justify-between grid grid-cols-4 gap-4">
        <div v-for="(event, index) in props.eventLists" :key="index">
          <div
            class="
              bg-red-100
              shadow-xl
              ring-1 ring-red-900/5
              sm:rounded-lg
              mt-4
              p-6
            "
            @click="showDet(event.bookingId), scrollWin()"
          >
            <h1 class="text-1xl font-bold mb-4 mt-4">{{ event.eventCategory }}</h1>
            <p class="text-1xl font-bold">Name : {{ event.bookingName }}</p>
            <p>Date : {{ event.eventDate }}</p>
            <p>Time : {{ event.eventTime }}</p>
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