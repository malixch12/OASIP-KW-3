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
const category =ref()
const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users/role/Lecturer`,
    {

      method: 'get',
      headers: {

        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
     userLect.value = await res.json();
    console.log(userLect.value )

  } else if (res.status === 401) {
    await RefreshToken()
    await getLinkAll()
  }
  if (res.status === 403) {
    textShow.value = "You are not an admin There is no right to view this information."
    console.log(textShow)
  }
  
  const res2 = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/eventcategorys` ,{

method: 'get',
headers: {

  'Content-Type': 'application/json',
  'Authorization': 'Bearer ' + jwtToken.value
}
}

  );
  if (res2.status === 200) {
    category.value = await res2.json();
    console.log(category.value)
  }
  if (res2.status === 401) {
    
    await RefreshToken()
    await getLinkAll()
}

};


const addUser = async () => {

    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/owners`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
        'Authorization': 'Bearer ' + jwtToken.value

      },
      body: JSON.stringify(dataUser.value),
    })

    if (res.status === 200) {
        isActivePopup3.value = true      
    } else {
        isActivePopup4.value = true      
    }

  
  


};


const userShow = ref('')
const userLectId = ref(0)

const cateShow = ref('')
const cateId = ref(0)


const dataUser = ref({    //สำหรับให้ ฟอม v-model
    userId:0,
    eventCategoryID:0
});

const isActivePopup = ref(false);
const isActivePopup2= ref(false);
const isActivePopup3= ref(false);
const isActivePopup4= ref(false);
const isActivePopup5= ref(false);



const jwtToken = ref()
const jwtTokenRF =ref()
const UserRole = ref()
onBeforeMount(() => {
    jwtToken.value = localStorage.getItem('jwtToken');
  jwtTokenRF.value = localStorage.getItem('jwtTokenRF');
  UserRole.value = localStorage.getItem('UserRole');
    getLinkAll()

});



function goHome () {
  router.push({
    name: "Login"
 
  });
}

const lectOwnDetail = ref([])

async function goEdit (id) {

    const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users/${id}`,
    {

      method: 'get',
      headers: {

        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
   var  checkOwn = await res.json();
      console.log(checkOwn.owners)

if(checkOwn.owners!=null) {
    lectOwnDetail.value = []
    for (const [key, value] of Object.entries(checkOwn.owners)) {
  console.log(`${value}`)
  lectOwnDetail.value.push({OwnId:key , name:value})
  console.log(lectOwnDetail.value)

}
}

isActivePopup.value = true

  } 

}


const dataOwnForDelete = ref(

    {name:"select your lecter",
     idOwn:""       }
)

async function deleteOwn () {
    if (confirm("Would you like to delete this user?") == true) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/owners/${dataOwnForDelete.value.idOwn}`,
      {
        method: "DELETE",
        headers: {

          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + jwtToken.value
        }
        
      }
      
    );

    if(res.status === 200) {
        isActivePopup2.value = true
    }

    if(res.status === 401) {
     console.log(await res.json())
     RefreshToken()
     removeUser(UserId)

    }

    if(res.status === 400) {
        isActivePopup5.value = true


    }
  }
}
</script>

<template>
  <div class="mt-16 ">


    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-2sm font-semibold text-green-600 tracking-wide pb-8">
            <Menu as="div" class=" mb-6 ">
                <div>
                  <MenuButton id="role"
                    class="text-left bg-transparent rounded-lg h-12 w-full  border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
{{            dataOwnForDelete.name
}}                    <ChevronDownIcon class="-mr-1 ml-2 h-5 w-5" aria-hidden="true" />
                  </MenuButton>
                </div>

                <transition enter-active-class="transition ease-out duration-100"
                  enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
                  leave-active-class="transition ease-in duration-75"
                  leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
                  <MenuItems
                    class=" absolute  rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <div class="py-1">
                      <MenuItem v-slot="{ active }" v-for="lectOwnDetail in lectOwnDetail">
                      <div
                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                        @click="dataOwnForDelete.name = lectOwnDetail.name , dataOwnForDelete.idOwn = lectOwnDetail.OwnId ">{{lectOwnDetail.name}} </div>
                      </MenuItem>
                     

                    </div>
                  </MenuItems>
                </transition>
              </Menu>    

              <div  @click="deleteOwn()" class="text-white mt-6 bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800">delete own</div>


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

<PopupPage v-show="isActivePopup5" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-3xl text-center font-semibold text-red-600 tracking-wide pb-8">
          remove own not succeeded
        </p>
     <div class="text-center mb-4">  
        เหลือคนสุดท้ายแล้ว
  </div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup5 = false " />
        </div>
      </div>
</PopupPage>

<PopupPage v-show="isActivePopup2" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-3xl text-center font-semibold text-green-600 tracking-wide pb-8">
          remove own succeeded
        </p>
     <div class="text-center mb-4">  
  
  </div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup2 = false " />
        </div>
      </div>
</PopupPage>

<PopupPage v-show="isActivePopup3" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-3xl text-center font-semibold text-green-600 tracking-wide pb-8">
          add own succeeded
        </p>
     <div class="text-center mb-4">  {{errorStatus.Email}} <br/>
     {{errorStatus.Name}}
  </div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup3 = false " />
        </div>
      </div>
</PopupPage>

<PopupPage v-show="isActivePopup4" :dim-background="true">
      <div  class="grid grid-cols-1 p-12">
        <p class="text-3xl text-center font-semibold text-red-600 tracking-wide pb-8">
            add own not succeeded        </p>
     <div class="text-center mb-4">  {{errorStatus.Email}} <br/>
     {{errorStatus.Name}}
  </div>
        <div class=" max-w-lg mx-auto  ">
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup4 = false " />
        </div>
      </div>
</PopupPage>

    <div class="flex justify-center">
    <form class="w-72">
        <label for="role" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">select user</label>

<Menu as="div" class=" mb-6 ">
                <div>
                  <MenuButton id="role"
                    class="text-left bg-transparent rounded-lg h-12 w-full  border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
                    {{userShow}}
                    
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
                      <MenuItem v-slot="{ active }" v-for="userLect in userLect">
                      <div
                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                        @click="userShow = userLect.name , dataUser.userId = userLect.id ">{{userLect.name}} </div>
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
                      {{cateShow}}
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
                        <MenuItem v-slot="{ active }" v-for="category in category">
                      <div
                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                        @click="cateShow = category.eventCategoryName , dataUser.eventCategoryID = category.eventCategoryID ">{{category.eventCategoryName}}</div>
                      </MenuItem>
                      </div>
                    </MenuItems>
                  </transition>
                </Menu>


  <div  @click="addUser()" class="text-white mt-6 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">add own</div>
</form>
   
</div>


<div class="container mt-16 mb-16">
		<table class="w-full flex flex-row flex-no-wrap sm:bg-white rounded-lg overflow-hidden sm:shadow-lg my-5">
			<thead class="text-white">
				<tr v-for="(user, index) in userLect" :key="index" class="bg-black flex flex-col flex-no wrap sm:table-row rounded-l-lg sm:rounded-none mb-2 sm:mb-0">
					<th scope="col" class="py-3 px-14 ">
              User name
            </th>
            <th scope="col" class="py-3 px-14">
              Email
            </th>
         
            <th scope="col" class="py-3   ">
              role
            </th>
         

            

            <th scope="col" class="py-3 px-14" > 
                    action
            </th >
         
				</tr>
			
			</thead>
			<tbody class="flex-1 sm:flex-none">
				<tr  v-for="(user, index) in userLect" :key="index" class="flex flex-col flex-no wrap sm:table-row rounded-l-lg sm:rounded-none mb-2 sm:mb-0">
          <th scope="row" class="p-3  text-center ">
              {{user.name}}
            </th>
            <td class="p-3 text-center">
              {{user.email}}
            </td>
         

     
            <td class="p-3 text-center">
              {{user.role}}
            </td>
            <td class="p-3 text-center mb-1.5">
              <span class="font-medium text-blue-500  px-2 hover:underline"  @click="goEdit(user.id)">edit own</span>

            </td>
            <!-- <td class="p-3  " @click="goEdit(user.id)">
              <svg width="1em" height="1em" viewBox="0 0 24 24">
                <path fill="#888888"
                  d="m19.3 8.925l-4.25-4.2l1.4-1.4q.575-.575 1.413-.575q.837 0 1.412.575l1.4 1.4q.575.575.6 1.388q.025.812-.55 1.387ZM17.85 10.4L7.25 21H3v-4.25l10.6-10.6Z">
                </path>
              </svg>
            </td> -->
				</tr>
		
			</tbody>
		</table>
	</div>



  </div>
</template>
 
<style>

</style>