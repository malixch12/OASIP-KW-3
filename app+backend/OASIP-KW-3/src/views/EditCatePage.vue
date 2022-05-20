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
const eventLists = ref([  ])
const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/eventcategorys/${myRouter.query.categoryId}`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
     console.log("corret")
  }else
  console.log("cant fetch")
};

onBeforeMount(async () => {
  getLinkAll();
});


const goBack = () => router.go(-1);



const updateNote = async () => {
  //eventLists.value.eventStartTime = await new Date(eventLists.value.eventStartTime).toISOString();
  if (1==1) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/eventcategorys/${
        myRouter.query.categoryId
      }`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify({

         eventCategoryName: eventLists.value.eventCategoryName,
       eventCategoryDescription: eventLists.value.eventCategoryDescription, 
         eventCategoryID:  myRouter.query.categoryId,
        eventDuration: eventLists.value.eventDuration
        }),
      }
    );
    if (res.status === 200) {
      // console.log(eventLists.value.eventStartTime);
      // isActivePopup.value = false;
      // hideEdit.value = true;
      // getLinkAll();
      console.log("edited successfully");
    } else console.log("error, cannot be added");
  }
};




onBeforeUpdate(() => {
  
});

const isActivePopup = ref(false);
</script>

<template>

  <div class="flex justify-center">
    <PopupPage v-show="isActivePopup" :dim-background="true" class="">
          <div class=" grid grid-cols-1">
          <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-8">
            edit succeeded
          </p>
          <div class=" max-w-lg mx-auto  ">
            <RoundButton
              bg-color="bg-emerald-400 text-white flex justify-center"
              button-name="ok"
              @click="isActivePopup=false "
            />
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
          
          <p>
            <span class="font-bold text-slate-600 flex justify-center">category name : <input class="border-2 border-sky-200 rounded-lg w-72 ml-4" type="text" v-model="eventLists.eventCategoryName"> </span>
         
          </p>

          <p>
            <span class="text-slate-600 font-bold flex justify-center">Duration :  <input class="border-2 border-sky-200 rounded-lg w-9 ml-4" type="text" v-model="eventLists.eventDuration"> </span>
          
          </p>

      
            <span class="text-slate-600 font-bold flex justify-center"> eventCategoryDescription : </span>
            
            <textarea  
              type="text"
              class="border-2 border-sky-200 w-11/12 h-56 rounded-lg"
              v-model="eventLists.eventCategoryDescription"
            ></textarea>

         
          <div  class=" pt-3 flex justify-center ">
            <RoundButton
              bg-color="bg-emerald-400 text-white  place-items-center"
              button-name="save"
              @click="updateNote() , isActivePopup = true"
            />
         
          </div>

      
         
        </div>
      </div>
      <!-- popup -->
     
    </div>
  </div>
</template>

<style></style>
