// src/api/cart.ts
import axiosInstance from "@/utils/axios";
import { Result, IPage } from "@/types/common";
import type { CartItemDTO, CartItemVO } from "@/types/cart";

// 分页响应类型
export type CartItemPage = IPage<CartItemVO>;

export const cartApi = {
  // 添加到购物车
  addToCart: (cartItemDTO: CartItemDTO) => {
    return axiosInstance.post<Result<void>>("/customer/Cart/add", cartItemDTO);
  },

  // 获取购物车商品
  getCartItems: (customerId: number) => {
    return axiosInstance.get<Result<CartItemVO[]>>(
      `/customer/Cart/${customerId}`
    );
  },

  // 分页获取购物车商品
  getCartItemsByCustomerId: (customerId: number, current = 1, size = 10) => {
    return axiosInstance.get<Result<CartItemPage>>(
      `/customer/Cart/page/${customerId}`,
      {
        params: {
          current,
          size,
        },
      }
    );
  },

  // 更新购物车商品
  updateCartItem: (cartId: number, number: number) => {
    return axiosInstance.put<Result<void>>(`/customer/Cart/${cartId}`, {
      number
    });
  },

  // 删除购物车商品
  deleteCartItem: (cartId: number) => {
    return axiosInstance.delete<Result<void>>(`/customer/Cart/${cartId}`);
  },

  // 清空购物车
  clearCart: (customerId: number) => {
    return axiosInstance.delete<Result<void>>(
      `/customer/Cart/clear/${customerId}`
    );
  },
};
