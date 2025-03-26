export type Status = "待维修" | "维修中" | "已完成" | "已取消";

export interface RepairOrderQueryDTO {
  orderId?: number;
  vehicleId?: number;
  customerId?: number;
  orderDate?: string; // 对应LocalDateTime
  status?: Status;
  orderCost?: number;
  technicianId?: number;
  vehicleLicensePlate?: string;
  current: number; // 分页参数
  size: number; // 分页参数
}

export interface RepairOrderDTO {
  orderId?: number;
  vehicleId?: number;
  customerId?: number;
  orderDate?: string; // 创建时可不传，由后端生成
  status?: Status;
  vehicleLicensePlate?: string;
  orderCost?: number;
  technicianId?: number;
}

export interface RepairOrderVO {
  orderId: number;
  vehicleId: number;
  customerId: number;
  orderDate: number[];
  status: Status;
  orderCost: number;
  technicianId?: number;
  vehicleLicensePlate: string;
  customerName: string;
  phone: string;
  technicianName?: string;
}
