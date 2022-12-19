<script setup>
import { onBeforeMount, onBeforeUpdate, ref } from "vue";
import { useRouter } from "vue-router";
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";

const router = useRouter();

const props = defineProps({
  name: {
    type: String,
    require: false,
  },
  categorys: {
    type: Array,
    require: true,
  },
  type: {
    type: String,
    require: true,
  },
});

const isActivePopup = ref(false);  // pop up 

const CateDetail = ref({          //เอาไว้เก็บดีเทล เอาไว้แสดงเมื่อยูสเชอร์ต้องการดู
  eventCategoryID: 0,
  eventCategoryName: '',
  eventCategoryDescription: '0',
  eventDuration: 0
})


function ShowDetailCate(Duration, Description, id, name) {    //เอาไว้โชว์

  isActivePopup.value = true
  CateDetail.value.eventDuration = Duration
  CateDetail.value.eventCategoryID = id
  CateDetail.value.eventCategoryName = name
  if (Description == null) {
    CateDetail.value.eventCategoryDescription = "ไม่มีคำอธิบาย"
    console.log(Description)
  } else
    CateDetail.value.eventCategoryDescription = Description
}

const goEdit = (categoryId) => {

  router.push({
    name: "EditCatePage",
    query: { categoryId: categoryId },
  });

};


const SentToNextPage = (categoryId, eventCategoryName, eventDuration) => {
  if (props.type == "ShowList") {
    router.push({
      name: "ShowList",
      query: { categoryId: categoryId, eventCategoryName: eventCategoryName },
    });
  }

  if (props.type == "Booking") {
    router.push({
      name: "Booking",
      query: {
        categoryId: categoryId,
        eventCategoryName: eventCategoryName,
        eventDuration: eventDuration,
      },
    });
  }
};

const askShowList = ref(true)


if (props.type == "ShowList") {
  askShowList.value = false
}




</script>

<template>
  <div>

    <!-- popup -->
    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div class="flex justify-end p-5" @click="() => isActivePopup = false">
        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true" role="img"
          class="iconify iconify--iconoir" width="32" height="32" preserveAspectRatio="xMidYMid meet"
          viewBox="0 0 24 24">
          <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
            d="M6.758 17.243L12.001 12m5.243-5.243L12 12m0 0L6.758 6.757M12.001 12l5.243 5.243"></path>
        </svg>

      </div>

      <div class="mb-2 px-10 pb-10 ">
        <div class="text-2xl font-semibold text-rose-400 pb-5">
          {{ CateDetail.eventCategoryName }}
        </div>
        <span class="text-2xl font-semibold text-slate-600 tracking-wide">
          Duration :
        </span> {{ CateDetail.eventDuration }} นาที

        <div class="text-2xl font-semibold text-slate-600 tracking-wide py-5 ">
          Description : </div>
        <div class="w-64 text-lg">{{ CateDetail.eventCategoryDescription }} </div>
        <div class="flex justify-center max-w-lg mx-auto mt-5">
          <RoundButton bg-color="bg-gray-400 text-white" button-name="edit" @click="
            goEdit(CateDetail.eventCategoryID)
          " />
        </div>
      </div>

    </PopupPage>
    <!-- popup end -->


    <!-- หัวข้อ -->
    <div class=" rounded-b-lg ml-24 mr-24 p-5 rounded ">
      <div class="text-3xl font-bold text-center mb-10  drop-shadow-md" v-if="type == `Booking`">
        {{ name }}
      </div>


      <div class="md:block hidden">
        <div class="flex justify-center md:mb-8  " v-if="type == `Booking`">
          <ol class="items-center sm:flex  ">
            <li class="relative mb-6 sm:mb-0">
              <div class="flex items-center">
                <div
                  class="flex z-10 justify-center items-center w-6 h-6 bg-blue-300 rounded-full ring-0 ring-blue-900 bg-blue-900 sm:ring-8  shrink-0">

                  <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true"
                    role="img" class="iconify iconify--material-symbols" width="15" height="15"
                    preserveAspectRatio="xMidYMid meet" viewBox="0 0 24 24">
                    <path fill="#000"
                      d="M17.825 9L15 6.175l1.4-1.425l1.425 1.425l3.525-3.55l1.425 1.425ZM5 21V5q0-.825.588-1.413Q6.175 3 7 3h6v2H7v12.95l5-2.15l5 2.15V11h2v10l-7-3ZM7 5h6h-1Z">
                    </path>
                  </svg>
                </div>
                <div class="hidden sm:flex w-48 bg-gray-500 h-0.5 bg-gray-700"></div>

              </div>
              <div class="mt-3 sm:pr-8">
                <h3 class="text-lg font-semibold text-gray-500 text-white">select category</h3>
              </div>
            </li>


            <li class="relative mb-6 sm:mb-0">
              <div class="flex items-center">
                <div
                  class="flex z-10 justify-center items-center w-6 h-6 bg-blue-300 rounded-full ring-0 ring-blue-900 bg-blue-900 sm:ring-8  shrink-0">
                  <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true"
                    role="img" class="iconify iconify--mdi" width="15" height="15" preserveAspectRatio="xMidYMid meet"
                    viewBox="0 0 24 24">
                    <path fill="#000000"
                      d="M21.04 12.13c.14 0 .27.06.38.17l1.28 1.28c.22.21.22.56 0 .77l-1 1l-2.05-2.05l1-1c.11-.11.25-.17.39-.17m-1.97 1.75l2.05 2.05L15.06 22H13v-2.06l6.07-6.06M11 19l-2 2H5c-1.1 0-2-.9-2-2V5c0-1.1.9-2 2-2h4.18C9.6 1.84 10.7 1 12 1c1.3 0 2.4.84 2.82 2H19c1.1 0 2 .9 2 2v4l-2 2V5h-2v2H7V5H5v14h6m1-16c-.55 0-1 .45-1 1s.45 1 1 1s1-.45 1-1s-.45-1-1-1Z">
                    </path>
                  </svg>
                </div>
                <div class="hidden sm:flex w-48 bg-gray-500 h-0.5 bg-gray-700"></div>

              </div>
              <div class="mt-3 sm:pr-8">
                <h3 class="text-lg font-semibold text-gray-500 text-white">enter information</h3>
              </div>
            </li>


            <li class="relative mb-6 sm:mb-0">
              <div class="flex items-center">
                <div
                  class="flex z-10 justify-center items-center w-6 h-6 bg-blue-300 rounded-full ring-0 ring-blue-900 bg-blue-900 sm:ring-8 shrink-0">
                  <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true"
                    role="img" class="iconify iconify--icon-park-outline" width="15" height="15"
                    preserveAspectRatio="xMidYMid meet" viewBox="0 0 48 48">
                    <g fill="none">
                      <g stroke="#000000" stroke-linecap="round" stroke-linejoin="round" stroke-width="4"
                        clip-path="url(#IconifyId180e2ff12bc36151318)">
                        <path d="M42 20v19a3 3 0 0 1-3 3H9a3 3 0 0 1-3-3V9a3 3 0 0 1 3-3h21"></path>
                        <path d="m16 20l10 8L41 7"></path>
                      </g>
                      <defs>
                        <clipPath id="IconifyId180e2ff12bc36151318">
                          <path fill="currentColor" d="M0 0h48v48H0z"></path>
                        </clipPath>
                      </defs>
                    </g>
                  </svg>
                </div>
                <div class="hidden sm:flex w-32  h-0.5 "></div>

              </div>
              <div class="mt-3 sm:pr-8">
                <h3 class="text-lg font-semibold text-gray-500 text-white">submit a form</h3>
              </div>
            </li>
          </ol>







        </div>
        <!-- หัวข้อ -->
      </div>

      <div class="">

        <div class="text-3xl font-bold text-center mb-10  drop-shadow-md " v-if="type == `ShowList`">
          {{ name }}
        </div>
       
        <router-link :to="{ name: 'ListAllEvent', query: { categoryId: 0 } }" class="flex justify-center" >
          <div v-if="type == `ShowList`"
            class=" transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 duration-100 shadow-xl text-center font-semibold mb-10 bg-gradient-to-r from-yellow-100 to-rose-300 text-gray-600 hover:bg-green-300 hover:text-black rounded-full w-72 p-1 ">
            check all
          </div>
        </router-link>


        <div class=" flex justify-center">
          <div v-if="type == `ShowList`" class="transition ease-in-out delay-150 
           hover:-translate-y-1 hover:scale-110 duration-100 shadow-xl 
           text-center font-semibold mb-10 bg-slate-100 text-slate-600 
           hover:bg-green-200 hover:text-black rounded-full w-72 p-1 " @click="() => askShowList = true">
            some appointment

          </div>
        </div>
        <!-- <img src="../assets/ques.gif" v-if="!askShowList"/> -->
      </div>

      <div class="grid md:grid-cols-3 grid-cols-1  " v-if="askShowList">
        <div v-for="category in categorys" :key="category.eventCategoryID">
          <div class="grid justify-items-center">
            <img :src="`/kw3/` + category.eventCategoryID + `.png`" @click="
              SentToNextPage(
                category.eventCategoryID,
                category.eventCategoryName,
                category.eventDuration
              )
            " class="w-64 transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 duration-100" />
            <div class="text-center py-3">{{ category.eventCategoryName }}</div>
            <button
              @click="ShowDetailCate(category.eventDuration, category.eventCategoryDescription, category.eventCategoryID, category.eventCategoryName)"
              class="transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110  duration-150 rounded-lg text-sm leading-6  font-semibold select-none text-sky-600 ">
              show detail </button>
            <br>

          </div>
        </div>
      </div>



    </div>
  </div>
</template>

<style>

</style>
