<script setup>
defineProps({
    buttonName:{
        type: String,
        require: true
    },
    bgColor:{
        type:String
    } ,
    addStyle:{
        type:String
    }
})

const style = "rounded-3xl px-5  shadow-xl"
const addStyle = ""


</script>
 
<template>
<span>
    <button :class="[style,bgColor,addStyle]">{{ buttonName }}</button>
</span>
</template>
 
<style>

</style>