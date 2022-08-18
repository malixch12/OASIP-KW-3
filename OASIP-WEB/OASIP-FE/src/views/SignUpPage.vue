<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
const route = useRoute();

</script>

<template>
  <div class="">
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
                            <input type="text" placeholder="Username">
                        </div>
                         <div class="inputBox">
                            <input type="text" placeholder="email">
                        </div>
                         <div class="inputBox">
                            <input type="text" placeholder="role">
                        </div>
                        <div class="inputBox">
                            <input type="submit" value="Sign up">
                        </div>

                        <p class="forget">Dont have an account ?  Click here</p>

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
