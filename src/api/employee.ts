// src/api/employee.ts
import axiosInstance from "@/utils/axios";
import { Result } from "@/types/common";
import type {
  EmployeeDTO,
  EmployeeLoginDTO,
  EmployeeLoginVO,
  EmployeeVO,
} from "@/types/employee";

const BASE_URL = "/api/employees";

export const employeeApi = {
  // 员工登录
  login: (employeeLoginDTO: EmployeeLoginDTO) => {
    return axiosInstance.post<Result<EmployeeLoginVO>>(
      `${BASE_URL}/login`,
      employeeLoginDTO
    );
  },

  // 获取所有员工
  getAllEmployees: () => {
    return axiosInstance.get<Result<EmployeeVO[]>>(`${BASE_URL}`);
  },

  // 根据员工ID获取员工详情
  getEmployeeById: (employeeId: number) => {
    return axiosInstance.get<Result<EmployeeVO>>(`${BASE_URL}/${employeeId}`);
  },

  // 创建员工
  createEmployee: (employeeDTO: EmployeeDTO) => {
    return axiosInstance.post<Result<EmployeeVO>>(`${BASE_URL}`, employeeDTO);
  },

  // 更新员工
  updateEmployee: (employeeId: number, employeeDTO: EmployeeDTO) => {
    return axiosInstance.put<Result<EmployeeVO>>(
      `${BASE_URL}/${employeeId}`,
      employeeDTO
    );
  },

  // 删除员工
  deleteEmployee: (employeeId: number) => {
    return axiosInstance.delete<Result<void>>(`${BASE_URL}/${employeeId}`);
  },
};
