<template>
  <div class="action-container">
    <label style="margin-left: 10px;margin-right: 10px;">客户姓名：</label>
    <el-input v-model="input" style="width: 240px; margin-right: 15px;" placeholder="请输入客户姓名" />
    <el-button type="primary" :icon="Search" @click="handleSearch">查询</el-button>
    <div class="button-group">
      <el-button type="primary" :icon="Edit" @click="handleInsert">新增客户</el-button>
      <el-button type="primary" :icon="Edit" @click="showEditDialog = true">修改客户信息</el-button>
      <el-button type="primary" :icon="Edit" @click="showDeleteDialog = true;" style="margin-right: 80px;">删除客户</el-button>
    </div>
  </div>

  <div v-if="userInfo && userInfo.length > 0">
    <h2>查询结果</h2>
    <el-table :data="userInfo" style="width: 100%">
      <el-table-column prop="customerId" label="客户ID" width="80" />
      <el-table-column prop="name" label="姓名" width="80" />
      <el-table-column prop="phone" label="电话" width="120" />
      <el-table-column prop="email" label="邮箱" width="200" />
      <el-table-column prop="address" label="地址" width="250" />
      <el-table-column prop="customerType" label="客户类型" width="120" />
      <el-table-column prop="vehicleCount" label="车辆数量" width="120" />
      <el-table-column prop="repairOrderCount" label="维修订单数量" width="150" />
    </el-table>
  </div>

  <!-- 分页 -->
  <div style="margin-top: 16px;">
    <el-pagination style="justify-content: center;" layout="prev, pager, next" :total="total" :current="current" :page-size="size"
    @current-change="handlePageChange"
    />
  </div>

  <!-- 新增客户对话框 -->
  <el-dialog v-model="dialogVisible" title="新增客户">
    <el-form :model="form" :rules="rules" ref="formRef">
      <el-form-item label="客户姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入客户姓名"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入电话"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>
      <el-form-item label="客户类型" prop="customerType">
        <el-select v-model="form.customerType" placeholder="请选择客户类型">
          <el-option label="VIP" value="VIP"></el-option>
          <el-option label="普通客户" value="普通客户"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 修改客户信息对话框 -->
  <el-dialog v-model="showEditDialog" title="修改客户信息" width="30%">
    <el-form :model="editForm" label-width="100px">
      <el-form-item label="客户ID">
        <el-input v-model="editForm.customerId" placeholder="请输入客户ID" />
      </el-form-item>
      <el-form-item label="客户姓名">
        <el-input v-model="editForm.name" placeholder="请输入客户姓名" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="editForm.phone" placeholder="请输入电话" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="editForm.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="editForm.address" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item label="客户类型">
        <el-select v-model="editForm.customerType" placeholder="请选择客户类型">
          <el-option label="VIP" value="VIP"></el-option>
          <el-option label="普通客户" value="普通客户"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="handleEdit">确认修改</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 删除客户对话框 -->
  <el-dialog v-model="showDeleteDialog" title="删除客户" width="30%">
    <p>请输入要删除的客户ID：</p>
    <el-input v-model="deleteCustomerId" placeholder="客户ID" />
    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="showDeleteDialog = false">取消</el-button>
        <el-button type="primary" @click="handleDelete">确认删除</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { Edit, Search } from '@element-plus/icons-vue';
import axiosInstance from '@/utils/axios';

interface Customer {
  customerId: number | null;
  name: string;
  phone: string;
  email: string;
  address: string;
  customerType: string;
  vehicleCount: number | null;
  repairOrderCount: number | null;
}

interface Result<T> {
  code: number;
  msg: string | null;
  data: T;
}

interface IPage<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}

// 响应式数据
let input = ref('');
let userInfo = ref<Customer[]>([]);
let current = ref(1);
let total = ref(10);
let size = ref(1);

// 新增客户对话框
let dialogVisible = ref(false);
let form = ref({
  name: null,
  phone: null,
  email: null,
  address: null,
  customerType: null
});
let rules = ref({
  name: [{ required: true, message: '请输入客户姓名', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
  customerType: [{ required: true, message: '请选择客户类型', trigger: 'change' }]
});
let formRef = ref<any>(null);

// 修改客户对话框
let showEditDialog = ref(false);
let editForm = ref<any>({
  customerId: null,
  name: null,
  phone: null,
  email: null,
  address: null,
  customerType: null
});


// 删除客户对话框
let showDeleteDialog = ref(false);
let deleteCustomerId = ref<number>();

// 定义方法
// 查询客户信息
const handleSearch = async () => {
  try {
    const query = input.value.trim();
    const siz = size.value; 
    const cur = current.value;
    let url = '/api/customers/page'; // 查询客户的总接口
    let response = await axiosInstance.get<Result<IPage<Customer>>>(url, {
        params: {
          current: cur,
          size: siz,
          name: query
        }
      });
      userInfo.value = response.data.data.records || [];
      total.value = response.data.data.total;

    if (response.data.code !== 1) {
      console.log("返回失败，出错码为:", response.data.code);
      alert(response.data.msg || '查询失败');
      return;
    }

    console.log(response.data.data); // 打印返回的数据
  } catch (error) {
    console.error('请求失败:', error);
    alert('查询失败，请稍后再试');
  }
};

// 分页修改
const handlePageChange = (newPage: number) => {
  current.value = newPage; // 更新当前页码
  handleSearch(); // 重新查询数据
};

// 表单可视化
const handleInsert = () => {
  dialogVisible.value = true;
  
};

// 新增客户信息
const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        const url = '/api/customers';
        const response = await axiosInstance.post<Result<Customer>>(url, form.value);
        if (response.data.code === 1) {
          alert('新增成功');
          dialogVisible.value = false;
          handleSearch(); // 刷新数据
        } else {
          alert(response.data.msg || '新增失败');
        }
      } catch (error) {
        console.error('请求失败:', error);
        alert('新增失败，请稍后再试');
      }
    } else {
      alert('表单验证失败');
    }
  });
};

// 修改客户信息
const handleEdit = async() => {
  // 将空字符串字段转换为 null
  const processedEditForm = {
    customerId: editForm.value.customerId || null,
    name: editForm.value.name || null,
    phone: editForm.value.phone || null,
    email: editForm.value.email || null,
    address: editForm.value.address || null,
    customerType: editForm.value.customerType || null
  };
  const customerId = processedEditForm.customerId;
  if (!customerId) {
    alert('请输入客户ID');
    return;
  }
  // 调用 API 更新客户信息
  await axiosInstance.put<Result<Customer>>(`/api/customers/update/${customerId}`, processedEditForm)
    .then(response => {
      if(response.data.code !== 1){
        console.log("修改失败，"+response.data.msg);
        alert("修改失败，"+response.data.msg)
        return;
      }
      console.log('修改成功:', response.data);
      alert('修改成功');
      showEditDialog.value = false; // 关闭对话框
    })
    .catch(error => {
      console.error('修改失败:', error);
      alert('修改失败，请稍后再试');
    });
  // 更新数据
  handleSearch();
};

// 删除客户
const handleDelete = () => {
  // 确认删除操作
  if (window.confirm('确定要删除客户吗？')) {
    // 要删除的客户ID
    const customerId = deleteCustomerId;

    // 调用 API 删除客户
    axiosInstance.delete(`/api/customers/${customerId}`)
      .then(response => {
        console.log('删除成功:', response.data);
        // 更新客户列表
        handleSearch();
      })
      .catch(error => {
        console.error('删除失败:', error);
      });
      showDeleteDialog.value=false;
  }
};

// 页面加载时自动调用 handleSearch 方法
onMounted(() => {
  handleSearch();
});
</script>

<style scoped>
.el-dialog {
  display: none;
}

.action-container {
  display: flex;
  align-items: center;
}

.button-group {
  display: flex;
  gap: 16px; /* 设置按钮之间的间距 */
  margin-left: auto; /* 将按钮组推到右侧 */
}
</style>
