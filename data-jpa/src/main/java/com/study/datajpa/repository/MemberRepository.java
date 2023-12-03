package com.study.datajpa.repository;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

// 인터페이스가 인터페이스를 상속
public interface MemberRepository extends JpaRepository<Member,Long> {
    // 이름을 가지고 유저를 찾기:<- 도메인 특화 기능
    // 커스텀 기능으로 이 기늠만 구현하고자 함.
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);// 쿼리 메서드 기능

    List<Member> findTop3HelloBy(); // 멤버 전부 조회

    // @Query부분 주석 처리해도 작동함
    //@Query(name = "Member.findByUsername") // 네임드 쿼리에서 찾는다.
    List<Member> findByUsername(@Param("username") String username); // @Param은 명확하게 JPQL이 있을 때-> 네임드파라미터가 넘어감으로

    // 이름이 없는 네임드쿼리 -> 로딩 시점에서 파싱 -> SQL을 만들기 때문에 문법 오류를 찾아줌
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 값이나 DTO 조회 - 사용자 이름만 다 가져오기
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 조회시에는 new 오퍼레이션 필요
    // DTO에 있는 컬럼들 조회 -> 생성자로 매칭되도록 작성하여 DTO로 반환
    // join m.team t으로 조인 진행
    @Query("select new com.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // in절을 만들어 줌
    @Query("select m from Member m where  m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names); // List 상위의 Collection

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    @Query(value = "select m from Member m left join m.team t",
        countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);// 쿼리 조건, n페이지
    // Slice<Member> findByAge(int age, Pageable pageable);// 쿼리 조건, n페이지


    // executeUpdate가 있기 위해 @Modifying  어노테이션 필요.
    @Modifying(clearAutomatically = true)  // 옵션 사용하면 em.clear() 대체
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // JPQL fetch join?
    @Query("select m from Member m left join fetch m.team")
        //fetch join은 meber 조회 시 select전에 연관된 member와 team을 한 번에 가져와서 Team엔티티에 넣어놓옴.
    List<Member> findMemberFetchJoin();

    @Override
    // 내부적으로 fetch join을 사용 + JPQL 사용하지 않고 사용
    @EntityGraph(attributePaths = ("team"))
    List<Member> findAll();

    // JPQL + EntityGraph
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 회원조회 시 팀 데이터도 사용할 일이 많으면 EntityGraph을 사용해서 뽑음
    @EntityGraph(attributePaths = ("team")) //attributePaths로 직접 작성
    //@EntityGraph("Member.all") // 미리  Member에서 지정
    List<Member> findEntityGraphByUsername(@Param("username") String username);



}
