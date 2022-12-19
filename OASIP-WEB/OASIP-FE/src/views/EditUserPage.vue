<script setup>
import {
  onBeforeMount,
  ref,
  onBeforeUpdate,
  computed,
} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";
import PopupPage from "../components/PopupPage.vue";
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import goToLogin from "../components/goToLogin.vue";


const jwtTokenRF = ref()
const jwtToken = ref()
const router = useRouter();
const myRouter = useRoute();
const User = ref({
  name: "",
  role: "",
  email: ""
})

const UserOld = ({
  name: "",
  role: "",
  email: ""
})

const getLinkAll = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users/${myRouter.query.UserId}`, {

    method: 'get',
    headers: {

      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + jwtToken.value
    }
  }
  );
  if (res.status === 200) {
    User.value = await res.json();
    UserOld.value = { ...User.value }
    console.log("corret")
    LoginCheck.value=true

  }else
  RefreshToken()
  getLinkAll()
    
};

const RefreshToken = async () => {

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
    updateUser()
  }else
  isActivePopup2.value=true
  




};

console.log(myRouter.query.userId)
const UserRole = ref()

onBeforeMount(async () => {
  UserRole.value = localStorage.getItem('UserRole');
  jwtTokenRF.value = localStorage.getItem('jwtTokenRF');
  jwtToken.value = localStorage.getItem('jwtToken');
  getLinkAll();
});


const goBack = () => router.go(-1);



const updateUser = async () => {

  if (UserOld.value.name == User.value.name && UserOld.value.email == User.value.email && UserOld.value.role == User.value.role) {
    isActivePopup.value = true
    CheckStatus.value = false
  }

  if (UserOld.value.name != User.value.name) {
    if (User.value.email != "" && EmailValidation.value != false && User.value.name != "") {
      const res = await fetch(
        `${import.meta.env.VITE_APP_TITLE}/api/users/${myRouter.query.UserId
        }`,
        {
          method: "PUT",
          headers: {
            "content-type": "application/json",
            'Authorization': 'Bearer ' + jwtToken.value
          },
          body: JSON.stringify({

            name: User.value.name,
          }),
        }
      );
      if (res.status === 200) {
        isActivePopup.value = true
        CheckStatus.value = false
        //   statusTrue()
        console.log("edited successfully");
      }

      if (res.status === 401) {
        

          RefreshToken()
        
      } else


        //  CheckStatus.value=true
        // isActivePopup.value=true
        StatusNameError.value = await res.json();

      console.log("xxx")
    } else {



    }
  }



  if (UserOld.value.email != User.value.email) {
    if (User.value.email != "" && EmailValidation.value != false && User.value.name != "") {
      const res2 = await fetch(
        `${import.meta.env.VITE_APP_TITLE}/api/users/${myRouter.query.UserId
        }`,
        {
          method: "PUT",
          headers: {
            "content-type": "application/json",
            'Authorization': 'Bearer ' + jwtToken.value
          },
          body: JSON.stringify({

            email: User.value.email,

          }),
        }
      );
      if (res2.status === 200) {
        isActivePopup.value = true
        CheckStatus.value = false
        //   statusTrue()
        console.log("edited successfully");
      } else if (res2.status === 401) {
        

          RefreshToken()
        
      } else

        CheckStatus.value = true
      isActivePopup.value = true
      StatusEmailError.value = await res2.json()

      console.log("xxx")
    } else {
      CheckStatus.value = true
      isActivePopup.value = true


    }
  }




  if (UserOld.value.role != User.value.role) {
    if (User.value.email != "" && EmailValidation.value != false && User.value.name != "") {
      const res = await fetch(
        `${import.meta.env.VITE_APP_TITLE}/api/users/${myRouter.query.UserId
        }`,
        {
          method: "PUT",
          headers: {
            "content-type": "application/json",
            'Authorization': 'Bearer ' + jwtToken.value
          },
          body: JSON.stringify({


            role: User.value.role

          }),
        }
      );
      if (res.status === 200) {
        isActivePopup.value = true
        CheckStatus.value = false
        //   statusTrue()
        console.log("edited successfully");
      } else if (res.status === 401)  {
          RefreshToken()
        
      } else

        CheckStatus.value = true
      isActivePopup.value = true
      console.log(await res.json());

      console.log("xxx")
    } else {
      CheckStatus.value = true
      isActivePopup.value = true


    }
  }

};


onBeforeUpdate(() => {
  CheckData()
});

const isActivePopup = ref(false);


function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}

onBeforeUpdate(() => {
  CheckData()
});

function CheckData() {

  if (User.value.email != "") {
    EmailCheck.value = true
  } else { EmailCheck.value = false }

  // Check Email validateEmail
  if (validateEmail(User.value.email) == true) {
    EmailValidation.value = true
  } else {
    EmailValidation.value = false

  }

  //check name
  if (User.value.name != "") {
    NameCheck.value = true
  } else {
    NameCheck.value = false
  }


  //check role

  if (User.value.role != "Admin" && User.value.role != "Lecturer" && User.value.role != "Student") {
    RoleCheck.value = false
  } else {
    RoleCheck.value = true
    console.log("")

  }

}

function removeToken() {
  localStorage.removeItem('jwtToken')
  localStorage.removeItem('jwtTokenRF')

  window.location.reload()
}

const LoginCheck =ref(false)
const EmailCheck = ref(true)   //เซ็คว่ากรอกรึยัง
const EmailValidation = ref(true)  //ฟอแมท เมล
const NameCheck = ref(true)     //เซ็คว่ากรอกรึยัง
const RoleCheck = ref(true) //check role
let StatusEmailError = ref({ error: "", Email: "" })
let StatusNameError = ref({ error: "", Name: "" })
const CheckStatus = ref(true) //check edit
const isActivePopup2 =ref(false)

</script>

<template>

  <div class="flex justify-center">
    <goToLogin/>
    <PopupPage v-show="UserRole!=`Admin`" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
         หน้านี้ใช้ได้เฉพาะ admin เท่านั้น
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , goBack ()" />
        </div>
      </div>
      </PopupPage>
    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div v-if="!CheckStatus" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-green-600 tracking-wide pb-8">
          edit succeeded
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
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok" @click="goBack()" />
        </div>
      </div>


      <div v-if="CheckStatus" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-red-600 tracking-wide ">
          edit not succeeded
        </p>
        <div class="mb-8">
          {{StatusEmailError.Email}} <br />
          {{StatusNameError.Name}}
        </div>
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
            @click="isActivePopup = false" />
        </div>
      </div>


    </PopupPage>


    <PopupPage v-show="isActivePopup2" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
        โปรดเข้าสู่ระบบใหม่
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , removeToken ()" />
        </div>
      </div>
      </PopupPage>




    <div class="space-y-7 bg-white shadow-xl mt-12 rounded-lg md:ml-24 md:p-16 p-8  rounded md:w-auto w-full">
      <div v-if="LoginCheck">
      <RoundButton bg-color="bg-slate-400 text-white text-sm" button-name="<< go back" @click="goBack" />


      <div class="space-y-7">
        <div>
          <span class="font-bold text-slate-600 ">User Name :<input
              class="border-2 border-sky-200 rounded-lg w-64 pl-2 ml-1" type="text" maxlength="100"
              v-model.trim="User.name">
          </span>
          <br> <span v-if="!NameCheck" class=" font-bold text-red-600 text-xs">*ชื่อห้ามเว้นว่างและห้ามซ้ำ</span><span
            class=" font-bold text-gray-600 text-xs"> ความยาวห้ามเกิน 100 ตัว</span> <span
            class=" font-bold text-gray-600 text-xs">--> เหลืออีก {{ 100 - User.name.length}} ตัว
          </span>
        </div>



        <span class="text-slate-600 font-bold">email : </span>
        <input class="border-2 border-sky-200 rounded-lg w-64 pl-2 ml-1" type="text" maxlength="100"
          v-model.trim="User.email">
        <br>
        <span v-show="!EmailCheck" class="text-red-600"> กรุณาใส่อีเมล</span>
        <span v-show="!EmailValidation & EmailCheck" class="text-red-600"> กรุณากรอกอีเมลล์ให้ถูกต้อง</span>



        <div class="text-slate-600 font-bold ">role :
          <Menu as="div" class=" ">
            <div>
              <MenuButton
                class="text-left bg-transparent h-12 w-44 mt-4 rounded-full border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
                {{User.role}}
                <ChevronDownIcon class="-mr-1 ml-2 h-5 w-5" aria-hidden="true" />
              </MenuButton>
            </div>

            <transition enter-active-class="transition ease-out duration-100"
              enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95">
              <MenuItems
                class="origin-top-right absolute  mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                <div class="py-1">
                  <MenuItem v-slot="{ active }">
                  <div :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                    @click="User.role=`Admin`">Admin</div>
                  </MenuItem>
                  <MenuItem v-slot="{ active }">
                  <div :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                    @click="User.role=`Lecturer`">Lecturer</div>
                  </MenuItem>
                  <MenuItem v-slot="{ active }">
                  <div :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']"
                    @click="User.role=`Student`">Student</div>
                  </MenuItem>

                </div>
              </MenuItems>
            </transition>
          </Menu>

        </div>



        <div class=" pt-3 flex justify-center ">


          <RoundButton bg-color="bg-emerald-400 text-white  place-items-center" button-name="save"
            @click="updateUser()" />

        </div>

      </div>
        
      </div>
      <!-- popup -->
<div v-if="!LoginCheck" class="text-center">
            pls login first
        </div>
    </div>
  </div>
</template>

<style>

</style>
