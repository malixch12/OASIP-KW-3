<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";



const route = useRoute();
const errorStatus = ref({Name : null,
Email : null})

const addUser = async () => {
CheckData()
    
  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/login`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(dataUser.value),
  })
    
    if(res.status === 200) {
    const test = await res.json()
      localStorage.setItem('jwtToken',test.jwttoken);
        console.log(dataUser.value);
       isActivePopup.value=true
       CheckStatus.value=true
       
    }
    
    // else {
    //     isActivePopup.value=true
    //     CheckStatus.value=false
    
    //    // console.log(await res.json());
    //     errorStatus.value = await res.json()
    // }

    if(res.status === 404) {
        messageError.value = "the specified email DOES NOT exist"
      isActivePopup.value=true
        CheckStatus.value=false
        console.log(await res.json());
        errorStatus.value = await res.json()
    }
      
    if(res.status === 401) {
        messageError.value = "the password incorrect "
      isActivePopup.value=true
        CheckStatus.value=false
        console.log(await res.json());
        errorStatus.value = await res.json()
    }

};



const dataUser = ref({    //สำหรับให้ ฟอม v-model
 
  email: null,
  password: null ,

});

const isActivePopup = ref(false);
const CheckStatus = ref()


//validate

function validateEmail(email) {
  var re =  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}

onBeforeUpdate(() => {
  
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
const PasswordCheck =ref(true) //check password
</script>

<template>
  <div class="">
 
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
            @click="isActivePopup = false" />
        </div>
      </div>


    <div v-if="!CheckStatus" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-red-600 tracking-wide ">        
         <br>    
          Login not succeeded !!! 

        </p>

          <div >
            <p class="text-xl text-red-600 text-center mb-8">           
          {{messageError}}

        </p>
    <p class="forget text-center ">dont have  account ?     <router-link
              :to="{ name: 'SignUpPage' }"
              class="
            
              "
              >Click here!</router-link
            > </p>
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
            @click="isActivePopup = false" />
        </div>
      </div>

      
    </PopupPage>


    <section class="">

        <div class="color"> </div>
         <div class="color"></div>
          <div class="color"></div>
          
          <div class="box">

                <div class="square" style="--i:0;"></div>
                <div class="square" style="--i:1;"></div>
                 <div class="square" style="--i:2;"></div>
               <div class="square" style="--i:3;"></div>
                <div class="square" style="--i:4;"></div>


            <div class="container2">
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
                            <input type="text" class="" placeholder="email" v-model.trim="dataUser.email" required>
                              <details class="" v-show="!EmailValidation || !EmailCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!EmailCheck" class="text-red-600"> กรุณาใส่อีเมล</span>
            <span v-show="!EmailValidation & EmailCheck" class="text-red-600"> กรุณากรอกอีเมลล์ให้ถูกต้อง</span>
          </div>
        </details>
                        </div>

                         <div class="inputBox">
                       
                            <input type="password" placeholder="password" v-model.trim="dataUser.password">
                         <details class="" v-if="!PasswordCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!PasswordCheck" class="text-red-600"> กรุณาใส่รหัส</span>
          </div>
        </details>
                        </div>




                        

                    
       <input  class="mt-4 test rounded-full px-8 py-1 drop-shadow-lg" value="Login" @click="addUser()">
                        <p class="forget">dont have  account ?     <router-link
              :to="{ name: 'SignUpPage' }"
              class="
            
              "
              >Click here!</router-link
            >
            </p>

                    </form>
                </div>

            </div>

          </div>
    </section>
 
  </div>
</template>

<style>



</style>
