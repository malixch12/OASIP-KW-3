<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";
import { useRouter } from "vue-router";
import CheckPassword from "../components/CheckPassword.vue";


const router = useRouter();

const isActivePopup = ref(false);
const UserLists = ref({ content: "" })

const page = ref(0);
const numPage = ref();
const textShow = ref("-------------no user------------" )
const jwtToken = ref()
const jwtTokenRF = ref()

const getLinkAll = async () => {
  console.log(jwtToken.value)
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users`,
    {

      method: 'get',
      headers: {

        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
    UserLists.value = await res.json();
    numPage.value = Math.ceil(UserLists.value.totalElements / 8);

  } else if (res.status === 401) {
    const TokenValue = ref( await res.json())
    console.log("status from backend = " +  TokenValue.value.message )
    if (TokenValue.value.message == "Token is expired") {

      RefreshToken()
    }
    if (TokenValue.value.message == "Token incorrect" & jwtToken.value != null) {

      localStorage.removeItem('jwtToken')
      localStorage.removeItem('jwtTokenRF')

    TokenValue.value = "x"
    TokenTimeOut.value = true
    isActivePopup.value = true

    }
    if (TokenValue.value.message == "Please log in for get Token again." ) {

localStorage.removeItem('jwtToken')
localStorage.removeItem('jwtTokenRF')
TokenValue.value = "x"
TokenTimeOut.value = true
isActivePopup.value = true

}
  }
  if (res.status === 403) {
    textShow.value = "You are not an admin There is no right to view this information."
    console.log(textShow)
  }

};

const RefreshToken = async () => {
  console.log("RefreshToken doing...")
  console.log(jwtTokenRF.value)
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/refresh`,
    {

      method: 'get',
      headers: {
        'IsRefreshToken': 'true',
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtTokenRF.value
      }
    }
  );
  if (res.status === 200) {
    console.log("โทเค้นหมดอายุ")
    let jwtTokenRF = await res.json()
    localStorage.setItem('jwtToken', jwtTokenRF.accessToken);
    jwtToken.value = localStorage.getItem('jwtToken');
    getLinkAll()
  } 
    if(res.status === 500) {
      console.log(await res.json())

      CheckTokenTimeOut()
     
    }
    if(res.status === 401) {
     console.log(await res.json())
     CheckTokenTimeOut()

    }

};

const TokenTimeOut = ref(false)


function CheckTokenTimeOut() {
    console.log("time out")
    localStorage.removeItem('jwtToken')
    localStorage.removeItem('time')
    TokenTimeOut.value = true
    isActivePopup.value = true
  

}

const UserRole = ref()
onBeforeMount(async () => {
  jwtToken.value = localStorage.getItem('jwtToken');
  jwtTokenRF.value = localStorage.getItem('jwtTokenRF');
  UserRole.value = localStorage.getItem('UserRole');
  const TimeLogin = localStorage.getItem('time');
  getLinkAll();


});


function removeToken() {
  localStorage.removeItem('jwtToken')
  window.location.reload()
}


const removeUser = async (UserId) => {

  if (confirm("Would you like to cancel your appointment?") == true) {
    const res = await fetch(
      `${import.meta.env.VITE_APP_TITLE}/api/users/${UserId}`,
      {
        method: "DELETE",
        headers: {

          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + jwtToken.value
        }
      }
    );
  } getLinkAll()
};


const dataDetail = ref({

  name: "",
  role: "",
  email: "",
  updatedOn: "",
  createdOn: ""

})


const goEdit = (UserId) => {
  console.log(UserId)
  router.push({
    name: "EditUserPage",
    query: { UserId: UserId },
  });

};

const isActivePopup2 =ref(false)

const backToHome = () => {

router.push({
  name: "Home"
 
});

};
</script>

<template>

  <div class="flex justify-center grid grid-rows-1  mb-16">

    <PopupPage v-show="UserRole==`Lecturer`" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
        Lecturer ไม่สามารถใช้หน้านี้ได้
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , backToHome ()" />
        </div>
      </div>
      </PopupPage>

    <PopupPage v-show="isActivePopup" :dim-background="true">

      <div v-if="TokenTimeOut==false" class="grid grid-cols-1 p-12">

        <div class="text-3xl font-bold text-rose-400 mb-3">USER DETAIL</div>
        <p> <span class="font-bold mr-1">NAME : </span> {{dataDetail.name}} </p>
        <p> <span class="font-bold mr-1"> ROLE : </span> {{dataDetail.role}} </p>
        <p> <span class="font-bold mr-1">EMAIL : </span> {{dataDetail.email}} </p>
        <p> <span class="font-bold mr-1"> CREATE ON :</span> {{new
          Date(dataDetail.createdOn).toLocaleDateString("th-TH")}} {{new
          Date(dataDetail.createdOn).toLocaleTimeString("th-TH")}} </p>
        <p> <span class="font-bold mr-1">UPDATE ON:</span> {{new
          Date(dataDetail.updatedOn).toLocaleDateString("th-TH")}} {{new
          Date(dataDetail.updatedOn).toLocaleTimeString("th-TH")}}</p>

        <div class="success-checkmark">
          <div class="check-icon">
            <span class="icon-line line-tip"></span>
            <span class="icon-line line-long"></span>
            <div class="icon-circle"></div>
            <div class="icon-fix"></div>
          </div>
        </div>
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false" />
        </div>
      </div>

      <div class="grid grid-cols-1 p-12" v-if="TokenTimeOut==true">
        โปรดเข้าสู่ระบบใหม่
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , removeToken ()" />
        </div>
      </div>

    </PopupPage>

    <PopupPage v-show="isActivePopup2" :dim-background="true">

<div  class="grid grid-cols-1  pl-24 pr-24 pt-2 " >
  <div class="flex justify-end mt-14 "  @click="() => isActivePopup2 = false">
    <svg  xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true" role="img" class="iconify iconify--iconoir" width="32" height="32" preserveAspectRatio="xMidYMid meet" viewBox="0 0 24 24">
      <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M6.758 17.243L12.001 12m5.243-5.243L12 12m0 0L6.758 6.757M12.001 12l5.243 5.243"></path>
    </svg>

  </div>
 
  

</div><CheckPassword/>
 </PopupPage>

    <br>
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg w-full px-24 bg-white py-8 ">
      <div class="text-3xl font-bold text-center   drop-shadow-md"> USER LIST </div>
      <table class="w-full text-sm text-left text-gray-500 ">
        <caption class="p-5 text-lg font-semibold text-left text-gray-900 bg-white ">
          show list all user in db
          <p class="mt-1 text-sm font-normal text-gray-500 ">This is the table where all user
            information is stored. Each user's information includes username, email address, role, time of creation,
            last modified time.</p>
        </caption>
        <thead class="text-xs text-gray-700 uppercase bg-gray-100 ">
          <tr>
            <th scope="col" class="py-3 px-14 ">
              User name
            </th>
            <th scope="col" class="py-3 px-14">
              Email
            </th>
            <th scope="col" class="py-3 px-14">
              role
            </th>
            <th scope="col" class="py-3 px-14 text-blue-600 ">
            </th>
         

            <th scope="col" class="py-3 px-14">
              <span class="sr-only">detail</span>
            </th>

            <th>

            </th>
            <th>

            </th>

          </tr>
        </thead>
        <tbody>

          <tr v-for="(user, index) in UserLists.content" :key="index"
            class="bg-white border-b ">

            <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap ">
              {{user.name}}
            </th>
            <td class="py-4 px-14">
              {{user.email}}
            </td>
            <td class="py-4 px-14">
              {{user.role}}
            </td>

            <td class="py-4 px-14 text-right">
              <div class="font-medium text-blue-600  hover:underline" @click="isActivePopup=true
                    , dataDetail.name=user.name ,
                    dataDetail.email=user.email ,
                     dataDetail.role=user.role ,
                      dataDetail.updatedOn = user.updatedOn ,
                      dataDetail.createdOn = user.createdOn
                    
                    ">detail</div>

            </td>
            <td class="py-4 px-14 text-right">
              <div class="font-medium text-red-600  hover:underline" @click="removeUser(user.id)">delete</div>

            </td>
            <td class="py-4 text-right " @click="goEdit(user.id)">
              <svg width="1em" height="1em" viewBox="0 0 24 24">
                <path fill="#888888"
                  d="m19.3 8.925l-4.25-4.2l1.4-1.4q.575-.575 1.413-.575q.837 0 1.412.575l1.4 1.4q.575.575.6 1.388q.025.812-.55 1.387ZM17.85 10.4L7.25 21H3v-4.25l10.6-10.6Z">
                </path>
              </svg>
            </td>





          </tr>

        </tbody>
      </table>

      <div class="text-center mt-10" v-if="UserLists.content.length==0 && jwtToken !=null">{{textShow}}</div>

      <div class="text-center mt-10 text-red-500" v-if="jwtToken ==null">Can't see data Please login first.</div>
      <div class="text-center text-sm underline underline-offset-4 text-gray-400" v-if="jwtToken ==null">
        <router-link :to="{ name: 'Login' }" class="
            
                ">Click here to login page!</router-link>
      </div>


      <div class="  rounded-b-lg p-8 ml-24 mr-24 text-center">
        <!-- <span
        v-for="(e, index) in numPage"
        :key="index"
        class="p-5 text-white hover:text-orange-600"
      >
        <button @click="$emit('paging', index, filter)">
          {{ index + 1 }}
        </button>
      </span> -->
        <nav aria-label="Page navigation example ">
          <ul class="inline-flex -space-x-px" v-for="(e, index) in numPage" :key="index">

            <button @click="page=index , getLinkAll() "
              class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 focus:bg-gray-200 focus:text-red-600 ">{{
              index + 1 }}</button>

          </ul>
        </nav>
        <hr class="mt-4"/>
        <div v-if="UserRole==`Admin`" @click="isActivePopup2=true" class="mt-4 font-normal text-blue-500 underline underline-offset-1
">CHECK PASSWORD</div>

      </div>


    </div>

  

  </div>
</template>

<style>

</style>
