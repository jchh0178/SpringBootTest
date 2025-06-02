package com.itwillbs.controller;


import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.entity.Board;
import com.itwillbs.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Controller
@Log
@RequestMapping("/board/*")
public class BoardController {
	
	private final BoardService boardService;
	
	//파일업로드 경로 가져오기
	//uploadPath = D:/workspace_sts4/SpringBoot/src/main/resources/static/uploadPath
	@Value("${uploadPath}")
	String uploadPath;
	
	//파일업로드 화면
	@GetMapping("fwrite")
	public String fwrite() {
		
		return "/board/fwrite";
	}
	
	//파일업로드 처리
	@PostMapping("/fwritePro")
	public String fwritePro(@RequestParam Map<String, String> map, @RequestParam("file") MultipartFile file) throws Exception {
		//업로드할 파일 이름 만들기 => 랜덤문자_파일이름
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString() + "_" + file.getOriginalFilename();
		
		//파일 복사 원본 파일 => 업로드폴더, 파일이름
		FileCopyUtils.copy(file.getBytes(), new File(uploadPath, filename));
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setName(map.get("name"));
		boardDTO.setSubject(map.get("subject"));
		boardDTO.setContent(map.get("content"));
		boardDTO.setFile(filename);
		
		boardService.save(boardDTO);
		
		return "redirect:/board/list";
	}
	
	
	
	
	@GetMapping("/write")
	public String write() {
		log.info("BoardController write()");
		
		return "/board/write";
	}
	
	@PostMapping("/writePro")
	public String writePro(BoardDTO boardDTO) {
		log.info("BoardController writePro()");
		log.info(boardDTO.toString());
	   
		boardService.save(boardDTO);
	   
		return "redirect:/board/list";
	}
	
	
//	http://localhost:8080/board/list get방식
//	List<Board> boardList = findAll() 호출
//	model "boardList",boardList담아서 이동
//	/board/list 이동
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		log.info("BoardController list()");
		
//		한 페이지 번호 page
//		한 화면에 보여줄 글 개수
		int size = 10;
		
//		select * from board order by num desc limit 1-1, 10
//		import org.springframework.data.domain.PageRequest;
//		PageRequest에서 page 0부터 시작 => page - 1
//		PageRequest.of(page, size, 정렬)
//		import org.springframework.data.domain.Pageable;
//		import org.springframework.data.domain.Sort;
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("num").descending());
		
//		List<Board> boardList = boardService.findAll();
//		import org.springframework.data.domain.Page;
		Page<Board> boardList = boardService.findAll(pageable);
		
//		전체 페이지 번호
		int totalPages = boardList.getTotalPages();
//		한 화면에 보여줄 페이지 개수 설정
		int pageBlock = 10;
//		한 화면에 시작 페이지 구하기
		int startPage = (page - 1) / pageBlock * pageBlock + 1;
//		한 화면에 끝 페이지 구하기
		int endPage = startPage + pageBlock - 1;
		
		if(endPage > totalPages) {
			endPage = totalPages;
		}
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "board/list";
	}
	
	
	@GetMapping("/listJson")
	@ResponseBody
	public List<Board> listJson( 
	@RequestParam(value = "page",defaultValue = "1", required = false) int page) {
	      
	   int size = 10;

	   Pageable pageable = PageRequest.of(page - 1, size, Sort.by("num").descending());
	      
	   Page<Board> boardList = boardService.findAll(pageable);
	      
	   return boardList.getContent();
	}
	
	@GetMapping("/content")
	public String content(@RequestParam("num") int num, Model model) {
	   log.info("BoardController content()==========");
	      
		// Optional<Board> board = boardService.findById(num);
			   
		// model.addAttribute("board",board.get());
	   
	   Board board = null;
	   
	   try {
		   board = boardService.findById(num).orElseThrow(() -> new Exception("없는 글"));
	   } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   
	   // 조회수 1증가
	   //update board set readcount = readcount + 1 where num = ?
	   //update board set name = ?, subject = ?, content = ?, rdate = ?, readcount = ?
	   board.setReadcount(board.getReadcount() + 1);
       boardService.updateReadcount(board);

	   
	   model.addAttribute("boardDTO", board);
	      
	   return "board/content";
	}
	
	@GetMapping("/update")
	public String update(Model model, @RequestParam("num") int num) {
		log.info("BoardController update()==========");
		
		 Board board = null;
		   
		   try {
			   board = boardService.findById(num).orElseThrow(() -> new Exception("없는 글"));
		   } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		
		 model.addAttribute("boardDTO", board);
		
		return "board/update";
		
	}
	
	@PostMapping("/updatePro")
	public String updatePro(BoardDTO boardDTO) {
		log.info("BoardController updatePro()==========");
		
		boardService.save(boardDTO);
		
		return "redirect:/main";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("num") int num) {
		log.info("BoardController delete()==========");
		
		boardService.deleteById(num);
		
		return "redirect:/main";
	}
	

}
