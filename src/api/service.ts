import axiosInstance from "@/utils/axios";
import { Result, IPage } from "@/types/common";
import type {
  ServiceChargeQueryDTO,
  ServiceChargeVO,
} from "@/types/service";

// 分页响应类型
export type ServiceChargePage = IPage<ServiceChargeVO>;

export const serviceChargeApi = {
  // 分页查询
  pageQuery: (params: ServiceChargeQueryDTO) => {
    return axiosInstance.get<Result<ServiceChargePage>>(
      "/api/service-charges/page",
      { params }
    );
  },

  // 获取单个服务详情
  getById: (serviceId: number) => {
    return axiosInstance.get<Result<ServiceChargeVO>>(
      `/api/service-charges/${serviceId}`
    );
  },

  // 创建服务
  create: (data: ServiceChargeQueryDTO) => {
    return axiosInstance.post<Result<ServiceChargeVO>>(
      "/api/service-charges",
      data
    );
  },

  // 更新服务
  update: (serviceId: number, data: ServiceChargeQueryDTO) => {
    return axiosInstance.put<Result<ServiceChargeVO>>(
      `/api/service-charges/${serviceId}`,
      data
    );
  },

  // 删除服务
  delete: (serviceId: number) => {
    return axiosInstance.delete<Result<void>>(
      `/api/service-charges/${serviceId}`
    );
  },
};
