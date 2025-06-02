package com.itwillbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwillbs.entity.Board;

//extends JpaRepository<T(Entity), ID(기본키 형)>
//JpaRepository 지원하는 기본 메서드 제공
//save(Entity) : 엔터티 저장(id 없으면) 및 수정(id 있으면)
//void delete(Entity) : 엔터티 삭제
//void deleteById(id) : 엔터티 삭제

//count : 엔터티 총 개수 반환
//List<Entity> findAll() : 모든 엔터티 조회
//Optional<Entity> c(id) : id(기준키)에 대한 엔터티 조회

//쿼리 메서드 정의
//아이디 비밀번호 조회 : findByIdAndPass(id,pass) => where id = ? and pass = ?
//					 findByIdOrPass(id,pass) => where id = ? or pass = ?
//					 findByNumBetween() => where num between ? and ?

public interface BoardRepository extends JpaRepository<Board, Integer> {


	
}
