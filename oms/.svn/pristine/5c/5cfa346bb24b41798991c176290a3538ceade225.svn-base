package oms.officework.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import oms.common.common.CommandMap;
import oms.officework.service.OfficeworkService;

@Controller
public class OfficeworkController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="officeworkService")
	private OfficeworkService officeworkService;
	
	@RequestMapping(value="/officework/officeworkRegList.do")
    public ModelAndView officeworkRegList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/officeworkRegList");
		
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectOfficeworkRegList.do")
    public ModelAndView selectOfficeworkRegList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectOfficeworkRegList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/officework/officeworkRegWrite.do")
	public ModelAndView officeworkRegWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/officeworkRegWrite");
	
		return mv;
	}
	
	@RequestMapping(value="/officework/insertofficeworkBasic.do")
	public ModelAndView insertofficeworkBasic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/officeworkRegList.do");
		
		officeworkService.insertofficeworkBasic(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/officework/officeworkBasicDetail.do")
	public ModelAndView officeworkBasicDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/officeworkBasicDetail");
		
		Map<String,Object> map = officeworkService.officeworkBasicDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		
		return mv;
	}
	
	
	@RequestMapping(value="/officework/officeworkBasicUpdate.do")
	public ModelAndView officeworkBasicUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/officeworkBasicUpdate");
		
		Map<String,Object> map = officeworkService.officeworkBasicDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	

	
	@RequestMapping(value="/officework/updateOfficeworkBasic.do")
	public ModelAndView updateOfficeworkBasic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/officeworkBasicDetail.do");
		
		officeworkService.updateOfficeworkBasic(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/deleteOfficeworkBasic.do")
	public ModelAndView deleteOfficeworkBasic(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/officeworkRegList.do");
		
		officeworkService.deleteOfficeworkBasic(commandMap.getMap());
		
		return mv;
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/officework/consignStatusWrite.do")
	public ModelAndView consignStatusWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/consignStatusWrite");
	
		return mv;
	}
	
	
	@RequestMapping(value="/officework/consignStatusList.do")
    public ModelAndView consignStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/consignStatusList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectConsignStatusList.do")
    public ModelAndView selectConsignStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectConsignStatusList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/insertConsignStatus.do")
	public ModelAndView insertConsignStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/consignStatusList.do");
		
		officeworkService.insertConsignStatus(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/consignStatusDetail.do")
	public ModelAndView consignStatusDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/consignStatusDetail");
		
		Map<String,Object> map = officeworkService.consignStatusDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		
		return mv;
	}
	
	
	@RequestMapping(value="/officework/consignStatusUpdate.do")
	public ModelAndView consignStatusUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/consignStatusUpdate");
		
		Map<String,Object> map = officeworkService.consignStatusDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/officework/updateConsignStatus.do")
	public ModelAndView updateConsignStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/consignStatusDetail.do");
		
		officeworkService.updateConsignStatus(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));
		mv.addObject("BDID", commandMap.get("BDID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
			
		return mv;
	}
	
	
	@RequestMapping(value="/officework/deleteConsignStatus.do")
	public ModelAndView deleteConsignStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/consignStatusList.do");
		
		officeworkService.deleteConsignStatus(commandMap.getMap());		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="/officework/budgetStatusWrite.do")
	public ModelAndView budgetStatusWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/budgetStatusWrite");
	
		return mv;
	}
	
	
	
	@RequestMapping(value="/officework/budgetStatusList.do")
    public ModelAndView budgetStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/budgetStatusList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectBudgetStatusList.do")
    public ModelAndView selectBudgetStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectBudgetStatusList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/insertBudgetStatus.do")
	public ModelAndView insertBudgetStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/consignStatusList.do");
		
		officeworkService.insertBudgetStatus(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		return mv;
	}
	
	
	@RequestMapping(value="/officework/budgetStatusUpdate.do")
	public ModelAndView budgetStatusUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/budgetStatusUpdate");
		
		Map<String,Object> map = officeworkService.budgetStatusDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/updateBudgetStatus.do")
	public ModelAndView updateBudgetStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/consignStatusList.do");
		
		officeworkService.updateBudgetStatus(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));

		return mv;
	}
	
	
	@RequestMapping(value="/officework/deleteBudgetStatus.do")
	public ModelAndView deleteBudgetStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/consignStatusList.do");
		
		officeworkService.deleteBudgetStatus(commandMap.getMap());		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="/officework/superviseStatusList.do")
    public ModelAndView superviseStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/superviseStatusList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectSuperviseStatusList.do")
    public ModelAndView selectSuperviseStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectSuperviseStatusList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/officework/manageEvalStatusList.do")
    public ModelAndView manageEvalStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/manageEvalStatusList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectManageEvalStatusList.do")
    public ModelAndView selectManageEvalStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectManageEvalStatusList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/officework/superviseStatusWrite.do")
	public ModelAndView superviseStatusWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/superviseStatusWrite");
	
		return mv;
	}
	
	
	@RequestMapping(value="/officework/manageEvalStatusWrite.do")
	public ModelAndView manageEvalStatusWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/manageEvalStatusWrite");
	
		return mv;
	}
	
	
	@RequestMapping(value="/officework/insertSuperviseStatus.do")
	public ModelAndView insertSuperviseStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/superviseStatusList.do");
		
		officeworkService.insertSuperviseStatus(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/insertManageEvalStatus.do")
	public ModelAndView insertManageEvalStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/superviseStatusList.do");
		
		officeworkService.insertManageEvalStatus(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
	
		return mv;
	}
	
	
	@RequestMapping(value="/officework/superviseStatusUpdate.do")
	public ModelAndView superviseStatusUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/superviseStatusUpdate");
		
		Map<String,Object> map = officeworkService.superviseStatusDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/updateSuperviseStatus.do")
	public ModelAndView updateSuperviseStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/superviseStatusList.do");
		
		officeworkService.updateSuperviseStatus(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));

		return mv;
	}
	
	
	@RequestMapping(value="/officework/deleteSuperviseStatus.do")
	public ModelAndView deleteSuperviseStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/superviseStatusList.do");
		
		officeworkService.deleteSuperviseStatus(commandMap.getMap());		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/officework/manageEvalStatusUpdate.do")
	public ModelAndView manageEvalStatusUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/manageEvalStatusUpdate");
		
		Map<String,Object> map = officeworkService.manageEvalStatusDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/updateManageEvalStatus.do")
	public ModelAndView updateManageEvalStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/superviseStatusList.do");
		
		officeworkService.updateManageEvalStatus(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/officework/deleteManageEvalStatus.do")
	public ModelAndView deleteManageEvalStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/superviseStatusList.do");
		
		officeworkService.deleteManageEvalStatus(commandMap.getMap());		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="/officework/qualiDelibStatusList.do")
    public ModelAndView qualiDelibStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/qualiDelibStatusList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectqualiDelibStatusList.do")
    public ModelAndView selectqualiDelibStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectqualiDelibStatusList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/officework/guAssembAgreementList.do")
    public ModelAndView guAssembAgreementList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/guAssembAgreementList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectguAssembAgreementList.do")
    public ModelAndView selectguAssembAgreementList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectguAssembAgreementList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/officework/qualiDelibStatusWrite.do")
	public ModelAndView qualiDelibStatusWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/qualiDelibStatusWrite");
	
		return mv;
	}
	
	@RequestMapping(value="/officework/guAssembAgreementWrite.do")
	public ModelAndView guAssembAgreementWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/guAssembAgreementWrite");
	
		return mv;
	}
	
	@RequestMapping(value="/officework/insertQualiDelibStatus.do")
	public ModelAndView insertQualiDelibStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/qualiDelibStatusList.do");
		
		officeworkService.insertQualiDelibStatus(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
	
		return mv;
	}
	
	@RequestMapping(value="/officework/insertGuAssembAgreement.do")
	public ModelAndView insertGuAssembAgreement(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/qualiDelibStatusList.do");
		
		officeworkService.insertGuAssembAgreement(commandMap.getMap(), request);
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/officework/qualiDelibStatusUpdate.do")
	public ModelAndView qualiDelibStatusUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/qualiDelibStatusUpdate");
		
		Map<String,Object> map = officeworkService.qualiDelibStatusDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/updateQualiDelibStatus.do")
	public ModelAndView updateQualiDelibStatus(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/qualiDelibStatusList.do");
		
		officeworkService.updateQualiDelibStatus(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));

		return mv;
	}
	
	
	@RequestMapping(value="/officework/deleteQualiDelibStatus.do")
	public ModelAndView deleteQualiDelibStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/qualiDelibStatusList.do");
		
		officeworkService.deleteQualiDelibStatus(commandMap.getMap());		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/guAssembAgreementUpdate.do")
	public ModelAndView guAssembAgreementUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/guAssembAgreementUpdate");
		
		Map<String,Object> map = officeworkService.guAssembAgreementUpdateDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/officework/updateGuAssembAgreement.do")
	public ModelAndView updateGuAssembAgreement(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/qualiDelibStatusList.do");
		
		officeworkService.updateGuAssembAgreement(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));

		return mv;
	}
	
	
	@RequestMapping(value="/officework/deleteGuAssembAgreement.do")
	public ModelAndView deleteGuAssembAgreement(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/officework/qualiDelibStatusList.do");
		
		officeworkService.deleteGuAssembAgreement(commandMap.getMap());		
		mv.addObject("ID", commandMap.get("OW_ID"));
		mv.addObject("WDID", commandMap.get("WDID"));
		mv.addObject("SAUP_DEPT", commandMap.get("SAUP_DEPT"));
		
		return mv;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="/officework/trustOworkStatusList.do")
    public ModelAndView trustOworkStatusList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/officework/trustOworkStatusList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/officework/selectTrustOworkStatusList.do")
    public ModelAndView selectTrustOworkStatusList(CommandMap commandMap, HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = officeworkService.selectTrustOworkStatusList(commandMap.getMap(), request);
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/officework/excelTrustOworkStatusList.do")
    public ModelAndView excelTrustOworkStatusList(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/officework/excelTrustOworkStatusList");
    	
    	List<Map<String,Object>> list = officeworkService.selectTrustOworkStatusList(commandMap.getMap(), request);
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	
	
	
	
	
	
	
}
