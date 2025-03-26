<template>
  <div class="common-layout">
    <el-container>
      <el-header style="text-align: center">
        <div class="header-content">
          <!-- 登录链接 -->
          <div class="header-left">
            <router-link v-if="!isAuthenticated" to="/login" class="login-link">
              <el-link>登录</el-link>
            </router-link>
            <el-link v-else @click="logout">退出</el-link>
          </div>
          <!-- 标题 -->
          <div class="header-center">
            <h1>千风汽修</h1>
          </div>
        </div>
      </el-header>
      <el-container>
        <el-aside style="width: 280px; height: auto">
          <el-row class="tac">
            <el-col :span="18">
              <h5 class="mb-2">选项</h5>
              <el-menu
                default-active="1"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
              >
                <router-link to="/customer/customerShoppingView" class="menu-link">
                  <el-menu-item index="1">
                    <el-icon><icon-menu /></el-icon>
                    <span>数据分析与报告</span>
                  </el-menu-item>
                </router-link>
                <!-- <router-link to="/dataTable" class="menu-link"> -->
                <!-- <router-link to="/customerView" class="menu-link">
                  <el-menu-item index="2">
                    <el-icon><icon-menu /></el-icon>
                    <span>客户管理</span>
                  </el-menu-item>
                </router-link> -->
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <!-- Main -->
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

// 获取store和router实体
const store = useStore();
const router = useRouter();

// 计算属性，获取登录状态
const isAuthenticated = store.state.isAuthenticated;

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};

// 退出登录
const logout = () => {
  store.dispatch('logout'); // 调用 Vuex 的 logout action
  router.push('/customerlogin'); // 重定向到登录页面
}

</script>

<style scoped>
.menu-link {
  text-decoration: none; /* 去掉下划线 */
}

.header-content {
  display: flex;
  width: 100%;
  align-items: center;
}

.header-left {
  display: flex;
}

.header-center {
  display: flex;
  margin-left: 50%; /* 将header-center推到中间 */
}
</style>
