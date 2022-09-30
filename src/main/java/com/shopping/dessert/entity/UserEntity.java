package com.shopping.dessert.entity;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.value.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email
    private String email;

    private String name;

    private String password;

    private String phone;

    private String address;

    @Builder.Default
    private Long point = 0L;

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

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReplyEntity> replyEntities = new LinkedHashSet<>();

    public void changeUserInfo(UserDto.Request.MyInfoUpdateForm updateForm){
        this.name = updateForm.getName();
        this.password = updateForm.getPassword();
        this.phone = updateForm.getPhone();
        this.address = updateForm.getAddress();
    }

}
