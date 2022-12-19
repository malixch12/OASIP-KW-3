<script setup>
import generateCategory from '../components/generateCategory.vue'
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import RoundButton from "../components/RoundButton.vue";
import { useRoute } from "vue-router";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import PopupPage from "../components/PopupPage.vue";
import { useRouter } from "vue-router";
import goToLogin from "../components/goToLogin.vue";

const router = useRouter();

const jwtTokenRF = ref()
const jwtToken = ref()
const CateLists = ref();
const isActivePopup = ref(false);
const TokenTimeOut = ref(false)
const isActivePopup2 = ref(false);

const getLinkAll = async () => {

  if (UserRole.value!="Guest") {
    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/eventcategorys` ,{

method: 'get',
headers: {

  'Content-Type': 'application/json',
  'Authorization': 'Bearer ' + jwtToken.value
}
}

  );
  if (res.status === 200) {
    CateLists.value = await res.json();
  }
  if (res.status === 401) {
    
    await RefreshToken()
    await getLinkAll()
}
if (res.status === 500) {   isActivePopup2.value=true
}
  }

  if (UserRole.value=="Guest") {
    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/eventcategorys` ,{

method: 'get',
headers: {

  'Content-Type': 'application/json',
  
}
}

  );

  if (res.status === 200) {
    CateLists.value = await res.json();
  }
  if (res.status === 401) {
   
    await RefreshToken()
    await getLinkAll()

}
if (res.status === 500) {   isActivePopup2.value=true
}
  }
 
  }

const RefreshToken = async () => {

const res = await fetch(
  `${import.meta.env.VITE_APP_TITLE}/api/refresh`,
  {

    method: 'get',
    headers: {
      'IsRefreshToken': 'true',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + jwtTokenRF.value
    }
  }
);
if (res.status === 200) {
  console.log("โทเค้นหมดอายุ")
  let jwtTokenRF = await res.json()
  localStorage.setItem('jwtToken', jwtTokenRF.accessToken);
  jwtToken.value = localStorage.getItem('jwtToken');
  getLinkAll()
}else

  console.log(await res.json())
  isActivePopup2.value=true





};

const UserRole = ref()

onBeforeMount(async () => {
  UserRole.value = localStorage.getItem('UserRole');
  jwtTokenRF.value = localStorage.getItem('jwtTokenRF');
  jwtToken.value = localStorage.getItem('jwtToken');
 
  getLinkAll();

});

</script>

<template>
<div>
<goToLogin/>
<generateCategory name="Select a clinic to booking" :categorys="CateLists" type="Booking"/>


</div>
</template>
 
<style>

</style>