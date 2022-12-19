<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";
import { useRouter } from "vue-router";
import jwt_decode from "jwt-decode";
import addUser from "../components/addUser.vue";
import { UserAgentApplication } from "msal" ;



const router = useRouter();
const route = useRoute();
const errorStatus = ref({
  Name: null,
  Email: null
})
const decoded = ref({ sub: ""  , role : ""})

const Login = async () => {
  CheckData()

  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/login`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(dataUser.value),
  })

  if (res.status === 200) {
    const jwtToken = await res.json()
    console.log(jwtToken)
    localStorage.setItem('jwtToken', jwtToken.accessToken);
    localStorage.setItem('jwtTokenRF', jwtToken.refreshToken);
    console.log(dataUser.value);
    isActivePopup.value = true
    CheckStatus.value = true
    jwtToken.value = localStorage.getItem('jwtToken');
    decoded.value = jwt_decode(jwtToken.value);
    console.log(decoded.value)
    console.log(decoded.value.roles)

    localStorage.setItem('UserRole', decoded.value.roles);
    localStorage.setItem('UserEmail', decoded.value.sub);
    localStorage.setItem('UserName', decoded.value.username);
  }

  if (res.status === 404) {
    messageError.value = "the specified email DOES NOT exist"
    isActivePopup.value = true
    CheckStatus.value = false
    errorStatus.value = await res.json()
  }

  if (res.status === 401) {
    messageError.value = "the password incorrect "
    isActivePopup.value = true
    CheckStatus.value = false
    console.log(await res.json());
    errorStatus.value = await res.json()
  }

};

const lectOwnDetail =ref({owners:""})
async function checkOwnForLect () {

  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users/byEmail`,
    {

      method: 'get',
      headers: {

        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
    lectOwnDetail.value = await res.json();
     // console.log(checkOwn.owners)

// if(checkOwn.owners!=null) {
//     lectOwnDetail.value = []
//     for (const [key, value] of Object.entries(checkOwn.owners)) {
//   console.log(`${value}`)
//   lectOwnDetail.value.push({OwnId:key , name:value})
//   console.log(lectOwnDetail.value)

// }
// }



  } 

}


const dataUser = ref({    //สำหรับให้ ฟอม v-model

  email: null,
  password: null,

});

const isActivePopup = ref(false);
const CheckStatus = ref()


//validate

function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}
const jwtToken = ref(null)
onBeforeMount(() => {
  
  jwtToken.value = localStorage.getItem('jwtToken');
 var  microsoft = localStorage.getItem('micosoft');

  if (jwtToken.value != null && microsoft == null) {
    console.log(microsoft)
    decoded.value = jwt_decode(jwtToken.value);
    console.log(decoded.value)
    localStorage.setItem('UserRole', decoded.value.roles);
    checkOwnForLect()


  }else {
    decoded.value.username = localStorage.getItem('UserName')
    decoded.value.roles = localStorage.getItem('UserRole')
    const jwtMicosoft = localStorage.getItem('msal.585a0cf6-90bc-4e5e-ad97-521891f56132.idtoken')
  if(jwtMicosoft!=null) {
    localStorage.setItem('jwtToken', jwtMicosoft);

  }

  }

 

  
});

function CheckData() {

  if (dataUser.value.email != null) {
    EmailCheck.value = true
  } else { EmailCheck.value = false }

  // Check Email validateEmail
  if (validateEmail(dataUser.value.email) == true) {
    EmailValidation.value = true
  } else {
    EmailValidation.value = false
  }


  if (dataUser.value.password != null) {
    PasswordCheck.value = true
  } else {
    PasswordCheck.value = false
  }


}

const messageError = ref("")
const EmailCheck = ref(true)   //เซ็คว่ากรอกรึยัง
const EmailValidation = ref(true)  //ฟอแมท เมล
const PasswordCheck = ref(true) //check password

const goHome = () => {

  window.location.reload()

};



const msalConfig = {
    auth: {
        clientId: "585a0cf6-90bc-4e5e-ad97-521891f56132",
        authority: "https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d",
        redirectURI: "http://localhost:3000/"
    },
    cache: {
        cacheLocation: "localStorage", // This configures where your cache will be stored
        storeAuthStateInCookie: true,
        popUp:true // Set this to "true" if you are having issues on IE11 or Edge
    }
};  


var requestObj = {
    scopes: ["user.read"]
};

var myMSALObj = new UserAgentApplication(msalConfig);

var login = async () => {
    var authResult = await myMSALObj.loginPopup(requestObj);
    accoutMicro.value = authResult.account
    localStorage.setItem('jwtToken', "wait");

    return authResult.account;
};

var getAccount = async () => {
    var account = await myMSALObj.getAccount();
    return account;
};

var logoff = () => {
    myMSALObj.logout();
};

const accoutMicro = ref({accountIdentifier:null , roles:[] , idTokenClaims: {test:"xxxx"}})


onBeforeUpdate(() => {
  
   
  if(accoutMicro.value.accountIdentifier!=null) {
    localStorage.setItem('jwtTokenRF', accoutMicro.value.userName);
    if(accoutMicro.value.idTokenClaims.roles!=undefined){
      localStorage.setItem('UserRole', accoutMicro.value.idTokenClaims.roles[0]);

    }else {
      localStorage.setItem('UserRole', "Guest");
    }


    localStorage.setItem('UserEmail', accoutMicro.value.userName);
    localStorage.setItem('UserName', accoutMicro.value.name);
    localStorage.setItem('micosoft', true);

    CheckStatus.value = true
    isActivePopup.value = true

    
  }

});
</script>

<template>
  <div class="">
<p class="opacity-0">{{accoutMicro.accountIdentifier}}</p>
    <div class="text-white text-xs">{{dataUser.role}}</div>
    <PopupPage v-show="isActivePopup" :dim-background="true">
      <div v-if="CheckStatus" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-green-600 tracking-wide">
          Login succeeded
        </p>
        <p class="text-xl font-semibold text-gray-600 tracking-wide pb-5 text-center mt-3">
          welcome to clinic booking 🥳
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
            @click="isActivePopup = false , goHome()" />
        </div>
      </div>


      <div v-if="!CheckStatus" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-red-600 tracking-wide ">
          <br>
          Login not succeeded !!!

        </p>

        <div>
          <p class="text-xl text-red-600 text-center mb-8">
            {{messageError}}

          </p>
          <p class="forget text-center ">dont have account ? <router-link :to="{ name: 'SignUpPage' }" class="
            
              ">Click here!</router-link>
          </p>
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
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center mt-5" button-name="ok"
            @click="isActivePopup = false " />
        </div>
      </div>


    </PopupPage>


    <div class="flex justify-center ">

      <div class="color"> </div>
      <div class="color"></div>
      <div class="color"></div>

      <div class="box">

        

      <div class="container2 mt-16 " v-if="jwtToken==null">
        <div class="form">
          <h2>Login</h2>

          <form>
            <!-- <div class="inputBox">
                            <input type="text" placeholder="Username" v-model.trim="dataUser.name" >
                              <details class="" v-if="!NameCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!NameCheck" class="text-red-600"> ยูสเซอร์เนมห้ามเว้นว่างและห้ามเกิน 100 ตัว</span>
          </div>
        </details>
                        </div> -->
            <div class="inputBox ">
              <p class="ml-4 mb-2 text-gray-500 text-sm">email</p>
              <input type="text" class="" placeholder="" v-model.trim="dataUser.email" required>
              <details class="" v-show="!EmailValidation || !EmailCheck">
                <summary
                  class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
                  invalid
                </summary>
                <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
                  <span v-show="!EmailCheck" class="text-red-600"> กรุณาใส่อีเมล</span>
                  <span v-show="!EmailValidation & EmailCheck" class="text-red-600"> กรุณากรอกอีเมลล์ให้ถูกต้อง</span>
                </div>
              </details>
            </div>

            <div class="inputBox">
              <p class="ml-4 mb-2 text-gray-500 text-sm">password</p>

              <input type="password"  v-model.trim="dataUser.password">
              <details class="" v-if="!PasswordCheck">
                <summary
                  class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
                  invalid
                </summary>
                <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
                  <span v-show="!PasswordCheck" class="text-red-600"> กรุณาใส่รหัส</span>
                </div>
              </details>
            </div>




<div class="flex justify-center">
  <button type="submit"  @click="Login()" class="text-gray-700 bg-gradient-to-r from-yellow-100 to-rose-300 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full drop-shadow-md text-sm w-full sm:w-auto px-12 py-2.5 text-center my-8 ">Login</button>

</div>

<div class="text-gray-500 text-center text-sm mb-4">━━━━━━━━━   or   ━━━━━━━━━</div>



<button @click="login()" class="bg-gray-200 mt-2 w-full drop-shadow-md rounded"> <img src="../assets/SignIn_with_microsoft-removebg-preview.png" class="h-14"/></button>

<!-- <a href="http://localhost:3000/kw3/#/logoutPage" target="_blank">  
  
  <div    class="bg-red-300sasad" >
      logout
    </div>

      </a> -->

          

          </form>
        </div>

      </div>
      
    </div>
    </div>

    <div class="text-center mt-36" v-if="jwtToken!=null">
        Welcome <span class=" font-bold underline underline-offset-4  
                  text-transparent  bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600 " >{{decoded.username}}</span> to Clinic Booking
        <br/><div class="text-center mt-2 text-gray-400 text-lg">  {{decoded.sub}} , {{decoded.roles}} role</div>
        <div v-if="decoded.roles==`Lecturer` && lectOwnDetail.owners!=`` " class="text-center text-gray-400 mt-2 ">     you own the subject <span v-for="(own,index) in lectOwnDetail.owners">{{own}} &nbsp</span>  </div>
        <span class="text-center text-gray-400 mt-2" v-if="decoded.roles==`Lecturer` && lectOwnDetail.owners==`` "> you own 0 subject </span>

    
      </div>
  </div>
</template>

<style>



</style>
