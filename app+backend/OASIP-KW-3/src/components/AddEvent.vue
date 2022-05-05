<script setup>
import RoundButton from '../components/RoundButton.vue'
import { ref , onBeforeMount, onBeforeUpdate } from 'vue'
  const props = defineProps ({
    id:{
        type:Number 
        
    } 
})
defineEmits(['addEvent'])

onBeforeUpdate( () => {
  dataBooking.value.bookingId = props.id+1
});

const dataBooking = ref({
        "bookingId": "",
        "bookingName": "",
        "bookingEmail": "",
        "eventCategory": "DevOps/Infra Clinic",
        "eventStartTime": null,
        "eventDuration": 30,
        "eventNotes": "",
        "eventCategoryID": 2  
    })
 
// var tester = /^[-!#$%&'*+\/0-9=?A-Z^_a-z{|}~](\.?[-!#$%&'*+\/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-*\.?[a-zA-Z0-9])*\.[a-zA-Z](-?[a-zA-Z0-9])+$/;
// function validateEmail(email)
// {
//     if (!email)
//         return false;
        
//     if(email.length>254)
//         return false;
//     var valid = tester.test(email);
//     if(!valid)
//         return false;
//     // Further checking of some things regex can't handle
//     var parts = email.split("@");
//     if(parts[0].length>64)
//         return false;
//     var domainParts = parts[1].split(".");
//     if(domainParts.some(function(part) { return part.length>63; }))
//         return false;
//     return true;
// }

// console.log(validateEmail("asdsad"))
</script>
 
<template>
<div class="space-y-7">
    
    <br>
    <h2 class="text-1xl ">Information for booking clinic</h2>
    <p>Name : <input type="text" v-model="dataBooking.bookingName" class="border-double border-2 border-sky-500 w-96 rounded-lg"/></p>
    <p>Email : <input type="text" v-model="dataBooking.bookingEmail" class="border-double border-2 border-sky-500 w-96 rounded-lg"></p>
    <hr>
    <p class="text-1xl " >Date and Time for booking clinic</p>
    <p>DateTime : <input type="datetime-local" v-model="dataBooking.eventStartTime" class="border-double border-2 border-sky-500 w-96 rounded-lg"></p>
    <p >Duration for booking : <input type="number" v-model="dataBooking.eventDuration" class="border-double border-2 border-sky-500 w-96 rounded-lg"></p>
    <p class="text-xs text-red-600">* Duration for booking can not exceed 30 minutes.*</p>
    <p>Message to Advisor : <input type="text" v-model="dataBooking.eventNotes" class="border-double border-2 border-sky-500 w-96 rounded-lg"></p>


 <div  class=" grid grid-cols-2 w-48 ml-52">
<RoundButton bg-color="bg-emerald-300 w-25" button-name="add" @click="$emit('addEvent' , dataBooking )"/>
<RoundButton bg-color="bg-rose-300 w-25" button-name="cancel" @click="vfgth"/>
</div>




</div>
</template>
 
<style>

</style>