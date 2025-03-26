<template>
  <div class="login-container">
    <div class="login-box">
      <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="form.username" placeholder="请输入顾客账号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input 
            v-model="form.password" 
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            :loading="loading"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import { customerApi } from "@/api/customer/customer";
import type { CustomerLoginDTO } from "@/types/customer";
import { useStore } from 'vuex';

const router = useRouter();
const route = useRoute();
const store = useStore();

const loginForm = ref<any>(null); // 表单引用
const form = ref<CustomerLoginDTO>({
  username: '',
  password: ''
});
const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
};
const loading = ref(false);

const handleLogin = async () => {
  try {
    loading.value = true;
    await loginForm.value.validate(); // 验证表单

    // 发起登录请求
    const response = await customerApi.login(form.value);

    // 登录成功
    const { customerId, name, token,userName,type } = response.data.data;
    // 更新 Vuex 状态，存储令牌
    store.dispatch('login', {
      customerId,
      name,
      token,
      userName,
      type
    });

    ElMessage.success('登录成功');
    const redirectUrl = '/customer/customerShoppingView';
    // const redirectUrl = route.query.redirect || '/customer/customerShoppingView';
    router.push(redirectUrl as string);
  } catch (error: any) {
    ElMessage.error('登录失败，请检查账号密码');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0; /* 可以根据需要设置背景颜色 */
}

.login-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 300px; /* 设置登录框的宽度 */
}

.el-form-item {
  margin-bottom: 15px;
}

.el-button {
  width: 100%; /* 让按钮宽度与输入框一致 */
}
</style>