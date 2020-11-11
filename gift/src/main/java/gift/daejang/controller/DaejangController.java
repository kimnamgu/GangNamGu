package gift.daejang.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import gift.common.common.CommandMap;
import gift.daejang.service.DaejangService;

@Controller
public class DaejangController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	@RequestMapping(value="/daejang/giftAcceptList.do")
    public ModelAndView giftAcceptList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/giftAcceptList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectgiftAcceptList.do")
    public ModelAndView selectGiftAcceptList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectGiftAcceptList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/giftPrintList.do")
    public ModelAndView giftPrintList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/giftPrintList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectGiftPrintList.do")
    public ModelAndView selectGiftPrintList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectGiftPrintList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/daejang/giftAcceptWrite.do")
	public ModelAndView giftAcceptWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftAcceptWrite");
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/insertGiftAccept.do")
	public ModelAndView insertGiftAccept(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftAcceptList.do");
		
		daejangService.insertGiftAccept(commandMap.getMap(), request);
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/giftAcceptDetail.do")
	public ModelAndView giftAcceptDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftAcceptDetail");
		
		Map<String,Object> map = daejangService.giftAcceptDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/giftAcceptUpdate.do")
	public ModelAndView giftAcceptUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftAcceptUpdate");
		
		Map<String,Object> map = daejangService.giftAcceptDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	

	@RequestMapping(value="/daejang/updateGiftAccept.do")
	public ModelAndView updateGiftAccept(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftAcceptList.do");
		
		daejangService.updateGiftAccept(commandMap.getMap(), request);		
		mv.addObject("ID", commandMap.get("ID"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/deleteGiftAccept.do")
	public ModelAndView deleteGiftAccept(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftAcceptList.do");
		
		daejangService.deleteGiftAccept(commandMap.getMap());
		
		return mv;
	}
	
	
	
	
	
	@RequestMapping(value="/daejang/giftMngStatus.do")
	public ModelAndView giftMngStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftMngStatus");
		
		Map<String,Object> map = daejangService.giftMngStatus(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/giftMngDetail.do")
	public ModelAndView giftMngDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftMngDetail");
		
		Map<String,Object> map = daejangService.giftMngStatus(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/giftMngWrite.do")
	public ModelAndView giftMngWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftMngWrite");
		
		Map<String,Object> map = daejangService.giftMngStatus(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/insertGiftMng.do")
	public ModelAndView insertGiftMng(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftMngDetail.do");
		
		daejangService.insertGiftMng(commandMap.getMap(), request);
		mv.addObject("MID", commandMap.get("MID"));
		mv.addObject("GIFT_NM", commandMap.get("GIFT_NM"));
		mv.addObject("GIFT_STD", commandMap.get("GIFT_STD"));
		mv.addObject("GIFT_MAN", commandMap.get("GIFT_MAN"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/updateGiftMng.do")
	public ModelAndView updateGiftMng(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftMngDetail.do");
		
		daejangService.updateGiftMng(commandMap.getMap(), request);
		mv.addObject("MID", commandMap.get("MID"));
		mv.addObject("GIFT_NM", commandMap.get("GIFT_NM"));
		mv.addObject("GIFT_STD", commandMap.get("GIFT_STD"));
		mv.addObject("GIFT_MAN", commandMap.get("GIFT_MAN"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/deleteGiftMng.do")
	public ModelAndView deleteGiftMng(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftMngDetail.do");
		
		daejangService.deleteGiftMng(commandMap.getMap(), request);
		mv.addObject("MID", commandMap.get("MID"));		
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/giftSellStatus.do")
	public ModelAndView giftSellStatus(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftSellStatus");
		
		Map<String,Object> map = daejangService.giftSellStatus(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/giftSellDetail.do")
	public ModelAndView giftSellDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftSellDetail");
		
		Map<String,Object> map = daejangService.giftSellStatus(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/giftSellWrite.do")
	public ModelAndView giftSellWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/giftSellWrite");
		
		Map<String,Object> map = daejangService.giftSellStatus(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/daejang/insertGiftSell.do")
	public ModelAndView insertGiftSell(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftSellDetail.do");
		
		daejangService.insertGiftSell(commandMap.getMap(), request);
		mv.addObject("MID", commandMap.get("MID"));
		mv.addObject("GIFT_NM", commandMap.get("GIFT_NM"));
		mv.addObject("GIFT_STD", commandMap.get("GIFT_STD"));
		mv.addObject("GIFT_MAN", commandMap.get("GIFT_MAN"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/updateGiftSell.do")
	public ModelAndView updateGiftSell(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftSellDetail.do");
		
		daejangService.updateGiftSell(commandMap.getMap(), request);
		mv.addObject("MID", commandMap.get("MID"));
		mv.addObject("GIFT_NM", commandMap.get("GIFT_NM"));
		mv.addObject("GIFT_STD", commandMap.get("GIFT_STD"));
		mv.addObject("GIFT_MAN", commandMap.get("GIFT_MAN"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/daejang/deleteGiftSell.do")
	public ModelAndView deleteGiftSell(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/giftSellDetail.do");
		
		daejangService.deleteGiftSell(commandMap.getMap(), request);
		mv.addObject("MID", commandMap.get("MID"));		
		
		return mv;
	}
	
}
