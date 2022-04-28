<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const props = defineProps({
  name: {
    type: String,
    require: false,
  },
});

const addList = (categoryId , categoryName) => {
  router.push({
    name: "ShowList",
    query: { categoryId: categoryId },
  });
};

</script>
 
<template>
   <div>
        <div class="text-2xl font-medium mt-24 mx-8 text-center">
        {{name}}
      </div>

    <div class="flex justify-around grid grid-cols-3 gap-4 mt-24 mx-8 mb-24 ">
  <div><img src="../assets/fontEnd.png"  @click="addList(6,)" class="w-64 mx-20"> <div class="text-center mt-4">FrontEnd Clinic</div></div>
  <div><img src="../assets/backend.png"  @click="addList(5)" class="w-64 mx-20"> <div class="text-center mt-4">BackEnd Clinic</div></div>
  <div><img src="../assets/database.png" @click="addList(3)" class="w-64 mx-20"> <div class="text-center mt-4">Database Clinic</div></div>
  <div><img src="../assets/devop.png"  @click="addList(2)" class="w-64 mx-20"><div class="text-center mt-4">DevOps-Infra Clinic</div> </div>
  <div><img src="../assets/BR.png" @click="addList(4)" class="w-64 mx-20"><div class="text-center mt-4">Business Requirements</div> </div>
  <div><img src="../assets/PM.png"  @click="addList(1)" class="w-64 mx-20"><div class="text-center mt-4">Project Managements</div> </div>
</div>

   </div>
</template>
 
<style>

</style>