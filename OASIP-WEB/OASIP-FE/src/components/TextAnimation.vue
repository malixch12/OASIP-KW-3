<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";

</script>
 
<template>
  <div >
 
<div class="font-bold drop-shadow-lg">Select  clinics</div>
   <div class="wrapper">
       
       
       <div class="words drop-shadow-lg">
           <span>Server-side Clinic.</span>
           <span>Project Management</span>
           <span>DevOps/Infra Clinic.</span>
           <span>Database Clinic.</span>
           <span>Client-side Clinic.</span>
           <span>Project Management</span>
       </div>
   </div>
<div class="font-bold drop-shadow-lg ">and booking</div>
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
  font-size: 80px;
    
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
    
    color: #0e6ffc;
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

