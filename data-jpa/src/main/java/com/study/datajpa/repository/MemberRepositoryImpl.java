package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
// 인터페이스를 상속한 구현체

@RequiredArgsConstructor
// 규칙: --Repository+Impl 형식 지키기
public class MemberRepositoryImpl implements MemberRepositoryCustom{
    // QueryDSL 시 주로 커스텀해서 사용
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
