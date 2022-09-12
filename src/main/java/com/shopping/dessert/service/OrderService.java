package com.shopping.dessert.service;

import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.OrderDto;
import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.repository.OrderProductRepository;
import com.shopping.dessert.repository.OrderRepository;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    public void addOrder(OrderDto.OrderProcDto orderProcDto, List<CartDto.Response.CartDetailForm> cartItems, Long userId){

        // 회원 조회
        UserEntity user = userRepository.findById(userId).orElseThrow(()->{
            throw new IllegalStateException("해당 Id의 회원이 존재하지 않습니다.");
        });

        // order 저장
        OrderEntity order = OrderEntity
                .builder()
                .user(user)
                .price(orderProcDto.getTotalPrice())
                .count((long) cartItems.size())
                .orderState("정상")
                .build();

        OrderEntity savedOrder = orderRepository.save(order);

        // orderProduct 저장
        for (CartDto.Response.CartDetailForm cartDetailForm : cartItems){
            OrderProductEntity orderProductEntity = OrderProductEntity
                    .builder()
                    .product(cartDetailForm.getProductDetail().toEntity())
                    .order(savedOrder)
                    .build();

            orderProductRepository.save(orderProductEntity);
        }
    }

    @Transactional
    public Set<OrderDto.OrderProductDetail> getOrderProductList(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(()->{
            throw new IllegalStateException("해당 아이디의 주문이 존재하지 않습니다.");
        });

        return order.getOrderProductEntities()
                .stream()
                .map(OrderDto.OrderProductDetail::of)
                .collect(Collectors.toSet());

    }
}
