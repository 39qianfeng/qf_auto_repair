// src/utils/axios.js
import axios from "axios";
import router from "@/router";
import { ElMessage } from "element-plus";

// 定义一个接口来描述队列中每个请求的结构
interface FailedRequest {
  resolve: (token: string) => void;
  reject: (error: Error) => void;
}

// axios实体axiosInstance
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
  timeout: 10000, // 请求超时时间
});

// 请求拦截器
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      if(config.headers != null){
        config.headers.Authorization = `${token}`;
        // config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // 如果是 401 错误且不是登录请求
    if (
      error.response?.status === 401 &&
      !originalRequest.url.includes("/login")
    ) {
      // 清除本地存储中的 token
      localStorage.removeItem("token");
      localStorage.removeItem("user");

      // 提示用户登录已过期
      ElMessage.error("登录已过期，请重新登录");

      // 跳转到登录页面
      router.push("/login");
    }

    // 如果不是 401 错误，直接返回错误
    return Promise.reject(error);
  }
);

export default axiosInstance;
