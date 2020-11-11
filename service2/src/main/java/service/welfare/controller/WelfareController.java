package service.welfare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import service.common.common.CommandMap;
import service.common.service.CommonService;
import service.common.util.CommonUtils;
import service.common.util.FileUtils;
import service.common.util.Nvl;
import service.daejang.service.DaejangService;
import service.welfare.service.WelfareService;

@CrossOrigin(origins = "*")
@Controller
public class WelfareController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="welfareService")
	private WelfareService welfareservice;
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	/* INSERT API */
	@RequestMapping(value="/api/welfare/welfareBenefitAppInsert.do",method=RequestMethod.POST)
	public ModelAndView tesApiController(@RequestBody Map<String, Object> params,HttpServletRequest request){
		
		log.debug("params11 :: " + params.toString());
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String, Object> rtmap = new HashMap<>();
		
		log.debug("tesApiController value = [" + params.toString() + "]");
		
		boolean result = false;
		try {
				log.debug("없음");
				params.put("confirmYn" , "N");
				rtmap = welfareservice.welfarInsert(params);
				
				if(rtmap.get("result").equals(false)){ //수행오류가 생길경우
					result = false;
				}else{
					result = true;
				}
			
		} catch (Exception e) {
			result = false;
		}
	
		mv.addObject("result", result);
		
			
		return mv;
	}
	
	
	// SELECT API
	@RequestMapping(value="/api/welfare/welfareBenefitAppSelect.do",method=RequestMethod.POST)
	public ModelAndView welfareBenefitAppSelect(@RequestBody Map<String, Object> params,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		params.put("daoGubunCode", "00");
		
		boolean result = false;
		
		String uuid = (String)params.get("uuid");
		String ci = (String)params.get("ci");
		
		if(uuid.equals("")){
			params.put("uuid", "");
		}		
		if(ci.equals("") ){
			params.put("ci", "");
		}
		
		try {
			List<Map<String,Object>> resultData = welfareservice.welfarSelect(params);
			if(resultData.size() > 0){
				log.debug("데이터있음");
				result = true;
				mv.addObject("resultData", resultData);
			}else{
				log.debug("없음");
				result = false;
			}
			
		} catch (Exception e) {
			result = false;
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	
	// LOGIN CONTROLLER
	@RequestMapping(value="/welfare/welfareLogin.do")
    public ModelAndView welfareLogin(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/welfare/welfareLogin");
    	return mv;
    }
	
	// LOGIN RETURN CONTROLLER
	@RequestMapping(value="/welfare/welfareLoginList.do")
	public ModelAndView iljaliLogin(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/welfare/welfareFootprintList.do");
		Map<String, Object> map2 = new HashMap<String,Object>();
		Map<String, Object> m_uinfo = new HashMap<String,Object>();
		
		
		
		Map<String,Object> map_i = welfareservice.loginProcessId(commandMap.getMap(), request);
		Map<String,Object> map_p = welfareservice.loginProcessPw(commandMap.getMap(), request);
		
		String StrId = commandMap.getMap().get("USER_ID").toString();
		
		request.getSession().invalidate();
		
		if(map_i.get("map_i") != null){
			
			if(map_p.get("map_p") != null){				
				map2 = (HashMap<String,Object>) map_p.get("map_p");
				
				if("Z".equals(map2.get("USER_STATUS")))
				{
					log.debug("============== 아이디 확인 ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("/welfare/welfare_msgOut");
					mv.addObject("flag", "1");
				}
				else
				{	
					m_uinfo.put("userId", map2.get("USER_ID"));
					m_uinfo.put("userName", map2.get("USER_NAME"));
					m_uinfo.put("deptId", map2.get("DEPT_ID"));
					m_uinfo.put("deptName", map2.get("DEPT_NAME"));
					m_uinfo.put("classId", map2.get("CLASS_ID"));
					m_uinfo.put("className", map2.get("CLASS_NAME"));
					m_uinfo.put("positionId", map2.get("POSITION_ID"));
					m_uinfo.put("positionName", map2.get("POSITION_NAME"));																			
					m_uinfo.put("userright", map2.get("USER_RIGHT"));
					m_uinfo.put("usertel", Nvl.nvlStr(map2.get("USER_TEL")));
					m_uinfo.put("deptName", Nvl.nvlStr(map2.get("DEPT_NAME")));
					request.getSession().setAttribute("userinfo", m_uinfo);	
					request.getSession().setMaxInactiveInterval(150 * 60);
					
					log.debug("login id=[" + map2.get("USER_ID") + "] name=[" + map2.get("USER_NAME") + "]");
				}
			}
			else{			
				log.debug("============== StrId 확인============= id=[" + StrId + "]\t");
				 mv = new ModelAndView("/welfare/welfare_msgOut");
				 mv.addObject("flag", "4");
			}
		}
		else{			
			log.debug("============== StrId 확인============= id=[" + StrId + "]\t");
			 mv = new ModelAndView("/welfare/welfare_msgOut");
			 mv.addObject("flag", "3");
		}
		
		return mv;
	}
	
	// LOGIN VIEW
	@RequestMapping(value="/welfare/welfareFootprintList.do")
    public ModelAndView iljaliList(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/welfare/welfareFootprintList");
    	return mv;
    }
	
	
	

	// VIEW DATA
	@RequestMapping(value="/api/v1/welfareViewList.do")
    public ModelAndView selectDocIssueReserveViewList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("jsonView");
		log.debug("AA11 : " + commandMap.toString());
		commandMap.put("deptName", Nvl.nvlStr(CommonUtils.ascToksc(commandMap.getMap().get("deptName").toString())));
    	//제우스 서버에서 깨지는거 떄문에 이렇게 넣음
//    	commandMap.put("apply_uname", Nvl.nvlStr(CommonUtils.ascToksc(commandMap.getMap().get("apply_uname").toString())));
    	
    	
    	List<Map<String,Object>> list = welfareservice.welfarSelect(commandMap.getMap());
//    	List<Map<String,Object>> statisticsList = daejangService.selectStatisticsList(commandMap.getMap());
//    	Map<String,Object> recentData  = daejangService.getDocIssueReserveRecent();
    	
    	mv.addObject("list", list);
//    	mv.addObject("statisticsList", statisticsList);
//    	mv.addObject("recentData", recentData);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	// STAT UPDATE (데이터받아오는 작업한후에 네임만 맞쳐주면 됨 현재로썬 미구현)
	@RequestMapping(value="/welfare/processDocIssueReserve.do")
	public ModelAndView processDocIssueReserve(@RequestBody Map<String, Object> params, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		Map<String, Object> rtmap = new HashMap<>();
		log.debug("test params val= [" + params.toString() + "]");
		
		
		try {
			rtmap = welfareservice.updateDocIssueReserve(params, request);
			
			mv.addObject("result",  rtmap.get("result"));
			mv.addObject("msg",rtmap.get("msg"));
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("result",  "false");
			mv.addObject("msg","fail");
		}
		
		return mv;
	}
	
}
