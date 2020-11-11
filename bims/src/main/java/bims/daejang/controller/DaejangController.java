package bims.daejang.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bims.common.common.CommandMap;
import bims.daejang.service.DaejangService;

@Controller
public class DaejangController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	@RequestMapping(value="/daejang/mainSaupList.do")
    public ModelAndView mainSaupList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/mainSaupList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectMainSaupList.do")
    public ModelAndView selectMainSaupList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectMainSaupList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/mainSaupSubList.do")
    public ModelAndView mainSaupSubList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/mainSaupSubList");
		
		Map<String,Object> map = daejangService.mainSaupDetail(commandMap.getMap());
		mv.addObject("mapM", map.get("map"));
		
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectMainSaupSubList.do")
    public ModelAndView selectMainSaupSubList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectMainSaupSubList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/daejang/mainSaupDetail.do")
	public ModelAndView mainSaupDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/mainSaupDetail");
		
		Map<String,Object> map = daejangService.mainSaupDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		//mv.addObject("list", map.get("list"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/mainSaupUpdate.do")
	public ModelAndView mainSaupUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/mainSaupUpdate");
		
		Map<String,Object> map = daejangService.mainSaupBasicDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/mainSaupWrite.do")
	public ModelAndView mainSaupWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/mainSaupWrite");
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/mainSaupSubWrite.do")
	public ModelAndView mainSaupSubWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/mainSaupSubWrite");
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/insertMainSaup.do")
	public ModelAndView insertMainSaup(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/mainSaupList.do?year=" + commandMap.get("YEAR") + "&sgb=" + commandMap.get("SGB"));
		
		daejangService.insertMainSaup(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/insertMainSaupSub.do")
	public ModelAndView insertMainSaupSub(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/mainSaupSubList.do?year=" + commandMap.get("YEAR") + "&sgb=" + commandMap.get("SGB"));
		
		daejangService.insertMainSaupSub(commandMap.getMap(), request);
		
		mv.addObject("MAST_ID", commandMap.get("MAST_ID"));
		mv.addObject("SAUP_DEPT_CD", commandMap.get("WRITE_DEPT"));
		mv.addObject("ID", commandMap.get("MAST_ID"));
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/mainSaupSubUpdate.do")
	public ModelAndView mainSaupSubUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/mainSaupSubUpdate");
		
		Map<String,Object> map = daejangService.mainSaupSubDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/updateMainSaup.do")
	public ModelAndView updateMainSaup(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/mainSaupSubList.do?year=" + commandMap.get("YEAR") + "&sgb=" + commandMap.get("SGB"));
		
		daejangService.updateMainSaup(commandMap.getMap(), request);
		
		mv.addObject("MAST_ID", commandMap.get("SID"));
		mv.addObject("SAUP_DEPT_CD", commandMap.get("WRITE_DEPT"));
		mv.addObject("ID", commandMap.get("SID"));
		

		return mv;
	}
	
	

	@RequestMapping(value="/daejang/updateMainSaupSub.do")
	public ModelAndView updateMainSaupSub(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/mainSaupSubList.do?year=" + commandMap.get("YEAR") + "&sgb=" + commandMap.get("SGB"));
		
		daejangService.updateMainSaupSub(commandMap.getMap(), request);
		
		mv.addObject("MAST_ID", commandMap.get("MAST_ID"));
		mv.addObject("SAUP_DEPT_CD", commandMap.get("WRITE_DEPT"));
		mv.addObject("ID", commandMap.get("MAST_ID"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/deleteMainSaup.do")
	public ModelAndView deleteMainSaup(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/mainSaupList.do?year=" + commandMap.get("YEAR") + "&sgb=" + commandMap.get("SGB"));
		
		daejangService.deleteMainSaup(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/deleteMainSaupSub.do")
	public ModelAndView deleteMainSaupSub(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/mainSaupSubList.do?year=" + commandMap.get("YEAR") + "&sgb=" + commandMap.get("SGB"));
		
		daejangService.deleteMainSaupSub(commandMap.getMap(), request);
		
		mv.addObject("MAST_ID", commandMap.get("MAST_ID"));
		mv.addObject("SAUP_DEPT_CD", commandMap.get("WRITE_DEPT"));
		mv.addObject("ID", commandMap.get("MAST_ID"));
	
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/mSaupList.do")
	public ModelAndView mSaupList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.mSaupList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    
    	return mv;
    }
	
}
