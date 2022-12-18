<script setup>
import {
  onBeforeMount,
  ref,
  onBeforeUpdate,
  computed,
} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";
import PopupPage from "../components/PopupPage.vue";
import goToLogin from "../components/goToLogin.vue";

const jwtToken = ref()
const router = useRouter();
const myRouter = useRoute();
const eventLists = ref({
  bookingId: "",
  bookingName: null,
  bookingEmail: null,
  eventCategory: null,
  eventStartTime: null,
  eventDuration: null,
  eventNotes: "",
  eventCategoryID: null,
  file : null
});
const hideEdit = ref(true);

const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/${myRouter.query.BookingId}`,
    {

method: 'get',
headers: {

  'Content-Type': 'application/json',
  'Authorization': 'Bearer ' + jwtToken.value
}
}
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    console.log( eventLists.value)
  }

  if (res.status === 403) {
    goList()
    

  
  }
};

const UserRole = ref()
onBeforeMount(async () => {
  jwtToken.value = localStorage.getItem('jwtToken');
  UserRole.value = localStorage.getItem('UserRole');
 
  getLinkAll();
});

const removeEvent = async () => {
  if (confirm("Would you like to cancel your appointment?") == true) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "DELETE",headers: {

'Content-Type': 'application/json',
'Authorization': 'Bearer ' + jwtToken.value
}
      }
    );
    router.go(-1);
  } else {
  }
};

function goBack () {
  router.go(-1);
} 

const goAboutUs = () => appRouter.push({ name: "About" });

const toEditMode = (editNote) => {
  console.log(editNote);
  editingNote.value = editNote;
};

const InputTime = ref()
const updateNote = async () => {
  let formData = new FormData();


  console.log("input " + InputTime.value )
  console.log("eventtime " + eventLists.value.eventStartTime )
  //eventLists.value.eventStartTime = await new Date(eventLists.value.eventStartTime).toISOString();
  if (DateTimeCheck.value == true && InputTime.value != null) {
    console.log("1")
    eventLists.value.eventStartTime = InputTime.value
    formData.append("eventNotes", eventLists.value.eventNotes);
  formData.append("eventStartTime", 
            eventLists.value.eventStartTime
  )
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "PUT",
        headers: {
          'Authorization': 'Bearer ' + jwtToken.value
        },
        body:formData
      }
    );
     if (res.status === 400) {
          test();
    } 

    if (res.status === 200) {
      console.log(eventLists.value.eventStartTime);
      isActivePopup.value = false;
      hideEdit.value = true;
      getLinkAll();
      console.log("edited successfully");
    } else console.log("error, cannot be added");
  }

  if (DateTimeCheck.value == true && InputTime.value == null) {
    console.log("2")
    formData.append("eventNotes", eventLists.value.eventNotes);

    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "PUT",
        headers: {
          'Authorization': 'Bearer ' + jwtToken.value
        },
        body: formData
      }
    );
     if (res.status === 400) {
          test();
    } 

    if (res.status === 200) {
      console.log(eventLists.value.eventStartTime);
      isActivePopup.value = false;
      hideEdit.value = true;
      getLinkAll();
      console.log("edited successfully");
    } else console.log("error, cannot be added");
  }

  if (preview.value!=null ) {
  
    formData.append("file", eventLists.value.file);

    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "PUT",
        headers: {
          'Authorization': 'Bearer ' + jwtToken.value
        },
        body: formData
      }
    );
     if (res.status === 400) {
          test();
    } 

    if (res.status === 200) {
      console.log(eventLists.value.eventStartTime);
      isActivePopup.value = false;
      hideEdit.value = true;
      getLinkAll();
       console.log("edited file successfully");

      reset()
    } else console.log("error, cannot be added");
    

   
  }
  if(deleteFileCheck.value == true) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/file/${
        myRouter.query.BookingId
      }`,
      {
        method: "DELETE",headers: {

'Content-Type': 'application/json',
'Authorization': 'Bearer ' + jwtToken.value
}
      }
    );if (res.status === 200) {
      console.log("delete")
      deleteFileCheck.value = false    } 

  } else {
  
   }

   window.location.reload();

};
const testname = ref('')
const isActivePopup = ref(false);
const DateTimeCheck = ref(false);
function test  () {
 testname.value = 'เวลาที่คุณเลือกนั้นตรงกับอีเว้นอื่นๆ กรุณาเช็คเวลาใหม่'
}


const countTime = ref("");
function setTime() {
  var today = new Date();
  var now_date =
    today.getDate() +
    "/" +
    (today.getMonth() + 1) +
    "/" +
    (today.getFullYear() + 543);
  var now_time = (
    today.getHours() +
    ":" +
    today.getMinutes() +
    ":" +
    today.getSeconds()
  ).toString("th-TH");
  countTime.value = today;
}
setInterval(setTime, 1000);



onBeforeUpdate(() => {
  if (countTime.value > eventLists.value.eventStartTime) {
    DateTimeCheck.value = false;
  } else {
    DateTimeCheck.value = true;
  }
});



const goList = () => {

router.push({
  name: "ListAllEvent"
 
});
}


const DowloadFlie = async () => {


   await fetch( `${import.meta.env.VITE_APP_TITLE}/api/download/${eventLists.value.bookingId}` )
.then((res) => { return res.blob(); })
.then((data) => {
  var a = document.createElement("a");
  a.href = window.URL.createObjectURL(data);
  a.download = `${eventLists.value.fileName}`;
  a.click();
});

}


const  isActivePopup2 = ref()
 
const preview =  ref(null)
const    image =  ref({name:null})
const    preview_list = ref([])

function previewImage(event) {
 // CheckSizeFile(event)

 if(event.target.files[0].size <= (10485760)) {
  eventLists.value.file = event.target.files[0];

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
      image.value = {name:null};
      preview.value = null;
      preview_list.value = [];
    }

 const   deleteFileCheck  =   ref(false)
   function deleteFile() {
      console.log("delete")
      eventLists.value.file = ""
      eventLists.value.fileName = ""
      deleteFileCheck.value = true

     
  
  
   

    }
</script>

<template>
  <div class="flex justify-center">
    <goToLogin/>
     <!-- popup -->
     <PopupPage v-show="isActivePopup2 == true" :dim-background="true">
        <!-- ข้อมูลผิด -->

      
          <div class="grid grid-cols-1 place-items-center text-slate-700 font-semibold text-center  p-10 space-y-5">
            <div>ขนาดไฟล์ห้ามเกิน 10 mb
             </div>
            <RoundButton bg-color="bg-gray-400" button-name="ok" @click="isActivePopup2 = false" />
        </div>



      </PopupPage>


      <PopupPage v-show="isActivePopup" :dim-background="true">
      <div class="grid grid-cols-1 place-items-center  font-semibold text-center  p-10 space-y-5">
        <div v-show="DateTimeCheck == true">
          <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-8">
            Do you want to update?
           
          </p>
          <span class="text-red-600"> {{testname}} </span> <br>
          <div class="flex justify-between max-w-lg mx-auto mt-6">
            <RoundButton
              bg-color="bg-emerald-400 text-white"
              button-name="ok"
              @click="updateNote "
            />

            <RoundButton
              bg-color="bg-rose-400 text-white"
              button-name="cancel"
              @click="
                () => {
                  isActivePopup = false;
                  testname = ''
                }
              "
            />
          </div>
        </div>

        <div v-show="DateTimeCheck == false">
          <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-16">
            Please check the date to make sure you haven't booked in the past.

          
          </p>
          <div class="flex justify-center max-w-lg mx-auto">
            <RoundButton
              bg-color="bg-rose-400 text-white"
              button-name="Ok"
              @click="
                () => {
                  isActivePopup = false;
                }
              "
            />
          </div>
        </div>
        </div>
      </PopupPage>
    <div class="space-y-7 bg-white shadow-xl rounded-lg md:ml-24 md:p-16 p-8  rounded md:w-auto w-full mt-12">
      <RoundButton
        bg-color="bg-slate-400 text-white text-sm"
        button-name="<< go back"
        @click="goBack()"
      />

      <div class="col-span-1 grid grid-cols-1 place-items-center">
        <div class="space-y-5">
          <p class="text-3xl font-bold text-rose-400">
            {{ eventLists.eventCategory }}
          </p>

          <p>
            <span class="font-bold text-slate-600">Booking Name : </span>
            {{ eventLists.bookingName }}
          </p>

          <p>
            <span class="text-slate-600 font-bold">Email : </span>
            {{ eventLists.bookingEmail }}
          </p>

          <p v-show="hideEdit">
            <span class="text-slate-600 font-bold"> Date : </span>
            {{
              new Date(eventLists.eventStartTime).toLocaleDateString("th-TH")
            }}
          </p>
          <p v-show="hideEdit">
            <span class="text-slate-600 font-bold"> Time : </span>
            {{
              new Date(eventLists.eventStartTime).toLocaleTimeString("th-TH")
            }}
          </p>
          <span v-if="!hideEdit" class="text-slate-600 font-bold">
            Date Time :
          </span>
          <input
            v-if="!hideEdit"
            type="datetime-local"
            class="border-2 border-sky-200 w-full rounded-lg"
            v-model="InputTime"
           
          />
          <div v-if="DateTimeCheck" v-show="!hideEdit" class="text-slate-600 font-bold text-xs">
            * If you leave it blank, the old date will be used.
          </div>
          <div
            v-if="!DateTimeCheck"
            v-show="!hideEdit"
            class=" font-bold text-red-600 text-xs"
          >
            * Please select future dates only.
          </div>

          <p>
            <span class="text-slate-600 font-bold">Duration </span>
            {{ eventLists.eventDuration }}
            <span class="text-slate-600 font-bold">Minutes</span>
          </p>

          <p class="text-slate-600 font-bold">file</p>

<span v-if="eventLists.fileName!=null & hideEdit">

    {{ eventLists.fileName }}  <span class="text-blue-500" @click="DowloadFlie()">Dowload</span>

</span>
<span v-if="eventLists.fileName==null &  hideEdit">

-

</span>

<span v-if="! hideEdit">

<span class="text-xs">old file : </span>    <span v-if="eventLists.fileName==null">-</span> 
<span class="text-sm">{{ eventLists.fileName }} <span class="text-xs text-red-500" v-if="eventLists.fileName!=null" @click="deleteFile()">delete</span> </span> <br/>
<span class="text-xs">new file file : {{ image.name }} </span> 
</span>
<form v-if="! hideEdit">
      <div class="form-group">
        <!-- <input type="file" @change="previewImage " class="form-control-file block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer  focus:outline-none" id="file"> -->
        <div class=" justify-center items-center w-full">
         
    <label for="dropzone-file" class="flex flex-col justify-center items-center w-full h-12 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
        <div class="flex flex-col justify-center items-center pt-6 pb-6">
          
            <p class=" text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold"> upload new file</span> </p>
          
        </div>
        <input id="dropzone-file" type="file" class="hidden"  @change="previewImage " />
    </label>
</div> 
        <div class="border-2 p-2 mt-3 w-full border-sky-200	">
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

          <p class="text-slate-600 font-bold">Message to Advisor</p>

          <div v-show="hideEdit">
          
              {{ eventLists.eventNotes }}
         
          </div>
          <div>
            <textarea
              v-show="!hideEdit"
              type="text"
              class="border-2 border-sky-200 w-full h-56 rounded-lg"
              v-model="eventLists.eventNotes"
            ></textarea>
          </div>

          <div v-if="UserRole!=`Lecturer`" v-show="hideEdit" class="grid grid-cols-2 pt-3">
            <RoundButton
              bg-color="bg-emerald-400 text-white"
              button-name="edit"
              @click="
                () => {
                  hideEdit = false;
                }
              "
            />
            <RoundButton
              bg-color="bg-rose-400 text-white"
              button-name="delete"
              @click="removeEvent"
            />
          </div>

          <div v-show="!hideEdit" class="grid grid-cols-2 pt-3">
            <RoundButton
              bg-color="bg-emerald-400 text-white"
              button-name="save"
              @click="() => (isActivePopup = true)"
            />

            <RoundButton
              bg-color="bg-rose-400 text-white"
              button-name="cancel"
              @click="
                () => {
                  hideEdit = true;
                }
              "
            />
          </div>
        </div>
      </div>
     
    </div>
  </div>
</template>

<style></style>
