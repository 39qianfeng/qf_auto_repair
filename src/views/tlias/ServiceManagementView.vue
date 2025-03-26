<template>
  <div class="service-charge-management">
    <!-- 添加服务按钮 -->
    <el-button type="primary" @click="showCreateDialog">添加服务</el-button>

    <!-- 服务列表表格 -->
    <el-table :data="serviceList" style="width: 100%" stripe>
      <el-table-column prop="serviceName" label="服务名称" width="180" />
      <el-table-column prop="description" label="描述" width="200" />
      <el-table-column prop="price" label="价格" width="120" align="right">
        <template #default="{ row }">¥ {{ row.price.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column prop="duration" label="持续时间" width="120" align="right" />
      <el-table-column prop="isAppointmentRequired" label="是否需要预约" width="120" align="center">
        <template #default="{ row }">
          {{ row.isAppointmentRequired === 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="showEditDialog(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.serviceId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      v-model:currentPage="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="fetchServiceCharges"
    />

    <!-- 添加/编辑服务对话框 -->
    <el-dialog
      v-model="showFormDialog"
      :title="formTitle"
      width="500px"
      destroy-on-close
    >
      <el-form
        ref="serviceForm"
        :model="formData"
        :rules="formRules"
        label-width="120px"
      >
        <el-form-item label="服务名称" prop="serviceName">
          <el-input v-model="formData.serviceName" placeholder="请输入服务名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="formData.price" :min="0" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="持续时间" prop="duration">
          <el-input-number v-model="formData.duration" :min="0" placeholder="请输入持续时间" />
        </el-form-item>
        <el-form-item label="是否需要预约" prop="isAppointmentRequired">
          <el-switch
            v-model="formData.isAppointmentRequired"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="showFormDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { serviceChargeApi} from "@/api/service";
import { ServiceChargeQueryDTO, ServiceChargeVO } from "@/types/service";

const serviceList = ref<ServiceChargeVO[]>([]);
const showFormDialog = ref(false);
const formTitle = ref("添加服务");
const formData = reactive<ServiceChargeQueryDTO>({
  serviceId: undefined,
  serviceName: "",
  description: "",
  price: 0.0,
  duration: 0.0,
  isAppointmentRequired: 0
});
const formRules = {
  serviceName: [{ required: true, message: "请输入服务名称", trigger: "blur" }],
  description: [{ required: true, message: "请输入描述", trigger: "blur" }],
  price: [{ required: true, message: "请输入价格", trigger: "blur" }],
  duration: [{ required: true, message: "请输入持续时间", trigger: "blur" }]
};

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 加载服务列表
const fetchServiceCharges = async () => {
  try {
    const res = await serviceChargeApi.pageQuery({
      current: currentPage.value,
      size: pageSize.value
    });
    const data = res.data.data;
    serviceList.value = data.records;
    total.value = data.total;
  } catch (error) {
    console.error("加载服务失败:", error);
    ElMessage.error("加载服务失败");
  }
};

// 显示添加服务对话框
const showCreateDialog = () => {
  formTitle.value = "添加服务";
  Object.assign(formData, {
    serviceId: undefined,
    serviceName: "",
    description: "",
    price: 0.0,
    duration: 0.0,
    isAppointmentRequired: 0
  });
  showFormDialog.value = true;
};

// 显示编辑服务对话框
const showEditDialog = (service: ServiceChargeVO) => {
  formTitle.value = "编辑服务";
  Object.assign(formData, service);
  showFormDialog.value = true;
};

// 提交表单
const submitForm = async () => {
  try {
    const api = formData.serviceId
      ? serviceChargeApi.update(formData.serviceId!, formData)
      : serviceChargeApi.create(formData);

    await api;
    ElMessage.success("操作成功");
    showFormDialog.value = false;
    fetchServiceCharges();
  } catch (error) {
    ElMessage.error("操作失败");
    console.error("操作失败:", error);
  }
};

// 删除服务
const handleDelete = async (id: number) => {
  try {
    await serviceChargeApi.delete(id);
    ElMessage.success("删除成功");
    fetchServiceCharges();
  } catch (error) {
    ElMessage.error("删除失败");
    console.error("删除失败:", error);
  }
};

onMounted(() => {
  fetchServiceCharges();
});
</script>

<style scoped>
.service-charge-management {
  padding: 20px;
}
</style>