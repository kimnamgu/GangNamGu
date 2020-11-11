package service.daejang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.common.common.CommandMap;
import service.common.service.CommonService;
import service.common.util.CommonUtils;
import service.common.util.HttpConnectionUtil;
import service.common.util.Nvl;
import service.daejang.service.DaejangService;

@Controller
public class DaejangController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/docIssueReserveList.do")
    public ModelAndView docIssueReserveList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/docIssueReserveList");
		
		Map<String,Object> recentData  = daejangService.getDocIssueReserveRecent(commandMap.getMap());
		mv.addObject("recentData", recentData);
		System.out.println("recentData확인1 :" + recentData.toString());
    	
    	return mv;
    }
	
	@RequestMapping(value="/api/v1/selectDocIssueReserveList.do")
    public ModelAndView selectDocIssueReserveList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectDocIssueReserveList(commandMap.getMap());
    	List<Map<String,Object>> statisticsList = daejangService.selectStatisticsList(commandMap.getMap());
    	Map<String,Object> recentData  = daejangService.getDocIssueReserveRecent(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	mv.addObject("statisticsList", statisticsList);
    	mv.addObject("recentData", recentData);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("total_count"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	//이거는 화면에서 조회하는부분
	@RequestMapping(value="/api/v1/selectDocIssueReserveViewList.do")
    public ModelAndView selectDocIssueReserveViewList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	//제우스 서버에서 깨지는거 떄문에 이렇게 넣음
    	commandMap.put("apply_uname", Nvl.nvlStr(CommonUtils.ascToksc(commandMap.getMap().get("apply_uname").toString())));
    	
    	
    	System.out.println("파라미터 확인 : " + commandMap.getMap());
    	
    	List<Map<String,Object>> list = daejangService.selectDocIssueReserveViewList(commandMap.getMap());
    	List<Map<String,Object>> statisticsList = daejangService.selectStatisticsList(commandMap.getMap());
    	Map<String,Object> recentData  = daejangService.getDocIssueReserveRecent(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	mv.addObject("statisticsList", statisticsList);
    	mv.addObject("recentData", recentData);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("total_count"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/api/v1/getDocumentKindList.do")
    public ModelAndView getDocumentKindList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	log.debug("2param val= [" + params.toString() + "]");
    	List<Map<String,Object>> list = daejangService.getDocumentKindList(params);
    	
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("total_count"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/api/v1/getDocIssueReserveList.do")
    public ModelAndView getDocIssueReserveList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");

    	log.debug("3param val= [" + params.toString() + "]");
    	List<Map<String,Object>> list = daejangService.selectDocIssueReserveList(params);
    	
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("total_count"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/api/v1/getDocIssueReserveDetail.do")
	@ResponseBody
	public Map<String, Object> getDocIssueReserveDetail(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
		
		log.debug("4param val= [" + params.toString() + "]");		
		Map<String,Object> map = daejangService.getDocIssueReserveDetail(params, request);
		log.debug("rtn map = (" + map + ")");
		
		return map;
	}
	
	@RequestMapping(value="/api/v1/insertDocIssueReserve.do")
	public ModelAndView insertDocIssueReserve(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
		
		System.out.println("insertDocIssueReserve 여기 들어온것임 : " + params.toString());
		
		ModelAndView mv = new ModelAndView("jsonView");
		Map<String, Object> rtmap = new HashMap<>();
		
		log.debug("5param val= [" + params.toString() + "]");
		rtmap = daejangService.insertDocIssueReserve(params, request);
		
		
		//민원서류
		if((boolean) rtmap.get("result") && !params.get("document_gb").equals("JOB")){
			System.out.println("document_gb 확인 : " +params.get("document_gb"));
			
			
			params.put("GUBUN", "minwon");
			List<Map<String,Object>> sendList = commonService.SelectSmsUserList(params);
			
			Map<String, Object> rtDocumnetGb = new HashMap<>();
			rtDocumnetGb = daejangService.getDocumentKindInfo(params);
			
			for (int i = 0; i < sendList.size(); i++) {
				
				HashMap<String, Object> tm = new HashMap<String, Object>(sendList.get(i));
				System.out.println("tm 확인  : " + tm.toString());
				params.put("USER_ID" , tm.get("USER_ID"));
				params.put("USER_NAME" , tm.get("USER_NAME"));
				params.put("PHONE" , tm.get("PHONE"));
				params.put("SUBJECT" , "민원서류요청발생");
				params.put("SMS_MSG" , params.get("apply_uname") +"님이 "+ rtDocumnetGb.get("ITEM_NM") + "을 신청하셨습니다.");
				
				//문자 발송
				commonService.sendSms(params);
				
				commonService.insertSmsLog(params);
			}
		}
		
		mv.addObject("result", rtmap.get("result"));
		mv.addObject("msg",rtmap.get("msg"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/api/v1/updateDocIssueReserve.do")
	public ModelAndView updateDocIssueReserve(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		Map<String, Object> rtmap = new HashMap<>();
		//System.out.println("data=("  + params + ")");
		log.debug("6param val= [" + params.toString() + "]");
		rtmap = daejangService.updateDocIssueReserve(params, request);
		
		System.out.println("controller rtn =("  + rtmap.get("result") + " msg=[" + rtmap.get("msg") + "] )");
		mv.addObject("result", rtmap.get("result"));
		mv.addObject("msg",rtmap.get("msg"));
		
		return mv;
	}
	
	@RequestMapping(value="/api/v1/test.do",method=RequestMethod.POST)
	public void test(CommandMap commandMap, HttpServletRequest request) throws Exception{
		log.debug("test 인자 값 확인  val= [" + commandMap.getMap().toString() + "]");
		
	}
	
	
	@RequestMapping(value="/api/v1/processDocIssueReserve.do")
	public ModelAndView processDocIssueReserve(@RequestBody Map<String, Object> params, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		Map<String, Object> rtmap = new HashMap<>();
		System.out.println("processDocIssueReserve 수행 " + params.toString());
		log.debug("7param val= [" + params.toString() + "]");
		
		
		try {
			rtmap = daejangService.updateDocIssueReserve(params, request);
			
			//삭제로 넘어올 경우 바로 결재취소 부분
			System.out.println("취소시 전달 params 확인 : " + params.toString());
			
			if (params.get("work_gb").equals("D")) {
				
				String url = "http://conf.gangnam.go.kr/gnconference/app/payment/cancel"; 	//URL
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("tno", (String) params.get("tno"));	//PARAM
				param.put("app_time", (String) params.get("app_time"));	//PARAM
				
				HttpConnectionUtil.postRequest(url, param);
			}
			
			mv.addObject("result",  rtmap.get("result"));
			mv.addObject("msg",rtmap.get("msg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("result",  "false");
			mv.addObject("msg","fail");
		}

		System.out.println("mv.toString 확인 : " + mv.toString());
		
		return mv;
	}

	
	@RequestMapping(value="/ws1.do")
	public ModelAndView ws1(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/ws1");
		
		return mv;
	}
	
	
	@RequestMapping(value="/api/v1/getDocIssueReserveRecent.do")
	@ResponseBody
	public Map<String, Object> getDocIssueReserveRecent(CommandMap commandMap) throws Exception{
		System.out.println("getDocIssueReserveRecent 확인 : " + commandMap.getMap());
		Map<String,Object> map = daejangService.getDocIssueReserveRecent(commandMap.getMap());
		log.debug("rtn map = (" + map + ")");
		
		return map;
	}
	
	/*통계확인 페이지*/
	@RequestMapping(value="/docIssueStatisticsList.do")
    public ModelAndView docIssueStatisticsList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/docIssueStatisticsList");
    	return mv;
    }
	
	/*통계조회*/
	@RequestMapping(value="/selectStatisticsList.do")
	 public ModelAndView selectStatisticsList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	/*발급통계조회*/
    	List<Map<String,Object>> issueStatisticsListYear = daejangService.selectDocIssueStaticsYearList(commandMap.getMap());
    	List<Map<String,Object>> issueStatisticsListMonth = daejangService.selectDocIssueStaticsMonthList(commandMap.getMap());
    	List<Map<String,Object>> issueStatisticsListDay = daejangService.selectDocIssueStaticsDayList(commandMap.getMap());
    	
    	/*수수료통계조회*/
    	List<Map<String,Object>> peeStatisticsListYear = daejangService.selectDocPeeStaticsYearList(commandMap.getMap());
    	List<Map<String,Object>> peeStatisticsListMonth = daejangService.selectDocPeeStaticsMonthList(commandMap.getMap());
    	List<Map<String,Object>> peeStatisticsListDay = daejangService.selectDocPeeStaticsDayList(commandMap.getMap());
    	
    	
    	mv.addObject("issueStatisticsListYear", issueStatisticsListYear);
    	mv.addObject("issueStatisticsListMonth", issueStatisticsListMonth);
    	mv.addObject("issueStatisticsListDay", issueStatisticsListDay);
    	
    	mv.addObject("peeStatisticsListYear", peeStatisticsListYear);
    	mv.addObject("peeStatisticsListMonth", peeStatisticsListMonth);
    	mv.addObject("peeStatisticsListDay", peeStatisticsListDay);
    	
    	return mv;
    }
	
	/*통계 엑셀 출력*/
	@RequestMapping(value = "/daejang/downloadExcelFile.do")
    public ModelAndView downloadExcelFile(CommandMap commandMap,Model model) throws Exception {
		log.debug("1param val= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("/daejang/excelConvert");
    	/*발급통계조회*/
    	List<Map<String,Object>> issueStatisticsListYear = daejangService.selectDocIssueStaticsYearList(commandMap.getMap());
    	List<Map<String,Object>> issueStatisticsListMonth = daejangService.selectDocIssueStaticsMonthList(commandMap.getMap());
    	List<Map<String,Object>> issueStatisticsListDay = daejangService.selectDocIssueStaticsDayList(commandMap.getMap());
    	
    	/*수수료통계조회*/
    	List<Map<String,Object>> peeStatisticsListYear = daejangService.selectDocPeeStaticsYearList(commandMap.getMap());
    	List<Map<String,Object>> peeStatisticsListMonth = daejangService.selectDocPeeStaticsMonthList(commandMap.getMap());
    	List<Map<String,Object>> peeStatisticsListDay = daejangService.selectDocPeeStaticsDayList(commandMap.getMap());
    	
    	
    	mv.addObject("issueStatisticsListYear", issueStatisticsListYear);
    	mv.addObject("issueStatisticsListMonth", issueStatisticsListMonth);
    	mv.addObject("issueStatisticsListDay", issueStatisticsListDay);
    	
    	mv.addObject("peeStatisticsListYear", peeStatisticsListYear);
    	mv.addObject("peeStatisticsListMonth", peeStatisticsListMonth);
    	mv.addObject("peeStatisticsListDay", peeStatisticsListDay);
    	
		return mv;
    }
	
	@RequestMapping(value="/daejang/controllertest.do")
	public ModelAndView controllertest() throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
    	//서손후 환불 처리
    	List<Map<String,Object>> list = daejangService.selectSeosonList();
    	System.out.println("selectSeosonList 확인 : " + list.toString());
    	Map<String,Object> tempMap = null;
    	String tno = "";
    	String app_time = "";
    	
    	for(int i=0; i<list.size(); i++){
			tempMap = list.get(i);
			
			System.out.println("tempMap 확인 : " + tempMap.toString());
			
			tno = (String) tempMap.get("PAYMENT_ID");
			app_time = (String) tempMap.get("PAYMENT_DATE");
			
			String url = "http://conf.gangnam.go.kr/gnconference/app/payment/cancel"; 	//URL
			HashMap<String, String> param = new HashMap<String, String>();
			
			param.put("tno", tno);	//PARAM
			param.put("app_time", app_time);	//PARAM
			
			//HttpConnectionUtil.postRequest(url, param);
		}
		
		
		return mv;
		
	}
}
