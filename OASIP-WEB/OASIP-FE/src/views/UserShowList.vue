<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";

const route = useRoute();


const UserLists = ref()

const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users`
  );
  if (res.status === 200) {
    UserLists.value = await res.json();
  }
};

onBeforeMount(async () => {
  getLinkAll();
});

const removeEvent  = async (UserId) => {
  if (confirm("Would you like to cancel your appointment?") == true) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/users/${UserId}`,
      {
        method: "DELETE",
      }
    );
  }getLinkAll()
};

const isActivePopup = ref(false);
const dataDetail = ref({

        userName: "",
        role: "",
        email: "",
        updateOn: "",
        createOn: ""

})
</script>

<template>

  <div class="flex justify-center grid grid-rows-1 mt-16">

   <PopupPage v-show="isActivePopup" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="">
        <span class="font-bold">user name : </span>   {{dataDetail.userName}} </p>
       <p>  <span class="font-bold"> role : </span>  {{dataDetail.role}} </p> 
         <p> <span class="font-bold">email : </span> {{dataDetail.email}} </p> 
         <p>   <span class="font-bold">update on :</span>  {{dataDetail.updateOn}} </p> 
          <p>  <span class="font-bold">create on :</span>   {{dataDetail.createOn}} </p> 
       
        <div class="success-checkmark">
  <div class="check-icon">
    <span class="icon-line line-tip"></span>
    <span class="icon-line line-long"></span>
    <div class="icon-circle"></div>
    <div class="icon-fix"></div>
  </div>
</div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false" />
        </div>
      </div>
    </PopupPage>

    <div class="text-3xl font-bold text-center   drop-shadow-md"> User list </div>
    <br>
<div class="overflow-x-auto relative shadow-md sm:rounded-lg w-full px-24 bg-white py-8 ">
    <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <caption class="p-5 text-lg font-semibold text-left text-gray-900 bg-white dark:text-white dark:bg-gray-800">
            show list all user in db
            <p class="mt-1 text-sm font-normal text-gray-500 dark:text-gray-400">This is the table where all user information is stored. Each user's information includes username, email address, role, time of creation, last modified time.</p>
        </caption>
        <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="py-3 px-14 ">
                    User name
                </th>
                <th scope="col" class="py-3 px-14">
                    Email
                </th>
                <th scope="col" class="py-3 px-14">
                    role
                </th>
            
                <th scope="col" class="py-3 px-14">
                    <span class="sr-only">detail</span>
                </th>

                <th>

                </th>
                <th>

                </th>
                
            </tr>
        </thead>
        <tbody>
            <tr v-for="(user, index) in UserLists" :key="index" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">

                <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    {{user.userName}}
                </th>
                <td class="py-4 px-14">
                    {{user.email}}
                </td>
                <td class="py-4 px-14">
                    {{user.role}}
                </td>
              
                <td class="py-4 px-14 text-right">
                    <div class="font-medium text-blue-600  hover:underline" @click="isActivePopup=true
                    , dataDetail.userName=user.userName ,
                    dataDetail.email=user.email ,
                     dataDetail.role=user.role ,
                      dataDetail.updateOn = user.updateOn ,
                      dataDetail.createOn = user.createOn
                    
                    ">detail</div>
                </td>
                  <td class="py-4 px-14 text-right">
                    <div class="font-medium text-red-600  hover:underline" @click="removeEvent(user.userId)">delete</div>
              
</td>
<td class="py-4 text-right">
  <svg width="1em" height="1em" viewBox="0 0 24 24"><path fill="#888888" d="m19.3 8.925l-4.25-4.2l1.4-1.4q.575-.575 1.413-.575q.837 0 1.412.575l1.4 1.4q.575.575.6 1.388q.025.812-.55 1.387ZM17.85 10.4L7.25 21H3v-4.25l10.6-10.6Z"></path></svg>
</td>


              
           
           
            </tr>
        </tbody>
    </table>
</div>


  </div>
</template>

<style></style>
