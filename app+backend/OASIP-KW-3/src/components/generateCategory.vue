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
function test(Duration,Description) {
    console.log(eventDuration)
   isActivePopup.value = true
   eventDuration.value = Duration
   if(Description == null) {
     eventCategoryDescription.value= "ไม่มีคำอธิบาย"
     console.log(Description)
   }else
   eventCategoryDescription.value = Description
}

</script>

<template>
  <div>
    <!-- popup -->
      <PopupPage v-show="isActivePopup" :dim-background="true">
        
          <div class="mb-2">
            <span class="text-2xl font-semibold text-slate-600 tracking-wide pb-16 ">
            duration :
          </span>  {{eventDuration  }} นาที  
          </div>
<div class="text-2xl font-semibold text-slate-600 tracking-wide ">
Description :       </div> <div class="w-64" >{{ eventCategoryDescription}} </div>
<br>
          <div class="flex justify-center max-w-lg mx-auto">
            <RoundButton
              bg-color="bg-green-400 text-white"
              button-name="Ok"
              @click="
                () => {
                  isActivePopup = false;
                }
              "
            />
          </div>
      </PopupPage>
   
    <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
      <div class="text-3xl font-bold text-center mb-6">
        {{ name }}
      </div>
      <router-link :to="{ name: 'ListAllEvent', query: { categoryId: 0 } }" class="grid justify-items-center">
        <div v-if="type == `ShowList`"
          class=" shadow-xl text-center font-semibold mb-6 bg-slate-100 text-slate-600 hover:bg-slate-600 hover:text-white rounded-full w-72 p-1 ">
          Check All Appointment
        </div>
      </router-link>


      <div class="grid grid-cols-3 gap-6 place-items-center mb-12">
        <div v-for="category in categorys" :key="category.eventCategoryID">
          <div>
            <img :src="`../../public/` + category.eventCategoryID + `.png`" @click="
              addList(
                category.eventCategoryID,
                category.eventCategoryName,
                category.eventDuration
              )
            " class="w-64" />
            <div class="text-center  ">{{ category.eventCategoryName }}
            </div>
            <div @click="test(category.eventDuration , category.eventCategoryDescription)" class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-sky-600 text-center"> show detail </div>
            <br>
       

            <!-- <div> <div @click="addList(category.eventCategoryID,category.eventCategoryName,category.eventDuration)"
 class="text-center mt-4">{{ category.eventCategoryName }}</div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
</style>
