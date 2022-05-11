<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const props = defineProps({
  name: {
    type: String,
    require: false,
  },
   categorys: {
    type: Array,
    require: true,
  } ,
  type: {
    type: String,
    require: true
  }


});

const addList = (categoryId , eventCategoryName , eventDuration) => {
  if(props.type=="ShowList") {
router.push({
    name: "ShowList",
    query: { categoryId: categoryId , eventCategoryName : eventCategoryName },
  });
  }
  
  if(props.type=="Booking"){
    router.push({
    name: "Booking",
    query: { categoryId: categoryId , eventCategoryName: eventCategoryName , eventDuration: eventDuration },
  });
  }
  
};



const pic2 = ["../../public/backend.png"]


</script>
 
<template>
   <div>
     <div class="bg-white shadow-xl rounded-b-lg ml-24 mr-24 p-12">
        <div class="text-2xl font-bold text-center">
        {{name}}
      </div>

  <div class="grid grid-cols-3 gap-6 place-items-center my-12">
 

      <div v-for="category in categorys" :key="category.eventCategoryID">
      
      <div><img :src="`../../public/`+category.eventCategoryID+`.png`"  @click="addList(category.eventCategoryID,category.eventCategoryName,category.eventDuration)" class="w-64"> <div class="text-center mt-4">{{ category.eventCategoryName }}</div>
   
      </div>

  
</div>


</div>
</div>
   </div>
</template>
 
<style>

</style> 