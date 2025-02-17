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
    <el-button type="primary" :icon="Search" @click="loadData">查询</el-button>

    <div class="button-group" style="padding-top: 20px;padding-bottom: 20px;">
      <el-button type="primary" @click="showCreateDialog">新建订单</el-button>
      <el-button type="warning" @click="showEditDialog">修改状态</el-button>
      <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
    </div>
  </div>

  <!-- 订单表格 -->
  <el-table
    :data="orderList"
    style="width: 100%"
    stripe
    @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="orderNumber" label="订单号" width="180" fixed>
      <template #default="{row}">
        <el-tag type="info">{{ row.orderNumber }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="客户信息" width="200">
      <template #default="{row}">
        <div class="customer-info">
          <div>{{ row.customerName }}</div>
          <div class="contact-info">{{ row.customerPhone }}</div>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="车辆信息" min-width="220">
      <template #default="{row}">
        <div class="vehicle-info">
          <div>{{ row.licensePlate }}</div>
          <div class="detail-info">
            {{ row.brand }} {{ row.model }} {{ row.color }}
          </div>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="totalAmount" label="金额" width="120" align="right">
      <template #default="{row}">¥ {{ row.totalAmount?.toFixed(2) }}</template>
    </el-table-column>
    <el-table-column prop="status" label="状态" width="120">
      <template #default="{row}">
        <el-tag :type="statusTagMap[row.status]">
          {{ statusLabels[row.status] }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" width="180" />
    <el-table-column label="操作" width="180" fixed="right">
      <template #default="{row}">
        <el-button link type="primary" @click="showDetail(row.id)">详情</el-button>
        <el-button link type="primary" @click="printOrder(row.id)">打印</el-button>
        <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
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

  <!-- 订单详情对话框 -->
  <el-dialog
    v-model="showDetailDialog"
    title="订单详情"
    width="800px"
  >
    <el-descriptions :column="2" border>
      <el-descriptions-item label="订单号">{{ currentOrder.orderNumber }}</el-descriptions-item>
      <el-descriptions-item label="客户姓名">{{ currentOrder.customerName }}</el-descriptions-item>
      <el-descriptions-item label="联系电话">{{ currentOrder.customerPhone }}</el-descriptions-item>
      <el-descriptions-item label="车牌号">{{ currentOrder.licensePlate }}</el-descriptions-item>
      <el-descriptions-item label="车辆品牌">{{ currentOrder.brand }}</el-descriptions-item>
      <el-descriptions-item label="车辆型号">{{ currentOrder.model }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ currentOrder.createTime }}</el-descriptions-item>
      <el-descriptions-item label="完成时间">{{ currentOrder.completeTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="总金额">¥ {{ currentOrder.totalAmount?.toFixed(2) }}</el-descriptions-item>
    </el-descriptions>

    <el-divider content-position="left">维修项目</el-divider>
    <el-table :data="currentOrder.services" size="small">
      <el-table-column prop="itemName" label="项目名称" />
      <el-table-column prop="price" label="单价" align="right" />
      <el-table-column prop="quantity" label="数量" align="center" />
      <el-table-column prop="total" label="小计" align="right" />
    </el-table>

    <el-divider content-position="left">使用配件</el-divider>
    <el-table :data="currentOrder.parts" size="small">
      <el-table-column prop="partName" label="配件名称" />
      <el-table-column prop="price" label="单价" align="right" />
      <el-table-column prop="quantity" label="数量" align="center" />
      <el-table-column prop="total" label="小计" align="right" />
    </el-table>
  </el-dialog>

  <!-- 创建/编辑对话框 -->
  <el-dialog
    v-model="showFormDialog"
    :title="formTitle"
    width="800px"
    destroy-on-close
  >
    <el-form
      ref="orderForm"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="客户选择" prop="customerId">
            <el-select
              v-model="formData.customerId"
              filterable
              placeholder="搜索客户"
              @change="loadCustomerVehicles"
            >
              <el-option
                v-for="customer in customerList"
                :key="customer.customerId"
                :label="customer.name"
                :value="customer.customerId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="车辆选择" prop="vehicleId">
            <el-select
              v-model="formData.vehicleId"
              :disabled="!formData.customerId"
              placeholder="请先选择客户"
            >
              <el-option
                v-for="vehicle in vehicleList"
                :key="vehicle.vehicleId"
                :label="`${vehicle.licensePlate} (${vehicle.brand})`"
                :value="vehicle.vehicleId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单状态" prop="status">
            <el-select v-model="formData.status">
              <el-option
                v-for="status in statusOptions"
                :key="status.value"
                :label="status.label"
                :value="status.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="紧急程度" prop="priority">
            <el-select v-model="formData.priority">
              <el-option label="普通" value="NORMAL" />
              <el-option label="加急" value="URGENT" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="left">维修项目</el-divider>
      <service-item-editor v-model="formData.services" />

      <el-divider content-position="left">使用配件</el-divider>
      <part-usage-editor v-model="formData.parts" />

      <el-divider />
      <div class="total-amount">
        预估总金额：¥ {{ totalAmount.toFixed(2) }}
      </div>
    </el-form>

    <template #footer>
      <el-button @click="showFormDialog = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive, computed, onMounted } from 'vue'
import axiosInstance from '@/utils/axios'
import type { FormInstance } from 'element-plus'
import { Search } from '@element-plus/icons-vue'

interface RepairOrder {
  id: number
  orderNumber: string
  customerId: number
  customerName: string
  customerPhone: string
  vehicleId: number
  licensePlate: string
  brand: string
  model: string
  color: string
  totalAmount: number
  status: string
  createTime: string
  completeTime?: string
  services: ServiceItem[]
  parts: PartUsage[]
}

interface ServiceItem {
  itemName: string
  price: number
  quantity: number
  total: number
}

interface PartUsage {
  partName: string
  price: number
  quantity: number
  total: number
}

// 状态配置
const statusOptions = [
  { value: 'CREATED', label: '已创建' },
  { value: 'PROCESSING', label: '进行中' },
  { value: 'COMPLETED', label: '已完成' },
  { value: 'CANCELLED', label: '已取消' }
]

const statusTagMap = {
  CREATED: 'info',
  PROCESSING: 'warning',
  COMPLETED: 'success',
  CANCELLED: 'danger'
}

const statusLabels = statusOptions.reduce((acc, cur) => {
  acc[cur.value] = cur.label
  return acc
}, {} as Record<string, string>)

// 分页配置
const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

// 窗口名称
const formTitle = ref('新增订单');

// 数据列表
const orderList = ref<RepairOrder[]>([])
const customerList = ref<any[]>([])
const vehicleList = ref<any[]>([])
const selectedIds = ref<number[]>([])
const currentOrder = ref<RepairOrder>({} as RepairOrder)

// 表单相关
const showFormDialog = ref(false)
const showDetailDialog = ref(false)
const formMode = ref<'create' | 'edit'>('create')
const orderForm = ref<FormInstance>()
const searchKey = ref('')
const orderStatus = ref('')
const dateRange = ref<string[]>([])

const formData = reactive({
  id: null as number | null,
  customerId: null as number | null,
  vehicleId: null as number | null,
  status: 'CREATED',
  priority: 'NORMAL',
  services: [] as ServiceItem[],
  parts: [] as PartUsage[]
})

// 表单验证规则
const formRules = {
  customerId: [{ required: true, message: '请选择客户', trigger: 'change' }],
  vehicleId: [{ required: true, message: '请选择车辆', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

// 计算总金额
const totalAmount = computed(() => {
  const serviceTotal = formData.services.reduce((sum, item) => sum + item.price * item.quantity, 0)
  const partTotal = formData.parts.reduce((sum, item) => sum + item.price * item.quantity, 0)
  return serviceTotal + partTotal
})

// 加载数据
const loadData = async () => {
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      searchKey: searchKey.value,
      status: orderStatus.value,
      startDate: dateRange.value?.[0],
      endDate: dateRange.value?.[1]
    }

    const res = await axiosInstance.get('/api/repair-orders/page', { params })
    orderList.value = res.data.data.records
    pagination.total = res.data.data.total
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

// 加载客户列表
const loadCustomers = async () => {
  const res = await axiosInstance.get('/api/customers')
  customerList.value = res.data.data
}

// 加载客户车辆
const loadCustomerVehicles = async () => {
  if (!formData.customerId) return
  const res = await axiosInstance.get(`/api/vehicles?customerId=${formData.customerId}`)
  vehicleList.value = res.data.data
}

// 显示创建对话框
const showCreateDialog = () => {
  formMode.value = 'create'
  Object.assign(formData, {
    id: null,
    customerId: null,
    vehicleId: null,
    status: 'CREATED',
    priority: 'NORMAL',
    services: [],
    parts: []
  })
  showFormDialog.value = true
}

// 提交表单
const submitForm = async () => {
  if (!orderForm.value) return
  const valid = await orderForm.value.validate()
  if (!valid) return

  try {
    const api = formMode.value === 'create' 
      ? axiosInstance.post('/api/repair-orders', formData)
      : axiosInstance.put(`/api/repair-orders/${formData.id}`, formData)

    await api
    loadData()
    showFormDialog.value = false
  } catch (error) {
    console.error('提交失败:', error)
  }
}

// 初始化加载
onMounted(() => {
  loadData()
  loadCustomers()
})
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