package service.welfare.service;
//
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
//
public interface WelfareService {
	
	List<Map<String, Object>> welfarSelect(Map<String, Object> map) throws Exception;
	
	
	Map<String, Object> welfarInsert(Map<String, Object> map) throws Exception;
	
	Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> updateDocIssueReserve(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> updateWelfareInfo(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	List<Map<String, Object>> welfarUserSelectList(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> welfareSelectCt(Map<String, Object> map) throws Exception;
	
}

