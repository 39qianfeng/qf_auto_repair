import { createStore } from 'vuex'

export default createStore({
  state: {
    isAuthenticated: false, // 默认用户未登录
    user: null, // 默认没有用户信息
  },
  getters: {},
  mutations: {
    setAuth(state, payload) {
      state.isAuthenticated = payload.isAuthenticated; // 更新登录状态
      state.user = payload.user; // 更新用户信息
      localStorage.setItem(
        "isAuthenticated",
        JSON.stringify(payload.isAuthenticated)
      );
      localStorage.setItem("user", JSON.stringify(payload.user));
    },
  },
  actions: {
    login({ commit }, user) {
      commit("setAuth", { isAuthenticated: true, user }); // 调用 mutation 更新状态
      localStorage.setItem("user", user); // 存储 user
      localStorage.setItem("token", user.token); // 存储 token
    },
    logout({ commit }) {
      commit("setAuth", { isAuthenticated: false, user: null }); // 调用 mutation 更新状态
      localStorage.removeItem("isAuthenticated");
      localStorage.removeItem("user");
      localStorage.removeItem("token");
    },
  },
  modules: {},
});
