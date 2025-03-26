export interface EmployeeLoginVO {
  employeeId: number | null; // 主键值
  name: string; // 姓名
  token: string; // JWT 令牌
  userName: string; // 用户名
  type: string; // 类型
}

export interface EmployeeDTO {
  employeeId: number;
  name?: string;
  position?: string;
  phone?: string;
  email?: string;
  skills?: string;
}

export interface EmployeeVO {
  employeeId: number;
  name: string;
  position: string;
  phone: string;
  email: string;
  skills: string;
  repairOrderCount: number; // 额外字段：该员工处理的维修工单数量
  username: string;
}

export interface EmployeeLoginDTO {
  username: string;
  password: string;
}