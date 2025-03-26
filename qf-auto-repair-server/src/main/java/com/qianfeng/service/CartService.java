package com.qianfeng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.CartItemDTO;
import com.qianfeng.entity.CartItem;

import java.util.List;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/26/18:09
 * @Description:
 */


public interface CartService {
    // 添加商品到购物车
    void addToCart(CartItemDTO cartItemDTO);

    // 根据客户 ID 查询购物车商品
    List<CartItem> getCartItemsByCustomerId(Integer customerId);

    // 更新购物车商品数量
    void updateCartItem(Integer cartId, CartItemDTO cartItemDTO);

    // 删除购物车商品
    void deleteCartItem(Integer cartId);

    // 清空客户购物车
    void clearCart(Integer customerId);

    // 分页查询购物车商品（可选）
    IPage<CartItem> getCartItemsByCustomerId(Page<CartItem> page, Integer customerId);
}