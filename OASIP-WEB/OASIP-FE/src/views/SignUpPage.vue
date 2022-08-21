<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";



const route = useRoute();

const addUser = async () => {

        if(dataUser.value.role== "Please select role") {
            dataUser.value.role="Student"
        }

  const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/users`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(dataUser.value),
  });
    if(res.status === 200) {
        console.log(dataUser.value);
       isActivePopup.value=true
       CheckStatus.value=true
       console.log(res.status, res.statusText);
    }else {
        isActivePopup.value=true
        CheckStatus.value=false
        console.log("fail")
        console.log(res.status, err.res.data);
    }
      
      
};



const dataUser = ref({    //สำหรับให้ ฟอม v-model
  name: "",
  role: "Please select role",
  email: ""
});

const isActivePopup = ref(false);
const CheckStatus = ref()


//validate

function validateEmail(email) {
  var re =  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}

onBeforeUpdate(() => {
  CheckData()
});

function CheckData() {
 
  if (dataUser.value.email != "") {
    EmailCheck.value = true
  } else { EmailCheck.value = false }

  // Check Email validateEmail
  if (validateEmail(dataUser.value.email) == true) {
    EmailValidation.value = true
  } else {
    EmailValidation.value = false
  }

  //check name
   if (dataUser.value.name != "" && dataUser.value.name.length < 100) {
    NameCheck.value = true
  } else {
    NameCheck.value = false
  }


  //check role

  if(dataUser.value.role!="Admin" && dataUser.value.role!= "Lecturer" &&  dataUser.value.role!= "Student") {
RoleCheck.value = false
  } else {
    RoleCheck.value = true
    console.log("")

  }

}

const EmailCheck = ref(true)   //เซ็คว่ากรอกรึยัง
const EmailValidation = ref(true)  //ฟอแมท เมล
const NameCheck = ref(true)     //เซ็คว่ากรอกรึยัง
const RoleCheck = ref(true) //check role

</script>

<template>
  <div class="">
<div class="text-white text-xs">{{dataUser.role}}</div>
 <PopupPage v-show="isActivePopup" :dim-background="true">
      <div v-if="CheckStatus" class="grid grid-cols-1 p-12">
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
            @click="isActivePopup = false" />
        </div>
      </div>


    <div v-if="!CheckStatus" class="grid grid-cols-1 p-12">
        <p class="text-3xl font-semibold text-red-600 tracking-wide pb-8">
          sign up not succeeded
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
                    <h2>Create account</h2>

                    <form>
                        <div class="inputBox">
                            <input type="text" placeholder="Username" v-model.trim="dataUser.name" >
                              <details class="" v-if="!NameCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!NameCheck" class="text-red-600"> ยูสเซอร์เนมห้ามเว้นว่างและห้ามเกิน 100 ตัว</span>
          </div>
        </details>
                        </div>
                         <div class="inputBox">
                            <input type="text" placeholder="email" v-model.trim="dataUser.email">
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
                         <div class="inputBox ">
                           <Menu as="div" class=" ">
    <div>
      <MenuButton class="text-left bg-transparent h-12 w-full rounded-full border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
        {{dataUser.role}}
        <ChevronDownIcon class="-mr-1 ml-2 h-5 w-5" aria-hidden="true" />
      </MenuButton>
    </div>

    <transition enter-active-class="transition ease-out duration-100" enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100" leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
      <MenuItems class="origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
        <div class="py-1">
          <MenuItem v-slot="{ active }">
            <div :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']" @click="dataUser.role=`Admin`">Admin</div>
          </MenuItem>
          <MenuItem v-slot="{ active }">
            <div :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']" @click="dataUser.role=`Lecturer`">Lecturer</div>
          </MenuItem>
          <MenuItem v-slot="{ active }">
            <div :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']" @click="dataUser.role=`Student`">Student</div>
          </MenuItem>
        
        </div>
      </MenuItems>
    </transition>
  </Menu>
   <details class="" v-if="!RoleCheck">
          <summary class="text-sm leading-6 text-slate-900 dark:text-white font-semibold select-none text-red-400 ml-3 mt-3">
            invalid
          </summary>
          <div class="mt-3 text-sm leading-6 text-slate-600 dark:text-slate-400">
            <span v-show="!RoleCheck" class="text-red-600"> กรุณาเลือก role</span>
                     <br>   <span v-show="!RoleCheck" class="text-gray-600"> **หากไม่เลือกจะถูกเลือกเป็น student </span>

          </div>
        </details>

                        </div>
                        <div class="inputBox">
                            <input type="submit" value="Sign up" @click="addUser()">
                        </div>

                        <p class="forget">Do you have an account ?  Click here!</p>

                    </form>
                </div>

            </div>

          </div>
    </section>

  </div>
</template>

<style>

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Poppins', sans-serif;
}

body {}

section
 {
    display: flex;
    justify-content:center;
    align-items: center;
    min-height: 77vh;
}

section .color
{
    position:absolute;
    filter: blur(250px);
}

section .color:nth-child(1)
{
    
    width: 400px;
    height: 400px;
    background: #ff359b
}

section .color:nth-child(2)
{
    bottom: -150px;
    left: 100px;
    height: 500px;
    width: 500px;
    background: #fffd87
}

section .color:nth-child(3)
{
    bottom: 50px;
    right: 100px;
    height: 500px;
    width: 500px;
    background: #00d2ff
}

.box {
    position:relative;

}

.box .square {
    position:absolute;
     backdrop-filter: blur(5px);
    box-shadow: 0 25px 45px rgba(0,0,0,0.1);
    border:1px solid rgba(255,255,255,0.5);
        border-right:1px solid rgba(255,255,255,0.2);
 border-bottom:1px solid rgba(255,255,255,0.2);
   background: rgba(255,255,255,0.1);
    animation: animate 10s linear infinite;
   border-radius: 10px;
   animation-delay: calc(-1s * var(--i));
}

@keyframes animate
{
    0%,100%{
        transform: translateY(-40px);
    }
    50% {
        transform: translateY(40px);
    }
    
}

.box .square:nth-child(2) {
    top: 150px;
    left: -200px;
    width:120px;
    height:120px;
    z-index: 2;
}

.box .square:nth-child(1) {
    top: -50px;
    right: -60px;
    width:100px;
    height:100px;
}

.box .square:nth-child(3) {
    bottom: 50px;
    right: -120px;
    width:80px;
    height:80px;
    z-index: 2;
}


.box .square:nth-child(4) {
    bottom: -80px;
    left: 100px;
    width:50px;
    height:50px;
 
}
.box .square:nth-child(5) {
    top: -80px;
    left: 140px;
    width:60px;
    height:60px;
 
}


.container2 {
    position: relative;
    width: 400px;
    min-height:400px;
    background: rgba(255,255,255,0.5);
    border-radius: 10px;
    display:flex;
    justify-content: center;
    align-items: center;
    backdrop-filter: blur(30px);
    box-shadow: 0 25px 45px rgba(0,0,0,0.1);
    border:1px solid rgba(255,255,255,0.5);
        border-right:1px solid rgba(255,255,255,0.2);
 border-bottom:1px solid rgba(255,255,255,0.2);


}

.form {
    position: relative;
    width:100%;
    height:100%;
    padding:40px;
}

.form h2{
    position: relative;
    color: rgb(0, 0, 0);
    font-size: 24px;
    font-weight: 600;
    letter-spacing:1px;
    margin-bottom: 40px;

}

.form h2::before{

    content:'';
    position: absolute;
    left:0;
    bottom:-10px;
    width:80px;
    height:4px;
    background:#fff;


}

.form .inputBox {
    width:100%;
    margin-top:20px;
}

.form .inputBox input {
    width:100%;
    background:rgba(255,255,255,0.2);
    border:none;
    outline:none;
    padding: 10px 20px;
    border-radius: 35px;
    border:1px solid rgba(255,255,255,0.5);
    border-right:1px solid rgba(255,255,255,0.2);
    border-bottom:1px solid rgba(255,255,255,0.2);
    font-size:16px;
    letter-spacing: 1px;
    color: rgb(111, 104, 104);
    box-shadow: 0 5px 15px rgba(0,0,0,0.05);

}

.MenuButton {
    background:rgba(255,255,255,0.2);
}

.form .inputBox input::placeholder {
    color: rgb(115, 109, 109);
}

.form .inputBox input[type="submit"] {
    background: #fff;
    color: #666;
    max-width: 100px;
    cursor: pointer;
    margin-bottom: 20px;
    font-weight: 600;
}

.forget {
    margin-top:5px;
    color: rgb(81, 78, 78);
    font-size: 15px;
}

.forget a {
    color: rgb(11, 8, 8);
    font-weight: 600;
}

</style>
