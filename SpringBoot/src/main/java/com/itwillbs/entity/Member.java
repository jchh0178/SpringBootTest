package com.itwillbs.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwillbs.domain.MemberDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
//      columnDefinition = varchar(5) 직접지정, insertable, updatable
// @GeneratedValue(Strategy = GenerationType.AUTO) 키값 생성, 자동으로 증가
// @Lob BLOB, CLOB 타입매핑
// @CreateTimestamp insert 시 시간 자동 저장
// @Enumerated enum 타입매핑

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
public class Member {

   @Id
   @Column(name = "id", length = 50)
   private String id;

   @Column(name = "pass", nullable = false)
   private String pass;

   @Column(name = "name")
   private String name;

   // 권한 컬럼 -> 일반 사용자 user, 관리자 admin
   @Column(name = "role")
   private String role;

   // 스프링 시큐리티 : 애플리케이션 인증, 인가를 일관된 형태로 처리하는 모듈
   // 인증 : 로그인 사용자 식별
   // 인가 : 시스템 자원에 대한 접근을 통제
   
   // SecurityFilterCahin : 인증
   //				      : 인가

   public Member() {
   }

   public Member(String id, String pass, String name, String role) {
      super();
      this.id = id;
      this.pass = pass;
      this.name = name;
      this.role = role;
   }

//   MemberDTO = Member에 저장
   public static Member setMemberEntity(MemberDTO memberDTO) {

      Member member = new Member();
      member.setId(memberDTO.getId());
      member.setPass(memberDTO.getPass());
      member.setName(memberDTO.getName());
      member.setRole(memberDTO.getRole());
      return member;
   }

   public static Member createUser(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
      String rolerUser = null;

      if (memberDTO.getId().equals("admin")) {
         rolerUser = "ADMIN";
      } else {
         rolerUser = "USER";
      }

      return new Member(memberDTO.getId(), passwordEncoder.encode(memberDTO.getPass()), memberDTO.getName(),
            rolerUser);
   }

}
