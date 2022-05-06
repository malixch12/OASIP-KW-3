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
     <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
        <div class="text-2xl font-bold text-center">
        {{name}}
      </div>

  <div class="grid grid-cols-3 gap-6 place-items-center my-12">
  <div><img src="../assets/fontEnd.png"  @click="addList(6)" class="w-64"> <div class="text-center mt-4">FrontEnd Clinic</div></div>
  <div><img src="../assets/backend.png"  @click="addList(5)" class="w-64"> <div class="text-center mt-4">BackEnd Clinic</div></div>
  <div><img src="../assets/database.png" @click="addList(3)" class="w-64"> <div class="text-center mt-4">Database Clinic</div></div>
  <div><img src="../assets/devop.png"  @click="addList(2)" class="w-64"><div class="text-center mt-4">DevOps-Infra Clinic</div> </div>
  <div><img src="../assets/BR.png" @click="addList(4)" class="w-64 "><div class="text-center mt-4">Business Requirements</div> </div>
  <div><img src="../assets/PM.png"  @click="addList(1)" class="w-64"><div class="text-center mt-4">Project Managements</div> </div>
</div>
</div>
   </div>
</template>
 
<style>

</style>