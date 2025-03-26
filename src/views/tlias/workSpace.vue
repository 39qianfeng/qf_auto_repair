<template>
  <div class="workspace">
    <!-- 业务数据 -->
    <el-card class="box-card" header="业务数据">
      <div>
        <p>营业额：¥{{ businessData.turnover ? businessData.turnover.toFixed(2) : '0.00' }}</p>
        <p>有效订单数：{{ businessData.validOrderCount }}</p>
        <p>订单完成率：{{ businessData.orderCompletionRate ? businessData.orderCompletionRate.toFixed(2) : '0.00' }}%</p>
        <p>平均客单价：¥{{ businessData.unitPrice ? businessData.unitPrice.toFixed(2) : '0.00' }}</p>
        <p>新增用户数：{{ businessData.newUsers }}</p>
      </div>
    </el-card>

    <!-- 维修订单总览 -->
    <el-card class="box-card" header="维修订单总览">
      <div>
        <p>待维修订单数量：{{ repairOrderOverView.toBeRepairCount }}</p>
        <p>维修中订单数量：{{ repairOrderOverView.repairingCount }}</p>
        <p>已完成订单数量：{{ repairOrderOverView.completedCount }}</p>
        <p>已取消订单数量：{{ repairOrderOverView.cancelledCount }}</p>
        <p>总订单数量：{{ repairOrderOverView.totalOrderCount }}</p>
      </div>
    </el-card>

    <!-- 客户总览 -->
    <el-card class="box-card" header="客户总览">
      <div>
        <p>客户总数：{{ customerOverView.totalCustomerCount }}</p>
        <p>新增客户数量：{{ customerOverView.newCustomerCount }}</p>
      </div>
    </el-card>

    <!-- 商品总览 -->
    <el-card class="box-card" header="商品总览">
      <div>
        <p>零件商品数量：{{ goodsOverView.partGoodsCount }}</p>
        <p>服务商品数量：{{ goodsOverView.serviceGoodsCount }}</p>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import { ElMessage } from "element-plus";
import {
  GoodsOverViewVO,
  RepairOrderOverViewVO,
  CustomerOverViewVO,
  BusinessDataVO
} from "@/types/workSpace";
import { workspaceApi } from "@/api/workSpace";

const goodsOverView = ref<GoodsOverViewVO>({
  partGoodsCount: 0,
  serviceGoodsCount: 0
});
const repairOrderOverView = ref<RepairOrderOverViewVO>({
  toBeRepairCount: 0,
  repairingCount: 0,
  completedCount: 0,
  cancelledCount: 0,
  totalOrderCount: 0
});
const customerOverView = ref<CustomerOverViewVO>({
  totalCustomerCount: 0,
  newCustomerCount: 0
});
const businessData = ref<BusinessDataVO>({
  turnover: 0,
  validOrderCount: 0,
  orderCompletionRate: 0,
  unitPrice: 0,
  newUsers: 0
});

const fetchWorkspaceData = async () => {
  try {
    const goodsOverViewRes = await workspaceApi.getGoodsOverView();
    const repairOrderOverViewRes = await workspaceApi.getRepairOrderOverView();
    const customerOverViewRes = await workspaceApi.getCustomerOverView();
    const businessDataRes = await workspaceApi.getBusinessData();

    goodsOverView.value = goodsOverViewRes.data.data;
    repairOrderOverView.value = repairOrderOverViewRes.data.data;
    customerOverView.value = customerOverViewRes.data.data;
    businessData.value = businessDataRes.data.data;
  } catch (error) {
    ElMessage.error("加载工作台数据失败");
    console.error("加载工作台数据失败:", error);
  }
};

onMounted(() => {
  fetchWorkspaceData();
});
</script>

<style scoped>
.workspace {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 两列 */
  grid-template-rows: 1fr 1fr; /* 两行 */
  gap: 20px;
  padding: 20px;
}

.box-card {
  width: 100%;
  height: 100%;
}
</style>