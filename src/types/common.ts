// 定义返回值接口
export interface Result<T> {
  code: number;
  msg: string | null;
  data: T;
}
// 定义分页查询结果接口
export interface IPage<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}
// 定义顾客接口
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
// 定义车辆接口
export interface Vehicle {
  vehicleId: number;
  customerId: number;
  licensePlate: string;
  brand: string;
  model: string;
  vin: string;
  engineNo: string;
  mileage: number;
  customerName: string;
  customerType: string;
  repairOrderCount: number;
}
// 定义预约接口
export interface Appointment {
  appointmentId?: number; // 预约ID（可选，用于更新时）
  customerId: number; // 客户ID
  vehicleId: number; // 车辆ID
  appointmentDate: string; // 预约日期和时间，格式为ISO 8601字符串
  status: string; // 预约状态
  technicianId: number; // 技师ID
  vehicleLicensePlate: string; // 车辆车牌号（额外字段）
  customerName: string; // 客户名称（额外字段）
  technicianName: string; // 技师名称（额外字段）
}
// 定义员工信息接口
export interface Employee {
  employeeId: number; // 员工ID
  name: string; // 员工姓名
  position: string; // 职位
  phone: string; // 联系电话
  email: string; // 电子邮箱
  skills: string; // 技能描述
  repairOrderCount: number; // 该员工处理的维修工单数量（额外字段）
}
// 定义财务交易接口
export interface Finance {
  transactionId: number; // 交易ID
  orderId: number; // 关联的订单ID
  amount: number; // 交易金额（使用number类型表示）
  transactionDate: string; // 交易日期和时间，格式为ISO 8601字符串
  type: string; // 交易类型（例如：收入、支出等）
  orderStatus: string; // 关联的维修工单状态（额外字段）
}
// 定义库存信息接口
export interface Inventory {
  inventoryId: number; // 库存ID
  partId: number; // 配件ID
  quantity: number; // 库存数量
  lastUpdated: string; // 最后更新时间，格式为ISO 8601字符串
  partName: string; // 额外字段：配件名称
}
// 定义配件信息接口
export interface Part {
  partId: number; // 配件ID
  partName: string; // 配件名称
  partNumber: string; // 配件编号
  description: string; // 配件描述
  stockQuantity: number; // 库存数量
  supplierId: number; // 供应商ID
  purchasePrice: number; // 采购价格
  salePrice: number; // 销售价格
  supplierName: string; // 额外字段：供应商名称
}
// 定义维修工单接口
export interface RepairOrder {
  orderId: number; // 工单ID
  vehicleId: number; // 车辆ID
  customerId: number; // 客户ID
  orderDate: string; // 工单日期，格式为ISO 8601字符串
  status: string; // 工单状态
  estimatedCost: number; // 预估费用
  actualCost: number; // 实际费用
  technicianId: number; // 技师ID
  vehicleLicensePlate: string; // 车辆车牌号（额外字段）
  customerName: string; // 客户名称（额外字段）
  technicianName: string; // 技师名称（额外字段）
}
// 定义供应商接口
export interface Supplier {
  supplierId: number; // 供应商ID
  name: string; // 供应商名称
  contactPerson: string; // 联系人
  phone: string; // 联系电话
  email: string; // 电子邮箱
  address: string; // 地址
  partCount: number; // 该供应商提供的配件数量（额外字段）
}
