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
  colNum: {
    type: String,
  },
  name: {
    type: String,
    default : "All Appointments"
  },
  numPage: {
    type: Number,
  },
});
defineEmits(["paging"], ["pastFilter"], ["futureFilter"], ["allFilter"]);

const check = ref(true);
const send = ref(false);
onBeforeUpdate(async () => {
  if (props.eventLists.length == 0) {
    check.value = false;
  }
  if (props.eventLists.length > 0) {
    check.value = true;
  }

  //filter.value = 3
});

const page = ref();
const index = ref();
const SendBookId = ref();
const sendIndex = (num, id) => {
  index.value = num;
  SendBookId.value = id;
  send.value = true;
  console.log(index.value);
};

const showDet = (BookingId) => {
  router.push({
    name: "ShowDetails",
    query: { BookingId: BookingId },
  });
};
const style = "flex justify-between grid gap-4";

// filter
const filter = ref(3);
const test2 = ref("2022-05-04")
var today = ref(new Date("2022-05-04"));
var now_date =
  today.value.getDate() +
  "/" +
  (today.value.getMonth() + 1) +
  "/" +
  (today.value.getFullYear() + 543);


const PageIndexCurrent = ref(1)

</script>

<template>
  <div>
    <div class="bg-white shadow-xl ml-24 mr-24 p-12 grid grid-cols-1">
      <h1 class="text-4xl pb-5 text-center font-bold">{{ name }}</h1>

      <!-- filter  -->
      <div  class="flex justify-center py-5">
        <div>
          <span>Event Status : </span>
          <RoundButton
            class="pr-2 "
            bg-color="bg-slate-100 hover:bg-slate-600 hover:text-slate-100 active:bg-slate-700 focus:outline-none focus:ring focus:ring-slate-200 focus:text-slate-100 focus:bg-slate-600 text-slate-600"
            button-name="All"
            @click="$emit('allFilter')" 
          />
          <RoundButton
            class="pr-2"
            bg-color="bg-slate-100 hover:bg-slate-600 hover:text-slate-100 active:bg-slate-700 focus:outline-none focus:ring focus:ring-slate-200 focus:text-slate-100 focus:bg-slate-600 text-slate-600"
            button-name="Past"
            @click="
              $emit('pastFilter')
            "
          />
          <RoundButton
            bg-color="bg-slate-100 hover:bg-slate-600 hover:text-slate-100 active:bg-slate-700 focus:outline-none focus:ring focus:ring-slate-200 focus:text-slate-100 focus:bg-slate-600 text-slate-600"
            button-name="Upcoming"
            @click="
              $emit('futureFilter')
            "
          />
          <span class="text-slate-400 px-3">|</span>
          <span> Select Date : </span>
          <span>
            <input
              type="date"
              class="border-2 pl-2 border-slate-200 w-82 rounded-full"
              v-model="FilterDate"
            />
            <button @click="$emit('dateFilter',FilterDate)">ok</button>
          </span>
        </div>
      </div>

      <!-- event list -->
      <div v-if="check" :class="[style, colNum]">
        <div v-for="(event, index) in props.eventLists" :key="index">
          <div
            class="bg-rose-200 shadow-xl ring-1 ring-red-900/5 sm:rounded-lg mt-4 p-6 space-y-3 hover:bg-orange-300 "
          >
          <p class="font-semibold ">{{ event.eventCategory }}</p>
            <p>Name : {{ event.bookingName }}</p>
            <p>
              Date :
              {{ new Date(event.eventStartTime).toLocaleDateString("th-TH") }}
            </p>
            <p class="pb-6">
              Time :
              {{ new Date(event.eventStartTime).toLocaleTimeString("th-TH") }}
            </p>
            
            <RoundButton
              bg-color="bg-red-700 text-white"
              button-name="Show detail ->"
              @click="showDet(event.bookingId)"
            />
            
          </div>

          <br />
          
        </div>
         
      </div>
      <div v-if="check == false" class="text-slate-400 text-center">
        Empty schedule ! ! !
      </div>
    </div>

    <!-- page -->
     <div class="bg-white shadow-xl rounded-b-lg p-8 ml-24 mr-24 text-center">
      <!-- <span
        v-for="(e, index) in numPage"
        :key="index"
        class="p-5 text-white hover:text-orange-600"
      >
        <button @click="$emit('paging', index, filter)">
          {{ index + 1 }}
        </button>
      </span> --> 
       <nav aria-label="Page navigation example">
    <ul class="inline-flex -space-x-px" v-for="(e, index) in numPage"
        :key="index">   
           
      <button @click="$emit('paging', index, filter)" class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 focus:bg-gray-200 focus:text-red-600 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">{{index+1}}</button>
   
    </ul>
</nav>
</div>

    </div>
  
</template>

<style>
body {
  font-size: 20px;
}
</style>
