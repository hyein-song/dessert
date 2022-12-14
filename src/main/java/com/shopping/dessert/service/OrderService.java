package com.shopping.dessert.service;

import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.OrderDto;
import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
import com.shopping.dessert.repository.OrderProductRepository;
import com.shopping.dessert.repository.OrderRepository;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addOrder(OrderDto.OrderProcDto orderProcDto, List<CartDto.Response.CartDetailForm> cartItems, Long userId) {

        // 회원 조회
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        // order 저장
        OrderEntity order = OrderEntity
                .builder()
                .user(user)
                .itemsPriceSum(orderProcDto.getItemsPriceSum())
                .shippingPrice(orderProcDto.getShippingPrice())
                .totalPrice(orderProcDto.getTotalPrice())
                .payment(orderProcDto.getPayment())
                .amount((long) cartItems.size())
                .orderState("정상")
                .build();

        OrderEntity savedOrder = orderRepository.save(order);

        // orderProduct 저장
        for (CartDto.Response.CartDetailForm cartDetailForm : cartItems) {
            OrderProductEntity orderProductEntity = OrderProductEntity
                    .builder()
                    .product(cartDetailForm.getProductDetail().toEntity())
                    .amount(cartDetailForm.getAmount())
                    .totalPrice(cartDetailForm.getTotalPrice())
                    .order(savedOrder)
                    .build();

            orderProductRepository.save(orderProductEntity);
        }

        return savedOrder.getOrderId();

    }

    @Transactional
    public OrderDto.OrderDetail getOrderDetail(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        });

        Set<OrderDto.OrderProductDetail> productDetails =
                orderProductRepository.findByOrder(order)
                        .stream()
                        .map(OrderDto.OrderProductDetail::of)
                        .collect(Collectors.toSet());

        return OrderDto.OrderDetail.of(order,productDetails);

    }

    public Page<OrderDto.OrderListForm> getOrderList(UserEntity user, Pageable pageable) {
        Page<OrderEntity> orderEntities = orderRepository.findByUser(user, pageable);

        return orderEntities.map(OrderDto.OrderListForm::of);

    }
}
