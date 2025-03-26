<template>
  <div class="action-container">
    <el-input
      v-model="searchKey"
      placeholder="订单号/车牌/客户"
      style="width: 300px; margin-right: 15px;"
      clearable
    />
    <el-select
      v-model="orderStatus"
      placeholder="订单状态"
      style="width: 150px; margin-right: 15px;"
      clearable
    >
      <el-option
        v-for="status in statusOptions"
        :key="status.value"
        :label="status.label"
        :value="status.value"
      />
    </el-select>
    <el-date-picker
      v-model="dateRange"
      type="daterange"
      range-separator="-"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      value-format="YYYY-MM-DD"
    />
    <el-button type="primary" :icon="Search" @click="loadData" style="margin-left: 3%;">查询</el-button>
  </div>

  <!-- 订单表格 -->
  <el-table
    :data="orderList"
    style="width: 100%"
    stripe
    @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="orderNumber" label="订单号" width="100" fixed>
      <template #default="{ row }">
        <el-tag type="info">{{ row.orderId }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="客户信息" width="150">
      <template #default="{ row }">
        <div class="customer-info">
          <div>{{ row.customerName }}</div>
          <div class="contact-info">{{ row.phone }}</div>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="车牌号" min-width="100">
      <template #default="{ row }">
        <div class="vehicle-info">
          <div>{{ row.vehicleLicensePlate }}</div>
          <!-- <div class="detail-info">
            {{ row.brand }} {{ row.model }} {{ row.color }}
          </div> -->
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="totalAmount" label="金额" width="100" align="right">
      <template #default="{ row }">¥ {{ row.orderCost?.toFixed(2) }}</template>
    </el-table-column>
    <el-table-column prop="status" label="状态" width="120">
      <template #default="{ row }">
        <el-tag :type="getStatusTag(row.status)">
          {{ getStatusLabel(row.status) }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" width="180">
      <template #default="{ row }">
        <div>{{ formatDate(row.orderDate) }}</div>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="220" fixed="right">
      <template #default="{ row }">
        <el-button
          v-if="row.status === '待维修'"
          link
          type="warning"
          @click="handleRepair(row.orderId)"
        >
          开始维修
        </el-button>
        <el-button
          v-if="row.status === '维修中'"
          link
          type="success"
          @click="handleRepaired(row.orderId)"
        >
          维修完成
        </el-button>
        <el-button link type="danger" @click="handleCancelOrder(row.orderId)">取消</el-button>
      </template>
    </el-table-column>
  </el-table>


  <!-- 分页 -->
  <el-pagination
    v-model:current-page="pagination.current"
    v-model:page-size="pagination.size"
    :total="pagination.total"
    layout="total, sizes, prev, pager, next, jumper"
    :page-sizes="[10, 20, 50]"
    @size-change="loadData"
    @current-change="loadData"
  />
</template>

<script lang="ts" setup>
import { ref, reactive, computed, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import type { RepairOrderVO, Status } from "@/types/repairOrder";
import { repairOrderApi } from "@/api/repairOrder";

const statusOptions = [
  { value: "待维修" as Status, label: "待维修" },
  { value: "维修中" as Status, label: "维修中" },
  { value: "已完成" as Status, label: "已完成" },
  { value: "已取消" as Status, label: "已取消" }
];

const statusTagMap: { [key in Status]: string } = {
  "待维修": "info",
  "维修中": "warning",
  "已完成": "success",
  "已取消": "danger"
};

const statusLabels: { [key in Status]: string } = {
  "待维修": "待维修",
  "维修中": "维修中",
  "已完成": "已完成",
  "已取消": "已取消"
};

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
});

const orderList = ref<RepairOrderVO[]>([]);
const selectedIds = ref<number[]>([]);
const searchKey = ref("");
const orderStatus = ref<Status>('待维修');
const dateRange = ref<string[]>([]);

const getStatusTag = (status: string) => statusTagMap[status as Status];
const getStatusLabel = (status: string) => statusLabels[status as Status];

const loadData = async () => {
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      searchKey: searchKey.value,
      status: orderStatus.value,
      startDate: dateRange.value?.[0],
      endDate: dateRange.value?.[1]
    };
    const res = await repairOrderApi.pageQuery(params);
    orderList.value = res.data.data.records || [];
    pagination.total = res.data.data.total || 0;
  } catch (error) {
    console.error("加载数据失败:", error);
  }
};

const handleSelectionChange = (selection: RepairOrderVO[]) => {
  selectedIds.value = selection.map(order => order.orderId);
};

// 将订单状态修改为”维修中“
const handleRepair = async (orderId: number) => {
  const status: Status = "维修中"
  try {
    const params = {
      status: status
    }
    await repairOrderApi.update(orderId,params);
    ElMessage.success("订单状态已更新为“维修中”");
    loadData();
  } catch (error) {
    ElMessage.error("更新状态失败");
    console.error("更新状态失败:", error);
  }
};

// 将订单状态修改为”已完成“
const handleRepaired = async (orderId: number) => {
  const status: Status = "已完成"
  try {
    const params = {
      status: status
    }
    await repairOrderApi.update(orderId,params);
    ElMessage.success("订单已完成");
    loadData();
  } catch (error) {
    ElMessage.error("订单完成失败：" + error);
    console.error("订单完成失败：", error);
  }
};

// 取消订单
const handleCancelOrder = async (orderId: number) => {
  const status: Status = "已取消"
  try {
    const params = {
      status: status
    }
    const res = await repairOrderApi.update(orderId,params);
    if (res.data.code !== 1) {
      ElMessage.error("取消失败：" + res.data.msg);
    } else {
      ElMessage.success("订单已成功取消");
      loadData();
    }
  } catch (error) {
    ElMessage.error("取消失败：" + error);
    console.error("取消失败：", error);
  }
};

// 将日期数组转换为 Date 对象
const formatDate = (dateArray: number[]) => {
  if (!dateArray || (dateArray.length !== 5 && dateArray.length !== 6)) {
    return '无效日期';
  }
  let date;
  if(dateArray.length === 5){
    const [year, month, day, hours, minutes] = dateArray;
    date = new Date(year, month, day, hours, minutes);
  }else{
    const [year, month, day, hours, minutes,second] = dateArray;
    date = new Date(year, month, day, hours, minutes,second);
  }
  return date.toLocaleString(); // 格式化为本地日期和时间字符串
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.customer-info {
  line-height: 1.4;
}
.contact-info {
  font-size: 0.9em;
  color: #666;
}
.vehicle-info {
  line-height: 1.5;
}
.detail-info {
  font-size: 0.9em;
  color: #666;
}
.total-amount {
  text-align: right;
  font-size: 1.2em;
  font-weight: bold;
  padding: 10px 20px;
  background: #f5f7fa;
  border-radius: 4px;
  margin-top: 20px;
}
</style>