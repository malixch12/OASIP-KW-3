<script setup>
import timeEvent from "../components/timeEvent.vue";
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";

const CateLists = ref()
const getLinkAll = async () => {



  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/eventcategorys` ,{

method: 'get',
headers: {

'Content-Type': 'application/json'
}
}

);
if (res.status === 200) {
  CateLists.value = await res.json();
}
if (res.status === 401) {
  
  await RefreshToken()
  await getLinkAll()

}
}


onBeforeMount(async () => {

  await  getLinkAll()

});

</script>



<template>
<timeEvent :cate="CateLists" :role="`Guest`"/>

</template>