package bims.daejang.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bims.daejang.service.DaejangService;

@Controller
public class DaejangApiController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	//������ȸ
	@RequestMapping(value="/daejang/api/selectApiMainSaupList.do",method=RequestMethod.POST)
    public ModelAndView selectApiMainSaupList(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception{
		System.out.println("selectApiMainSaupListŽ");
		System.out.println("params Ȯ�� : " + params.toString());
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectApiMainSaupList(params);
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	//�̷���ȸ
	@RequestMapping(value="/daejang/api/selectMainSaupSubList.do",method=RequestMethod.POST)
    public ModelAndView selectMainSaupSubList(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectMainSaupSubList(params);
    	mv.addObject("list", list);
    	
    	return mv;
    }
	
	//����� ��ȸ
	@RequestMapping(value="/daejang/api/mainSaupDetail.do",method=RequestMethod.POST)
	public ModelAndView mainSaupDetail(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String,Object> map = daejangService.mainSaupDetail(params);
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	
	//�⺻���� ��ȸ
	@RequestMapping(value="/daejang/api/mainSaupBasicDetail.do",method=RequestMethod.POST)
	public ModelAndView mainSaupBasicDetail(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String,Object> map = daejangService.mainSaupBasicDetail(params);
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/api/mainSaupSubDetail.do",method=RequestMethod.POST)
	public ModelAndView mainSaupSubDetail(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		Map<String,Object> map = daejangService.mainSaupSubDetail(params);
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/api/mSaupList.do",method=RequestMethod.POST)
	public ModelAndView mSaupList(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.mSaupList(params);
    	mv.addObject("list", list);
    	
    
    	return mv;
    }
	
}
