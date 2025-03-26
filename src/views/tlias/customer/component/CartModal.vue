<template>
  <el-dialog
    v-model="visible"
    title="购物车"
    width="50%"
    :before-close="handleClose"
  >
    <el-table
      :data="cartItems"
      style="width: 100%"
      stripe
    >
      <el-table-column
        prop="itemName"
        label="商品名称"
        width="180"
      />
      <el-table-column
        prop="itemPrice"
        label="价格"
        width="100"
        align="right"
      >
        <template #default="{ row }">
          ¥ {{ row.itemPrice }}
        </template>
      </el-table-column>
      <el-table-column
        prop="number"
        label="数量"
        width="200"
      >
        <template #default="{ row }">
          <el-input-number
            v-model="row.number"
            :min="1"
            @change="updateCartItem(row.cartId, row.number)"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="100"
      >
        <template #default="{ row }">
          <el-button
            type="danger"
            link
            @click="deleteCartItem(row.cartId)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="total">
      <span>总价: ¥ {{ total }}</span>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="checkout">结算</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import { ref } from 'vue';

import { ElMessage } from 'element-plus';
import type { CartItemVO } from '@/types/cart';
import { cartApi } from '@/api/customer/cart';
import { repairOrderApi } from '@/api/customer/repairOrder';
import { Result } from '@/types/common';
import { RepairOrderVO } from '@/types/repairOrder';

export default {
  name: 'CartModal',
  setup() {
    const visible = ref(false); // 控制模态窗口的显示
    const cartItems = ref<CartItemVO[]>([]);
    const total = ref(0);
    const loading = ref(false); // 加载状态

    const open = () => {
      visible.value = true;
      fetchCartItems();
    };

    const handleClose = () => {
      visible.value = false;

    };
    
    // 钩取数据
    const fetchCartItems = async () => {
      loading.value = true;
      try {
        const userstring = localStorage.getItem("userstring");
        if(!userstring){
          console.log("userstring为空");
          return;
        }
        const user = JSON.parse(userstring);
        const customerId = user.customerId;
        const response = await cartApi.getCartItems(customerId);
        cartItems.value = response.data.data || [];
        total.value = cartItems.value.reduce((sum, item) => sum + item.itemPrice * item.number, 0);
      } catch (error) {
        ElMessage.error('加载购物车失败');
      } finally {
        loading.value = false;
      }
    };

    const updateCartItem = async (cartId: number, quantity: number) => {
      loading.value = true;
      try {
        await cartApi.updateCartItem(cartId, quantity);
        fetchCartItems();
      } catch (error) {
        ElMessage.error('更新购物车失败');
      } finally {
        loading.value = false;
      }
    };

    const deleteCartItem = async (cartId: number) => {
      loading.value = true;
      try {
        await cartApi.deleteCartItem(cartId);
        fetchCartItems();
      } catch (error) {
        ElMessage.error('删除购物车项失败');
      } finally {
        loading.value = false;
      }
    };

    // 结算
    const checkout = async () => {
      loading.value = true;
      try {
        // 这里可以调用结算接口
        const userstring = localStorage.getItem("userstring");
        if(!userstring){
          console.log("userstring为空");
          return;
        }
        const user = JSON.parse(userstring);
        const customerId = user.customerId;
        const res = await repairOrderApi.createOrder(customerId);
        const data = res.data;
        if(data.code == 0){
          ElMessage.error('购物车为空，结算失败');
          visible.value = false;
          return;
        }
        ElMessage.success('结算成功');
        visible.value = false;
      } catch (error) {
        ElMessage.error('结算失败');
      } finally {
        loading.value = false;
      }
    };

    // 暴露方法供父组件调用
    return {
      visible,
      open,
      handleClose,
      cartItems,
      total,
      fetchCartItems,
      updateCartItem,
      deleteCartItem,
      checkout
    };
  }
};
</script>

<style scoped>
.total {
  margin-top: 20px;
  text-align: right;
  font-size: 16px;
  font-weight: bold;
}

.loading {
  text-align: center;
  margin-top: 50px;
}
</style>