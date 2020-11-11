package com.board.controller;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.board.service.BoardService;
import com.board.vo.BoardVO;
import com.common.service.FileMngService;
import com.common.util.Const;
import com.common.util.GetSession;
import com.common.vo.FileVO;
 
@Controller
public class WriteController {
 
    @Resource(name="boardService")
    private BoardService boardService;
    
    @Resource(name="fileMngService")
    private FileMngService fileMngService;
    
    //글쓰기 화면가기
    @RequestMapping(value="/board/boardWrite", method=RequestMethod.GET)
    public ModelAndView wirteView(@ModelAttribute("writeRequest") BoardVO vo){
    	ModelAndView view = new ModelAndView();
    	System.out.println("GET 인가");
        view.setViewName("board/Board_Write.view");
        
        return view;
    }
    
    //글쓰기
    @ResponseBody //리턴되는 값은 View 를 통해서 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
    @RequestMapping(value="/board/boardWrite", method=RequestMethod.POST)
	public int boardWrite(@RequestParam("files") List<MultipartFile> fileList,
			@RequestParam("id") String id,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			HttpServletRequest request) throws IOException{
		System.out.println("들어오긴해야하는데2");
		System.out.println("id 확인 : " + id);
		System.out.println("title 확인 : " + title);
		System.out.println("content 확인 : " + content);
		
		BoardVO vo = new BoardVO();
		
		String content2 = content.replace("\n", "<br/>");
		
        // 자신의 IP 출력
        InetAddress local = InetAddress.getLocalHost();
        String ip = local.getHostAddress();
        
        vo.setId(id);
        vo.setTitle(title);
        vo.setContent(content2);
        vo.setIp(ip);
		
        boardService.insertBoard(vo);
        
        String uploadDir =this.getClass().getResource("").getPath();
        uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"daramzi/src/main/webapp/resources/uploadImage";
        
    	String folderPath = "";
    	
    	folderPath = id + "/board";
    	
    	uploadDir = uploadDir + "/" + folderPath;
        
        long sizeSum = 0;
        int i =0;
        
        System.out.println("파일리스트 확인 : " + fileList.size());
        for(MultipartFile f : fileList){
        	
        	String uuid = UUID.randomUUID().toString();
        	uuid = uuid.replace("-", "");
        	
        	// check for Unix-style path
    		int pos = f.getOriginalFilename().lastIndexOf("/");
    		String getOrgFileName = "";
    		if (pos == -1) {
    			// check for Windows-style path
    			pos = f.getOriginalFilename().lastIndexOf("\\");
    		}
    		if (pos != -1)  {
    			// any sort of path separator found
    			getOrgFileName= f.getOriginalFilename().substring(pos + 1);
    		}
    		
            //확장자 검사
            if(!isValidExtension(getOrgFileName)){
                return Const.RESULT_UNACCEPTED_EXTENSION;
            }

            
            //용량 검사
            sizeSum += f.getSize();
            if(sizeSum >= Const.LIMIT_SIZE) {
                return Const.RESULT_EXCEED_SIZE;
            }

        	
        	// 파일이 실제 저장될때 고유의 값으로 저장 되도록 random 값으로 파일명 설정
        	String savedName = uuid + "_" + getOrgFileName;
        	
        	File target = new File(uploadDir,savedName);
        	FileCopyUtils.copy(f.getBytes(), target);
        	
        	
            String fileExtension = FilenameUtils.getExtension(f.getOriginalFilename()); // 확장자
            String fileName = FilenameUtils.getBaseName(f.getOriginalFilename());	// 파일명
        	
            
            FileVO sendVO = new FileVO();
            
        	sendVO.setFileName(fileName);
        	sendVO.setFileExtension(fileExtension);
        	sendVO.setFileRealName(savedName);
        	sendVO.setFileSize(f.getSize());
        	sendVO.setCategoryCode("board");
        	sendVO.setFolderPath(folderPath);
        	sendVO.setFileSeq(i);
        	sendVO.setBoardIdx(vo.getBoardIdx()); // auto increment 값을 쿼리에서 설정으로 받아올수 있음
        	sendVO.setId(id);
        	i++;
        	fileMngService.fileupload(sendVO);

        }
        
        return Const.RESULT_SUCCESS;
    }
    
    //required above jdk 1.7 - switch(String)
	private boolean isValidExtension(String originalName) {
		String originalNameExtension = originalName.substring(originalName.lastIndexOf(".") + 1);
		switch (originalNameExtension) {
		case "jpg":
		case "png":
		case "gif":
			return true;
		}
		return false;
	}
    
    //수정화면가기
    @RequestMapping(value="/board/boardUpdate", method=RequestMethod.POST)
    public ModelAndView boardUpdate(@ModelAttribute("modContent") BoardVO searchInfo,HttpServletRequest request)
    {
        ModelAndView view = new ModelAndView();
        int boardIdx = searchInfo.getBoardIdx();
        
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	searchInfo.setId(id);
        
        BoardVO modContent = boardService.findByIdx(searchInfo);
        List<FileVO> resultlist = boardService.fileList(boardIdx);
        
        view.addObject("search",searchInfo);
        view.addObject("modContent",modContent);
        view.addObject("resultlist", resultlist);
        
        view.setViewName("board/Board_Update.view");
        return view; 
    }
    
    
    //글수정
    @RequestMapping(value="/board/boardUpdateComp", method=RequestMethod.POST)
    public ModelAndView boardUpdateComp(@ModelAttribute("modComp") BoardVO modComp,HttpServletRequest request, HttpServletResponse response)
    {
    	ModelAndView view = new ModelAndView();
        int result = boardService.updateBoard(modComp);
        if(result == 1){
        	
            view.addObject("search",modComp);
            view.setViewName("forward:/board/boardList");
        }else{
            view.addObject("modContent",modComp);
            view.setViewName("board/Board_Update.view");
        }return view;
    }
    
    //글삭제
    @RequestMapping(value="/board/boardDelete", method=RequestMethod.POST)
    public ModelAndView boardDelete(@ModelAttribute("modContent") BoardVO boardVO)
    {
    	ModelAndView view = new ModelAndView();
    	
    	List<FileVO> fileList = boardService.fileList(boardVO.getBoardIdx());

    	for(FileVO f : fileList){
    		fileDel(f.getFileIdx());
    	}
    	
        int result = boardService.deleteBoard(boardVO);
        if(result == 1){
            view.addObject("search",boardVO);
            view.setViewName("forward:/board/boardList");
        }else{
            view.addObject("modContent",boardVO);
            view.setViewName("board/Board_View.view");
        }return view;
    }
    
    //파일 삭제 수행
    @RequestMapping(value="/board/fileDel", method= RequestMethod.POST)
    public ResponseEntity<String> fileDel(String fileIdx){
    	FileVO fileSelct= fileMngService.selectFile(fileIdx);
		
        String uploadDir =this.getClass().getResource("").getPath();

        uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"daramzi/src/main/webapp/resources/uploadImage";
    		
		File f = new File(uploadDir +"/" + fileSelct.getFolderPath() + "/" + fileSelct.getFileRealName()); // 파일 객체생성
    	if( f.exists()) {// 파일이 존재하면 파일을 삭제한다. 
    		fileMngService.deleteFile(fileIdx);
    		f.delete(); 
    	}
    	
	   return new ResponseEntity<String>("deleted",HttpStatus.OK);
    }
}

