package manpower.manage.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import manpower.common.common.CommandMap;
import manpower.manage.dao.ManageDAO;
import manpower.manage.service.ManageService;

@Controller
public class ManageController {
	Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource(name="manageService")
	private ManageService manageService;
	
	@Resource(name="manageDAO")
	private ManageDAO manageDAO;
	
	/*공지사항*/
	@RequestMapping(value="/manage/noticeManage.do")
    public ModelAndView noticeManage(CommandMap commandMap) throws Exception{
    	System.out.println("noticeManage탐");
		ModelAndView mv = new ModelAndView("/manage/noticeManage");
    	
    	return mv;
    }
	
	@RequestMapping(value="/manage/selectNoticeList.do")
    public ModelAndView selectNoticeList(CommandMap commandMap) throws Exception{
		log.debug("selectConfirmList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		

		
		
    	List<Map<String,Object>> list = manageService.selectNoticeList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		System.out.println("확인 : " + list.toString());
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	//선택삭제
	@RequestMapping(value="/manage/deleteNoticeChk.do")
	@ResponseBody
	public Map<String, Object> deleteNoticeChk(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals 확인 : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelNoticeAllList(map);
	    }
		
		return map;
	}
	
	/*임금관리*/
	@RequestMapping(value="/manage/paymentManage.do")
    public ModelAndView paymentManage(CommandMap commandMap) throws Exception{
    	System.out.println("paymentManage탐");
		ModelAndView mv = new ModelAndView("/manage/paymentManage");
    	
    	return mv;
    }
	
	/*인력관리*/
	@RequestMapping(value="/manage/workerManage.do")
    public ModelAndView workerManage(CommandMap commandMap) throws Exception{
    	System.out.println("workerManage탐");
		ModelAndView mv = new ModelAndView("/manage/workerManage");
    	
    	return mv;
    }
	
	/*사업관리*/
	@RequestMapping(value="/manage/businessManage.do")
    public ModelAndView businessManage(CommandMap commandMap) throws Exception{
    	System.out.println("businessManage탐");
		ModelAndView mv = new ModelAndView("/manage/businessManage");
    	
    	return mv;
    }
	
	/*은행코드관리*/
	@RequestMapping(value="/manage/bankManage.do")
    public ModelAndView bankManage(CommandMap commandMap) throws Exception{
    	System.out.println("bankManage탐");
		ModelAndView mv = new ModelAndView("/manage/bankManage");
    	
    	return mv;
    }
	
	@RequestMapping(value="/manage/selectBankList.do")
    public ModelAndView selectBankList(CommandMap commandMap) throws Exception{
		System.out.println("selectBankList탐");
		log.debug("selectConfirmList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		
    	List<Map<String,Object>> list = manageService.selectBankList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		System.out.println("확인 : " + list.toString());
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value = "/manage/bankExcelUp.do", method = RequestMethod.POST)
	public ModelAndView bankExcelUp(MultipartFile testFile, MultipartHttpServletRequest request) throws Exception {
		System.out.println("bankExcelUp탐");
		MultipartFile excelFile = request.getFile("excelFile");
		String ins_id = request.getParameter("INS_ID");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		File destFile = new File("C:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = manageService.bankExcelUp(destFile,ins_id);
		destFile.delete();

		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("resultMap", resultMap);
		
		return mv;
	}
	
	
	//선택삭제
	@RequestMapping(value="/manage/deleteBankChk.do")
	@ResponseBody
	public Map<String, Object> deleteBankChk(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals 확인 : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelBankAllList(map);
	    }
		
		return map;
	}
	
	
	
	
	/*통계관리*/
	@RequestMapping(value="/manage/manpowerStatus.do")
    public ModelAndView manpowerStatus(CommandMap commandMap) throws Exception{
    	System.out.println("manpowerStatus탐");
		ModelAndView mv = new ModelAndView("/manage/manpowerStatus");
    	
    	return mv;
    }
	
	/*권한관리*/
	@RequestMapping(value="/manage/authorityManage.do")
    public ModelAndView authorityManage(CommandMap commandMap) throws Exception{
    	System.out.println("authorityManage탐");
		ModelAndView mv = new ModelAndView("/manage/authorityManage");
    	
    	return mv;
    }
	
	
	/*소득세관리*/
	@RequestMapping(value="/manage/incomeManage.do")
    public ModelAndView incomeManage(CommandMap commandMap) throws Exception{
    	System.out.println("incomeManage탐");
		ModelAndView mv = new ModelAndView("/manage/incomeManage");
    	
    	return mv;
    }
	
}