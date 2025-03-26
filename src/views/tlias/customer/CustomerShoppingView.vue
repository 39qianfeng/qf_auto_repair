<template>
  <div class="menu-container">
    <el-card class="menu-card">
      <div class="menu-section">
        <h2>零件</h2>
        <el-table
          :data="parts"
          style="width: 100%"
          stripe
        >
          <el-table-column
            prop="partName"
            label="零件名称"
            width="180"
          />
          <el-table-column
            prop="description"
            label="描述"
            width="200"
          />
          <el-table-column
            prop="salePrice"
            label="价格"
            width="100"
            align="right"
          >
            <template #default="{ row }">
              ¥ {{ row.salePrice }}
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="100"
          >
            <template #default="{ row }">
              <el-button
                type="primary"
                link
                @click="addToCart(row.partId, 'PART', row.partName, row.salePrice)"
              >
                添加到购物车
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="menu-section">
        <h2>服务</h2>
        <el-table
          :data="services"
          style="width: 100%"
          stripe
        >
          <el-table-column
            prop="serviceName"
            label="服务名称"
            width="180"
          />
          <el-table-column
            prop="description"
            label="描述"
            width="200"
          />
          <el-table-column
            prop="price"
            label="价格"
            width="100"
            align="right"
          >
            <template #default="{ row }">
              ¥ {{ row.price }}
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="100"
          >
            <template #default="{ row }">
              <el-button
                type="primary"
                link
                @click="addToCart(row.serviceId, 'SERVICE', row.serviceName, row.price)"
              >
                添加到购物车
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <!-- 购物车按钮 -->
    <el-button type="success" @click="openCart">查看购物车</el-button>

    <!-- 购物车模态窗口 -->
    <CartModal ref="cartModal" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";
import axios from "axios";
import type { PartVO } from "@/types/part";
import type { CartItemDTO } from "@/types/cart";
import { ServiceChargeVO } from "@/types/service";
import { cartApi } from "@/api/customer/cart";
import { partApi } from "@/api/customer/part";
import { serviceChargeApi } from "@/api/customer/service";
import CartModal from "./component/CartModal.vue";

const parts = ref<PartVO[]>([]);
const services = ref<ServiceChargeVO[]>([]);
const cartModal = ref<InstanceType<typeof CartModal> | null>(null);

const addToCart = async (itemId: number, itemType: string, itemName: string, itemPrice: number) => {
  const cartItem: CartItemDTO = {
    customerId: 1, // 假设当前用户ID为1
    itemId,
    itemType,
    itemName,
    itemPrice,
    number: 1 // 默认数量为1
  };

  try {
    await cartApi.addToCart(cartItem);
    ElMessage.success("商品已添加到购物车");
  } catch (error) {
    ElMessage.error("添加到购物车失败");
    console.error("添加到购物车失败", error);
  }
};

const fetchParts = async () => {
  try {
    const response = await partApi.pageQuery({ current: 1, size: 100 });
    parts.value = response.data.data.records || [];
  } catch (error) {
    console.error("加载零件失败", error);
  }
};

const fetchServices = async () => {
  try {
    const response = await serviceChargeApi.pageQuery({ current: 1, size: 100 });
    services.value = response.data.data.records || [];
  } catch (error) {
    console.error("加载服务失败", error);
  }
};

const openCart = () => {
  if (cartModal.value) {
    console.log('open 方法被调用'); // 添加日志
    cartModal.value.open();
  } else {
    console.error('CartModal 组件未正确引用');
  }
};

onMounted(() => {
  fetchParts();
  fetchServices();
});
</script>

<style scoped>
.menu-container {
  padding: 20px;
}

.menu-card {
  margin-bottom: 20px;
}

.menu-section {
  margin-bottom: 20px;
}

h2 {
  margin-bottom: 10px;
}
</style>