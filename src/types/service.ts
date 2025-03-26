export interface ServiceChargeDTO {
  serviceId?: number; // 可选字段，用于更新时指定 ID
  serviceName: string;
  description: string;
  price: number; // 使用 number 类型表示价格
  duration: number; // 使用 number 类型表示时长
  isAppointmentRequired: number; // 使用 number 类型表示布尔值（0 或 1）
}

export interface ServiceChargeVO {
  serviceId: number;
  serviceName: string;
  description: string;
  price: number; // 使用 number 类型表示价格
  duration: number; // 使用 number 类型表示时长
  isAppointmentRequired: number; // 使用 number 类型表示布尔值（0 或 1）
  createTime: string; // 使用 ISO 8601 格式的字符串表示时间
  updateTime: string; // 使用 ISO 8601 格式的字符串表示时间
}

export interface ServiceChargeQueryDTO {
  serviceId?: number; // 可选字段，用于更新时指定 ID
  serviceName?: string;
  description?: string;
  price?: number; // 前端处理时，通常将 BigDecimal 转为 number 或 string
  duration?: number; // 前端处理时，通常将 BigDecimal 转为 number 或 string
  isAppointmentRequired?: number; // 0 或 1
  current?: number; // 当前页码
  size?: number; // 每页显示条数
}