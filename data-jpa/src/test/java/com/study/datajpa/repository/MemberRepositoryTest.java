package com.study.datajpa.repository;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import com.study.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    // 같은 트랜잭션이면 같은 엔티티메니저 사용
    @PersistenceContext
    EntityManager em;

    // 사용자 정의 추가
    @Autowired MemberQueryRepository memberQueryRepository;

    @Test
    public void testMember(){
        System.out.println("memberRepository ="+memberRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        // JAVA 8부터 Optional 제공
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        findMember1.setUsername("member!!!!!!");


        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA",15);
        // Username은 =로 적용, AgeGreaterThan은 >
        // 만약에 필드명 이름을 틀린다면? No property 오류 발생
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void findHelloBy(){
        List<Member> helloBy = memberRepository.findTop3HelloBy();
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMEmber = result.get(0);
        assertThat(findMEmber).isEqualTo(m1);
    }

    @Test
    public void testQuery(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA",10);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void findUsernameList(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList){
            System.out.println("s =" +s);
        }
    }

    @Test
    public void findMemberDto(){
        // member와 team 이너조인
        // 생성자에 전닿해서 만들어준 후 반환하는 SQL 로직 확인

        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA",10);
        memberRepository.save(m1);

        m1.setTeam(team);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for(MemberDto dto: memberDto){
            System.out.println("dto ="+dto);
        }

    }


    @Test
    public void findByNames(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA","BBB"));
        // AAA,BBBfmf in절로 가져옴
        for (Member member : result) {
            System.out.println("member ="+member);
        }
    }

    @Test
    public void returnType(){
        Member m1 = new Member("AAA",10);
        Member m2 = new Member("BBB",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // 컬렉션 조회 시 데이터가 없는 값에 대한 조회-> null이 아니라 빈 컬렉션을 반환함.
        // 컬렉션은 null이 아님을 보장함. 빈 컬렉션은 null은 아님.
        List<Member> aaa = memberRepository.findListByUsername("AAA");

        // 단건 조회는 없으면 null을 반환함
        Member aaa2 = memberRepository.findMemberByUsername("AAA");

        // 데이터가 없을 가능성 있다면 Optional을 사용
        // Optional 타입으로 반환하면, 없으면 Optional.empty
        Optional<Member> findMember =memberRepository.findOptionalByUsername("adaada");

    }

    @Test
    public void paging(){
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        // page 1 -> offset=0,limit=10, page 2-> offset=1 ,limit =10

        int age =10;
        // 페이지는 0부터 시작
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findByAge(age,pageRequest); // 실제 페이지된 컨텐츠, Pageable 자식이 PageRequest
        // 반환 타입이 Page이면 totalCount 쿼리도 같이 진행해줘서 아래 코드 필요X
        // long totalCount = memberRepository.totalCount(age); // 페이지 위치
        page.map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));

        // 페이지 계산 공식 적용...
        // totalPage = totalCount / size;
        // 마지막 페이지? 최초 페이지?

        // then
        List<Member> content = page.getContent(); //page내부 실제 컨텐츠 꺼내기 : getContent
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member ="+member);
        }
        System.out.println("totalElements="+totalElements); // totalElements == totalCount

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

    }



   /* @Test
    public void slice(){
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        // page 1 -> offset=0,limit=10, page 2-> offset=1 ,limit =10

        int age =10;
        // 슬라이스는 limt에 1개더 더해서 요청
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Slice<Member> page = memberRepository.findByAge(age,pageRequest); // 실제 페이지된 컨텐츠, Pageable 자식이 PageRequest
        // List로 반환 타입을 하면?pageRequest로 limit만 나옴.

        // then
        List<Member> content = page.getContent(); //page내부 실제 컨텐츠 꺼내기 : getContent



        assertThat(content.size()).isEqualTo(3);
        //assertThat(page.getTotalElements()).isEqualTo(5); Slice는 TotalElements, TotalCount 쿼리가 없어서 모른다!
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

    }*/


    @Test
    public void bulkUpdate(){
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",19));
        memberRepository.save(new Member("member3",20));
        memberRepository.save(new Member("member4",21));
        memberRepository.save(new Member("member5",40));
        /*
        JPQL 나가기 전에 기본적으로 DB에 반영 => flush 한다 => 쿼리 보냄
        그러고 나서 JPQL이 실행됨
        */


        //when
        int resultCount = memberRepository.bulkAgePlus(20);
        em.flush();
        //em.clear(); //벌크연산 이후에는 영속성 컨텍스트를 날려야 한다!

        List<Member> result = memberRepository.findByUsername("member5");
        // 벌크연산이라서 jpa 영속성 컨텍스트에는 40살로 남아있지만 db에는 41살로 반연됨.
        Member member5 = result.get(0);
        System.out.println("member5="+member5);

        // then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        //given
        // member1 -> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member3 = new Member("member1", 10, teamB);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //when : N+1 문제라고 말함
        // 이때 1은 select Member를 말하고, 결과를 N이라고 하는데 결과가 n번이면 추가 쿼리는 n번 더 나간다.
        // => 지금은 2개니깐 2번 결과 : N은 2
        // select Member만 가져옴
        List<Member> members = memberRepository.findAll();// findAll은 순수하게 Member객체만 가져옴

        // fetch join하면 기존으로 left out join이 실행됨.
        List<Member> memberTeam = memberRepository.findEntityGraphByUsername("member1");

        for (Member member : members) {
            System.out.println("member =" +member.getUsername());
            // 하이버네티으 프록시라서 member쿼리가 나고 member.getTeam().getName()시 실제 쿼리가 나감
            System.out.println("member.teamClass= " +member.getTeam().getClass());
            System.out.println("member.team =" + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint(){
        Member member1 = new Member("member1",10);
        memberRepository.save(member1);
        em.flush(); // JPA 영속성 컨텍스트내 1차 캐시를 DB에 동기화만 하고
        em.clear(); // 삭제는 clear
        
        // when
        Member findmember = memberRepository.findReadOnlyByUsername("member1");
        findmember.setUsername("member2");

        em.flush(); //변경 감지
    }

    @Test
    public void lock(){
        // given
        Member member1 = new Member("member1",10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        List<Member> result = memberRepository.findLockByUsername("member1");
    }

    @Test
    public void callCustom(){
        List<Member> result = memberRepository.findMemberCustom();
    }


    @Test
    public void projections(){
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1",0,teamA);
        Member m2 = new Member("m2",0,teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        List<UsernameOnly> result = memberRepository.findProjectionsByUsername("m1");
        for (UsernameOnly usernameOnly : result) {
            System.out.println("usernameOnly=" +usernameOnly);
        }

    }

    @Test
    public void projectionsDto(){
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1",0,teamA);
        Member m2 = new Member("m2",0,teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
//      List<UsernameOnlyDto> result = memberRepository.findProjectionsDtoByUsername("m1");
        //      동적 Projection은 제너릭 <T> 타입으로 바꾸고 클래스를 명시헤줌
        List<UsernameOnlyDto> result = memberRepository.findProjectionsDtoByUsername("m1", UsernameOnlyDto.class);
        for (UsernameOnlyDto usernameOnlyDto : result) {
            System.out.println("usernameOnly=" +usernameOnlyDto);
        }
    }

    @Test
    public void NestedClosedProjections(){
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1",0,teamA);
        Member m2 = new Member("m2",0,teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        
        // 동적 Projection은 제너릭 <T> 타입으로 바꾸고 클래스를 명시헤줌
        // 쿼리조건만 username이고 실제 조건은 NestedClosedProjections를 사용함
        List<NestedClosedProjections> result = memberRepository.findProjectionsDtoByUsername("m1", NestedClosedProjections.class);

        for (NestedClosedProjections usernameOnly : result) {
           String username = usernameOnly.getUsername();
            System.out.println("username"+username);
            String teamName = usernameOnly.getTeam().getName();
            System.out.println("teamName="+teamName);
        }

    }

}