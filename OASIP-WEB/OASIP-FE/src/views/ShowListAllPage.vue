<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import RoundButton from "../components/RoundButton.vue";
import { useRoute } from "vue-router";
import ShowList from "../components/ShowList.vue";
import PopupPage from "../components/PopupPage.vue";
import { useRouter } from "vue-router";
import goToLogin from "../components/goToLogin.vue";

const router = useRouter();
const eventLists = ref({content:[{test:"test"}]});
const page = ref(0);
const numPage = ref();
const jwtToken = ref()
const jwtTokenRF = ref()

const isActivePopup = ref(false);
const TokenTimeOut = ref(false)
const RefreshToken = async () => {
  console.log("RefreshToken doing...")

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
  }else
  isActivePopup2.value=true


};

const getLinkAll = async () => {
 // await RefreshToken()
  // const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events?page=${page.value}&pageSize=8`,{

method: 'get',
headers: {

  'Content-Type': 'application/json',
  'Authorization': 'Bearer ' + jwtToken.value
}
}

  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
  if (res.status === 401) {
    await RefreshToken()
    await getLinkAll()

}
  if(res.status === 500) {
    eventLists.value = {content:[]}
  }
 
  }
;

const getLinkAllNoPage = async (FilterDate) => {
  //await RefreshToken()
  // const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events?page=${page.value}&pageSize=100000` , 
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

    let dateArrayFilter = eventLists.value.content.filter((event)=>{
  return event.date == FilterDate
})
  eventLists.value.content = dateArrayFilter
      numPage.value = 0
  }
  if (res.status === 401) {
    await RefreshToken()
    await getLinkAllNoPage()

  }
  if(res.status === 500) {
    eventLists.value = {content:[]}
  }
};


const UserRole = ref()
onBeforeMount(async () => {
  UserRole.value = localStorage.getItem('UserRole');
  jwtTokenRF.value = localStorage.getItem('jwtTokenRF');
  jwtToken.value = localStorage.getItem('jwtToken');

  getLinkFuture();

});

function paging(index,filter) {
  page.value = index;
 
  if(filter==3){
getLinkAll()
}
if(filter==2){
getLinkFuture()
}
if(filter==1){
getLinkPast()
}
}

const getLinkPast = async () => {
  //await RefreshToken()
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/pastdays/?pageSize=8&page=${page.value}`,
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
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
  if (res.status === 401) {
    await RefreshToken()
    await getLinkPast()

  
  }
  if(res.status === 500) {
    eventLists.value = {content:[]}
  }
};

const getLinkFuture = async () => {
  //await RefreshToken()
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/futuredays/?pageSize=8&page=${page.value}`,
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
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
    console.log( eventLists.value)
  }
  if (res.status === 401) {
    await RefreshToken()
    await getLinkFuture()
  
  }
  if(res.status === 500) {
    eventLists.value = {content:[]}
  }
};

function pastFilter() {
  getLinkPast();
}

function futureFilter() {
  getLinkFuture();
}

function allFilter() {
  getLinkAll();
 
}


const dateFilter =  (FilterDate) =>  {
getLinkAllNoPage(FilterDate);
}

    
function removeToken() {
  localStorage.removeItem('jwtToken')
  window.location.reload()

}

const isActivePopup2 = ref(false)

</script>

<template>
  <div>
<goToLogin/>
    <PopupPage v-show="isActivePopup2" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
        โปรดเข้าสู่ระบบใหม่
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , removeToken ()" />
        </div>
      </div>
      </PopupPage>

    <PopupPage v-show="isActivePopup" :dim-background="true">
    <div class="grid grid-cols-1 p-12" v-if="TokenTimeOut==true">
        โปรดเข้าสู่ระบบใหม่
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , removeToken ()" />
        </div>
      </div>

    </PopupPage>

    <ShowList
      :eventLists="eventLists.content"
      colNum="md:grid-cols-4"
      :numPage="numPage"
      @paging="paging"
      @pastFilter="pastFilter"
      @futureFilter="futureFilter"
      @allFilter="allFilter"
      @dateFilter="dateFilter"
    />
  </div>
</template>

<style></style>
