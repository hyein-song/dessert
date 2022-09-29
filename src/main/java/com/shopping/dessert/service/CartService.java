package com.shopping.dessert.service;

import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.entity.CartEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
import com.shopping.dessert.repository.CartRepository;
import com.shopping.dessert.repository.ProductRepository;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addToCart(CartDto.Response.CartAddForm cartAddForm, UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->{
            throw new CustomException("해당 이메일의 유저가 존재하지 않습니다.", ErrorCode.USER_NOT_FOUND);
        });

        ProductEntity product = productRepository.findByProductId(cartAddForm.getProductId()).orElseThrow(()-> {
            throw new CustomException("해당 id의 상품이 존재하지 않습니다.",ErrorCode.PRODUCT_NOT_FOUND);
        });

        Optional<CartEntity> cart = cartRepository.findByUserAndProduct(user,product);

        if (cart.isPresent()){
            cart.get().changeCartEntity(cartAddForm); // 원래 있으면 새로 담긴 개수만큼 증가
        } else {
            CartEntity cartEntity = CartEntity.builder()
                    .amount(cartAddForm.getAmount())
                    .user(user)
                    .product(product)
                    .build();

            cartRepository.save(cartEntity);
        }

    }

    @Transactional
    public List<CartDto.Response.CartDetailForm> getCartlist(UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->{
            throw new CustomException("해당 이메일의 유저가 존재하지 않습니다.",ErrorCode.USER_NOT_FOUND);
        });

        List<CartEntity> cart = cartRepository.findByUser(user);

        return cart.stream()
                .map(CartDto.Response.CartDetailForm::of)
                .collect(Collectors.toList());

    }

    @Transactional
    public void deleteFromCart(Long cartId){
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()->{
            throw new CustomException("해당 id의 장바구니 아이템이 존재하지 않습니다.",ErrorCode.CART_NOT_FOUND);
        });

        cartRepository.delete(cart);
    }


}
