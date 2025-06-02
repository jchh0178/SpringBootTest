package com.itwillbs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwillbs.domain.MemberDTO;

import lombok.extern.java.Log;

@Controller
@Log
public class TestController {
	
//	http://localhost:8080/test1
	@GetMapping("/test1")
	public String test1() {
		
		return "test1";
	}
	
//	http://localhost:8080/test2
	@GetMapping("/test2")
	public String test2() {
//		test2.jsp => 에러발생
		return "test2";
	}
	
//	http://localhost:8080/test3
//	MemberDTO 객체생성
//	set메서드 호출 "kim", "p123", "김길동"
//	model 담아서 test3.html 이동
	@GetMapping("/test3")
	public String test3(Model model) {
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setId("kim");
		memberDTO.setPass("p123");
		memberDTO.setName("김길동");
		
		model.addAttribute("memberDTO", memberDTO);
		
		return "test3";
	}
	
	
//	http://localhost:8080/test4
//	List<MemberDTO> memberList = 객체생성
	
//	MemberDTO 객체생성
//	set메서드 호출 "kim", "p123", "김길동"
//	memberList.add()
	
//	MemberDTO 객체생성
//	set메서드 호출 "lee", "p456", "이순신"
//	memberList.add()
	
//	MemberDTO 객체생성
//	set메서드 호출 "hong", "p789", "홍길동"
//	memberList.add()
	
//	model 담아서 test4.html 이동
	@GetMapping("/test4")
	public String test4(Model model) {
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		MemberDTO memberDTO1 = new MemberDTO();
		memberDTO1.setId("kim");
		memberDTO1.setPass("p123");
		memberDTO1.setName("김길동");
		memberList.add(memberDTO1);
		
		MemberDTO memberDTO2 = new MemberDTO();
		memberDTO2.setId("lee");
		memberDTO2.setPass("p456");
		memberDTO2.setName("이순신");
		memberList.add(memberDTO2);
		
		MemberDTO memberDTO3 = new MemberDTO();
		memberDTO3.setId("hong");
		memberDTO3.setPass("p789");
		memberDTO3.setName("홍길동");
		memberList.add(memberDTO3);
		
		model.addAttribute("memberList", memberList);
		
		return "test4";
	}
	
	@GetMapping("/test5")
	public String test5(Model model, MemberDTO memberDTO) {
		
		model.addAttribute("memberDTO", memberDTO);
		
		return "test5";
	}
	
	
}
