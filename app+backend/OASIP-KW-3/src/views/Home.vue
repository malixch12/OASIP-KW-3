<script setup>
import ButtonLink from '../components/ButtonLink.vue'
</script>

<template>
<div>
      <div class="text-7xl font-medium  col-span-1 text-center">Clinic</div>
      
      <div class="col-span-1 text-center">
        Website for booking clinics of INT221 subject
      </div>
      <br />
       <div class="col-span-1 grid grid-cols-3">
      <ButtonLink name="book a clinic" />
      <ButtonLink name="our book information" />
      <ButtonLink name="check all book" link="ListAllEvent" />
       </div>
     
  </div>
</template>

<style>
@import url("https://fonts.googleapis.com/css?family=Rubik");

html,
body {
  font-family: "Rubik", sans-serif;
}

#app {
  font-family: "Rubik", sans-serif;
}</style>
