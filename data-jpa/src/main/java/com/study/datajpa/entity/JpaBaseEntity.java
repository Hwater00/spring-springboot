package com.study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 속성을 내려서 데이터만 공유하는 진짜 상속관계는 아님.
public class JpaBaseEntity {
    @Column(updatable = false, insertable = true)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // 저장 전에 발생
    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now; // update의 null이 있으면 나중에 쿼리 시 복잡해짐으로 최기 입력값을 같이 넣어준다.

    }

    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}

// JPA 진짜 상속관계와 속성만 내려받는 상속관계
