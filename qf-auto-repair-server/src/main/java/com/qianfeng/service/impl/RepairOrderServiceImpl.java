package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.dto.RepairOrderQueryDTO;
import com.qianfeng.entity.*;
import com.qianfeng.exception.OrderBusinessException;
import com.qianfeng.mapper.*;
import com.qianfeng.service.CartService;
import com.qianfeng.service.RepairOrderService;
import com.qianfeng.utils.BaseContext;
import com.qianfeng.vo.RepairOrderVO;
import com.qianfeng.vo.VehicleVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.python.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.qianfeng.constant.MessageConstant.CART_IS_NULL;
import static com.qianfeng.constant.OrderStatusConstant.TO_BE_REPAIR;

@Service
@Slf4j
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<RepairOrderVO> getAllRepairOrders() {
        List<RepairOrder> repairOrders = repairOrderMapper.selectList(null);
        return repairOrders.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public RepairOrderVO getRepairOrderById(Integer id) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        return convertToVO(repairOrder);
    }

    @Override
    @Transactional
    public RepairOrderVO createRepairOrder(RepairOrderDTO repairOrderDTO) {
        Integer customerId = repairOrderDTO.getCustomerId();
        // 从购物车中获取商品信息
        List<CartItem> cartItems = cartService.getCartItemsByCustomerId(customerId);
        if(cartItems == null || cartItems.size() <= 0){
            System.out.println("购物车内容为空，生成订单失败");
            log.info("购物车内容为空，生成订单失败");
            throw new OrderBusinessException(CART_IS_NULL);
        }

        // 计算总价
        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getItemPrice().multiply(BigDecimal.valueOf(item.getNumber())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        // 生成订单
        RepairOrder order = new RepairOrder();
        order.setCustomerId(customerId);
        order.setStatus(TO_BE_REPAIR);
        order.setOrderCost(totalAmount);
        repairOrderMapper.insert(order);

        // 生成订单详情
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setItemName(cartItem.getItemName());
            orderDetail.setItemPrice(cartItem.getItemPrice());
            orderDetail.setQuantity(cartItem.getNumber());
            orderDetail.setTotalPrice(cartItem.getItemPrice().multiply(BigDecimal.valueOf(cartItem.getNumber())));
            if ("PART".equals(cartItem.getItemType())) {
                orderDetail.setPartId(cartItem.getItemId());
            } else if ("SERVICE".equals(cartItem.getItemType())) {
                orderDetail.setServiceId(cartItem.getItemId());
            }
            orderDetailMapper.insert(orderDetail);
        }

        // 清空购物车
        cartService.clearCart(customerId);
        RepairOrderVO vo = convertToVO(order);
        return vo;
    }

    @Override
    public RepairOrderVO updateRepairOrder(Integer id, RepairOrderDTO repairOrderDTO) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        BeanUtils.copyProperties(repairOrderDTO, repairOrder);
        repairOrder.setOrderId(id);
        repairOrderMapper.updateById(repairOrder);
        return convertToVO(repairOrder);
    }

    @Override
    public void deleteRepairOrder(Integer id) {
        repairOrderMapper.deleteById(id);
    }

    @Override
    public IPage<RepairOrderVO> pageQuery(RepairOrderQueryDTO repairOrderQueryDTO) {
        // 使用mp创建进行分页查询
        // 创建分页对象
        int current = repairOrderQueryDTO.getCurrent();
        int size = repairOrderQueryDTO.getSize();
        IPage<RepairOrder> page = new Page<>(current,size);
        // 创建查询条件
//        LambdaQueryWrapper<RepairOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer orderId = repairOrderQueryDTO.getOrderId();
        LocalDateTime startDate = repairOrderQueryDTO.getStartDate();
        LocalDateTime endDate = repairOrderQueryDTO.getEndDate();
        String searchKey = repairOrderQueryDTO.getSearchKey();
//        if(orderId != null){
//            lambdaQueryWrapper.eq(RepairOrder::getOrderId,orderId);
//        }
//        if(startDate != null){
//            lambdaQueryWrapper.ge(RepairOrder::getOrderDate,startDate);
//        }
//        if(endDate != null){
//            lambdaQueryWrapper.le(RepairOrder::getOrderDate,endDate);
//        }
        // 查询数据库
//        IPage<RepairOrder> repairOrderIPage = repairOrderMapper.selectPage(page, lambdaQueryWrapper);
        IPage<RepairOrder> repairOrderIPage = repairOrderMapper.selectPageBySearchKey(page,orderId,startDate,endDate,searchKey);
        // 根据查询到的数据根据车俩ID去查询车俩所属客户和车俩所相关的维修订单数
        IPage<RepairOrderVO> voIPage = repairOrderIPage.convert(repairOrder -> {
            RepairOrderVO vo = convertToVO(repairOrder);
            // 设置订单相关车俩的车牌号
            if(repairOrder.getVehicleId() != null){
                vo.setVehicleLicensePlate(vehicleMapper.selectById(repairOrder.getVehicleId()).getLicensePlate());
            }
            // 设置订单相关顾客名和电话
            if(repairOrder.getCustomerId() != null){
                Customer customer = customerMapper.selectById(repairOrder.getCustomerId());
                vo.setCustomerName(customer.getName());
                vo.setPhone(customer.getPhone());
            }
            // 设置订单相关技师名
            if(repairOrder.getTechnicianId() != null){
                vo.setTechnicianName(employeeMapper.selectById(repairOrder.getTechnicianId()).getName());
            }
            System.out.println(vo.getOrderDate());
            return vo;
        });
        // 返回分页查询结果
        return voIPage;
    }

    private RepairOrderVO convertToVO(RepairOrder repairOrder) {
        RepairOrderVO vo = new RepairOrderVO();
        BeanUtils.copyProperties(repairOrder, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}