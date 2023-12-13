package com.study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

    @Id //@GeneratedValue
    // GeneratedValue는 언제 들어가나요?
    // em.persist()가 완료되면 GeneratedValue는가 진행되어 id값이 생기고 그 전에는 null 값 입니다.
    private String id;

    @CreatedDate
    // @CreatedDate는 persiste 전에 호출된다.
    private LocalDateTime createDate;

    public Item(String id){
        this.id =id;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        // 조건을 넣어서 새로 생성되는지 아닌지 로직 작성
        return  createDate == null;
    }
}
