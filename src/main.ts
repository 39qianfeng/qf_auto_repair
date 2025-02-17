// main.ts
import { createApp } from "vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import App from "./App.vue";
import axios from "axios";
import axiosInstance from "./utils/axios";
import router from './router'; // 确保引入路由
import store from "./store";

const app = createApp(App);

app.use(ElementPlus);
app.use(router); // 使用路由
app.use(store); // 使用vuex store
app.mount("#app");
