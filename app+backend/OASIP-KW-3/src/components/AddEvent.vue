<script setup>
import RoundButton from "../components/RoundButton.vue";
import ErrorForm from "../components/ErrorForm.vue";
import { useRouter, useRoute } from "vue-router";
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import PopupPage from "../components/PopupPage.vue"

const goBack = () => appRouter.go(-1);

const props = defineProps({
  id: {
    type: Number,
    default: 0,
  },
  categoryDetail: {
    type: Object,
  },
});

defineEmits(["addEvent"]);

onBeforeUpdate(() => {

dataBooking.value.bookingId = props.id + 1;

 if ((countTime.value > new Date(dataBooking.value.eventStartTime))  ){

textTest.value = true
}else{
textTest.value = false
 }
});

const textTest = ref(false)
const appRouter = useRouter();

const dataBooking = ref({
  bookingId: "",
  bookingName: null,
  bookingEmail: null,
  eventCategory: props.categoryDetail.categoryName,
  eventStartTime: null,
  eventDuration: props.categoryDetail.categoryDuration,
  eventNotes: "",
  eventCategoryID: props.categoryDetail.categoryId,
});




const cancelBooking = () => {
  appRouter.push({ name: "Home" });
};

const reSet = () => {
  dataBooking.value.bookingId = "";
  dataBooking.value.bookingName = null;
  dataBooking.value.bookingEmail = null;
  dataBooking.value.eventStartTime = null;
  dataBooking.value.eventNotes = "";
};

console.log(dataBooking.value);




const isActivePopup = ref(false)


const countTime = ref('');
function setTime() {
var today = new Date()
var now_date = (today.getDate() + '/' + (today.getMonth()+1) + '/' + (today.getFullYear()+543 ));
var now_time = (today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()).toString("th-TH")

  countTime.value = today

}
setInterval(setTime, 1000);




</script>

<template>
  <div>
    <div class="space-y-7 bg-white shadow-xl rounded-lg ml-24 p-10">
      <RoundButton
        bg-color="bg-slate-400 text-sm"
        button-name="<< go back"
        @click="goBack"
      />
      <h2 class="text-2xl font-semibold text-center">
        Information for booking {{ categoryDetail.categoryName }}
      </h2>
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
      <p class="text-2xl font-semibold text-center">
        Date and Time for Booking
      </p>
      <p>
       Date Time :
        <input
          type="datetime-local"
          v-model="dataBooking.eventStartTime"
          class="border-2 border-sky-200 w-9/12 rounded-lg"
        />
        {{new Date(dataBooking.eventStartTime)}}<br>
        <br>
       
        <br>
         time now--> {{countTime}}
         
      </p>
      <div v-show="textTest" class="text-red-600"> กรุณาเลือกวันที่ในอนาคตเท่านั้น </div>
      <p>Duration {{ categoryDetail.categoryDuration }} minutes</p>
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
          @click="()=>isActivePopup=true"
        />
        <RoundButton
          bg-color="bg-rose-400"
          button-name="cancel"
          @click="cancelBooking"
        />
      </div>

      <!-- popup -->
      <PopupPage v-show="isActivePopup" :dim-background="true">
        <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-16">
          Do you want to add?
        </p>
        <div class="flex justify-between max-w-lg mx-auto">
          <RoundButton
            bg-color="bg-emerald-400"
            button-name="Yes"
            @click="$emit('addEvent', dataBooking , textTest ), reSet(),isActivePopup = false"
          />

          <RoundButton
            bg-color="bg-rose-400"
            button-name="No"
            @click="
              () => {
                isActivePopup = false;
              }
            "
          />

          
        </div>
      </PopupPage>
    </div>




  </div>
</template>

<style></style>
