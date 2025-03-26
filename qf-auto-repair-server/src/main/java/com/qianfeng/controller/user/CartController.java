package com.qianfeng.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.CartItemDTO;
import com.qianfeng.entity.CartItem;
import com.qianfeng.result.Result;
import com.qianfeng.service.CartService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/26/18:05
 * @Description:
 */
@RestController
@RequestMapping("/customer/Cart")
@Api(tags = "顾客购物车相关接口")
@Component("customerCartController")
@Slf4j
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
    public Result<List<CartItem>> getCartItems(@PathVariable Integer customerId) {
        return Result.success(cartService.getCartItemsByCustomerId(customerId));
    }

    // 分页查询购物车商品（可选）
    @GetMapping("/page/{customerId}")
    public Result<IPage<CartItem>> getCartItemsByCustomerId(
            @PathVariable Integer customerId,
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size) {
        Page<CartItem> page = new Page<>(current, size);
        return Result.success(cartService.getCartItemsByCustomerId(page, customerId));
    }

    // 更新购物车商品数量
    @PutMapping("/{cartId}")
    public Result<Void> updateCartItem(@PathVariable Integer cartId,@RequestBody CartItemDTO cartItemDTO) {
        cartService.updateCartItem(cartId, cartItemDTO);
        return Result.success();
    }

    // 删除购物车商品
    @DeleteMapping("/{cartId}")
    public Result<Void> deleteCartItem(@PathVariable Integer cartId) {
        cartService.deleteCartItem(cartId);
        return Result.success();
    }

    // 清空购物车
    @DeleteMapping("/clear/{customerId}")
    public Result<Void> clearCart(@PathVariable Integer customerId) {
        cartService.clearCart(customerId);
        return Result.success();
    }


}
