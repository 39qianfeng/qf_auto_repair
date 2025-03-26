// src/store/userStore.js
import { createStore } from "vuex";

export default createStore({
  state: {
    isAuthenticated: false, // 默认用户未登录
    user: null, // 默认没有用户信息
    userRole: "", // 用户角色，例如 'customer'
  },
  getters: {},
  mutations: {
    setAuth(state, payload) {
      state.isAuthenticated = payload.isAuthenticated; // 更新登录状态
      state.user = payload.user; // 更新用户信息
      state.userRole = payload.userRole; // 更新用户角色
      localStorage.setItem(
        "isAuthenticated",
        JSON.stringify(payload.isAuthenticated)
      );
      localStorage.setItem("user", JSON.stringify(payload.user));
      localStorage.setItem("userRole", payload.userRole);
    },
  },
  actions: {
    login({ commit }, user) {
      commit("setAuth", {
        isAuthenticated: true,
        user,
        userRole: user.type || "customer", // 默认角色为 'customer'
      }); // 调用 mutation 更新状态
      localStorage.setItem("user", JSON.stringify(user));
      localStorage.setItem("token", user.token);
      localStorage.setItem("type", user.type || "customer");
    },
    logout({ commit }) {
      commit("setAuth", {
        isAuthenticated: false,
        user: null,
        userRole: "",
      }); // 调用 mutation 更新状态
      localStorage.removeItem("isAuthenticated");
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      localStorage.removeItem("type");
    },
  },
  modules: {},
});
