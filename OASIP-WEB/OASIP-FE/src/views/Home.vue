<script setup>
import ButtonLink from "../components/ButtonLink.vue";
import Navbar from '../components/Navbar.vue'
import Apple from '../components/TextAnimation.vue'
import { onBeforeMount, onBeforeUpdate, ref } from "vue";

const UserRole = ref()
onBeforeMount(() => {
  UserRole.value = localStorage.getItem('UserRole');
});

</script>

<template>
  <div>
    <div class="md:invisible sm:visible visible md:hidden text-8xl">
      <div class="flex mt-48 ">
  <div class="m-auto">
    <div>
    <div class="text-2xl">Welcome to</div>
<div class="start text-6xl font-bold	mt-3">
   
        <span class="end1 text-red-400">C</span><span class="middle1">LINICBOO</span>
        <span class="middle2"><i class="fas fa-code"></i></span><span class="end2 text-yellow-500">K</span>
    </div>
    <div class="text-2xl">    website  🥳🎉
      <p class="text-neutral-500 text-sm	drop-shadow-lg"> booking and managing your clinic in 222
          subjects.</p>
    </div>
    <div class="text-white font-bold text-base mt-4 ">
          <div class="flex flex-wrap">

            <ButtonLink name="Login ->" link="Login" addStyle="bg-gradient-to-r from-yellow-100 to-rose-300 text-gray-700" class="justify-start" />
            <ButtonLink v-if="UserRole!=`Guest`" name="Event check" link="ListAllByCate" addStyle="bg-white text-neutral-500"    class="justify-self-start " />
            <ButtonLink v-if="UserRole==`Guest`" name="Event check" link="GuestViewEvent" addStyle="bg-white text-neutral-500"    class="justify-self-start " />

          </div>
        </div>
    </div>
    
  </div>
</div>
      </div>


    <div
      class=" flex flex-wrap text-gray-900 space-y-7  items-center  mx-24  relative mt-12 md:visible  invisible ">

      <!-- <div class="text-7xl font-bold col-span-1 text-center">Clinic</div>

      <div class="col-span-1 text-center">
        Website for booking clinics of INT221 subject

      </div> -->

      <div class="ml-14 mt-14 w-7/12 ">
        <Apple />
        <p class="text-neutral-500 text-2xl	drop-shadow-lg">A website for booking and managing your clinic in 222
          subjects.</p>

        <br />

        <div class="text-white font-bold ">
          <div class="flex flex-wrap">
            <ButtonLink name="Login ->" link="Login" addStyle="bg-gradient-to-r from-yellow-100 to-rose-300 text-gray-700" class="justify-start" />
              <ButtonLink  v-if="UserRole!=`Guest`" name="Event check" link="ListAllByCate" addStyle="bg-white text-neutral-500"  class="justify-self-start " />
              <ButtonLink v-if="UserRole==`Guest`" name="Event check" link="GuestViewEvent" addStyle="bg-white text-neutral-500"  class="justify-self-start " />

         
            </div>
        </div>
      </div>

      <div class=" w-80 ">

        <div class="    ">
          <div class="relative w-full max-w-lg ">
            <div
              class="absolute top-0 -left-4 w-72 h-72 bg-red-300 rounded-full mix-blend-multiply filter blur-xl opacity-80 animate-blob">
            </div>
            <div
              class="absolute top-0 -right-4 w-72 h-72 bg-yellow-300 rounded-full mix-blend-multiply filter blur-xl opacity-80 animate-blob animation-delay-1000">
            </div>
            <div
              class="absolute -bottom-8 left-20 w-72 h-72 bg-pink-200 rounded-full mix-blend-multiply filter blur-xl opacity-80 animate-blob animation-delay-2000">
            </div>
            <div class="m-8 fixed space-y-4 md:visible invisible">
            </div><img src="../assets/logo-removebg-preview.png "
              class="drop-shadow-2xl transition duration-700 ease-in-out hover:-translate-y-1 hover:scale-110 duration-700" />
          </div>
        </div>
      </div>

    </div>


     



  </div>

</template>

<style>
div .color2 {
  position: absolute;
  filter: blur(300px);
}

div .color2:nth-child(1) {

  width: 400px;
  height: 400px;

  background: #ffbf00
}



div .color2:nth-child(3) {
  bottom: 50px;
  right: 100px;
  height: 500px;
  width: 500px;
  background: #e9a3e4;

}

.end1, .middle1, .middle2{
    display: inline-block;
    overflow:hidden;
   
}
.end2{
    position:absolute;
   
}
.middle1, .middle2{
    color: #2f2a2a
}
.middle1 {
    animation: ani1 5s infinite;
    animation-delay: -3s;
    max-width: 0;
}
.middle2 {
    animation: ani2 5s infinite;
    animation-delay: -3s;
    max-width: 0;
}
@keyframes ani1 {
    0% {
    max-width: 500px;
  }
  
  25% {
    max-width: 0;
  }
  
  75% {
    max-width: 0;
  }
  
  90% {
    max-width: 500px;
  }
  
  100% {
    max-width: 500px;
  }
}
@keyframes ani2 {
    0% {
        max-width: 0;
    }
    25% {
        max-width: 0;
    }
    50% {
        max-width: 500px;
    }
    75% {
        max-width: 0;
    }
    100% {
        max-width: 0;
    }
}
</style>
