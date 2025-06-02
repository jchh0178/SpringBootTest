package com.itwillbs.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.entity.Board;
import com.itwillbs.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public void save(BoardDTO boardDTO) {
		log.info("BoardService save()");
		
		boardDTO.setRdate(new Timestamp(System.currentTimeMillis()));
		boardDTO.setReadcount(0);

		Board board = Board.setBoardEntity(boardDTO);
		
		boardRepository.save(board);
		
	}

	
	public Page<Board> findAll(Pageable pageable) {
		log.info("BoardService findAll()");
		
		return boardRepository.findAll(pageable);
	}

	public Optional<Board> findById(int num) {
		log.info("BoardService findById()");
		
		return boardRepository.findById(num);
	}

	public void updateReadcount(Board board) {
        log.info("BoardService updateReadcount()");
      
        boardRepository.save(board);
	      
	   }

	public void deleteById(int num) {
		log.info("BoardService deleteById()");

		boardRepository.deleteById(num);
	}

}
