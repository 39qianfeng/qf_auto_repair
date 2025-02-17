<template>
  <router-view />
</template>

<script setup>
import { useStore } from 'vuex';

const store = useStore();

// 恢复 Vuex 状态
if (localStorage.getItem("store")) {
  store.replaceState(Object.assign({}, store.state, JSON.parse(localStorage.getItem("store"))));
}

// 在页面卸载时存储 Vuex 状态
window.addEventListener("beforeunload", () => {
  localStorage.setItem("store", JSON.stringify(store.state));
});
</script>