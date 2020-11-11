package service.visitor.controller;

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
import org.springframework.web.servlet.ModelAndView;

import service.common.common.CommandMap;
import service.common.util.CommonUtils;
import service.common.util.Nvl;
import service.visitor.service.VisitorService;

@Controller
public class VisitorController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="visitorService")
	private VisitorService visitorService;
	
	
	@RequestMapping(value="/api/QR/getVisitorInfo.do",method=RequestMethod.POST)
	public ModelAndView getVisitorInfo(@RequestBody Map<String, Object> params,HttpServletRequest request){
		System.out.println("getVisitorInfo 타는지 확인222");

		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String, Object> rtmap = new HashMap<>();
		Map<String, Object> rtfootmap = new HashMap<>();
		Map<String, Object> rtlocmap = new HashMap<>();
		
		log.debug("getVisitorInfo val= [" + params.toString() + "]");
		
		String result = null;
		String header = request.getHeader("Authorization");
		
		if(header != null && header.toString().equals("Bearer E82E6C125F3D3145EA41F7ED546DC4C2F590BC03ABE8DD81EB7C687CF4867810")){
			
			try {
				rtmap = visitorService.getVisitorInfo(params);
				rtlocmap = visitorService.getVisitorLocationInfo(params);
				if(rtmap.get("uuid").equals(params.get("uuid"))){ //아이디값이 동일할경우 true 리턴
					//사용자 정보가 있을경우 방문기록 남김
					rtfootmap = visitorService.insertFootprint(params);
					
					if(rtfootmap.get("result").equals(false)){ //수행오류가 생길경우
						result = "false";
					}else{
						result = "true";
					}
					
				}else{
					result = "false";
				}
			} catch (Exception e) {
				result = "false";
			}
			mv.addObject("result", result);
			mv.addObject("sitecode",rtlocmap.get("sitecode"));
			mv.addObject("x",rtlocmap.get("x_location"));
			mv.addObject("y",rtlocmap.get("y_location"));
		
		}else{ //인증키오류
			mv.addObject("result", "false");
			mv.addObject("sitecode",rtlocmap.get("sitecode"));
			mv.addObject("x",rtlocmap.get("x_location"));
			mv.addObject("y",rtlocmap.get("y_location"));
		}
		
		
		System.out.println("getVisitorInfo 시  result확인 : "  + result );
	
		return mv;
	}

	
	@RequestMapping(value="/api/QR/insertVisitorInfo.do",method=RequestMethod.POST)
	public ModelAndView insertVisitorInfo(@RequestBody Map<String, Object> params,HttpServletRequest request){

		System.out.println("insertVisitorInfo 타는지 확인2");
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String, Object> rtmap = new HashMap<>();
		Map<String, Object> rtfootmap = new HashMap<>();
		
		log.debug("insertVisitorInfo val= [" + params.toString() + "]");
		
		String result = null;
		String header = request.getHeader("Authorization");
		
		if(header != null && header.toString().equals("Bearer E82E6C125F3D3145EA41F7ED546DC4C2F590BC03ABE8DD81EB7C687CF4867810")){
				
			
			try {
				rtmap = visitorService.insertVisitorInfo(params);
				
				if(rtmap.get("result").equals(false)){ //수행오류가 생길경우
					result = "false";
					
				}else{
					
					//사용자 정보가 저장되면 방문기록 남김
					rtfootmap = visitorService.insertFootprint(params);
					
					if(rtfootmap.get("result").equals(false)){ //수행오류가 생길경우
						result = "false";
					}else{
						result = "true";
					}
				}
			} catch (Exception e) {
				result = "false";
			}
		
			mv.addObject("result", result);
		
		}else{ //인증키오류
			mv.addObject("result", "false");
		}
			
		return mv;
	}
	
	@RequestMapping(value="/api/QR/updateVisitorInfo.do")
	public ModelAndView updateVisitorInfo(@RequestBody Map<String, Object> params){
		System.out.println("updateVisitorInfo 타는지 확인");
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String, Object> rtmap = new HashMap<>();
		
		log.debug("updateVisitorInfo val= [" + params.toString() + "]");
		
		String result = null;
		String msg = null;
		
		try {
			rtmap = visitorService.updateVisitorInfo(params);
			
			if(rtmap.get("result").equals(false)){ //수행오류가 생길경우
				result = "False";
				msg = "Fail";
			}else{
				result = "True";
				msg = "Success";
			}
		} catch (Exception e) {
			result = "False";
			msg = "Fail";
			//e.printStackTrace();
		}
		
		mv.addObject("result", result);
		mv.addObject("msg",msg);
		
		return mv;
	}
	
	@RequestMapping(value="/api/QR/insertFootprint.do")
	public ModelAndView insertFootprint(@RequestBody Map<String, Object> params){
		System.out.println("insertFootprint 타는지 확인");
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String, Object> rtmap = new HashMap<>();
		
		log.debug("insertFootprint val= [" + params.toString() + "]");
		
		String result = null;
		String msg = null;
		
		try {
			rtmap = visitorService.insertFootprint(params);
			
			if(rtmap.get("result").equals(false)){ //수행오류가 생길경우
				result = "False";
				msg = "Fail";
			}else{
				result = "True";
				msg = "Success";
			}
		} catch (Exception e) {
			result = "False";
			msg = "Fail";
			//e.printStackTrace();
		}
		
		mv.addObject("result", result);
		mv.addObject("msg",msg);
		
		return mv;
		
	}
	

	@RequestMapping(value="/visitor/visitorLogin.do")
    public ModelAndView visitorLogin(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/visitor/visitorLogin");
    	return mv;
    }
	
	//방문기록 확인 화면 접근 인터셉터 확인을 위해 visitor 을 넣어야함
	@RequestMapping(value="/visitor/visitorFootprintList.do")
    public ModelAndView visitorFootprintList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/visitor/visitorFootprintList");
    	return mv;
    }
	
	@RequestMapping(value="/visitor/selectVisitorFootprintList.do")
    public ModelAndView selectVisitorFootprintList(CommandMap commandMap) throws Exception{
		log.debug("selectVisitorFootprintList param2= [" + commandMap.getMap().toString() + "]");
    	//제우스 서버에서 깨지는거 떄문에 이렇게 넣음
    	commandMap.put("name", Nvl.nvlStr(CommonUtils.ascToksc(commandMap.getMap().get("name").toString())));
    	commandMap.put("gigwan_name", Nvl.nvlStr(CommonUtils.ascToksc(commandMap.getMap().get("gigwan_name").toString())));
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = visitorService.selectVisitorFootprintList(commandMap.getMap());
    	List<Map<String,Object>> statisticsList = visitorService.selectStatisticsList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	mv.addObject("statisticsList", statisticsList);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value = "/visitor/downloadExcelFile.do")
    public ModelAndView downloadExcelFile(CommandMap commandMap,Model model) throws Exception {
		log.debug("1param val= [" + commandMap.getMap().toString() + "]");
        
		List<Map<String,Object>> resultlist = visitorService.selectVisitorFootprintListExcel(commandMap.getMap());
        
		ModelAndView mv = new ModelAndView("/visitor/excelConvert");
		mv.addObject("resultlist",resultlist);
		return mv;
    }
	
}
