import { createRouter, createWebHistory } from "vue-router";
import NotFound from "../views/NotFound.vue";
import ListAllEvent from "../views/ListAllEvent.vue";
import Home from "../views/Home.vue";
const history = createWebHistory();
const routes = [
{
        path: "/",
        name: "Home",
        component: Home,
},
{
    path: "/viewAll",
    name: "ListAllEvent",
    component: ListAllEvent,
},
{
    path: "/:pageNotFound(.*)",
    name: "NotFound",
    component: NotFound,
 }
  
];

const router = createRouter({ history, routes });
export default router;
