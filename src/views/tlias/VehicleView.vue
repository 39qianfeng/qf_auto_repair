<template>
  <div class="action-container">
    <el-input
      v-model="searchKey"
      placeholder="车牌号/VIN/客户姓名"
      style="width: 300px; margin-right: 15px;"
      clearable
    />
    <el-select
      v-model="CustomerType"
      placeholder="客户类型"
      style="width: 120px; margin-right: 15px;"
      clearable
    >
      <el-option label="VIP" value="VIP客户" />
      <el-option label="普通" value="普通客户" />
    </el-select>
    <el-button type="primary" :icon="Search" @click="loadData">查询</el-button>

    <div class="button-group" style="padding-top: 20px;padding-bottom: 20px;">
      <el-button type="primary" @click="handleAddDialog">新增车辆</el-button>
      <el-button type="warning" @click="handleEditDialog">修改信息</el-button>
      <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
    </div>
  </div>

  <!-- 车辆表格 -->
  <el-table
    :data="vehicleList"
    style="width: 100%"
    stripe
    @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="vehicleId" label="车俩Id" width="80" fixed>
      <template #default="{row}">
        <el-tag>{{ row.vehicleId }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="licensePlate" label="车牌号" width="130">
      <template #default="{row}">
        <el-tag>{{ row.licensePlate }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="客户信息" width="130">
      <template #default="{row}">
        <div class="customer-info">
          <div>{{ row.customerName }}</div>
          <div class="customer-type" :class="row.customerType.toLowerCase()">
            {{ row.customerType }}
          </div>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="车辆信息" min-width="220">
      <template #default="{row}">
        <div class="vehicle-info">
          <div>{{ row.brand }} {{ row.model }}</div>
          <div class="detail-info">
            <span>VIN：{{ row.vin }}</span>
            <span>发动机：{{ row.engineNo }}</span>
          </div>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="mileage" label="里程(km)" width="120" align="right">
      <template #default="{row}">
        {{ row.mileage?.toLocaleString() }}
      </template>
    </el-table-column>
    <el-table-column prop="repairOrderCount" label="维修次数" width="100" align="center">
      <template #default="{row}">
        <el-tag :type="row.repairOrderCount > 0 ? 'success' : 'info'">
          {{ row.repairOrderCount }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="150" fixed="right">
      <template #default="{row}">
        <el-button link type="primary" @click="showDetail(row.vehicleId)">详情</el-button>
        <el-button link type="danger" @click="handleDelete(row.vehicleId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <el-pagination
    v-model:current-page="pagination.current"
    v-model:page-size="pagination.size"
    :total="pagination.total"
    layout="total, sizes, prev, pager, next, jumper"
    :page-sizes="[1, 2, 3]"
    @size-change="loadData"
    @current-change="loadData"
  />

  <!-- 新增/编辑对话框 -->
  <el-dialog
    v-model="showAddOrEditDialog"
    :title="formTitle"
    width="600px"
    destroy-on-close
  >
    <el-form
      ref="vehicleForm"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="客户" prop="customerId" v-if="formMode === 'add'">
            <el-input
              v-model="formData.customerId"
              placeholder="请输入客户ID"
            />
          </el-form-item>
          <el-form-item label="车俩" prop="vehicleId" v-if="formMode === 'edit'">
            <el-input
              v-model="formData.vehicleId"
              placeholder="请输入车俩ID"
            />
          </el-form-item>
          <el-form-item label="车牌号" prop="licensePlate">
            <el-input v-model="formData.licensePlate" />
          </el-form-item>
          <el-form-item label="品牌型号" required>
            <el-input-group>
              <el-input 
                v-model="formData.brand" 
                placeholder="品牌" 
                style="width: 48%; margin-right: 4%"
              />
              <el-input
                v-model="formData.model"
                placeholder="型号"
                style="width: 48%"
              />
            </el-input-group>
          </el-form-item>
        </el-col>
        
        <el-col :span="12">
          <el-form-item label="VIN码" prop="vin">
            <el-input v-model="formData.vin" maxlength="17" show-word-limit />
          </el-form-item>
          <el-form-item label="发动机号" prop="engineNo">
            <el-input v-model="formData.engineNo" />
          </el-form-item>
          <el-form-item label="当前里程" prop="mileage">
            <el-input-number
              v-model="formData.mileage"
              :min="0"
              :step="1000"
              controls-position="right"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <el-button @click="showFormDialog = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive, computed , onMounted } from 'vue'
import axiosInstance from '@/utils/axios'
import type { FormInstance } from 'element-plus'
import { Edit, Search } from '@element-plus/icons-vue'
import { Customer,Vehicle,Result,IPage } from '@/types/common'
import { getCustomersPage } from '@/api/customer'
import { deleteVehicle, getVehicleById, batchDeleteVehicles } from '@/api/vehicle'
import { ElMessage } from 'element-plus'

// 响应式数据
const searchKey = ref<string>('')
const vehicleList = ref<Vehicle[]>([])
const customerList = ref<Customer[]>([])
const selectedIds = ref<number[]>([])
const showAddOrEditDialog = ref(false)
let formTitle = ref('新增表单')
let CustomerType = ref<string>()
let input = ref('')
let vehicleForm = ref<FormInstance | null>(null);

// 分页配置
const pagination = reactive({
  current: 1,
  size: 2,
  total: 0
})

// 表单相关
const showFormDialog = ref(false)
const formMode = ref<'add' | 'edit'>('add')
const formData = reactive({
  vehicleId: null,       // 车辆ID，通常由后端生成，前端可能不需要用户输入
  customerId: null,      // 客户ID，用户需要输入
  licensePlate: '',     // 车牌号，用户需要输入
  brand: '',            // 品牌，用户需要输入
  model: '',            // 型号，用户需要输入
  vin: '',              // VIN码，用户需要输入
  engineNo: '',         // 发动机号，用户需要输入
  mileage: 0,           // 里程，用户需要输入
})

// 表单验证规则
const formRules = {
  customerId: [{ required: true, message: '请选择客户', trigger: 'blur' }],
  licensePlate: [
    { required: true, message: '请输入车牌号', trigger: 'blur' },
    { pattern: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼][A-HJ-NP-Z][A-HJ-NP-Z0-9]{4,5}[A-HJ-NP-Z0-9挂学警港澳]$/, 
      message: '车牌格式不正确' }
  ],
  vin: [
    { required: true, message: '请输入VIN码', trigger: 'blur' },
    { len: 17, message: 'VIN码必须为17位', trigger: 'blur' }
  ],
  engineNo: [
    { required: true, message: '请输入发动机号', trigger: 'blur' },
    { pattern: /^[A-HJ-NPR-Z0-9]{6,8}$/, message: '发动机号格式错误' }
  ]
}

// 加载数据
const loadData = async () => {
  try {
    // 获取分页参数和搜索关键字
    const siz = pagination.size; // 获取每页显示条数
    const cur = pagination.current; // 获取当前页码
    const ctype = CustomerType.value // 获取客户类型
    const query = searchKey.value.trim(); // 获取搜索关键字
    // 查询车辆信息接口
    const url = '/api/vehicles/page';
    const res = await axiosInstance.get<Result<IPage<Vehicle>>>(url, {
      params: {
        current: cur,
        size: siz,
        searchKey: query, // 传递搜索关键字
        customerType: ctype // 传递客户类型
      }
    });

    // 检查返回状态码
    if (res.data.code !== 1) {
      console.error("返回失败，出错码为:", res.data.code);
      alert(res.data.msg || '查询失败');
      return;
    }

    // 更新车辆列表和分页信息
    vehicleList.value = res.data.data.records; // 更新车辆列表
    pagination.total = res.data.data.total; // 更新总记录数
  } catch (error) {
    // 捕获并处理请求错误
    console.error('请求失败:', error);
  }
};

// 切换新增表单
const handleAddDialog = () => {
  showAddOrEditDialog.value = true;
  formMode.value = 'add';
}

// 切换修改表单
const handleEditDialog = () => {
  showAddOrEditDialog.value = true;
  formMode.value = 'edit';
}

// 提交表单
const submitForm = async () => {
  const valid = await (vehicleForm.value as FormInstance).validate()
  if (!valid) return

  const api = formMode.value === 'add' 
    ? axiosInstance.post('/api/vehicles', formData)
    : axiosInstance.put(`/api/vehicles/${formData.vehicleId}`, formData)

  try {
    await api
    loadData()
    showFormDialog.value = false
    showAddOrEditDialog.value = false
  } catch (error) {
    console.error('提交失败', error)
  }
}

// // 加载客户信息
// const loadCustomers = getCustomersPage;

// 删除车俩信息
const handleDelete = (id:number) => {
  deleteVehicle(id);
  loadData();
}

// 捕获用户勾选的车辆 ID
const handleSelectionChange = (selection: Vehicle[]) => {
  selectedIds.value = selection.map((vehicle) => vehicle.vehicleId);
};

// 批量删除
const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.error('请先选择要删除的车辆');
    return;
  }

  try {
    await batchDeleteVehicles(selectedIds.value);
    ElMessage.success('批量删除成功');
    loadData();
  } catch (error) {
    ElMessage.error('批量删除失败');
    console.error('批量删除失败:', error);
  }
};

// 初始化加载
onMounted(() => {
    loadData();
});
</script>

<style scoped>
.customer-info {
  line-height: 1.4;
}
.customer-type {
  font-size: 0.8em;
  padding: 2px 5px;
  border-radius: 3px;
  display: inline-block;
}
.customer-type.vip {
  background: #fff3e0;
  color: #ff9800;
}
.vehicle-info {
  line-height: 1.5;
}
.detail-info {
  font-size: 0.9em;
  color: #666;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
</style>