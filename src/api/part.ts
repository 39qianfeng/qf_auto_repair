import axiosInstance from "@/utils/axios";
import { Result, IPage } from "@/types/common";
import type { PartDTO, PartVO } from "@/types/part";

// 分页响应类型
export type PartPage = IPage<PartVO>;

export const partApi = {
  // 分页查询
  pageQuery: (params: { current: number; size: number }) => {
    return axiosInstance.get<Result<PartPage>>("/api/parts/page", { params });
  },

  // 获取单个零件详情
  getById: (partId: number) => {
    return axiosInstance.get<Result<PartVO>>(`/api/parts/${partId}`);
  },

  // 创建零件
  create: (data: PartDTO) => {
    return axiosInstance.post<Result<PartVO>>("/api/parts", data);
  },

  // 更新零件
  update: (partId: number, data: PartDTO) => {
    return axiosInstance.put<Result<PartVO>>(`/api/parts/${partId}`, data);
  },

  // 删除零件
  delete: (partId: number) => {
    return axiosInstance.delete<Result<void>>(`/api/parts/${partId}`);
  },
};
