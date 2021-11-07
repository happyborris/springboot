package com.koscom.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Optional은 null일 수도, 아닐 수도 있는 반환형을 받을 때 사용.
    Optional<User> findByEmail(String email);
}
