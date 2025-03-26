<template>
  <div class="employee-management">
    <!-- 添加员工按钮 -->
    <el-button type="primary" @click="showCreateDialog">添加员工</el-button>

    <!-- 员工列表表格 -->
    <el-table :data="employeeList" style="width: 100%" stripe>
      <el-table-column prop="employeeId" label="员工ID" width="100" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="position" label="职位" width="120" />
      <el-table-column prop="phone" label="电话" width="150" />
      <el-table-column prop="email" label="邮箱" width="200" />
      <el-table-column prop="skills" label="技能" width="200" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="showEditDialog(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.employeeId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      v-model:currentPage="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="fetchEmployees"
    />

    <!-- 添加/编辑员工对话框 -->
    <el-dialog
      v-model="showFormDialog"
      :title="formTitle"
      width="500px"
      destroy-on-close
    >
      <el-form
        ref="employeeForm"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="formData.position" placeholder="请输入职位" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="技能" prop="skills">
          <el-input
            v-model="formData.skills"
            type="textarea"
            placeholder="请输入技能"
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
import { employeeApi } from "@/api/employee";
import { EmployeeDTO, EmployeeVO } from "@/types/employee";

const employeeList = ref<EmployeeVO[]>([]);
const showFormDialog = ref(false);
const formTitle = ref("添加员工");
const formData = reactive<EmployeeDTO>({
  employeeId: 1,
  name: "",
  position: "",
  phone: "",
  email: "",
  skills: ""
});
const formRules = {
  name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
  position: [{ required: true, message: "请输入职位", trigger: "blur" }],
  phone: [{ required: true, message: "请输入电话", trigger: "blur" }],
  email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
  skills: [{ required: true, message: "请输入技能", trigger: "blur" }]
};

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 加载员工列表
const fetchEmployees = async () => {
  try {
    const res = await employeeApi.getAllEmployees();
    const data = res.data.data;
    employeeList.value = data;
    total.value = data.length;
  } catch (error) {
    console.error("加载员工失败:", error);
    ElMessage.error("加载员工失败");
  }
};

// 显示添加员工对话框
const showCreateDialog = () => {
  formTitle.value = "添加员工";
  Object.assign(formData, {
    employeeId: undefined,
    name: "",
    position: "",
    phone: "",
    email: "",
    skills: ""
  });
  showFormDialog.value = true;
};

// 显示编辑员工对话框
const showEditDialog = (employee: EmployeeVO) => {
  formTitle.value = "编辑员工";
  Object.assign(formData, employee);
  showFormDialog.value = true;
};

// 提交表单
const submitForm = async () => {
  try {
    const api = formData.employeeId
      ? employeeApi.updateEmployee(formData.employeeId!, formData)
      : employeeApi.createEmployee(formData);

    await api;
    ElMessage.success("操作成功");
    showFormDialog.value = false;
    fetchEmployees();
  } catch (error) {
    ElMessage.error("操作失败");
    console.error("操作失败:", error);
  }
};

// 删除员工
const handleDelete = async (id: number) => {
  try {
    await employeeApi.deleteEmployee(id);
    ElMessage.success("删除成功");
    fetchEmployees();
  } catch (error) {
    ElMessage.error("删除失败");
    console.error("删除失败:", error);
  }
};

onMounted(() => {
  fetchEmployees();
});
</script>

<style scoped>
.employee-management {
  padding: 20px;
}
</style>