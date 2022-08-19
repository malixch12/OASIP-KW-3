<script setup>
import ButtonLink from "../components/ButtonLink.vue";
import Navbar from '../components/Navbar.vue'
import Apple from '../components/Apple.vue'

</script>

<template>
  <div>
   

    <div class=" space-y-7   mt-24 ml-24 mr-24 p-12">
      <!-- <div class="text-7xl font-bold col-span-1 text-center">Clinic</div>

      <div class="col-span-1 text-center">
        Website for booking clinics of INT221 subject

      </div> -->
      <Apple/>
      <br />

      <div class="text-white font-bold grid grid-cols-2">
        <ButtonLink name="Book a Clinic" link="BookingEventByCate" class="justify-self-end"/>
        <ButtonLink name="Check Appointments" link="ListAllByCate" class="justify-self-start"/>
        
      </div>
    </div>
  </div>
</template>

<style>
</style>
