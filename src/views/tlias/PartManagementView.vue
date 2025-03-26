<template>
  <div class="part-management">
    <!-- 添加配件按钮 -->
    <el-button type="primary" @click="showCreateDialog">添加配件</el-button>

    <!-- 配件列表表格 -->
    <el-table :data="partList" style="width: 100%" stripe>
      <el-table-column prop="partName" label="配件名称" width="180" />
      <el-table-column prop="partNumber" label="配件编号" width="180" />
      <el-table-column prop="description" label="描述" width="200" />
      <el-table-column prop="salePrice" label="销售价格" width="120" align="right">
        <template #default="{ row }">¥ {{ row.salePrice.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="showEditDialog(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.partId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      v-model:currentPage="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="fetchParts"
    />

    <!-- 添加/编辑配件对话框 -->
    <el-dialog
      v-model="showFormDialog"
      :title="formTitle"
      width="500px"
      destroy-on-close
    >
      <el-form
        ref="partForm"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="配件名称" prop="partName">
          <el-input v-model="formData.partName" placeholder="请输入配件名称" />
        </el-form-item>
        <el-form-item label="配件编号" prop="partNumber">
          <el-input v-model="formData.partNumber" placeholder="请输入配件编号" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="库存数量" prop="stockQuantity">
          <el-input-number v-model="formData.stockQuantity" :min="0" placeholder="请输入库存数量" />
        </el-form-item>
        <el-form-item label="供应商 ID" prop="supplierId">
          <el-input-number v-model="formData.supplierId" :min="0" placeholder="请输入供应商 ID" />
        </el-form-item>
        <el-form-item label="采购价格" prop="purchasePrice">
          <el-input-number v-model="formData.purchasePrice" :min="0" placeholder="请输入采购价格" />
        </el-form-item>
        <el-form-item label="销售价格" prop="salePrice">
          <el-input-number v-model="formData.salePrice" :min="0" placeholder="请输入销售价格" />
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
import { partApi} from "@/api/part";
import { PartDTO, PartVO } from "@/types/part";

const partList = ref<PartVO[]>([]);
const showFormDialog = ref(false);
const formTitle = ref("添加配件");
const formData = reactive<PartDTO>({
  partId: undefined,
  partName: "",
  partNumber: "",
  description: "",
  stockQuantity: 0,
  supplierId: 0,
  purchasePrice: 0.0,
  salePrice: 0.0
});
const formRules = {
  partName: [{ required: true, message: "请输入配件名称", trigger: "blur" }],
  partNumber: [{ required: true, message: "请输入配件编号", trigger: "blur" }],
  description: [{ required: true, message: "请输入描述", trigger: "blur" }],
  stockQuantity: [{ required: true, message: "请输入库存数量", trigger: "blur" }],
  supplierId: [{ required: true, message: "请输入供应商 ID", trigger: "blur" }],
  purchasePrice: [{ required: true, message: "请输入采购价格", trigger: "blur" }],
  salePrice: [{ required: true, message: "请输入销售价格", trigger: "blur" }]
};

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 加载配件列表
const fetchParts = async () => {
  try {
    const res = await partApi.pageQuery({ current: currentPage.value, size: pageSize.value });
    const data = res.data.data;
    partList.value = data.records;
    total.value = data.total;
  } catch (error) {
    console.error("加载配件失败:", error);
    ElMessage.error("加载配件失败");
  }
};

// 显示添加配件对话框
const showCreateDialog = () => {
  formTitle.value = "添加配件";
  Object.assign(formData, {
    partId: undefined,
    partName: "",
    partNumber: "",
    description: "",
    stockQuantity: 0,
    supplierId: 0,
    purchasePrice: 0.0,
    salePrice: 0.0
  });
  showFormDialog.value = true;
};

// 显示编辑配件对话框
const showEditDialog = (part: PartVO) => {
  formTitle.value = "编辑配件";
  Object.assign(formData, part);
  showFormDialog.value = true;
};

// 提交表单
const submitForm = async () => {
  try {
    const api = formData.partId
      ? partApi.update(formData.partId!, formData)
      : partApi.create(formData);

    await api;
    ElMessage.success("操作成功");
    showFormDialog.value = false;
    fetchParts();
  } catch (error) {
    ElMessage.error("操作失败");
    console.error("操作失败:", error);
  }
};

// 删除配件
const handleDelete = async (id: number) => {
  try {
    await partApi.delete(id);
    ElMessage.success("删除成功");
    fetchParts();
  } catch (error) {
    ElMessage.error("删除失败");
    console.error("删除失败:", error);
  }
};

onMounted(() => {
  fetchParts();
});
</script>

<style scoped>
.part-management {
  padding: 20px;
}
</style>