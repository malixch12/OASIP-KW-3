<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import UserShowList from "../components/UserShowList.vue";
import CheckPassword from "../components/CheckPassword.vue";
import CategoryOwn from "../components/CategoryOwn.vue";
import addUser from "../components/addUser.vue";
import goToLogin from "../components/goToLogin.vue";

import PopupPage from "../components/PopupPage.vue";

const UserShowListShow = ref(true);
const CheckPasswordShow = ref(false);
const CategoryManageShow = ref(false);


</script>

<template>

  <div >
  <goToLogin/>
    <div class="md:flex md:justify-center mt-8 grid grid-cols-4">
  <button @click="UserShowListShow=true,CheckPasswordShow=false,CategoryManageShow=false ,addUserShow=false" class="border-r-2   border-white bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-9 rounded-l md:text-base text-sm">
    USER LIST
  </button>
  <button @click="UserShowListShow=false,CheckPasswordShow=true ,CategoryManageShow=false" class="bg-gray-300 border-r-2  border-white hover:bg-gray-400 text-gray-800 font-bold py-2 px-9 text-base  ">
    CHECK PASSWORD
  </button>
  <button  @click="UserShowListShow=false,CheckPasswordShow=false ,CategoryManageShow=true ,addUserShow=false" class="bg-gray-300 border-r-2  border-white hover:bg-gray-400 text-gray-800 font-bold py-2 px-9  md:text-base text-sm">
    CATEGORY OWN
  </button>
</div>

<div class="flex justify-center">
  <UserShowList v-if="UserShowListShow"/>

</div>
    
<div class="flex justify-center">
  <CheckPassword v-if="CheckPasswordShow"/>

</div>

<div class="flex justify-center">
  <CategoryOwn v-if="CategoryManageShow"/>

</div>
   
  </div>
</template>

<style>
html,
  body {
    height: 100%;
  }

  @media (min-width: 640px) {
    table {
      display: inline-table !important;
    }

    thead tr:not(:first-child) {
      display: none;
    }
  }

  td:not(:last-child) {
    border-bottom: 0;
  }

  th:not(:last-child) {
    border-bottom: 2px solid rgba(0, 0, 0, .1);
  }
</style>
