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
	
	/*확진자*/
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
		System.out.println("confirmManageExcelUp service 탐");
		System.out.println("ins_id 확인 : " + ins_id);
		
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
	            	System.out.println("map 확인 : " + map.toString());
	            	name = map.get("D");
	            	
	            	manageDAO.insertConfirmManageExcel(map);
	            }
	            
		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="입력일";}
		    	if(array[1].contains("CELL_NUM")) {err_column="핸드폰번호";}
		    	if(array[1].contains("PATIENT_NUM")) {err_column="환자번호";}
		    	if(array[1].contains("PATIENT_NAME")) {err_column="환자명";}
		    	if(array[1].contains("CONTACT_NUM")) {err_column="접촉자번호";}
		    	if(array[1].contains("CONTACT_NAME")) {err_column="접촉자명";}
		    	if(array[1].contains("CONFIRM_GRADE")) {err_column="확진차수";}
		    	if(array[1].contains("SEX")) {err_column="성별";}
		    	if(array[1].contains("BIRTH")) {err_column="생년월일";}
		    	if(array[1].contains("JOB")) {err_column="직업";}
		    	if(array[1].contains("ORG_GUBUN")) {err_column="주소구분";}
		    	if(array[1].contains("ORG_DONG")) {err_column="주소동";}
		    	if(array[1].contains("ORG_ADDRESS")) {err_column="주소";}
		    	if(array[1].contains("ORG_ADDRESS_DTL")) {err_column="주소상세";}
		    	if(array[1].contains("INFECT_GUBUN")) {err_column="감염경로구분";}
		    	if(array[1].contains("INFECT_DONG")) {err_column="감염경로동";}
		    	if(array[1].contains("INFECT_DAE")) {err_column="감염경로대구분";}
		    	if(array[1].contains("INFECT_SO")) {err_column="감염경로소구분";}
		    	if(array[1].contains("INFECT_ASFECT_AREA")) {err_column="추정감염경로";}
		    	if(array[1].contains("INFECT_ASFECT_AREA_DTL")) {err_column="추정감염경로상세주소";}
		    	if(array[1].contains("INFECT_JIPDAN_GUBUN")) {err_column="집단감염구분";}
		    	if(array[1].contains("INFECT_JIPDAN_GUBUN_FACILITY")) {err_column="감염시설";}
		    	if(array[1].contains("CONFIRM_DATE")) {err_column="확진 판정일";}
		    	if(array[1].contains("HOSPITAL")) {err_column="입원병원";}
		    	if(array[1].contains("DISCHARGE_DATE")) {err_column="퇴원일자";}
		    	if(array[1].contains("REMARK")) {err_column="비고";}
		    	if(array[1].contains("DEATH")) {err_column="사망";}
		    	if(array[1].contains("TA_CONFIRM_YN")) {err_column="타지역 확진 판정";}
		    	if(array[1].contains("ICHUP_YN")) {err_column="강남구민 이첩 자료";}
		    	
		    	resultMap.put("err_msg", "엑셀 업로드중 " + name + "님 의 "+ err_column + " 자료에서 에러가 발생하였습니다.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	

		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	
	
	/*국내자가격리자*/
	@Override
	public List<Map<String, Object>> selectDomesticContactList(Map<String, Object> map) throws Exception {
		return manageDAO.selectDomesticContactList(map);
	}
	
	
	@Override
	public Map<String, Object> domesticContactExcelUp(File destFile,String ins_id){
		System.out.println("domesticContactExcelUp service 탐");
		System.out.println("ins_id 확인 : " + ins_id);
		
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
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="입력일자";}
		    	if(array[1].contains("CELL_NUM")) {err_column="핸드폰번호";}
		    	if(array[1].contains("NAME")) {err_column="이름";}
		    	if(array[1].contains("SEX")) {err_column="성별";}
		    	if(array[1].contains("BIRTH")) {err_column="생년월일";}
		    	if(array[1].contains("JOB")) {err_column="직업";}
		    	if(array[1].contains("SELF_ISO_AREA_DONG")) {err_column="자가격리지동";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS")) {err_column="자가격리지";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS_DTL")) {err_column="자가격리지상세";}
		    	if(array[1].contains("PATIENT_NUM")) {err_column="환자번호";}
		    	if(array[1].contains("PATIENT_NAME")) {err_column="환자명";}
		    	if(array[1].contains("CONTACT_NUM")) {err_column="접촉자번호";}
		    	if(array[1].contains("CONTACT_NAME")) {err_column="접촉자명";}
		    	if(array[1].contains("FINAL_CONTACT")) {err_column="최종접촉일";}
		    	if(array[1].contains("MONITOR_START")) {err_column="모니터링시작일";}
		    	if(array[1].contains("MONITOR_END")) {err_column="모니터링종료일";}
		    	if(array[1].contains("CONTACT_ADDRESS")) {err_column="접촉장소";}
		    	if(array[1].contains("CONTACT_TYPE")) {err_column="접촉유형";}
		    	if(array[1].contains("CONTACT_GUBUN")) {err_column="접촉자구분";}
		    	if(array[1].contains("PATIENT_YN")) {err_column="환자여부";}
		    	if(array[1].contains("BALGUB_YN")) {err_column="통지서 발급 여부";}
		    	if(array[1].contains("DAMDANG_DEPART")) {err_column="소속";}
		    	if(array[1].contains("DAMDANG_CLASS")) {err_column="직위";}
		    	if(array[1].contains("DAMDANG_POSITION")) {err_column="직급";}
		    	if(array[1].contains("DAMDANG_NAME")) {err_column="전담공무원명";}
		    	if(array[1].contains("DAMDANG_CALL")) {err_column="내선번호";}

		    	
		    	resultMap.put("err_msg", "엑셀 업로드중 " + name + "님 의 "+ err_column + " 자료에서 에러가 발생하였습니다.");
		    	resultMap.put("err_msg_dtl", array[1]);
				
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	
	/*해외입국자*/
	@Override
	public List<Map<String, Object>> selectOverseaList(Map<String, Object> map) throws Exception {
		return manageDAO.selectOverseaList(map);
	}
	
	
	@Override
	public Map<String, Object> overseaExcelUp(File destFile,String ins_id){
		System.out.println("overseaExcelUp service 탐");
		System.out.println("ins_id 확인 : " + ins_id);
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
	            	System.out.println("map 확인 : " + map.toString());
	            	name = map.get("C");
	            	manageDAO.insertOverseaManageExcel(map);
	            }

		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="입력일자";}
		    	if(array[1].contains("CELL_NUM")) {err_column="핸드폰번호";}
		    	if(array[1].contains("NAME")) {err_column="이름";}
		    	if(array[1].contains("SEX")) {err_column="성별";}
		    	if(array[1].contains("BIRTH")) {err_column="생년월일";}
		    	if(array[1].contains("JOB")) {err_column="직업";}
		    	if(array[1].contains("ORG_ADDRESS")) {err_column="주소";}
		    	if(array[1].contains("ORG_ADDRESS_DTL")) {err_column="주소상세";}
		    	if(array[1].contains("SELF_ISO_AREA_DONG")) {err_column="자가격리지동";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS")) {err_column="자가격리지";}
		    	if(array[1].contains("SELF_ISO_AREA_ADDRESS_DTL")) {err_column="자가격리지상세";}
		    	if(array[1].contains("COME_DATE")) {err_column="입국일";}
		    	if(array[1].contains("FREE_DATE")) {err_column="격리해제일";}
		    	if(array[1].contains("VISIT_NATION")) {err_column="체류국가";}
		    	if(array[1].contains("DAMDANG_DEPART")) {err_column="소속";}
		    	if(array[1].contains("DAMDANG_CLASS")) {err_column="직위";}
		    	if(array[1].contains("DAMDANG_POSITION")) {err_column="직급";}
		    	if(array[1].contains("DAMDANG_NAME")) {err_column="전담공무원명";}
		    	if(array[1].contains("DAMDANG_CALL")) {err_column="내선번호";}
		    	
		    	resultMap.put("err_msg", "엑셀 업로드중 " + name + "님 의 "+ err_column + " 자료에서 에러가 발생하였습니다.");
		    	resultMap.put("err_msg_dtl", array[1]);
				
		    	
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	        
	  }
	
	/*상담민원*/
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
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="입력일자";}
		    	if(array[1].contains("CONSULT_TIME")) {err_column="업무시간";}
		    	if(array[1].contains("MINWON_NAME")) {err_column="이름";}
		    	if(array[1].contains("SEX")) {err_column="성별";}
		    	if(array[1].contains("MINWON_PHONE")) {err_column="연락처";}
		    	if(array[1].contains("CONSULT_GUBUN")) {err_column="업무구분";}
		    	if(array[1].contains("MINWON_CONTENT")) {err_column="상담내용";}
		    	if(array[1].contains("PROCESS_RESULT")) {err_column="처리결과";}
		    	if(array[1].contains("REMARK")) {err_column="비고";}

		    	
		    	resultMap.put("err_msg", "엑셀 업로드중 " + name + "님 의 "+ err_column + " 자료에서 에러가 발생하였습니다.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	

	
	/*선별진료소*/
	@Override
	public List<Map<String, Object>> selectClinicList(Map<String, Object> map) throws Exception {
		return manageDAO.selectClinicList(map);
	}
	
	
	@Override
	public Map<String, Object> clinicExcelUp(File destFile,String ins_id){
		System.out.println("clinicExcelUp service 탐");
		System.out.println("ins_id 확인 : " + ins_id);
		
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
	            	System.out.println("map 확인 : " + map.toString());
	            	
	            	name = map.get("D");
	            	
	            	if(map.get("M")!="" && map.get("J").equals("강남구")){
		            	dongnm = rtnDongAddr(map.get("M"));
		            	map.put("K", dongnm);
		            	System.out.println("dongnm 확인 : " + dongnm);
		            	
	            	}
	            	
	            	manageDAO.insertClinicManageExcel(map);
	            }

		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("WRITE_DATE")) {err_column="입력일";}
		    	if(array[1].contains("CLINIC_VISIT_DATE")) {err_column="진료소 방문일자";}
		    	if(array[1].contains("CELL_NUM")) {err_column="핸드폰번호";}
		    	if(array[1].contains("NAME")) {err_column="이름";}
		    	if(array[1].contains("SEX")) {err_column="성별";}
		    	if(array[1].contains("BIRTH")) {err_column="생년월일";}
		    	if(array[1].contains("JOB")) {err_column="직업";}
		    	if(array[1].contains("INSPECTION_CASE")) {err_column="감염케이스";}
		    	if(array[1].contains("SARAE_GUBUN")) {err_column="사례분류";}
		    	if(array[1].contains("SUSPICION_GUBUN")) {err_column="경로구분";}
		    	if(array[1].contains("SUSPICION_DAE")) {err_column="거주지대구분";}
		    	if(array[1].contains("SUSPICION_SO")) {err_column="거주지소구분";}
		    	if(array[1].contains("ORG_ADDRESS")) {err_column="거주지주소";}
		    	if(array[1].contains("ORG_ADDRESS_DTL")) {err_column="거주지주소상세";}

		    	
		    	resultMap.put("err_msg", "엑셀 업로드중 " + name + "님 의 "+ err_column + " 자료에서 에러가 발생하였습니다.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	
	//동주소 추출
	public String rtnDongAddr(String jibun) throws Exception {
		String currentPage = "1"; 
		String countPerPage = "1";
		String resultType = "json";
		//String confmKey = "devU01TX0FVVEgyMDIwMDgxNDA4MzU0MzExMDA2NDU="; //개발
		String confmKey = "U01TX0FVVEgyMDIwMDgxNDE0NDczNjExMDA2NjI="; //운영
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
        System.out.println("주소 확인 : " + sb.toString());
        if(!jusoArray.isEmpty()){
	        JSONObject jusoObject = (JSONObject) jusoArray.get(0);
	        dongNm = jusoObject.get("emdNm").toString();
        }else{
        	dongNm = "동미확인";
        }
        
    	br.close();
		
		return dongNm;
	}
	
	/*선별진료소 데이터 정비*/
	@Override
	public List<Map<String, Object>> selectGangnamguArrangeList(Map<String, Object> map) throws Exception {
		return manageDAO.selectGangnamguArrangeList(map);
	}	
	
	@Override
	public void updateGangnamguArrange(Map<String, Object> map) throws Exception {
		manageDAO.updateGangnamguArrange(map);
	}
	
	
	/*선별진료소 데이터 정비*/
	@Override
	public List<Map<String, Object>> selectTasiguArrangeList(Map<String, Object> map) throws Exception {
		return manageDAO.selectTasiguArrangeList(map);
	}	
	
	@Override
	public void updateTasiguArrange(Map<String, Object> map) throws Exception {
		manageDAO.updateTasiguArrange(map);
	}
	
	
	
	
	/*엑셀다운로드*/
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
