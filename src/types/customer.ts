export interface Customer {
  customerId: number | null;
  name: string;
  phone: string;
  email: string;
  address: string;
  customerType: string;
  vehicleCount: number | null;
  repairOrderCount: number | null;
}

export interface CustomerLoginDTO {
  username: string;
  password: string;
}

export interface CustomerLoginVO {
  customerId: number | null; // 主键值
  name: string; // 姓名
  token: string; // JWT 令牌
  userName: string; // 用户名
  type: string; // 类型
}  

// src/types/customer.ts
export interface CustomerDTO {
  customerId?: number; // 可选字段，用于更新时指定ID
  name: string;
  phone: string;
  email: string;
  address: string;
  customerType: string;
}

export interface CustomerQueryDTO {
  current?: number; // 当前页码
  size?: number;    // 每页大小
  customerId?: number; // 客户ID查询条件
  name?: string;     // 客户名称查询条件
  phone?: string;    // 客户电话查询条件
  email?: string;    // 客户邮箱查询条件
  customerType?: string; // 客户类型查询条件
}

export interface CustomerVO {
  customerId: number;
  name: string;
  phone: string;
  email: string;
  address: string;
  customerType: string;
  vehicleCount?: number; // 额外字段：该客户拥有的车辆数量
  repairOrderCount?: number; // 额外字段：该客户的维修工单数量
}

export interface Result<T> {
  code: number;
  msg: string | null;
  data: T;
}

export interface IPage<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}
