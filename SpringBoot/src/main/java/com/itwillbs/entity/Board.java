package com.itwillbs.entity;

import java.sql.Timestamp;

import com.itwillbs.domain.BoardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Hibernate: 
//create table members (
//  id varchar(50) not null,
//  name varchar(255),
//  pass varchar(255) not null,
//  primary key (id)
//) engine=InnoDB

// 엔터티 매핑 관련 어노테이션
// @Entity : 클래스 엔터티 선언
// @Table : 엔터티와 매핑 할 테이블 지정
// @Id : 테이블에서 기본키 사용할 속성 지정
// @Column : 필드와 컬럼 매핑(멤버변수 테이블 열이름 매핑)
// name = "컬럼명", length = 크기, nullable = false, unique,
//		columnDefinition = varchar(5) 직접지정, insertable, updatable
// @GeneratedValue(Strategy = GenerationType.AUTO) 키값 생성, 자동으로 증가
// @Lob BLOB, CLOB 타입매핑
// @CreateTimestamp insert 시 시간 자동 저장
// @Enumerated enum 타입매핑


@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num", updatable = false)
	private int num;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "readcount")
	private int readcount;
	
	@Column(name = "rdate")
	private Timestamp rdate;
	
	@Column(name = "file")
	private String file;
	
	
//	create table board (
//	    num integer not null auto_increment,
//	    content varchar(255),
//	    file varchar(255),
//	    name varchar(255),
//	    rdate datetime(6),
//	    readcount integer,
//	    subject varchar(255),
//	    primary key (num)
//	) engine=InnoDB
	
//	MemberDTO = Member에 저장
	public static Board setBoardEntity(BoardDTO boardDTO) {

		Board board = new Board();
		board.setNum(boardDTO.getNum());
		board.setName(boardDTO.getName());
		board.setSubject(boardDTO.getSubject());
		board.setContent(boardDTO.getContent());
		board.setReadcount(boardDTO.getReadcount());
		board.setRdate(boardDTO.getRdate());
		board.setFile(boardDTO.getFile());
		
		return board;
	}
	
}
