package com.study.datajpa.controller;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import com.study.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMEmber(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member){
        return member.getUsername();
    }

    @GetMapping("/members")
    // pageable은 파라미터 정보, Page는 결과정보
    // pageable이 있으면 PageRequest라는 객체를 생성해서 값을 채워서 준다.
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable){

        Page<Member> page = memberRepository.findAll(pageable);
         //return page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        return page.map(MemberDto::new);
    }


    @PostConstruct // 스프링애플리케이션이 올라올때 실행됨
    public void init(){
        for(int i=0; i<100; i++) {
            memberRepository.save(new Member("user"+i,i));
        }
    }
}
