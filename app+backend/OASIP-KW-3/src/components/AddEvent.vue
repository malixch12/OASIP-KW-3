<script setup>
import RoundButton from "../components/RoundButton.vue";
import { useRouter, useRoute } from "vue-router";
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import PopupPage from "../components/PopupPage.vue"
const appRouter = useRouter();
const goBack = () => appRouter.go(-1);

const props = defineProps({
  id: {
    type: Number,
    default: 0,
  },
  categoryDetail: {
    type: Object,
  },
  CheckOverlap : {
    type: Boolean,
    default: true
  }
});

defineEmits(["addEvent"]);

onBeforeUpdate(() => {
  CheckData()
});

function CheckData() {
  //check date and time
  if ((countTime.value > new Date(dataBooking.value.eventStartTime))) {
    DateTimeCheck.value = true
  } else {
    DateTimeCheck.value = false
  }
  //check name
  if (dataBooking.value.bookingName != "") {
    NameCheck.value = true
  } else {
    NameCheck.value = false
  }

  if (dataBooking.value.bookingEmail != "") {
    EmailCheck.value = true
  } else { EmailCheck.value = false }

  // Check Email validateEmail
  if (validateEmail(dataBooking.value.bookingEmail) == true) {
    EmailValidation.value = true
  } else {
    EmailValidation.value = false
  }

  // All check
  if (NameCheck.value == true && EmailCheck.value == true && DateTimeCheck.value == false && EmailValidation.value == true ) {
    AllDataCheck.value = true
  } else {
    AllDataCheck.value = false



  }
}


function validateEmail(email) {
  var re =  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}


const AllDataCheck = ref(false)
const DateTimeCheck = ref(false)    
const NameCheck = ref(false)     //เซ็คว่ากรอกรึยัง
const EmailCheck = ref(false)   //เซ็คว่ากรอกรึยัง
const EmailValidation = ref(false)  //ฟอแมท เมล
const dataBooking = ref({    //สำหรับให้ ฟอม v-model
  bookingId: "",
  bookingName: "",
  bookingEmail: "",
  eventCategory: props.categoryDetail.categoryName,
  eventStartTime: null,
  eventDuration: props.categoryDetail.categoryDuration,
  eventNotes: "",
  eventCategoryID: props.categoryDetail.categoryId,
});



const reSet = () => {
  if (AllDataCheck.value == true && props.CheckOverlap == false) {
    dataBooking.value.bookingId = "";
    dataBooking.value.bookingName = '';
    dataBooking.value.bookingEmail = '';
    dataBooking.value.eventStartTime = null;
    dataBooking.value.eventNotes = "";
  }
};

const countTime = ref('');
const currentTime = ref('')
function setTime() {
  var today = new Date()
  var now_date = (today.getDate() + '/' + (today.getMonth() + 1) + '/' + (today.getFullYear() + 543));
  var now_time = (today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()).toString("th-TH")
  countTime.value = today
  currentTime.value = now_date + " - " + now_time
}
setInterval(setTime, 1);

const isActivePopup = ref(false)
function eror() {
  if (!AllDataCheck.value) { isActivePopup.value = true }
}

</script>

<template>
  <div>
    <div class="space-y-7 bg-white shadow-xl rounded-lg ml-24 p-10">
      <RoundButton bg-color="bg-slate-400 text-sm text-white" button-name="<< go back" @click="goBack" />


      <h2 class="text-2xl font-semibold text-center ">
        Booking {{ categoryDetail.categoryName }}
      </h2>
      <span class="flex justify-center text-sm"> current time : {{ currentTime }} </span>
      <span class="after:content-['*'] after:ml-0.5 after:text-red-500"> Name
      </span> :
      <input maxlength="100" type="text" v-model="dataBooking.bookingName" placeholder="Please enter your name"
        class="border-2 pl-2 border-sky-200 w-8/12 rounded-lg col-span-5" />
      <br>

      <span class=" font-bold text-gray-600 text-xs"> *คุณสามารถกรอกได้เพียง 100 ตัวอักษร</span> <span
        class=" font-bold text-gray-600 text-xs">--> เหลืออีก {{ 100 - dataBooking.bookingName.length }} ตัว

        <details class="" v-if="!NameCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!NameCheck" class="text-red-600"> กรุณาใส่ชื่อ</span>
          </div>
        </details>
      </span>
      <div class=" font-bold text-red-600 text-xs" v-if="dataBooking.bookingName.length == 100">
        ตอนนี้คุณไม่สามารถเพิ่มข้อความได้มากกว่านี้ กรุณาตรวจความถูกต้องของข้อความทั้งหมด</div>
      <div>
        <span class="after:content-['*'] after:ml-0.5 after:text-red-500"> Email
        </span> :

        <input type="text" v-model="dataBooking.bookingEmail" placeholder="Please enter your email"
          class="pl-2 border-2 border-sky-200 w-8/12 rounded-lg" maxlength="100" />
        <details class="" v-show="!EmailValidation || !EmailCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!EmailCheck" class="text-red-600"> กรุณาใส่อีเมล</span>
            <span v-show="!EmailValidation & EmailCheck" class="text-red-600"> กรุณากรอกอีเมลล์ให้ถูกต้อง</span>
          </div>
        </details>
      </div>


      <hr />
      <p class="text-2xl font-semibold text-center">
        Date and Time for Booking
      </p>
      <div>

        <span class="after:content-['*'] after:ml-0.5 after:text-red-500"> Date Time
        </span> :
        <input type="datetime-local" v-model="dataBooking.eventStartTime"
          class="border-2 border-sky-200 w-9/12 rounded-lg" />
        <details class="" v-show="DateTimeCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <div v-show="DateTimeCheck" class="text-red-600"> กรุณาเลือกวันที่ในอนาคตเท่านั้น </div>
          </div>
        </details>
      </div>






      <p>Duration {{ categoryDetail.categoryDuration }} minutes</p>
      <p>Message to Advisor</p>
      <span class=" font-bold text-gray-600 text-xs"> *คุณสามารถกรอกได้เพียง 500 ตัวอักษร</span> <span
        class=" font-bold text-gray-600 text-xs">--> เหลืออีก {{ 500 - dataBooking.eventNotes.length }} ตัว</span><br>
      <span class=" font-bold text-red-600 text-xs" v-if="dataBooking.eventNotes.length == 500">
        ตอนนี้คุณไม่สามารถเพิ่มข้อความได้มากกว่านี้ กรุณาตรวจความถูกต้องของข้อความทั้งหมด</span>
      <textarea maxlength="500" type="text" v-model="dataBooking.eventNotes"
        class="border-2 border-sky-200 w-11/12 h-56 rounded-lg"></textarea>
      <div class="grid grid-cols-1 place-items-center">
        <RoundButton bg-color="bg-emerald-400" button-name="add" @click="isActivePopup = true" />
        <br><span v-if="!AllDataCheck"
          class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400">
          **ยังไม่สามารถเพิ่มอีเว้นได้ กรุณาตรวจสอบข้อมูลของคุณให้ถูกต้อง </span>

        <!-- <RoundButton
          bg-color="bg-rose-400"
          button-name="cancel"
          @click="cancelBooking"
        /> -->
      </div>


      <PopupPage v-show="isActivePopup == true" :dim-background="true">
        <!-- ข้อมูลผิด -->

        <div v-if="!AllDataCheck">
          <div class="grid grid-cols-1 place-items-center text-slate-700 font-semibold text-center  p-10 space-y-5">
            <div>Can't add appointments. <br>
              Please check again.</div>
            <RoundButton bg-color="bg-gray-400" button-name="ok" @click="isActivePopup = false" />
          </div>
        </div>

        <div v-if="AllDataCheck ==true">
          <div class="text-slate-700 font-semibold text-center p-10 space-y-5">
            <div> Do you want to add appointment?</div>
            <div class="grid grid-cols-2 place-items-center">
            <RoundButton bg-color="bg-green-400 text-white " button-name="YES"
              @click="$emit('addEvent', dataBooking, AllDataCheck), isActivePopup = false" />
            <RoundButton bg-color="bg-red-400 text-white" button-name="NO" @click="isActivePopup = false" />
         </div>
         </div>
        </div>

           
      </PopupPage>



    </div>




  </div>
</template>

<style>
</style>
