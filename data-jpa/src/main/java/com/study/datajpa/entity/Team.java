package com.study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
public class Team extends JpaBaseEntity{
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // FK가 없는 쪽에 mappedBy 설정하기
    private List<Member> members  = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
