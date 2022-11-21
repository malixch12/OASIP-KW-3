<script setup>
const props = defineProps({
  dimBackground: {
    type: Boolean,
    default: false
  },
})
</script>
 
<template>
  <div class="z-50 popup-container " :class="{ 'bg-black/80': props.dimBackground } ">
    <div class="popup">
      <slot>
        
      </slot>
    </div>
  </div>
</template>
 
<style scoped>
.popup-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 100;
}

.popup {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -60%);
  border-radius: 8px;
  background-color: white;
  /* padding-top: 20px;
  padding-left: 70px;
  padding-right: 70px;
  padding-bottom: 60px; */
  margin: auto auto;
}
</style>