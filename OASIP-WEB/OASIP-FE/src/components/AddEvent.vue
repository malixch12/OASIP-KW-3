<script setup>
import RoundButton from "../components/RoundButton.vue";
import { useRouter, useRoute } from "vue-router";
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import PopupPage from "../components/PopupPage.vue"
import privew from "../components/PicPreview.vue"
import timeEvent from "../components/timeEvent.vue"

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
  CheckOverlap: {
    type: Boolean,
    default: true
  }
});

defineEmits(["addEvent"]);

onBeforeUpdate(() => {
  CheckData()
});
const UserRole = ref()
const UserEmail = ref()
onBeforeMount(() => {
  UserRole.value = localStorage.getItem('UserRole');
  if (UserRole.value != "Admin") {
    dataBooking.value.bookingEmail = localStorage.getItem('UserEmail');

  }



});
function CheckData() {
  //check date and time
  console.log(dataBooking.value.bookingName.length)
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
  if (NameCheck.value == true && EmailCheck.value == true && DateTimeCheck.value == false && EmailValidation.value == true) {
    AllDataCheck.value = true
  } else {
    AllDataCheck.value = false



  }
}


function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}


const AllDataCheck = ref(false)
const DateTimeCheck = ref(false)
const NameCheck = ref(true)     //เซ็คว่ากรอกรึยัง
const EmailCheck = ref(true)   //เซ็คว่ากรอกรึยัง
const EmailValidation = ref(true)  //ฟอแมท เมล
const dataFile = ref()
const dataBooking = ref({    //สำหรับให้ ฟอม v-model
  bookingId: "",
  bookingName: "",
  bookingEmail: "",
  eventCategory: props.categoryDetail.categoryName,
  eventStartTime: null,
  eventDuration: props.categoryDetail.categoryDuration,
  eventNotes: "",
  eventCategoryID: props.categoryDetail.categoryId,
  file: null
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

const oldFile = ref()
function CheckSizeFile(size) {
  var uploadField = document.getElementById("file");
  let photo = document.getElementById("file").files[0];

  if (event.target.files[0].size > (10485760)) {
    isActivePopup2.value = true
    if (dataBooking.value.file == null) {

    }
    if (dataBooking.value.file != null) {

    }

  } else
    dataBooking.value.file = event.target.files[0];




}

const isActivePopup2 = ref()

const preview = ref(null)
const image = ref(null)
const preview_list = ref([])

function previewImage(event) {
 // CheckSizeFile(event)

 if(event.target.files[0].size <= (10485760)) {
  dataBooking.value.file = event.target.files[0];

  var input = event.target;
     if (input.files) {
       var reader = new FileReader();
       reader.onload = (e) => {
         preview.value = e.target.result;
       }
       image.value=input.files[0];
       reader.readAsDataURL(input.files[0]);
     }

 }else
 isActivePopup2.value = true
 document.getElementById("dropzone-file").value = "";
 console.log("1-1")

     
   }
function reset() {
  image.value = null;
  preview.value = null;
  preview_list.value = [];
  document.getElementById("dropzone-file").value = "";
}

window.addEventListener("load", function() {
    var now = new Date();
    var utcString = now.toISOString().substring(0,19);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var day = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes()+1;
    var localDatetime = year + "-" +
                      (month < 10 ? "0" + month.toString() : month) + "-" +
                      (day < 10 ? "0" + day.toString() : day) + "T" +
                      (hour < 10 ? "0" + hour.toString() : hour) + ":" +
                      (minute < 10 ? "0" + minute.toString() : minute) 
    var datetimeField = document.getElementById("inputTime");
    datetimeField.value = localDatetime;
});


const timeCheck = ref(false)
</script>

<template>
  <div>
    <div class="space-y-7 bg-white shadow-xl rounded-lg md:ml-24 md:p-16 p-8  rounded md:w-auto w-full">
      <RoundButton bg-color="bg-slate-400 text-sm text-white" button-name="<< go back" @click="goBack" />


      <h2 class="px-4 text-3xl font-semibold text-center ">
        Booking {{ categoryDetail.categoryName }}
      </h2>
      <!-- <span class="flex justify-center text-sm"> current time {{ currentTime }} </span> -->

      <div>
        <span class="after:content-['*'] after:ml-0.5 after:text-red-500"> Name
        </span>
        <input maxlength="100" type="text" v-model="dataBooking.bookingName" placeholder="Please enter your name" class="
        my-3
        form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-white bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none" />

        <details class="" v-if="!NameCheck">
          <summary class="text-xs leading-6  font-semibold select-none text-red-300">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 ">
            <span v-show="!NameCheck" class="text-red-600"> กรุณาใส่ชื่อ</span>
          </div>
        </details>
      </div>
      <div class=" font-bold text-red-600 text-xs" v-if="dataBooking.bookingName.length == 100">
        ตอนนี้คุณไม่สามารถเพิ่มข้อความได้มากกว่านี้ กรุณาตรวจความถูกต้องของข้อความทั้งหมด</div>
      <div>
        <span class="after:content-['*'] after:ml-0.5 after:text-red-500"> Email
        </span> :


        <div class="relative">
  <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
    <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path><path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path></svg>
  </div>
  <input  v-if="UserRole == `Student`" type="text" v-model="dataBooking.bookingEmail"
          placeholder="Please enter your email"  id="email-address-icon" class="my-2
        form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-gray-200 bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
        my-3
        cursor-not-allowed
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none  pl-10 p-2.5 " disabled >
        <input v-else type="text" v-model="dataBooking.bookingEmail" placeholder="Please enter your email"
          class=" form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-white bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
      my-3
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none  pl-10 p-2.5  " maxlength="100" />
</div>


  
       
        <details class="" v-show="!EmailValidation || !EmailCheck">
          <summary class="text-xs leading-6 text-red-300  font-semibold select-none text-red-300">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 ">
            <span v-show="!EmailCheck" class="text-red-600"> กรุณาใส่อีเมล</span>
            <span v-show="!EmailValidation & EmailCheck" class="text-red-600"> กรุณากรอกอีเมลล์ให้ถูกต้อง</span>
          </div>
        </details>
      </div>


      <hr />
      <p class="text-2xl font-semibold text-center">
        Date and Time for Booking
      </p>
      <p class="text-sm  text-center">Duration {{ categoryDetail.categoryDuration }} minutes</p>

      <div>
        <span class="after:content-['*'] after:ml-0.5 after:text-red-500"> Date Time
        </span> :
    
    <div class=" ">
        <input type="datetime-local" id="inputTime" v-model="dataBooking.eventStartTime"
          class="my-3
        form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-white bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none
        col-span-3 " />

       

        <details class="" v-show="DateTimeCheck">
          <summary class="text-xs leading-6 text-red-300  font-semibold select-none text-red-300">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <div v-show="DateTimeCheck" class="text-red-600"> กรุณาเลือกวันที่ในอนาคตเท่านั้น </div>
          </div>
        </details>
      </div>
      </div>


      <div class="flex justify-center">        <button type="submit" @click="timeCheck = !timeCheck" class=" text-gray-500 bg-gray-300 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-2 py-1 text-center ">click for view all booked events </button>
</div>

      <timeEvent :cate="categoryDetail" v-if="timeCheck"/>




      <hr/>
      <p >Message to Advisor</p>

      <textarea maxlength="500" type="text" v-model="dataBooking.eventNotes"
        class="my-2
        form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-white bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none "></textarea>

      <div>

        <div id="app" class="container my-3">
          <div class="row">
            <div class="col-12 text-center">
            </div>
            <div class="col-md-5 offset-md-1">

              <hr/>
              <p class="my-4">File upload</p>
              <form >
      <div class="form-group">
        <!-- <input type="file" @change="previewImage " class="form-control-file block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer  focus:outline-none" id="file"> -->
        <div class=" justify-center items-center w-full">
         
    <label for="dropzone-file" class="flex flex-col justify-center items-center w-full h-13 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
        <div class="flex flex-col justify-center items-center pt-6 pb-6">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 text-gray-500">
  <path stroke-linecap="round" stroke-linejoin="round" d="M12 16.5V9.75m0 0l3 3m-3-3l-3 3M6.75 19.5a4.5 4.5 0 01-1.41-8.775 5.25 5.25 0 0110.233-2.33 3 3 0 013.758 3.848A3.752 3.752 0 0118 19.5H6.75z" />
</svg>

            <p class=" text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold"> upload  file</span> </p>
          
        </div>
        <input id="dropzone-file" type="file" class="hidden"  @change="previewImage " />
    </label>
</div> 
        <div v-if="preview" class="border-2 p-2 mt-3 w-full border-gray-200	grid justify-items-center">
          <p class="text-xs">Your file upload:</p>
          <template v-if="preview">
            <img :src="preview" class="img-fluid h-24" />
            <!-- <p class="text-green-500 text-xs">Upload success !!</p> -->
            <p class="mb-0 text-xs">file name: {{ image.name }}</p>
            <p class="mb-0 text-xs" id="size">size: {{ image.size/1024 }}KB</p>
    <div class="col-12 mt-3 text-center">
      <button @click="reset" class="text-sm text-red-500 ">cancel upload </button>
    </div>
          </template>
        </div>
      </div>
    </form>
            </div>
           
          </div>
        </div>
      </div>
      <div class="grid grid-cols-1 place-items-center">
    
        <button type="submit" @click="isActivePopup = true" class="text-gray-700 bg-gradient-to-r from-yellow-100 to-rose-300 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-12 py-2.5 text-center ">Submit</button>

        <!--<RoundButton bg-color="bg-sky-600" button-name="add" @click="isActivePopup = true" /> -->
        <br><span v-if="!AllDataCheck" class="text-sm leading-6 text-slate-900  font-semibold select-none text-red-400">
          **ยังไม่สามารถเพิ่มอีเว้นได้ กรุณาตรวจสอบข้อมูลของคุณให้ถูกต้อง </span>

        <!-- <RoundButton
          bg-color="bg-rose-400"
          button-name="cancel"
          @click="cancelBooking"
        /> -->

        <!-- <label class="block mb-2 text-sm font-medium text-gray-900 mt-4" for="file_input">Upload file</label>
<input class="block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer  focus:outline-none  " id="file_input" type="file" @change="previewFiles()"> -->

        <!-- <div id="output"></div> -->


        <!-- <privew @CheckSizeFile="CheckSizeFile"/> -->
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

        <div v-if="AllDataCheck == true">
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

      <PopupPage v-show="isActivePopup2 == true" :dim-background="true">
        <!-- ข้อมูลผิด -->


        <div class="grid grid-cols-1 place-items-center text-slate-700 font-semibold text-center  p-10 space-y-5">
          <div>ขนาดไฟล์ห้ามเกิน 10 mb
          </div>
          <RoundButton bg-color="bg-gray-400" button-name="ok" @click="isActivePopup2 = false" />
        </div>



      </PopupPage>


    </div>




  </div>
</template>

<style>

</style>
