package com.Amember.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Amember.service.AmemberService;
import com.Amember.vo.AmemberVO;
import com.board.vo.PageVO;
 
@Controller
public class AmemberController {
    @Resource(name="memberService")
    private AmemberService memberService;
    
    //게시판 글 목록
    @RequestMapping(value="/admin/memberList")
    public ModelAndView memberList(@ModelAttribute("search") AmemberVO info,ModelMap model)
    {
    	System.out.println("인자값 확인 memberList: " + info.toStringMultiline());
        ModelAndView view = new ModelAndView();
        int totalCount = memberService.memberListCnt(info);
        PageVO page = new PageVO();
        page.setDisplayRow(info.getListNum());       
        page.setTotalCount(totalCount);
        page.setPage(info.getPage());
        
        List<AmemberVO> resultlist = memberService.memberList(info);
        
        view.addObject("resultlist", resultlist);
        view.addObject("page",page);
        
        //조건값 유지를 위한 model
        view.addObject("search",info);
        view.setViewName("member/Member_List.Aview");
        return view;
    }
    
    
    

}
