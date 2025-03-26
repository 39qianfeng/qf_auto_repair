export interface PartDTO {
  partId?: number; // 可选字段，用于更新时指定 ID
  partName: string;
  partNumber: string;
  description: string;
  stockQuantity: number;
  supplierId: number;
  purchasePrice: number; // 使用 number 类型表示价格
  salePrice: number; // 使用 number 类型表示价格
}

export interface PartVO {
  partId: number;
  partName: string;
  partNumber: string;
  description: string;
  stockQuantity: number;
  supplierId: number;
  purchasePrice: number; // 使用 number 类型表示价格
  salePrice: number; // 使用 number 类型表示价格
  supplierName: string; // 额外字段：供应商名称
}

export interface PartQueryDTO {
  partId?: number; // 可选字段，用于更新时指定 ID
  partName: string;
  partNumber: string;
  description: string;
  stockQuantity: number;
  supplierId: number;
  purchasePrice: number; // 使用 number 类型表示价格
  salePrice: number; // 使用 number 类型表示价格
  current: number;
  size: number;
}