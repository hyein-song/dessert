package com.shopping.dessert.entity;

import com.shopping.dessert.entity.value.UserGender;
import com.shopping.dessert.entity.value.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // TODO: format
    private String email;

    private String name;

    private String password;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Builder.Default
    private Long point = 0L;

    private LocalDateTime joinDateTime;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderEntity> orderEntities = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostEntity> postEntities = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CartEntity> cartEntities = new LinkedHashSet<>();

}
