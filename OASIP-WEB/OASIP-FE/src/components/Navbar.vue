<script setup>
import { ref , onBeforeMount } from "vue";

function push() {
  router.push({
    name: "ShowList",
    query: { categoryId: categoryId },
  });
}

const countTime = ref('');
function setTime() {
var today = new Date()
var now_date = (today.getDate() + '/' + (today.getMonth()+1) + '/' + (today.getFullYear()+543 ));
var now_time = (today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()).toString("th-TH")
  countTime.value = now_date + " - " + now_time
}

const loginCheck = ref()
const jwtToken = ref()
const UserRole = ref()
const micosoft = ref()
onBeforeMount(async () => {
setInterval(setTime, 1);
jwtToken.value = localStorage.getItem('jwtToken');
UserRole.value = localStorage.getItem('UserRole');
micosoft.value = localStorage.getItem('micosoft');
if(jwtToken.value != null) {
  loginCheck.value = false
}else
loginCheck.value = true
});

function logout () {
  localStorage.clear();



  window.location.reload()

}

const open = ref(false)

function toggle () {
    if(open.value==false){
      open.value=true
    }else
    open.value=false
}

</script>
 
<template>


  <div class="font-sans antialiased " id="app">
  <nav class="container flex flex-wrap justify-between items-center mx-auto mt-4">
    <div class="flex items-center flex-no-shrink text-white mr-6">
      <router-link :to="{ name: 'Home' }" class="flex items-center ">
      <img src="../assets/logo-removebg-preview.png" class="w-14 ml-4"/>   </router-link>
  
    </div>
    <div class="md:hidden">
      <button @click="toggle ()" class="mr-4 bg-white  flex items-center px-3 py-2 border rounded text-teal-lighter border-black hover:text-black hover:border-red-300">
        <svg class="fill-current h-3 w-3" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>Menu</title><path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"/></svg>
      </button>
    </div>
    <div :class="open ? 'block': 'hidden'" class="w-full  flex-warp md:flex md:items-center md:w-auto">
      <div class="text-sm sm:flex-grow ">
        <ul class="  flex flex-col p-4 mt-4  rounded-lg border border-gray-300 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium md:border-0 ">
          <li>
            <router-link
              :to="{ name: 'Home' }"
              class="
                block

                text-gray-700
                md:hover:text-pink-500 md:p-0
              "
              >🏠&nbspHome</router-link
            >
          </li>
          <li v-if=" (UserRole!=`Lecturer` &  UserRole!=`Guest`) " >
            <router-link
              :to="{ name: 'BookingEventByCate' }"
              class="
                 block
                text-gray-700
                
                md:hover:text-pink-500 md:p-0
              "
              >⏰&nbsp booking</router-link
            >
          </li>

          <li v-if="!loginCheck & UserRole!=`Guest` ">
            <router-link
              :to="{ name: 'ListAllByCate' }"
              class="
                 block
                text-gray-700
               
                md:hover:text-pink-500 md:p-0  
              "
              ><span >📅&nbsp check appointments</span>
            </router-link >
          </li>

          <li v-if="UserRole==`Guest` ">
            <router-link
              :to="{ name: 'GuestViewEvent' }"
              class="
                 block
                text-gray-700
               
                md:hover:text-pink-500 md:p-0  
              "
              ><span >📅&nbsp check appointments</span>
            </router-link >
          </li>

             <li>
            <router-link v-show="!loginCheck && UserRole==`Admin`"
              :to="{ name: 'UserManage' }"
              class="
                 block
                text-gray-700
               
                md:hover:text-pink-500 md:p-0 
              "
              ><span >👤 &nbsp user manage</span>
            </router-link>
          </li>
              <li >
            <router-link 
              :to="{ name: 'Login' }"
              class="
                  block
                  text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600 
                
              "
              ><span v-if="!loginCheck">my profile</span> <span v-if="loginCheck">Login</span> 
            </router-link>
          </li>
          <!-- <li v-if="loginCheck">
            <router-link 
              :to="{ name: 'SignUpPage' }"
              class="
                 block
                text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600 
               
                
              "
              >sign up
            </router-link>
          </li> -->
          <li>
            <div v-if="!loginCheck && micosoft ==null"  @click="logout()"
             
              class="
                 block
                text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600 
               
                
              "
              >     Log out
            </div>
            <div v-if="!loginCheck && micosoft==`true`"  @click="logout()"
             
             class="
             block
                text-gray-700
               
                md:hover:text-pink-500 md:p-0  
              
               
             "
             >    <a href="http://localhost:3000/kw3/#/logoutPage" target="_blank"> Log out </a> 
           </div>
          </li>
          
          <li>
            <span
              :to="{  }"
              class="
              text-gray-400 
              "
              >{{countTime}}</span
            >
          </li>
    </ul>
      </div>
     
    </div>
  </nav>
  
</div>
</template>
 
<style scoped>
.router-link-active {
  color: rgb(93, 176, 209);
  /* border-color: #0a0e0b; */
}



</style>