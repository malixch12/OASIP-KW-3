<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import PopupPage from "../components/PopupPage.vue";
import RoundButton from "../components/RoundButton.vue";
import { useRouter } from "vue-router";


const router = useRouter();

const isActivePopup = ref(false);
const UserLists = ref({ content: "" })

const page = ref(0);
const numPage = ref();
const textShow = ref("-------------no user------------" )
const jwtToken = ref()
const jwtTokenRF = ref()

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
    UserLists.value = await res.json();
    numPage.value = Math.ceil(UserLists.value.totalElements / 8);

  } else if (res.status === 401) {
    await RefreshToken()
    await getLinkAll()
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

    }else
    CheckTokenTimeOut()



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
  getLinkAll();

  if(jwtToken.value==null) {
    goLogin()
  }
});


function removeToken() {
  localStorage.removeItem('jwtToken')
  window.location.reload()
}

const isActivePopup3 =ref(false)

const removeUser = async (UserId , role , name ) => {

  var checkOwnLect = false
  var checkOwn = {}
  var cate = []
  if(role=="Lecturer") {
    const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/users/${UserId}`,
    {

      method: 'get',
      headers: {

        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + jwtToken.value
      }
    }
  );
  if (res.status === 200) {
     checkOwn = await res.json();
      console.log(checkOwn.owners)


for (const [key, value] of Object.entries(checkOwn.owners)) {
  cate.push(`${value}`)
}

    if(checkOwn.owners!=null) {
      checkOwnLect = true
    }

  } 
  }

if(checkOwnLect==false) {
  if (confirm("Would you like to delete this user?") == true) {
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

    if(res.status === 401) {
     console.log(await res.json())
     RefreshToken()
     removeUser(UserId)

    }

    if(res.status === 400) {


         const TokenValue = ref( await res.json())
   console.log("status from backend = " +  TokenValue.value.message )
   isActivePopup3.value = true
    }
  }
}
  
if(checkOwnLect==true) {
  if (confirm(`${name} is the owner of ${cate} . deletion of this user account will also remove this user from the event category. Do you still want to delete this account?`) == true) {
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

    if(res.status === 401) {
     console.log(await res.json())
     RefreshToken()
     removeUser(UserId)

    }

    if(res.status === 400) {


         const TokenValue = ref( await res.json())
   console.log("status from backend = " +  TokenValue.value.message )
   isActivePopup3.value = true
    }
  }
}
  
  getLinkAll()
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

const goLogin = () => {

router.push({
  name: "Login"
 
});

};
</script>


<template>
     <PopupPage class="" v-show="UserRole==`Lecturer`" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
        Lecturer ไม่สามารถใช้หน้านี้ได้
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup = false , backToHome ()" />
        </div>
      </div>
      </PopupPage>


      <PopupPage v-show="isActivePopup3" :dim-background="true">
      <div class="grid grid-cols-1 p-12" >
        ลบไม่ได้เพราะเป็นอาจารย์คนสุดท้ายในวิชานี้ละงับ
        <div class=" max-w-lg mx-auto  ">
          <br>
          <RoundButton bg-color="bg-gray-400 text-white flex justify-center" button-name="ok"
            @click="isActivePopup3 = false " />
        </div>
      </div>
      </PopupPage>
    <PopupPage v-show="isActivePopup" :dim-background="true">

      <div v-if="TokenTimeOut==false" class="grid grid-cols-1 p-12 ">
       
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

  

    <br>
    <div class="text-sm mb-16 ">
      <div class="text-3xl font-bold text-center   drop-shadow-md mt-8"> USER </div>
      <div class="flex items-center justify-center">
	<div class="container">
		<table class="w-full flex flex-row flex-no-wrap sm:bg-white rounded-lg overflow-hidden sm:shadow-lg my-5">
			<thead class="text-white">
				<tr v-for="(user, index) in UserLists.content" :key="index" class="bg-black flex flex-col flex-no wrap sm:table-row rounded-l-lg sm:rounded-none mb-2 sm:mb-0">
					<th scope="col" class="py-3 px-14 ">
              User name
            </th>
            <th scope="col" class="py-3 px-14">
              Email
            </th>
            <th scope="col" class="py-3 px-14">
              detail
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
				<tr  v-for="(user, index) in UserLists.content" :key="index" class="flex flex-col flex-no wrap sm:table-row rounded-l-lg sm:rounded-none mb-2 sm:mb-0">
          <th scope="row" class="p-3  text-center ">
              {{user.name}}
            </th>
            <td class="p-3 text-center">
              {{user.email}}
            </td>
         

            <td class="p-3">
              <div class="font-medium text-blue-600 text-center hover:underline" @click="isActivePopup=true
                    , dataDetail.name=user.name ,
                    dataDetail.email=user.email ,
                     dataDetail.role=user.role ,
                      dataDetail.updatedOn = user.updatedOn ,
                      dataDetail.createdOn = user.createdOn
                    
                    ">Show</div>

            </td>
            <td class="p-3 text-center">
              {{user.role}}
            </td>
            <td class="p-3 text-center mb-1.5">
              <span class="font-medium text-blue-500  px-2 hover:underline"  @click="goEdit(user.id)">edit</span>
              <span class="font-medium text-red-600  hover:underline" @click="removeUser(user.id , user.role , user.name)">delete</span>

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
      </div>
</template>