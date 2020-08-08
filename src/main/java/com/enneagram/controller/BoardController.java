package com.enneagram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.enneagram.function.OracleDateChange;
import com.enneagram.service.BoardService;
import com.enneagram.vo.BoardVO;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//게시판 작성
	@RequestMapping(value = "/boardWrite", method = RequestMethod.GET)
	public void boardWrite() {
		
	}
	
	//게시판 리스트 ,  리스트를 jsp파일로 넘기는게 필요하다. Model을 활용해서 넘긴다.
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)  
	public ModelAndView boardList(HttpServletRequest request, @RequestParam(defaultValue = "10", value = "maxLine") String maxLine,
			 @RequestParam(defaultValue = "", value = "search") String search,  @RequestParam(defaultValue = "", value = "search_type") String search_type) {
		ModelAndView mv = new ModelAndView("board/boardList");
		
		/* lineMax 설정 */
		int lineMax=Integer.parseInt(maxLine);
		int page;
		if(request.getParameter("startnum")==null || Integer.parseInt(request.getParameter("startnum"))<=0) {
			page = 1;
		}else {
			page = Integer.parseInt(request.getParameter("startnum"));
		}
		
		/* count 계산 */
		List<BoardVO> list = boardService.boardCount(); // 게시판의 개수를 가져오는 함수
		int count=0;
		for(BoardVO a : list) {
			count++;
		}
		count = count/lineMax +1;
		
		if(page>count) {
			page--;
		}
		
		System.out.println(search);
		System.out.println(search_type);
		
		
		//보여줄 시작게시글번호, 마지막 게시글번호
		BoardVO bo = new BoardVO();
		int start = (page-1)*lineMax+1; 
		int end = (page)*lineMax; 
		bo.setStart(start);
		bo.setEnd(end);
		
		List<BoardVO> blist =  new ArrayList<BoardVO>();
		if(search.equals("")) {
			//list 가져오기
			blist =boardService.selectList(bo);   // ArrayList는 안되고 List는 되는 이유가 뭘까? 왜 SqlSession의 selectList는 List형만 반환할까?
		}else {
			bo.setSearch_type(search_type);
			bo.setSearch(search);
			blist = boardService.selectListSearch(bo);
		}
		
		/* Date를 String으로 포맷 변환*/
		for(BoardVO board : blist) {
			board.setS_regdate(OracleDateChange.changeDate(board.getRegdate()));  
		}
		mv.addObject("blist",blist);
		mv.addObject("count",count);
		mv.addObject("page",page);
		
		return mv;
	}
	
	//게시판 작성을 데이터베이스로 입력
	@RequestMapping("/boardWrite_ok")
	public ModelAndView boardWrite_ok(BoardVO bo) {
		ModelAndView mv = new ModelAndView("redirect:/board/boardList");
		boardService.insertBoard(bo);
		return mv;
	}
	
	//게시판 내용보기
	@GetMapping("/boardCont")
	public ModelAndView boardCont(int bno, HttpSession session) {
		ModelAndView mv = new ModelAndView("board/boardCont");
		
		/* 댓글 조회수 막기 기능*/
		if(session.getAttribute(Integer.toString(bno))==null) {
			session.setAttribute(Integer.toString(bno), session.getId());
			session.setMaxInactiveInterval(3600);
		}
		
		BoardVO b = boardService.selectboard(bno,session,session.getId());
		b.setS_regdate(OracleDateChange.changeDate(b.getRegdate()));
		mv.addObject("b", b);
		
		
		return mv;
	}
	
	//게시판 수정페이지로 이동
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public void boardUpdate(Model m ,BoardVO b) {
		m.addAttribute("b", b);
	}
	
	//게시판 수정
	@PostMapping("/boardUpdate_ok")
	public ModelAndView boardUpdate_ok(BoardVO b) {
		ModelAndView mv = new ModelAndView("redirect:/board/boardCont?bno="+b.getBno());
		boardService.boardUpdate(b);
		return mv; 
	}
	
	//게시판 삭제
	@GetMapping("/boardDelete")
	public ModelAndView boardDelete(int bno, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView("redirect:/board/boardList");
		boardService.boardDelete(bno);
		PrintWriter out = response.getWriter();
		
		out.println("alert('게시글이 삭제되었습니다');");
		return mv; 
	} 
	
	//게시판 작성
	@RequestMapping(value = "/notice_board", method = RequestMethod.GET)
	public void notice_board() {
			
	}
}