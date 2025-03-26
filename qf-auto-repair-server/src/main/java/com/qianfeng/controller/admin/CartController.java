package com.qianfeng.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.CartItemDTO;
import com.qianfeng.entity.CartItem;
import com.qianfeng.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/26/18:05
 * @Description:
 */
@RestController
@RequestMapping("/api/Cart")
@Api(tags = "购物车相关接口")
public class CartController {
    @Autowired
    private CartService cartService;

    // 添加商品到购物车
    @PostMapping("/add")
    public String addToCart(@RequestBody CartItemDTO cartItemDTO) {
        cartService.addToCart(cartItemDTO);
        return "商品已添加到购物车";
    }

    // 获取购物车商品
    @GetMapping("/{customerId}")
    public List<CartItem> getCartItems(@PathVariable Integer customerId) {
        return cartService.getCartItemsByCustomerId(customerId);
    }

    // 分页查询购物车商品（可选）
    @GetMapping("/page/{customerId}")
    public IPage<CartItem> getCartItemsByCustomerId(
            @PathVariable Integer customerId,
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size) {
        Page<CartItem> page = new Page<>(current, size);
        return cartService.getCartItemsByCustomerId(page, customerId);
    }

    // 更新购物车商品数量
    @PutMapping("/{cartId}")
    public String updateCartItem(@PathVariable Integer cartId, @RequestParam CartItemDTO cartItemDTO) {
        cartService.updateCartItem(cartId, cartItemDTO);
        return "购物车商品数量已更新";
    }

    // 删除购物车商品
    @DeleteMapping("/{cartId}")
    public String deleteCartItem(@PathVariable Integer cartId) {
        cartService.deleteCartItem(cartId);
        return "商品已从购物车移除";
    }

    // 清空购物车
    @DeleteMapping("/clear/{customerId}")
    public String clearCart(@PathVariable Integer customerId) {
        cartService.clearCart(customerId);
        return "购物车已清空";
    }


}
