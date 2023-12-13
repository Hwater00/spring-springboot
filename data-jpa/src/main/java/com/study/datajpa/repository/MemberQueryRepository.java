package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

// 인터페이스가 아닌 클래스로 만들고 커맨드와 쿼리 분리
// 핵심 비지니스 로직과 아닌 로직 분리 시 클래스로 쪼갬.
@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final EntityManager em;
    List<Member> findAllMembers(){
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
