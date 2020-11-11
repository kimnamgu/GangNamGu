package corona.status.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import corona.common.common.CommandMap;
import corona.status.service.StatusService;

@Controller
public class StatusController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="statusService")
	private StatusService statusService;
	
	@RequestMapping(value="/status/mainStatusView.do")
    public ModelAndView mainStatusView(CommandMap commandMap) throws Exception{
    	System.out.println("mainStatusView탐");
    	
    	
    	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
    	SimpleDateFormat format2 = new SimpleDateFormat ( "MM월dd일");
    			
    	Date time = new Date();
    			
    	String time1 = format1.format(time);
    	String time2 = format2.format(time);
    	
    	
/*    	   Calendar day = Calendar.getInstance();
    	    day.add(Calendar.DATE , -1);
    	    String beforeDate = new java.text.SimpleDateFormat("yyyyMMdd").format(day.getTime());
    	    String beforeDate2 = new java.text.SimpleDateFormat("MM월dd일").format(day.getTime());
    	    System.out.println(beforeDate);
    	    System.out.println(beforeDate2);*/
    	
    	commandMap.put("seldate", time1);
    	commandMap.put("showdate", time2);
    	
		ModelAndView mv = new ModelAndView("/status/mainStatusView");
		
		
		/*총괄통계*/
		Map<String,Object> totalMap = statusService.selectTotalStatus(commandMap.getMap());
		mv.addObject("totalMap", totalMap.get("map"));
		
		/*확진자통계*/
		mv.addObject("show_gubun", totalMap.get("map"));
		Map<String,Object> confirmShow = statusService.selectConfirmShow(commandMap.getMap());
		
		System.out.println("confirmShow 확인 : " + confirmShow.get("map"));
		
		Map<String, Object> confirmShowList = new HashMap<String,Object>();
		confirmShowList = (HashMap<String,Object>) confirmShow.get("map");
		System.out.println("confirmShowList 확인 : " + (String) confirmShowList.get("SHOW_LIST"));
		String[] SHOW_LIST = ((String) confirmShowList.get("SHOW_LIST")).split(",") ;
		
		/*String[] SHOW_LIST = {(String) confirmShowList.get("SHOW_LIST")} ;*/
		/*String[] SHOW_LIST = {"신천지","대구","콜센터","분당제생병원","이태원","삼성서울병원","확진자접촉자","기타"} ;*/

		commandMap.put("SHOW_LIST", SHOW_LIST);
		mv.addObject("SHOW_LIST", SHOW_LIST);
		System.out.println("SHOW_LIST  확인 : " + SHOW_LIST);
		Map<String,Object> confirmMap = statusService.selectConfirmStatus(commandMap.getMap());
    	
    	mv.addObject("confirmMap", confirmMap.get("map"));
    	
		/*자가격리자통계*/
		Map<String,Object> domIsoMap = statusService.selectDomesticStatus(commandMap.getMap());
		Map<String,Object> overseaIsoMap = statusService.selectOverseaStatus(commandMap.getMap());
    	
    	mv.addObject("domIsoMap", domIsoMap.get("map"));
    	mv.addObject("overseaIsoMap", overseaIsoMap.get("map"));
    	
		/*상담민원통계*/
		Map<String,Object> consultMap = statusService.selectConsultStatus(commandMap.getMap());
		
		mv.addObject("consultMap", consultMap.get("map"));
				
		/*선별진료소 통계*/
		Map<String,Object> clinicMap = statusService.selectClinicStatus(commandMap.getMap());
		
		mv.addObject("clinicMap", clinicMap.get("map"));
		
		/*선별진료소 검사케이스통계*/
		Map<String,Object> clinicCaseMap = statusService.selectClinicCaseStatus(commandMap.getMap());
		
		mv.addObject("clinicCaseMap", clinicCaseMap.get("map"));
    	
    	
		
    	return mv;
    }
	
	@RequestMapping(value="/status/updateClinicDae.do")
    public ModelAndView updateClinicDae(CommandMap commandMap) throws Exception{
		log.debug("updateClinicDae param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		statusService.updateClinicDae(commandMap.getMap());
    	return mv;
    }
	
	@RequestMapping(value="/status/updateClinicSo.do")
    public ModelAndView updateClinicSo(CommandMap commandMap) throws Exception{
		log.debug("updateClinicSo param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		statusService.updateClinicSo(commandMap.getMap());
    	return mv;
    }
	
	@RequestMapping(value="/status/statusMiddleConsultView.do")
	public ModelAndView statusMiddleView(CommandMap commandMap, HttpServletRequest request,ModelMap model) throws Exception{
		System.out.println("statusMiddleView탐");
		
		ModelAndView mv = new ModelAndView("/status/statusMiddleConsultView");
		
		Map<String,Object> selectConsultMiddle = statusService.selectConsultMiddle(commandMap.getMap());
		
		
		Map<String,Object> consultMap  = (Map<String, Object>) selectConsultMiddle.get("map");
		

		String percent;
		
		double TOTAL_JU = Integer.parseInt(String.valueOf(consultMap.get("TOTAL_JU")));
		double TOTAL_YA = Integer.parseInt(String.valueOf(consultMap.get("TOTAL_YA")));
		
		percent = String.format("%.1f",TOTAL_JU/(TOTAL_JU + TOTAL_YA)*100);
		consultMap.put("TOTAL_JU_PER", percent);
		
		percent = String.format("%.1f",TOTAL_YA/(TOTAL_JU + TOTAL_YA)*100);
		consultMap.put("TOTAL_YA_PER", percent);
		
		double DAY_1 = Integer.parseInt(String.valueOf(consultMap.get("DAY_1")));
		double DAY_2 = Integer.parseInt(String.valueOf(consultMap.get("DAY_2")));
		double DAY_3 = Integer.parseInt(String.valueOf(consultMap.get("DAY_3")));
		double DAY_4 = Integer.parseInt(String.valueOf(consultMap.get("DAY_4")));
		double DAY_5 = Integer.parseInt(String.valueOf(consultMap.get("DAY_5")));
		double DAY_6 = Integer.parseInt(String.valueOf(consultMap.get("DAY_6")));
		double DAY_7 = Integer.parseInt(String.valueOf(consultMap.get("DAY_7")));
		double DAY_8 = Integer.parseInt(String.valueOf(consultMap.get("DAY_8")));
		double DAY_9 = Integer.parseInt(String.valueOf(consultMap.get("DAY_9")));
		double DAY_10 = Integer.parseInt(String.valueOf(consultMap.get("DAY_10")));
		double DAY_ALL = DAY_1 + DAY_2 + DAY_3 + DAY_4 + DAY_5 + DAY_6 + DAY_7 + DAY_8 + DAY_9 + DAY_10;
		if(DAY_ALL == 0){
			DAY_ALL = 100;
		}
		
		consultMap.put("DAY_1_PER", String.format("%.1f",DAY_1/(DAY_ALL)*100));
		consultMap.put("DAY_2_PER", String.format("%.1f",DAY_2/(DAY_ALL)*100));
		consultMap.put("DAY_3_PER", String.format("%.1f",DAY_3/(DAY_ALL)*100));
		consultMap.put("DAY_4_PER", String.format("%.1f",DAY_4/(DAY_ALL)*100));
		consultMap.put("DAY_5_PER", String.format("%.1f",DAY_5/(DAY_ALL)*100));
		consultMap.put("DAY_6_PER", String.format("%.1f",DAY_6/(DAY_ALL)*100));
		consultMap.put("DAY_7_PER", String.format("%.1f",DAY_7/(DAY_ALL)*100));
		consultMap.put("DAY_8_PER", String.format("%.1f",DAY_8/(DAY_ALL)*100));
		consultMap.put("DAY_9_PER", String.format("%.1f",DAY_9/(DAY_ALL)*100));
		consultMap.put("DAY_10_PER", String.format("%.1f",DAY_10/(DAY_ALL)*100));
		
		double ACCUM_1 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_1")));
		double ACCUM_2 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_2")));
		double ACCUM_3 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_3")));
		double ACCUM_4 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_4")));
		double ACCUM_5 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_5")));
		double ACCUM_6 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_6")));
		double ACCUM_7 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_7")));
		double ACCUM_8 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_8")));
		double ACCUM_9 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_9")));
		double ACCUM_10 = Integer.parseInt(String.valueOf(consultMap.get("ACCUM_10")));
		double ACCUM_ALL = ACCUM_1 + ACCUM_2 + ACCUM_3 + ACCUM_4 + ACCUM_5 + ACCUM_6 + ACCUM_7 + ACCUM_8 + ACCUM_9 + ACCUM_10;
		
		if(ACCUM_ALL == 0){
			ACCUM_ALL = 100;
		}
		
		consultMap.put("ACCUM_1_PER", String.format("%.1f",ACCUM_1/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_2_PER", String.format("%.1f",ACCUM_2/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_3_PER", String.format("%.1f",ACCUM_3/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_4_PER", String.format("%.1f",ACCUM_4/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_5_PER", String.format("%.1f",ACCUM_5/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_6_PER", String.format("%.1f",ACCUM_6/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_7_PER", String.format("%.1f",ACCUM_7/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_8_PER", String.format("%.1f",ACCUM_8/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_9_PER", String.format("%.1f",ACCUM_9/(ACCUM_ALL)*100));
		consultMap.put("ACCUM_10_PER", String.format("%.1f",ACCUM_10/(ACCUM_ALL)*100));
		
		mv.addObject("consultMap", consultMap);
		
		//상담민원 주야 그래프
/*		List<Map<String,Object>> consultStatisticList = statusService.selectConsultStatisticsList(commandMap.getMap());
		model.addAttribute("consultStatisticList",consultStatisticList);*/
		
		//상담민원 구분 그래프
		List<Map<String,Object>> consultGubunStatisticList = statusService.selectConsultGubunStatisticsList(commandMap.getMap());
		model.addAttribute("consultGubunStatisticList",consultGubunStatisticList);
		
		return mv;
	}
	
	
	//총괄 중간 화면
	@RequestMapping(value="/status/statusMiddleTotalView.do")
	public ModelAndView statusMiddleTotalView(CommandMap commandMap, HttpServletRequest request,ModelMap model) throws Exception{
		System.out.println("statusMiddleTotalView탐");
		
		ModelAndView mv = new ModelAndView("/status/statusMiddleTotalView");
		
		model.addAttribute("WRITE_DATE",commandMap.get("WRITE_DATE"));
		
		String WRITE_DATE_SHOW = (String) commandMap.get("WRITE_DATE");
		model.addAttribute("WRITE_DATE_SHOW",WRITE_DATE_SHOW.substring(0,4)+ "-" + WRITE_DATE_SHOW.substring(4,6)+ "-" + WRITE_DATE_SHOW.substring(6,8));
		
		List<Map<String,Object>> totalStatisticList = statusService.selectTotalStatisticsList(commandMap.getMap());
		model.addAttribute("totalStatisticList",totalStatisticList);
		
		//합산값 조회
		Map<String,Object> totalSum = statusService.selectTotalSum(commandMap.getMap());
		mv.addObject("totalSum", totalSum.get("map"));
		
    	//당일
		commandMap.put("ACCUM","");
		commandMap.put("ICHUP","");
		List<Map<String,Object>> selectTotalMiddle = statusService.selectTotalMiddle(commandMap.getMap());
    	mv.addObject("selectTotalMiddle", selectTotalMiddle);
		List<Map<String,Object>> selectTotalExMiddle = statusService.selectTotalExMiddle(commandMap.getMap());
    	mv.addObject("selectTotalExMiddle", selectTotalExMiddle);
    	//누적
		commandMap.put("ACCUM","Y");
		
		commandMap.put("ICHUP","N");
		List<Map<String,Object>> selectAccumTotalMiddle = statusService.selectTotalMiddle(commandMap.getMap());
    	mv.addObject("selectAccumTotalMiddle", selectAccumTotalMiddle);

    	commandMap.put("ICHUP","Y");
		List<Map<String,Object>> selectAccumTotalIchupMiddle = statusService.selectTotalMiddle(commandMap.getMap());
    	mv.addObject("selectAccumTotalIchupMiddle", selectAccumTotalIchupMiddle);
    	
		List<Map<String,Object>> selectAccumTotalExMiddle = statusService.selectTotalExMiddle(commandMap.getMap());
    	mv.addObject("selectAccumTotalExMiddle", selectAccumTotalExMiddle);
		return mv;
	}
	
	
	//확진자 중간 화면
	@RequestMapping(value="/status/statusMiddleConfirmView.do")
	public ModelAndView statusMiddleConfirmView(CommandMap commandMap, HttpServletRequest request,ModelMap model) throws Exception{
		System.out.println("statusMiddleConfirmView탐");
		
		ModelAndView mv = new ModelAndView("/status/statusMiddleConfirmView");
		
		model.addAttribute("WRITE_DATE",commandMap.get("WRITE_DATE"));
		model.addAttribute("DEPART",commandMap.get("DEPART"));
		
		String WRITE_DATE_SHOW = (String) commandMap.get("WRITE_DATE");
		
		model.addAttribute("WRITE_DATE_SHOW",WRITE_DATE_SHOW.substring(0,4)+ "-" + WRITE_DATE_SHOW.substring(4,6)+ "-" + WRITE_DATE_SHOW.substring(6,8));
		
		
		
		//확진지 노출 리스트
		Map<String,Object> confirmShow = statusService.selectConfirmShow(commandMap.getMap());
		
		Map<String, Object> confirmShowList = new HashMap<String,Object>();
		confirmShowList = (HashMap<String,Object>) confirmShow.get("map");
		mv.addObject("confirmShowList", (String) confirmShowList.get("SHOW_LIST"));
		System.out.println("confirmShowList 확인 : " + (String) confirmShowList.get("SHOW_LIST"));
		
		String[] SHOW_LIST = ((String) confirmShowList.get("SHOW_LIST")).split(",") ;
		commandMap.put("SHOW_LIST", SHOW_LIST);
		mv.addObject("SHOW_LIST", SHOW_LIST);
		mv.addObject("SHOW_LIST_LENGTH", SHOW_LIST.length);
		
		//합산값 조회
		Map<String,Object> confirmSum = statusService.selectConfirmSum(commandMap.getMap());
		Map<String, Object> confirmSumList = new HashMap<String,Object>();
		confirmSumList = (HashMap<String,Object>) confirmSum.get("map");
		
		
		mv.addObject("confirmSumList", confirmSumList);
		System.out.println("confirmSumList 확인 : " + confirmSumList);
		
		//확진자
		model.addAttribute("INFECT_GUBUN",commandMap.get("INFECT_GUBUN"));
		model.addAttribute("INFECT_DAE",commandMap.get("INFECT_DAE"));
		model.addAttribute("INFECT_SO",commandMap.get("INFECT_SO"));
		
		//확진지 정보 지도 표시
		List<Map<String,Object>> confirmMaplist = statusService.selectConfirmMaplist(commandMap.getMap());
		List<Map<String,Object>> confirmMapJipdanlist = statusService.selectConfirmMapJipdanlist(commandMap.getMap());
		
		//집단 합계 따로
		Map<String,Object> sumJipdanMap = statusService.selectConfirmJipdanSumStatus(commandMap.getMap());
		mv.addObject("sumJipdanMap", sumJipdanMap.get("map"));
		
		String jibun = "";
        String dong = "";
		
		//확진지 정보 그래프 표시
		List<Map<String,Object>> confirmAccumStatisticList = statusService.selectConfirmAccumStatisticlist(commandMap.getMap());
		List<Map<String,Object>> confirmStatisticList = statusService.selectConfirmStatisticlist(commandMap.getMap());
		
		model.addAttribute("confirmMaplist",confirmMaplist);
		model.addAttribute("confirmMapJipdanlist",confirmMapJipdanlist);
		
		model.addAttribute("confirmAccumStatisticList",confirmAccumStatisticList);
		model.addAttribute("confirmStatisticList",confirmStatisticList);
		
		return mv;
	}
	
	/*자가격리중간요약-해외*/
	@RequestMapping(value="/status/selectIsoOverseaMiddle.do")
    public ModelAndView selectIsoOverseaMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectIsoOverseaMiddle param2= [" + commandMap.getMap().toString() + "]");
		System.out.println("commandMap 확인 : " + commandMap.getMap().toString());
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectIsoOverseaMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	/*자가격리중간요약-국내*/
	@RequestMapping(value="/status/selectIsoDomMiddle.do")
    public ModelAndView selectIsoDomMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectIsoDomMiddle param2= [" + commandMap.getMap().toString() + "]");
		System.out.println("commandMap 확인 : " + commandMap.getMap().toString());
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectIsoDomMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	/*자가격리중간요약-해외*/
	@RequestMapping(value="/status/selectIsoOverseaAccumMiddle.do")
    public ModelAndView selectIsoOverseaAccumMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectIsoDomAccumMiddle param2= [" + commandMap.getMap().toString() + "]");
		System.out.println("commandMap 확인 : " + commandMap.getMap().toString());
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectIsoOverseaAccumMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	/*자가격리중간요약-국내*/
	@RequestMapping(value="/status/selectIsoDomAccumMiddle.do")
    public ModelAndView selectIsoDomAccumMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectIsoDomAccumMiddle param2= [" + commandMap.getMap().toString() + "]");
		System.out.println("commandMap 확인 : " + commandMap.getMap().toString());
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectIsoDomAccumMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	
	/*총괄중간요약*/
	@RequestMapping(value="/status/selectTotalMiddle.do")
    public ModelAndView selectTotalMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectTotalMiddle param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectTotalMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	/*총괄중간요약-타구*/
	@RequestMapping(value="/status/selectTotalExMiddle.do")
    public ModelAndView selectTotalExMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectTotalExMiddle param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectTotalExMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	
	/*총괄중간요약-해외*/
	@RequestMapping(value="/status/selectTotalExOverseaMiddle.do")
    public ModelAndView selectTotalExOverseaMiddle(CommandMap commandMap) throws Exception{
		log.debug("selectTotalExOverseaMiddle param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = statusService.selectTotalExOverseaMiddle(commandMap.getMap());
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());

    	return mv;
    }
	
	/*확진자 중간 요약- 해외*/
	@RequestMapping(value="/status/selectConfirmMiddleOversea.do")
    public ModelAndView selectConfirmMiddleOversea(CommandMap commandMap) throws Exception{
		System.out.println("selectConfirmMiddleOversea param2= [" + commandMap.getMap().toString() + "]");
		
		
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = statusService.selectConfirmMiddleOversea(commandMap.getMap());
		
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());
    	mv.addObject("WRITE_DATE", commandMap.get("WRITE_DATE"));
    	mv.addObject("DEPART", commandMap.get("DEPART"));

    	return mv;
    }
	
	/*확진자 중간 요약- 유동리스트*/
	@RequestMapping(value="/status/selectConfirmMiddle.do")
    public ModelAndView selectConfirmMiddle(CommandMap commandMap) throws Exception{
		System.out.println("selectConfirmMiddle param2= [" + commandMap.getMap().toString() + "]");
		
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		
		Map<String,Object> confirmShow = statusService.selectConfirmShow(commandMap.getMap());
		
		System.out.println("confirmShow 확인 : " + confirmShow.get("map"));
		
		Map<String, Object> confirmShowList = new HashMap<String,Object>();
		confirmShowList = (HashMap<String,Object>) confirmShow.get("map");
		System.out.println("confirmShowList 확인 : " + (String) confirmShowList.get("SHOW_LIST"));
		String[] SHOW_LIST = ((String) confirmShowList.get("SHOW_LIST")).split(",") ;

		/*String[] SHOW_LIST = {(String) confirmShowList.get("SHOW_LIST")} ;*/
		/*String[] SHOW_LIST = {"신천지","대구","콜센터","분당제생병원","이태원","삼성서울병원","확진자접촉자","기타"} ;*/
		
		commandMap.put("SHOW_LIST", SHOW_LIST);
		
		
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = statusService.selectConfirmMiddle(commandMap.getMap());
		
    	mv.addObject("list", list);
    	mv.addObject("list_size", list.size());
    	mv.addObject("INDEX", commandMap.get("INDEX"));
    	mv.addObject("WRITE_DATE", commandMap.get("WRITE_DATE"));
    	mv.addObject("DEPART", commandMap.get("DEPART"));

    	return mv;
    }
	
	/*확진자 중간요약 - 병원*/
	@RequestMapping(value="/status/selectConfirmMiddleHospital.do")
    public ModelAndView selectConfirmMiddleHospital(CommandMap commandMap) throws Exception{
		System.out.println("selectConfirmMiddleHospital param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		
		Map<String,Object> confirmShow = statusService.selectConfirmShow(commandMap.getMap());
		
		System.out.println("confirmShow 확인 : " + confirmShow.get("map"));
		
		Map<String, Object> confirmShowList = new HashMap<String,Object>();
		confirmShowList = (HashMap<String,Object>) confirmShow.get("map");
		System.out.println("confirmShowList 확인 : " + (String) confirmShowList.get("SHOW_LIST"));
		String[] SHOW_LIST = ((String) confirmShowList.get("SHOW_LIST")).split(",") ;
		commandMap.put("SHOW_LIST", SHOW_LIST);
		
		
		
    	
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		list = statusService.selectConfirmMiddleHospital(commandMap.getMap());
	/*	if(!commandMap.get("DEPART").equals("accum")){
			list = statusService.selectConfirmMiddleHospital(commandMap.getMap());
		}else{
			list = statusService.selectConfirmAccumMiddleHospital(commandMap.getMap());
		}*/
		
    	mv.addObject("list", list);
    	
    	mv.addObject("INDEX", commandMap.get("INDEX"));
    	mv.addObject("WRITE_DATE", commandMap.get("WRITE_DATE"));
    	mv.addObject("DEPART", commandMap.get("DEPART"));
    	mv.addObject("INFECT_DAE", commandMap.get("INFECT_DAE"));


    	return mv;
    }
	
	
	
	@RequestMapping(value="/status/statusTrackingView.do")
	public ModelAndView statusTrackingVeiw(CommandMap commandMap, HttpServletRequest request,ModelMap model) throws Exception{
		System.out.println("statusTrackingVeiw탐");
		System.out.println("commandMap 확인 : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("/status/statusTrackingView");
		
		model.addAttribute("WRITE_DATE",commandMap.get("WRITE_DATE"));
		model.addAttribute("ETC_GUBUN",commandMap.get("ETC_GUBUN"));
		model.addAttribute("ETC_GUBUN_CONDITION",commandMap.get("ETC_GUBUN_CONDITION"));
		
		//총괄
		model.addAttribute("TOTAL_GUBUN",commandMap.get("TOTAL_GUBUN"));
		model.addAttribute("TOTAL_CONDITION",commandMap.get("TOTAL_CONDITION"));
		model.addAttribute("TOTAL_ICHUP",commandMap.get("TOTAL_ICHUP"));
		model.addAttribute("TOTAL_DONG_GUBUN",commandMap.get("TOTAL_DONG_GUBUN"));
		
		//확진자
		model.addAttribute("INFECT_GUBUN",commandMap.get("INFECT_GUBUN"));
		model.addAttribute("INFECT_DAE",commandMap.get("INFECT_DAE"));
		model.addAttribute("INFECT_SO",commandMap.get("INFECT_SO"));
		
		//확진자-집단감염
		model.addAttribute("JIPDAN_GUBUN",commandMap.get("JIPDAN_GUBUN"));
		model.addAttribute("JIPDAN_DIVIDE",commandMap.get("JIPDAN_DIVIDE"));
		model.addAttribute("JIPDAN_INFECT_DAE",commandMap.get("JIPDAN_INFECT_DAE"));
		
		//자가격리자
		model.addAttribute("ISO_GUBUN",commandMap.get("ISO_GUBUN"));
		model.addAttribute("ISO_TYPE",commandMap.get("ISO_TYPE"));
		model.addAttribute("ISO_DONG",commandMap.get("ISO_DONG"));
		model.addAttribute("SELDATE",commandMap.get("SELDATE"));
		
		//상담민원
		model.addAttribute("CONSULT_DEPART",commandMap.get("CONSULT_DEPART"));
		model.addAttribute("CONSULT_JUYA_GUBUN",commandMap.get("CONSULT_JUYA_GUBUN"));
		model.addAttribute("CONSULT_CONSULT_GUBUN",commandMap.get("CONSULT_CONSULT_GUBUN"));
		
		//선별진료소
		model.addAttribute("CLINIC_DEPART",commandMap.get("CLINIC_DEPART"));
		model.addAttribute("CLINIC_SUSPICION_GUBUN",commandMap.get("CLINIC_SUSPICION_GUBUN"));
		model.addAttribute("CLINIC_SUSPICION_DAE",commandMap.get("CLINIC_SUSPICION_DAE"));
		model.addAttribute("CLINIC_SUSPICION_SO",commandMap.get("CLINIC_SUSPICION_SO"));
		model.addAttribute("ISO",commandMap.get("ISO"));
		
		//선별진료소 양성자
		model.addAttribute("CLINIC_CONFIRM_DEPART",commandMap.get("CLINIC_CONFIRM_DEPART"));
		model.addAttribute("CLINIC_CONFIRM_SUSPICION_GUBUN",commandMap.get("CLINIC_CONFIRM_SUSPICION_GUBUN"));
		model.addAttribute("CLINIC_CONFIRM_SUSPICION_DAE",commandMap.get("CLINIC_CONFIRM_SUSPICION_DAE"));
		model.addAttribute("CLINIC_CONFIRM_SUSPICION_SO",commandMap.get("CLINIC_CONFIRM_SUSPICION_SO"));
		model.addAttribute("CONFIRM_ISO",commandMap.get("CONFIRM_ISO"));
		
		//선별진료소 케이스
		model.addAttribute("CLINIC_CASE_GUBUN",commandMap.get("CLINIC_CASE_GUBUN"));
		model.addAttribute("CLINIC_CASE_CONDITION",commandMap.get("CLINIC_CASE_CONDITION"));
		model.addAttribute("CLINIC_CASE_SELDATE",commandMap.get("CLINIC_CASE_SELDATE"));
		
		return mv;
	}
	
	@RequestMapping(value="/status/selectTotalDtlList.do")
    public ModelAndView selectTotalDtlList(CommandMap commandMap) throws Exception{
		log.debug("selectTotalDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectTotalDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/status/selectConfirmDtlList.do")
    public ModelAndView selectConfirmDtlList(CommandMap commandMap) throws Exception{
		log.debug("selectConfirmDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectConfirmDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/status/selectConfirmJipdanDtlList.do")
    public ModelAndView selectConfirmJipdanDtlList(CommandMap commandMap) throws Exception{
		log.debug("selectConfirmJipdanDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectConfirmJipdanDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	/*자가격리자 해외입국자*/
	@RequestMapping(value="/status/selectIsoOverseaDtlList.do")
    public ModelAndView selectOverseaDtlList(CommandMap commandMap) throws Exception{
		log.debug("selectIsoOverseaDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectIsoOverseaDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	/*자가격리자 국내접촉자*/
	@RequestMapping(value="/status/selectIsoDomesticDtlList.do")
    public ModelAndView selectDomesticDtlList(CommandMap commandMap) throws Exception{
		log.debug("selectDomesticDtlList param2= [" + commandMap.getMap().toString() + "]");
		System.out.println("CommandMap 확인 : " +commandMap.getMap().toString());
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectIsoDomesticDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	/*상담민원*/
	@RequestMapping(value="/status/selectConsultDtlList.do")
    public ModelAndView selectConsultDtlList(CommandMap commandMap) throws Exception{
		System.out.println("selectConsultDtlList");
		log.debug("selectConsultDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectConsultDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	/*선별진료소*/
	@RequestMapping(value="/status/selectClinicDtlList.do")
    public ModelAndView selectClinicDtlList(CommandMap commandMap) throws Exception{
		System.out.println("selectClinicDtlList");
		log.debug("selectClinicDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectClinicDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	/*선별진료소 양성자*/
	@RequestMapping(value="/status/selectClinicConfirmDtlList.do")
    public ModelAndView selectClinicConfirmDtlList(CommandMap commandMap) throws Exception{
		System.out.println("selectClinicConfirmDtlList");
		log.debug("selectClinicConfirmDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectClinicConfirmDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	/*선별진료소*/
	@RequestMapping(value="/status/selectClinicCaseDtlList.do")
    public ModelAndView selectClinicCaseDtlList(CommandMap commandMap) throws Exception{
		System.out.println("selectClinicDtlList");
		log.debug("selectClinicCaseDtlList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = statusService.selectClinicCaseDtlList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/status/statusMiddleClinicView.do")
	public ModelAndView statusMiddleClinicView(CommandMap commandMap, HttpServletRequest request,ModelMap model) throws Exception{
		System.out.println("statusMiddleClinicView탐");
		System.out.println("commandMap 확인 : " + commandMap.getMap().toString());
		
		ModelAndView mv = new ModelAndView("/status/statusMiddleClinicView");
		
		model.addAttribute("SELDATE",commandMap.get("SELDATE"));
		
		String SHOWDATE = ((String) commandMap.get("SELDATE"));
		
		model.addAttribute("SHOWDATE",SHOWDATE.substring(0,4) + "-" + SHOWDATE.substring(4,6) + "-" + SHOWDATE.substring(6,8));
		
		
		//자가격리자 그래프
		List<Map<String,Object>> clinicStatisticList = statusService.selectClinicStatisticsList(commandMap.getMap());
		model.addAttribute("clinicStatisticList",clinicStatisticList);
		
		
		//합계 따로
		Map<String,Object> sumMap = statusService.selectClinicMiddleSumStatus(commandMap.getMap());
		mv.addObject("sumMap", sumMap.get("map"));
		
		
		//당일 자가격리자 제외
		commandMap.put("ISO","ISO_EX");
		
		List<Map<String,Object>> selectClinicGangnamMiddleList = statusService.selectClinicGangnamMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicGangnamMiddleList", selectClinicGangnamMiddleList);
		
		List<Map<String,Object>> selectClinicTasiguMiddleList = statusService.selectClinicTasiguMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicTasiguMiddleList", selectClinicTasiguMiddleList);
		
		List<Map<String,Object>> selectClinicOverseaMiddleList = statusService.selectClinicOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicOverseaMiddleList", selectClinicOverseaMiddleList);
    	
    	//당일 자가격리자
		commandMap.put("ISO","ISO");
		
		List<Map<String,Object>> selectClinicGangnamIsoMiddleList = statusService.selectClinicGangnamMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicGangnamIsoMiddleList", selectClinicGangnamIsoMiddleList);
		
		List<Map<String,Object>> selectClinicTasiguIsoMiddleList = statusService.selectClinicTasiguMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicTasiguIsoMiddleList", selectClinicTasiguIsoMiddleList);
    	
		List<Map<String,Object>> selectClinicOverseaIsoMiddleList = statusService.selectClinicOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicOverseaIsoMiddleList", selectClinicOverseaIsoMiddleList);
		
    	//누적
		commandMap.put("ISO","");
		commandMap.put("SELDATE","");
		
		List<Map<String,Object>> selectClinicGangnamAccumMiddleList = statusService.selectClinicGangnamMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicGangnamAccumMiddleList", selectClinicGangnamAccumMiddleList);
		
		List<Map<String,Object>> selectClinicTasiguAccumMiddleList = statusService.selectClinicTasiguMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicTasiguAccumMiddleList", selectClinicTasiguAccumMiddleList);
		
		List<Map<String,Object>> selectClinicOverseaAccumMiddleList = statusService.selectClinicOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectClinicOverseaAccumMiddleList", selectClinicOverseaAccumMiddleList);
		
		return mv;
	}
	
	
	@RequestMapping(value="/status/statusMiddleIsoView.do")
	public ModelAndView statusMiddleIsoView(CommandMap commandMap, HttpServletRequest request,ModelMap model) throws Exception{
		System.out.println("statusMiddleIsoView탐");
		
		ModelAndView mv = new ModelAndView("/status/statusMiddleIsoView");
		
		
		model.addAttribute("SELDATE",commandMap.get("SELDATE"));
		
		String SHOWDATE = ((String) commandMap.get("SELDATE"));
		
		model.addAttribute("SHOWDATE",SHOWDATE.substring(0,4) + "-" + SHOWDATE.substring(4,6) + "-" + SHOWDATE.substring(6,8));
		
		//자가격리자 지도
		List<Map<String,Object>> isoMaplist = statusService.selectIsoMaplist(commandMap.getMap());
		mv.addObject("isoMaplist",isoMaplist);
		
		//자가격리자 그래프
		List<Map<String,Object>> isoStatisticList = statusService.selectIsoStatisticsList(commandMap.getMap());
		model.addAttribute("isoStatisticList",isoStatisticList);
		
		
		//합계 따로
		Map<String,Object> sumMap = statusService.selectIsoMiddleSumStatus(commandMap.getMap());
		mv.addObject("sumMap", sumMap.get("map"));
		
		//당일
		commandMap.put("CUR","Y");
		commandMap.put("DUE","");
		commandMap.put("FREE","");
		commandMap.put("ACCUM","");
		
		List<Map<String,Object>> selectIsoDomMiddleList = statusService.selectIsoDomMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoDomMiddleList", selectIsoDomMiddleList);
		
		List<Map<String,Object>> selectIsoOverseaMiddleList = statusService.selectIsoOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoOverseaMiddleList", selectIsoOverseaMiddleList);
		
    	//자가격리 기간중인자
    	
		commandMap.put("CUR","");
		commandMap.put("DUE","Y");
		commandMap.put("FREE","");
		commandMap.put("ACCUM","");
		List<Map<String,Object>> selectIsoDomDueMiddleList = statusService.selectIsoDomMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoDomDueMiddleList", selectIsoDomDueMiddleList);
		
		List<Map<String,Object>> selectIsoOverseaDueMiddleList = statusService.selectIsoOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoOverseaDueMiddleList", selectIsoOverseaDueMiddleList);
    	
    	//해제
		commandMap.put("CUR","");
		commandMap.put("DUE","");
		commandMap.put("FREE","Y");
		commandMap.put("ACCUM","");
		List<Map<String,Object>> selectIsoDomFreeMiddleList = statusService.selectIsoDomMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoDomFreeMiddleList", selectIsoDomFreeMiddleList);
		
		List<Map<String,Object>> selectIsoOverseaFreeMiddleList = statusService.selectIsoOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoOverseaFreeMiddleList", selectIsoOverseaFreeMiddleList);
		
    	//누적
		commandMap.put("CUR","");
		commandMap.put("DUE","");
		commandMap.put("FREE","");
		commandMap.put("ACCUM","Y");
		List<Map<String,Object>> selectIsoDomAccumMiddleList = statusService.selectIsoDomMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoDomAccumMiddleList", selectIsoDomAccumMiddleList);
		
		List<Map<String,Object>> selectIsoOverseaAccumMiddleList = statusService.selectIsoOverseaMiddleList(commandMap.getMap());
    	mv.addObject("selectIsoOverseaAccumMiddleList", selectIsoOverseaAccumMiddleList);
		
		
		return mv;
	}
	
}
