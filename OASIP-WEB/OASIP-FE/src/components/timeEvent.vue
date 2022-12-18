<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";

// const myRouter = useRoute();
const router = useRouter();
const props = defineProps({
  cate: {
    type: Object,
  }
});


onBeforeUpdate(async () => {

});

const UserRole =ref()

onBeforeMount(async () => {
    getLinkAll()
    UserRole.value = localStorage.getItem('UserRole');
});


const style = "flex flex-wrap   justify-content-center grid grid-cols-1";

// filter
const timeCheck = ref({content:""})

const getLinkAll = async () => {
  //RefreshToken()
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/datetime/future`,
  {

method: 'get',
headers: {

  'Content-Type': 'application/json',
  
}
});
  if (res.status === 200) {
    timeCheck.value = await res.json();
    console.log(timeCheck.value)
    addAlldate()
    
  }

};


const alldate = ref([])

function addAlldate () {

    timeCheck.value.forEach(element => {
        if(element.eventCategory==props.cate.categoryName  ) {
            alldate.value.push(element.date)

        }

   
});
let uniqueChars = [...new Set(alldate.value)];

alldate.value = uniqueChars

}


</script>

<template>
  <div>
      
    
   <div class="flex justify-center  bg-gray-100 ">
  <div class="bg-white rounded-lg w-full p-4 shadow">


    <div v-for="date in alldate">
      <span class="text-gray-900 relative inline-block date uppercase font-medium tracking-widest">{{date}}</span>

      <div v-for="time in timeCheck">
      <div class="flex mb-2" v-if="time.date == date & cate.categoryName == time.eventCategory" >
        <div class="w-2/12">
          <span class="text-sm text-gray-600 block"> {{ new Date(time.eventStartTime).toLocaleTimeString("th-TH") }}</span>
          <span class="text-sm text-gray-600 block">{{new Date(time.endTime).toLocaleTimeString("th-TH")}}</span>
        </div>
        <div class="w-1/12">
          <span class="bg-blue-400 h-2 w-2 rounded-full block mt-2"></span>
        </div>
        <div class="w-9/12">
          <span class="text-sm font-semibold block">{{time.eventCategory}}</span>
          <span class="text-sm">{{time.eventDuration}} min</span>
        </div>
      </div>

      </div>
      
    </div>



  </div>
</div>


  </div>

</template>

<style>
body {
  font-size: 20px;
}

.date:after {
  content: "";
  position: absolute;
  border-top: 1px solid #e2e8f0;
  top: 12px;
  width: 150px;
}

.date:after {
  margin-left: 15px;
}
</style>
