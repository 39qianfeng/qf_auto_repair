import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/tlias/HomeView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/tlias/AboutView.vue"),
  },
  {
    path: "/hostPage",
    name: "主页",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/tlias/HostPage.vue"),
  },
  {
    path: "/dataTable",
    name: "表格",
    component: () => import("../views/tlias/DataTable.vue"),
  },
  {
    path: "/customerView",
    name: "客户管理",
    component: () => import("../views/tlias/CustomerView.vue"),
  },
  {
    path: "/vehicleView",
    name: "车俩信息管理",
    component: () => import("../views/tlias/VehicleView.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
