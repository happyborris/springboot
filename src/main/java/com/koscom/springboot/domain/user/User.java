package com.koscom.springboot.domain.user;

import com.koscom.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING) // enum은 원래 숫자라, ORIDINAL로 하면 db에 숫자로 저장 -> 새로운 필드가 생겼을 시 기존 꺼 전부 migration 해야하는 위험성 -> String으로 db에 저장!
    @Column(nullable = false)
    private Role role; // DB에는 varchar 문자열로 들어감, JPA에서 꺼낼 때는 enum으로 맵핑 됨.

    @Builder
    public User(Long id, String name, String email, String picture, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
