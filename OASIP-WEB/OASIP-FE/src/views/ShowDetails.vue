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
  }
};

onBeforeMount(async () => {
  jwtToken.value = localStorage.getItem('jwtToken');
  
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

const goBack = () => router.go(-1);

const goAboutUs = () => appRouter.push({ name: "About" });

const toEditMode = (editNote) => {
  console.log(editNote);
  editingNote.value = editNote;
};

const InputTime = ref()
const updateNote = async () => {
  console.log("input " + InputTime.value )
  console.log("eventtime " + eventLists.value.eventStartTime )
  //eventLists.value.eventStartTime = await new Date(eventLists.value.eventStartTime).toISOString();
  if (DateTimeCheck.value == true && InputTime.value != null) {
    console.log("1")
    eventLists.value.eventStartTime = InputTime.value
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
          'Authorization': 'Bearer ' + jwtToken.value
        },
        body: JSON.stringify({
          eventNotes: eventLists.value.eventNotes,
          eventStartTime: new Date(
            eventLists.value.eventStartTime
          ).toISOString(),
        }),
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
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
          'Authorization': 'Bearer ' + jwtToken.value
        },
        body: JSON.stringify({
          eventNotes: eventLists.value.eventNotes
         
        }),
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
  if (countTime.value > new Date(eventLists.value.eventStartTime)) {
    DateTimeCheck.value = false;
  } else {
    DateTimeCheck.value = true;
  }
});


</script>

<template>
  <div class="flex justify-center">
     <!-- popup -->
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
    <div class="bg-white space-y-7 shadow-xl rounded-lg ml-48 mr-48 p-12 w-2/5">
      <RoundButton
        bg-color="bg-slate-400 text-white text-sm"
        button-name="<< go back"
        @click="goBack"
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
            class="border-2 border-sky-200 w-8/12 rounded-lg"
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

          <p class="text-slate-600 font-bold">Message to Advisor</p>

          <div v-show="hideEdit">
          
              {{ eventLists.eventNotes }}
         
          </div>
          <div>
            <textarea
              v-show="!hideEdit"
              type="text"
              class="border-2 border-sky-200 w-11/12 h-56 rounded-lg"
              v-model="eventLists.eventNotes"
            ></textarea>
          </div>

          <div v-show="hideEdit" class="grid grid-cols-2 pt-3">
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
