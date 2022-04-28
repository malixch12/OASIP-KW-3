<script setup>

</script>
 
<template>
<nav class="bg-white border-gray-200  rounded dark:bg-gray-800">
          <div class="container flex flex-wrap justify-between items-center mx-auto">
              <router-link
                   :to="{name : 'Home'}" class="flex items-center">
        <div class =" w-24"><img src="../assets/logo.png"></div>
   
  </router-link>
            
            <div class="w-full md:block md:w-auto">
              <ul
                class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium"
              >
               <li>
                
                  <router-link
                   :to="{name : 'Home' }"
                    class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-pink-500 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                  >Home</router-link>
                </li>
                <li>
                
                  <router-link
                   :to="{name : '' }"
                    class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-pink-500 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                  >Booking</router-link>
                </li>
                <li>
                   <router-link
                   :to="{name : ''}"
                    class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-pink-500 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                  >our book</router-link>
                </li>

                <li>
                   <router-link
                   :to="{name : 'ListAllByCate'}"
                    class="block py-2 pr-4 pl-3 text-gray-700 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-pink-500 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                  >All booking </router-link>
                </li>
                <li>
                   <router-link
                   :to="{name : 'ListAllEvent' , query: { categoryId: 0 }}"
                    class="block py-2 pr-4 pl-3 text-gray-700 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-pink-500 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent"
                  >test all book no catetory</router-link>
                </li>
              </ul>
              
            </div>
          </div>
          <br><hr style="height:1px;border-width:0;background-color:gray">
        </nav>
</template>
 
<style>

</style>