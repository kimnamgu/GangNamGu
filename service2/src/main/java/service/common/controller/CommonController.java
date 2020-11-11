package service.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import service.common.common.CommandMap;
import service.common.service.CommonService;
import service.common.util.Nvl;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/common/downloadFile.do")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception{
		Map<String,Object> map = commonService.selectFileInfo(commandMap.getMap());
		String storedFileName = (String)map.get("STORED_FILE_NAME");
		String originalFileName = (String)map.get("ORIGINAL_FILE_NAME");
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\dev\\file\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	
	
	
	
	@RequestMapping(value="/common/ssoLogin.do")
	public ModelAndView ssoLogin(CommandMap commandMap, HttpServletRequest request, RedirectAttributes redirecAttributes) throws Exception{
		
		ModelAndView mv = new ModelAndView("redirect:/docIssueReserveList.do");		
		String deStrId = null;
		String StrId = request.getParameter("userid");
		
		log.debug("*********get id=[" + StrId + "]\t");
		log.debug("*********get id getbyete=[" + StrId.getBytes() + "]\t");
		
		byte[] encodedBytes = Base64.encodeBase64(StrId.getBytes()); /* base64 decoding */ 
		byte[] decodedBytes = Base64.decodeBase64(StrId); 
		
		deStrId =  new String(decodedBytes); 
		System.out.println("占쏙옙占쌘듸옙 text : " + new String(encodedBytes));
		System.out.println("占쏙옙占쌘듸옙 text : " + deStrId);
		
		commandMap.put("USER_ID", deStrId);
		Map<String,Object> map = commonService.ssoLogin(commandMap.getMap());
		
		Map<String, Object> msso = new HashMap<String,Object>();
		Map<String, Object> mtmp = new HashMap<String,Object>();
		Map<String, Object> mtmp2 = new HashMap<String,Object>();
		
		request.getSession().invalidate();
		
		if(map.get("map") != null){
									
			//userid 占쌍댐옙占쏙옙 체크			
			Map<String,Object> map2 = commonService.loginProcessId(commandMap.getMap(), request);
			
			if(map2.get("map_i") == null){
				log.debug("sso ============== user 占쏙옙占싱븝옙 占쏙옙占쏙옙  ============= id=[" + StrId + "]\t");
				mv = new ModelAndView("redirect:/LoginMsgOut.do");
				redirecAttributes.addFlashAttribute("map", map.get("map"));
				
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
				fm.put("gb", "1");   //view 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙
			
			}
			else{	
				
				mtmp = null;
				mtmp = (HashMap<String,Object>) map2.get("map_i");
				
				if("Z".equals(mtmp.get("USER_STATUS")))
				{
					log.debug("sso ============== 占쏙옙占싸안듸옙  ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("redirect:/LoginMsgOut.do");
					FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
					fm.put("gb", "2");    //view 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙
					fm.put("flag", "1");  //占쌨쇽옙占쏙옙 占쏙옙占쏙옙占쏙옙占�
					
				}
				else{
					
					msso = (HashMap<String,Object>) map.get("map");
					String user_right = (String) mtmp.get("USER_RIGHT");
					 
					mtmp2.put("userId", msso.get("USER_ID"));
					mtmp2.put("userName", msso.get("USER_NAME"));
					mtmp2.put("deptId", msso.get("DEPT_ID"));
					mtmp2.put("deptName", msso.get("DEPT_NAME"));
					mtmp2.put("classId", msso.get("CLASS_ID"));
					mtmp2.put("className", msso.get("CLASS_NAME"));
					mtmp2.put("positionId", Nvl.nvlStr(msso.get("POSITION_ID")));
					mtmp2.put("positionName", Nvl.nvlStr(msso.get("POSITION_NAME")));										
					mtmp2.put("userright", user_right);
					mtmp2.put("usertel", Nvl.nvlStr(msso.get("TEL")));
					request.getSession().setAttribute("userinfo", mtmp2);	
					request.getSession().setMaxInactiveInterval(150 * 60);

					commandMap.put("USER_NAME", msso.get("USER_NAME"));			
					commandMap.put("DEPT_ID", msso.get("DEPT_ID"));
					commandMap.put("DEPT_NAME", msso.get("DEPT_NAME"));
					commandMap.put("CLASS_ID", msso.get("CLASS_ID"));
					commandMap.put("CLASS_NAME", msso.get("CLASS_NAME"));
					commandMap.put("POSITION_ID", Nvl.nvlStr(msso.get("POSITION_ID")));
					commandMap.put("POSITION_NAME", Nvl.nvlStr(msso.get("POSITION_NAME")));
					commandMap.put("USER_TEL", Nvl.nvlStr(msso.get("TEL")));
					
					commonService.updateUserinfo(commandMap.getMap());
					
					log.debug("sso login id=[" + msso.get("USER_ID") + "] name=[" + msso.get("USER_NAME") + "]");
				}
			}
			
			
		}
		else{			
			 log.debug("sso ============== 占싣울옙 占쏙옙占쏙옙 占쏙옙占싱듸옙  ============= id=[" + StrId + "]\t");		 
			 mv = new ModelAndView("redirect:/LoginMsgOut.do");
			 FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
			 fm.put("gb", "1");	//view 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙		   
		}
		return mv;
	}
	
	
	
	//=========================================================================================================
	@RequestMapping(value="/common/login.do")
	public ModelAndView login(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		String word ="인코딩 문제인가? 이클립스 문제인가? WAS문제 인가 그것이 알고 싶다....";
		System.out.println("utf-8 -> euc-kr        : " +new String(word.getBytes("utf-8"),"euc-kr"));
		System.out.println("utf-8 -> ksc5601       : " +new String(word.getBytes("utf-8"),"ksc5601"));
		System.out.println("utf-8 -> x-windows-949 : " +new String(word.getBytes("utf-8"),"x-windows-949"));
		System.out.println("utf-8 -> iso-8859-1    : " +new String(word.getBytes("utf-8"),"iso-8859-1"));
		System.out.println("iso-8859-1 -> euc-kr        : " +new String(word.getBytes("iso-8859-1"),"euc-kr"));
		System.out.println("iso-8859-1 -> ksc5601       : " +new String(word.getBytes("iso-8859-1"),"ksc5601"));
		System.out.println("iso-8859-1 -> x-windows-949 : " +new String(word.getBytes("iso-8859-1"),"x-windows-949"));
		System.out.println("iso-8859-1 -> utf-8         : " +new String(word.getBytes("iso-8859-1"),"utf-8"));
		System.out.println("euc-kr -> utf-8         : " +new String(word.getBytes("euc-kr"),"utf-8"));
		System.out.println("euc-kr -> ksc5601       : " +new String(word.getBytes("euc-kr"),"ksc5601"));
		System.out.println("euc-kr -> x-windows-949 : " +new String(word.getBytes("euc-kr"),"x-windows-949"));
		System.out.println("euc-kr -> iso-8859-1    : " +new String(word.getBytes("euc-kr"),"iso-8859-1"));
		System.out.println("ksc5601 -> euc-kr        : " +new String(word.getBytes("ksc5601"),"euc-kr"));
		System.out.println("ksc5601 -> utf-8         : " +new String(word.getBytes("ksc5601"),"utf-8"));
		System.out.println("ksc5601 -> x-windows-949 : " +new String(word.getBytes("ksc5601"),"x-windows-949"));
		System.out.println("ksc5601 -> iso-8859-1    : " +new String(word.getBytes("ksc5601"),"iso-8859-1"));
		System.out.println("x-windows-949 -> euc-kr     : " +new String(word.getBytes("x-windows-949"),"euc-kr"));
		System.out.println("x-windows-949 -> utf-8      : " +new String(word.getBytes("x-windows-949"),"utf-8"));
		System.out.println("x-windows-949 -> ksc5601    : " +new String(word.getBytes("x-windows-949"),"ksc5601"));
		System.out.println("x-windows-949 -> iso-8859-1 : " +new String(word.getBytes("x-windows-949"),"iso-8859-1"));
		 


		ModelAndView mv = new ModelAndView("redirect:/docIssueReserveList.do");
		Map<String, Object> map2 = new HashMap<String,Object>();
		Map<String, Object> m_uinfo = new HashMap<String,Object>();
		
		Map<String,Object> map_i = commonService.loginProcessId(commandMap.getMap(), request);
		Map<String,Object> map_p = commonService.loginProcessPw(commandMap.getMap(), request);
		
		String StrId = commandMap.getMap().get("USER_ID").toString();
		
		request.getSession().invalidate();
		
		if(map_i.get("map_i") != null){
			
			if(map_p.get("map_p") != null){				
				map2 = (HashMap<String,Object>) map_p.get("map_p");
				
				if("Z".equals(map2.get("USER_STATUS")))
				{
					log.debug("============== 占쏙옙占싸안듸옙  ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("/main/msgOut");
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
					request.getSession().setAttribute("userinfo", m_uinfo);	
					request.getSession().setMaxInactiveInterval(150 * 60);
					
					log.debug("login id=[" + map2.get("USER_ID") + "] name=[" + map2.get("USER_NAME") + "]");
				}
			}
			else{			
				log.debug("============== 占싻쏙옙占쏙옙占쏙옙 틀占쏙옙 ============= id=[" + StrId + "]\t");
				 mv = new ModelAndView("/main/msgOut");
				 mv.addObject("flag", "4");
			}
		}
		else{			
			log.debug("============== 占쏙옙占싱듸옙 틀占쏙옙  ============= id=[" + StrId + "]\t");
			 mv = new ModelAndView("/main/msgOut");
			 mv.addObject("flag", "3");
		}
		
		return mv;
	}
	
		
	
	@RequestMapping(value="/LoginMsgOut.do")
	public ModelAndView LoginMsgOut(HttpServletRequest request) throws Exception{
		
		ModelAndView mv = null;
		String gb = null;  //view 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙
		String flag = null; //占쏙옙占� 占쌨쇽옙占쏙옙 占쏙옙占쏙옙
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);  
		if(flashMap !=null) {
			gb = (String)flashMap.get("gb");
			flag = (String)flashMap.get("flag");		   
		}  

		if("1".equals(gb)){
			mv = new ModelAndView("/main/noUseId");
		}
		else if("2".equals(gb)){
			mv = new ModelAndView("/main/msgOut");			
			mv.addObject("flag", flag);
		}
		return mv;
	}
	
	
	@RequestMapping(value="/common/insertUserinfo.do")
	public ModelAndView insertUserinfo(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/main/msgOut");
		mv.addObject("flag", "2");
		
		commonService.insertUserinfo(commandMap.getMap(),  request);
		
		return mv;
	}
	
	@RequestMapping(value="/main.do")
	public ModelAndView main(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/main");
		
	
		return mv;
	}
	
	
	@RequestMapping(value="/top.do")
	public ModelAndView top(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/top");
		
	
		return mv;
	}
	
	
	@RequestMapping(value="/mbody.do")
	public ModelAndView mbody(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/mbody");
		
	
		return mv;
	}
	

	@RequestMapping(value="/gofirst.do")
	public ModelAndView gofirst(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/gofirst");
		
	
		return mv;
	}
	
	@RequestMapping(value="/logout.do")
	public ModelAndView logout(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/logout");
		
	
		return mv;
	}
	
	@RequestMapping(value="/logoutVisitor.do")
	public ModelAndView logoutVisitor(CommandMap commandMap, HttpServletRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/logoutVisitor");
		
	
		return mv;
	}
	
	
	@RequestMapping(value="/common/yearList.do")
	public ModelAndView yearList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.yearList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	@RequestMapping(value="/common/dongList.do")
	public ModelAndView dongList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.dongList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	
	
	@RequestMapping(value="/common/idApproveList.do")
    public ModelAndView idApproveList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/common/idApproveList");
		
    	return mv;
    }
	
	@RequestMapping(value="/common/selectIdApproveList.do")
    public ModelAndView selectIdApproveList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.selectIdApproveList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/common/updateIdApprove.do")
	public ModelAndView updateIdApprove(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/common/idApproveList.do");
		
		commonService.updateIdApprove(commandMap.getMap());		
		
		return mv;
	}
	
	
	@RequestMapping(value="/common/idApproveListDetail.do")
	public ModelAndView idApproveListDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/common/idApproveListDetail");
		
		Map<String,Object> map = commonService.idApproveListDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		
		return mv;
	}
	
	@RequestMapping(value="/common/updateIdApproveList.do")
	public ModelAndView updateIdApproveList(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/common/idApproveList.do");
		
		commonService.updateIdApproveList(commandMap.getMap());
		log.debug("status=" + "[" + commandMap.get("USER_STATUS") + "]");
		mv.addObject("USER_STATUS", commandMap.get("USER_STATUS"));		
	
		return mv;
	}
	
	@RequestMapping(value="/common/deleteIdApproveList.do")
	public ModelAndView deleteIdApproveList(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/common/idApproveList.do");
		
		commonService.deleteIdApproveList(commandMap.getMap());
		mv.addObject("USER_STATUS", commandMap.get("USER_STATUS"));	
		
		return mv;
	}
	
	
	@RequestMapping(value="/common/popJikWonList.do")
	public ModelAndView popJikWonList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.popJikWonList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    
    	return mv;
    }
	
	
	@RequestMapping(value="/common/deptList.do")
    public ModelAndView deptList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.deptList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	@RequestMapping(value="/common/visitorLogin.do")
	public ModelAndView visitorLogin(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/visitor/visitorFootprintList.do");
		Map<String, Object> map2 = new HashMap<String,Object>();
		Map<String, Object> m_uinfo = new HashMap<String,Object>();
		
		Map<String,Object> map_i = commonService.loginProcessId(commandMap.getMap(), request);
		Map<String,Object> map_p = commonService.loginProcessPw(commandMap.getMap(), request);
		
		String StrId = commandMap.getMap().get("USER_ID").toString();
		
		//보안 강화를 위한 아이피 확인 추가
/*		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();
			
			System.out.println("local ip : " + ip);
			if(!ip.substring(0, 9).equals("98.23.113")){
				 mv = new ModelAndView("/main/visitor_msgOut");
				 mv.addObject("flag", "5");
				 return mv;
			}
			
		} catch (UnknownHostException e1) {
			 mv = new ModelAndView("/main/visitor_msgOut");
			 mv.addObject("flag", "5");
			 return mv;
		}*/

		
		
		request.getSession().invalidate();
		
		if(map_i.get("map_i") != null){
			
			if(map_p.get("map_p") != null){				
				map2 = (HashMap<String,Object>) map_p.get("map_p");
				
				if("Z".equals(map2.get("USER_STATUS")))
				{
					log.debug("============== 아이디 확인 ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("/main/visitor_msgOut");
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
					request.getSession().setAttribute("userinfo", m_uinfo);	
					request.getSession().setMaxInactiveInterval(150 * 60);
					
					log.debug("login id=[" + map2.get("USER_ID") + "] name=[" + map2.get("USER_NAME") + "]");
				}
			}
			else{			
				log.debug("============== StrId 확인============= id=[" + StrId + "]\t");
				 mv = new ModelAndView("/main/visitor_msgOut");
				 mv.addObject("flag", "4");
			}
		}
		else{			
			log.debug("============== StrId 확인============= id=[" + StrId + "]\t");
			 mv = new ModelAndView("/main/visitor_msgOut");
			 mv.addObject("flag", "3");
		}
		
		return mv;
	}
	
	@RequestMapping(value="/common/iljaliLogin.do")
	public ModelAndView iljaliLogin(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/iljali/iljaliList.do");
		Map<String, Object> map2 = new HashMap<String,Object>();
		Map<String, Object> m_uinfo = new HashMap<String,Object>();
		
		Map<String,Object> map_i = commonService.loginProcessId(commandMap.getMap(), request);
		Map<String,Object> map_p = commonService.loginProcessPw(commandMap.getMap(), request);
		
		String StrId = commandMap.getMap().get("USER_ID").toString();
		
		request.getSession().invalidate();
		
		if(map_i.get("map_i") != null){
			
			if(map_p.get("map_p") != null){				
				map2 = (HashMap<String,Object>) map_p.get("map_p");
				
				if("Z".equals(map2.get("USER_STATUS")))
				{
					log.debug("============== 아이디 확인 ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("/main/iljali_msgOut");
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
					request.getSession().setAttribute("userinfo", m_uinfo);	
					request.getSession().setMaxInactiveInterval(150 * 60);
					
					log.debug("login id=[" + map2.get("USER_ID") + "] name=[" + map2.get("USER_NAME") + "]");
				}
			}
			else{			
				log.debug("============== StrId 확인============= id=[" + StrId + "]\t");
				 mv = new ModelAndView("/main/iljali_msgOut");
				 mv.addObject("flag", "4");
			}
		}
		else{			
			log.debug("============== StrId 확인============= id=[" + StrId + "]\t");
			 mv = new ModelAndView("/main/iljali_msgOut");
			 mv.addObject("flag", "3");
		}
		
		return mv;
	}
	
	
}
