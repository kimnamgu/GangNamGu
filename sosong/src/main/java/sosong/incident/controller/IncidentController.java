package sosong.incident.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import sosong.common.common.CommandMap;
import sosong.incident.service.IncidentService;

@Controller
public class IncidentController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="incidentService")
	private IncidentService incidentService;
	
	@RequestMapping(value="/incident/allIncidentList.do")
    public ModelAndView allIncidentList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/allIncidentList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/testIncidentList.do")
    public ModelAndView testIncidentList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/testIncidentList");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/printAllIncidentList.do")
    public ModelAndView printAllIncidentList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/printAllIncidentList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectAllIncidentList.do")
    public ModelAndView selectAllIncidentList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectAllIncidentList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/incident/excelAllIncidentList.do")
    public ModelAndView excelAllIncidentList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelAllIncidentList");
    	
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectAllIncidentList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/incident/allSimList.do")
    public ModelAndView allSimList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/allSimList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectAllSimList.do")
    public ModelAndView selectAllSimList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectAllSimList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/excelAllSimList.do")
    public ModelAndView excelAllSimList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelAllSimList");
    	
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectAllSimList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/crimIncidentList.do")
    public ModelAndView crimIncidentList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/crimIncidentList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectCrimIncidentList.do")
    public ModelAndView selectCrimIncidentList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectCrimIncidentList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/excelCrimIncidentList.do")
    public ModelAndView excelCrimIncidentList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelCrimIncidentList");
    	
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectCrimIncidentList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/incident/indIncidentList.do")
    public ModelAndView indIncidentList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/indIncidentList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectIndIncidentList.do")
    public ModelAndView selectIndIncidentList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectIndIncidentList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	
	
	@RequestMapping(value="/incident/incidentBasicDetail.do")
	public ModelAndView incidentBasicDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/incidentBasicDetail");
		
		Map<String,Object> map = incidentService.incidentBasicDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/indTrialDetail.do")
	public ModelAndView indTrialDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/indTrialDetail");
		
		Map<String,Object> map = incidentService.indTrialDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/crimListDetail.do")
	public ModelAndView crimListDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/crimListDetail");
		
		Map<String,Object> map = incidentService.crimListDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/incident/thirdTrial3Detail.do")
	public ModelAndView thirdTrial3Detail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/thirdTrial3Detail");
		
		Map<String,Object> map = incidentService.indTrialDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/incident/incidentStatisticsList1.do")
    public ModelAndView incidentStatisticsList1(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/incidentStatisticsList1");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/printIncidentStatisticsList1.do")
    public ModelAndView printIncidentStatisticsList1(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/printIncidentStatisticsList1");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/selectIncidentStatisticsList1.do")
    public ModelAndView selectIncidentStatisticsList1(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList1(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/excelIncidentStatistics1.do")
    public ModelAndView excelIncidentStatistics1(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelIncidentStatistics1");
		
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList1(commandMap.getMap());
    	mv.addObject("list", list);
        	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/incidentStatisticsList2.do")
    public ModelAndView incidentStatisticsList2(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/incidentStatisticsList2");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectIncidentStatisticsList2.do")
    public ModelAndView selectIncidentStatisticsList2(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList2(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/excelIncidentStatistics2.do")
    public ModelAndView excelIncidentStatistics2(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelIncidentStatistics2");
		
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList2(commandMap.getMap());
    	mv.addObject("list", list);
        	
    	return mv;
    }
	
	@RequestMapping(value="/incident/incidentStatisticsList3.do")
    public ModelAndView incidentStatisticsList3(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/incidentStatisticsList3");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectIncidentStatisticsList3.do")
    public ModelAndView selectIncidentStatisticsList3(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList3(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/excelIncidentStatistics3.do")
    public ModelAndView excelIncidentStatistics3(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelIncidentStatistics3");
		
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList3(commandMap.getMap());
    	mv.addObject("list", list);
        	
    	return mv;
    }
	
	@RequestMapping(value="/incident/incidentStatisticsList4.do")
    public ModelAndView incidentStatisticsList4(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/incident/incidentStatisticsList4");
    	
    	return mv;
    }
	
	@RequestMapping(value="/incident/selectIncidentStatisticsList4.do")
    public ModelAndView selectIncidentStatisticsList4(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList4(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/incident/excelIncidentStatistics4.do")
    public ModelAndView excelIncidentStatistics4(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/excelIncidentStatistics4");
		
		commandMap.put("XLS", "1"); //ø¢ºø¿˙¿Â
    	List<Map<String,Object>> list = incidentService.selectIncidentStatisticsList4(commandMap.getMap());
    	mv.addObject("list", list);
        	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/incident/incidentBasicWrite.do")
	public ModelAndView incidentBasicWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/incidentBasicWrite");
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/insertIncidentBasic.do")
	public ModelAndView insertIncidentBasic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/allIncidentList.do");
		
		incidentService.insertIncidentBasic(commandMap.getMap(), request);
		
		return mv;
	}
	

	
	@RequestMapping(value="/incident/indTrialWrite.do")
	public ModelAndView indTrialWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/indTrialWrite");
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/crimListWrite.do")
	public ModelAndView crimListWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/crimListWrite");
		
		return mv;
	}
	
	@RequestMapping(value="/incident/insertIndTrial.do")
	public ModelAndView insertIndTrial(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/indIncidentList.do");
		
		incidentService.insertIndTrial(commandMap.getMap(), request);
		
		mv.addObject("MID", commandMap.get("MAST_ID"));		
		mv.addObject("ICDNT_TRIAL_NO", commandMap.get("ICDNT_TRIAL_NO"));
		mv.addObject("INCDNT_GB", commandMap.get("INCDNT_GB"));
		/*
		log.debug("mid=" + "[" + commandMap.get("MAST_ID") + "]");
		log.debug("tri_no=" + "[" + commandMap.get("ICDNT_TRIAL_NO") + "]");
		*/
		return mv;
	}
	
	
	
	@RequestMapping(value="/incident/insertCrimList.do")
	public ModelAndView insertCrimList(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/crimIncidentList.do");
		int rtn= 0;
		
		rtn = incidentService.insertCrimList(commandMap.getMap(), request);
		
		mv.addObject("MID", commandMap.get("MAST_ID"));		
		mv.addObject("ICDNT_TRIAL_NO", commandMap.get("ICDNT_TRIAL_NO"));
		mv.addObject("INCDNT_GB", commandMap.get("INCDNT_GB"));
		
		log.debug("return val =" + "[" + rtn + "]");
		/*
		log.debug("mid=" + "[" + commandMap.get("MAST_ID") + "]");
		log.debug("tri_no=" + "[" + commandMap.get("ICDNT_TRIAL_NO") + "]");
		*/
		return mv;
	}
	
	@RequestMapping(value="/incident/thirdTrial3Write.do")
	public ModelAndView thirdTrial3Write(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/thirdTrial3Write");
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/incidentBasicUpdate.do")
	public ModelAndView incidentBasicUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/incidentBasicUpdate");
		
		Map<String,Object> map = incidentService.incidentBasicDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/updateIncidentBasic.do")
	public ModelAndView updateIncidentBasic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/incidentBasicDetail.do");
		
		incidentService.updateIncidentBasic(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("ID"));	
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/deleteIncidentBasic.do")
	public ModelAndView deleteIncidentBasic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/allIncidentList.do");
		
		incidentService.deleteIncidentBasic(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/incident/indTrialUpdate.do")
	public ModelAndView indTrialUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/indTrialUpdate");
		
		Map<String,Object> map = incidentService.indTrialDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/updateIndTrial.do")
	public ModelAndView updateIndTrial(CommandMap commandMap, HttpServletRequest request) throws Exception{
		String url_str = null;
		
		if("33".equals(commandMap.get("ICDNT_TRIAL_NO"))) //º“º€∫ÒøÎ»Æ¡§ ≈«¿Ã∏È
			url_str = "redirect:/incident/thirdTrial3Detail.do";
		else
			url_str = "redirect:/incident/indTrialDetail.do";
			
		ModelAndView mv = new ModelAndView(url_str);
		
		incidentService.updateIndTrial(commandMap.getMap(), request);
		mv.addObject("ICDNT_TRIAL_NO", commandMap.get("ICDNT_TRIAL_NO"));	
		mv.addObject("MID", commandMap.get("MID"));
		mv.addObject("ICDNT_NO", commandMap.get("ICDNT_NO"));
		mv.addObject("INCDNT_GB", commandMap.get("INCDNT_GB"));
		
		if("12".equals(commandMap.get("ICDNT_TRIAL_NO")) || "13".equals(commandMap.get("ICDNT_TRIAL_NO"))) //Ω≈√ªªÁ∞« , Ω≈√ªªÁ∞« (¡ÔΩ√«◊∞Ì)¿Ã∏È
			mv.addObject("TRNO_GR_CD", "16"); //Ω≈√ªªÁ∞«
		else
			mv.addObject("TRNO_GR_CD", "15");
	
		return mv;
	}
	
	
	@RequestMapping(value="/incident/deleteIndTrial.do")
	public ModelAndView deleteIndTrial(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/indIncidentList.do");
		
		incidentService.deleteIndTrial(commandMap.getMap(), request);
		
		mv.addObject("MID", commandMap.get("MID"));		
		mv.addObject("ICDNT_TRIAL_NO", commandMap.get("ICDNT_TRIAL_NO"));
		mv.addObject("INCDNT_GB", commandMap.get("INCDNT_GB"));
		
		/*
		log.debug("mid=" + "[" + commandMap.get("MAST_ID") + "]");
		log.debug("tri_no=" + "[" + commandMap.get("ICDNT_TRIAL_NO") + "]");
		*/
		
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/incident/thirdTrial3Update.do")
	public ModelAndView thirdTrial3Update(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/thirdTrial3Update");
		
		Map<String,Object> map = incidentService.indTrialDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/crimListUpdate.do")
	public ModelAndView crimListUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/incident/crimListUpdate");
		
		Map<String,Object> map = incidentService.crimListDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/incident/updateCrimList.do")
	public ModelAndView updateCrimList(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/crimListDetail.do");
		
		incidentService.updateCrimList(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("ID"));	
		
		return mv;
	}
	
	@RequestMapping(value="/incident/deleteCrimList.do")
	public ModelAndView deleteCrimList(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/incident/crimIncidentList.do");
		
		incidentService.deleteCrimList(commandMap.getMap(), request);
		
		
		return mv;
	}
}
