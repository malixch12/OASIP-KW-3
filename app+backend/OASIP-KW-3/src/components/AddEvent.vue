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
});

defineEmits(["addEvent"]);

onBeforeUpdate(() => {

//dataBooking.value.bookingId = props.id + 1;

 CheckData ()
});

function CheckData () {
  //check date and time
  if ((countTime.value > new Date(dataBooking.value.eventStartTime))  ){

    DateTimeCheck.value = true
 }else{
  DateTimeCheck.value = false
  }

  //check name
  if(dataBooking.value.bookingName != "") {
    NameCheck.value =true
  }else {
        NameCheck.value =false

  }

   if(dataBooking.value.bookingEmail != "") {
    EmailCheck.value =true
   }else{EmailCheck.value =false}


    // Check Email validateEmail
   if(validateEmail(dataBooking.value.bookingEmail)==true) {
       EmailValidation.value = true
   }else {
            EmailValidation.value = false
   }
   // All check
  if(NameCheck.value==true && EmailCheck.value==true && DateTimeCheck.value ==false && EmailValidation.value == true ) {
    AllDataCheck.value = true
  }else {
    AllDataCheck.value = false

  }
}


function validateEmail(email) 
    {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }


const AllDataCheck = ref(false)
const DateTimeCheck = ref(false)
const NameCheck = ref(false)
const EmailCheck =ref(false)
const EmailValidation = ref(false)
const dataBooking = ref({
  bookingId: "",
  bookingName: "",
  bookingEmail: "",
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
  if(AllDataCheck.value = true) {
  dataBooking.value.bookingId = "";
  dataBooking.value.bookingName = '';
  dataBooking.value.bookingEmail = '';
  dataBooking.value.eventStartTime = null;
  dataBooking.value.eventNotes = "";
  }
};

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
        bg-color="bg-slate-400 text-sm text-white"
        button-name="<< go back"
        @click="goBack"
      />
      
      <h2 class="text-2xl font-semibold text-center">
         Booking {{ categoryDetail.categoryName }}
      </h2>
     
        Name :
        
        <input
        maxlength="100"
          type="text"
          v-model="dataBooking.bookingName"
          placeholder="Please enter your name"
          class="border-2 pl-2 border-sky-200 w-8/12 rounded-lg col-span-5" 
        />  
<br>

            <span class=" font-bold text-gray-600 text-xs"> *คุณสามารถกรอกได้เพียง 100 ตัวอักษร</span> <span  class=" font-bold text-gray-600 text-xs">--> เหลืออีก {{100-dataBooking.bookingName.length}} ตัว</span><br>
      <span class=" font-bold text-red-600 text-xs" v-if="dataBooking.bookingName.length==100"> ตอนนี้คุณไม่สามารถเพิ่มข้อความได้มากกว่านี้ กรุณาตรวจความถูกต้องของข้อความทั้งหมด</span>
       <span v-show="!NameCheck"  class="text-red-600"> กรุณาใส่ชื่อ</span>
      <p>
        Email :
        <input
          type="text"
          v-model="dataBooking.bookingEmail"
          placeholder="Please enter your email"
          class="pl-2 border-2 border-sky-200 w-8/12 rounded-lg" maxlength="100"
        />
      </p>
    <span v-show="!EmailCheck"  class="text-red-600"> กรุณาใส่อีเมล</span><br>
    <span v-show="!EmailValidation"  class="text-red-600"> กรุณากรอกอีเมลล์ให้ถูกต้อง</span>
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
      </p>
      <div v-show="DateTimeCheck" class="text-red-600"> กรุณาเลือกวันที่ในอนาคตเท่านั้น </div>
      <p>Duration {{ categoryDetail.categoryDuration }} minutes</p>
      <p>Message to Advisor</p>
      <span class=" font-bold text-gray-600 text-xs"> *คุณสามารถกรอกได้เพียง 500 ตัวอักษร</span> <span  class=" font-bold text-gray-600 text-xs">--> เหลืออีก {{500-dataBooking.eventNotes.length}} ตัว</span><br>
      <span class=" font-bold text-red-600 text-xs" v-if="dataBooking.eventNotes.length==500"> ตอนนี้คุณไม่สามารถเพิ่มข้อความได้มากกว่านี้ กรุณาตรวจความถูกต้องของข้อความทั้งหมด</span>
      <textarea
       maxlength="500"
        type="text"
        v-model="dataBooking.eventNotes"
        class="border-2 border-sky-200 w-11/12 h-56 rounded-lg"
      ></textarea>

      <div class="grid grid-cols-1 place-items-center">
        <RoundButton
          bg-color="bg-emerald-400"
          button-name="add"
          @click="$emit('addEvent', dataBooking , AllDataCheck ), reSet()"
        />
     
        <!-- <RoundButton
          bg-color="bg-rose-400"
          button-name="cancel"
          @click="cancelBooking"
        /> -->
      </div>

      <!-- popup -->
      <!-- <PopupPage v-show="isActivePopup" :dim-background="true">
        <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-16">
          Do you want to add?
        </p>
        <div class="flex justify-between max-w-lg mx-auto">
          <RoundButton
            bg-color="bg-emerald-400"
            button-name="Yes"
            @click="$emit('addEvent', dataBooking , DateTimeCheck ), reSet(),isActivePopup = false"
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
      </PopupPage> -->
    </div>




  </div>
</template>

<style></style>
