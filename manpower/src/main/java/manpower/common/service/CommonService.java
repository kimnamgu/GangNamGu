package manpower.common.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface CommonService {

	Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;
	
	void updateDownCount(Map<String, Object> map) throws Exception;
	
	void updateApplyCount(Map<String, Object> map) throws Exception;
	
	Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> ssoLogin(Map<String, Object> map) throws Exception;
	
	void updateUserinfo(Map<String, Object> map) throws Exception;
	
	void insertUserinfo(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateEmployeeTransData(Map<String, Object> map) throws Exception;
	
	void insertDeptData(Map<String, Object> map) throws Exception;
	
	void deleteDeptData(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> selectEmployeeTransIdList() throws Exception;
	
	List<Map<String, Object>> selectDeptList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> structList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> purposeList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> stateList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> dongList(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> popJikWonList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception;
	
	void updateIdApprove(Map<String, Object> map) throws Exception;
	
	Map<String, Object> idApproveListDetail(Map<String, Object> map) throws Exception;
	
	void updateIdApproveList(Map<String, Object> map) throws Exception;
	
	void deleteIdApproveList(Map<String, Object> map) throws Exception;
	
	
	
	void deleteScmsUserData(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectSsoUserList(Map<String, Object> map) throws Exception;
	
	
	
	List<Map<String, Object>> yearList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> dutyMonthList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> deptList(Map<String, Object> map) throws Exception;
	
	void excelUpload(File destFile,String ins_id,String ins_dept) throws Exception;

	SXSSFWorkbook excelFileDownloadProcess(List<Map<String, Object>> resultlist); 

}
