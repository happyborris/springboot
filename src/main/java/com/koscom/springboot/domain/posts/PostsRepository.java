package com.koscom.springboot.domain.posts;


import org.springframework.data.jpa.repository.JpaRepository;

// 예전에 DAO(Data Access Object), 즉 DB와 통신하며 데이터에 접근하는 객체를 요즘은 Repository 라고 부른다.
// <Posts, Long> ==> <대상이 되는 Entity, PK의 타입>
// JpaRepository 상속받은 인터페이스는 기본 CRUD가 자동 구현된다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
