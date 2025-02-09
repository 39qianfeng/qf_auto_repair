import axiosInstance from "@/utils/axios";


// 获取客户分页数据
export const getCustomersPage = async (params: any) => {
  return axiosInstance.get('/api/customers/page', { params });
};

// 新增客户
export const addCustomer = async (data: any) => {
  return axiosInstance.post('/api/customers', data);
};

// 修改客户信息
export const updateCustomer = async (customerId: number, data: any) => {
  return axiosInstance.put(`/api/customers/update/${customerId}`, data);
};

// 删除客户
export const deleteCustomer = async (customerId: number) => {
  return axiosInstance.delete(`/api/customers/${customerId}`);
};