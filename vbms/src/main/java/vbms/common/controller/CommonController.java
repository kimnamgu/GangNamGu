package vbms.common.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.CookieGenerator;

import vbms.common.util.CommonLib;
import vbms.common.common.CommandMap;
import vbms.common.service.CommonService;
import vbms.common.util.Nvl;
import vbms.common.util.AES256Cipher;
import vbms.common.util.AES256Util;
import vbms.common.util.CookieUtils;

import vbms.common.util.Base64Coder;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	
	@Value("#{conf['sms.callback']}")
    private String s_callback;
	
	@Value("#{conf['sms.targetno']}")
    private String s_targetno;
	
	
	@RequestMapping(value="/common/downloadFile.do")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response, HttpServletRequest request) throws Exception{
		Map<String,Object> map = commonService.selectFileInfo(commandMap.getMap());
		String storedFileName = (String)map.get("STORED_FILE_NAME");
		String originalFileName = (String)map.get("ORIGINAL_FILE_NAME");
		String file_path = null;
		String cname = null;
		String cvalue = null;
		String dgns_no = request.getParameter("DGNS_NO");
		
		if(request.getServerName().equals("98.23.7.109"))  //운영서버				
		{
			file_path = "/usr/local/server/file/vbms/";
		}
		else
		{
			file_path = "C:\\dev\\file\\";
		}
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File(file_path+storedFileName));
		
		String cookie = CookieUtils.getValue("down" + dgns_no, request);
		
		log.debug("dgns_no=[" + dgns_no + "] cookie=[" + cookie + "]\t");
		
		if(!"Y".equals(cookie)){
			CookieUtils.createNewCookie("down" + dgns_no, "Y", 1, request, response);			
			commonService.updateDownCount(commandMap.getMap());
		}
				
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
		
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingList.do");
		String deStrId = null;
		
		String StrId = request.getParameter("userid");
		log.debug("*********get id=[" + StrId + "]\t");
		
		log.debug("*********get id getbyete=[" + StrId.getBytes() + "]\t");
		
		byte[] encodedBytes = Base64.encodeBase64(StrId.getBytes()); /* base64 decoding */ 
		byte[] decodedBytes = Base64.decodeBase64(StrId); 
		
		deStrId =  new String(decodedBytes); 
		System.out.println("encoding text : " + new String(encodedBytes));
		System.out.println("디코딩 text : " + deStrId);
		
		
		
		commandMap.put("USER_ID", deStrId);
		Map<String,Object> map = commonService.ssoLogin(commandMap.getMap());
		
		Map<String, Object> msso = new HashMap<String,Object>();
		Map<String, Object> mtmp = new HashMap<String,Object>();
		Map<String, Object> mtmp2 = new HashMap<String,Object>();
		
		
		
		/*
		AES256Cipher a256 = AES256Cipher.getInstance();
		 
	    //String enId = a256.AES_Encode(StrId);
		String enId =  StrId;
				
	    log.debug("*********get en_id=[" + enId + "]\t");
	    
	    log.debug("decode_id=[" + a256.AES_Decode(enId) + "]\t");
	    */
	    /*
		String key = "12345678901234567890123456789012"; 
		AES256Util aes256 = new AES256Util(key);
		URLCodec codec = new URLCodec();

		//String encLoginidx = codec.encode(aes256.aesEncode(StrId));
		String encLoginidx = StrId;
		log.debug("##########encode_id=[" + encLoginidx + "]\t");
		
		String decLoginidx = aes256.aesDecode(codec.decode(encLoginidx));
		log.debug("##########decode_id=[" + decLoginidx + "]\t");
		*/ 		
	
		/*
		String enStr = null;
		enStr = Base64Coder.encodeString(StrId);
		System.out.println("========new 인코딩 text : " + enStr);
		System.out.println("========new 디코딩 text : " + Base64Coder.decodeString(enStr));
		*/
		
		request.getSession().invalidate();
		
		if(map.get("map") != null){
									
			//userid 있는지 체크		
			Map<String,Object> map2 = commonService.loginProcessId(commandMap.getMap(), request);
			
			if(map2.get("map_i") == null){				
				log.debug("sso11111111 ============== user  ============= id=[" + StrId + "]\t");
				mv = new ModelAndView("redirect:/LoginMsgOut.do");
				redirecAttributes.addFlashAttribute("map", map.get("map"));
				
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
				fm.put("gb", "1");   //view 
			
			}
			else{	
				
				mtmp = null;
				mtmp = (HashMap<String,Object>) map2.get("map_i");
				
				if("Z".equals(mtmp.get("USER_STATUS")))
				{
					log.debug("sso ============== 승인안됨  ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("redirect:/LoginMsgOut.do");
					FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
					fm.put("gb", "2");    //view 페이지구분
					fm.put("flag", "1");  //메세지 결과구분				
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
					request.getSession().setAttribute("userinfo", mtmp2);	
					request.getSession().setMaxInactiveInterval(150 * 60);

					commandMap.put("USER_NAME", msso.get("USER_NAME"));			
					commandMap.put("DEPT_ID", msso.get("DEPT_ID"));
					commandMap.put("DEPT_NAME", msso.get("DEPT_NAME"));
					commandMap.put("CLASS_ID", msso.get("CLASS_ID"));
					commandMap.put("CLASS_NAME", msso.get("CLASS_NAME"));
					commandMap.put("POSITION_ID", Nvl.nvlStr(msso.get("POSITION_ID")));
					commandMap.put("POSITION_NAME", Nvl.nvlStr(msso.get("POSITION_NAME")));
					
					commonService.updateUserinfo(commandMap.getMap());
					
					log.debug("#####################################################################\t");
					log.debug("sso login id=[" + msso.get("USER_ID") + "] name=[" + msso.get("USER_NAME") + "]");
				}
			}
			
			
		}
		else{			
			 log.debug("sso ============== 아에 없는 아이디  ============= id=[" + StrId + "]\t");		 
			 mv = new ModelAndView("redirect:/LoginMsgOut.do");
			 FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
			 fm.put("gb", "1");	//   
		}
		return mv;
	}
	
	
	
	//=========================================================================================================
	@RequestMapping(value="/common/login.do")
	public ModelAndView login(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingList.do");
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
					log.debug("============== �α��ξ��̵�  ============= id=[" + StrId + "]\t");
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
					request.getSession().setAttribute("userinfo", m_uinfo);	
					request.getSession().setMaxInactiveInterval(150 * 60);
					
					log.debug("login id=[" + map2.get("USER_ID") + "] name=[" + map2.get("USER_NAME") + "]");
				}
			}
			else{			
				log.debug("========================== id=[" + StrId + "]\t");
				 mv = new ModelAndView("/main/msgOut");
				 mv.addObject("flag", "4");
			}
		}
		else{			
			log.debug("=========================== id=[" + StrId + "]\t");
			 mv = new ModelAndView("/main/msgOut");
			 mv.addObject("flag", "3");
		}
		
		return mv;
	}
	
		
	
	@RequestMapping(value="/LoginMsgOut.do")
	public ModelAndView LoginMsgOut(HttpServletRequest request) throws Exception{
		
		ModelAndView mv = null;
		String gb = null;  //view 페이지구분
		String flag = null; //결과 메세지 구분
		
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
	
	
	@RequestMapping(value="/common/structList.do")
	public ModelAndView structList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.structList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	@RequestMapping(value="/common/purposeList.do")
	public ModelAndView purposeList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.purposeList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	@RequestMapping(value="/common/stateList.do")
	public ModelAndView stateList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.stateList(commandMap.getMap());
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
	
	
	
	
	/*###############################################################################################*/
	
	
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
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/common/syncUserinfo.do")
	public void syncUserinfo(CommandMap commandMap, HttpServletRequest request, RedirectAttributes redirecAttributes) throws Exception{
		
		String strUserid;
		Map<String,Object> map;
		List<Map<String,Object>> list = null;
		Map<String, Object> msso = new HashMap<String,Object>();
		
		/* */
		list = commonService.selectEmployeeTransIdList();
		
		for(int i= 0; i < list.size(); i++)
		{
			strUserid = null;
			map = null;
			msso = null;
			
			strUserid = (String) list.get(i).get("USER_ID");			
			System.out.println("size=[" + i + "] = (" + strUserid + ")");
			
			commandMap.put("USER_ID", strUserid);
			map = commonService.ssoLogin(commandMap.getMap());
						
			msso = (HashMap<String,Object>) map.get("map");
			
			System.out.println("name ==> (" + msso.get("USER_NAME") + ")");
			commandMap.put("USER_NAME", msso.get("USER_NAME"));			
			commandMap.put("CLASS_ID", msso.get("CLASS_ID"));
			commandMap.put("CLASS_NAME", msso.get("CLASS_NAME"));
			commandMap.put("GRADE_ID", Nvl.nvlStr(msso.get("GRADE_ID")));
			commandMap.put("GRADE_NAME", Nvl.nvlStr(msso.get("GRADE_NAME")));
			commandMap.put("CUR_DEPT_ID", msso.get("DEPT_ID"));
			commandMap.put("CUR_DEPT_NAME", msso.get("DEPT_NAME"));
			
			commonService.updateEmployeeTransData(commandMap.getMap());
						
		}
		
		/*  */
		list = null;
		commandMap.clear();		
		commonService.deleteDeptData(commandMap.getMap());
		
		commandMap.clear();
		list = commonService.selectDeptList(commandMap.getMap());
		
		for(int i= 0; i < list.size(); i++)
		{		
			commandMap.clear();
			commandMap.put("DEPT_ID", list.get(i).get("DEPT_ID"));			
			commandMap.put("DEPT_NAME", list.get(i).get("DEPT_NAME"));
			
			commonService.insertDeptData(commandMap.getMap());
		}
				
		
	}
	
	
	
	 @RequestMapping(value = "/common/applyCntUpdateAjax.do", method = RequestMethod.POST)
	    public @ResponseBody ModelAndView excelUploadAjax(CommandMap commandMap)  throws Exception{
	        
	    	ModelAndView mv = new ModelAndView("jsonView");
	    	int chk = 0;
	    	
	    	try {
	    		commonService.updateApplyCount(commandMap.getMap());
	    		chk = 1;	    		
	 		} catch ( Exception e) {	 			
	 			mv.addObject("result", "error");
	        	mv.addObject("message", "카운트 업데이트 중  에러가 발생하였습니다.\n\n" + e.getMessage());	        	
	 		}
	        
	    	if(chk == 1){
	    		mv.addObject("result", "success");
	    		mv.addObject("message", "카운트 업데이트 성공!!!!");            
	    	}
	    	
	    	log.debug("mv11=[" +  mv + "]");
	    	System.out.println("mv22=[" +  mv + "]");
	    	
	    	return mv;	               
		}
	 
	 
	 
	 
	 @RequestMapping(value="/alrimService.do")
		public ModelAndView alrimService(CommandMap commandMap, HttpServletRequest request) throws Exception{
			String hnolist = null;
			String[] arrayType = null;
			String[] arrayType2 = null;
			String tmpList = null;     
			String tmpIcdntno = null;  
			String tmpIcdntTrino = null;  
			String tmpTday = null; //target date
			String tmpDong = null;//행정동
			String tmpAddr1 = null; //해당 주소1
			String tmpGb = null; //구분
			String tmpTxt = null; //문자내용
			
			log.debug("[alrim] ============== start!!!!  =============\t");
			
			log.debug("[alrim] callback=[" + s_callback + "] targetno=[" + s_targetno + "]============== ===========\t");
			
			ModelAndView mv = new ModelAndView("/main/alrimService");
					
			List<Map<String,Object>> list = commonService.selectArimService(commandMap.getMap());
			mv.addObject("list", list);
			
			commandMap.clear();
			commandMap.put("NDAY", request.getParameter("nday"));		
			String nToDay = commonService.getnBeforeDay(commandMap.getMap());
			
			log.debug("[alrim] gijun_day=[" + request.getParameter("nday") + "]=======[" +  nToDay  + "]=============\t");
			//log.debug("[alrim] list size=[" + list.size() + "] ==============[" +  nToDay  + "]=============\t");
			//SendArimMail.sendMail();
			//commonService.sosongSendMail(commandMap.getMap());
			
			for(int i = 0, n = list.size(); i < n; i++){
				
				
				tmpList = Nvl.nvlStr((String) list.get(i).get("PERFORM_USERHNO"));
				tmpTday = Nvl.nvlStr((String) list.get(i).get("TDAY"));
				tmpDong = Nvl.nvlStr((String) list.get(i).get("BLD_DONG"));
				tmpAddr1 = Nvl.nvlStr((String) list.get(i).get("BLD_ADDR1"));
				tmpGb =  Nvl.nvlStr((String) list.get(i).get("GB"));		
				log.debug("[alrim] tmpSetDateList : [" + i + "]==============[" +  tmpTday  + "]=============\t");
		
				if(!"".equals(tmpList)){
					hnolist = tmpList;
						
					log.debug("[for] + (" + i + ") ============== [" + list.get(i).get("PERFORM_USERHNO") +"] =============\t");
					arrayType = null;
					arrayType = hnolist.split(",");
					
					for(int j = 0 ; j < arrayType.length ; j++){
						tmpTxt = "";
						//시정명령
						if("1".equals(tmpGb) )
						{						
							if(nToDay.equals(tmpTday)) 
							{																												
								tmpTxt = "[" + tmpDong + " " + tmpAddr1 + "] 시정명령일  (" + CommonLib.getDateFormat(Nvl.nvlStr(tmpTday), ".") + ") " + request.getParameter("nday") + "일전 입니다.";								
							}
						}
						
						//이행강제금부과일
						if("2".equals(tmpGb) )
						{						
							if(nToDay.equals(tmpTday)) 
							{																												
								tmpTxt = "[" + tmpDong + " " + tmpAddr1 + "] 이행강제금부과일 (" + CommonLib.getDateFormat(Nvl.nvlStr(tmpTday), ".") + ") " + request.getParameter("nday") + "일전 입니다.";								
							}
						}
						
						//세무과 통보일
						if("3".equals(tmpGb) )
						{						
							if(nToDay.equals(tmpTday)) 
							{																												
								tmpTxt = "[" + tmpDong + " " + tmpAddr1 + " ] 세무과 통보일 (" + CommonLib.getDateFormat(Nvl.nvlStr(tmpTday), ".") + ") " + request.getParameter("nday") + "일전 입니다.";								
							}
						}
						
						
						if(!"".equals(tmpTxt)) 
						{
							commandMap.clear();
							commandMap.put("MSG_TYPE", "1");
							commandMap.put("DSTADDR", s_targetno);
							commandMap.put("DSTADDR", arrayType[j]);
							commandMap.put("CALLBACK", s_callback);
							commandMap.put("STAT", "0");					
							commandMap.put("TEXT", tmpTxt);						
							//log.debug("[" + tmpGb + "] " + " [" + commandMap.entrySet() +"] =============\t");
							log.debug("[" + tmpGb + "] " + " " + tmpTxt +" =============\t");
							commonService.insertSendMsg(commandMap.getMap());						
						}
											
					}				
				
				}
				log.debug("[hnolist][" + i + "] ============== [" + hnolist +"] =============\t");			
			}
		
			log.debug("[alrim] ============== end!!!!  =============\t");		
			return mv;        
	    }
		
	
}
