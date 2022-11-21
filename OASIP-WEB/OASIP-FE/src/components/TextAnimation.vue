<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";

</script>
 
<template>
  <div class=""  >
 
<div class="font-bold drop-shadow-lg text-[80px] ">Select  clinics</div>
   <div class="wrapper">
       
       
       <div class="words drop-shadow-lg  text-[80px]">
           <span class="text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600 ">Server-side Clinic.</span>
           <span class="text-transparent  bg-clip-text bg-gradient-to-r from-red-400 to-orange-600 ">Project Management</span>
           <span class="text-transparent  bg-clip-text bg-gradient-to-r from-blue-400 to-pink-600 ">DevOps/Infra Clinic.</span>
           <span class="text-transparent  bg-clip-text bg-gradient-to-r from-gray-400 to-green-600 ">Database Clinic.</span>
           <span class="text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-red-600 ">Client-side Clinic.</span>
           <span class="text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-blue-600 ">Project Management</span>
       </div>
   </div>
<div class="font-bold drop-shadow-lg text-[80px] ">and booking</div>
  </div>
</template>
 
<style scoped>
*,
*:before,
*:after{
    padding: 0;
    margin: -5px;
    box-sizing: border-box;
    font-family: Helvetica;
    width: 980px;
  
    
}
body{
    background-color: #0e6ffc;
    height: 100vh;
    display: grid;
    place-items: center;
    
}
.wrapper{
    box-sizing: content-box;
   
    height: 80px;
   
    display: flex;

   
}
.words{
    overflow: hidden;
}
span{
    display: block;
    height: 100%;
    
   
    animation: spin_words 10s infinite;
}

@keyframes spin_words{
    10%{
        transform: translateY(-112%);
    }
    25%{
        transform: translateY(-100%);
    }
    35%{
        transform: translateY(-212%);
    }
    50%{
        transform: translateY(-200%);
    }
    60%{
        transform: translateY(-312%);
    }
    75%{
        transform: translateY(-300%);
    }
    85%{
        transform: translateY(-412%);
    }
    100%{
        transform: translateY(-400%);
    }
}
</style>

