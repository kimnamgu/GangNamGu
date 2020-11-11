package com.admin.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.controller.HomeController;

@Controller
public class Administrator {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
    //글쓰기 화면가기
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String wirteView(Locale locale, Model model){
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
        
        return "admin_index.Aview";
    }
	
}
