package com.board.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.service.BoardService;
import com.board.vo.BoardVO;
import com.board.vo.PageVO;
import com.board.vo.ReplyVO;
import com.common.service.ExcelService;
import com.common.util.GetSession;
import com.common.vo.FileVO;
 
@Controller
public class BoardController {
    @Resource(name="boardService")
    private BoardService boardService;

    @Resource(name="excelService")
    private ExcelService service;
	
	
    //게시판 글 목록
    @RequestMapping(value="/board/boardList")
    public ModelAndView boardList(@ModelAttribute("search") BoardVO info,ModelMap model)
    {
    	System.out.println("인자값 확인 boardList: " + info.toStringMultiline());
        ModelAndView view = new ModelAndView();
        int totalCount = boardService.boardListCnt(info);
        PageVO page = new PageVO();
        page.setDisplayRow(info.getListNum());       
        page.setTotalCount(totalCount);
        page.setPage(info.getPage());
        
        List<BoardVO> resultlist = boardService.boardList(info);
        
        view.addObject("resultlist", resultlist);
        view.addObject("page",page);
        
        //조건값 유지를 위한 model
        view.addObject("search",info);
        view.setViewName("board/Board_List.view");
        return view;
    }
    
    //게시판 view
    @RequestMapping(value="/board/boardView", method=RequestMethod.POST)
    public ModelAndView boardView(@ModelAttribute("read") BoardVO searchInfo,ModelMap model,HttpServletRequest request)
    {
    	System.out.println("인자값 확인 searchInfo: " + searchInfo.toStringMultiline());
    	
        ModelAndView view = new ModelAndView();
        
        //내용불러오기
        int boardIdx = searchInfo.getBoardIdx();
        BoardVO viewContent = boardService.viewContent(searchInfo);
        
        //로그인한 사람인지 확인
		try {
			String login_id = GetSession.GetSessionId(request); //세션아이디 가져오기
			viewContent.setLogin_id(login_id);
		}catch (Exception e) {
			viewContent.setLogin_id("");
		}
        
        view.addObject("viewContent",viewContent);
        
        //게시글 첨부파일 불러오기
        List<FileVO> resultlist = boardService.fileList(boardIdx);
        view.addObject("resultlist", resultlist);

        //답글 불러오기
        List<ReplyVO> replylist = boardService.replyList(boardIdx);
        view.addObject("replylist", replylist);
        
        //검색조건 유지
        view.addObject("search",searchInfo);
        
        view.setViewName("board/Board_View.view");
        return view;        
    }
    
    //파일 추천 상태 변경
    @RequestMapping(value="/board/boardRecommend", method= RequestMethod.POST)
    public @ResponseBody BoardVO fileRecommend(BoardVO info,HttpServletRequest request) throws Exception {
    	
    	System.out.println("info인자값 확인 : " + info.toStringMultiline());
    	
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	info.setId(id);
    	
    	//추천 상태 바꾸기전 기존 정보 삭제
    	int delresult = boardService.deleteBoardRecommend(info);
    	
    	if(delresult == 1) {
    		boardService.insertBoardRecommend(info);
    	}
    	
    	BoardVO getCnt = new BoardVO();
    	getCnt = boardService.selectBoardRecommendCnt(info);
    	
    	info.setRecommendYcnt(getCnt.getRecommendYcnt());
    	info.setRecommendNcnt(getCnt.getRecommendNcnt());
    	
    	System.out.println("나중 info인자값 확인 : " + info.toStringMultiline());
    	
    	
	   return info;
    }
    
    //답글 등록
    @RequestMapping(value="/board/replyReg", method= RequestMethod.POST)
    public @ResponseBody ReplyVO replyReg(ReplyVO info,HttpServletRequest request) throws Exception {
    	
    	System.out.println("replyReg info인자값 확인 : " + info.toStringMultiline());
    	
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	info.setId(id);
    	    	
    	boardService.insertReply(info);
    	
    	System.out.println("나중 info인자값 확인 : " + info.toStringMultiline());
    	
    	
	   return info;
    }

    //엑셀 다운로드
    @RequestMapping(value = "/board/downloadExcelFile", method = RequestMethod.POST)
    public String downloadExcelFile(@ModelAttribute("excelDown") BoardVO info,Model model) {
		System.out.println("downloadExcelFile 확인");
        System.out.println("인자값 확인 : " + info.toStringMultiline());
        List<BoardVO> resultlist = boardService.boardList(info);
        
        SXSSFWorkbook workbook = service.excelFileDownloadProcess(resultlist);
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", "게시판 리스트");
        
        return "excelDownloadView";
    }
    
    

}
