package com.koscom.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Auditing : createDate, modifieDate, user 등을 자동으로 관리해주는 기능
@Getter
@MappedSuperclass // 클래스로는 필드를 상속받을 수 있지만, JPA는 인식 못하기 때문에 이 어노테이션을 사용해서 명시해줌
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 사용하겠다
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdTime; // 등록 시간

    @LastModifiedDate
    private LocalDateTime updatedTime; // 마지막 수정 시간
}
