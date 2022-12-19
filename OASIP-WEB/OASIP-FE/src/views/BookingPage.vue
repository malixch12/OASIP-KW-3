<script setup>
import AddEvent from "../components/AddEvent.vue";
import { onBeforeMount, onBeforeUpdate, ref } from "vue";
import ShowList from "../components/ShowList.vue";
import { useRoute } from "vue-router";
import PopupPage from "../components/PopupPage.vue"
import RoundButton from "../components/RoundButton.vue";
import timeEvent from "../components/timeEvent.vue";
import goToLogin from "../components/goToLogin.vue";

import { useRouter } from "vue-router";


const route = useRoute();
const router = useRouter();

const eventLists = ref({content:null});
const id = ref();
const page = ref(0)
const numPage = ref( )
const jwtToken = ref()
const jwtTokenRF = ref()
const getLinkAll = async () => {
  //RefreshToken()
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/categories/${route.query.categoryId}?page=${page.value}&pageSize=6`,
  {

method: 'get',
headers: {

  'Content-Type': 'application/json',
  'Authorization': 'Bearer ' + jwtToken.value
}
});
  if (res.status === 200) {
    eventLists.value = await res.json();
      numPage.value = Math.ceil(eventLists.value.totalElements / 6)
    
  } 
  if (res.status === 401) {
    await RefreshToken() 
    await getLinkAll() 

  }

};

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
  } else
isActivePopup2.value=true


};

const TokenTimeOut = ref(false)


onBeforeUpdate(() => {

});

const UserRole = ref()

onBeforeMount(() => {
  UserRole.value = localStorage.getItem('UserRole');
  jwtTokenRF.value = localStorage.getItem('jwtTokenRF');
  jwtToken.value = localStorage.getItem('jwtToken');
 
  if(UserRole.value!="Guest") {
    getLinkFuture();
  }
 
  
});
// const yourISODateTime = computed(() => {
//   test1.value = new Date(yourDateTime.value).toISOString();
//  return new Date(yourDateTime.value).toISOString();
// });
const CheckOverlap = ref(false)
const addEvent = async (dataBooking , AllDataCheck) => {
  dataBooking.eventStartTime=new Date(dataBooking.eventStartTime).toISOString();

  console.log(dataBooking)
  let formData = new FormData();
            formData.append("bookingEmail", dataBooking.bookingEmail);
            formData.append("bookingName", dataBooking.bookingName);
            formData.append("eventCategory", dataBooking.eventCategory);
            formData.append("eventCategoryID", dataBooking.eventCategoryID);
            formData.append("eventDuration", dataBooking.eventDuration);
            formData.append("eventNotes", dataBooking.eventNotes);
            formData.append("eventStartTime", dataBooking.eventStartTime);
            if(dataBooking.file!=null) {
              formData.append("file", dataBooking.file);
            }

         //   formData.append("file", "John123");


  if(AllDataCheck == true && UserRole.value!="Guest") {
  //  RefreshToken()
 //dataBooking.eventStartTime = "2024-11-30T05:55:00.000Z"
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`, {
    method: "POST",
    headers: {
      'Authorization': 'Bearer ' + jwtToken.value

    },
    body: formData,
  })
    if(res.status === 400) {
      console.log(...formData)
      console.log("overlap")
      OverlapTrue()
      console.log(CheckOverlap.value)
      isActivePopup.value = true
      

    }else
         OverlapFalse()
          getLinkFuture();
          isActivePopup.value = true

  }
  if(AllDataCheck == true && UserRole.value=="Guest") {
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events`, {
    method: "POST",
    headers: {
      "content-type": "application/json",

    },
    body: JSON.stringify(dataBooking),
  });
    if(res.status === 400) {
      console.log("overlap")
      OverlapTrue()
      console.log(CheckOverlap.value)
      isActivePopup.value = true
      

    }else
         OverlapFalse()
          
          isActivePopup.value = true

  }
};

function OverlapTrue (  ) {
CheckOverlap.value = true
}

function OverlapFalse (  ) {
CheckOverlap.value = false
}


const categoryDetail = {
  categoryId: route.query.categoryId,
  categoryName: route.query.eventCategoryName,
  categoryDuration: route.query.eventDuration,
};



function paging(index , filter) {
  page.value = index;

  if (filter == 3) {
    getLinkAll();
  }
  if (filter == 2) {
    getLinkFuture();
  }
  if (filter == 1) {
    getLinkPast();
  }
}


const getLinkPast = async () => {
  //RefreshToken()
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/categories/pastdays/${
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
  }
  if(res.status===401) {
    await RefreshToken() 
    await getLinkFuture() 
  }
};

const getLinkFuture = async () => {
 // RefreshToken()
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/categories/futuredays/${
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
    console.log(eventLists.value);
  } 
  if(res.status===401) {
    await RefreshToken() 
    await getLinkFuture() 
  }
};


const backToHome = () => {

  router.push({
    name: "Home"
   
  });

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
const isActivePopup = ref(false)
const isActivePopup2 = ref(false)


function removeToken() {
  localStorage.removeItem('jwtToken')
  localStorage.removeItem('jwtTokenRF')

  window.location.reload()
}

const dateFilter =  (FilterDate) =>  {
getLinkAllNoPage(FilterDate);
}

const getLinkAllNoPage = async (FilterDate) => {
 // await RefreshToken()

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
    await RefreshToken() 
    await getLinkAllNoPage() 
  }
};
</script>
 
<template>
  <div>
    <div class="md:flex md:justify-center   rounded">

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

      <PopupPage v-show="UserRole==`Lecturer`" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
        Lecturer ไม่สามารถใช้หน้านี้ได้
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , backToHome ()" />
        </div>
      </div>
      </PopupPage>


      

      <PopupPage v-show="isActivePopup == true" :dim-background="true">
       
           <div v-if="CheckOverlap" class="grid grid-cols-1 place-items-center text-slate-700 font-semibold text-center  p-10 space-y-5">
            <div>time overlap
              Please check again.</div>
            <RoundButton bg-color="bg-gray-400" button-name="ok" @click="isActivePopup = false" />
          </div> 

          <div  v-if="!CheckOverlap"> 
<div class="grid grid-cols-1 place-items-center text-slate-700 font-semibold text-center  p-10 space-y-5">
            <div>add success</div>
            <RoundButton bg-color="bg-green-400" button-name="ok" @click="isActivePopup = false" />
          </div>

          </div>
      
      </PopupPage>

      <div class="">
        <AddEvent class="px-2" @addEvent="addEvent"  :categoryDetail="categoryDetail"/>
      </div>
  <!-- <AddEvent :id="id" @addEvent="addEvent" @click="getLinkAll" :categoryDetail="categoryDetail"/> -->
  <div  v-if="UserRole!=`Guest`" class="md:block  hidden">   <ShowList 
        :eventLists="eventLists.content"
        colNum="grid-cols-2"
        class="col-span-2" :numPage = "numPage" @paging="paging"  @pastFilter="pastFilter"
      @futureFilter="futureFilter"
      @allFilter="allFilter"
      @dateFilter="dateFilter"
      :CheckOverlap="CheckOverlap"
      /></div> 

 <goToLogin/>

    </div>
  </div>
</template>
 
<style>
</style>