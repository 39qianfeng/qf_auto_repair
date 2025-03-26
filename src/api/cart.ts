// src/api/part.ts
import axiosInstance from "@/utils/axios";
import { Result, IPage } from "@/types/common";
import type { PartDTO, PartVO, PartQueryDTO } from "@/types/part";

// 定义公共路径
const API_BASE_URL = "/api/parts";

// 分页响应类型
export type PartPage = IPage<PartVO>;

export const partApi = {
  // 分页查询
  pageQuery: (params: PartQueryDTO) => {
    return axiosInstance.get<Result<PartPage>>(`${API_BASE_URL}/page`, {
      params,
    });
  },

  // 获取单个零件详情
  getById: (partId: number) => {
    return axiosInstance.get<Result<PartVO>>(`${API_BASE_URL}/${partId}`);
  },

  // 创建零件
  create: (data: PartDTO) => {
    return axiosInstance.post<Result<PartVO>>(API_BASE_URL, data);
  },

  // 更新零件
  update: (partId: number, data: PartDTO) => {
    return axiosInstance.put<Result<PartVO>>(`${API_BASE_URL}/${partId}`, data);
  },

  // 删除零件
  delete: (partId: number) => {
    return axiosInstance.delete<Result<void>>(`${API_BASE_URL}/${partId}`);
  },
};
