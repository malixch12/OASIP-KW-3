<script setup>
import ButtonLink from "../components/ButtonLink.vue";

</script>

<template>
  <div>
    <div class="bg-white rounded-lg space-y-7 shadow-xl  ml-24 mr-24 p-12">
      <div class="text-7xl font-bold col-span-1 text-center">Clinic</div>

      <div class="col-span-1 text-center">
        Website for booking clinics of INT221 subject

      </div>
      <div class="col-span-1 grid grid-cols-1 place-items-center pt-5"><img src="../assets/logoHome.gif" class="w-2/5" /></div>
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
