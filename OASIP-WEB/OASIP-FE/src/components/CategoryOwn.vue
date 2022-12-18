<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";
import { useRouter } from "vue-router";


const router = useRouter();

const route = useRoute();
const errorStatus = ref({
  Name: null,
  Email: null
})
const page = ref(0);
const userLect = ref([])
const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users?page=${page.value}&pageSize=100`,
    {

      method: 'get',
      headers: {

        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
    var UserLists = await res.json();
    

  } else if (res.status === 401) {
    await RefreshToken()
    await getLinkAll()
  }
  if (res.status === 403) {
    textShow.value = "You are not an admin There is no right to view this information."
    console.log(textShow)
  }
  

};


const addUser = async () => {

    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/users/signup`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(dataUser.value),
    })

    if (res.status === 200) {
      console.log(dataUser.value);
      isActivePopup.value = true
      CheckStatus.value = true
    } else {
      CheckStatus.value = false
      isActivePopup2.value = true

      // console.log(await res.json());
      errorStatus.value = await res.json()
      console.log(errorStatus.value)
    }

  
  


};



const dataUser = ref({    //สำหรับให้ ฟอม v-model
  name: null,
  role: "Student",
  email: null,
  password: null,
  passwordConfirm: ""
});

const isActivePopup = ref(false);
const isActivePopup2= ref(false);



onBeforeMount(() => {
   // getLinkAll()

});



function goHome () {
  router.push({
    name: "Login"
 
  });
}



</script>

<template>
  <div class="mt-16">
  



    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-green-600 tracking-wide pb-8">
          sign up succeeded
        </p>
        <div class="success-checkmark">
          <div class="check-icon">
            <span class="icon-line line-tip"></span>
            <span class="icon-line line-long"></span>
            <div class="icon-circle"></div>
            <div class="icon-fix"></div>
          </div>
        </div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false " />
        </div>
      </div>
</PopupPage>


<PopupPage v-show="isActivePopup2" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-3xl text-center font-semibold text-red-600 tracking-wide pb-8">
          sign not up succeeded
        </p>
     <div class="text-center mb-4">  {{errorStatus.Email}} <br/>
     {{errorStatus.Name}}
  </div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup2 = false " />
        </div>
      </div>
</PopupPage>


    
    <form class="w-72">
        <label for="role" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">user</label>

<Menu as="div" class=" mb-6 ">
                <div>
                  <MenuButton id="role"
                    class="text-left bg-transparent rounded-lg h-12 w-full  border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
                    eventCategoryID
                    <ChevronDownIcon class="-mr-1 ml-2 h-5 w-5" aria-hidden="true" />
                  </MenuButton>
                </div>

                <transition enter-active-class="transition ease-out duration-100"
                  enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
                  leave-active-class="transition ease-in duration-75"
                  leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
                  <MenuItems
                    class=" absolute  rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <div class="py-1">
                      <MenuItem v-slot="{ active }">
                      <div
                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                        @click="dataUser.role = `Admin`">Admin</div>
                      </MenuItem>
                      <MenuItem v-slot="{ active }">
                      <div
                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                        @click="dataUser.role = `Lecturer`">Lecturer</div>
                      </MenuItem>
                      <MenuItem v-slot="{ active }">
                      <div
                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                        @click="dataUser.role = `Student`">Student</div>
                      </MenuItem>

                    </div>
                  </MenuItems>
                </transition>
              </Menu>

  <label for="role" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">eventCategoryID</label>

  <Menu as="div" class=" ">
                  <div>
                    <MenuButton id="role"
                      class="text-left bg-transparent rounded-lg h-12 w-full  border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
                      eventCategoryID
                      <ChevronDownIcon class="-mr-1 ml-2 h-5 w-5" aria-hidden="true" />
                    </MenuButton>
                  </div>

                  <transition enter-active-class="transition ease-out duration-100"
                    enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
                    leave-active-class="transition ease-in duration-75"
                    leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
                    <MenuItems
                      class=" absolute  rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                      <div class="py-1">
                        <MenuItem v-slot="{ active }">
                        <div
                          :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                          @click="dataUser.role = `Admin`">Admin</div>
                        </MenuItem>
                        <MenuItem v-slot="{ active }">
                        <div
                          :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                          @click="dataUser.role = `Lecturer`">Lecturer</div>
                        </MenuItem>
                        <MenuItem v-slot="{ active }">
                        <div
                          :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                          @click="dataUser.role = `Student`">Student</div>
                        </MenuItem>

                      </div>
                    </MenuItems>
                  </transition>
                </Menu>


  <button type="submit" @click="addUser()" class="text-white mt-6 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Add user</button>
</form>
   
  </div>
</template>
 
<style>

</style>