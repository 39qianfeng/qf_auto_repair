export interface EmployeeLoginVO {
  employeeId: number | null; // 主键值
  name: string; // 姓名
  token: string; // JWT 令牌
  userName: string; // 用户名
}
