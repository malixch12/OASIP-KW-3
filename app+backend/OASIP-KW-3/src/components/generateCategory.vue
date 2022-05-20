<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";

const router = useRouter();

const props = defineProps({
  name: {
    type: String,
    require: false,
  },
  categorys: {
    type: Array,
    require: true,
  },
  type: {
    type: String,
    require: true,
  },
});



const addList = (categoryId, eventCategoryName, eventDuration) => {
  if (props.type == "ShowList") {
    router.push({
      name: "ShowList",
      query: { categoryId: categoryId, eventCategoryName: eventCategoryName },
    });
  }

  if (props.type == "Booking") {
    router.push({
      name: "Booking",
      query: {
        categoryId: categoryId,
        eventCategoryName: eventCategoryName,
        eventDuration: eventDuration,
      },
    });
  }
};

const isActivePopup = ref(false);
const eventDuration = ref(0);
const eventCategoryDescription = ref()
const eventCategoryID = ref();
const eventCategoryName = ref();
function test(Duration, Description, id, name) {
  console.log(eventDuration)
  isActivePopup.value = true
  eventDuration.value = Duration
  eventCategoryID.value = id
  eventCategoryName.value = name
  if (Description == null) {
    eventCategoryDescription.value = "ไม่มีคำอธิบาย"
    console.log(Description)
  } else
    eventCategoryDescription.value = Description
}

const goEdit = (categoryId) => {

  router.push({
    name: "EditCatePage",
    query: { categoryId: categoryId },
  });

};

</script>

<template>
  <div>
    <!-- popup -->
    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div class="flex justify-end p-5" @click="() => isActivePopup = false">
        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true" role="img"
          class="iconify iconify--iconoir" width="32" height="32" preserveAspectRatio="xMidYMid meet"
          viewBox="0 0 24 24">
          <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
            d="M6.758 17.243L12.001 12m5.243-5.243L12 12m0 0L6.758 6.757M12.001 12l5.243 5.243"></path>
        </svg>

      </div>

      <div class="mb-2 px-10 pb-10">
        {{ eventCategoryName }} <br>
        <span class="text-2xl font-semibold text-slate-600 tracking-wide pb-16 ">
          Duration :
        </span> {{ eventDuration }} นาที

        <div class="text-2xl font-semibold text-slate-600 tracking-wide py-5 ">
          Description : </div>
        <div class="w-64 text-lg">{{ eventCategoryDescription }} </div>
        <div class="flex justify-center max-w-lg mx-auto mt-5">
          <RoundButton bg-color="bg-green-400 text-white" button-name="edit" @click="
            goEdit(eventCategoryID)
          " />
        </div>
      </div>

    </PopupPage>

    <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
      <div class="text-3xl font-bold text-center mb-10">
        {{ name }}
      </div>

      <router-link :to="{ name: 'ListAllEvent', query: { categoryId: 0 } }" class="grid justify-items-center">
        <div v-if="type == `ShowList`"
          class="transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 duration-100 shadow-xl text-center font-semibold mb-10 bg-slate-100 text-slate-600 hover:bg-slate-600 hover:text-white rounded-full w-72 p-1 ">
          Check All Appointment
        </div>
      </router-link>


      <div class="grid grid-cols-3 gap-6 place-items-center mb-12">
        <div v-for="category in categorys" :key="category.eventCategoryID">
          <div class="grid justify-items-center">
            <img :src="`../../public/` + category.eventCategoryID + `.png`" @click="
              addList(
                category.eventCategoryID,
                category.eventCategoryName,
                category.eventDuration
              )
            " class="w-64 transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 duration-100" />
            <div class="text-center py-3">{{ category.eventCategoryName }}</div>
            <button
              @click="test(category.eventDuration, category.eventCategoryDescription, category.eventCategoryID, category.eventCategoryName)"
              class="transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110  duration-150 rounded-lg text-sm leading-6  font-semibold select-none text-sky-600 ">
              show detail </button>
            <br>

          </div>
        </div>
      </div>



    </div>
  </div>
</template>

<style>
</style>
