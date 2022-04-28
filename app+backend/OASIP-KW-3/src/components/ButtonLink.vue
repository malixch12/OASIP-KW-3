<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
const props = defineProps ({
    name:{
        type:String ,
        default: "button"
    } ,
     link:{
        type:String    
    }


})

const myRouter=useRouter()

const GotoLink = () => myRouter.push({name :props.link})



</script>
<template>
<div>
<button @click="GotoLink" type="button" class=" transition ease-in-out delay-150 bg-yellow-300 hover:-translate-y-1 hover:scale-110 hover:bg-red-200 duration-150 h-14 w-80 pr-4 pl-4 mr-4 rounded-lg">
 {{name}}
</button>

</div>
</template>
 
<style>

</style>