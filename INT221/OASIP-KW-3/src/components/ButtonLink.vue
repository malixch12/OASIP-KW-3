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
<button @click="GotoLink" type="button" class=" bg-gradient-to-r from-green-400 to-blue-500 hover:from-pink-500 hover:to-yellow-500 h-14 w-80 pr-4 pl-4 mr-4 rounded-lg">
 {{name}}
</button>

</div>
</template>
 
<style>

</style>