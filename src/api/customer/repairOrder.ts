// src/api/repairOrder.ts
import axiosInstance from "@/utils/axios";
import { Result, IPage } from "@/types/common";
import type {
  RepairOrderDTO,
  RepairOrderQueryDTO,
  RepairOrderVO,
} from "@/types/repairOrder";

const BASE_URL = "/customer/repair-orders";

export const repairOrderApi = {
  // 创建订单
  createOrder: (customerId: number) => {
    return axiosInstance.post<Result<RepairOrderVO>>(`${BASE_URL}/createOrder`, null, {
      params: {
        customerId,
      },
    });
  },

  // 获取所有订单
  getAllRepairOrders: () => {
    return axiosInstance.get<Result<RepairOrderVO[]>>(`${BASE_URL}`);
  },

  // 根据订单ID获取订单详情
  getRepairOrderById: (orderId: number) => {
    return axiosInstance.get<Result<RepairOrderVO>>(`${BASE_URL}/${orderId}`);
  },

//   // 创建订单
//   createRepairOrder: (repairOrderDTO: RepairOrderDTO) => {
//     return axiosInstance.post<Result<RepairOrderVO>>(
//       `${BASE_URL}`,
//       repairOrderDTO
//     );
//   },

  // 更新订单
  updateRepairOrder: (orderId: number, repairOrderDTO: RepairOrderDTO) => {
    return axiosInstance.put<Result<RepairOrderVO>>(
      `${BASE_URL}/${orderId}`,
      repairOrderDTO
    );
  },

  // 删除订单
  deleteRepairOrder: (orderId: number) => {
    return axiosInstance.delete<Result<void>>(`${BASE_URL}/${orderId}`);
  },

  // 分页查询订单
  pageQuery: (repairOrderQueryDTO: RepairOrderQueryDTO) => {
    return axiosInstance.get<Result<IPage<RepairOrderVO>>>(`${BASE_URL}/page`, {
      params: repairOrderQueryDTO,
    });
  },
};
