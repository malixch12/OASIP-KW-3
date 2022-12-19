<template>
    <div>

          
          <div class="text-center mt-60 text-3xl">we're taking you logout</div>
          <div class="text-center  text-lg">You can close this page now.</div>

            

    </div>

  </template>
  
  <script>
  import { onMounted } from 'vue'

  import aad from "../services/aad.js";
  export default {
    name: "App",
    data: () => ({
      items: ["Apple", "Banana", "Mango"],
      newText: "",
      username: ""
    }),

    setup() {
    onMounted(() => {
     

  aad.logoff() 




    })
  },
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