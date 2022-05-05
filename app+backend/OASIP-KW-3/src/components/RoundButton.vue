<script setup>
defineProps({
    buttonName:{
        type: String,
        require: true
    },
    bgColor:{
        type:String
    }
})

const style = "rounded-3xl px-5 text-white"


</script>
 
<template>
<div>
    <button :class="[style,bgColor]">{{ buttonName }}</button>
</div>
</template>
 
<style>

</style>