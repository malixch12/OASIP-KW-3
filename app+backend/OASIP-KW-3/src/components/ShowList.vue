<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";

const router = useRouter();
const props = defineProps({
  eventLists: {
    type: Array,
  },
  colNum:{
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
const style = "flex justify-between grid gap-4"
</script>
 
<template>
  <div>
    <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
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
            <h1 class="text-1xl font-bold">{{ event.eventCategory }}</h1>
            <p >Name : {{ event.bookingName }}</p>
            <p>Date : {{ event.eventDate }}</p>
            <p>Time : {{ event.eventTime }}</p>
            <RoundButton bg-color="bg-rose-300" button-name="Show detail ->>" @click="showDet(event.bookingId)" />
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