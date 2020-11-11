package ffems.daejang.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import ffems.common.common.CommandMap;
import ffems.daejang.service.DaejangService;

@Controller
public class DaejangController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	@RequestMapping(value="/daejang/ffemsDataList.do")
    public ModelAndView mainSaupList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/ffemsDataList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectFfemsDataList.do")
    public ModelAndView selectFfemsDataList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectFfemsDataList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/daejang/ffemsDataDetail.do")
	public ModelAndView ffemsDataDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/ffemsDataDetail");
		
		Map<String,Object> map = daejangService.ffemsDataDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
				
		return mv;
	}
	

	
	@RequestMapping(value="/daejang/ffemsDataUpload.do")
	public ModelAndView mainSaupWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/ffemsDataUpload");
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/insertFfemsData.do")
	public ModelAndView insertFfemsData(CommandMap commandMap, MultipartHttpServletRequest request) throws Exception{
			
		ModelAndView mv = new ModelAndView("redirect:/LoginMsgOut.do");
		String spath = null;
		int rtn = 0;
		
		MultipartFile excelFile =request.getFile("uploadFile");
		System.out.println("엑셀 파일 업로드 컨트롤러");
		
        if(excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("엑셀파일을 선택 해 주세요.");        	
        }
               
        String profiles = currentProfile();
       
        if("prod".equals(profiles))
        {        	
        	spath = "/usr/local/server/file/ffems/";
        }
        else{        	        
        	spath = "D:\\imsi\\";        	
        }
        
        File destFile = new File(spath+excelFile.getOriginalFilename());
        
        System.out.println("===============================>>>>> ");
        try{
            excelFile.transferTo(destFile);
        }catch(IllegalStateException | IOException e){        	
            throw new RuntimeException(e.getMessage(),e);        	
        }        
        
        try {
        	rtn = daejangService.excelUpload(commandMap.getMap(), request, destFile);
        	System.out.println("out controller =[" + rtn + "]");
        	FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
			
        	fm.put("gb", "2");
			if(rtn > 0)
			{				
				fm.put("flag", "6");
			}
			else{
				fm.put("flag", "5");
			}	
        } catch ( Exception e) {        	
            //e.printStackTrace();
        	log.debug(e.getMessage());
        	
        }
		return mv;
	}
	
	

		
	@Autowired ConfigurableWebApplicationContext subContext;
    private String currentProfile(){ 
    	String[] profiles = subContext.getEnvironment().getActiveProfiles(); 
    	
    	if( profiles.length==0 ){ 
    		profiles = subContext.getEnvironment().getDefaultProfiles(); 
    	} 
    	return profiles[0];    	
    }
}
