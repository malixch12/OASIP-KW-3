import { createRouter, createWebHistory } from "vue-router";
import NotFound from "../views/NotFound.vue";
import ShowListAll from "../views/ShowListAll.vue";
import Home from "../views/Home.vue";
import ListAllByCate from "../views/ListAllEventByCate.vue";
import ShowList from "../views/ShowList.vue";
import Booking from "../views/Booking.vue";
import ShowDetails from "../views/ShowDetails.vue"

//const history = createWebHistory();
const history = createWebHistory('/kw3/');
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
  },
  {
    path: "/Booking",
    name: "Booking",
    component: Booking,
  },
  {
    path: "/ShowDetails",
    name: "ShowDetails",
    component: ShowDetails,
  },
];

const router = createRouter({ history, routes });
export default router;
