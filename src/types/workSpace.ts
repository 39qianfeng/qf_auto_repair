export interface GoodsOverViewVO {
  partGoodsCount: number; // 零件商品数量
  serviceGoodsCount: number; // 服务商品数量
}

export interface RepairOrderOverViewVO {
  toBeRepairCount: number; // 待维修订单数量
  repairingCount: number; // 维修中订单数量
  completedCount: number; // 已完成订单数量
  cancelledCount: number; // 已取消订单数量
  totalOrderCount: number; // 总订单数量
}

export interface CustomerOverViewVO {
  totalCustomerCount: number; // 客户总数
  newCustomerCount: number; // 新增客户数量
}

export interface BusinessDataVO {
  turnover: number; // 营业额
  validOrderCount: number; // 有效订单数
  orderCompletionRate: number; // 订单完成率
  unitPrice: number; // 平均客单价
  newUsers: number; // 新增用户数
}
