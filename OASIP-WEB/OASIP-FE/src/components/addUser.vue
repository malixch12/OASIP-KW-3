<template>
    <div>

            <div @click="login()"  class="bg-green-300">
                login microsoft
            </div>

            <div  @click="logout()"  class="bg-red-300">
              asdsad
            </div>

            

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