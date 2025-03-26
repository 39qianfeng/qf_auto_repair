package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qianfeng.dto.CartItemDTO;
import com.qianfeng.entity.CartItem;
import com.qianfeng.entity.Customer;
import com.qianfeng.entity.Vehicle;
import com.qianfeng.mapper.CartMapper;
import com.qianfeng.service.CartService;
import com.qianfeng.vo.VehicleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, CartItem> implements CartService {
    @Override
    public void addToCart(CartItemDTO cartItemDTO) {
        Integer customerId = cartItemDTO.getCustomerId();
        String itemType = cartItemDTO.getItemType();
        Integer itemId = cartItemDTO.getItemId();
        // 查找购物车里是否已有同种商品
        LambdaQueryWrapper<CartItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CartItem::getCustomerId,customerId)
                .eq(CartItem::getItemType,itemType)
                .eq(CartItem::getItemId,itemId);
        CartItem one = this.getOne(lambdaQueryWrapper);
        // 该顾客的购物车里有同种商品时，在原有商品的基础上数量+1即可
        if(one != null && one.getNumber()>0){
//            CartItemDTO c = new CartItemDTO();
//            c.setNumber(cartItemDTO.getNumber()+1);
            cartItemDTO.setNumber(cartItemDTO.getNumber()+1);
            this.updateCartItem(one.getCartId(),cartItemDTO);
//            this.updateCartItem(one.getCartId(),c);
            return;
        }
        // 新增商品到购物车里
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(cartItemDTO,cartItem);
        this.save(cartItem);
    }

    @Override
    public List<CartItem> getCartItemsByCustomerId(Integer customerId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        return this.list(queryWrapper);
    }

    @Override
    public void updateCartItem(Integer cartId,@RequestBody CartItemDTO cartItemDTO) {
        log.info(""+cartItemDTO);
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(cartItemDTO,cartItem);
        log.info(""+cartItem);
        cartItem.setCartId(cartId);
        this.updateById(cartItem);
    }

    @Override
    public void deleteCartItem(Integer cartId) {
        this.removeById(cartId);
    }

    @Override
    public void clearCart(Integer customerId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        this.remove(queryWrapper);
    }

    @Override
    public IPage<CartItem> getCartItemsByCustomerId(Page<CartItem> page, Integer customerId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        return this.page(page, queryWrapper);
    }

}