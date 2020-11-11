package com.user.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.common.exception.AlreadyExistingEmailException;
import com.common.exception.AlreadyExistingIdException;
import com.common.util.RegisterRequest;
import com.common.vo.FolderVO;
import com.user.dao.UserDAO;
import com.user.service.UserService;

@Controller
public class UserController {
    @RequestMapping("/register/step1")
    public String step1() throws Exception {
        return "user/register/step1.part";
    }
     
    @RequestMapping("/register/step2")
    public ModelAndView step2(@RequestParam(value="agree", defaultValue="false") Boolean agree) throws Exception {
        if(!agree) {
            ModelAndView mv = new ModelAndView("user/register/step1.page");
            return mv;
        }
        ModelAndView mv = new ModelAndView("user/register/step2.part");
        mv.addObject("registerRequest", new RegisterRequest());
        return mv;
    }
    
    @Resource(name="userService")
    private UserService userSer;
    
    @Resource(name="userDAO")
    private UserDAO userDAO;
 
    @RequestMapping("/register/step3")
    public ModelAndView step3(@Valid RegisterRequest regReq, BindingResult bindingResult) throws Exception{
        
        //@Valid 검증        
        if(bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("user/register/step2.part");
            return mv;
        }
 
        //비밀번호 확인
        boolean check = regReq.isPwEqualToCheckPw();
        if(!check) {
            bindingResult.rejectValue("checkPw", "noMatch", "비밀번호를 확인해주세요.");
            ModelAndView mv = new ModelAndView("user/register/step2.part");
            return mv;
        }
        
        try {
            userSer.register(regReq);
        } catch (AlreadyExistingEmailException e) {
            bindingResult.rejectValue("email", "duplicate", "이미 가입된 이메일입니다.");
            ModelAndView mv = new ModelAndView("user/register/step2.part");
            return mv;
        } catch (AlreadyExistingIdException e) {
            bindingResult.rejectValue("id", "duplicate", "이미 가입된 아이디입니다.");
            ModelAndView mv = new ModelAndView("user/register/step2.part");
            return mv;
        }
        
        //회원가입 완료시 아이디로 파일 폴더 생성
        
        
		String uploadDir = this.getClass().getResource("").getPath();

		uploadDir = uploadDir.substring(1, uploadDir.indexOf(".metadata"))
				+ "daramzi/src/main/webapp/resources/uploadImage";

		String filePath = uploadDir + "/" + regReq.getId() + "/";

		File fPath = new File(filePath); // 경로생성
		File fPath_board = new File(filePath + "/board"); // 게시판용 폴더생성
		File fPath_imageboard = new File(filePath + "/imageBoard"); // 사진첨용 폴더생성
		
		 
		FolderVO folderVo = new FolderVO();
		

		if (!fPath.exists()) { // 아이디로 기본 폴더 생성
			fPath.mkdirs(); // 상위 디렉토리가 존재하지 않으면 상위디렉토리부터 생성.
			
			folderVo.setId(regReq.getId());
			folderVo.setUpperFolder("root");
			folderVo.setFolderDepth("1"); // 아이디는 1번 폴더
			folderVo.setFolderName(regReq.getId());
			
			userSer.mkDir(folderVo);
		}
		
		if (!fPath_board.exists()) { // 기본 board 폴더 생성
			fPath_board.mkdirs(); // 상위 디렉토리가 존재하지 않으면 상위디렉토리부터 생성.
			
			folderVo.setId(regReq.getId());
			folderVo.setUpperFolder(regReq.getId());
			folderVo.setFolderDepth("2"); // 아이디는 1번 폴더
			folderVo.setFolderName("board");
			
			userSer.mkDir(folderVo);
		}
		
		if (!fPath_imageboard.exists()) { // 기본 imageboard 폴더 생성
			fPath_imageboard.mkdirs(); // 상위 디렉토리가 존재하지 않으면 상위디렉토리부터 생성.
			
			folderVo.setId(regReq.getId());
			folderVo.setUpperFolder(regReq.getId());
			folderVo.setFolderDepth("2"); // 아이디는 1번 폴더
			folderVo.setFolderName("imageBoard");
			
			userSer.mkDir(folderVo);
		}
		
 
        ModelAndView mv = new ModelAndView("user/register/step3.part");
        return mv;
    }
}


