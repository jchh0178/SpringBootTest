package com.itwillbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Member;
import com.itwillbs.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@Log
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	private final PasswordEncoder passwordEncoder;

	@GetMapping("/insert")
	public String insert() {

		return "member/insert";
	}

// 	http://localhost:8080/insertPro post방식
	@PostMapping("/insertPro")
	public String insertPro(MemberDTO memberDTO) {
		log.info("MemberController insertPro()");
		log.info(memberDTO.toString());

		memberService.save(memberDTO);

		return "redirect:/login";
	}

// 	http://localhost:8080/login get방식
	@GetMapping("/login")
	public String login() {
		log.info("MemberController login()");

		return "member/login";
	}

// 	http://localhost:8080/loginPro post방식
//	Member member = findByIdAndPass(memberDTO)
//	if member != null 세션값 "id",id 이동 /main
//			  == null 이동 /login
//	@PostMapping("/loginPro")
//	public String loginPro(MemberDTO memberDTO, HttpSession session) {
//		log.info("MemberController loginPro()");
//		log.info(memberDTO.toString());
//		
//		Member member = memberService.findByIdAndPass(memberDTO);
//		
//		if(member != null) {
//			session.setAttribute("id", member.getId());
//			
//			return "redirect:/main";
//		}else {
//			return "redirect:/login";
//		}
//		
////		Hibernate: 
////		    select
////		        m1_0.id,
////		        m1_0.name,
////		        m1_0.pass 
////		    from
////		        members m1_0 
////		    where
////		        m1_0.id=? 
////		        and m1_0.pass=?
//		
//	}

// 	http://localhost:8080/main get방식
	@GetMapping("/main")
	public String main() {

		return "member/main";
	}

// 	http://localhost:8080/logout get방식
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}

// 	http://localhost:8080/info get방식
//	Optional<Member> member = findById(id)
//	model ("member", member.get())
	@GetMapping("/info")
	public String info(HttpSession session, Model model) {
		log.info("MemberController info()");

//		String id = (String)session.getAttribute("id");
		String id = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Member> member = memberService.findById(id);

		model.addAttribute("member", member.get());

		return "/member/info";
	}

// 	http://localhost:8080/update get방식
//	Optional<Member> member = findById(id)
//	model ("member", member.get())
//	/member/update 이동
	@GetMapping("/update")
	public String update(HttpSession session, Model model) {
		log.info("MemberController update()");

//		String id = (String)session.getAttribute("id");
		String id = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Member> member = memberService.findById(id);

		model.addAttribute("member", member.get());

		return "/member/update";
	}

// 	http://localhost:8080/updatePro post방식
//	MemberDTO 받기(id,pass,name)
//	rdate 디비에 가져와서 findById(id) -> 생략
//	memberDTO.set메서드 호출 rdate 저장 -> 생략
//	id,pass 일치 -> saveUpdate(memberDTO), main으로 이동
//	틀리면 -> update로 이동
	@PostMapping("/updatePro")
	public String updatePro(MemberDTO memberDTO) {
		log.info("MemberController loginPro()");
		log.info(memberDTO.toString());

//		Member member = memberService.findByIdAndPass(memberDTO);
		Member member = memberService.findById(memberDTO.getId())
				.orElseThrow(() -> new UsernameNotFoundException("없는 회원"));

//		비밀번호 비교
//		passwordEncoder.matches(로그인 시 사용자가 아는 비밀번호, 암호화된 비밀번호)
		boolean match = passwordEncoder.matches(memberDTO.getPass(), member.getPass());

		if (match == true) {
//			비밀번호 일치
			// 화면 id, pass, name
			// => 화면에 없으면 직접 입력 pass, role
			// save() 메서드 =? update members set pass=?,name=?,role=? where id=?
			memberDTO.setPass(member.getPass());
			memberDTO.setRole(member.getRole());

			memberService.saveUpdate(memberDTO);
			return "redirect:/main";
		} else {
//			비밀번호 틀리면
			return "redirect:/update";
		}

//		if(member != null) {
//			// 수정하러 가기전에 수정되지 않는 값도 담아서 들고감
//			// => 안들고 가면 기존값 null로 수정이 되어짐
////			memberDTO.setRDate(member.getRdate());
//			
//			memberService.saveUpdate(memberDTO);
//			
//			return "redirect:/main";
//		}else {
//			return "redirect:/update";
//		}

	}

// 	http://localhost:8080/delete get방식
//	/member/delete 이동
	@GetMapping("/delete")
	public String delete(Model model) {

		String id = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Member> member = memberService.findById(id);

		model.addAttribute("member", member.get());

		return "/member/delete";
	}

// 	http://localhost:8080/deletePro post방식
//	MemberDTO 받기(id,pass)
//	id,pass 일치 -> deleteById(id), logout으로 이동
//	틀리면 -> delete로 이동
//	@PostMapping("/deletePro")
//	public String deletePro(MemberDTO memberDTO, HttpSession session) {
//		log.info("MemberController deletePro()");
//		log.info(memberDTO.toString());
//
////		Member member = memberService.findByIdAndPass(memberDTO);
//		Member member = memberService.findById(memberDTO.getId())
//				.orElseThrow(() -> new UsernameNotFoundException("없는 회원"));
//
//		if (member != null) {
//			memberService.deleteById(member.getId());
//			
//			session.invalidate();
//			
//			return "redirect:/main";
//		} else {
//			return "redirect:/delete";
//		}
//
//	}

	@PostMapping("/deletePro")
	public String deletePro(MemberDTO memberDTO) {
		log.info("MemberController deletePro()");
		log.info(memberDTO.toString());
		Member member = memberService.findById(memberDTO.getId())
				.orElseThrow(() -> new UsernameNotFoundException("없는 회원"));

		// passwordEncoder.matches(로그인 시 사용자가 아는 비밀번호, 회원가입 시 암호화된 비밀번호)

		boolean match = passwordEncoder.matches(memberDTO.getPass(), member.getPass());
		if (match == true) {

			memberDTO.setPass(member.getPass());
			memberDTO.setRole(member.getRole());
			memberService.deleteById(memberDTO.getId());

			return "redirect:/logout";

		} else {
			return "redirect:/delete";
		}
	}

//	http://localhost:8080/list get방식
//	List<Member> memberList = findAll() 호출
//	model "memberList",memberList담아서 이동
//	/member/list 이동
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		log.info("MemberController list()");

//		한 페이지 번호 page
//		한 화면에 보여줄 글 개수
		int size = 10;

//		select * from board order by num desc limit 1-1, 10
//		import org.springframework.data.domain.PageRequest;
//		PageRequest에서 page 0부터 시작 => page - 1
//		PageRequest.of(page, size, 정렬)
//		import org.springframework.data.domain.Pageable;
//		import org.springframework.data.domain.Sort;
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());

//		List<Board> boardList = boardService.findAll();
//		import org.springframework.data.domain.Page;
		Page<Member> memberList = memberService.findAll(pageable);

//		전체 페이지 번호
		int totalPages = memberList.getTotalPages();
//		한 화면에 보여줄 페이지 개수 설정
		int pageBlock = 10;
//		한 화면에 시작 페이지 구하기
		int startPage = (page - 1) / pageBlock * pageBlock + 1;
//		한 화면에 끝 페이지 구하기
		int endPage = startPage + pageBlock - 1;

		if (endPage > totalPages) {
			endPage = totalPages;
		}

		model.addAttribute("memberList", memberList);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/member/list";
	}

	@GetMapping("/listJson")
	@ResponseBody
	public List<Member> listJson(@RequestParam(value = "page", defaultValue = "1", required = false) int page) {

		int size = 10;

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());

		Page<Member> memberList = memberService.findAll(pageable);

		return memberList.getContent();
	}

	@PostMapping("/checkId")
	@ResponseBody
	public Map<String, Boolean> checkId(@RequestBody Map<String, String> request) {
		String id = request.get("id");
		boolean exists = memberService.checkIfIdExists(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", exists);
		return response;
	}

//	 @GetMapping("/idCheck")
//     @ResponseBody
//     public String idCheck(@RequestParam("id") String id) {
//        String result = "";
//        Optional<Member> member = memberService.findById(id);
//        if(!member.isEmpty()) {
//           result="아이디 중복";
//        }else {
//           result="아이디 사용가능";
//        }
//        return result;
//     }

}