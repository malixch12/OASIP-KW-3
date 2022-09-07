<script setup>
import ButtonLink from "../components/ButtonLink.vue";
import Navbar from '../components/Navbar.vue'
import Apple from '../components/TextAnimation.vue'

</script>

<template>
  <div>
   

    <div class="grid grid-cols-2  text-gray-900 space-y-7  items-center  ml-36  relative mt-12">
      <div class="color2"> </div>
      <div class="color2"></div>
      <div class="color2"></div>
     <!-- <div class="text-7xl font-bold col-span-1 text-center">Clinic</div>

      <div class="col-span-1 text-center">
        Website for booking clinics of INT221 subject

      </div> -->
          
      <div class="mt-24"> <Apple/><p class="text-neutral-500 text-2xl	drop-shadow-lg">A website for booking and managing your clinic in 222 subjects.</p>

      <br />

      <div class="text-white font-bold grid grid-cols-2">
        <div class="grid grid-cols-2">
        <ButtonLink name="Sign up ->" link="SignUpPage" addStyle="bg-red-400" class="justify-start"/>
        <ButtonLink name="Appointments" link="ListAllByCate" addStyle="bg-white text-neutral-500" class="justify-self-start ml-8"/>
        </div>
      </div></div>
     
      <div class="ml-24">  <img src="../assets/logo-removebg-preview.png " class="drop-shadow-2xl transition duration-700 ease-in-out hover:-translate-y-1 hover:scale-110 duration-700"/></div>
   
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
  background:  #f56e56;
  
}


</style>
