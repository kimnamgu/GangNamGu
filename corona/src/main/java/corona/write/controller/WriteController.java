package corona.write.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import corona.common.common.CommandMap;
import corona.write.service.WriteService;

@Controller
public class WriteController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="writeService")
	private WriteService writeService;
	
	@RequestMapping(value="/write/confirmWrite.do")
    public ModelAndView confirmWrite(CommandMap commandMap) throws Exception{
    	System.out.println("confirmWrite≈Ω");
		ModelAndView mv = new ModelAndView("/write/confirmWrite");
    	
    	return mv;
    }
	
	@RequestMapping(value="/write/insertConfirmWrite.do")
	public ModelAndView insertConfirmWrite(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("insertConfirmWrite≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/confirmManage.do");
		
		writeService.insertConfirmWrite(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/write/updateConfirmView.do")
	public ModelAndView updateConfirmView(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateConfirmView≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("/write/confirmUpdateView");
		
		Map<String,Object> map = writeService.selectConfirmDetail(commandMap.getMap());
		System.out.println("map »Æ¿Œ : " + map.get("map"));
		
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/write/updateConfirm.do")
	public ModelAndView updateConfirm(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateConfirm≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/confirmManage.do");

		writeService.updateConfirm(commandMap.getMap(), request);
		
		return mv;
	}
	
	/*±π≥ª¿⁄∞°∞›∏Æ¿⁄*/
	@RequestMapping(value="/write/domesticWrite.do")
    public ModelAndView domesticWrite(CommandMap commandMap) throws Exception{
    	System.out.println("domesticWrite≈Ω");
		ModelAndView mv = new ModelAndView("/write/domesticWrite");
    	
    	return mv;
    }
	
	@RequestMapping(value="/write/insertDomesticWrite.do")
	public ModelAndView insertDomesticWrite(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("insertDomesticWrite≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/domesticContactManage.do");
		
		writeService.insertDomesticWrite(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/write/updateDomesticView.do")
	public ModelAndView updateDomesticView(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateDomesticView≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("/write/domesticUpdateView");
		
		Map<String,Object> map = writeService.selectDomesticDetail(commandMap.getMap());
		System.out.println("map »Æ¿Œ : " + map.get("map"));
		
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/write/updateDomestic.do")
	public ModelAndView updateDomestic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateDomestic≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/domesticContactManage.do");

		writeService.updateDomestic(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	/*«ÿø‹¿‘±π¿⁄*/
	@RequestMapping(value="/write/overseaWrite.do")
    public ModelAndView overseaWrite(CommandMap commandMap) throws Exception{
    	System.out.println("overseaWrite≈Ω");
		ModelAndView mv = new ModelAndView("/write/overseaWrite");
    	
    	return mv;
    }
	
	@RequestMapping(value="/write/insertOverseaWrite.do")
	public ModelAndView insertOverseaWrite(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("insertOverseaWrite≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/overseaManage.do");
		
		writeService.insertOverseaWrite(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/write/updateOverseaView.do")
	public ModelAndView updateOverseaView(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateOverseaView≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("/write/overseaUpdateView");
		
		Map<String,Object> map = writeService.selectOverseaDetail(commandMap.getMap());
		System.out.println("map »Æ¿Œ : " + map.get("map"));
		
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/write/updateOversea.do")
	public ModelAndView updateOversea(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateConfirm≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/overseaManage.do");

		writeService.updateOversea(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	/*ªÛ¥„πŒø¯*/
	@RequestMapping(value="/write/consultWrite.do")
    public ModelAndView consultWrite(CommandMap commandMap) throws Exception{
    	System.out.println("consultWrite≈Ω");
		ModelAndView mv = new ModelAndView("/write/consultWrite");
    	
    	return mv;
    }
	
	@RequestMapping(value="/write/insertConsultWrite.do")
	public ModelAndView insertConsultWrite(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("insertConsultWrite≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/consultManage.do");
		
		writeService.insertConsultWrite(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/write/updateConsultView.do")
	public ModelAndView updateConsultView(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateConsultView≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("/write/consultUpdateView");
		
		Map<String,Object> map = writeService.selectConsultDetail(commandMap.getMap());
		System.out.println("map »Æ¿Œ : " + map.get("map"));
		
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	
	
	@RequestMapping(value="/write/updateConsult.do")
	public ModelAndView updateConsult(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateConfirm≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/consultManage.do");

		writeService.updateConsult(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	/*º±∫∞¡¯∑·º“*/
	@RequestMapping(value="/write/clinicWrite.do")
    public ModelAndView clinicWrite(CommandMap commandMap) throws Exception{
    	System.out.println("clinicWrite≈Ω");
		ModelAndView mv = new ModelAndView("/write/clinicWrite");
    	
    	return mv;
    }
	
	@RequestMapping(value="/write/insertClinicWrite.do")
	public ModelAndView insertClinicWrite(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("insertClinicWrite≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/clinicManage.do");
		
		writeService.insertClinicWrite(commandMap.getMap(), request);
		
		return mv;
	}
	
	@RequestMapping(value="/write/updateClinicView.do")
	public ModelAndView updateClinicView(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateClinicView≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("/write/clinicUpdateView");
		
		Map<String,Object> map = writeService.selectClinicDetail(commandMap.getMap());
		System.out.println("map »Æ¿Œ : " + map.get("map"));
		
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	
	@RequestMapping(value="/write/updateClinic.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateClinic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateClinic≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		Map<String, Object> map = new HashMap<String,Object>();

		writeService.updateClinic(commandMap.getMap(), request);
		
		return map;
	}
	/*
	@RequestMapping(value="/write/updateClinic.do")
	public ModelAndView updateClinic(CommandMap commandMap, HttpServletRequest request) throws Exception{
		System.out.println("updateClinic≈Ω");
		System.out.println("commandMap »Æ¿Œ : " + commandMap.getMap()); 
		
		ModelAndView mv = new ModelAndView("redirect:/manage/clinicManage.do");

		writeService.updateClinic(commandMap.getMap(), request);
		
		return mv;
	}*/
	
	
	

	
}
