// @/types/cart.ts
export interface CartItemDTO {
  cartId?: number; // 可选，因为添加时可能没有cartId
  customerId: number;
  itemId: number;
  itemType: string; // 区分商品类型
  itemName: string;
  itemPrice: number; // 使用 number 表示金额
  number: number;
}

export interface CartItemVO {
  cartId: number; // 购物车项ID
  customerId: number; // 客户ID
  itemId: number; // 商品ID
  itemType: string; // 商品类型（PART 或 SERVICE）
  itemName: string; // 商品名称
  itemPrice: number; // 商品单价
  number: number; // 商品数量
  totalPrice: number; // 商品总价（单价 × 数量）
}
