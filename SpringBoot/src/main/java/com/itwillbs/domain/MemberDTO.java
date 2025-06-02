package com.itwillbs.domain;

//p38 롬북 라이브러리 설치
//https://projectlombok.org/download
//다운로드 Download 1.18.38 => lombok.jar
//cmd
//cd Download
//java -jar lombok.jar
//p64 Lombok 관련 어노테이션
//@Setter, @Getter : Setter, Getter 메서드 생성
//@ToString : toString() 메서드 생성
//@ToString(exclude={변수명}) : 변수명 제외 toString() 메서드 생성
//@NonNull : null체크 => NullPointException 예외발생
//@EqualsAndHashCode : equals(), hashCode() 메서드 생성
//@Builder : 빌더 패턴 이용 객체생성
//@NoArgsConstructor : 파라미터가 없는 생성자(기본생성자) 생성
//@AllArgsConstructor : 모든 파라미터가 있는 생성자 생성
//@RequiredArgsContructor : 초기화 되지 않은 Final, @NonNull 붙은 필드 생성자 생성
//@Value : 불변(값이 변하지 않음, 변경할 수 없는) 클래스 생성
//@data : @Setter, @Getter, @ToString,
//		  @EqualsAndHashCode, @RequiredArgsContructor 합친 어노테이션
//@log4j : log 자동변수 생성

//pom.xml
//<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
//<dependency>
//    <groupId>org.projectlombok</groupId>
//    <artifactId>lombok</artifactId>
//    <version>1.18.38</version>
//    <scope>provided</scope>
//</dependency>


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 회원관련 데이터를 저장해서 전달
@Setter
@Getter
@ToString
public class MemberDTO {
   
   // 객체지향개념 특징 : 데이터 은닉(캡슐화)
   // 멤버변수 : 외부에서 접근 못하게 막아줌(데이터 은닉, 접근 제어자 private)
   private String id;
   private String pass;
   private String name;
   // 권한
   private String role;

}
