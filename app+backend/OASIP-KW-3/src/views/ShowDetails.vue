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
    `${import.meta.env.VITE_APP_TITLE}/api/events/${myRouter.query.BookingId}`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
  }
};

onBeforeMount(async () => {
  getLinkAll();
});

const removeEvent = async () => {
  if (confirm("Would you like to cancel your appointment?") == true) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/events/${
        myRouter.query.BookingId
      }`,
      {
        method: "DELETE",
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

const updateNote = async () => {
  //eventLists.value.eventStartTime = await new Date(eventLists.value.eventStartTime).toISOString();

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/${myRouter.query.BookingId}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        eventDuration: eventLists.value.eventDuration,
        bookingEmail: eventLists.value.bookingEmail,
        eventCategoryID: eventLists.value.eventCategoryID,
        eventCategory: eventLists.value.eventCategory,
        bookingId: eventLists.value.bookingId,
        eventNotes: eventLists.value.eventNotes,
        bookingName: eventLists.value.bookingName,
        eventStartTime: new Date(eventLists.value.eventStartTime).toISOString(),
      }),
    }
  );
  if (res.status === 200) {
    console.log(eventLists.value.eventStartTime);
    isActivePopup.value = false;
    hideEdit.value = true;
    getLinkAll();
    console.log("edited successfully");
  } else console.log("error, cannot be added");
};

const isActivePopup = ref(false);
</script>

<template>
  <div class="flex justify-center">
    <div class="bg-white space-y-7 shadow-xl rounded-lg ml-48 mr-48 p-12 w-2/5">
      <RoundButton
        bg-color="bg-slate-400 text-sm"
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
            {{ new Date(eventLists.eventStartTime).toLocaleDateString("th-TH") }}
          </p>
          <p v-show="hideEdit">
            <span class="text-slate-600 font-bold"> Time : </span>
            {{ new Date(eventLists.eventStartTime).toLocaleTimeString("th-TH") }}
          </p>

          <p>
            <span class="text-slate-600 font-bold"> Date Time : </span>
            <input
              v-show="!hideEdit"
              type="datetime-local"
              class="border-2 border-sky-200 w-8/12 rounded-lg"
              v-model="eventLists.eventStartTime"
            />
          </p>
          <p>
            <span class="text-slate-600 font-bold">Duration </span>
            {{ eventLists.eventDuration }}
            <span class="text-slate-600 font-bold">Minutes</span>
          </p>

          <p class="text-slate-600 font-bold">Message to Advisor</p>

          <div v-show="hideEdit">
            <p class="pl-5" v-if="eventLists.eventNotes.length > 0">
              {{ eventLists.eventNotes }}
            </p>
            <p class="pl-5" v-else>-</p>
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
              bg-color="bg-emerald-400"
              button-name="edit"
              @click="
                () => {
                  hideEdit = false;
                }
              "
            />
            <RoundButton
              bg-color="bg-rose-400"
              button-name="delete"
              @click="removeEvent"
            />
          </div>

          <div v-show="!hideEdit" class="grid grid-cols-2 pt-3">
            <RoundButton
              bg-color="bg-emerald-400"
              button-name="save"
              @click="() => (isActivePopup = true)"
            />

            <RoundButton
              bg-color="bg-rose-400"
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
      <!-- popup -->
      <PopupPage v-show="isActivePopup" :dim-background="true">
        <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-16">
          Do you want to update?
        </p>
        <div class="flex justify-between max-w-lg mx-auto">
          <RoundButton
            bg-color="bg-emerald-400"
            button-name="Yes"
            @click="updateNote"
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
