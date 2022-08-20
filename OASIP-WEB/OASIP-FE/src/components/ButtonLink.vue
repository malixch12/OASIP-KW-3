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
    ,
     addStyle:{
        type:String
     },


})

const myRouter=useRouter()

const GotoLink = () => myRouter.push({name :props.link})

const style = "drop-shadow-lg transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110  duration-150 h-14 w-44 pr-4 pl-4 mr-4 rounded-lg"


</script>
<template>
<div>
<button @click="GotoLink" type="button" :class="[style,addStyle,]">
{{name}}
</button>

</div>
</template>
 
<style>

</style>