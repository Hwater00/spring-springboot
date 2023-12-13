package com.study.datajpa.repository;

public class UsernameOnlyDto {
    private final String username;

    public UsernameOnlyDto(String username){ // 생성자 파라마티 이름으로 매칭시켜서 Projections 가능
        this.username= username;
    }
    public String getUsername(){
        return username;
    }
}
