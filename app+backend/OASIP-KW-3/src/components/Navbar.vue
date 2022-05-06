<script setup>
function push() {
  router.push({
    name: "ShowList",
    query: { categoryId: categoryId },
  });
}
</script>
 
<template>
  <nav class="bg-white border-gray-200 rounded-t-lg dark:bg-gray-800 ml-24 mr-24">
    <div class="container flex flex-wrap justify-between items-center pr-20 pl-12 py-5">
      <router-link :to="{ name: 'Home' }" class="flex items-center">
        <div class="w-16 "><img src="../assets/logo.png" /></div>
      </router-link>

      <div class="w-full md:block md:w-auto">
        <ul
          class="
            flex flex-col
            md:flex-row md:space-x-8 md:mt-0 text-base md:font-medium
          "
        >
          <li>
            <router-link
              :to="{ name: 'Home' }"
              class="
                block

                text-gray-700
                border-b border-gray-100
                hover:bg-gray-50
                md:hover:text-pink-500 md:p-0
              "
              >Home</router-link
            >
          </li>
          <li>
            <router-link
              :to="{ name: 'Booking' }"
              class="
                 block
                text-gray-700
                border-b border-gray-100
                hover:bg-gray-50
                md:hover:text-pink-500 md:p-0
              "
              >Booking</router-link
            >
          </li>

          <li>
            <router-link
              :to="{ name: 'ListAllByCate' }"
              class="
                 block
                text-gray-700
                border-b border-gray-100
                hover:bg-gray-50
                md:hover:text-pink-500 md:p-0
              "
              >All booking
            </router-link>
          </li>
          <li>
            <router-link
              :to="{ name: 'ListAllEvent', query: { categoryId: 0 } }"
              class="
                 block
                text-gray-700
                border-b border-gray-100
                hover:bg-gray-50
                md:hover:text-pink-500 md:p-0
              "
              >test all book no catetory</router-link
            >
          </li>
        </ul>
      </div>
    </div>
    <hr style="height: 1px;  background-color: gray" />
  </nav>
</template>
 
<style scoped>
.router-link-active {
  color: skyblue;
}
</style>