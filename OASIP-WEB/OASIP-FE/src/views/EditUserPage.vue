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
const User= ref({
    userName : "" ,
    role : "" ,
    email : ""
})

const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users/${myRouter.query.UserId}`
  );
  if (res.status === 200) {
   User.value = await res.json();
    console.log("corret")
  } else
    console.log("cant fetch")
};

console.log(myRouter.query.userId)

onBeforeMount(async () => {
// getLinkAll();
});


const goBack = () => router.go(-1);



const updateUser = async () => {
  //eventLists.value.eventStartTime = await new Date(eventLists.value.eventStartTime).toISOString();
  if (1 == 1) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/users/${myRouter.query.UserId
      }`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify({

          userName: User.value.userName,
          role: User.value.role,
          email: User.query.email,
        
        }),
      }
    );
    if (res.status === 200) {
   
    //   statusTrue()
      console.log("edited successfully");
    } else 
    // statusFalse()
console.log("xxx")
  }
};

const CheckStatusPut = ref(true)

function statusTrue() {

  CheckStatusPut.value = true
}

function statusFalse() {

  CheckStatusPut.value = false
}

onBeforeUpdate(() => {

});

const isActivePopup = ref(false);
</script>

<template>

  <div class="flex justify-center">
     
    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div v-if="CheckStatusPut" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-8">
          edit succeeded
        </p>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-emerald-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false" />
        </div>
      </div>

      <div v-if="!CheckStatusPut" class="grid grid grid-cols-1 p-12 place-items-center">
        <img src="../assets/error.png" class="w-24 " /> <br>
        <p class="text-3xl font-semibold text-slate-600 tracking-wide pb-8">
          edit fail pls check your data
        </p>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-emerald-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false" />
        </div>
      </div>
    </PopupPage>




    <div class="bg-white space-y-7 shadow-xl rounded-lg ml-48 mr-48 p-12 w-2/5">
      <RoundButton bg-color="bg-slate-400 text-white text-sm" button-name="<< go back" @click="goBack" />


      <div class="space-y-7">
        <div>
          <span class="font-bold text-slate-600 ">User Name :<input
              class="border-2 border-sky-200 rounded-lg w-64 pl-2 ml-1" type="text" maxlength="100"
              v-model="User.userName">
          </span>
          <br> <span class=" font-bold text-red-600 text-xs">*ชื่อห้ามเว้นว่างและห้ามซ้ำ</span><span
            class=" font-bold text-gray-600 text-xs"> และ ยาวสุดไม่เกิน 100 ตัว</span> <span
            class=" font-bold text-gray-600 text-xs">--> เหลืออีก {{ 100 - User}} ตัว
          </span>
        </div>
        <div class="text-slate-600 font-bold ">role : <input
            class="border-2 border-sky-200 rounded-lg w-20 ml-1 pl-2" type="number" min="1" max="480"
            v-model="User.role">
          Minutes
        </div>

        <span class=" font-bold text-red-600 text-xs">*duration ห้ามเว้นว่าง </span>
        <span class=" font-bold text-gray-600 text-xs"> และ ห้ามจองเกิน 480 นาที</span>


        <div class="text-slate-600 font-bold">email </div>
        <input
              class="border-2 border-sky-200 rounded-lg w-64 pl-2 ml-1" type="text" maxlength="100"
              v-model="User.email">
        <div class=" pt-3 flex justify-center ">


          <RoundButton bg-color="bg-emerald-400 text-white  place-items-center" button-name="save"
            @click="updateUser(), isActivePopup = true" />

        </div>



      </div>
      <!-- popup -->

    </div>
  </div>
</template>

<style>
</style>
