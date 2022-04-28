import { createRouter, createWebHistory } from "vue-router";
import NotFound from "../views/NotFound.vue";
import ShowListAll from "../views/ShowListAll.vue";
import Home from "../views/Home.vue";
import ListAllByCate from "../views/ListAllEventByCate.vue";
import ShowList from "../views/ShowList.vue";

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
    component: ShowListAll,
},
{
    path: "/:pageNotFound(.*)",
    name: "NotFound",
    component: NotFound,
 },
 {
    path: "/ListAllByCate",
    name: "ListAllByCate",
    component: ListAllByCate,
 },
 {
    path: "/ShowList",
    name: "ShowList",
    component: ShowList,
 }

  
];

const router = createRouter({ history, routes });
export default router;
