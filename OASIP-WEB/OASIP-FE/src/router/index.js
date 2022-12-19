import { createRouter, createWebHashHistory } from "vue-router";
import NotFound from "../views/NotFound.vue";
import ShowListAll from "../views/ShowListAllPage.vue";
import Home from "../views/Home.vue";
import ListAllByCate from "../views/CategoryPage.vue";
import ShowList from "../views/ShowListPage.vue";
import Booking from "../views/BookingPage.vue";
import ShowDetails from "../views/ShowDetails.vue"
import BookingEventByCate from "../views/BookingEventByCategory.vue"
import EditCatePage from "../views/EditCatePage.vue"
import UserManage from "../views/UserManage.vue"
import SignUpPage from "../views/SignUpPage.vue"
import EditUserPage from "../views/EditUserPage.vue"
import Login from "../views/Login.vue"
import logoutPage from "../views/logoutPage.vue"
import GuestViewEvent from "../views/GuestViewEvent.vue"

//const history = createWebHistory();
const history = createWebHashHistory('/kw3/');
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
  },{
    path: "/BookingEventByCate",
    name: "BookingEventByCate",
    component: BookingEventByCate,
  },
  {
    path: "/EditCatePage",
    name: "EditCatePage",
    component: EditCatePage,
  } ,
  {
    path: "/UserManage",
    name: "UserManage",
    component: UserManage,
  }
  ,
  {
    path: "/SignUpPage",
    name: "SignUpPage",
    component: SignUpPage,
  } ,
  {
    path: "/EditUserPage",
    name: "EditUserPage",
    component: EditUserPage,
  } ,
  {
    path: "/Login",
    name: "Login",
    component: Login,
  } ,
  {
    path: "/logoutPage",
    name: "logoutPage",
    component: logoutPage,
  } ,

  {
    path: "/GuestViewEvent",
    name: "GuestViewEvent",
    component: GuestViewEvent,
  }
];

const router = createRouter({ history, routes });
export default router;
