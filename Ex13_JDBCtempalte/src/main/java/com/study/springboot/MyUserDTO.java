package com.study.springboot;

import lombok.Data;

@Data
public class MyUserDTO {
	private String id;
	private String name;
}

// DTO는 쿼리로 발생하는 데이터를 처리하기 위한 것으로
