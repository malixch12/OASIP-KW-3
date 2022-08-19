<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";



onBeforeMount( () => {
 setInterval(function () {
  const show = document.querySelector('span[data-show]')
  const next = show.nextElementSibling || document.querySelector('span:first-child')
  const up = document.querySelector('span[data-up]')
  
  if (up) {
    up.removeAttribute('data-up')
  }
  
  show.removeAttribute('data-show')
  show.setAttribute('data-up', '')
  
  next.setAttribute('data-show', '')
}, 2000)

});


const test = '#0e6ffc'
</script>
 
<template>
  <div >
 
<div >Clinic booking</div>
   <div class="wrapper">
       
       
       <div class="words">
           <span>Server-side Clinic.</span>
           <span>Project Management</span>
           <span>DevOps/Infra Clinic.</span>
           <span>Database Clinic.</span>
           <span>Client-side Clinic.</span>
           <span>Project Management</span>
       </div>
   </div>

  </div>
</template>
 
<style scoped>
*,
*:before,
*:after{
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    font-family: Helvetica;
    width: 980px;
  font-size: 80px;
    font-weight: 500;
}
body{
    background-color: #0e6ffc;
    height: 100vh;
    display: grid;
    place-items: center;
}
.wrapper{
    box-sizing: content-box;
   
    height: 125px;
   
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

