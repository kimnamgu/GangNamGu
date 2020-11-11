package corona.manage.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import corona.common.common.CommandMap;
import corona.manage.dao.ManageDAO;
import corona.manage.service.ManageService;
import corona.status.service.StatusService;

@Controller
public class ManageController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="manageService")
	private ManageService manageService;
	
	@Resource(name="manageDAO")
	private ManageDAO manageDAO;
	
	@Resource(name="statusService")
	private StatusService statusService;
	
	
	/*Ȯ���� ����*/
	@RequestMapping(value="/manage/confirmManage.do")
    public ModelAndView confirmManage(CommandMap commandMap) throws Exception{
    	System.out.println("confirmManageŽ");
		ModelAndView mv = new ModelAndView("/manage/confirmManage");
		
		Map<String,Object> confirmShow = statusService.selectConfirmShow(commandMap.getMap());
		
		Map<String, Object> confirmShowList = new HashMap<String,Object>();
		confirmShowList = (HashMap<String,Object>) confirmShow.get("map");
		mv.addObject("SHOW_LIST", confirmShowList.get("SHOW_LIST"));
    	System.out.println("Ȯ��Ȯ�� : " + confirmShowList.get("SHOW_LIST"));
    	
    	return mv;
    }
	
	@RequestMapping(value="/manage/selectConfirmList.do")
    public ModelAndView selectConfirmList(CommandMap commandMap) throws Exception{
		log.debug("selectConfirmList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
		

		
		
    	List<Map<String,Object>> list = manageService.selectConfirmList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		System.out.println("Ȯ�� : " + list.toString());
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/manage/updateConfirmShow.do")
    public ModelAndView updateConfirmShow(CommandMap commandMap) throws Exception{
		log.debug("updateConfirmShow param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	manageService.updateConfirmShow(commandMap.getMap());
    	return mv;
    }
	
	@RequestMapping(value = "/manage/confirmManageExcelUp.do", method = RequestMethod.POST)
	public ModelAndView confirmManageExcelUp(MultipartFile testFile, MultipartHttpServletRequest request) throws Exception {
		System.out.println("confirmManageExcelUpŽ");
		MultipartFile excelFile = request.getFile("excelFile");
		String ins_id = request.getParameter("INS_ID");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("���������� ���� �� �ּ���.");
		}

		File destFile = new File("C:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = manageService.confirmManageExcelUp(destFile,ins_id);
		destFile.delete();

		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("resultMap", resultMap);
		
		return mv;
	}
	
	//���û���
	@RequestMapping(value="/manage/deleteConfirmChk.do")
	@ResponseBody
	public Map<String, Object> allListDel(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals Ȯ�� : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        //System.out.println("vals(" + i + ") : " + vals.get(i));
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelConfirmAllList(map);
	    }
		
		return map;
	}
	
	
	/*�ڰ��ݸ���_���������� ����*/
	@RequestMapping(value="/manage/domesticContactManage.do")
    public ModelAndView domesticContactManage(CommandMap commandMap) throws Exception{
    	System.out.println("domesticContactManageŽ");
		ModelAndView mv = new ModelAndView("/manage/domesticContactManage");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/manage/selectDomesticContactList.do")
    public ModelAndView selectDomesticContactList(CommandMap commandMap) throws Exception{
		log.debug("selectDomesticContactList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = manageService.selectDomesticContactList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	@ResponseBody
	@RequestMapping(value = "/manage/domesticContactExcelUp.do", method = RequestMethod.POST)
	public ModelAndView domesticContactExcelUp(MultipartFile testFile, MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("domesticContactExcelUpŽ");
		MultipartFile excelFile = request.getFile("excelFile");
		String ins_id = request.getParameter("INS_ID");
		
		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("���������� ���� �� �ּ���.");
		}

		File destFile = new File("C:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = manageService.domesticContactExcelUp(destFile,ins_id);

		destFile.delete();

		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("resultMap", resultMap);
		
		return mv;
	}
	
	//���û���
	@RequestMapping(value="/manage/deleteDomesticChk.do")
	@ResponseBody
	public Map<String, Object> allDomesticListDel(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals Ȯ�� : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        //System.out.println("vals(" + i + ") : " + vals.get(i));
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelDomesticAllList(map);
	    }
		
		return map;
	}
	
	//���û���
	@RequestMapping(value="/manage/deleteOverseaChk.do")
	@ResponseBody
	public Map<String, Object> allOverseaListDel(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals Ȯ�� : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        //System.out.println("vals(" + i + ") : " + vals.get(i));
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelOverseaAllList(map);
	    }
		
		return map;
	}
	
	/*�ڰ��ݸ���_�ؿ��Ա��� ����*/
	@RequestMapping(value="/manage/overseaManage.do")
    public ModelAndView overseaManage(CommandMap commandMap) throws Exception{
    	System.out.println("overseaManageŽ");
		ModelAndView mv = new ModelAndView("/manage/overseaManage");
    	
    	return mv;
    }
	
	@RequestMapping(value="/manage/selectOverseaList.do")
    public ModelAndView selectOverseaList(CommandMap commandMap) throws Exception{
		log.debug("selectOverseaList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = manageService.selectOverseaList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	@ResponseBody
	@RequestMapping(value = "/manage/overseaExcelUp.do", method = RequestMethod.POST)
	public ModelAndView overseaExcelUp(MultipartFile testFile, MultipartHttpServletRequest request) throws Exception {
		System.out.println("overseaExcelUpŽ");
		MultipartFile excelFile = request.getFile("excelFile");
		String ins_id = request.getParameter("INS_ID");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("���������� ���� �� �ּ���.");
		}

		File destFile = new File("C:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = manageService.overseaExcelUp(destFile,ins_id);

		destFile.delete();

		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("resultMap", resultMap);

		return mv;
	}
	
	/*���ο� ����*/
	@RequestMapping(value="/manage/consultManage.do")
    public ModelAndView consultManage(CommandMap commandMap) throws Exception{
    	System.out.println("consultManageŽ");
		ModelAndView mv = new ModelAndView("/manage/consultManage");
    	
    	return mv;
    }
	
	
	@RequestMapping(value="/manage/selectConsultList.do")
    public ModelAndView selectConsultList(CommandMap commandMap) throws Exception{
		log.debug("selectConsultList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = manageService.selectConsultList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	@ResponseBody
	@RequestMapping(value = "/manage/consultExcelUp.do", method = RequestMethod.POST)
	public ModelAndView consultExcelUp(MultipartFile testFile, MultipartHttpServletRequest request) throws Exception {
		System.out.println("consultExcelUpŽ");
		MultipartFile excelFile = request.getFile("excelFile");
		String ins_id = request.getParameter("INS_ID");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("���������� ���� �� �ּ���.");
		}

		File destFile = new File("C:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = manageService.consultExcelUp(destFile,ins_id);

		destFile.delete();

		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("resultMap", resultMap);

		return mv;
	}
	
	/*��������� �˻� ����*/
	@RequestMapping(value="/manage/clinicManage.do")
    public ModelAndView clinicManage(CommandMap commandMap) throws Exception{
    	System.out.println("clinicManageŽ");
		ModelAndView mv = new ModelAndView("/manage/clinicManage");
    	
    	return mv;
    }
	
	@RequestMapping(value="/manage/selectClinicList.do")
    public ModelAndView selectClinicList(CommandMap commandMap) throws Exception{
		log.debug("selectClinicList param2= [" + commandMap.getMap().toString() + "]");
		
		ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = manageService.selectClinicList(commandMap.getMap());
    	
    	mv.addObject("list", list);
    	
    	if(list.size() > 0){
    		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("total", 0);
    	}
    	
    	return mv;
    }
	
	@ResponseBody
	@RequestMapping(value = "/manage/clinicExcelUp.do", method = RequestMethod.POST)
	public ModelAndView clinicExcelUp(MultipartFile testFile, MultipartHttpServletRequest request) throws Exception {
		System.out.println("clinicExcelUpŽ");
		MultipartFile excelFile = request.getFile("excelFile");
		String ins_id = request.getParameter("INS_ID");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("���������� ���� �� �ּ���.");
		}

		File destFile = new File("C:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap = manageService.clinicExcelUp(destFile,ins_id);

		destFile.delete();

		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("resultMap", resultMap);

		return mv;
	}
	
	//���û���
	@RequestMapping(value="/manage/deleteClinicChk.do")
	@ResponseBody
	public Map<String, Object> allClinicListDel(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals Ȯ�� : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        //System.out.println("vals(" + i + ") : " + vals.get(i));
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelClinicAllList(map);
	    }
		
		return map;
	}
	
	
	@RequestMapping(value = "/manage/gangnamguArrange.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gangnamguArrange(CommandMap commandMap,Model model) throws Exception{
		System.out.println("gangnamguArrange Ž");
		Map<String, Object> map = new HashMap<String,Object>();
		
		List<Map<String,Object>> resultlist = manageService.selectGangnamguArrangeList(commandMap.getMap());
		
		 String jibun = ""; 
		 String dong  = "";
		 System.out.println("resultlist sizeȮ�� : " + resultlist.size());
		
	    for(int i=0; i<resultlist.size() ;i++){
	        System.out.println("resultlist(" + i + ") : " + resultlist.get(i));
	        
	        map.put("id", resultlist.get(i).get("ID"));
	        
	        jibun = (String) resultlist.get(i).get("ORG_ADDRESS");
	        map.put("jibun", jibun);
	        
	        dong = rtnDongAddr(jibun);
	        
	        
	        System.out.println("jibun Ȯ�� : " + jibun);
	        System.out.println("dong Ȯ�� : " + dong);
	        
	        if(dong.equals("����Ȯ��")){
	        	continue;
	        }
	        
	        map.put("dong", dong);
	        
	        manageDAO.updateGangnamguArrange(map);
	    }
		
		return map;
	}
	
	
	@RequestMapping(value = "/manage/tasiguArrange.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tasiguArrange(CommandMap commandMap,Model model) throws Exception{
		System.out.println("tasiguArrange Ž");
		Map<String, Object> map = new HashMap<String,Object>();
		
		List<Map<String,Object>> resultlist = manageService.selectTasiguArrangeList(commandMap.getMap());
		
		 String jibun = ""; 
		 String si  = "";
		 String gu  = "";
		 System.out.println("resultlist sizeȮ�� : " + resultlist.size());
		
	    for(int i=0; i<resultlist.size() ;i++){
	        System.out.println("resultlist(" + i + ") : " + resultlist.get(i));
	        
	        
	        
	        jibun = (String) resultlist.get(i).get("ORG_ADDRESS");
	        
	        
	        map = rtnAddr(jibun);
	        si = (String) map.get("si");
	        gu = (String) map.get("gu");
	        
	        System.out.println("jibun Ȯ�� : " + jibun);
	        System.out.println("si Ȯ�� : " + si);
	        System.out.println("gu Ȯ�� : " + gu);
	        
	        if(si.equals("�ù�Ȯ��") || gu.equals("����Ȯ��")){
	        	continue;
	        }
	        
	        map.put("id", resultlist.get(i).get("ID"));
	        map.put("jibun", jibun);
	        
	        manageDAO.updateTasiguArrange(map);
	    }
		
		return map;
	}
	
	
	//�� ����
	public Map<String, Object> rtnAddr(String jibun) throws Exception {
		
		Map<String, Object> map = new HashMap<String,Object>();
		String currentPage = "1"; 
		String countPerPage = "1";
		String resultType = "json";
		//String confmKey = "devU01TX0FVVEgyMDIwMDgxNDA4MzU0MzExMDA2NDU="; //����
		String confmKey = "U01TX0FVVEgyMDIwMDgxNDE0NDczNjExMDA2NjI="; //�
		String keyword = jibun;
		String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")+"&confmKey="+confmKey+"&resultType="+resultType;
		URL url = new URL(apiUrl);
    	BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
    	StringBuffer sb = new StringBuffer();
    	String tempStr = null;
    	String siNm = "�ù�Ȯ��";
    	String guNm = "����Ȯ��";
    	while(true){
    		tempStr = br.readLine();
    		if(tempStr == null) break;
    		sb.append(tempStr);								
    	}
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        JSONObject JSONObject2 = (JSONObject) jsonObject.get("results");
        JSONArray jusoArray = (JSONArray) JSONObject2.get("juso");
        
        if(jusoArray != null && !jusoArray.isEmpty()){
	        JSONObject jusoObject = (JSONObject) jusoArray.get(0);
	        siNm = jusoObject.get("siNm").toString();
	        
	        guNm = jusoObject.get("sggNm").toString();
	        String[] array = guNm.split(" ");
	        guNm = array[0];
        }
        
        map.put("si", siNm);
        map.put("gu", guNm);
        
    	br.close();
		
		return map;
	}
	
	
	
	//���ּ� ����
	public String rtnDongAddr(String jibun) throws Exception {
		String currentPage = "1"; 
		String countPerPage = "1";
		String resultType = "json";
		//String confmKey = "devU01TX0FVVEgyMDIwMDgxNDA4MzU0MzExMDA2NDU="; //����
		String confmKey = "U01TX0FVVEgyMDIwMDgxNDE0NDczNjExMDA2NjI="; //�
		String keyword = jibun;
		String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")+"&confmKey="+confmKey+"&resultType="+resultType;
		URL url = new URL(apiUrl);
    	BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
    	StringBuffer sb = new StringBuffer();
    	String tempStr = null;
    	String dongNm = "";
    	while(true){
    		tempStr = br.readLine();
    		if(tempStr == null) break;
    		sb.append(tempStr);								
    	}
    	
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        JSONObject JSONObject2 = (JSONObject) jsonObject.get("results");
        JSONArray jusoArray = (JSONArray) JSONObject2.get("juso");
        
        if(jusoArray != null && !jusoArray.isEmpty()){
	        JSONObject jusoObject = (JSONObject) jusoArray.get(0);
	        dongNm = jusoObject.get("emdNm").toString();
        }else{
        	dongNm = "����Ȯ��";
        }
        
    	br.close();
		
		return dongNm;
	}
	
	
	
	public Map<String, Object> tasiguArrange(@RequestParam(value="val[]") List<Integer> vals) throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("vals Ȯ�� : " + vals.toString());
		
	    for(int i=0; i<vals.size() ;i++){
	        //System.out.println("vals(" + i + ") : " + vals.get(i));
	        map.put("selId", vals.get(i));
	        manageDAO.updateDelClinicAllList(map);
	    }
		
		return map;
	}
	
	//���� �ٿ�ε�
	@RequestMapping(value = "/manage/downloadConfirmExcelFile.do")
    public ModelAndView downloadConfirmExcelFile(CommandMap commandMap,Model model) throws Exception {
		log.debug("1param val= [" + commandMap.getMap().toString() + "]");
        
		List<Map<String,Object>> resultlist = manageService.selectConfirmExcel(commandMap.getMap());
        
		ModelAndView mv = new ModelAndView("/manage/excelConfirmConvert");
		mv.addObject("resultlist",resultlist);
		return mv;
    }
	
	@RequestMapping(value = "/manage/downloadClinicExcelFile.do")
    public ModelAndView downloadClinicExcelFile(CommandMap commandMap,Model model) throws Exception {
		log.debug("1param val= [" + commandMap.getMap().toString() + "]");
        
		List<Map<String,Object>> resultlist = manageService.selectClinicExcel(commandMap.getMap());
        
		ModelAndView mv = new ModelAndView("/manage/excelClinicConvert");
		mv.addObject("resultlist",resultlist);
		return mv;
    }
	
	@RequestMapping(value = "/manage/downloadOverseaExcelFile.do")
    public ModelAndView downloadOverseaExcelFile(CommandMap commandMap,Model model) throws Exception {
		log.debug("1param val= [" + commandMap.getMap().toString() + "]");
        
		List<Map<String,Object>> resultlist = manageService.selectOverseaExcel(commandMap.getMap());
        
		ModelAndView mv = new ModelAndView("/manage/excelOverseaConvert");
		mv.addObject("resultlist",resultlist);
		return mv;
    }
	
	@RequestMapping(value = "/manage/downloadDomesticExcelFile.do")
    public ModelAndView downloadDomesticExcelFile(CommandMap commandMap,Model model) throws Exception {
		log.debug("1param val= [" + commandMap.getMap().toString() + "]");
        
		List<Map<String,Object>> resultlist = manageService.selectDomesticExcel(commandMap.getMap());
        
		ModelAndView mv = new ModelAndView("/manage/excelDomesticConvert");
		mv.addObject("resultlist",resultlist);
		return mv;
    }

	
}