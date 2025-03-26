import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/tlias/HomeView.vue'
import MainLayOut from '@/layout/MainLayOut.vue';
import store from '@/store';

const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/tlias/LoginView.vue"),
  },
  {
    path: "/",
    name: "App",
    component: MainLayOut,
    children: [
      {
        path: "",
        name: "Home",
        component: () => import("../views/tlias/HomeView.vue"),
      },
      {
        path: "about",
        name: "About",
        component: () => import("../views/tlias/AboutView.vue"),
      },
      {
        path: "hostPage",
        name: "主页",
        component: () => import("../views/tlias/HostPage.vue"),
      },
      {
        path: "dataTable",
        name: "表格",
        component: () => import("../views/tlias/DataTable.vue"),
      },
      {
        path: "customerView",
        name: "客户管理",
        component: () => import("../views/tlias/CustomerView.vue"),
      },
      {
        path: "vehicleView",
        name: "车辆信息管理",
        component: () => import("../views/tlias/VehicleView.vue"),
      },
      {
        path: "repairOrderView",
        name: "维修订单管理",
        component: () => import("../views/tlias/RepairOrderView.vue"),
      },
      {
        path: "partManagementView",
        name: "零件管理",
        component: () => import("../views/tlias/PartManagementView.vue"),
      },
      {
        path: "serviceManagementView",
        name: "收费服务管理",
        component: () => import("../views/tlias/ServiceManagementView.vue"),
      },
    ],
    meta: { requireAuth: true }, // 需要认证
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth && !store.state.isAuthenticated) {
    next({ name: "Login" }); // 如果需要认证但未登录，跳转到登录页面
  } else {
    next(); // 允许访问
  }
});

export default router
