package com.study.datajpa.entity;

import com.study.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity(){
        Team teamA = new Team("temaA");
        Team teamB = new Team("temaB");
        em.persist(teamA); //
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamB);
        Member member4 = new Member("member4",40,teamB);

        em.persist(member1); // JPA 영속성 컨텍스트에 member를 모아둠
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush(); // DB에 강제로 insert 쿼리를 날림
        em.clear(); //

        // 확인
        List<Member> members = em.createQuery("select  m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member="+ member);
            System.out.println("-> member.team ="+member.getTeam());
        }
    }

    @Test
    public void JpaEventBaseEntity(){
        // given
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersist가 발생
//        Thread.sleep(100);
        member.setUsername("member2");
        em.flush(); // @PreUpdat 실행
        em.clear();

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        System.out.println("findMember= getCreatedDate"+ findMember.getCreatedDate());
        System.out.println("findMember= getCreatedDate"+ findMember.getLastModifiedDate());
        System.out.println("findMember= getLastModifiedBy" + findMember.getLastModifiedBy());
        System.out.println("findMember= getCreatedBy" + findMember.getCreatedBy());
    }
}