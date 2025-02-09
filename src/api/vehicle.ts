import axiosInstance from "@/utils/axios";
import type {
  VehicleDTO,
  VehicleQueryDTO,
  VehicleVO,
} from "@/types/vehicle";
import type {
  Result,
  IPage,
} from "@/types/common"


// 获取所有车辆
export const getAllVehicles = async () => {
  return axiosInstance.get<Result<VehicleVO[]>>("/api/vehicles");
};

// 根据 ID 获取车辆
export const getVehicleById = async (id: number) => {
  return axiosInstance.get<Result<VehicleVO>>(`/api/vehicles/${id}`);
};

// 新增车辆
export const createVehicle = async (vehicleDTO: VehicleDTO) => {
  return axiosInstance.post<Result<VehicleVO>>("/api/vehicles", vehicleDTO);
};

// 更新车辆
export const updateVehicle = async (id: number, vehicleDTO: VehicleDTO) => {
  return axiosInstance.put<Result<VehicleVO>>(
    `/api/vehicles/${id}`,
    vehicleDTO
  );
};

// 删除车辆
export const deleteVehicle = async (id: number) => {
  return axiosInstance.delete<Result<void>>(`/api/vehicles/${id}`);
};

// 批量删除车俩
export const batchDeleteVehicles = async (ids: number[]) => {
  // 创建一个 URLSearchParams 实例
  const params = new URLSearchParams();
  // 将 ids 数组中的每个值添加到查询参数中
  ids.forEach((id) => params.append("ids", id.toString()));

  // 发送 DELETE 请求，并将查询参数附加到 URL 中
  return axiosInstance.delete<Result<void>>(
    `/api/vehicles/deleteByIds?${params.toString()}`
  );
}

// 分页查询车辆
export const pageQuery = async (vehicleQueryDTO: VehicleQueryDTO) => {
  return axiosInstance.get<Result<IPage<VehicleVO>>>("/api/vehicles/page", {
    params: vehicleQueryDTO,
  });
};
