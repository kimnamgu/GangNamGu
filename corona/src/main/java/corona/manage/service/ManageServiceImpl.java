package corona.manage.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import corona.common.util.ExcelRead;
import corona.common.util.ExcelReadOption;
import corona.common.util.FileUtils;
import corona.manage.dao.ManageDAO;

@Service("manageService")
public class ManageServiceImpl implements ManageService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="manageDAO")
	private ManageDAO manageDAO;
	
	/*Ȯ����*/
	@Override
	public List<Map<String, Object>> selectConfirmList(Map<String, Object> map) throws Exception {
		return manageDAO.selectConfirmList(map);
	}
	
	@Override
	public void updateDelConfirmAllList(Map<String, Object> map) throws Exception {
		manageDAO.updateDelConfirmAllList(map);
	}
	
	@Override
	public void updateConfirmShow(Map<String, Object> map) throws Exception {
		manageDAO.updateConfirmShow(map);
	}
	
	
	@Override
	public Map<String, Object> confirmManageExcelUp(File destFile,String ins_id){
		System.out.println("confirmManageExcelUp service Ž");
		System.out.println("ins_id Ȯ�� : " + ins_id);
		
	    	ExcelReadOption excelReadOption = new ExcelReadOption();

	        excelReadOption.setFilePath(destFile.getAbsolutePath());
 
	        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA","AB","AC");


	        excelReadOption.setStartRow(2);

	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);

	        int rtn=1;
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        
	        String name="";
	        String err_column="";
	        
	        try {

	            for (Map<String, String> map : excelContent) {
	            	map.put("INS_ID", ins_id);
	            	System.out.println("map Ȯ�� : " + map.toString());
	            	name = map.get("D");
	            	
	            	manageDAO.insertConfirmManageExcel(map);
	            }
	            
		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="�Է���";}
		    	if(array[1].contains("CELL_NUM")) {err_column="�ڵ�����ȣ";}
		    	if(array[1].contains("PATIENT_NUM")) {err_column="ȯ�ڹ�ȣ";}
		    	if(array[1].contains("PATIENT_NAME")) {err_column="ȯ�ڸ�";}
		    	if(array[1].contains("CONTACT_NUM")) {err_column="�����ڹ�ȣ";}
		    	if(array[1].contains("CONTACT_NAME")) {err_column="�����ڸ�";}
		    	if(array[1].contains("CONFIRM_GRADE")) {err_column="Ȯ������";}
		    	if(array[1].contains("SEX")) {err_column="����";}
		    	if(array[1].contains("BIRTH")) {err_column="�������";}
		    	if(array[1].contains("JOB")) {err_column="����";}
		    	if(array[1].contains("ORG_GUBUN")) {err_column="�ּұ���";}
		    	if(array[1].contains("ORG_DONG")) {err_column="�ּҵ�";}
		    	if(array[1].contains("ORG_ADDRESS")) {err_column="�ּ�";}
		    	if(array[1].contains("ORG_ADDRESS_DTL")) {err_column="�ּһ�";}
		    	if(array[1].contains("INFECT_GUBUN")) {err_column="������α���";}
		    	if(array[1].contains("INFECT_DONG")) {err_column="������ε�";}
		    	if(array[1].contains("INFECT_DAE")) {err_column="������δ뱸��";}
		    	if(array[1].contains("INFECT_SO")) {err_column="������μұ���";}
		    	if(array[1].contains("INFECT_ASFECT_AREA")) {err_column="�����������";}
		    	if(array[1].contains("INFECT_ASFECT_AREA_DTL")) {err_column="����������λ��ּ�";}
		    	if(array[1].contains("INFECT_JIPDAN_GUBUN")) {err_column="���ܰ�������";}
		    	if(array[1].contains("INFECT_JIPDAN_GUBUN_FACILITY")) {err_column="�����ü�";}
		    	if(array[1].contains("CONFIRM_DATE")) {err_column="Ȯ�� ������";}
		    	if(array[1].contains("HOSPITAL")) {err_column="�Կ�����";}
		    	if(array[1].contains("DISCHARGE_DATE")) {err_column="�������";}
		    	if(array[1].contains("REMARK")) {err_column="���";}
		    	if(array[1].contains("DEATH")) {err_column="���";}
		    	if(array[1].contains("TA_CONFIRM_YN")) {err_column="Ÿ���� Ȯ�� ����";}
		    	if(array[1].contains("ICHUP_YN")) {err_column="�������� ��ø �ڷ�";}
		    	
		    	resultMap.put("err_msg", "���� ���ε��� " + name + "�� �� "+ err_column + " �ڷῡ�� ������ �߻��Ͽ����ϴ�.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	

		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	
	
	/*�����ڰ��ݸ���*/
	@Override
	public List<Map<String, Object>> selectDomesticContactList(Map<String, Object> map) throws Exception {
		return manageDAO.selectDomesticContactList(map);
	}
	
	
	@Override
	public Map<String, Object> domesticContactExcelUp(File destFile,String ins_id){
		System.out.println("domesticContactExcelUp service Ž");
		System.out.println("ins_id Ȯ�� : " + ins_id);
		
	    	ExcelReadOption excelReadOption = new ExcelReadOption();

	        excelReadOption.setFilePath(destFile.getAbsolutePath());
	        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
	        excelReadOption.setStartRow(2);
	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);
			int rtn=1;
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        String name="";
	        String err_column="";
	        String dongnm = "";
	        try {
	            for (Map<String, String> map : excelContent) {
	            	if(map.get("A") == null){map.put("A", "");}
	            	if(map.get("B") == null){map.put("B", "");}
	            	if(map.get("C") == null){map.put("C", "");}
	            	if(map.get("D") == null){map.put("D", "");}
	            	if(map.get("E") == null){map.put("E", "");}
	            	if(map.get("F") == null){map.put("F", "");}
	            	if(map.get("G") == null){map.put("G", "");}
	            	if(map.get("H") == null){map.put("H", "");}
	            	if(map.get("I") == null){map.put("I", "");}
	            	if(map.get("J") == null){map.put("J", "");}
	            	if(map.get("K") == null){map.put("K", "");}
	            	if(map.get("L") == null){map.put("L", "");}
	            	if(map.get("M") == null){map.put("M", "");}
	            	if(map.get("N") == null){map.put("N", "");}
	            	if(map.get("N") == null){map.put("N", "");}
	            	if(map.get("O") == null){map.put("O", "");}
	            	if(map.get("P") == null){map.put("P", "");}
	            	if(map.get("Q") == null){map.put("Q", "");}
	            	if(map.get("R") == null){map.put("R", "");}
	            	if(map.get("S") == null){map.put("S", "");}
	            	if(map.get("T") == null){map.put("T", "");}
	            	if(map.get("U") == null){map.put("U", "");}
	            	if(map.get("V") == null){map.put("V", "");}
	            	if(map.get("W") == null){map.put("W", "");}
	            	if(map.get("X") == null){map.put("X", "");}
	            	if(map.get("Y") == null){map.put("Y", "");}
	            	if(map.get("Z") == null){map.put("Z", "");}
	            	if(map.get("H")!=""){
		            	dongnm = rtnDongAddr(map.get("H"));
		            	map.put("G", dongnm);
	            	}
	            	
	            	map.put("INS_ID", ins_id);
	            	name = map.get("C");
	            	manageDAO.insertDomesticContactManageExcel(map);
	            }

		    } catch (Exception e) {
		    	
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="�Է�����";}
		    	if(array[1].contains("CELL_NUM")) {err_column="�ڵ�����ȣ";}
		    	if(array[1].contains("NAME")) {err_column="�̸�";}
		    	if(array[1].contains("SEX")) {err_column="����";}
		    	if(array[1].contains("BIRTH")) {err_column="�������";}
		    	if(array[1].contains("JOB")) {err_column="����";}
		    	if(array[1].contains("SELF_ISO_AREA_DONG")) {err_column="�ڰ��ݸ�����";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS")) {err_column="�ڰ��ݸ���";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS_DTL")) {err_column="�ڰ��ݸ�����";}
		    	if(array[1].contains("PATIENT_NUM")) {err_column="ȯ�ڹ�ȣ";}
		    	if(array[1].contains("PATIENT_NAME")) {err_column="ȯ�ڸ�";}
		    	if(array[1].contains("CONTACT_NUM")) {err_column="�����ڹ�ȣ";}
		    	if(array[1].contains("CONTACT_NAME")) {err_column="�����ڸ�";}
		    	if(array[1].contains("FINAL_CONTACT")) {err_column="����������";}
		    	if(array[1].contains("MONITOR_START")) {err_column="����͸�������";}
		    	if(array[1].contains("MONITOR_END")) {err_column="����͸�������";}
		    	if(array[1].contains("CONTACT_ADDRESS")) {err_column="�������";}
		    	if(array[1].contains("CONTACT_TYPE")) {err_column="��������";}
		    	if(array[1].contains("CONTACT_GUBUN")) {err_column="�����ڱ���";}
		    	if(array[1].contains("PATIENT_YN")) {err_column="ȯ�ڿ���";}
		    	if(array[1].contains("BALGUB_YN")) {err_column="������ �߱� ����";}
		    	if(array[1].contains("DAMDANG_DEPART")) {err_column="�Ҽ�";}
		    	if(array[1].contains("DAMDANG_CLASS")) {err_column="����";}
		    	if(array[1].contains("DAMDANG_POSITION")) {err_column="����";}
		    	if(array[1].contains("DAMDANG_NAME")) {err_column="�����������";}
		    	if(array[1].contains("DAMDANG_CALL")) {err_column="������ȣ";}

		    	
		    	resultMap.put("err_msg", "���� ���ε��� " + name + "�� �� "+ err_column + " �ڷῡ�� ������ �߻��Ͽ����ϴ�.");
		    	resultMap.put("err_msg_dtl", array[1]);
				
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	
	/*�ؿ��Ա���*/
	@Override
	public List<Map<String, Object>> selectOverseaList(Map<String, Object> map) throws Exception {
		return manageDAO.selectOverseaList(map);
	}
	
	
	@Override
	public Map<String, Object> overseaExcelUp(File destFile,String ins_id){
		System.out.println("overseaExcelUp service Ž");
		System.out.println("ins_id Ȯ�� : " + ins_id);
	    	ExcelReadOption excelReadOption = new ExcelReadOption();
	        excelReadOption.setFilePath(destFile.getAbsolutePath());
	        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S");

	        excelReadOption.setStartRow(2);
	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);
			int rtn=1;
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        String name="";
	        String err_column="";
	        String dongnm = "";
	        try {

	            for (Map<String, String> map : excelContent) {
	            	if(map.get("A") == null){map.put("A", "");}
	            	if(map.get("B") == null){map.put("B", "");}
	            	if(map.get("C") == null){map.put("C", "");}
	            	if(map.get("D") == null){map.put("D", "");}
	            	if(map.get("E") == null){map.put("E", "");}
	            	if(map.get("F") == null){map.put("F", "");}
	            	if(map.get("G") == null){map.put("G", "");}
	            	if(map.get("H") == null){map.put("H", "");}
	            	if(map.get("I") == null){map.put("I", "");}
	            	if(map.get("J") == null){map.put("J", "");}
	            	if(map.get("K") == null){map.put("K", "");}
	            	if(map.get("L") == null){map.put("L", "");}
	            	if(map.get("M") == null){map.put("M", "");}
	            	if(map.get("N") == null){map.put("N", "");}
	            	if(map.get("O") == null){map.put("O", "");}
	            	if(map.get("P") == null){map.put("P", "");}
	            	if(map.get("Q") == null){map.put("Q", "");}
	            	if(map.get("R") == null){map.put("R", "");}
	            	if(map.get("S") == null){map.put("S", "");}
	            	
	            	
	            	if(map.get("J")!=""){
		            	dongnm = rtnDongAddr(map.get("J"));
		            	map.put("I", dongnm);
	            	}
	            	
	            	map.put("INS_ID", ins_id);
	            	System.out.println("map Ȯ�� : " + map.toString());
	            	name = map.get("C");
	            	manageDAO.insertOverseaManageExcel(map);
	            }

		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="�Է�����";}
		    	if(array[1].contains("CELL_NUM")) {err_column="�ڵ�����ȣ";}
		    	if(array[1].contains("NAME")) {err_column="�̸�";}
		    	if(array[1].contains("SEX")) {err_column="����";}
		    	if(array[1].contains("BIRTH")) {err_column="�������";}
		    	if(array[1].contains("JOB")) {err_column="����";}
		    	if(array[1].contains("ORG_ADDRESS")) {err_column="�ּ�";}
		    	if(array[1].contains("ORG_ADDRESS_DTL")) {err_column="�ּһ�";}
		    	if(array[1].contains("SELF_ISO_AREA_DONG")) {err_column="�ڰ��ݸ�����";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS")) {err_column="�ڰ��ݸ���";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS_DTL")) {err_column="�ڰ��ݸ�����";}
		    	if(array[1].contains("COME_DATE")) {err_column="�Ա���";}
		    	if(array[1].contains("FREE_DATE")) {err_column="�ݸ�������";}
		    	if(array[1].contains("VISIT_NATION")) {err_column="ü������";}
		    	if(array[1].contains("DAMDANG_DEPART")) {err_column="�Ҽ�";}
		    	if(array[1].contains("DAMDANG_CLASS")) {err_column="����";}
		    	if(array[1].contains("DAMDANG_POSITION")) {err_column="����";}
		    	if(array[1].contains("DAMDANG_NAME")) {err_column="�����������";}
		    	if(array[1].contains("DAMDANG_CALL")) {err_column="������ȣ";}
		    	
		    	resultMap.put("err_msg", "���� ���ε��� " + name + "�� �� "+ err_column + " �ڷῡ�� ������ �߻��Ͽ����ϴ�.");
		    	resultMap.put("err_msg_dtl", array[1]);
				
		    	
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	        
	  }
	
	/*���ο�*/
	@Override
	public List<Map<String, Object>> selectConsultList(Map<String, Object> map) throws Exception {
		return manageDAO.selectConsultList(map);
	}
	
	
	@Override
	public Map<String, Object> consultExcelUp(File destFile,String ins_id){
	    	ExcelReadOption excelReadOption = new ExcelReadOption();

	        excelReadOption.setFilePath(destFile.getAbsolutePath());
 
	        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H");


	        excelReadOption.setStartRow(2);

	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);
	        
			int rtn=1;
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        
	        String name="";
	        String err_column="";

	        try {

	            for (Map<String, String> map : excelContent) {
	            	map.put("INS_ID", ins_id);
	            	name = map.get("E");
	            	manageDAO.insertConsultExcel(map);
	            }

		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="�Է�����";}
		    	if(array[1].contains("CONSULT_TIME")) {err_column="�����ð�";}
		    	if(array[1].contains("MINWON_NAME")) {err_column="�̸�";}
		    	if(array[1].contains("SEX")) {err_column="����";}
		    	if(array[1].contains("MINWON_PHONE")) {err_column="����ó";}
		    	if(array[1].contains("CONSULT_GUBUN")) {err_column="��������";}
		    	if(array[1].contains("MINWON_CONTENT")) {err_column="��㳻��";}
		    	if(array[1].contains("PROCESS_RESULT")) {err_column="ó�����";}
		    	if(array[1].contains("REMARK")) {err_column="���";}

		    	
		    	resultMap.put("err_msg", "���� ���ε��� " + name + "�� �� "+ err_column + " �ڷῡ�� ������ �߻��Ͽ����ϴ�.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	

	
	/*���������*/
	@Override
	public List<Map<String, Object>> selectClinicList(Map<String, Object> map) throws Exception {
		return manageDAO.selectClinicList(map);
	}
	
	
	@Override
	public Map<String, Object> clinicExcelUp(File destFile,String ins_id){
		System.out.println("clinicExcelUp service Ž");
		System.out.println("ins_id Ȯ�� : " + ins_id);
		
	    	ExcelReadOption excelReadOption = new ExcelReadOption();

	        excelReadOption.setFilePath(destFile.getAbsolutePath());
 
	        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N");


	        excelReadOption.setStartRow(2);

	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);
	        
			int rtn=1;
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        
	        String name="";
	        String err_column="";
	        String dongnm = "";

	        try {

	            for (Map<String, String> map : excelContent) {
	            	
	            	if(map.get("A") == null){map.put("A", "");}
	            	if(map.get("B") == null){map.put("B", "");}
	            	if(map.get("C") == null){map.put("C", "");}
	            	if(map.get("D") == null){map.put("D", "");}
	            	if(map.get("E") == null){map.put("E", "");}
	            	if(map.get("F") == null){map.put("F", "");}
	            	if(map.get("G") == null){map.put("G", "");}
	            	if(map.get("H") == null){map.put("H", "");}
	            	if(map.get("I") == null){map.put("I", "");}
	            	if(map.get("J") == null){map.put("J", "");}
	            	if(map.get("K") == null){map.put("K", "");}
	            	if(map.get("L") == null){map.put("L", "");}
	            	if(map.get("M") == null){map.put("M", "");}
	            	if(map.get("N") == null){map.put("N", "");}
	            	
	            	map.put("INS_ID", ins_id);
	            	System.out.println("map Ȯ�� : " + map.toString());
	            	
	            	name = map.get("D");
	            	
	            	if(map.get("M")!="" && map.get("J").equals("������")){
		            	dongnm = rtnDongAddr(map.get("M"));
		            	map.put("K", dongnm);
		            	System.out.println("dongnm Ȯ�� : " + dongnm);
		            	
	            	}
	            	
	            	manageDAO.insertClinicManageExcel(map);
	            }

		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="�Է���";}
		    	if(array[1].contains("CLINIC_VISIT_DATE")) {err_column="����� �湮����";}
		    	if(array[1].contains("CELL_NUM")) {err_column="�ڵ�����ȣ";}
		    	if(array[1].contains("NAME")) {err_column="�̸�";}
		    	if(array[1].contains("SEX")) {err_column="����";}
		    	if(array[1].contains("BIRTH")) {err_column="�������";}
		    	if(array[1].contains("JOB")) {err_column="����";}
		    	if(array[1].contains("INSPECTION_CASE")) {err_column="�������̽�";}
		    	if(array[1].contains("SARAE_GUBUN")) {err_column="��ʺз�";}
		    	if(array[1].contains("SUSPICION_GUBUN")) {err_column="��α���";}
		    	if(array[1].contains("SUSPICION_DAE")) {err_column="�������뱸��";}
		    	if(array[1].contains("SUSPICION_SO")) {err_column="�������ұ���";}
		    	if(array[1].contains("ORG_ADDRESS")) {err_column="�������ּ�";}
		    	if(array[1].contains("ORG_ADDRESS_DTL")) {err_column="�������ּһ�";}

		    	
		    	resultMap.put("err_msg", "���� ���ε��� " + name + "�� �� "+ err_column + " �ڷῡ�� ������ �߻��Ͽ����ϴ�.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
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
        System.out.println("�ּ� Ȯ�� : " + sb.toString());
        if(!jusoArray.isEmpty()){
	        JSONObject jusoObject = (JSONObject) jusoArray.get(0);
	        dongNm = jusoObject.get("emdNm").toString();
        }else{
        	dongNm = "����Ȯ��";
        }
        
    	br.close();
		
		return dongNm;
	}
	
	/*��������� ������ ����*/
	@Override
	public List<Map<String, Object>> selectGangnamguArrangeList(Map<String, Object> map) throws Exception {
		return manageDAO.selectGangnamguArrangeList(map);
	}	
	
	@Override
	public void updateGangnamguArrange(Map<String, Object> map) throws Exception {
		manageDAO.updateGangnamguArrange(map);
	}
	
	
	/*��������� ������ ����*/
	@Override
	public List<Map<String, Object>> selectTasiguArrangeList(Map<String, Object> map) throws Exception {
		return manageDAO.selectTasiguArrangeList(map);
	}	
	
	@Override
	public void updateTasiguArrange(Map<String, Object> map) throws Exception {
		manageDAO.updateTasiguArrange(map);
	}
	
	
	
	
	/*�����ٿ�ε�*/
	@Override
	public List<Map<String, Object>> selectConfirmExcel(Map<String, Object> map) throws Exception {
		return manageDAO.selectConfirmExcel(map);
	}
	
	@Override
	public List<Map<String, Object>> selectClinicExcel(Map<String, Object> map) throws Exception {
		return manageDAO.selectClinicExcel(map);
	}
	
	@Override
	public List<Map<String, Object>> selectOverseaExcel(Map<String, Object> map) throws Exception {
		return manageDAO.selectOverseaExcel(map);
	}
	
	@Override
	public List<Map<String, Object>> selectDomesticExcel(Map<String, Object> map) throws Exception {
		return manageDAO.selectDomesticExcel(map);
	}
	
	
}
