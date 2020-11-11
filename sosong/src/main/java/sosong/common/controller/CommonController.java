package sosong.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import sosong.common.common.CommandMap;
import sosong.common.service.CommonService;
import sosong.common.util.Nvl;
import sosong.common.util.CommonLib;
import sosong.common.alrim.SendArimMail;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	 // ������Ƽ ���� �б�
	@Value("#{conf['sms.callback']}")
    private String s_callback;
        
	@Value("#{conf['sms.targetno']}")
    private String s_targetno;
		
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
		
		ModelAndView mv = new ModelAndView("redirect:/body.do");
		
		commandMap.put("USER_ID", request.getParameter("userid"));
		Map<String,Object> map = commonService.ssoLogin(commandMap.getMap());
		
		Map<String, Object> msso = new HashMap<String,Object>();
		Map<String, Object> mtmp = new HashMap<String,Object>();
		Map<String, Object> mtmp2 = new HashMap<String,Object>();
		
		String StrId = request.getParameter("userid");
		
		request.getSession().invalidate();
		
		if(map.get("map") != null){
									
			//userid �ִ��� üũ			
			Map<String,Object> map2 = commonService.loginProcessId(commandMap.getMap(), request);
			
			if(map2.get("map_i") == null){
				log.debug("sso ============== user ���̺? ����  ============= id=[" + StrId + "]\t");
				mv = new ModelAndView("redirect:/LoginMsgOut.do");
				redirecAttributes.addFlashAttribute("map", map.get("map"));
				
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
				fm.put("gb", "1");   //view ����������
			
			}
			else{	
				
				mtmp = null;
				mtmp = (HashMap<String,Object>) map2.get("map_i");
				
				if("Z".equals(mtmp.get("USER_STATUS")))
				{
					log.debug("sso ============== ���ξȵ�  ============= id=[" + StrId + "]\t");
					mv = new ModelAndView("redirect:/LoginMsgOut.do");
					FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
					fm.put("gb", "2");    //view ����������
					fm.put("flag", "1");  //�޼��� ����
					
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
					
					log.debug("sso login id=[" + msso.get("USER_ID") + "] name=[" + msso.get("USER_NAME") + "]");
				}
			}
			
			
		}
		else{			
			 log.debug("sso ============== �ƿ� ��� ���̵�  ============= id=[" + StrId + "]\t");		 
			 mv = new ModelAndView("redirect:/LoginMsgOut.do");
			 FlashMap fm = RequestContextUtils.getOutputFlashMap(request);  
			 fm.put("gb", "1");	//view ����������		   
		}
		return mv;
	}
	
	
	
	//=========================================================================================================
	@RequestMapping(value="/common/login.do")
	public ModelAndView login(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/body.do");
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
					log.debug("============== ���ξȵ�  ============= id=[" + StrId + "]\t");
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
				log.debug("============== �н����� Ʋ�� ============= id=[" + StrId + "]\t");
				 mv = new ModelAndView("/main/msgOut");
				 mv.addObject("flag", "4");
			}
		}
		else{			
			log.debug("============== ���̵� Ʋ��  ============= id=[" + StrId + "]\t");
			 mv = new ModelAndView("/main/msgOut");
			 mv.addObject("flag", "3");
		}
		
		return mv;
	}
	
		
	
	@RequestMapping(value="/LoginMsgOut.do")
	public ModelAndView LoginMsgOut(HttpServletRequest request) throws Exception{
		
		ModelAndView mv = null;
		String gb = null;  //view ����������
		String flag = null; //��� �޼��� ����
		
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
	
	
	@RequestMapping(value="/common/deptList.do")
    public ModelAndView deptList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.deptList(commandMap.getMap());
    	mv.addObject("list", list);
    
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
	
	
	
	
	@RequestMapping(value="/body.do")
	public ModelAndView body(CommandMap commandMap) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/body");
    
    	return mv;
    }
	
	@RequestMapping(value="/top.do")
	public ModelAndView top(CommandMap commandMap) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/top");
    
    	return mv;
    }
	
	@RequestMapping(value="/menu.do")
	public ModelAndView menu(CommandMap commandMap) throws Exception{
		
		ModelAndView mv = new ModelAndView("/main/menu");
    
    	return mv;
    }
	
	
	@RequestMapping(value="/common/popJikWonList.do")
	public ModelAndView popJikWonList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.popJikWonList(commandMap.getMap());
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
	
	
	@RequestMapping(value="/common/selectJudgmentList.do")
    public ModelAndView selectJudgmentList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.selectJudgmentList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	
	@RequestMapping(value="/alrimService.do")
	public ModelAndView alrimService(CommandMap commandMap, HttpServletRequest request) throws Exception{
		String hnolist = null;
		String[] arrayType = null;
		String[] arrayType2 = null;
		String tmpList = null;     // ��Ǹ���Ʈ
		String tmpIcdntno = null;  //��ǹ�ȣ
		String tmpIcdntTrino = null;  //��ǽɱ��й�ȣ
		String tmpresdate = null; //�亯�� ��������
		String tmpreasondate = null;//��������������
		String tmpsubdate = null; //�׼�/�����������
		String tmpsetdate = null; //���б���
		String tmpSetDateList = null; //���б��� ����Ʈ
		String tmpGb = null; //���ڱ���
		String tmpTxt = null; //��۳���
		
		
		log.debug("[alrim] ============== start!!!!  =============\t");
		
		//log.debug("[alrim] callback=[" + s_callback + "] targetno=[" + s_targetno + "]============== ===========\t");
		
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
			
			tmpSetDateList = "";
			tmpList = Nvl.nvlStr((String) list.get(i).get("PERFORM_USERHNO"));
			tmpresdate = Nvl.nvlStr((String) list.get(i).get("APPEAL_RESPONSE_DATE"));
			tmpreasondate = Nvl.nvlStr((String) list.get(i).get("APPEAL_REASON_DATE"));
			tmpsubdate = Nvl.nvlStr((String) list.get(i).get("APPEAL_SUBMIT_DATE"));
			tmpSetDateList = Nvl.nvlStr((String) list.get(i).get("ARGUE_SET_DATE"));
			tmpIcdntno =  Nvl.nvlStr((String) list.get(i).get("ICDNT_NO"));
			tmpIcdntTrino = String.valueOf( list.get(i).get("ICDNT_TRIAL_NO") );
			tmpGb =  Nvl.nvlStr((String) list.get(i).get("GB"));		
			//log.debug("[alrim] tmpSetDateList : [" + i + "]==============[" +  tmpSetDateList  + "]=============\t");
	
			if(!"".equals(tmpList)){
				hnolist = tmpList;
					
				//log.debug("[for] + (" + i + ") ============== [" + list.get(i).get("PERFORM_USERHNO") +"] =============\t");
				arrayType = null;
				arrayType = hnolist.split(",");
				
				for(int j = 0 ; j < arrayType.length ; j++){
					tmpTxt = "";
					
					if("1".equals(tmpGb) )
					{						
						if(nToDay.equals(tmpresdate)) 
						{																												
							tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� �亯�� ��������(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpresdate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";								
						}
					}
					else if("2".equals(tmpGb) )
					{						
						if(nToDay.equals(tmpreasondate)) 
						{														
							if("21".equals(tmpIcdntTrino))
								tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� �׼������� ��������(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpreasondate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";
							else if("31".equals(tmpIcdntTrino))
								tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� ��������� ��������(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpreasondate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";					
						}
					}					
					else if("3".equals(tmpGb) )
					{
						
						if(nToDay.equals(tmpsubdate)) 
						{														
							if("11".equals(tmpIcdntTrino))
								tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� �׼��� ��������(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpsubdate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";
							else if("21".equals(tmpIcdntTrino))
								tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� ����� ��������(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpsubdate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";						
						}
					}					
					else if("4".equals(tmpGb) )
					{										
						arrayType2 = null;
						arrayType2 = tmpSetDateList.split(";");
						
						for( int k = 0 ; k < arrayType2.length ; k++){
							
							tmpsetdate = arrayType2[k];
						
							if(nToDay.equals(tmpsetdate)) 
							{																
								if("11".equals(tmpIcdntTrino) || "21".equals(tmpIcdntTrino))
									tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� " + (k+1) +"�� ���б���(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpsetdate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";
								else if("12".equals(tmpIcdntTrino))
									tmpTxt = "#�Ҽ۾ȳ�����# [" + tmpIcdntno + "]�� " + (k+1) +"�� �ɹ�����(" + CommonLib.getDateFormat(Nvl.nvlStr(tmpsetdate), ".") + ") " + request.getParameter("nday") + "���� �Դϴ�.";								
							}														
						}
						
					}
										
					if(!"".equals(tmpTxt)) 
					{
						commandMap.clear();
						commandMap.put("MSG_TYPE", "1");
						//commandMap.put("DSTADDR", s_targetno);
						commandMap.put("DSTADDR", arrayType[j]);
						commandMap.put("CALLBACK", s_callback);
						commandMap.put("STAT", "0");					
						commandMap.put("TEXT", tmpTxt);						
						log.debug("[" + tmpGb + "] " + " [" + commandMap.entrySet() +"] =============\t");
						commonService.insertSendMsg(commandMap.getMap());						
					}
										
				}				
			
			}
			//log.debug("[hnolist][" + i + "] ============== [" + hnolist +"] =============\t");			
		}
	
		log.debug("[alrim] ============== end!!!!  =============\t");		
		return mv;        
    }
	
	
	
	@RequestMapping(value="/common/allSmsAlrimList.do")
    public ModelAndView allSmsAlrimList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/common/allSmsAlrimList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/common/selectAllSmsAlrimList.do")
    public ModelAndView selectAllSmsAlrimList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.selectArimService(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/common/getSmsSendList.do")
	public ModelAndView popSmsSendList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/common/getSmsSendList");
    	
    	List<Map<String,Object>> list = commonService.popSmsSendList(commandMap.getMap());
    	mv.addObject("list", list);
    
    	return mv;
    }
	
	
	@RequestMapping(value="/common/allSmsSendList.do")
    public ModelAndView allSmsSendList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/common/allSmsSendList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/common/selectAllSmsSendList.do")
    public ModelAndView selectAllSmsSendList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.popSmsSendList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/common/allSmsSendList22.do")
    public ModelAndView allSmsSendList22(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/common/allSmsSendList22");
    	
    	return mv;
    }
	
	@RequestMapping(value="/common/selectAllSmsSendList22.do")
    public ModelAndView selectAllSmsSendList22(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = commonService.popSmsSendList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	
}