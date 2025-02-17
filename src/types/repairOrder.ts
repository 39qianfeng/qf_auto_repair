export interface RepairOrderQueryDTO {
  orderId?: number;
  vehicleId?: number;
  customerId?: number;
  orderDate?: string; // 对应LocalDateTime
  status?: string;
  estimatedCost?: number;
  actualCost?: number;
  technicianId?: number;
  current: number; // 分页参数
  size: number; // 分页参数
}

export interface RepairOrderDTO {
  orderId?: number;
  vehicleId: number;
  customerId: number;
  orderDate?: string; // 创建时可不传，由后端生成
  status: string;
  estimatedCost: number;
  actualCost?: number;
  technicianId?: number;
}

export interface RepairOrderVO {
  orderId: number;
  vehicleId: number;
  customerId: number;
  orderDate: string;
  status: string;
  estimatedCost: number;
  actualCost?: number;
  technicianId?: number;
  vehicleLicensePlate: string;
  customerName: string;
  technicianName?: string;
}
