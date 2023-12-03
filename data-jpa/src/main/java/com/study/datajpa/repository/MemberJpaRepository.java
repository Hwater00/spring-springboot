package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository // jpa 예외를 spring 공통적 예외로 변환하는 기능도 포함
public class MemberJpaRepository {

    @PersistenceContext //스프링부트 컨테이너가 영속성 컨텍스트 엔티티 메니저를 가져와 줌
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }


    public void delete(Member member){
        em.remove(member); // 실제 데이터베이스에스 delete쿼리가 나오면서 삭제됨
    }
    public List<Member> findAll(){
        // 전체를 조회하거나 특정 조건을 조회할 떄는 JPA가 제공하는 JPQL을 사용
        // Member 엔티티라는 객체를 대상으로 함
        // ("select m from Member m",반환 타입 :Member.class)
        List<Member> result = em.createQuery("select m from Member m",Member.class)
                .getResultList();
        return result;
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class,id); //Member.class를 id로 조회
        return Optional.ofNullable(member);
    }

    public long count(){
        return em.createQuery("select count(m) from Member m",Long.class)
                .getSingleResult(); // 단 건인 경우
    }

    public Member find(Long id){
        return em.find(Member.class,id);

    }

    public List<Member> findByUsernameAndAgeGreaterThen(String username, int age){
        return em.createQuery("select m from Member m where m.username =:username and m.age > :age")
                .setParameter("username",username)
                .setParameter("age",age)
                .getResultList();
    }

    public List<Member> findByUsername(String username){
        return em.createNamedQuery("Member.findByUsername", Member.class) // Member에서 정의한 것을 불러옴
                .setParameter("username",username)
                .getResultList();
    }

    public List<Member> findByPage(int age,int offset, int limit){ // int offset, int limit는 몇 부터 시작해서 몇 개씩
        return em.createQuery("select  m from  Member m where m.age = :age order by m.username desc ")
                .setParameter("age",age)
                .setFirstResult(offset) // 시작
                .setMaxResults(limit) // 갯수
                .getResultList();
    }

    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age =:age", Long.class)
                .setParameter("age",age)
                .getSingleResult();
    }

    public int bulkAgePlus(int age){
        int resultCount = em.createQuery("update Member  m set m.age = m.age +1 " +
                        "where m.age >= :age")
                .setParameter("age",age)
                .executeUpdate();
        return resultCount;
    }

}
