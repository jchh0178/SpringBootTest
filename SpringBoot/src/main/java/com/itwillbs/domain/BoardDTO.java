package com.itwillbs.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BoardDTO {

	private int num;
	private String name;
	private String subject;
	private String content;
	private Timestamp rdate;
	private int readcount;
	//file
	private String file; 
	
}
