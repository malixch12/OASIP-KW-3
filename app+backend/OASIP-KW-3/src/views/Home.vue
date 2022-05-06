<script setup>
import ButtonLink from "../components/ButtonLink.vue";

</script>

<template>
  <div>
    <div class="bg-white shadow-xl rounded-lg ml-24 mr-24 p-12">
      <div class="text-7xl font-bold col-span-1 text-center">Clinic</div>

      <div class="col-span-1 text-center">
        Website for booking clinics of INT221 subject
      </div>
   
      <br />
      <div class="col-span-1 grid grid-cols-3">
        <ButtonLink name="Book a clinic" link="Booking"/>
        <ButtonLink name="Our book information" />
        <ButtonLink name="Check all book" link="ListAllEvent" />
      </div>
    </div>
  </div>
</template>

<style>

</style>
