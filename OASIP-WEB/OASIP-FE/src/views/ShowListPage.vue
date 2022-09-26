<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
import PopupPage from "../components/PopupPage.vue";
import { useRouter } from "vue-router";
const router = useRouter();
const route = useRoute();
const eventLists = ref({content:null});
const page = ref(0);
const numPage = ref();
const jwtToken = ref()
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
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
    console.log("โทเค้นหมดอายุ")
    let jwtTokenRF = await res.json()
    localStorage.setItem('jwtToken', jwtTokenRF.jwttoken);
    jwtToken.value = localStorage.getItem('jwtToken');
  } else
    if(res.status === 401) {
    
     
    }

};

const getLink = async () => {
  await RefreshToken()

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/categories/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8` ,{

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
    const TokenValue = ref( await res.json())
    console.log("status from backend = " +  TokenValue.value.message )
    if (TokenValue.value.message == "Token is expired") {

  
    }
    if (TokenValue.value.message == "Token incorrect" & jwtToken.value != null) {

      localStorage.removeItem('jwtToken')
    localStorage.removeItem('time')
    TokenValue.value = "x"
    TokenTimeOut.value = true
    isActivePopup.value = true

    }
    if (TokenValue.value.message == "Please log in for get Token again." ) {

localStorage.removeItem('jwtToken')
localStorage.removeItem('time')
TokenValue.value = "x"
TokenTimeOut.value = true
isActivePopup.value = true
    }}
};

onBeforeMount(async () => {
  jwtToken.value = localStorage.getItem('jwtToken');
  getLink();
});


function paging(index , filter) {
  page.value = index;

  if (filter == 3) {
    getLink();
  }
  if (filter == 2) {
    getLinkFuture();
  }
  if (filter == 1) {
    getLinkPast();
  }
}
//http://localhost:8080/api/events/category/1?pageSize=4

const getLinkPast = async () => {
  await RefreshToken()

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/categories/pastdays/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8` ,
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
    const TokenValue = ref( await res.json())
    console.log("status from backend = " +  TokenValue.value.message )
    if (TokenValue.value.message == "Token is expired") {

  
    }
    if (TokenValue.value.message == "Token incorrect" & jwtToken.value != null) {

      localStorage.removeItem('jwtToken')
    localStorage.removeItem('time')
    TokenValue.value = "x"
    TokenTimeOut.value = true
    isActivePopup.value = true

    }
    if (TokenValue.value.message == "Please log in for get Token again." ) {

localStorage.removeItem('jwtToken')
localStorage.removeItem('time')
TokenValue.value = "x"
TokenTimeOut.value = true
isActivePopup.value = true
    }}
};

const getLinkFuture = async () => {
  await RefreshToken()

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/categories/futuredays/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8`,
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
    console.log(eventLists.value);
  }
  if (res.status === 401) {
    const TokenValue = ref( await res.json())
    console.log("status from backend = " +  TokenValue.value.message )
    if (TokenValue.value.message == "Token is expired") {

  
    }
    if (TokenValue.value.message == "Token incorrect" & jwtToken.value != null) {

      localStorage.removeItem('jwtToken')
    localStorage.removeItem('time')
    TokenValue.value = "x"
    TokenTimeOut.value = true
    isActivePopup.value = true

    }
    if (TokenValue.value.message == "Please log in for get Token again." ) {

localStorage.removeItem('jwtToken')
localStorage.removeItem('time')
TokenValue.value = "x"
TokenTimeOut.value = true
isActivePopup.value = true
    }
  }
};

function pastFilter() {
  getLinkPast();
}

function futureFilter() {
  getLinkFuture();
}

function allFilter() {
  getLink();
}


const dateFilter =  (FilterDate) =>  {
getLinkAllNoPage(FilterDate);
}

const getLinkAllNoPage = async (FilterDate) => {
  await RefreshToken()

  // const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`);

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events?page=${page.value}&pageSize=100000`,
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
      numPage.value = Math.ceil(eventLists.value.content.length / 8);
  }
  if (res.status === 401) {
    const TokenValue = ref( await res.json())
    console.log("status from backend = " +  TokenValue.value.message )
    if (TokenValue.value.message == "Token is expired") {

  
    }
    if (TokenValue.value.message == "Token incorrect" & jwtToken.value != null) {

      localStorage.removeItem('jwtToken')
    localStorage.removeItem('time')
    TokenValue.value = "x"
    TokenTimeOut.value = true
    isActivePopup.value = true

    }
    if (TokenValue.value.message == "Please log in for get Token again." ) {

localStorage.removeItem('jwtToken')
localStorage.removeItem('time')
TokenValue.value = "x"
TokenTimeOut.value = true
isActivePopup.value = true
    }
  }
};


function removeToken() {
  localStorage.removeItem('jwtToken')
  window.location.reload()

}
</script>

<template>
  <div>
    
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
      colNum="grid-cols-4"
      :name="route.query.eventCategoryName"
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
