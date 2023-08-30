package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/put-api")
public class PutController {
    @PutMapping(value = "/default")
    public String putMethod(){
        return "Hello World";
    }

    @PutMapping(value = "/member")
    public String postMember(@RequestBody Map<String,Object> putData){
        StringBuilder sb = new StringBuilder();
        putData.entrySet().forEach(map->{
            sb.append(map.getKey()+":"+map.getValue());
        });
        return sb.toString();
    }

    @PutMapping(value = "/member1")
    public String  postMember1(@RequestBody MemberDto memberDto){
        return memberDto.toString(); //
    }

    @PutMapping(value = "/member2")
    public MemberDto postMember2(@RequestBody MemberDto memberDto){
        return memberDto; //
    }

    @PutMapping(value = "/member3")
    public ResponseEntity<MemberDto> postMember3(@RequestBody MemberDto memberDto){ // HttpEntity 상속,
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(memberDto);
    }
}
