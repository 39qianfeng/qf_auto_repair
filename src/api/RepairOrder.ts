import { Result,IPage } from '@/types/common';
import axiosInstance from '@/utils/axios'
import type {
  RepairOrderDTO,
  RepairOrderVO,
  RepairOrderQueryDTO,
} from "@/types/repairOrder";

// 分页响应类型
export type RepairOrderPage = IPage<RepairOrderVO>;

export const repairOrderApi = {
  // 分页查询
  pageQuery: (params: RepairOrderQueryDTO) => {
    return axiosInstance.post<Result<RepairOrderPage>>(
      '/api/repair-orders/page',
      params
    )
  },

  // 获取单个订单详情
  getById: (orderId: number) => {
    return axiosInstance.get<Result<RepairOrderVO>>(
      `/api/repair-orders/${orderId}`
    )
  },

  // 创建订单
  create: (data: RepairOrderDTO) => {
    return axiosInstance.post<Result<RepairOrderVO>>(
      '/api/repair-orders',
      data
    )
  },

  // 更新订单
  update: (orderId: number, data: RepairOrderDTO) => {
    return axiosInstance.put<Result<RepairOrderVO>>(
      `/api/repair-orders/${orderId}`,
      data
    )
  },

  // 删除订单
  delete: (orderId: number) => {
    return axiosInstance.delete<Result<void>>(
      `/api/repair-orders/${orderId}`
    )
  },

  // 批量删除（需后端支持）
  batchDelete: (orderIds: number[]) => {
    return axiosInstance.post<Result<void>>(
      '/api/repair-orders/batch-delete',
      orderIds
    )
  }
}