<script setup>
import { useRouter } from "vue-router";
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";

const router = useRouter();

const jwtToken = ref()
onBeforeMount(async () => {

  jwtToken.value = localStorage.getItem('jwtToken');
  if(jwtToken.value==null) {
    goHome()
  }
  
 

});

const goHome = () => {

router.push({
  name: "Login"
 
});

const goBack = () => appRouter.go(-1);


};
</script>
 
<template>

</template>
 
<style>

</style>