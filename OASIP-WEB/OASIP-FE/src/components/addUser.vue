<template>
    <div>

          

            <button class="bg-gray-300 mt-2 w-full  rounded"> <img src="../assets/SignIn_with_microsoft-removebg-preview.png" class="h-14"/></button>

            <!-- <div  @click="logoff()"  class="bg-red-300">
              logout
            </div> -->

            

    </div>

  </template>
  
  <script>
  import aad from "../services/aad.js";
  export default {
    name: "App",
    data: () => ({
      items: ["Apple", "Banana", "Mango"],
      newText: "",
      username: ""
    }),
    created() {
      aad.getAccount().then((account) => {
        if(account !== null) {
          this.username = account.userName;
        }
      });
    },
    methods: {
      deleteItem(index) {
        this.items.splice(index, 1);
      },
      addItem() {
        this.items.push(this.newText);
        this.newText = "";
      },
      login() {
        aad.login().then((account) => {
          this.username = account.userName;
        });
      },
      logoff() {
        aad.logoff();
      }
    }
  };
  </script>