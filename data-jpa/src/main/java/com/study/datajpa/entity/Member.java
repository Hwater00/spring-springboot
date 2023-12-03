package com.study.datajpa.entity;

import com.study.datajpa.repository.MemberJpaRepository;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"}) // 연관관계 필드는 toString 하지 않는 것을 추천
@NamedQuery(
        name="Member.findByUsername",
        query = "select m from Member  m where m.username =:username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩을 세팅하여 성능 최적화
    @JoinColumn(name="team_id")
    private Team team;

    // 서로 연관간계를 세팅해주는 메서드 필요
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }


//    // JPA 표준 스펙에서 엔티티는 기본적으로 파라미터 없는 기본 생성자 필요
//    // private으로 만들지 못한다. protected까지 열어나여 JPA 구현체들이 프록시 할 떄 필요함.
//    protected Member(){
//    }

    public Member(String username) {
        this.username = username;
    }

    public Member(String username,int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username,int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }

    }

    public void changeUsername(String username){
        this.username = username;
    }

}
