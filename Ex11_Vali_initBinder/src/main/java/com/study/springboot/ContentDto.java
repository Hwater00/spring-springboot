package com.study.springboot;

import lombok.Data;

@Data
public class ContentDto {
	private int id;
	private String writer;
	private String content;
//	private int id;
//	@NotNull(message = "writer is null")
//	@NotEmpty(message = "writer is empty")
//	@size(min=3, max=10, message="writer min3, max 10.")
//	private String writer;
//	@NotNull(message="content is null")
//	@NotEmpty(message="content id wmpty")
//	private String content;
}
