package com.study.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MyUserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<MyUserDTO> list(){
		String query="select * from myuser";
		List<MyUserDTO> list = jdbcTemplate.query(query, new BeanPropertyRowMapper<MyUserDTO>(MyUserDTO.class));
		
		return list;
	}
}
// 실제로 데이터베이스에 엑세스하는 역할