package com.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.exception.IdPasswordNotMatchingException;
import com.common.util.AuthInfo;
import com.common.util.LoginCommand;
import com.user.service.UserService;

@Controller
public class LoginController {
 
    @Resource(name="userService")
    private UserService userSer;
 
    @RequestMapping(value="/login")
    public ModelAndView loginSuccess(@Valid LoginCommand loginCommand, BindingResult bindingResult,
                                    HttpSession session, HttpServletResponse response) throws Exception {
    	//폼 변조 공격을 막기 위한 검사
        if(loginCommand.getId() != null && loginCommand.getPw() != null) {
	    	if(loginCommand.getId().indexOf("\'") > 0 || loginCommand.getPw().indexOf("\'") > 0 ){
	            ModelAndView mv = new ModelAndView("user/login/loginForm.part");
	            return mv;
	    	}
        }


    	if(bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("user/login/loginForm.part");
            return mv;
        }
        
        try {
            AuthInfo authInfo = userSer.loginAuth(loginCommand);
            session.setAttribute("authInfo", authInfo);
            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getId());
            rememberCookie.setPath("/");
            
            if(loginCommand.isRememberId()) {
                rememberCookie.setMaxAge(60*60*24*7);
            } else {
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);
        } catch (IdPasswordNotMatchingException e) {
            bindingResult.rejectValue("pw", "notMatch", "아이디와 비밀번호가 맞지않습니다.");
            ModelAndView mv = new ModelAndView("user/login/loginForm.part");
            return mv;
        }
        
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }
    
    @RequestMapping(value="/loginConfirm")
	public @ResponseBody String loginConfirm(@RequestParam("loginID") String loginID,
			@RequestParam("loginPW") String loginPW, HttpSession session, HttpServletResponse response)
			throws Exception {
    	LoginCommand loginCommand = new LoginCommand();
    	
    	loginCommand.setId(loginID);
    	loginCommand.setPw(loginPW);
    	
    	//폼 변조 공격을 막기 위한 검사
        if(loginCommand.getId() != null && loginCommand.getPw() != null) {
	    	if(loginCommand.getId().indexOf("\'") > 0 || loginCommand.getPw().indexOf("\'") > 0 ){
	            return "loginFail";
	    	}
        }


        try {
            AuthInfo authInfo = userSer.loginAuth(loginCommand);
            session.setAttribute("authInfo", authInfo);
            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getId());
            rememberCookie.setPath("/");
            
            if(loginCommand.isRememberId()) {
                rememberCookie.setMaxAge(60*60*24*7);
            } else {
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);
        } catch (IdPasswordNotMatchingException e) {
            return "loginFail";
        }
        
        return "loginSuccess";
    }
    
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }
}
