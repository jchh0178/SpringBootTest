package com.itwillbs.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Member;
import com.itwillbs.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	 //비밀번호 암호화
	   //import org.springframework.security.crypto.password.PasswordEncoder;
	   private final PasswordEncoder passwordEncoder;

	
	public void save(MemberDTO memberDTO) {
		log.info("MemberService save()");
		
//		Member member = new Member();
//		member.setId(memberDTO.getId());
//		member.setPass(memberDTO.getPass());
//		member.setName(memberDTO.getName());

//		Member member = Member.setMemberEntity(memberDTO);
		
		//비밀번호 암호화, role 권한 부여 
	    Member member = Member.createUser(memberDTO, passwordEncoder);

		memberRepository.save(member);
		
//		Hibernate: 
//		    select
//		        m1_0.id,
//		        m1_0.name,
//		        m1_0.pass,
//		        m1_0.role 
//		    from
//		        members m1_0 
//		    where
//		        m1_0.id=?
//		Hibernate: 
//		    insert 
//		    into
//		        members
//		        (name, pass, role, id) 
//		    values
//		        (?, ?, ?, ?)
	}

	public Member findByIdAndPass(MemberDTO memberDTO) {
		log.info("MemberService findByIdAndPass()");
		
		return memberRepository.findByIdAndPass(memberDTO.getId(), memberDTO.getPass());
	}

	public Optional<Member> findById(String id) {
		log.info("MemberService findById()");
		
		return memberRepository.findById(id);
	}

	public void saveUpdate(MemberDTO memberDTO) {
		log.info("MemberService saveUpdate()");
		
		Member member = Member.setMemberEntity(memberDTO);
		
		memberRepository.save(member);
		
	}

	public void deleteById(String id) {
		log.info("MemberService deleteById()");
		
		memberRepository.deleteById(id);
		
	}

	public Page<Member> findAll(Pageable pageable) {
		log.info("MemberService findAll()");
		
		return memberRepository.findAll(pageable);
	}

	public boolean checkIfIdExists(String id) {
		log.info("MemberService checkIfIdExists()");
		
		return memberRepository.existsById(id);
	}
	
	
}
