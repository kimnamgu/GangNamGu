package scms.daejang.controller;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import scms.common.common.CommandMap;
import scms.common.util.CommonUtils;
import scms.common.util.Nvl;
import scms.daejang.dao.DaejangDAO;
import scms.daejang.service.DaejangService;

@Controller
public class DaejangController {
	Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;
	
	
	@RequestMapping(value="/daejang/prvCnrtCompList.do")
    public ModelAndView prvCnrtCompList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCompList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtCompList.do")
    public ModelAndView selectPrvCnrtCompList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtCompList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtCompInsList.do")
    public ModelAndView prvCnrtCompInsList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCompInsList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtCompInsList.do")
    public ModelAndView selectPrvCnrtCompInsList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtCompInsList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtContractList.do")
    public ModelAndView prvCnrtContractList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtContractList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtContractList.do")
    public ModelAndView selectPrvCnrtContractList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtContractList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtCompRecordList.do")
    public ModelAndView prvCnrtCompRecordList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCompRecordList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtDutyList.do")
    public ModelAndView selectPrvCnrtDutyList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtDutyList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/selectPrvCnrtExpertList.do")
    public ModelAndView selectPrvCnrtExpertList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtExpertList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtReasonList.do")
    public ModelAndView prvCnrtReasonList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtReasonList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtReasonList.do")
    public ModelAndView selectPrvCnrtReasonList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtReasonList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtEvalList.do")
    public ModelAndView serviceContractEvalList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtEvalList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtEvalList.do")
    public ModelAndView selectPrvCnrtEvalList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtEvalList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/daejang/prvCnrtPlanList.do")
    public ModelAndView prvCnrtPlanList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtPlanList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtPlanList.do")
    public ModelAndView selectPrvCnrtPlanList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtPlanList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtAcceptList.do")
    public ModelAndView prvCnrtAcceptList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtAcceptList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtAcceptList.do")
    public ModelAndView selectPrvCnrtAcceptList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtAcceptList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	@RequestMapping(value="/daejang/attachFileList.do")
    public ModelAndView attachFileList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.attachFileList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	
	@RequestMapping(value="/daejang/prvCnrtStatistics1.do")
    public ModelAndView prvCnrtStatistics1(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtStatistics1");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtStatistics1.do")
    public ModelAndView selectPrvCnrtStatistics1(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtStatistics1(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/prvCnrtStatistics2.do")
    public ModelAndView prvCnrtStatistics2(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtStatistics2");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/selectPrvCnrtStatistics2.do")
    public ModelAndView selectPrvCnrtStatistics2(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtStatistics2(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtStatistics1List.do")
    public ModelAndView selectPrvCnrtStatistics1List(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtStatistics1List(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectCountList.do")
	public ModelAndView selectCountList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
	    	
	    List<Map<String,Object>> list = daejangService.selectCountList(commandMap.getMap());
	    mv.addObject("list", list);
	    
	    if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
	    
	    return mv;
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtCompWrite.do")
	public ModelAndView prvCnrtCompWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCompWrite");
		
	   	return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtContractWrite.do")
	public ModelAndView prvCnrtContractWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtContractWrite");
		
	   	return mv;
	}
	
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtDutyWrite.do")
	public ModelAndView prvCnrtDutyWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtDutyWrite");
		
	   	return mv;
	}
	
	@RequestMapping(value="/daejang/prvCnrtExpertWrite.do")
	public ModelAndView prvCnrtExpertWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtExpertWrite");
		
	   	return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/prvCnrtReasonWrite.do")
	public ModelAndView prvCnrtReasonWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtReasonWrite");
		
	   	return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/scEvalWrite.do")
	public ModelAndView scEvalWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/scEvalWrite");
		
	   	return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtPlanWrite.do")
	public ModelAndView scPlanWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtPlanWrite");
		
	   	return mv;
	}
		
	
	@RequestMapping(value="/daejang/prvCnrtCompInfo.do")
	public ModelAndView prvCnrtCompInfo(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCompInfo");
		
		Map<String,Object> map = daejangService.prvCnrtCompInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("list6", map.get("list6"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtTotalCompInfo.do")
	public ModelAndView prvCnrtTotalCompInfo(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtTotalCompInfo");
		Map<String, Object> rmap1 = new HashMap<String,Object>();
		Map<String,Object> map = daejangService.prvCnrtCompInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("list6", map.get("list6"));
		rmap1 = (HashMap<String,Object>) map.get("map");
		commandMap.put("DATA_GB", "1");
		commandMap.put("COMP_ID", rmap1.get("ID"));
		List<Map<String,Object>> list1 = daejangService.selectPrvCnrtDutyList(commandMap.getMap());
    	mv.addObject("list1", list1);
    	commandMap.put("COMP_ID", rmap1.get("ID"));
    	List<Map<String,Object>> list2 = daejangService.selectPrvCnrtExpertList(commandMap.getMap());
    	mv.addObject("list2", list2);
    	commandMap.put("DATA_GB", "2");
    	commandMap.put("COMP_ID", rmap1.get("ID"));
    	List<Map<String,Object>> list3 = daejangService.selectPrvCnrtReasonList(commandMap.getMap());
    	mv.addObject("list3", list3);
    	commandMap.put("COMP_ID", rmap1.get("ID"));
    	commandMap.put("CNRT_EVAL", "NOTNULL");
    	List<Map<String,Object>> list4 = daejangService.selectPrvCnrtEvalList(commandMap.getMap());
    	mv.addObject("list4", list4);
		return mv;
	}
	
	@RequestMapping(value="/daejang/prvCnrtTotalCompInfoView.do")
	public ModelAndView prvCnrtTotalCompInfoView(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtTotalCompInfoView");
		Map<String, Object> rmap1 = new HashMap<String,Object>();
		Map<String,Object> map = daejangService.prvCnrtCompInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("list6", map.get("list6"));
		rmap1 = (HashMap<String,Object>) map.get("map");
		commandMap.put("DATA_GB", "1");
		commandMap.put("COMP_ID", rmap1.get("ID"));
		List<Map<String,Object>> list1 = daejangService.selectPrvCnrtDutyList(commandMap.getMap());
    	mv.addObject("list1", list1);
    	commandMap.put("COMP_ID", rmap1.get("ID"));
    	List<Map<String,Object>> list2 = daejangService.selectPrvCnrtExpertList(commandMap.getMap());
    	mv.addObject("list2", list2);
    	commandMap.put("DATA_GB", "2");
    	commandMap.put("COMP_ID", rmap1.get("ID"));
    	List<Map<String,Object>> list3 = daejangService.selectPrvCnrtReasonList(commandMap.getMap());
    	mv.addObject("list3", list3);
    	commandMap.put("COMP_ID", rmap1.get("ID"));
    	commandMap.put("CNRT_EVAL", "NOTNULL");
    	List<Map<String,Object>> list4 = daejangService.selectPrvCnrtEvalList(commandMap.getMap());
    	mv.addObject("list4", list4);
		return mv;
	}
	
	
	

	@RequestMapping(value="/daejang/insertPrvCnrtComp.do")
	public ModelAndView insertPrvCnrtComp(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompInfo.do");
		String spath = null;
		String flag = null;
		
		daejangService.insertPrvCnrtComp(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("SID"));
		mv.addObject("PARENT_ID", commandMap.get("SID"));
		mv.addObject("BOARD_GB", commandMap.get("BOARD_GB"));
		
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/daejang/insertPrvCnrtDuty.do")
	public ModelAndView insertPrvCnrtDuty(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.insertPrvCnrtContract(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("COMP_ID"));
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/insertPrvCnrtContract.do")
	public ModelAndView insertPrvCnrtContract(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtContractList.do");
		String spath = null;
		String flag = null;
		
		daejangService.insertPrvCnrtContract(commandMap.getMap(), request);
					
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/insertPrvCnrtReason.do")
	public ModelAndView insertPrvCnrtReason(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtReasonList.do");
		String spath = null;
		String flag = null;
		
		daejangService.insertPrvCnrtReason(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("COMP_ID"));
					
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/updatePrvCnrtPlan.do")
	public ModelAndView updatePrvCnrtPlan(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtPlanList.do");

		daejangService.updatePrvCnrtPlan(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/updatePrvCnrtComp.do")
	public ModelAndView updatePrvCnrtComp(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompInsList.do");

		daejangService.updatePrvCnrtComp(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/daejang/updatePrvCnrtDuty.do")
	public ModelAndView updatePrvCnrtDuty(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.updatePrvCnrtDuty(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/updatePrvCnrtExpert.do")
	public ModelAndView updatePrvCnrtExpert(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.updatePrvCnrtExpert(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/updatePrvCnrtEval.do")
	public ModelAndView updatePrvCnrtEval(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtEvalList.do");
		String spath = null;
		String flag = null;
		
		daejangService.updatePrvCnrtEval(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/updatePrvCnrtReason.do")
	public ModelAndView updatePrvCnrtReason(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtReasonList.do");
		String spath = null;
		String flag = null;
		
		log.debug("test commandMap :: " + commandMap.toString());
		commandMap.put("PARENT_ID",commandMap.get("CON_ID"));
		
		
		daejangService.updatePrvCnrtReason(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/deletePrvCnrtPlan.do")
	public ModelAndView deletePrvCnrtPlan(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtPlanList.do");
		String spath = null;
		String flag = null;
		
		daejangService.deletePrvCnrtPlan(commandMap.getMap(), request);		//duty 와 같은 테이블
				
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/deletePrvCnrtComp.do")
	public ModelAndView deletePrvCnrtComp(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompInsList.do");
		String spath = null;
		String flag = null;
		
		daejangService.deletePrvCnrtComp(commandMap.getMap(), request);		//duty 와 같은 테이블
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/deletePrvCnrtDuty.do")
	public ModelAndView deletePrvCnrtDuty(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.deletePrvCnrtDuty(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	@RequestMapping(value="/daejang/deletePrvCnrtExpert.do")
	public ModelAndView deletePrvCnrtExpert(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.deletePrvCnrtExpert(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/deletePrvCnrtReason.do")
	public ModelAndView deletePrvCnrtReason(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtReasonList.do");
		String spath = null;
		String flag = null;
		
		
		daejangService.deletePrvCnrtDuty(commandMap.getMap(), request);		//duty 와 같은 테이블
		mv.addObject("ID", commandMap.get("ID"));			
		return mv;
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/insScOtherWork.do")
	public ModelAndView insScOtherWork(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.insScOtherWork(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("MAST_ID"));
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/insertPrvCnrtExpert.do")
	public ModelAndView insertPrvCnrtExpert(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtCompRecordList.do");
		String spath = null;
		String flag = null;
		
		daejangService.insertPrvCnrtExpert(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("COMP_ID"));
		
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtPlanUpdate.do")
	public ModelAndView prvCnrtPlanUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtPlanUpdate");
		
		Map<String,Object> map = daejangService.prvCnrtPlanInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
				
		return mv;
	}
	
	@RequestMapping(value="/daejang/prvCnrtPlanView.do")
	public ModelAndView prvCnrtPlanView(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtPlanView");
		
		Map<String,Object> map = daejangService.prvCnrtPlanInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
				
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/prvCnrtCompUpdate.do")
	public ModelAndView prvCnrtCompUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCompUpdate");
		
		Map<String,Object> map = daejangService.prvCnrtCompInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("list6", map.get("list6"));		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtDutyUpdate.do")
	public ModelAndView prvCnrtDutyUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtDutyUpdate");
		
		Map<String,Object> map = daejangService.prvCnrtDutyUpdate(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtExpertUpdate.do")
	public ModelAndView prvCnrtExpertUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtExpertUpdate");
		
		Map<String,Object> map = daejangService.prvCnrtExpertUpdate(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
				
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/prvCnrtReasonUpdate.do")
	public ModelAndView prvCnrtReasonUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtReasonUpdate");
		
		Map<String,Object> map = daejangService.prvCnrtEvalUpdate(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/daejang/prvCnrtEvalUpdate.do")
	public ModelAndView prvCnrtEvalUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtEvalUpdate");
		
		Map<String,Object> map = daejangService.prvCnrtEvalUpdate(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/daejang/insertPrvCnrtPlan.do")
	public ModelAndView insertPrvCnrtPlan(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/orderPlanPriorApprovalList.do");
		String spath = null;
		String flag = null;
		
//		daejangService.insertPrvCnrtPlan(commandMap.getMap(), request);
		daejangService.orderPlanPriorApprovalInsert(commandMap.getMap(), request);
		
		
		
		return mv;
	}
	
	//寃ъ���� ���� ��濡���
	@RequestMapping(value="/daejang/upEstFile.do")
	@ResponseBody
	public Map<String, Object> upEstFile(CommandMap commandMap, HttpServletRequest request) throws Exception{
		Map<String, Object> map = daejangService.upEstFile(commandMap.getMap(), request);
		return map;
	}
	
	//寃ъ���� ���� ����
	@RequestMapping(value="/daejang/delEstFile.do")
	@ResponseBody
	public Map<String, Object> delEstFile(CommandMap commandMap) throws Exception{
		Map<String, Object> map = commandMap.getMap();
		daejangDAO.deleteFileList(map);
		
		return map;
	}
	
	
    @RequestMapping(value = "/daejang/excel.do", method = RequestMethod.GET)
	public ModelAndView exceldown() {
    	ModelAndView mv = new ModelAndView("/daejang/excel");
    	
    	return mv;
	}
    
    
   
    
    @RequestMapping(value="/daejang/samplepop.do")
   	public ModelAndView SamplePop(CommandMap commandMap) throws Exception{
   	    	
   		ModelAndView mv = new ModelAndView("/daejang/jusoPopup");
   		
   	   	return mv;
   	}
    
    
    
    
    
    
    @RequestMapping(value="/daejang/chkSaup.do")
	public ModelAndView chkSaup(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.chkSaup(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
     
  
    
    @RequestMapping(value="/daejang/popSaupList.do")
	public ModelAndView popSaupList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.popSaupList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
    
    
    
    @RequestMapping(value = "/daejang/exceldown.do")
    public void excelDown(CommandMap commandMap, HttpServletResponse response) throws Exception {
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtCompList(commandMap.getMap());

        // 워크북 생성
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("업체리스트");

        Row row = null;
        Cell cell = null;
        int rowNo = 0;     
        
        // 테이블 헤더용 스타일
        CellStyle headStyle = wb.createCellStyle();

        // 가는 경계선을 가집니다.
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);

        // 배경색은 노란색입니다.
        headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 데이터는 가운데 정렬합니다.
        headStyle.setAlignment(HorizontalAlignment.CENTER);

        // 데이터용 경계 스타일 테두리만 지정
        CellStyle bodyStyle = wb.createCellStyle();

        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);



        // 헤더 생성
        row = sheet.createRow(rowNo++);
        cell = row.createCell(0);
        cell.setCellStyle(headStyle);
        cell.setCellValue("번호");

        cell = row.createCell(1);
        cell.setCellStyle(headStyle);
        cell.setCellValue("유형");

        cell = row.createCell(2);
        cell.setCellStyle(headStyle);
        cell.setCellValue("세부유형");
                
        cell = row.createCell(3);
        cell.setCellStyle(headStyle);
        cell.setCellValue("사업자번호");
        
        cell = row.createCell(4);
        cell.setCellStyle(headStyle);
        cell.setCellValue("업체명");
        
        cell = row.createCell(5);
        cell.setCellStyle(headStyle);
        cell.setCellValue("대표자");
        
        cell = row.createCell(6);
        cell.setCellStyle(headStyle);
        cell.setCellValue("전화번호");
        
        cell = row.createCell(7);
        cell.setCellStyle(headStyle);
        cell.setCellValue("주소");
        
        cell = row.createCell(8);
        cell.setCellStyle(headStyle);
        cell.setCellValue("자본금");
                
        cell = row.createCell(9);
        cell.setCellStyle(headStyle);
        cell.setCellValue("강남구과업실적");
        
        cell = row.createCell(10);
        cell.setCellStyle(headStyle);
        cell.setCellValue("현장점검");

        // 데이터 부분 생성
        for( int i=0; i < list.size(); i++) {

            row = sheet.createRow(rowNo++);
            cell = row.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("RNUM").toString());
            
            cell = row.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(CommonUtils.outUH1((String) list.get(i).get("COMP_FIELD1_GB").toString()));

            cell = row.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(CommonUtils.outUH2((String) list.get(i).get("COMP_FIELD2_GB").toString()));
                        
            cell = row.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(Nvl.nvlStr((String) list.get(i).get("COMP_SAUP_NO")));
                        
            cell = row.createCell(4);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(Nvl.nvlStr((String) list.get(i).get("COMP_NM")));
            
            cell = row.createCell(5);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(Nvl.nvlStr((String) list.get(i).get("COMP_HEAD_NM")));
            
            cell = row.createCell(6);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(Nvl.nvlStr((String) list.get(i).get("COMP_TEL")));
            
            cell = row.createCell(7);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(Nvl.nvlStr((String) list.get(i).get("COMP_ADDR_ROAD")));
            
            cell = row.createCell(8);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(NumberFormat.getInstance().format(list.get(i).get("COMP_CAPITAL")));            
                        
            cell = row.createCell(9);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("CNT").toString());
            
            cell = row.createCell(10);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("CNT2").toString());
        }
        
        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=DGNS_LIST.xls");

        // 엑셀 출력
        wb.write(response.getOutputStream());
        wb.close();
    }
    
    
    
    
    
    
    
    
    
    @RequestMapping(value="/daejang/prvCnrtBoardList.do")
    public ModelAndView prvCnrtBoardList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/daejang/prvCnrtBoardList");		
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectPrvCnrtBoardList.do")
    public ModelAndView selectPrvCnrtBoardList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtBoardList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/prvCnrtBoardWrite.do")
	public ModelAndView prvCnrtBoardWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtBoardWrite");
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/insertPrvCnrtBoard.do")
	public ModelAndView insertPrvCnrtBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{

		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtBoardList.do");
		
		log.debug("cont ==> [" + commandMap.getMap() + "]" );
		
		daejangService.insertPrvCnrtBoard(commandMap.getMap(), request);
		
		mv.addObject("bdId", commandMap.get("BDID"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtBoardDetail.do")
	public ModelAndView prvCnrtBoardDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtBoardDetail");
		
		Map<String,Object> map = daejangService.selectPrvCnrtBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/prvCnrtBoardUpdate.do")
	public ModelAndView prvCnrtBoardUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtBoardUpdate");
		
		Map<String,Object> map = daejangService.selectPrvCnrtBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/updatePrvCnrtBoard.do")
	public ModelAndView updatePrvCnrtBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtBoardDetail.do");
		
		daejangService.updatePrvCnrtBoard(commandMap.getMap(), request);
		
		mv.addObject("ID", commandMap.get("ID"));
		mv.addObject("PARENT_ID", commandMap.get("ID"));
		mv.addObject("BOARD_GB", "7");
		mv.addObject("BOARD_ID", "1");
		
		log.debug("id ==> " + commandMap.get("ID") );		
		return mv;
	}
	
	@RequestMapping(value="/daejang/deletePrvCnrtBoard.do")
	public ModelAndView deletePrvCnrtBoard(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/prvCnrtBoardList.do");
		
		daejangService.deletePrvCnrtBoard(commandMap.getMap());
		mv.addObject("bdId", commandMap.get("BDID"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/prvCnrtCurrentBuildList.do")
    public ModelAndView prvCnrtCurrentBuildList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/prvCnrtCurrentBuildList");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/selectPrvCnrtCurrentBuildList.do")
    public ModelAndView selectPrvCnrtCurrentBuildList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectPrvCnrtCurrentBuildList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/allListDel.do")
	@ResponseBody
	public Map<String, Object> allListDel(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
	    for(int i=0; i<vals.size() ;i++){
	        //System.out.println("vals(" + i + ") : " + vals.get(i));
	        map.put("selId", vals.get(i));
			daejangDAO.updateDelAllFileList(map);
			daejangDAO.updateDelAllList(map);
	    }
		
		return map;
	}
	
	
	/*만족도 상세*/
    @RequestMapping(value="/daejang/satisfactionPop.do")
	public ModelAndView satisfactionPop(CommandMap commandMap,@RequestParam("ID") String ID) throws Exception{
    	System.out.println("satisfactionPop 탐");
    	System.out.println("ID : " + ID);
    	ModelAndView mv = new ModelAndView("/daejang/satisfactionPop");
    	commandMap.put("COMP_ID", ID);	
    	List<Map<String,Object>> satisfactionList = daejangService.selectSatisfactionList(commandMap.getMap());
    	mv.addObject("satisfactionList", satisfactionList);
    
    	return mv;
    }
	
    
    @RequestMapping(value="/daejang/orderPlanPriorApprovalList.do")
    public ModelAndView orderPlanPriorApprovalList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/orderPlanPriorApprovalList");
    	
    	return mv;
    }
    
    

	@RequestMapping(value="/daejang/orderPlanPriorApprovalListTe.do")
    public ModelAndView orderPlanPriorApprovalListTe(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.orderPlanPriorApprovalList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/prvCnrtPlanUpdateTe.do")
	public ModelAndView orderPlanPriorApprovalUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/orderPlanPriorApprovalList");
		
//		Map<String,Object> map = daejangService.prvCnrtPlanInfoTe(commandMap.getMap());
//		mv.addObject("map", map.get("map"));
//		mv.addObject("list", map.get("list"));
//		
//		commandMap.put("map", map.get("map"));
//		commandMap.put("list", map.get("list"));
//		
		
		HttpServletRequest request = null;
//		if(map.size() > 0){
//		daejangService.insertPrvCnrtPlan(commandMap.getMap(), request);
//		}
		daejangService.updatePrivateSaup(commandMap.getMap());
		
		return mv;
	}
    
	
	
	@RequestMapping(value="/daejang/orderPlanPriorApprovalDetail.do")
	public ModelAndView orderPlanPriorApprovalDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/orderPlanPriorApprovalDetail");
		
		Map<String,Object> map = daejangService.prvCnrtPlanInfoTe(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
				
		return mv;
	}
    
	
	
}