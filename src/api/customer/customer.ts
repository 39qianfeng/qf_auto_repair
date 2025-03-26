// src/api/customer.ts
import axiosInstance from "@/utils/axios";
import { IPage,Result } from "@/types/common";
import type {
  CustomerDTO,
  CustomerVO,
  CustomerQueryDTO,
  CustomerLoginDTO,
  CustomerLoginVO,
} from "@/types/customer";

export const customerApi = {
  // 分页查询顾客
  pageQuery: (params: CustomerQueryDTO) => {
    return axiosInstance.get<Result<IPage<CustomerVO>>>(
      "/customer/customers/page",
      { params }
    );
  },

  // 获取单个顾客详情
  getById: (customerId: number) => {
    return axiosInstance.get<Result<CustomerVO>>(
      `/customer/customers/${customerId}`
    );
  },

  // 创建顾客
  create: (data: CustomerDTO) => {
    return axiosInstance.post<Result<CustomerVO>>("/customer/customers", data);
  },

  // 更新顾客信息
  update: (customerId: number, data: CustomerDTO) => {
    return axiosInstance.put<Result<CustomerVO>>(
      `/customer/customers/update/${customerId}`,
      data
    );
  },

  // 删除顾客
  delete: (customerId: number) => {
    return axiosInstance.delete<Result<void>>(
      `/customer/customers/${customerId}`
    );
  },

  // 顾客登录
  login: (data: CustomerLoginDTO) => {
    return axiosInstance.post<Result<CustomerLoginVO>>(
      "/customer/customers/login",
      data
    );
  },

  // 获取所有顾客信息
  getAllCustomers: () => {
    return axiosInstance.get<Result<CustomerVO[]>>("/customer/customers");
  },
};
