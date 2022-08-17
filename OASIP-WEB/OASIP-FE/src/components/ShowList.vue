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
    default: "All Appointments"
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
    <div class="ml-24 mr-24 p-12 grid grid-cols-1 ">
      <h1 class="text-4xl pb-5 text-center font-bold">{{ name }}</h1>

      <!-- filter  -->
      <div class="flex justify-center py-5">
        <div>
          <span>Event Status : </span>
          <RoundButton class="pr-2 "
            bg-color="bg-slate-100 hover:bg-slate-600 hover:text-slate-100 active:bg-slate-700 focus:outline-none focus:ring focus:ring-slate-200 focus:text-slate-100 focus:bg-slate-600 text-slate-600"
            button-name="All" @click="$emit('allFilter'), filter = 3" />
          <RoundButton class="pr-2"
            bg-color="bg-slate-100 hover:bg-slate-600 hover:text-slate-100 active:bg-slate-700 focus:outline-none focus:ring focus:ring-slate-200 focus:text-slate-100 focus:bg-slate-600 text-slate-600"
            button-name="Past" @click="
              $emit('pastFilter', filter = 1)
            " />
          <RoundButton
            bg-color="bg-slate-100 hover:bg-slate-600 hover:text-slate-100 active:bg-slate-700 focus:outline-none focus:ring focus:ring-slate-200 focus:text-slate-100 focus:bg-slate-600 text-slate-600"
            button-name="Upcoming" @click="
              $emit('futureFilter', filter = 2)
            " />

          <span class="text-slate-400 px-3">|</span>
          <span> Select Date : </span>
          <span>
            <input type="date" class="border-2 px-2 border-slate-200 w-82 rounded-full" v-model="FilterDate" />
            <RoundButton bg-color="bg-red-400  ml-2 text-white hover:bg-red-600" button-name="search"
              @click="$emit('dateFilter', FilterDate)" />
          </span>
        </div>
      </div>

      <!-- event list -->
      <div v-if="check" :class="[style, colNum]">
        <div v-for="(event, index) in props.eventLists" :key="index">
          <div className="rounded-xl  mx-auto bg-gradient-to-r p-[3px] from-[#94ade1]  to-[#94ade1] ">
            <div
              className="flex flex-col justify-between h-full bg-neutral-100 text-black rounded-lg p-4 ">
                
              <p class=" drop-shadow-2xl  font-semibold  w-auto pr-2 mr-2 rounded-lg text-orange-900" v-if="event.eventCategoryID==3">{{ event.eventCategory }}</p>
              <p class=" drop-shadow-2xl  font-semibold w-auto  pr-2  mr-2 rounded-lg text-sky-700" v-if="event.eventCategoryID==2">{{ event.eventCategory }}</p>
              <p class=" drop-shadow-2xl  font-semibold  w-auto  pr-2  mr-2 rounded-lg text-rose-500" v-if="event.eventCategoryID==6">{{ event.eventCategory }}</p>
              <p class=" drop-shadow-2xl  font-semibold  w-auto  pr-2 mr-2 rounded-lg text-green-600" v-if="event.eventCategoryID==5">{{ event.eventCategory }}</p>
              <p class="drop-shadow-2xl  font-semibold   w-auto  pr-2  mr-2 rounded-lg text-violet-400" v-if="event.eventCategoryID==1">{{ event.eventCategory }}</p>
              <p class="drop-shadow-2xl font-semibold  w-auto  pr-2  mr-2 rounded-lg text-gray-700" v-if="event.eventCategoryID==4">{{ event.eventCategory }}</p>

              <p>Name : {{ event.bookingName }}</p>
              <p>
                Date :
                {{ new Date(event.eventStartTime).toLocaleDateString("th-TH") }}
              </p>
              <p class="pb-6 text-rose-600 font-semibold">
                
                {{ new Date(event.eventStartTime).toLocaleTimeString("th-TH") }} - {{new Date(event.endTime).toLocaleTimeString("th-TH")}}
              </p>

              <RoundButton
                bg-color="bg-rose-400 text-white transition ease-in-out delay-75  hover:-translate-y-1 hover:scale-110  duration-150"
                button-name="Show detail ->" @click="showDet(event.bookingId)" />

            </div>
          </div>
          <br />

        </div>

      </div>
      <div v-if="check == false" class="text-slate-400 text-center">
        Empty schedule ! ! !
      </div>
    </div>

    <!-- page -->
    <div class="  rounded-b-lg p-8 ml-24 mr-24 text-center">
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
        <ul class="inline-flex -space-x-px" v-for="(e, index) in numPage" :key="index">

          <button @click="$emit('paging', index, filter)"
            class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 focus:bg-gray-200 focus:text-red-600 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">{{ index + 1 }}</button>

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
